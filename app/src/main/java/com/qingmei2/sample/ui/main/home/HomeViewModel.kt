package com.qingmei2.sample.ui.main.home

import androidx.annotation.WorkerThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.qingmei2.rhine.base.viewmodel.BaseViewModel
import com.qingmei2.rhine.ext.paging.PagingRequestHelper
import com.qingmei2.rhine.ext.paging.toRxPagedList
import com.qingmei2.rhine.ext.reactivex.onNextWithLast
import com.qingmei2.rhine.util.RxSchedulers
import com.qingmei2.sample.base.Result
import com.qingmei2.sample.entity.ReceivedEvent
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.Executors

@SuppressWarnings("checkResult")
class HomeViewModel(
        private val repository: HomeRepository
) : BaseViewModel() {

    private val mResultList: PublishProcessor<Result<List<ReceivedEvent>>> =
            PublishProcessor.create()
    private val mViewStateSubject: BehaviorSubject<HomeViewState> =
            BehaviorSubject.createDefault(HomeViewState.initial())

    private val mBoundaryCallback = HomeBoundaryCallback { result, pageIndex ->
        if (result is Result.Success) {
            when (pageIndex == 1) {
                true -> repository.clearAndInsertNewData(result.data)
                false -> repository.insertNewPageData(result.data)
            }
        }
        mResultList.onNext(result)
    }

    init {
        subscribePagedListFlowable()

        // only subscribe once in fragment scope
        mResultList
                .autoDisposable(this)
                .subscribe { result ->
                    when (result) {
                        is Result.Loading ->
                            mViewStateSubject.onNextWithLast { last ->
                                last.copy(isLoading = true, throwable = null)
                            }
                        is Result.Idle ->
                            mViewStateSubject.onNextWithLast { last ->
                                last.copy(isLoading = false, throwable = null)
                            }
                        is Result.Failure ->
                            mViewStateSubject.onNextWithLast { last ->
                                last.copy(isLoading = false, throwable = result.error)
                            }
                        is Result.Success ->
                            mViewStateSubject.onNextWithLast { last ->
                                last.copy(isLoading = false, throwable = null)
                            }
                    }
                }
    }

    private fun subscribePagedListFlowable(): Disposable {
        return repository
                .fetchEventDataSourceFactory()
                .toRxPagedList(
                        boundaryCallback = mBoundaryCallback,
                        fetchSchedulers = RxSchedulers.io
                )
                .autoDisposable(this)
                .subscribe { pagedList ->
                    mViewStateSubject.onNextWithLast { state ->
                        state.copy(isLoading = false, throwable = null, pagedList = pagedList)
                    }
                }
    }

    fun observeViewState(): Observable<HomeViewState> {
        return mViewStateSubject.hide().distinctUntilChanged()
    }

    /**
     * Refresh event list action.
     */
    fun refreshDataSource() {
        repository.fetchEventByPage(1)
                .autoDisposable(this)
                .subscribe { result ->
                    // notify paging update.
                    if (result is Result.Success) {
                        repository.clearAndInsertNewData(result.data)
                    }
                    // notify Refresh state update.
                    mResultList.onNext(result)
                }
    }

    companion object {

        fun instance(fragment: Fragment, repo: HomeRepository): HomeViewModel =
                ViewModelProviders
                        .of(fragment, HomeViewModelFactory(repo))
                        .get(HomeViewModel::class.java)
    }

    inner class HomeBoundaryCallback(
            @WorkerThread private val handleResponse: (Result<List<ReceivedEvent>>, Int) -> Unit
    ) : PagedList.BoundaryCallback<ReceivedEvent>() {

        private val mExecutor = Executors.newSingleThreadExecutor()
        private val mHelper = PagingRequestHelper(mExecutor)

        override fun onZeroItemsLoaded() {
            mHelper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) { callback ->
                repository.fetchEventByPage(1)
                        .doFinally { callback.recordSuccess() }
                        .autoDisposable(this@HomeViewModel)
                        .subscribe { handleResponse(it, 1) }
            }
        }

        override fun onItemAtEndLoaded(itemAtEnd: ReceivedEvent) {
            mHelper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) { callback ->
                val currentPageIndex = (itemAtEnd.indexInResponse / 30) + 1
                val nextPageIndex = currentPageIndex + 1

                repository.fetchEventByPage(nextPageIndex)
                        .doFinally { callback.recordSuccess() }
                        .autoDisposable(this@HomeViewModel)
                        .subscribe { handleResponse(it, nextPageIndex) }
            }
        }
    }
}

class HomeViewModelFactory(private val repo: HomeRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repo) as T
    }
}