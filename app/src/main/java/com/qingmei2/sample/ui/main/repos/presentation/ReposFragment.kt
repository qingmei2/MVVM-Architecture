package com.qingmei2.sample.ui.main.repos.presentation

import android.os.Bundle
import android.view.View
import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseApplication
import com.qingmei2.sample.base.BaseFragment
import com.qingmei2.sample.databinding.FragmentReposBinding
import com.qingmei2.sample.ui.main.repos.reposKodeinModule
import kotlinx.android.synthetic.main.fragment_repos.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

class ReposFragment : BaseFragment<FragmentReposBinding>() {

    override val kodein: Kodein = Kodein.lazy {
        extend(BaseApplication.INSTANCE.kodein)
        import(reposKodeinModule)
        bind<ReposFragment>() with instance(this@ReposFragment)
    }

    private val viewDelegate: ReposViewDelegate by instance()

    override val layoutId: Int = R.layout.fragment_repos

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.inflateMenu(R.menu.menu_repos_filter_type)
        binding.delegate = viewDelegate
    }
}