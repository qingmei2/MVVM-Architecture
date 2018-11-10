package com.qingmei2.sample.common.loadings

import android.arch.lifecycle.MutableLiveData
import com.qingmei2.sample.base.BaseViewModel

@Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
class CommonLoadingViewModel private constructor() : BaseViewModel() {

    val loadingState: MutableLiveData<CommonLoadingState> = MutableLiveData()

    fun applyState(state: CommonLoadingState) {
        loadingState.postValue(state)
    }

    companion object {

        fun instance() = CommonLoadingViewModel()
    }
}

enum class CommonLoadingState {

    ERROR, EMPTY, LOADING, IDLE

}