package com.qingmei2.sample.ui.main.home.presentation

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.support.design.widget.FloatingActionButton
import com.qingmei2.rhine.ext.lifecycle.bindLifecycle
import com.qingmei2.rhine.ext.livedata.toFlowable
import com.qingmei2.sample.base.viewdelegates.BaseViewDelegate
import com.qingmei2.sample.common.FabAnimateViewModel

@SuppressLint("CheckResult")
class HomeViewDelegate(
        val homeViewModel: HomeViewModel,
        val fabViewModel: FabAnimateViewModel,
        val fabTop: FloatingActionButton
) : BaseViewDelegate() {

    init {
        fabViewModel.visibleState
                .toFlowable()
                .bindLifecycle(fabViewModel)
                .subscribe {
                    switchFabState(it)
                }
    }

    private fun switchFabState(show: Boolean) =
            when (show) {
                false -> ObjectAnimator.ofFloat(fabTop, "translationX", 250f, 0f)
                true -> ObjectAnimator.ofFloat(fabTop, "translationX", 0f, 250f)
            }.apply {
                duration = 300
                start()
            }
}