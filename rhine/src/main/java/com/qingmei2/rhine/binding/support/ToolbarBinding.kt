package com.qingmei2.rhine.binding.support

import android.databinding.BindingAdapter
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.qingmei2.rhine.functional.Consumer

interface ToolbarMenuClickListener : Consumer<MenuItem>

@BindingAdapter("bind_menuClick")
fun onToolbarMenuClick(toolbar: Toolbar,
                       consumer: ToolbarMenuClickListener) =
        toolbar.setOnMenuItemClickListener {
            consumer.accept(it)
            true
        }