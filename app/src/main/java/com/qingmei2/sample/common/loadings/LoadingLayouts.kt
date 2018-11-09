package com.qingmei2.sample.common.loadings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qingmei2.rhine.widget.placeholderview.BaseLoadingLayout
import com.qingmei2.sample.R

class ErrorLayout : BaseLoadingLayout() {

    override fun onCreateView(root: ViewGroup): View =
            LayoutInflater.from(root.context)
                    .inflate(R.layout.layout_placeholder_error, root, false).also {
                        content = it
                    }!!
}

class EmptyLayout : BaseLoadingLayout() {

    override fun onCreateView(root: ViewGroup): View =
            LayoutInflater.from(root.context)
                    .inflate(R.layout.layout_placeholder_empty, root, false).also {
                        content = it
                    }!!
}

class LoadingLayout : BaseLoadingLayout() {

    override fun onCreateView(root: ViewGroup): View =
            LayoutInflater.from(root.context)
                    .inflate(R.layout.layout_placeholder_load, root, false).also {
                        content = it
                    }!!
}