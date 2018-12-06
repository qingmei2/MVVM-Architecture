package com.qingmei2.sample.ui.main.home

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.qingmei2.rhine.base.viewdelegate.BaseViewDelegate
import com.qingmei2.rhine.ext.autodispose.bindLifecycle
import com.qingmei2.rhine.ext.livedata.toFlowable
import com.qingmei2.sample.common.FabAnimateViewModel
import com.qingmei2.sample.common.loadings.CommonLoadingState
import com.qingmei2.sample.common.loadings.ILoadingDelegate
import io.reactivex.Completable

@SuppressLint("CheckResult")
class HomeViewDelegate(
        val homeViewModel: HomeViewModel,
        val fabViewModel: FabAnimateViewModel,
        val fabTop: FloatingActionButton,
        private val loadingDelegate: ILoadingDelegate
) : BaseViewDelegate(), ILoadingDelegate by loadingDelegate {

    override fun onCreate(lifecycleOwner: LifecycleOwner) {
        super.onCreate(lifecycleOwner)

        Completable
                .mergeArray(
                        fabViewModel.visibleState
                                .toFlowable()
                                .doOnNext { switchFabState(it) }
                                .ignoreElements(),
                        homeViewModel.loadingLayout
                                .toFlowable()
                                .filter { it ->
                                    it != CommonLoadingState.LOADING    // Refreshing state not used
                                }
                                .doOnNext { applyState(it) }
                                .ignoreElements()
                )
                .bindLifecycle(lifecycleOwner)
                .subscribe()
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