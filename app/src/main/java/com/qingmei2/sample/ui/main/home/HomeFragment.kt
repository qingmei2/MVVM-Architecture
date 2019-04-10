package com.qingmei2.sample.ui.main.home

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import com.qingmei2.rhine.adapter.BasePagingDataBindingAdapter
import com.qingmei2.rhine.base.view.fragment.BaseFragment
import com.qingmei2.rhine.ext.jumpBrowser
import com.qingmei2.rhine.ext.livedata.toReactiveStream
import com.qingmei2.rhine.functional.Consumer
import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseApplication
import com.qingmei2.sample.common.FabAnimateViewModel
import com.qingmei2.sample.databinding.FragmentHomeBinding
import com.qingmei2.sample.databinding.ItemHomeReceivedEventBinding
import com.qingmei2.sample.entity.ReceivedEvent
import com.uber.autodispose.autoDisposable
import kotlinx.android.synthetic.main.fragment_home.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

@SuppressLint("CheckResult")
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(homeKodeinModule)
    }

    val viewModel: HomeViewModel by instance()
    val fabViewModel: FabAnimateViewModel by instance()

    override val layoutId: Int = R.layout.fragment_home

    val adapter: BasePagingDataBindingAdapter<ReceivedEvent, ItemHomeReceivedEventBinding> =
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
        fabViewModel.visibleState.toReactiveStream()
                .autoDisposable(scopeProvider)
                .subscribe { switchFabState(it) }
        viewModel.pagedList.toReactiveStream()
                .autoDisposable(scopeProvider)
                .subscribe { adapter.submitList(it) }
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