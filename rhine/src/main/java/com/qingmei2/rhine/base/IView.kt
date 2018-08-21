package com.qingmei2.rhine.base

import android.arch.lifecycle.LifecycleOwner

interface IView {

    /**
     * 注入[LifecycleOwner]对象
     */
    fun injectLifecycleOwner(lifecycleOwner: LifecycleOwner)

    /**
     * 显示加载框
     */
    fun showLoading()

    /**
     * 隐藏加载框
     */
    fun hideLoading()
}