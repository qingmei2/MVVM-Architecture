package com.qingmei2.sample.ui.main.repos

import android.animation.ObjectAnimator
import android.view.MenuItem
import com.jakewharton.rxbinding3.recyclerview.scrollStateChanges
import com.jakewharton.rxbinding3.swiperefreshlayout.refreshes
import com.qingmei2.rhine.base.view.fragment.BaseFragment
import com.qingmei2.rhine.ext.jumpBrowser
import com.qingmei2.rhine.ext.livedata.toReactiveStream
import com.qingmei2.rhine.ext.reactivex.clicksThrottleFirst
import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseApplication
import com.qingmei2.sample.common.listScrollChangeStateProcessor
import com.qingmei2.sample.databinding.FragmentReposBinding
import com.uber.autodispose.autoDisposable
import kotlinx.android.synthetic.main.fragment_repos.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import java.util.concurrent.TimeUnit

class ReposFragment : BaseFragment<FragmentReposBinding>() {

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(reposKodeinModule)
    }

    private val mViewModel: ReposViewModel by instance()

    override val layoutId: Int = R.layout.fragment_repos

    private val mAdapter = ReposPagedAdapter(this)

    override fun initView() {
        toolbar.inflateMenu(R.menu.menu_repos_filter_type)

        mRecyclerView.adapter = mAdapter

        binds()
    }

    private fun binds() {
        // 列表滑动，底部按钮动态显示/隐藏
        mRecyclerView.scrollStateChanges()
                .debounce(500, TimeUnit.MILLISECONDS)
                .flatMap(listScrollChangeStateProcessor)
                .autoDisposable(scopeProvider)
                .subscribe { switchFabState(it) }

        // 每当数据源更新，更新列表
        mViewModel.pagedList
                .toReactiveStream()
                .autoDisposable(scopeProvider)
                .subscribe {
                    mAdapter.submitList(null)
                    mAdapter.submitList(it)
                }

        // 下拉刷新
        mSwipeRefreshLayout.refreshes()
                .autoDisposable(scopeProvider)
                .subscribe { mViewModel.refreshDataSource() }

        // 刷新状态恢复
        mViewModel.refreshing.toReactiveStream()
                .filter { it != mSwipeRefreshLayout.isRefreshing }
                .autoDisposable(scopeProvider)
                .subscribe { mSwipeRefreshLayout.isRefreshing = it }

        // 点击底部按钮，回到列表顶部
        fabTop.clicksThrottleFirst()
                .autoDisposable(scopeProvider)
                .subscribe { mRecyclerView.scrollToPosition(0) }

        // 选择排序策略
        toolbar.setOnMenuItemClickListener {
            onMenuSelected(it)
            true
        }

        // 列表点击事件
        mAdapter.getItemClickEvent()
                .autoDisposable(mAdapter)
                .subscribe { BaseApplication.INSTANCE.jumpBrowser(it) }
    }

    private fun onMenuSelected(menuItem: MenuItem) {
        mViewModel.sort.value = when (menuItem.itemId) {
            R.id.menu_repos_letter -> ReposViewModel.sortByLetter
            R.id.menu_repos_update -> ReposViewModel.sortByUpdate
            R.id.menu_repos_created -> ReposViewModel.sortByCreated
            else -> throw IllegalArgumentException("failure menuItem id.")
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