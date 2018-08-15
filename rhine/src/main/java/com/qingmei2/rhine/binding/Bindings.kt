package com.qingmei2.rhine.binding

import android.annotation.SuppressLint
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.BindingAdapter
import android.os.Build
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.webkit.WebSettings
import android.webkit.WebView

import com.annimon.stream.IntPair
import com.annimon.stream.Stream
import com.annimon.stream.function.Function
import com.qingmei2.rhine.binding.adapter.DataBindingItemViewBinder

import me.drakeet.multitype.ItemViewBinder
import me.drakeet.multitype.MultiTypeAdapter

/**
 * Created by QingMei on 2017/10/17.
 * desc:
 */

object Bindings {

    class BindableVariables : BaseObservable() {
        @Bindable
        var data: Any? = null
    }

    @BindingAdapter("itemLayout", "onBindItem")
    fun setAdapter(view: RecyclerView, resId: Int, onBindItem: DataBindingItemViewBinder.OnBindItem<*, *>) {
        val adapter = getOrCreateAdapter(view)

        adapter.register<Any>(Any::class.java, DataBindingItemViewBinder(resId, onBindItem))
    }

    private fun getOrCreateAdapter(view: RecyclerView): MultiTypeAdapter {
        if (view.adapter is MultiTypeAdapter) {
            return view.adapter as MultiTypeAdapter
        } else {
            val adapter = MultiTypeAdapter()
            view.adapter = adapter
            return adapter
        }
    }

    @BindingAdapter("linkers", "onBindItem")
    fun setAdapter(view: RecyclerView, linkers: List<Linker>, onBindItem: DataBindingItemViewBinder.OnBindItem<*, *>) {
        val adapter = getOrCreateAdapter(view)

        val binders = Stream.of(linkers)
                .map(???({ it.getLayoutId() }))
        .map { v -> DataBindingItemViewBinder(v, onBindItem) }
                .toArray(ItemViewBinder[]::new  /* Currently unsupported in Kotlin */)

        adapter.register(Any::class.java)
                .to(*binders)
                .withLinker({ o ->
                    Stream.of(linkers)
                            .map(???({ it.getMatcher() }))
                    .indexed()
                        .filter({ v -> v.getSecond().apply(o) })
                        .findFirst()
                        .map(???({ IntPair.getFirst() }))
                    .orElse(0)
                })
    }

    class Linker internal constructor(internal val matcher: Function<Any, Boolean>, internal val layoutId: Int) {
        companion object {

            fun of(matcher: Function<Any, Boolean>, layoutId: Int): Linker {
                return Linker(matcher, layoutId)
            }
        }
    }

    @BindingAdapter("items")
    fun setItems(view: RecyclerView, items: List<*>?) {
        val adapter = getOrCreateAdapter(view)
        adapter.items = items ?: emptyList<Any>()
        adapter.notifyDataSetChanged()
    }

    @BindingAdapter("onRefresh")
    fun setOnRefreshListener(view: SwipeRefreshLayout,
                             listener: SwipeRefreshLayout.OnRefreshListener) {
        view.setOnRefreshListener(listener)
    }

    @SuppressLint("SetJavaScriptEnabled")
    @BindingAdapter("html")
    fun setHtml(view: WebView, html: String) {
        var html = html
        view.settings.defaultTextEncodingName = "utf-8"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
        } else {
            view.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
        }
        view.settings.defaultFontSize = 12
        view.settings.javaScriptEnabled = true

        val tmp = "" +
                "<html>" +
                "<head>" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\">" +
                "    <style>" +
                "       img {max-width: 100%%; width:auto; height:auto;}" +
                "    </style>" +
                "</head>" +
                "<body>%s</body>" +
                "</html>"
        html = String.format(tmp, html)
        view.loadData(html, "text/html; charset=utf-8", "utf-8")
    }

}
