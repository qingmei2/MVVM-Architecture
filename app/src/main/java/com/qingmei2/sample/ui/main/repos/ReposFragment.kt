package com.qingmei2.sample.ui.main.repos

import android.animation.ObjectAnimator
import android.view.MenuItem
import com.qingmei2.rhine.base.view.fragment.BaseFragment
import com.qingmei2.rhine.ext.livedata.toReactiveX
import com.qingmei2.sample.R
import com.qingmei2.sample.common.FabAnimateViewModel
import com.qingmei2.sample.databinding.FragmentReposBinding
import com.uber.autodispose.autoDisposable
import kotlinx.android.synthetic.main.fragment_repos.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class ReposFragment : BaseFragment<FragmentReposBinding>() {

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(reposKodeinModule)
    }

    val viewModel: ReposViewModel by instance()

    val fabViewModel: FabAnimateViewModel by instance()

    override val layoutId: Int = R.layout.fragment_repos

    override fun initView() {
        toolbar.inflateMenu(R.menu.menu_repos_filter_type)

        fabViewModel.visibleState
                .toReactiveX()
                .doOnNext { switchFabState(it) }
                .autoDisposable(viewModel)
                .subscribe()
    }

    fun onMenuSelected(menuItem: MenuItem) {
        viewModel.sort.value = when (menuItem.itemId) {
            R.id.menu_repos_letter -> ReposViewModel.sortByLetter
            R.id.menu_repos_update -> ReposViewModel.sortByUpdate
            R.id.menu_repos_created -> ReposViewModel.sortByCreated
            else -> throw IllegalArgumentException("error menuItem id.")
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