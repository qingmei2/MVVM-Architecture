package com.qingmei2.rhine.binding

import android.databinding.BindingAdapter
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.functions.Consumer
import java.util.concurrent.TimeUnit

interface ViewClickConsumer : Consumer<View>

const val DEFAULT_THROTTLE_TIME = 500L

@BindingAdapter("bind_visible")
fun setVisible(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("bind_long_click")
fun setOnLongClickEvent(view: View, consumer: ViewClickConsumer) {
    RxView.longClicks(view)
            .subscribe { consumer.accept(view) }
}

@BindingAdapter("bind_first_click", "bind_throttle_time")
fun setOnClickEvent(view: View, consumer: ViewClickConsumer, time: Long?) {
    RxView.clicks(view)
            .throttleFirst(time ?: DEFAULT_THROTTLE_TIME, TimeUnit.MILLISECONDS)
            .subscribe { consumer.accept(view) }
}