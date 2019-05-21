package com.qingmei2.sample.ui.main.repos

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.qingmei2.rhine.base.viewmodel.BaseViewModel
import com.qingmei2.rhine.ext.livedata.toReactiveStream
import com.qingmei2.rhine.util.RxSchedulers
import com.qingmei2.rhine.util.SingletonHolderSingleArg
import com.qingmei2.sample.base.Result
import com.qingmei2.sample.entity.Repo
import com.uber.autodispose.autoDisposable
import io.reactivex.Completable

@SuppressWarnings("checkResult")
class ReposViewModel(
        private val repo: ReposRepository
) : BaseViewModel() {

    private val events: MutableLiveData<List<Repo>> = MutableLiveData()

    val sort: MutableLiveData<String> = MutableLiveData()

    val refreshing: MutableLiveData<Boolean> = MutableLiveData()

    val error: MutableLiveData<Throwable> = MutableLiveData()

    val pagedList = MutableLiveData<PagedList<Repo>>()

    init {
        refreshing.toReactiveStream()
                .distinctUntilChanged()
                .filter { it }
                .autoDisposable(this)
                .subscribe { refreshDataSource() }

        // try auto refreshing page list when sort has changed
        sort.toReactiveStream()
                .startWith(sortByUpdate)
                .distinctUntilChanged()
                .doOnNext { refreshing.postValue(true) }
                .autoDisposable(this)
                .subscribe()

        // only subscribe once in fragment scope
        repo.subscribeRemoteRequestState()
                .observeOn(RxSchedulers.ui)
                .doOnNext { result ->
                    when (result) {
                        is Result.Loading -> applyState()
                        is Result.Idle -> applyState()
                        is Result.Failure -> {
                            applyState(error = result.error)
                            refreshing.postValue(false)
                        }
                        is Result.Success -> {
                            refreshing.postValue(false)
                        }
                    }
                }
                .autoDisposable(this)
                .subscribe()

        fetchPagedListFromDbCompletable()
                .autoDisposable(this)
                .subscribe()
    }

    private fun fetchPagedListFromDbCompletable(): Completable {
        return repo.fetchPagedListFromDb(sort.value ?: sortByUpdate)
                .subscribeOn(RxSchedulers.io)
                .doOnNext { pagedList.postValue(it) }
                .ignoreElements()
    }

    fun refreshDataSource() {
        repo.refreshDataSource(sort.value ?: sortByUpdate)
    }

    override fun onCleared() {
        super.onCleared()

        repo.mAutoDisposeObserver.onNext(Unit)
        repo.mAutoDisposeObserver.onComplete()
    }

    private fun applyState(events: List<Repo>? = null,
                           error: Throwable? = null) {
        this.error.postValue(error)
        this.events.postValue(events)
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