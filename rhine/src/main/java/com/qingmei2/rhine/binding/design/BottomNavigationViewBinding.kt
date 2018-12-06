package com.qingmei2.rhine.binding.design

import androidx.databinding.BindingAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
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