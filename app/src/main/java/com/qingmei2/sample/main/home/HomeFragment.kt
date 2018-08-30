package com.qingmei2.sample.main.home

import com.qingmei2.rhine.ext.viewmodel.viewModel
import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseFragment
import com.qingmei2.sample.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewDelegate>() {

    private val homeViewModel: HomeViewModel by lazy {
        viewModel(HomeViewModel::class.java)
    }

    override val delegateSupplier = {
        HomeViewDelegate(homeViewModel).apply {
            binding.delegate = this
        }
    }

    override val layoutId: Int = R.layout.fragment_home
}