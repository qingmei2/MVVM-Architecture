package com.qingmei2.sample.ui.main.home

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import com.jakewharton.rxbinding3.recyclerview.scrollStateChanges
import com.jakewharton.rxbinding3.swiperefreshlayout.refreshes
import com.qingmei2.rhine.adapter.BasePagingDataBindingAdapter
import com.qingmei2.rhine.base.view.fragment.BaseFragment
import com.qingmei2.rhine.ext.jumpBrowser
import com.qingmei2.rhine.ext.livedata.toReactiveStream
import com.qingmei2.rhine.ext.reactivex.clicksThrottleFirst
import com.qingmei2.rhine.functional.Consumer
import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseApplication
import com.qingmei2.sample.common.listScrollChangeStateProcessor
import com.qingmei2.sample.databinding.FragmentHomeBinding
import com.qingmei2.sample.databinding.ItemHomeReceivedEventBinding
import com.qingmei2.sample.entity.ReceivedEvent
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

    val mViewModel: HomeViewModel by instance()

    override val layoutId: Int = R.layout.fragment_home

    private val mAdapter: BasePagingDataBindingAdapter<ReceivedEvent, ItemHomeReceivedEventBinding> =
            BasePagingDataBindingAdapter(
                    layoutId = R.layout.item_home_received_event,
                    bindBinding = {
                        ItemHomeReceivedEventBinding.bind(it)
                    },
                    callback = { data, binding, _ ->
                        binding.data = data
                        binding.actorEvent = object : Consumer<String> {
                            override fun accept(t: String) {
                                BaseApplication.INSTANCE.jumpBrowser(t)
                            }
                        }
                        binding.repoEvent = object : Consumer<String> {
                            override fun accept(t: String) {
                                BaseApplication.INSTANCE.jumpBrowser(t)
                            }
                        }
                    }
            )

    override fun initView() {
        binds()

        mRecyclerView.adapter = mAdapter
    }

    private fun binds() {
        mRecyclerView.scrollStateChanges()
                .debounce(500, TimeUnit.MILLISECONDS)
                .flatMap(listScrollChangeStateProcessor)
                .autoDisposable(scopeProvider)
                .subscribe { switchFabState(it) }

        fabTop.clicksThrottleFirst()
                .autoDisposable(scopeProvider)
                .subscribe { mRecyclerView.scrollToPosition(0) }

        mSwipeRefreshLayout.refreshes()
                .autoDisposable(scopeProvider)
                .subscribe { mViewModel.refreshDataSource() }

        mViewModel.refreshing.toReactiveStream()
                .filter { it != mSwipeRefreshLayout.isRefreshing }
                .autoDisposable(scopeProvider)
                .subscribe { mSwipeRefreshLayout.isRefreshing = it }

        mViewModel.pagedList.toReactiveStream()
                .autoDisposable(scopeProvider)
                .subscribe { mAdapter.submitList(it) }
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