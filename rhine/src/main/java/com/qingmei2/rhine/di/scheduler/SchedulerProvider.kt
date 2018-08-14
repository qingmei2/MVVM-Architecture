package com.qingmei2.rhine.di.scheduler

import io.reactivex.Scheduler

/**
 * Created by QingMei on 2017/11/17.
 * desc:
 */

interface SchedulerProvider {

    fun ui(): Scheduler

    fun io(): Scheduler
}
