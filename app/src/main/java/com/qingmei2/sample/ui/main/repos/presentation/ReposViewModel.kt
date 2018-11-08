package com.qingmei2.sample.ui.main.repos.presentation

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import arrow.core.toOption
import com.qingmei2.sample.base.SimpleViewState
import com.qingmei2.rhine.ext.jumpBrowser
import com.qingmei2.rhine.ext.lifecycle.bindLifecycle
import com.qingmei2.rhine.ext.livedata.toFlowable
import com.qingmei2.rhine.functional.Consumer
import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseApplication
import com.qingmei2.sample.base.BaseViewModel
import com.qingmei2.sample.databinding.ItemReposRepoBinding
import com.qingmei2.sample.entity.Repo
import com.qingmei2.sample.http.RxSchedulers
import com.qingmei2.sample.manager.UserManager
import com.qingmei2.sample.ui.main.repos.data.ReposDataSource
import com.qingmei2.sample.utils.TimeConverter
import indi.yume.tools.adapterdatabinding.dataBindingItem
import indi.yume.tools.dsladapter.RendererAdapter
import indi.yume.tools.dsladapter.renderers.LayoutRenderer
import io.reactivex.Observable
import java.text.Collator

@SuppressWarnings("checkResult")
class ReposViewModel(
        private val repo: ReposDataSource
) : BaseViewModel() {

    private val events: MutableLiveData<List<Repo>> = MutableLiveData()
    val adapter: MutableLiveData<RendererAdapter> = MutableLiveData()

    val sortFunc: MutableLiveData<RepoTransformer> = MutableLiveData()

    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val error: MutableLiveData<Throwable> = MutableLiveData()

    override fun onCreate(lifecycleOwner: LifecycleOwner) {
        super.onCreate(lifecycleOwner)
        sortFunc.toFlowable()
                .distinctUntilChanged()
                .bindLifecycle(this)
                .subscribe {
                    events.value?.also { repos ->
                        events.value = sortUserRepos(repos)
                    }
                }
        events.toFlowable()
                .observeOn(RxSchedulers.ui)
                .bindLifecycle(this)
                .subscribe { _ ->
                    adapter.value.toOption()
                            .fold({
                                adapter.postValue(
                                        RendererAdapter.repositoryAdapter()
                                                .add({
                                                    events.value ?: listOf()
                                                }, LayoutRenderer.dataBindingItem<List<Repo>, ItemReposRepoBinding>(
                                                        count = events.value?.size ?: 0,
                                                        layout = R.layout.item_repos_repo,
                                                        bindBinding = { ItemReposRepoBinding.bind(it) },
                                                        binder = { bind, item, index ->
                                                            bind.data = item[index]
                                                            bind.avatarEvent = object : Consumer<String> {
                                                                override fun accept(t: String) {
                                                                    BaseApplication.INSTANCE.jumpBrowser(t)
                                                                }
                                                            }
                                                            bind.repoEvent = object : Consumer<String> {
                                                                override fun accept(t: String) {
                                                                    BaseApplication.INSTANCE.jumpBrowser(t)
                                                                }
                                                            }
                                                        },
                                                        recycleFun = { it.data = null }
                                                ))
                                                .build()
                                )
                            }, {
                                it.forceUpdateAdapter()
                            })
                }
        queryUserRepos()
    }

    fun queryUserRepos() {
        repo.queryRepos(UserManager.INSTANCE.name)
                .map { either ->
                    either.fold({
                        SimpleViewState.error<List<Repo>>(it)
                    }, {
                        SimpleViewState.result(sortUserRepos(it))
                    })
                }
                .startWith(SimpleViewState.loading())
                .startWith(SimpleViewState.idle())
                .onErrorReturn { it -> SimpleViewState.error(it) }
                .bindLifecycle(this)
                .subscribe {
                    when (it) {
                        is SimpleViewState.Loading -> applyState(isLoading = true)
                        is SimpleViewState.Idle -> applyState(isLoading = false)
                        is SimpleViewState.Error -> applyState(isLoading = false, error = it.error)
                        is SimpleViewState.Result -> applyState(isLoading = false, events = it.result)
                    }
                }
    }

    private fun sortUserRepos(repos: List<Repo>): List<Repo> =
            Observable.fromIterable(repos)
                    .sorted(sortFunc.value ?: sortByStars)  // default sort by stars
                    .toList()
                    .blockingGet()

    private fun applyState(isLoading: Boolean,
                           events: List<Repo>? = null,
                           error: Throwable? = null) {
        this.loading.postValue(isLoading)
        this.error.postValue(error)

        this.events.postValue(events)
    }

    companion object {

        val sortByStars: RepoTransformer = { o1, o2 ->
            o2.stargazersCount - o1.stargazersCount
        }

        val sortByUpdate: RepoTransformer = { o1, o2 ->
            val stamp1 = TimeConverter.transTimeStamp(o1.updatedAt)
            val stamp2 = TimeConverter.transTimeStamp(o2.updatedAt)
            (stamp1 - stamp2).toInt()
        }

        val sortByLetter: RepoTransformer = { o1, o2 ->
            val value1 = Collator.getInstance().getCollationKey(o1.name)
            val value2 = Collator.getInstance().getCollationKey(o2.name)
            value1.compareTo(value2)
        }
    }
}

typealias RepoTransformer = (Repo, Repo) -> Int