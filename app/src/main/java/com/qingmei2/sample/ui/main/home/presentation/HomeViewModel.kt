package com.qingmei2.sample.ui.main.home.presentation

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import com.qingmei2.sample.base.BaseViewModel
import com.qingmei2.sample.http.entity.QueryUser
import com.qingmei2.sample.ui.main.home.data.HomeRepository

class HomeViewModel(
        private val repo: HomeRepository
) : BaseViewModel() {

    val query: MutableLiveData<String> = MutableLiveData()
    val error: MutableLiveData<Throwable> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val result: MutableLiveData<QueryUser> = MutableLiveData()

    override fun onCreate(lifecycleOwner: LifecycleOwner) {
        super.onCreate(lifecycleOwner)
    }

    private fun applyState(isLoading: Boolean, userInfo: QueryUser? = null, error: Throwable? = null) {
        this.loading.postValue(isLoading)
        this.result.postValue(userInfo)
        this.error.postValue(error)
    }
}