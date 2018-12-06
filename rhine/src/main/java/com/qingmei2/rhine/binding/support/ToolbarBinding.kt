package com.qingmei2.rhine.binding.support

import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import com.qingmei2.rhine.functional.Consumer

interface ToolbarMenuClickListener : Consumer<MenuItem>

@BindingAdapter("bind_menuClick")
fun onToolbarMenuClick(toolbar: Toolbar,
                       consumer: ToolbarMenuClickListener) =
        toolbar.setOnMenuItemClickListener {
            consumer.accept(it)
            true
        }