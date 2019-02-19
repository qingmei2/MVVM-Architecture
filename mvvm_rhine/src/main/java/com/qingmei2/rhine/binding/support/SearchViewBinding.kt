package com.qingmei2.rhine.binding.support

import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter
import com.jakewharton.rxbinding3.appcompat.queryTextChangeEvents
import io.reactivex.functions.Consumer

@BindingAdapter("bind_hintColor", "bind_textColor", requireAll = false)
fun bindHintColor(searchView: SearchView, hintColor: Int, textColor: Int) {
    searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text).apply {
        setTextColor(textColor)
        setHintTextColor(hintColor)
    }
}

@SuppressWarnings("checkResult")
@BindingAdapter("bind_onQuerySubmit", "bind_onQueryChanged", requireAll = false)
fun bindSearchViewQueryText(searchView: SearchView,
                            onSubmit: SearchConsumer?,
                            onChange: SearchConsumer?) {
    searchView.queryTextChangeEvents()
            .subscribe {
                if (it.isSubmitted)
                    onSubmit?.accept(it.queryText.toString())
                else
                    onChange?.accept(it.queryText.toString())
            }
}

interface SearchConsumer : Consumer<String>