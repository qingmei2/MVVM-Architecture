package com.qingmei2.sample.common.loadings

import android.arch.lifecycle.MutableLiveData

interface ILoadingDelegate {

    fun loadingState(): MutableLiveData<CommonLoadingState>

    fun applyState(state: CommonLoadingState)
}

enum class CommonLoadingState {

    ERROR, EMPTY, LOADING, IDLE
    
}