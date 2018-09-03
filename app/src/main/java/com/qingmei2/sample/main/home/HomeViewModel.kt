package com.qingmei2.sample.main.home

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import com.qingmei2.rhine.base.viewmodel.RhineViewModel
import com.qingmei2.rhine.ext.lifecycle.bindLifecycle
import com.qingmei2.rhine.ext.livedata.toFlowable
import com.qingmei2.rhine.http.entity.QueryUser
import io.reactivex.Flowable

class HomeViewModel : RhineViewModel() {

    val query: MutableLiveData<String> = MutableLiveData()
    val error: MutableLiveData<Throwable> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val result: MutableLiveData<QueryUser> = MutableLiveData()

    override fun onCreate(lifecycleOwner: LifecycleOwner) {
        super.onCreate(lifecycleOwner)
        query.toFlowable()
                .flatMap { fetchUserInfo(it) }
                .startWith(HomeViewState.idle())
                .bindLifecycle(this)
                .subscribe { state ->
                    when (state) {
                        is HomeViewState.Loading -> applyState(isLoading = true)
                        is HomeViewState.Idle -> applyState(isLoading = false)
                        is HomeViewState.Error -> applyState(isLoading = false, error = state.error)
                        is HomeViewState.Result -> applyState(isLoading = false, userInfo = state.result)
                    }
                }
    }

    private fun fetchUserInfo(username: String): Flowable<HomeViewState<QueryUser>> = serviceManager
            .userService
            .queryUser(username)
            .subscribeOn(schedulers.io())
            .map { HomeViewState.result(it) }
            .startWith(HomeViewState.loading())

    private fun applyState(isLoading: Boolean, userInfo: QueryUser? = null, error: Throwable? = null) {
        this.loading.postValue(isLoading)
        this.result.postValue(userInfo)
        this.error.postValue(error)
    }
}