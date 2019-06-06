package com.qingmei2.sample.ui.main.home

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import arrow.core.Option
import arrow.core.none
import arrow.core.some
import com.qingmei2.rhine.base.viewmodel.BaseViewModel
import com.qingmei2.rhine.ext.arrow.whenNotNull
import com.qingmei2.rhine.util.SingletonHolderSingleArg
import com.qingmei2.sample.base.Result
import com.qingmei2.sample.entity.ReceivedEvent
import com.uber.autodispose.autoDisposable
import io.reactivex.Completable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

@SuppressWarnings("checkResult")
class HomeViewModel(
        private val repo: HomeRepository
) : BaseViewModel() {

    val pagedListEventSubject = BehaviorSubject.create<PagedList<ReceivedEvent>>()
    val refreshStateChangedEventSubject = BehaviorSubject.create<Boolean>()
    private val mErrorEventSubject = BehaviorSubject.create<Throwable>()

    init {
        refreshStateChangedEventSubject
                .distinctUntilChanged()
                .filter { it }
                .autoDisposable(this)
                .subscribe { refreshDataSource() }

        // only subscribe once in fragment scope
        repo.subscribeRemoteRequestState()
                .doOnNext { result ->
                    when (result) {
                        is Result.Loading -> applyState()
                        is Result.Idle -> applyState()
                        is Result.Failure -> {
                            applyState(error = result.error.some())
                            refreshStateChangedEventSubject.onNext(false)
                        }
                        is Result.Success -> {
                            refreshStateChangedEventSubject.onNext(false)
                        }
                    }
                }
                .autoDisposable(this)
                .subscribe()

        fetchPagedListFromDbCompletable()
                .autoDisposable(this)
                .subscribe()
    }

    fun refreshDataSource() {
        repo.refreshPagedList()
    }

    private fun fetchPagedListFromDbCompletable(): Completable {
        return repo.initPagedListFromDb()
                .doOnNext { pagedListEventSubject.onNext(it) }
                .ignoreElements()
    }

    private fun applyState(error: Option<Throwable> = none()) {
        error.whenNotNull { this.mErrorEventSubject.onNext(it) }
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