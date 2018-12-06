package com.qingmei2.rhine.base.navigator

import androidx.fragment.app.FragmentActivity

open class BaseNavigator(
        private val activity: FragmentActivity
) {

    fun finish() = activity.finish()
}