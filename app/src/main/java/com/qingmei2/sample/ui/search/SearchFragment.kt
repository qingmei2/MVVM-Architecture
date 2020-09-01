package com.qingmei2.sample.ui.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.qingmei2.architecture.core.base.view.fragment.BaseFragment
import com.qingmei2.architecture.core.ext.jumpBrowser
import com.qingmei2.architecture.core.ext.observe
import com.qingmei2.sample.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_search

    private val mViewModel: SearchViewModel by viewModels()

    private val mAdapter = SearchAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        binds()
    }

    private fun initViews() {
        //显示提交按钮
        searchView.isSubmitButtonEnabled = true

        mSwipeRefreshLayout.setOnRefreshListener { mAdapter.refresh() }

        mRecyclerView.adapter = mAdapter.withLoadStateFooter(SearchLoadStateAdapter(mAdapter))
    }

    private fun binds() {
        // navigation clicked event.
        toolbar.setNavigationOnClickListener {
            activity?.finish()
        }
        // search button clicked event.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                mViewModel.search(query)
                return false
            }

            override fun onQueryTextChange(newText: String?) = false
        })

        // when button was clicked, scrolling list to top.
        fabTop.setOnClickListener {
            mRecyclerView.scrollToPosition(0)
        }

        lifecycleScope.launch {
            mAdapter.loadStateFlow.collectLatest { loadStates ->
                mSwipeRefreshLayout.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }

        observe(mViewModel.repoListLiveData) { mAdapter.submitData(lifecycle, it) }

        observe(mAdapter.observeItemEvent(), requireContext()::jumpBrowser)
    }

}
