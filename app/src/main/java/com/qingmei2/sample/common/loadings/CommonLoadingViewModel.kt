package com.qingmei2.sample.common.loadings

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import com.qingmei2.rhine.ext.viewmodel.addLifecycle
import com.qingmei2.rhine.base.viewmodel.BaseViewModel

@Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
class CommonLoadingViewModel private constructor() : BaseViewModel(), ILoadingDelegate {

    private val state: MutableLiveData<CommonLoadingState> = MutableLiveData()

    override fun loadingState(): MutableLiveData<CommonLoadingState> = state

    override fun applyState(state: CommonLoadingState) {
        this.state.postValue(state)
    }

    companion object {

        fun instance(lifecycleOwner: LifecycleOwner) =
                CommonLoadingViewModel().apply {
                    addLifecycle(lifecycleOwner)
                }
    }
}