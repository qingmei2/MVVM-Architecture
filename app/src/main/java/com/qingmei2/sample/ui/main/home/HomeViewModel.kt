package com.qingmei2.sample.ui.main.home

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import arrow.core.Option
import arrow.core.none
import arrow.core.some
import com.qingmei2.rhine.ext.lifecycle.bindLifecycle
import com.qingmei2.rhine.ext.livedata.toFlowable
import com.qingmei2.sample.base.BaseViewModel
import com.qingmei2.sample.base.SimpleViewState
import com.qingmei2.sample.common.loadings.CommonLoadingState
import com.qingmei2.sample.common.loadmore.CommonLoadMoreDataSource
import com.qingmei2.sample.entity.ReceivedEvent
import com.qingmei2.sample.manager.UserManager
import io.reactivex.Flowable

@SuppressWarnings("checkResult")
class HomeViewModel(
        private val repo: HomeRepository
) : BaseViewModel() {

    private val events: MutableLiveData<List<ReceivedEvent>> = MutableLiveData()

    val adapter = HomeListAdapter()

    val refreshing: MutableLiveData<Boolean> = MutableLiveData()

    val loadingLayout: MutableLiveData<CommonLoadingState> = MutableLiveData()

    val error: MutableLiveData<Option<Throwable>> = MutableLiveData()

    override fun onCreate(lifecycleOwner: LifecycleOwner) {
        super.onCreate(lifecycleOwner)
        initReceivedEvents()   // fetch API when fragment's onCreated()
    }

    fun initReceivedEvents() {
        LivePagedListBuilder<Int, ReceivedEvent>(
                CommonLoadMoreDataSource.factory(
                        dataSourceProvider = { pageIndex ->
                            when (pageIndex) {
                                1 -> queryReceivedEventsRefreshAction()
                                else -> queryReceivedEventsAction(pageIndex)
                            }.flatMap { state ->
                                when (state) {
                                    is SimpleViewState.Refreshing -> Flowable.empty()
                                    is SimpleViewState.Idle -> Flowable.empty()
                                    is SimpleViewState.Error -> Flowable.empty()
                                    is SimpleViewState.Result -> Flowable.just(state.result)
                                }
                            }
                        }),
                PagedList.Config
                        .Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(30)
                        .setPageSize(15)
                        .build())
                .build()
                .toFlowable()
                .bindLifecycle(this)
                .subscribe {
                    adapter.submitList(it)
                }
    }

    private fun queryReceivedEventsAction(pageIndex: Int): Flowable<SimpleViewState<List<ReceivedEvent>>> =
            repo.queryReceivedEvents(UserManager.INSTANCE.name, pageIndex = pageIndex, perPage = 15)
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
}