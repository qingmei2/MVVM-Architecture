package com.qingmei2.sample.ui.main.repos

import com.qingmei2.rhine.base.view.BaseFragment
import com.qingmei2.sample.R
import com.qingmei2.sample.R.id.toolbar
import com.qingmei2.sample.databinding.FragmentReposBinding
import kotlinx.android.synthetic.main.fragment_repos.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class ReposFragment : BaseFragment<FragmentReposBinding>() {

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(reposKodeinModule)
    }

    override val viewDelegate: ReposViewDelegate by instance()

    override val layoutId: Int = R.layout.fragment_repos

    override fun initView() {
        toolbar.inflateMenu(R.menu.menu_repos_filter_type)
        binding.delegate = viewDelegate
    }
}