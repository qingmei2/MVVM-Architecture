package com.qingmei2.sample.ui.login

import androidx.fragment.app.FragmentActivity
import com.qingmei2.sample.ui.MainActivity

class LoginNavigator(
        private val activity: androidx.fragment.app.FragmentActivity
) {

    fun toMain() = MainActivity.launch(activity)
}