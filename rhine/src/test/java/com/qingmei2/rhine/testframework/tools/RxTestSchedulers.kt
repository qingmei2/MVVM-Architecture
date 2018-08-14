package com.qingmei2.rhine.testframework.tools

import com.qingmei2.rhine.di.scheduler.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * Created by QingMei on 2017/11/17.
 * desc:
 */
class RxTestSchedulers : SchedulerProvider {

    override fun io(): Scheduler = Schedulers.io()

    override fun ui(): Scheduler = Schedulers.io()

}