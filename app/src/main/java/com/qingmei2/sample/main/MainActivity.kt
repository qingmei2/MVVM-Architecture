package com.qingmei2.sample.main

import com.qingmei2.rhine.ext.viewmodel.viewModel
import com.qingmei2.sample.BR
import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseActivity
import com.qingmei2.sample.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, MainScreenDelegate>() {

    val mainViewModel = viewModel(MainViewModel::class.java)

    override val delegateId: Int = BR.delegate

    override val instanceDelegate = {
        MainScreenDelegate(mainViewModel)
    }

    override val layoutId = R.layout.activity_main
}
