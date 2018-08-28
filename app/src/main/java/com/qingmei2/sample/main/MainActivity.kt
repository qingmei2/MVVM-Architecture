package com.qingmei2.sample.main

import com.qingmei2.rhine.ext.viewmodel.viewModel
import com.qingmei2.sample.BR
import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseActivity
import com.qingmei2.sample.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, MainViewDelegate>() {

    private val mainViewModel by lazy {
        viewModel(MainViewModel::class.java)
    }

    override val viewDelegate by lazy {
        MainViewDelegate(mainViewModel).apply {
            binding.delegate = this
        }
    }

    override val layoutId = R.layout.activity_main
}