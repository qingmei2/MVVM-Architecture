package com.qingmei2.sample.ui.main.repos

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.qingmei2.rhine.base.viewmodel.BaseViewModel
import com.qingmei2.rhine.ext.livedata.toReactiveStream
import com.qingmei2.rhine.ext.paging.IntPageKeyedData
import com.qingmei2.rhine.ext.paging.IntPageKeyedDataSource
import com.qingmei2.rhine.ext.paging.Paging
import com.qingmei2.rhine.util.SingletonHolderSingleArg
import com.qingmei2.sample.base.Result
import com.qingmei2.sample.entity.Repo
import com.qingmei2.sample.manager.UserManager
import com.uber.autodispose.autoDisposable
import io.reactivex.Completable
import io.reactivex.Flowable

@SuppressWarnings("checkResult")
class ReposViewModel(
        private val repo: ReposDataSource
) : BaseViewModel() {

    private val events: MutableLiveData<List<Repo>> = MutableLiveData()

    val sort: MutableLiveData<String> = MutableLiveData()

    val refreshing: MutableLiveData<Boolean> = MutableLiveData()

    val error: MutableLiveData<Throwable> = MutableLiveData()

    val pagedList = MutableLiveData<PagedList<Repo>>()

    init {
        Completable
                .mergeArray(
                        refreshing.toReactiveStream()
                                .filter { it }
                                .doOnNext { initReposList() }
                                .ignoreElements(),
                        sort.toReactiveStream()
                                .distinctUntilChanged()
                                .startWith(sortByLetter)
                                .doOnNext { refreshing.postValue(true) }
                                .ignoreElements()
                )
                .autoDisposable(this)
                .subscribe()
    }

    private fun initReposList() {
        Paging
                .buildReactiveStream(
                        intPageKeyedDataSource = IntPageKeyedDataSource(
                                loadInitial = {
                                    queryReposRefreshAction(it.requestedLoadSize)
                                            .flatMap { state ->
                                                when (state) {
                                                    is Result.Success -> Flowable.just(
                                                            IntPageKeyedData.build(
                                                                    data = state.data,
                                                                    pageIndex = 1,
                                                                    hasAdjacentPageKey = state.data.size == it.requestedLoadSize
                                                            )
                                                    )
                                                    else -> Flowable.empty()
                                                }
                                            }
                                },
                                loadAfter = { param ->
                                    queryReposAction(param.key, param.requestedLoadSize)
                                            .flatMap { state ->
                                                when (state) {
                                                    is Result.Success -> Flowable.just(
                                                            IntPageKeyedData.build(
                                                                    data = state.data,
                                                                    pageIndex = param.key,
                                                                    hasAdjacentPageKey = state.data.size == param.requestedLoadSize
                                                            )
                                                    )
                                                    else -> Flowable.empty()
                                                }
                                            }
                                }
                        )
                )
                .doOnNext { pagedList.postValue(it) }
                .autoDisposable(this)
                .subscribe()
    }

    private fun queryReposAction(pageIndex: Int, pageSize: Int): Flowable<Result<List<Repo>>> =
            repo.queryRepos(
                    UserManager.INSTANCE.login,
                    pageIndex, pageSize,
                    sort.value ?: sortByLetter
            )
                    .map { either ->
                        either.fold({
                            Result.failure<List<Repo>>(it)
                        }, {
                            Result.success(it)
                        })
                    }
                    .onErrorReturn { it -> Result.failure(it) }

    private fun queryReposRefreshAction(pageSize: Int): Flowable<Result<List<Repo>>> =
            queryReposAction(1, pageSize)
                    .startWith(Result.loading())
                    .startWith(Result.idle())
                    .doOnNext { state ->
                        when (state) {
                            is Result.Loading -> applyState()
                            is Result.Idle -> applyState()
                            is Result.Failure -> applyState(error = state.error)
                            is Result.Success-> applyState(events = state.data)
                        }
                    }
                    .doFinally { refreshing.postValue(false) }

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
                     repo: ReposDataSource): ReposViewModel =
                ViewModelProviders
                        .of(fragment, ReposViewModelFactory.getInstance(repo))
                        .get(ReposViewModel::class.java)
    }
}

class ReposViewModelFactory(
        private val repo: ReposDataSource
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ReposViewModel(repo) as T
    }

    companion object : SingletonHolderSingleArg<ReposViewModelFactory, ReposDataSource>(::ReposViewModelFactory)
}