package com.qingmei2.sample.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.qingmei2.sample.R

class LoadStateAdapter(
        private val adapter: SearchAdapter?
) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_footer, parent, false)
        return LoadStateViewHolder(view, adapter)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.binds(loadState)
    }
}

class LoadStateViewHolder(
        view: View,
        private val adapter: SearchAdapter?
) : RecyclerView.ViewHolder(view) {

    private val tvError = view.findViewById<TextView>(R.id.tvError)
    private val pbLoading = view.findViewById<ProgressBar>(R.id.pbLoading)

    @SuppressLint("SetTextI18n")
    fun binds(loadState: LoadState) {
        pbLoading.isVisible = loadState is LoadState.Loading
        tvError.isVisible = loadState is LoadState.Error
        if (loadState is LoadState.Error) {
            tvError.text = loadState.error.message + "\nClick to try again"
            tvError.setOnClickListener { adapter?.retry() }
        }
    }

}

