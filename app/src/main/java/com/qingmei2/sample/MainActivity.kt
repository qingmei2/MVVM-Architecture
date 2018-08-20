package com.qingmei2.sample

import com.qingmei2.rhine.base.acitivty.BaseActivity
import com.qingmei2.sample.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, MainRhineViewModel>() {

    override fun variableId(): Int = BR.viewModel

    override val layoutId: Int = R.layout.activity_main

    override fun initData() {
        viewModel.fetchUserInfo()
    }

    override fun initView() {

    }
}
