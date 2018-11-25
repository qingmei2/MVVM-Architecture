package com.qingmei2.rhine.binding.support

import android.databinding.BindingAdapter
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager

@BindingAdapter("bind_viewPager", requireAll = true)
fun bindTabLayoutWithViewPager(tabLayout: TabLayout,
                               viewPager: ViewPager) =
        tabLayout.setupWithViewPager(viewPager)