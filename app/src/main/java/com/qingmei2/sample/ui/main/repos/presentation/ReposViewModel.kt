package com.qingmei2.sample.ui.main.repos.presentation

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import com.qingmei2.rhine.base.viewstate.SimpleViewState
import com.qingmei2.rhine.ext.lifecycle.bindLifecycle
import com.qingmei2.rhine.ext.livedata.toFlowable
import com.qingmei2.rhine.functional.Consumer
import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseViewModel
import com.qingmei2.sample.data.UserManager
import com.qingmei2.sample.databinding.ItemReposRepoBinding
import com.qingmei2.sample.http.RxSchedulers
import com.qingmei2.sample.http.entity.Repo
import com.qingmei2.sample.ui.main.repos.data.ReposDataSource
import com.qingmei2.sample.utils.toast
import indi.yume.tools.adapterdatabinding.dataBindingItem
import indi.yume.tools.dsladapter.RendererAdapter
import indi.yume.tools.dsladapter.renderers.LayoutRenderer

@SuppressWarnings("checkResult")
class ReposViewModel(
        private val repo: ReposDataSource
) : BaseViewModel() {

    private val events: MutableLiveData<List<Repo>> = MutableLiveData()
    val adapter: MutableLiveData<RendererAdapter> = MutableLiveData()

    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val error: MutableLiveData<Throwable> = MutableLiveData()

    init {
        events.toFlowable()
                .observeOn(RxSchedulers.ui)
                .subscribe {
                    if (adapter.value == null) {
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
                                                            toast {
                                                                "avatar: $t"
                                                            }
                                                        }
                                                    }
                                                    bind.repoEvent = object : Consumer<String> {
                                                        override fun accept(t: String) {
                                                            toast {
                                                                "repo: $t"
                                                            }
                                                        }
                                                    }
                                                },
                                                recycleFun = { it.data = null }
                                        ))
                                        .build()
                        )
                    } else {
                        adapter.value!!.apply {
                            forceUpdateAdapter()
                        }
                    }
                }
    }

    override fun onCreate(lifecycleOwner: LifecycleOwner) {
        super.onCreate(lifecycleOwner)
        queryUserRepos()
    }

    fun queryUserRepos() {
        repo.queryRepos(UserManager.INSTANCE.name)
                .map { SimpleViewState.result(it) }
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

    private fun applyState(isLoading: Boolean, events: List<Repo>? = null, error: Throwable? = null) {
        this.loading.postValue(isLoading)
        this.error.postValue(error)

        this.events.postValue(events)
    }


}