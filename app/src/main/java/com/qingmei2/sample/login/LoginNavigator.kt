package com.qingmei2.sample.login

import android.content.Intent
import com.qingmei2.sample.main.MainActivity

class LoginNavigator(
        private val context: LoginActivity
) {

    fun toMain() {
        Intent(context, MainActivity::class.java).apply {
            context.startActivity(this)
        }
    }
}