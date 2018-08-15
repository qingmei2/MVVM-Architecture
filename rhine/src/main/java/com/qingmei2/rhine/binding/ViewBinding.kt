package com.qingmei2.rhine.binding

import android.databinding.BindingAdapter
import android.view.View

object ViewBinding {

    @BindingAdapter("visible")
    fun setVisible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @BindingAdapter("onLongClick")
    fun setOnLongClick(view: View, callback: Runnable) {
        view.setOnLongClickListener {
            callback.run()
            true
        }
    }
}
