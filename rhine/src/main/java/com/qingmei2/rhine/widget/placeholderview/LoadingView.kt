package com.qingmei2.rhine.widget.placeholderview

import android.R
import android.support.v4.app.FragmentActivity
import android.view.View
import android.view.ViewGroup

class LoadingView private constructor(
        private val controller: LoadingViewController
) : ILoadingViewController by controller {

    companion object {

        fun build(view: View,
                  loading: () -> ILoadingLayout,
                  empty: () -> ILoadingLayout,
                  error: () -> ILoadingLayout
        ): LoadingView = LoadingView(
                when (view is ViewGroup) {
                    true -> LoadingViewController(view, loading(), empty(), error())
                    false -> throw IllegalArgumentException("the param need ViewGroup instance.")
                }
        )

        fun build(context: FragmentActivity,
                  loading: () -> ILoadingLayout,
                  empty: () -> ILoadingLayout,
                  error: () -> ILoadingLayout
        ): LoadingView = build(
                context.findViewById<ViewGroup>(R.id.content).getChildAt(0),
                loading, empty, error
        )
    }
}