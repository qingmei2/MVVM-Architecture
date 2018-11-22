package com.qingmei2.rhine.base.navigator

import android.support.v4.app.FragmentActivity

open class BaseNavigator(
        private val activity: FragmentActivity
) {

    fun finish() = activity.finish()
}