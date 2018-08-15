package com.qingmei2.sample

import com.qingmei2.rhine.base.acitivty.BaseActivity
import com.qingmei2.sample.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {


    override fun initViewModel(): MainViewModel = MainViewModel()

    override val layoutId: Int = R.layout.activity_main

    override fun initData() {

    }

    override fun initView() {
        serviceManager.userInfoService
                .getUserInfo("qingmei2")
                .map { it.toString() }
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(tvContent::setText)
    }
}
