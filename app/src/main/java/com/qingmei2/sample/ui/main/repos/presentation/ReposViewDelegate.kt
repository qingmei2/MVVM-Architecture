package com.qingmei2.sample.ui.main.repos.presentation

import android.animation.ObjectAnimator
import android.support.design.widget.FloatingActionButton
import android.view.MenuItem
import com.qingmei2.rhine.base.viewdelegate.IViewDelegate
import com.qingmei2.rhine.ext.lifecycle.bindLifecycle
import com.qingmei2.rhine.ext.livedata.toFlowable
import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseViewDelegate
import com.qingmei2.sample.common.FabAnimateViewModel

class ReposViewDelegate(
        val viewModel: ReposViewModel,
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

    fun onMenuSelected(menuItem: MenuItem) {
        viewModel.sortFunc.value = when (menuItem.itemId) {
            R.id.menu_repos_letter -> ReposViewModel.sortByLetter
            R.id.menu_repos_update -> ReposViewModel.sortByUpdate
            R.id.menu_repos_stars -> ReposViewModel.sortByStars
            else -> throw IllegalArgumentException("error menuItem id.")
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