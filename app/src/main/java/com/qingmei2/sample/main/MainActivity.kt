package com.qingmei2.sample.main

import com.qingmei2.rhine.ext.viewmodel.viewModel
import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseActivity
import com.qingmei2.sample.databinding.ActivityMainBinding
import com.qingmei2.sample.main.profile.ProfileFragment

class MainActivity : BaseActivity<ActivityMainBinding, MainViewDelegate>() {

    private val mainViewModel by lazy {
        viewModel(MainViewModel::class.java)
    }

    private val navigator by lazy {
        MainNavigator(this).apply {

        }
    }

    private val profile by lazy {
        ProfileFragment()
    }

    override val delegateSupplier = {
        MainViewDelegate(
                mainViewModel,
                profile,
                navigator
        ).apply {
            binding.delegate = this
        }
    }

    override val layoutId = R.layout.activity_main
}