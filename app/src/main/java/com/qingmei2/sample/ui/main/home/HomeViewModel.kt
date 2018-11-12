package com.qingmei2.sample.ui.main.home

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import arrow.core.Option
import arrow.core.none
import arrow.core.some
import arrow.core.toOption
import com.qingmei2.rhine.ext.jumpBrowser
import com.qingmei2.rhine.ext.autodispose.bindLifecycle
import com.qingmei2.rhine.ext.livedata.toFlowable
import com.qingmei2.rhine.functional.Consumer
import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseApplication
import com.qingmei2.sample.base.BaseViewModel
import com.qingmei2.sample.base.SimpleViewState
import com.qingmei2.sample.common.loadings.CommonLoadingState
import com.qingmei2.sample.databinding.ItemHomeReceivedEventBinding
import com.qingmei2.sample.entity.ReceivedEvent
import com.qingmei2.sample.http.RxSchedulers
import com.qingmei2.sample.manager.UserManager
import indi.yume.tools.adapterdatabinding.dataBindingItem
import indi.yume.tools.dsladapter.RendererAdapter
import indi.yume.tools.dsladapter.renderers.LayoutRenderer

@SuppressWarnings("checkResult")
class HomeViewModel(
        private val repo: HomeRepository
) : BaseViewModel() {

    private val events: MutableLiveData<List<ReceivedEvent>> = MutableLiveData()

    val adapter: MutableLiveData<RendererAdapter> = MutableLiveData()

    val refreshing: MutableLiveData<Boolean> = MutableLiveData()

    val loadingLayout: MutableLiveData<CommonLoadingState> = MutableLiveData()

    val error: MutableLiveData<Option<Throwable>> = MutableLiveData()

    init {
        events.toFlowable()
                .observeOn(RxSchedulers.ui)
                .subscribe { _ ->
                    adapter.value.toOption()
                            .fold({
                                adapter.postValue(
                                        RendererAdapter
                                                .repositoryAdapter()
                                                .add({
                                                    events.value ?: listOf()
                                                }, LayoutRenderer.dataBindingItem<List<ReceivedEvent>, ItemHomeReceivedEventBinding>(
                                                        count = events.value?.size ?: 0,
                                                        layout = R.layout.item_home_received_event,
                                                        bindBinding = { ItemHomeReceivedEventBinding.bind(it) },
                                                        binder = { bind, item, index ->
                                                            bind.data = item[index]
                                                            bind.actorEvent = object : Consumer<String> {
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
    }

    override fun onCreate(lifecycleOwner: LifecycleOwner) {
        super.onCreate(lifecycleOwner)
        queryReceivedEvents()   // fetch API when fragment's onCreated()
    }

    fun queryReceivedEvents() {
        repo.queryReceivedEvents(UserManager.INSTANCE.name)
                .map { either ->
                    either.fold({
                        SimpleViewState.error<List<ReceivedEvent>>(it)
                    }, {
                        SimpleViewState.result(it)
                    })
                }
                .startWith(SimpleViewState.loading())
                .startWith(SimpleViewState.idle())
                .onErrorReturn { it -> SimpleViewState.error(it) }
                .bindLifecycle(this)
                .subscribe { state ->
                    when (state) {
                        is SimpleViewState.Loading -> applyState(refreshing = true)
                        is SimpleViewState.Idle -> applyState(refreshing = false)
                        is SimpleViewState.Error -> applyState(
                                loadingLayout = CommonLoadingState.ERROR,
                                error = state.error.some()
                        )
                        is SimpleViewState.Result -> applyState(
                                events = state.result.some()
                        )
                    }
                }
    }

    private fun applyState(loadingLayout: CommonLoadingState = CommonLoadingState.IDLE,
                           refreshing: Boolean = false,
                           events: Option<List<ReceivedEvent>> = none(),
                           error: Option<Throwable> = none()) {
        this.loadingLayout.postValue(loadingLayout)
        this.refreshing.postValue(refreshing)
        this.error.postValue(error)

        this.events.postValue(events.orNull())
    }
}