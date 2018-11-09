package com.qingmei2.rhine.widget.placeholderview

import android.view.View
import android.view.ViewGroup
import kotlin.properties.Delegates

interface ILoadingLayout {

    fun onCreateView(root: ViewGroup): View

    fun getView(): View

    fun show()

    fun hide()
}

abstract class BaseLoadingLayout : ILoadingLayout {

    var content: View by Delegates.notNull()

    override fun getView(): View = content

    abstract override fun onCreateView(root: ViewGroup): View

    override fun show() {
        content.visibility = View.VISIBLE
    }

    override fun hide() {
        content.visibility = View.GONE
    }
}