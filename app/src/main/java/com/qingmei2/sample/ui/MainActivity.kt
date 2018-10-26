package com.qingmei2.sample.ui

import android.os.Bundle
import androidx.navigation.findNavController
import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseActivity
import com.qingmei2.sample.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val delegate: MainActivityDelegate = MainActivityDelegate()

    override val layoutId = R.layout.activity_main

    override fun onSupportNavigateUp(): Boolean =
            findNavController(R.id.navHostFragment).navigateUp()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.delegate = delegate
    }
}