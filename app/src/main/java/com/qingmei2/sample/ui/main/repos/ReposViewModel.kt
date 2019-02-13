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
import com.qingmei2.sample.base.SimpleViewState
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
                                                    is SimpleViewState.Result -> Flowable.just(
                                                            IntPageKeyedData.build(
                                                                    data = state.result,
                                                                    pageIndex = 1,
                                                                    hasAdjacentPageKey = state.result.size == it.requestedLoadSize
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
                                                    is SimpleViewState.Result -> Flowable.just(
                                                            IntPageKeyedData.build(
                                                                    data = state.result,
                                                                    pageIndex = param.key,
                                                                    hasAdjacentPageKey = state.result.size == param.requestedLoadSize
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

    private fun queryReposAction(pageIndex: Int, pageSize: Int): Flowable<SimpleViewState<List<Repo>>> =
            repo.queryRepos(
                    UserManager.INSTANCE.login,
                    pageIndex, pageSize,
                    sort.value ?: sortByLetter
            )
                    .map { either ->
                        either.fold({
                            SimpleViewState.error<List<Repo>>(it)
                        }, {
                            SimpleViewState.result(it)
                        })
                    }
                    .onErrorReturn { it -> SimpleViewState.error(it) }

    private fun queryReposRefreshAction(pageSize: Int): Flowable<SimpleViewState<List<Repo>>> =
            queryReposAction(1, pageSize)
                    .startWith(SimpleViewState.loading())
                    .startWith(SimpleViewState.idle())
                    .doOnNext { state ->
                        when (state) {
                            is SimpleViewState.Refreshing -> applyState()
                            is SimpleViewState.Idle -> applyState()
                            is SimpleViewState.Error -> applyState(error = state.error)
                            is SimpleViewState.Result -> applyState(events = state.result)
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