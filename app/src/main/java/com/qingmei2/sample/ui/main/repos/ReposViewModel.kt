package com.qingmei2.sample.ui.main.repos

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
import com.qingmei2.sample.entity.Repo
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.Executors

@SuppressWarnings("checkResult")
class ReposViewModel(
        private val repository: ReposRepository
) : BaseViewModel() {

    private val mResultList: PublishProcessor<Result<List<Repo>>> =
            PublishProcessor.create()
    private val mViewStateSubject: BehaviorSubject<ReposViewState> =
            BehaviorSubject.createDefault(ReposViewState.initial())

    private val mBoundaryCallback = RepoBoundaryCallback { result, pageIndex ->
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

        mResultList
                .observeOn(RxSchedulers.ui)
                .autoDisposable(this)
                .subscribe { result ->
                    when (result) {
                        is Result.Failure ->
                            mViewStateSubject.onNextWithLast { last ->
                                last.copy(isLoading = false, throwable = result.error, pagedList = null, nextPageData = null)
                            }
                        is Result.Success ->
                            mViewStateSubject.onNextWithLast { last ->
                                last.copy(isLoading = false, throwable = null, pagedList = null, nextPageData = result.data)
                            }
                    }
                }

        observeViewState()
                .map { it.isLoading }
                .distinctUntilChanged()
                .filter { it }
                .autoDisposable(this)
                .subscribe { refreshDataSource() }
    }

    private fun subscribePagedListFlowable(): Disposable {
        return repository
                .fetchRepoDataSourceFactory()
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

    fun onSortChanged(sort: String) {
        if (sort != fetchCurrentSort())
            mViewStateSubject.onNextWithLast { last ->
                // 'isLoading = true' will trigger refresh action.
                last.copy(isLoading = true, throwable = null, pagedList = null, nextPageData = null, sort = sort)
            }
    }

    private fun fetchCurrentSort(): String {
        // sort is always exist by BehaviorSubject.createDefault(initValue).
        return mViewStateSubject.value?.sort!!
    }

    fun observeViewState(): Observable<ReposViewState> {
        return mViewStateSubject.hide().distinctUntilChanged()
    }

    fun refreshDataSource() {
        repository
                .fetchRepoByPage(fetchCurrentSort(), 1)
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

        const val sortByCreated: String = "created"

        const val sortByUpdate: String = "updated"

        const val sortByLetter: String = "full_name"

        fun instance(fragment: Fragment,
                     repo: ReposRepository): ReposViewModel =
                ViewModelProviders
                        .of(fragment, ReposViewModelFactory(repo))
                        .get(ReposViewModel::class.java)
    }

    inner class RepoBoundaryCallback(
            @WorkerThread private val handleResponse: (Result<List<Repo>>, Int) -> Unit
    ) : PagedList.BoundaryCallback<Repo>() {

        private val mExecutor = Executors.newSingleThreadExecutor()
        private val mHelper = PagingRequestHelper(mExecutor)

        override fun onZeroItemsLoaded() {
            mHelper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) { callback ->
                repository.fetchRepoByPage(fetchCurrentSort(), 1)
                        .doFinally { callback.recordSuccess() }
                        .autoDisposable(this@ReposViewModel)
                        .subscribe { handleResponse(it, 1) }
            }
        }

        override fun onItemAtEndLoaded(itemAtEnd: Repo) {
            mHelper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) { callback ->
                val currentPageIndex = (itemAtEnd.indexInSortResponse / 30) + 1
                val nextPageIndex = currentPageIndex + 1

                repository.fetchRepoByPage(fetchCurrentSort(), nextPageIndex)
                        .doFinally { callback.recordSuccess() }
                        .autoDisposable(this@ReposViewModel)
                        .subscribe { handleResponse(it, nextPageIndex) }
            }
        }
    }
}

class ReposViewModelFactory(
        private val repo: ReposRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ReposViewModel(repo) as T
    }
}