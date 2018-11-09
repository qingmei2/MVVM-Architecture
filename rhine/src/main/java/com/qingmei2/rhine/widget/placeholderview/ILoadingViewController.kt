package com.qingmei2.rhine.widget.placeholderview

import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup

interface ILoadingViewController {

    fun showContentView()

    fun showEmptyView()

    fun showErrorView()

    fun showLoadingView()

    fun hidePlaceholders()

    fun hideContentView()

    fun destroyPlaceholders()
}

class LoadingViewController(
        private val rootView: ViewGroup,
        private val loadingLayout: ILoadingLayout,
        private val emptyLayout: ILoadingLayout,
        private val errorLayout: ILoadingLayout
) : ILoadingViewController {

    init {
        load(loadingLayout)
        load(emptyLayout)
        load(errorLayout)
    }

    override fun showEmptyView() {
        hideContentView()
        hidePlaceholders()
        emptyLayout.show()
    }

    override fun showErrorView() {
        hideContentView()
        hidePlaceholders()
        errorLayout.show()
    }

    override fun showLoadingView() {
        hidePlaceholders()
        loadingLayout.show()
    }

    override fun showContentView() {
        hidePlaceholders()
        for (index in 0..rootView.childCount) {
            rootView.getChildAt(index)?.also { child ->
                if (child != emptyLayout.getView()
                        && child != loadingLayout.getView()
                        && child != errorLayout.getView()) {
                    child.visibility = VISIBLE
                }
            }
        }
    }

    override fun hideContentView() {
        if (rootView.childCount <= 0) return

        for (index in 0..rootView.childCount) {
            rootView.getChildAt(index)?.also { child ->
                if (child != emptyLayout.getView()
                        && child != loadingLayout.getView()
                        && child != errorLayout.getView()) {
                    child.visibility = GONE
                }
            }
        }
    }

    override fun hidePlaceholders() {
        emptyLayout.hide()
        loadingLayout.hide()
        errorLayout.hide()
    }

    override fun destroyPlaceholders() {
        remove(emptyLayout)
        remove(loadingLayout)
        remove(errorLayout)
    }

    private fun load(placeholderView: ILoadingLayout) =
            rootView.addView(
                    placeholderView.onCreateView(rootView).apply {
                        visibility = GONE
                    }
            )

    private fun remove(placeholderView: ILoadingLayout) =
            rootView.removeView(placeholderView.getView())
}