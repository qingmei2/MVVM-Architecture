package com.qingmei2.rhine.ext.reactivex

import android.view.View
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun View.clicksThrottleFirst(): Observable<Unit> {
    return clicks().throttleFirst(500, TimeUnit.MILLISECONDS)
}