package com.qingmei2.rhine.util

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast

import com.qingmei2.rhine.base.BaseApplication

import java.lang.reflect.Field

/**
 * Created by sashiro on 2017/6/5.
 */

object ScreenUtil {

    val screenHeight: Int
        get() = BaseApplication.instance!!.resources.displayMetrics.heightPixels

    fun layoutInflater(context: Context): LayoutInflater {
        return context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    fun toast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    /**
     * get the statusbar height
     *
     * @param context
     * @return
     */
    fun getStatusbarHeight(context: Context): Int {
        var statusBarHeight = 0
        try {
            val c = Class.forName("com.android.internal.R\$dimen")
            val o = c.newInstance()
            val field = c.getField("status_bar_height")
            val x = field.get(o) as Int
            statusBarHeight = context.resources.getDimensionPixelSize(x)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return statusBarHeight
    }

    fun dp2px(dpValue: Float): Int {
        val scale = BaseApplication.instance!!.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun getScreenHeightExcludeStatusbar(context: Context): Int {
        return screenHeight - getStatusbarHeight(context)
    }

}
