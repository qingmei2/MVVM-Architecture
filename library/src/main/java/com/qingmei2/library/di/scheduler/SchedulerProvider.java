package com.qingmei2.library.di.scheduler;

import io.reactivex.Scheduler;

/**
 * Created by QingMei on 2017/11/17.
 * desc:
 */

public interface SchedulerProvider {

    Scheduler ui();

    Scheduler io();
}
