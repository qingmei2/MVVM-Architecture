package com.qingmei2.sample.ui.main.home

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import com.qingmei2.rhine.base.view.fragment.BaseFragment
import com.qingmei2.rhine.ext.livedata.toReactiveX
import com.qingmei2.sample.R
import com.qingmei2.sample.common.FabAnimateViewModel
import com.qingmei2.sample.common.loadings.CommonLoadingState
import com.qingmei2.sample.common.loadings.CommonLoadingViewModel
import com.qingmei2.sample.databinding.FragmentHomeBinding
import com.uber.autodispose.autoDisposable
import io.reactivex.Completable
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
    val loadingViewModel: CommonLoadingViewModel by instance()

    override val layoutId: Int = R.layout.fragment_home

    override fun initView() {
        Completable
                .mergeArray(
                        fabViewModel.visibleState
                                .toReactiveX()
                                .doOnNext { switchFabState(it) }
                                .ignoreElements(),
                        viewModel.loadingLayout
                                .toReactiveX()
                                .filter { it ->
                                    it != CommonLoadingState.LOADING    // Refreshing state not used
                                }
                                .doOnNext { loadingViewModel.applyState(it) }
                                .ignoreElements()
                )
                .autoDisposable(scopeProvider)
                .subscribe()
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