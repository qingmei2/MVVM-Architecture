package com.qingmei2.sample.ui.login

import android.content.Intent
import com.qingmei2.sample.ui.main.MainActivity

class LoginNavigator(
        private val context: LoginActivity
) {

    fun toMain() {
        Intent(context, MainActivity::class.java).apply {
            context.startActivity(this)
        }
    }
}