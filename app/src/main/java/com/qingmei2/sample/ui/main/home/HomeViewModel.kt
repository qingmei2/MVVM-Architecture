package com.qingmei2.sample.ui.main.home

import androidx.lifecycle.*
import androidx.fragment.app.FragmentActivity
import arrow.core.Option
import arrow.core.none
import arrow.core.some
import com.qingmei2.rhine.adapter.BasePagingDataBindingAdapter
import com.qingmei2.rhine.base.viewmodel.BaseViewModel
import com.qingmei2.rhine.ext.jumpBrowser
import com.qingmei2.rhine.ext.lifecycle.bindLifecycle
import com.qingmei2.rhine.ext.livedata.toFlowable
import com.qingmei2.rhine.ext.paging.Paging
import com.qingmei2.rhine.ext.paging.toLiveData
import com.qingmei2.rhine.ext.viewmodel.addLifecycle
import com.qingmei2.rhine.functional.Consumer
import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseApplication
import com.qingmei2.sample.base.SimpleViewState
import com.qingmei2.sample.common.loadings.CommonLoadingState
import com.qingmei2.sample.databinding.ItemHomeReceivedEventBinding
import com.qingmei2.sample.entity.ReceivedEvent
import com.qingmei2.sample.manager.UserManager
import io.reactivex.Flowable

@SuppressWarnings("checkResult")
class HomeViewModel(
        private val repo: HomeRepository
) : BaseViewModel() {

    private val events: MutableLiveData<List<ReceivedEvent>> = MutableLiveData()

    val adapter = BasePagingDataBindingAdapter<ReceivedEvent, ItemHomeReceivedEventBinding>(
            layoutId = R.layout.item_home_received_event,
            callback = { data, binding, _ ->
                binding.data = data
                binding.actorEvent = object : Consumer<String> {
                    override fun accept(t: String) {
                        BaseApplication.INSTANCE.jumpBrowser(t)
                    }
                }
                binding.repoEvent = object : Consumer<String> {
                    override fun accept(t: String) {
                        BaseApplication.INSTANCE.jumpBrowser(t)
                    }
                }
            }
    )

    val refreshing: MutableLiveData<Boolean> = MutableLiveData()

    val loadingLayout: MutableLiveData<CommonLoadingState> = MutableLiveData()

    val error: MutableLiveData<Option<Throwable>> = MutableLiveData()

    override fun onCreate(lifecycleOwner: LifecycleOwner) {
        super.onCreate(lifecycleOwner)
        initReceivedEvents()   // fetch API when fragment's onCreated()
    }

    fun initReceivedEvents() {
        Paging
                .dataSource { pageIndex ->
                    when (pageIndex) {
                        1 -> queryReceivedEventsRefreshAction()
                        else -> queryReceivedEventsAction(pageIndex)
                    }.flatMap { state ->
                        when (state) {
                            is SimpleViewState.Result -> Flowable.just(state.result)
                            else -> Flowable.empty()
                        }
                    }
                }
                .toLiveData()
                .toFlowable()
                .bindLifecycle(this)
                .subscribe {
                    adapter.submitList(it)
                }
    }

    private fun queryReceivedEventsAction(pageIndex: Int): Flowable<SimpleViewState<List<ReceivedEvent>>> =
            repo.queryReceivedEvents(UserManager.INSTANCE.login, pageIndex = pageIndex, perPage = 15)
                    .map { either ->
                        either.fold({
                            SimpleViewState.error<List<ReceivedEvent>>(it)
                        }, {
                            SimpleViewState.result(it)
                        })
                    }
                    .onErrorReturn { it -> SimpleViewState.error(it) }

    private fun queryReceivedEventsRefreshAction(): Flowable<SimpleViewState<List<ReceivedEvent>>> =
            queryReceivedEventsAction(1)
                    .startWith(SimpleViewState.loading())
                    .startWith(SimpleViewState.idle())
                    .doOnNext { state ->
                        when (state) {
                            is SimpleViewState.Refreshing -> applyState(refreshing = true)
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

    private fun applyState(loadingLayout: CommonLoadingState = CommonLoadingState.IDLE,
                           refreshing: Boolean = false,
                           events: Option<List<ReceivedEvent>> = none(),
                           error: Option<Throwable> = none()) {
        this.loadingLayout.postValue(loadingLayout)
        this.refreshing.postValue(refreshing)
        this.error.postValue(error)

        this.events.postValue(events.orNull())
    }

    companion object {

        fun instance(activity: FragmentActivity, repo: HomeRepository): HomeViewModel =
                ViewModelProviders
                        .of(activity, HomeViewModelFactory(repo))
                        .get(HomeViewModel::class.java).apply {
                            addLifecycle(activity)
                        }
    }
}

class HomeViewModelFactory(
        private val repo: HomeRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repo) as T
    }
}