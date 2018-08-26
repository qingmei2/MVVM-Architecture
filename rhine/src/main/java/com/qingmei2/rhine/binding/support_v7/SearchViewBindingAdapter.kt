package com.qingmei2.rhine.binding.support_v7

import android.databinding.BindingAdapter
import android.support.v7.widget.SearchView
import android.widget.EditText
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import io.reactivex.functions.Consumer

@BindingAdapter("bind_hint_color", "bind_text_color", requireAll = false)
fun bindHintColor(searchView: SearchView, hintColor: Int, textColor: Int) {
    searchView.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text).apply {
        setTextColor(textColor)
        setHintTextColor(hintColor)
    }
}

@BindingAdapter("on_query_text_submit", "on_query_text_change", requireAll = false)
fun bindSearchViewQueryText(searchView: SearchView,
                            onSubmit: SearchConsumer?,
                            onChange: SearchConsumer?) {
    RxSearchView.queryTextChangeEvents(searchView)
            .subscribe {
                if (it.isSubmitted)
                    onSubmit?.accept(it.queryText().toString())
                else
                    onChange?.accept(it.queryText().toString())
            }
}

interface SearchConsumer : Consumer<String>