package com.qingmei2.sample.main.ui

import android.databinding.BindingAdapter
import android.support.v7.widget.SearchView
import io.reactivex.functions.Consumer

@BindingAdapter("on_query_text_submit", "on_query_text_change", requireAll = false)
fun bindSearchViewQueryText(searchView: SearchView,
                            onSubmit: SearchConsumer?,
                            onChange: SearchConsumer?) {
    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

        override fun onQueryTextSubmit(query: String?): Boolean {
            onSubmit?.accept(query)
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            onChange?.accept(newText)
            return true
        }
    })
}

interface SearchConsumer : Consumer<String>