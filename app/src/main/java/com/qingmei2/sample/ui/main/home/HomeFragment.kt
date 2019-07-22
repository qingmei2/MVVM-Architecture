package com.qingmei2.sample.ui.main.home

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import com.jakewharton.rxbinding3.recyclerview.scrollStateChanges
import com.jakewharton.rxbinding3.swiperefreshlayout.refreshes
import com.qingmei2.rhine.base.view.fragment.BaseFragment
import com.qingmei2.rhine.ext.jumpBrowser
import com.qingmei2.rhine.ext.reactivex.clicksThrottleFirst
import com.qingmei2.rhine.util.RxSchedulers
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
        fabTop.clicksThrottleFirst()
                .map { 0 }
                .autoDisposable(scopeProvider)
                .subscribe(mRecyclerView::scrollToPosition)

        // swipe refresh event.
        mSwipeRefreshLayout.refreshes()
                .autoDisposable(scopeProvider)
                .subscribe { mViewModel.refreshDataSource() }

        // list item clicked event.
        mAdapter.getItemClickEvent()
                .autoDisposable(scopeProvider)
                .subscribe(BaseApplication.INSTANCE::jumpBrowser)

        mViewModel.observeViewState()
                .observeOn(RxSchedulers.ui)
                .autoDisposable(scopeProvider)
                .subscribe(this::onStateArrived)
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