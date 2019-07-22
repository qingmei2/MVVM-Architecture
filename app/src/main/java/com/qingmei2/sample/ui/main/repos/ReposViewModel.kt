package com.qingmei2.sample.ui.main.repos

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.qingmei2.rhine.base.viewmodel.BaseViewModel
import com.qingmei2.rhine.ext.reactivex.copyMap
import com.qingmei2.rhine.util.RxSchedulers
import com.qingmei2.rhine.util.SingletonHolderSingleArg
import com.qingmei2.sample.base.Result
import com.uber.autodispose.autoDisposable
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

@SuppressWarnings("checkResult")
class ReposViewModel(
        private val repo: ReposRepository
) : BaseViewModel() {

    private val mViewStateSubject: BehaviorSubject<ReposViewState> =
            BehaviorSubject.createDefault(ReposViewState.initial())

    init {
        observeViewState()
                .map { it.isLoading }
                .distinctUntilChanged()
                .filter { it }
                .flatMapCompletable { refreshDataSource() }
                .autoDisposable(this)
                .subscribe()

        repo.subscribeRemoteRequestState()
                .observeOn(RxSchedulers.ui)
                .autoDisposable(this)
                .subscribe { result ->
                    when (result) {
                        is Result.Failure -> mViewStateSubject.copyMap { state ->
                            state.copy(isLoading = false, throwable = result.error, pagedList = null, nextPageData = null)
                        }
                        is Result.Success -> mViewStateSubject.copyMap { state ->
                            state.copy(isLoading = false, throwable = null, pagedList = null, nextPageData = result.data)
                        }
                    }
                }

        repo.fetchPagedListFromDb { mViewStateSubject.value!!.sort }
                .subscribeOn(RxSchedulers.io)
                .toObservable()
                .autoDisposable(this)
                .subscribe { pagedList ->
                    mViewStateSubject.copyMap { state ->
                        state.copy(isLoading = false, throwable = null, pagedList = pagedList, nextPageData = null)
                    }
                }
    }

    fun onSortChanged(sort: String) {
        if (sort != mViewStateSubject.value!!.sort)
            mViewStateSubject.copyMap { state ->
                state.copy(isLoading = true, throwable = null, pagedList = null, nextPageData = null, sort = sort)
            }
    }

    fun observeViewState(): Observable<ReposViewState> {
        return mViewStateSubject.hide().distinctUntilChanged()
    }

    fun refreshDataSource(): Completable {
        return Completable.fromCallable {
            repo.refreshDataSource(mViewStateSubject.value!!.sort)
        }.subscribeOn(RxSchedulers.io)
    }

    override fun onCleared() {
        super.onCleared()

        repo.mAutoDisposeObserver.onNext(Unit)
        repo.mAutoDisposeObserver.onComplete()
    }

    companion object {

        const val sortByCreated: String = "created"

        const val sortByUpdate: String = "updated"

        const val sortByLetter: String = "full_name"

        fun instance(fragment: Fragment,
                     repo: ReposRepository): ReposViewModel =
                ViewModelProviders
                        .of(fragment, ReposViewModelFactory.getInstance(repo))
                        .get(ReposViewModel::class.java)
    }
}

class ReposViewModelFactory(
        private val repo: ReposRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ReposViewModel(repo) as T
    }

    companion object : SingletonHolderSingleArg<ReposViewModelFactory, ReposRepository>(::ReposViewModelFactory)
}