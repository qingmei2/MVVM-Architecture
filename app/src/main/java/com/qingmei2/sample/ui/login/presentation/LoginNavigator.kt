package com.qingmei2.sample.ui.login.presentation

import android.support.v4.app.FragmentActivity
import androidx.navigation.Navigation
import com.qingmei2.sample.R

class LoginNavigator(
        private val activity: FragmentActivity
) {

    private fun findNavController() =
            Navigation.findNavController(activity, R.id.navHostFragment)

    fun toMain() = findNavController().navigate(R.id.nav_main)
}