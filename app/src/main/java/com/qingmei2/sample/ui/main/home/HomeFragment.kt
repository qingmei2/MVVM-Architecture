package com.qingmei2.sample.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.paging.PagedList
import com.qingmei2.architecture.core.base.view.fragment.BaseFragment
import com.qingmei2.architecture.core.ext.jumpBrowser
import com.qingmei2.architecture.core.ext.observe
import com.qingmei2.sample.R
import com.qingmei2.sample.entity.ReceivedEvent
import com.qingmei2.sample.utils.removeAllAnimation
import com.qingmei2.sample.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private val mViewModel: HomeViewModel by viewModels()

    override val layoutId: Int = R.layout.fragment_home

    private val mAdapter: HomePagedAdapter = HomePagedAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binds()

        mRecyclerView.adapter = mAdapter
        mRecyclerView.removeAllAnimation()
    }

    private fun binds() {
        // when button was clicked, scrolling list to top.
        fabTop.setOnClickListener {
            mRecyclerView.scrollToPosition(0)
        }

        // swipe refresh event.
        mSwipeRefreshLayout.setOnRefreshListener {
            mViewModel.refreshDataSource()
        }

        // list item clicked event.
        observe(mAdapter.observeItemEvent(), requireActivity()::jumpBrowser)

        // subscribe UI state.
        observe(mViewModel.viewStateLiveData, this::onNewState)
        observe(mViewModel.pagedListLiveData, this::onPagedList)
    }

    private fun onPagedList(pagedList: PagedList<ReceivedEvent>) {
        mAdapter.submitList(pagedList)
        mRecyclerView.scrollToPosition(0)
    }

    private fun onNewState(state: HomeViewState) {
        if (state.throwable != null) {
            toast { state.throwable.message ?: "network error." }
        }

        if (state.isLoading != mSwipeRefreshLayout.isRefreshing) {
            mSwipeRefreshLayout.isRefreshing = state.isLoading
        }
    }
}
