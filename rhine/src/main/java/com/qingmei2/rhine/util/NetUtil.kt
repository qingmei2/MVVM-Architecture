package com.qingmei2.rhine.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Created by QingMei on 2017/6/19.
 * desc:
 */

object NetUtil {

    val NET_CNNT_BAIDU_OK = 1 // NetworkAvailable
    val NET_CNNT_BAIDU_TIMEOUT = 2 // no NetworkAvailable
    val NET_NOT_PREPARE = 3 // Net no ready
    val NET_ERROR = 4 //net error
    private val TIMEOUT = 3000 // TIMEOUT

    /**
     * check NetworkAvailable
     *
     * @param context
     * @return
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val manager = context.applicationContext.getSystemService(
                Context.CONNECTIVITY_SERVICE) as ConnectivityManager ?: return false
        val info = manager.activeNetworkInfo
        return if (null == info || !info.isAvailable) false else true
    }

}
