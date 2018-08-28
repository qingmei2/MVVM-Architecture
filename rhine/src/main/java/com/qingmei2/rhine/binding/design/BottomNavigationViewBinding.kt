package com.qingmei2.rhine.binding.design

import android.annotation.SuppressLint
import android.databinding.BindingAdapter
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView

/**
 * Disable [BottomNavigationView] shift mode.
 */
@SuppressLint("RestrictedApi")
@BindingAdapter("bind_disable_shift")
fun disableShiftMode(view: BottomNavigationView, disable: Boolean = false) {
    if (disable) {
        val menuView = view.getChildAt(0) as BottomNavigationMenuView
        try {
            menuView.javaClass.getDeclaredField("mShiftingMode").apply {
                isAccessible = true
                setBoolean(menuView, false)
                isAccessible = false
            }
            for (index in 0..menuView.childCount) {
                val item = menuView.getChildAt(index) as BottomNavigationItemView
                item.setShiftingMode(false)
                item.setChecked(item.itemData.isChecked)
            }
        } catch (e: NoSuchFieldException) {
        } catch (e: IllegalAccessException) {
        }
    }
}