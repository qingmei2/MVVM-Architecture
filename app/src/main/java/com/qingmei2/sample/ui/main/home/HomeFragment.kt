package com.qingmei2.sample.ui.main.home

import android.annotation.SuppressLint
import com.qingmei2.rhine.base.view.BaseFragment
import com.qingmei2.sample.R
import com.qingmei2.sample.databinding.FragmentHomeBinding
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

@SuppressLint("CheckResult")
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewDelegate>() {

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(homeKodeinModule)
    }

    override val viewDelegate: HomeViewDelegate by instance()

    override val layoutId: Int = R.layout.fragment_home

    override fun initView() {
        binding.delegate = viewDelegate
    }
}