package com.qingmei2.sample.ui.main.repos

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import com.qingmei2.rhine.adapter.BasePagingDataBindingAdapter
import com.qingmei2.rhine.base.viewmodel.BaseViewModel
import com.qingmei2.rhine.ext.jumpBrowser
import com.qingmei2.rhine.ext.lifecycle.bindLifecycle
import com.qingmei2.rhine.ext.livedata.toFlowable
import com.qingmei2.rhine.ext.paging.Paging
import com.qingmei2.rhine.ext.paging.toLiveData
import com.qingmei2.rhine.functional.Consumer
import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseApplication
import com.qingmei2.sample.base.SimpleViewState
import com.qingmei2.sample.databinding.ItemReposRepoBinding
import com.qingmei2.sample.entity.Repo
import com.qingmei2.sample.manager.UserManager
import io.reactivex.Flowable

@SuppressWarnings("checkResult")
class ReposViewModel(
        private val repo: ReposDataSource
) : BaseViewModel() {

    private val events: MutableLiveData<List<Repo>> = MutableLiveData()

    val adapter = BasePagingDataBindingAdapter<Repo, ItemReposRepoBinding>(
            layoutId = R.layout.item_repos_repo,
            callback = { repo, binding, _ ->
                binding.apply {
                    data = repo
                    repoEvent = object : Consumer<String> {
                        override fun accept(t: String) {
                            BaseApplication.INSTANCE.jumpBrowser(t)
                        }
                    }
                }
            }
    )

    val sort: MutableLiveData<String> = MutableLiveData()

    val loading: MutableLiveData<Boolean> = MutableLiveData()

    val error: MutableLiveData<Throwable> = MutableLiveData()

    override fun onCreate(lifecycleOwner: LifecycleOwner) {
        super.onCreate(lifecycleOwner)
        sort.toFlowable()
                .distinctUntilChanged()
                .startWith(sortByLetter)
                .bindLifecycle(this)
                .subscribe {
                    initReposList()
                }
    }

    fun initReposList() {
        Paging
                .dataSource { pageIndex ->
                    when (pageIndex) {
                        1 -> queryReposRefreshAction()
                        else -> queryReposAction(pageIndex)
                    }.flatMap { state ->
                        when (state) {
                            is SimpleViewState.Result -> Flowable.just(state.result)
                            else -> Flowable.empty()
                        }
                    }
                }.toLiveData(
                        enablePlaceholders = false,
                        pageSize = 15,
                        initialLoadSizeHint = 30
                ).toFlowable()
                .bindLifecycle(this)
                .subscribe { pagedList ->
                    adapter.submitList(pagedList)
                }
    }

    private fun queryReposAction(pageIndex: Int): Flowable<SimpleViewState<List<Repo>>> =
            repo.queryRepos(
                    UserManager.INSTANCE.login,
                    pageIndex, 15,
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

    private fun queryReposRefreshAction(): Flowable<SimpleViewState<List<Repo>>> =
            queryReposAction(1)
                    .startWith(SimpleViewState.loading())
                    .startWith(SimpleViewState.idle())
                    .doOnNext { state ->
                        when (state) {
                            is SimpleViewState.Refreshing -> applyState(isLoading = true)
                            is SimpleViewState.Idle -> applyState()
                            is SimpleViewState.Error -> applyState(error = state.error)
                            is SimpleViewState.Result -> applyState(events = state.result)
                        }
                    }

    private fun applyState(isLoading: Boolean = false,
                           events: List<Repo>? = null,
                           error: Throwable? = null) {
        this.loading.postValue(isLoading)
        this.error.postValue(error)

        this.events.postValue(events)
    }

    companion object {

        const val sortByCreated: String = "created"

        const val sortByUpdate: String = "updated"

        const val sortByLetter: String = "full_name"
    }
}