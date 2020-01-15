package com.qingmei2.sample.ui.main.home

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import com.jakewharton.rxbinding3.recyclerview.scrollStateChanges
import com.qingmei2.architecture.core.base.view.fragment.BaseFragment
import com.qingmei2.architecture.core.ext.jumpBrowser
import com.qingmei2.architecture.core.ext.observe
import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseApplication
import com.qingmei2.sample.common.listScrollChangeStateProcessor
import com.qingmei2.sample.utils.toast
import com.uber.autodispose.autoDisposable
import kotlinx.android.synthetic.main.fragment_home.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import java.util.concurrent.TimeUnit

class HomeFragment : BaseFragment() {

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(homeKodeinModule)
    }

    private val mViewModel: HomeViewModel by instance()

    override val layoutId: Int = R.layout.fragment_home

    private val mAdapter: HomePagedAdapter = HomePagedAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binds()

        mRecyclerView.adapter = mAdapter
    }

    private fun binds() {
        // when list scrolling start or stop, then switch bottom button visible state.
        mRecyclerView.scrollStateChanges()
                .debounce(500, TimeUnit.MILLISECONDS)
                .compose(listScrollChangeStateProcessor)
                .autoDisposable(scopeProvider)
                .subscribe(::switchFabState)

        // when button was clicked, scrolling list to top.
        fabTop.setOnClickListener {
            mRecyclerView.scrollToPosition(0)
        }

        // swipe refresh event.
        mSwipeRefreshLayout.setOnRefreshListener {
            mViewModel.refreshDataSource()
        }

        // list item clicked event.
        observe(mAdapter.observeItemEvent(), BaseApplication.INSTANCE::jumpBrowser)

        // 订阅UI状态的变更
        observe(mViewModel.viewStateLiveData, this::onStateArrived)
    }

    private fun onStateArrived(state: HomeViewState) {
        if (state.throwable != null) {
            toast { state.throwable.message ?: "network error." }
        }

        if (state.pagedList != null) {
            mAdapter.submitList(state.pagedList)
        }

        if (state.isLoading != mSwipeRefreshLayout.isRefreshing) {
            mSwipeRefreshLayout.isRefreshing = state.isLoading
        }
    }

    private fun switchFabState(show: Boolean) {
        when (show) {
            false -> ObjectAnimator.ofFloat(fabTop, "translationX", 250f, 0f)
            true -> ObjectAnimator.ofFloat(fabTop, "translationX", 0f, 250f)
        }.apply {
            this.duration = 300
            this.start()
        }
    }
}