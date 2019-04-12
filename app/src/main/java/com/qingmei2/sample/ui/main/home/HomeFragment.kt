package com.qingmei2.sample.ui.main.home

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import com.jakewharton.rxbinding3.recyclerview.scrollStateChanges
import com.jakewharton.rxbinding3.swiperefreshlayout.refreshes
import com.qingmei2.rhine.base.view.fragment.BaseFragment
import com.qingmei2.rhine.ext.jumpBrowser
import com.qingmei2.rhine.ext.livedata.toReactiveStream
import com.qingmei2.rhine.ext.reactivex.clicksThrottleFirst
import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseApplication
import com.qingmei2.sample.common.listScrollChangeStateProcessor
import com.qingmei2.sample.databinding.FragmentHomeBinding
import com.uber.autodispose.autoDisposable
import kotlinx.android.synthetic.main.fragment_home.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import java.util.concurrent.TimeUnit

@SuppressLint("CheckResult")
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(homeKodeinModule)
    }

    private val mViewModel: HomeViewModel by instance()

    override val layoutId: Int = R.layout.fragment_home

    private val mAdapter: HomePagedAdapter = HomePagedAdapter()

    override fun initView() {
        binds()

        mRecyclerView.adapter = mAdapter
    }

    private fun binds() {
        // 列表滑动，底部按钮动态显示/隐藏
        mRecyclerView.scrollStateChanges()
                .debounce(500, TimeUnit.MILLISECONDS)
                .flatMap(listScrollChangeStateProcessor)
                .autoDisposable(scopeProvider)
                .subscribe { switchFabState(it) }

        // 点击底部按钮，回到列表顶部
        fabTop.clicksThrottleFirst()
                .autoDisposable(scopeProvider)
                .subscribe { mRecyclerView.scrollToPosition(0) }

        // 下拉刷新
        mSwipeRefreshLayout.refreshes()
                .autoDisposable(scopeProvider)
                .subscribe { mViewModel.refreshDataSource() }

        // 刷新状态恢复
        mViewModel.refreshing.toReactiveStream()
                .filter { it != mSwipeRefreshLayout.isRefreshing }
                .autoDisposable(scopeProvider)
                .subscribe { mSwipeRefreshLayout.isRefreshing = it }

        // 每当数据源更新，更新列表
        mViewModel.pagedList.toReactiveStream()
                .autoDisposable(scopeProvider)
                .subscribe {
                    mAdapter.submitList(null)
                    mAdapter.submitList(it)
                }

        // 列表点击事件
        mAdapter.getItemClickEvent()
                .autoDisposable(scopeProvider)
                .subscribe { BaseApplication.INSTANCE.jumpBrowser(it) }
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