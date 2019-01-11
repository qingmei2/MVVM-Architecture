package com.qingmei2.sample.ui.main.home

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import arrow.core.Option
import arrow.core.none
import arrow.core.some
import com.qingmei2.rhine.base.viewmodel.BaseViewModel
import com.qingmei2.rhine.ext.livedata.toReactiveStream
import com.qingmei2.rhine.ext.paging.Paging
import com.qingmei2.rhine.ext.paging.toLiveData
import com.qingmei2.sample.base.SimpleViewState
import com.qingmei2.sample.common.loadings.CommonLoadingState
import com.qingmei2.sample.entity.ReceivedEvent
import com.qingmei2.sample.manager.UserManager
import com.uber.autodispose.autoDisposable
import io.reactivex.Flowable

@SuppressWarnings("checkResult")
class HomeViewModel(
        private val repo: HomeRepository
) : BaseViewModel() {

    private val events: MutableLiveData<List<ReceivedEvent>> = MutableLiveData()

    val pagedList = MutableLiveData<PagedList<ReceivedEvent>>()

    val refreshing: MutableLiveData<Boolean> = MutableLiveData()

    val loadingLayout: MutableLiveData<CommonLoadingState> = MutableLiveData()

    val error: MutableLiveData<Option<Throwable>> = MutableLiveData()

    init {
        refreshing.toReactiveStream()
                .filter { it }
                .startWith(true)
                .doOnNext { initReceivedEvents() }
                .autoDisposable(this)
                .subscribe()
    }

    private fun initReceivedEvents() {
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
                .toReactiveStream()
                .doOnNext { pagedList.postValue(it) }
                .autoDisposable(this)
                .subscribe()
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
                            is SimpleViewState.Refreshing -> applyState()
                            is SimpleViewState.Idle -> applyState()
                            is SimpleViewState.Error -> applyState(
                                    loadingLayout = CommonLoadingState.ERROR,
                                    error = state.error.some()
                            )
                            is SimpleViewState.Result -> applyState(
                                    events = state.result.some()
                            )
                        }
                    }
                    .doFinally { refreshing.postValue(false) }

    private fun applyState(loadingLayout: CommonLoadingState = CommonLoadingState.IDLE,
                           events: Option<List<ReceivedEvent>> = none(),
                           error: Option<Throwable> = none()) {
        this.loadingLayout.postValue(loadingLayout)
        this.error.postValue(error)

        this.events.postValue(events.orNull())
    }

    companion object {

        fun instance(fragment: Fragment, repo: HomeRepository): HomeViewModel =
                ViewModelProviders
                        .of(fragment, HomeViewModelFactory(repo))
                        .get(HomeViewModel::class.java)
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