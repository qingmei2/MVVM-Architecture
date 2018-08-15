package com.qingmei2.rhine.di

import com.qingmei2.rxschedulers.RxSchedulerProvider
import com.qingmei2.rxschedulers.SchedulerProvider
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

const val RX_MODULE_TAG = "rxModule"

val rxModule = Kodein.Module(RX_MODULE_TAG) {

    bind<SchedulerProvider>() with singleton {
        RxSchedulerProvider.INSTANCE
    }
}