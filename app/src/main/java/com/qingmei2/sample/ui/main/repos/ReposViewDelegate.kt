package com.qingmei2.sample.ui.main.repos

import android.animation.ObjectAnimator
import android.view.MenuItem
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.qingmei2.rhine.base.viewdelegate.BaseViewDelegate
import com.qingmei2.rhine.ext.livedata.toFlowable
import com.qingmei2.sample.R
import com.qingmei2.sample.common.FabAnimateViewModel
import com.uber.autodispose.autoDisposable

class ReposViewDelegate(
        val viewModel: ReposViewModel,
        val fabViewModel: FabAnimateViewModel,
        val fabTop: FloatingActionButton
) : BaseViewDelegate() {

    override fun onCreate(lifecycleOwner: LifecycleOwner) {
        super.onCreate(lifecycleOwner)
        fabViewModel.visibleState
                .toFlowable()
                .doOnNext { switchFabState(it) }
                .autoDisposable(viewModel)
                .subscribe()
    }

    fun onMenuSelected(menuItem: MenuItem) {
        viewModel.sort.value = when (menuItem.itemId) {
            R.id.menu_repos_letter -> ReposViewModel.sortByLetter
            R.id.menu_repos_update -> ReposViewModel.sortByUpdate
            R.id.menu_repos_created -> ReposViewModel.sortByCreated
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