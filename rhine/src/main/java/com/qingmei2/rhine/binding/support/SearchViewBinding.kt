package com.qingmei2.rhine.binding.support

import android.databinding.BindingAdapter
import android.support.v7.widget.SearchView
import android.widget.EditText
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import io.reactivex.functions.Consumer

@BindingAdapter("bind_hintColor", "bind_textColor", requireAll = false)
fun bindHintColor(searchView: SearchView, hintColor: Int, textColor: Int) {
    searchView.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text).apply {
        setTextColor(textColor)
        setHintTextColor(hintColor)
    }
}

@SuppressWarnings("checkResult")
@BindingAdapter("bind_onQuerySubmit", "bind_onQueryChanged", requireAll = false)
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