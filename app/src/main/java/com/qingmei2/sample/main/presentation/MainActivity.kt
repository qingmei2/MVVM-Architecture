package com.qingmei2.sample.main.presentation

import android.os.Bundle
import android.os.PersistableBundle
import com.qingmei2.rhine.ext.viewmodel.viewModel
import com.qingmei2.sample.BR
import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseActivity
import com.qingmei2.sample.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding, MainViewDelegate>() {

    private val mainViewModel by lazy {
        viewModel(MainViewModel::class.java)
    }

    override val delegateId: Int = BR.delegate

    override val instanceDelegate = {
        MainViewDelegate(mainViewModel)
    }

    override val layoutId = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setSupportActionBar(toolbar)
    }
}