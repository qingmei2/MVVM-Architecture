package com.qingmei2.sample.ui.login.presentation

import android.support.v4.app.FragmentActivity
import com.qingmei2.sample.ui.MainActivity

class LoginNavigator(
        private val activity: FragmentActivity
) {

    fun toMain() = MainActivity.launch(activity)
}