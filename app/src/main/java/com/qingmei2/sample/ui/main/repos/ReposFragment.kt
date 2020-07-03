package com.qingmei2.sample.ui.main.repos

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.paging.PagedList
import com.qingmei2.architecture.core.base.view.fragment.BaseFragment
import com.qingmei2.architecture.core.ext.jumpBrowser
import com.qingmei2.architecture.core.ext.observe
import com.qingmei2.sample.R
import com.qingmei2.sample.entity.Repo
import com.qingmei2.sample.utils.removeAllAnimation
import com.qingmei2.sample.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_repos.*
import kotlinx.android.synthetic.main.fragment_repos.fabTop
import kotlinx.android.synthetic.main.fragment_repos.mRecyclerView
import kotlinx.android.synthetic.main.fragment_repos.mSwipeRefreshLayout

@AndroidEntryPoint
class ReposFragment : BaseFragment() {

    private val mViewModel: ReposViewModel by viewModels()

    override val layoutId: Int = R.layout.fragment_repos

    private val mAdapter = ReposPagedAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.inflateMenu(R.menu.menu_repos_filter_type)

        mRecyclerView.adapter = mAdapter
        mRecyclerView.removeAllAnimation()

        binds()
    }

    private fun binds() {
        // swipe refresh event.
        mSwipeRefreshLayout.setOnRefreshListener {
            mViewModel.refreshDataSource()
        }

        // when button was clicked, scrolling list to top.
        fabTop.setOnClickListener {
            mRecyclerView.scrollToPosition(0)
        }

        // menu item clicked event.
        toolbar.setOnMenuItemClickListener {
            onMenuSelected(it)
            true
        }

        // list item clicked event.
        observe(mAdapter.getItemClickEvent(), requireActivity()::jumpBrowser)

        // subscribe UI state
        observe(mViewModel.viewStateLiveData, this::onNewState)
        observe(mViewModel.pagedListLiveData, this::onPagedList)
    }

    private fun onPagedList(pagedList: PagedList<Repo>) {
        mAdapter.submitList(pagedList)
        mRecyclerView.scrollToPosition(0)
    }

    private fun onNewState(state: ReposViewState) {
        if (state.throwable != null) {
            // handle throwable
            toast { "network failure." }
        }

        if (state.isLoading != mSwipeRefreshLayout.isRefreshing) {
            mSwipeRefreshLayout.isRefreshing = state.isLoading
        }
    }

    private fun onMenuSelected(menuItem: MenuItem) {
        mViewModel.onSortChanged(
                when (menuItem.itemId) {
                    R.id.menu_repos_letter -> ReposViewModel.sortByLetter
                    R.id.menu_repos_update -> ReposViewModel.sortByUpdate
                    R.id.menu_repos_created -> ReposViewModel.sortByCreated
                    else -> throw IllegalArgumentException("failure menuItem id.")
                }
        )
    }
}
