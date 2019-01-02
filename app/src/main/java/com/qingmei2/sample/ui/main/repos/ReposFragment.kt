package com.qingmei2.sample.ui.main.repos

import android.animation.ObjectAnimator
import android.view.MenuItem
import com.qingmei2.rhine.adapter.BasePagingDataBindingAdapter
import com.qingmei2.rhine.base.view.fragment.BaseFragment
import com.qingmei2.rhine.ext.jumpBrowser
import com.qingmei2.rhine.ext.livedata.toReactiveStream
import com.qingmei2.rhine.functional.Consumer
import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseApplication
import com.qingmei2.sample.common.FabAnimateViewModel
import com.qingmei2.sample.databinding.FragmentReposBinding
import com.qingmei2.sample.databinding.ItemReposRepoBinding
import com.qingmei2.sample.entity.Repo
import com.uber.autodispose.autoDisposable
import io.reactivex.Completable
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

    val adapter = BasePagingDataBindingAdapter<Repo, ItemReposRepoBinding>(
            layoutId = R.layout.item_repos_repo,
            bindBinding = { ItemReposRepoBinding.bind(it) },
            callback = { repo, binding, _ ->
                binding.apply {
                    data = repo
                    repoEvent = object : Consumer<String> {
                        override fun accept(t: String) {
                            BaseApplication.INSTANCE.jumpBrowser(t)
                        }
                    }
                }
            }
    )

    override fun initView() {
        toolbar.inflateMenu(R.menu.menu_repos_filter_type)

        Completable
                .mergeArray(
                        fabViewModel.visibleState
                                .toReactiveStream()
                                .doOnNext { switchFabState(it) }
                                .ignoreElements(),
                        viewModel.pagedList
                                .toReactiveStream()
                                .doOnNext { adapter.submitList(it) }
                                .ignoreElements()
                )
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