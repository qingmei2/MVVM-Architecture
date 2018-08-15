package com.qingmei2.rhine.binding

import android.databinding.BindingAdapter
import android.view.View

/**
 * Created by QingMei on 2017/11/6.
 * desc:
 */

object ViewBinding {

    @BindingAdapter("visible")
    fun setVisible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @BindingAdapter("onLongClick")
    fun setOnLongClick(view: View, callback: Runnable) {
        view.setOnLongClickListener { __ ->
            callback.run()
            true
        }
    }
}
