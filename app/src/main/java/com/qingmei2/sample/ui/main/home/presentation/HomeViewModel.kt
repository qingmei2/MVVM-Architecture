package com.qingmei2.sample.ui.main.home.presentation

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import com.qingmei2.rhine.base.viewstate.SimpleViewState
import com.qingmei2.rhine.ext.lifecycle.bindLifecycle
import com.qingmei2.rhine.ext.livedata.toFlowable
import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseViewModel
import com.qingmei2.sample.data.UserManager
import com.qingmei2.sample.databinding.ItemHomeReceivedEventBinding
import com.qingmei2.sample.http.RxSchedulers
import com.qingmei2.sample.http.entity.ReceivedEvent
import com.qingmei2.sample.ui.main.home.data.HomeRepository
import indi.yume.tools.adapterdatabinding.dataBindingItem
import indi.yume.tools.dsladapter.RendererAdapter
import indi.yume.tools.dsladapter.renderers.LayoutRenderer

@SuppressWarnings("checkResult")
class HomeViewModel(
        private val repo: HomeRepository
) : BaseViewModel() {

    private val events: MutableLiveData<List<ReceivedEvent>> = MutableLiveData()
    val adapter: MutableLiveData<RendererAdapter> = MutableLiveData()

    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val error: MutableLiveData<Throwable> = MutableLiveData()

    init {
        events.toFlowable()
                .map { list ->
                    RendererAdapter.repositoryAdapter()
                            .addItem(list, LayoutRenderer.dataBindingItem<List<ReceivedEvent>, ItemHomeReceivedEventBinding>(
                                    count = list.size,
                                    layout = R.layout.item_home_received_event,
                                    bindBinding = { ItemHomeReceivedEventBinding.bind(it) },
                                    binder = { bind, item, index ->
                                        bind.data = item[index]
                                    },
                                    recycleFun = { it.data = null }
                            ))
                            .build()
                }
                .observeOn(RxSchedulers.ui)
                .subscribe { new ->
                    adapter.postValue(new)
                }
    }

    override fun onCreate(lifecycleOwner: LifecycleOwner) {
        super.onCreate(lifecycleOwner)
        repo.queryReceivedEvents(UserManager.INSTANCE.name)
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

    private fun applyState(isLoading: Boolean, events: List<ReceivedEvent>? = null, error: Throwable? = null) {
        this.loading.postValue(isLoading)
        this.error.postValue(error)

        this.events.postValue(events)
    }
}