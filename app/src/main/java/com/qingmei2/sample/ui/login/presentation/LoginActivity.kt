package com.qingmei2.sample.ui.login.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.qingmei2.sample.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initFragment()
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction()
                .add(R.id.flContainer, LoginFragment())
                .commitAllowingStateLoss()
    }
}