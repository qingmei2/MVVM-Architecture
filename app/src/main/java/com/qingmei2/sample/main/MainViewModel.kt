package com.qingmei2.sample.main

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import com.qingmei2.rhine.base.viewmodel.RhineViewModel
import com.qingmei2.rhine.ext.lifecycle.bindLifecycle
import com.qingmei2.rhine.ext.livedata.toFlowable
import com.qingmei2.rhine.http.entity.UserInfo
import io.reactivex.Flowable

class MainViewModel : RhineViewModel() {

    val query: MutableLiveData<String> = MutableLiveData()
    val error: MutableLiveData<Throwable> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val result: MutableLiveData<UserInfo> = MutableLiveData()

    override fun onCreate(lifecycleOwner: LifecycleOwner) {
        super.onCreate(lifecycleOwner)
        query.toFlowable()
                .flatMap { fetchUserInfo(it) }
                .startWith(MainViewState.idle())
                .bindLifecycle(this)
                .subscribe { state ->
                    when (state) {
                        is MainViewState.Loading -> applyState(isLoading = true)
                        is MainViewState.Idle -> applyState(isLoading = false)
                        is MainViewState.Error -> applyState(isLoading = false, error = state.error)
                        is MainViewState.Result -> applyState(isLoading = false, userInfo = state.result)
                    }
                }
    }

    private fun fetchUserInfo(username: String): Flowable<MainViewState<UserInfo>> = serviceManager
            .userService
            .fetchUserInfo(username)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .map { MainViewState.result(it) }
            .startWith(MainViewState.loading())

    private fun applyState(isLoading: Boolean, userInfo: UserInfo? = null, error: Throwable? = null) {
        this.loading.postValue(isLoading)
        this.result.postValue(userInfo)
        this.error.postValue(error)
    }
}