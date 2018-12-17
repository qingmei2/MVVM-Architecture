package com.qingmei2.sample.ui.main

import android.annotation.SuppressLint
import com.qingmei2.rhine.base.view.fragment.BaseFragment
import com.qingmei2.sample.R
import com.qingmei2.sample.databinding.FragmentMainBinding
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

@SuppressLint("CheckResult")
class MainFragment : BaseFragment<FragmentMainBinding>() {

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(mainKodeinModule)
        bind<androidx.fragment.app.FragmentManager>() with instance(childFragmentManager)
    }

    override val layoutId: Int = R.layout.fragment_main

    override val viewDelegate: MainViewDelegate by instance()

    override fun initView() {
        binding.delegate = viewDelegate
    }
}