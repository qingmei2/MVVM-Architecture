package com.qingmei2.sample.ui.main.home

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.qingmei2.rhine.base.viewmodel.BaseViewModel
import com.qingmei2.rhine.ext.reactivex.copyMap
import com.qingmei2.rhine.util.SingletonHolderSingleArg
import com.qingmei2.sample.base.Result
import com.qingmei2.sample.entity.ReceivedEvent
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

@SuppressWarnings("checkResult")
class HomeViewModel(
        private val repo: HomeRepository
) : BaseViewModel() {

    val pagedListEventSubject = BehaviorSubject.create<PagedList<ReceivedEvent>>()
    val refreshStateChangedEventSubject = BehaviorSubject.create<Boolean>()
    private val mErrorEventSubject = BehaviorSubject.create<Throwable>()

    private val mHomeViewStateSubject: BehaviorSubject<HomeViewState> =
            BehaviorSubject.createDefault(HomeViewState.initial())

    init {
        refreshStateChangedEventSubject
                .distinctUntilChanged()
                .filter { it }
                .autoDisposable(this)
                .subscribe { refreshDataSource() }

        // only subscribe once in fragment scope
        repo.subscribeRemoteRequestState()
                .autoDisposable(this)
                .subscribe { result ->
                    when (result) {
                        is Result.Loading -> mHomeViewStateSubject.copyMap { viewState ->
                            viewState.copy(isLoading = true, throwable = null)
                        }
                        is Result.Idle -> mHomeViewStateSubject.copyMap { viewState ->
                            viewState.copy(isLoading = false, throwable = null)
                        }
                        is Result.Failure ->
                            mHomeViewStateSubject.copyMap { viewState ->
                                viewState.copy(isLoading = false, throwable = result.error)
                            }
                        is Result.Success ->
                            mHomeViewStateSubject.copyMap { viewState ->
                                viewState.copy(isLoading = false, throwable = null)
                            }
                    }
                }

        repo.initPagedListFromDb()
                .autoDisposable(this)
                .subscribe { pagedList ->
                    mHomeViewStateSubject.copyMap { state ->
                        state.copy(isLoading = false, throwable = null, pagedList = pagedList)
                    }
                }
    }

    fun observeViewState(): Observable<HomeViewState> {
        return mHomeViewStateSubject.hide().distinctUntilChanged()
    }

    fun refreshDataSource() {
        repo.refreshPagedList()
    }

    override fun onCleared() {
        super.onCleared()

        repo.mAutoDisposeObserver.onNext(Unit)
        repo.mAutoDisposeObserver.onComplete()
    }

    companion object {

        fun instance(fragment: Fragment, repo: HomeRepository): HomeViewModel =
                ViewModelProviders
                        .of(fragment, HomeViewModelFactory.getInstance(repo))
                        .get(HomeViewModel::class.java)
    }
}

class HomeViewModelFactory(
        private val repo: HomeRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repo) as T
    }

    companion object : SingletonHolderSingleArg<HomeViewModelFactory, HomeRepository>(::HomeViewModelFactory)
}