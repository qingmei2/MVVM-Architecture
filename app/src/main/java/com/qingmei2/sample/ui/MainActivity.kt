package com.qingmei2.sample.ui

import android.content.Intent
import androidx.navigation.findNavController
import com.qingmei2.rhine.base.view.activity.BaseActivity
import com.qingmei2.sample.R
import com.qingmei2.sample.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutId = R.layout.activity_main

    override fun onSupportNavigateUp(): Boolean =
            findNavController(R.id.navHostFragment).navigateUp()

    companion object {

        fun launch(activity: androidx.fragment.app.FragmentActivity) =
                activity.apply {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
    }
}