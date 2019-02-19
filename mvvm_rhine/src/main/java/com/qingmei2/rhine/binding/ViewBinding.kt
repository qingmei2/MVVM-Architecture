package com.qingmei2.rhine.binding

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.BindingAdapter
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.view.longClicks
import io.reactivex.functions.Consumer
import java.util.concurrent.TimeUnit


interface ViewClickConsumer : Consumer<View>

const val DEFAULT_THROTTLE_TIME = 500L

/**
 * [View]是否可见
 *
 * @param visible 值为true时可见
 */
@BindingAdapter("bind_view_visibility")
fun setVisible(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

/**
 * [View]长按事件
 *
 * @param consumer 事件消费者
 */
@SuppressLint("CheckResult")
@BindingAdapter("bind_view_onLongClick")
fun setOnLongClickEvent(view: View, consumer: ViewClickConsumer) {
    view.longClicks()
            .subscribe { consumer.accept(view) }
}

/**
 * [View]防抖动点击事件
 *
 * @param consumer 点击事件消费者
 * @param time 防抖动时间间隔，单位ms
 */
@SuppressLint("CheckResult")
@BindingAdapter("bind_view_onClick", "bind_view_throttleFirst", requireAll = false)
fun setOnClickEvent(view: View, consumer: ViewClickConsumer, time: Long?) {
    view.clicks()
            .throttleFirst(time ?: DEFAULT_THROTTLE_TIME, TimeUnit.MILLISECONDS)
            .subscribe { consumer.accept(view) }
}

/**
 * [View]被点击时,关闭输入框
 *
 * @param closed 当值为true时，启动该功能
 */
@SuppressLint("CheckResult")
@BindingAdapter("bind_view_onClick_closeSoftInput")
fun closeSoftInputWhenClick(view: View, closed: Boolean = false) {
    view.clicks()
            .subscribe {
                if (closed) {
                    val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
            }
}