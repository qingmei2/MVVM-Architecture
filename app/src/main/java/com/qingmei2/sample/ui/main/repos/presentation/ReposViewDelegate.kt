package com.qingmei2.sample.ui.main.repos.presentation

import android.view.MenuItem
import com.qingmei2.rhine.base.viewdelegate.IViewDelegate
import com.qingmei2.sample.R

class ReposViewDelegate(
        val viewModel: ReposViewModel
) : IViewDelegate {

    fun onMenuSelected(menuItem: MenuItem) {
        viewModel.sortFunc.value = when (menuItem.itemId) {
            R.id.menu_repos_letter -> ReposViewModel.sortByLetter
            R.id.menu_repos_update -> ReposViewModel.sortByUpdate
            R.id.menu_repos_stars -> ReposViewModel.sortByStars
            else -> throw IllegalArgumentException("error menuItem id.")
        }
    }
}