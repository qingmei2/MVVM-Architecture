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
import com.qingmei2.rhine.util.RxSchedulers
import com.qingmei2.rhine.util.SingletonHolderSingleArg
import com.qingmei2.sample.base.Result
import com.qingmei2.sample.common.loadings.CommonLoadingState
import com.qingmei2.sample.entity.ReceivedEvent
import com.qingmei2.sample.manager.UserManager
import com.uber.autodispose.autoDisposable
import io.reactivex.Completable

@SuppressWarnings("checkResult")
class HomeViewModel(
        private val repo: HomeRepository
) : BaseViewModel() {

    val pagedList = MutableLiveData<PagedList<ReceivedEvent>>()

    val refreshing: MutableLiveData<Boolean> = MutableLiveData()

    val loadingLayout: MutableLiveData<CommonLoadingState> = MutableLiveData()

    val error: MutableLiveData<Option<Throwable>> = MutableLiveData()

    init {
        refreshing.toReactiveStream()
                .distinctUntilChanged()
                .filter { it }
                .flatMapCompletable { queryEventByPage(1) }
                .autoDisposable(this)
                .subscribe()

        initReceivedEvents()
    }

    private fun initReceivedEvents() {
        // fetch single actual data source from db and render them on screen.
        this.refreshing.postValue(true)

        this.repo.fetchPagedListFromDb()
                .subscribeOn(RxSchedulers.database)
                .doOnNext { pagedList.postValue(it) }
                .autoDisposable(this)
                .subscribe()

        this.queryEventByPage(1).autoDisposable(this).subscribe()
    }

    private fun queryEventByPage(pageIndex: Int): Completable {
        val username = UserManager.INSTANCE.login
        return repo.queryReceivedEventsFromRemote(username, pageIndex)
                .observeOn(RxSchedulers.ui)
                .doOnNext { result ->
                    when (result) {
                        is Result.Loading -> applyState()
                        is Result.Idle -> applyState()
                        is Result.Failure -> applyState(
                                loadingLayout = CommonLoadingState.ERROR,
                                error = result.error.some()
                        )
                    }
                }
                .doFinally { refreshing.postValue(false) }
                .ignoreElements()
    }

    private fun applyState(loadingLayout: CommonLoadingState = CommonLoadingState.IDLE,
                           error: Option<Throwable> = none()) {
        this.loadingLayout.postValue(loadingLayout)
        this.error.postValue(error)
    }

    companion object {

        fun instance(fragment: Fragment, repo: HomeRepository): HomeViewModel =
                ViewModelProviders
                        .of(fragment, HomeViewModelFactory.getInstance(repo))
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

    companion object : SingletonHolderSingleArg<HomeViewModelFactory, HomeRepository>(::HomeViewModelFactory)
}