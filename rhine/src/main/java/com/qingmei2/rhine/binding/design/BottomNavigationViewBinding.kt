package com.qingmei2.rhine.binding.design

import android.databinding.BindingAdapter
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import android.view.ViewGroup
import io.reactivex.functions.Consumer

interface SelectedChangeConsumer : Consumer<MenuItem>

@BindingAdapter("bind_onNavigationBottomSelectedChanged")
fun setOnSelectedChangeListener(view: BottomNavigationView,
                                consumer: SelectedChangeConsumer?) {
    view.setOnNavigationItemSelectedListener { item: MenuItem ->
        consumer?.accept(item)
        true
    }
}