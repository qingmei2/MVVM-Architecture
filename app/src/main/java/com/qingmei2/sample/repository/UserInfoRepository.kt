package com.qingmei2.sample.repository

import android.content.SharedPreferences
import com.qingmei2.architecture.core.util.SingletonHolderSingleArg
import com.qingmei2.architecture.core.util.prefs.boolean
import com.qingmei2.architecture.core.util.prefs.string

class UserInfoRepository(prefs: SharedPreferences) {

    var accessToken: String by prefs.string("user_access_token", "")

    var username by prefs.string("username", "")

    var password by prefs.string("password", "")

    var isAutoLogin: Boolean by prefs.boolean("auto_login", true)

    companion object :
            SingletonHolderSingleArg<UserInfoRepository, SharedPreferences>(::UserInfoRepository)
}