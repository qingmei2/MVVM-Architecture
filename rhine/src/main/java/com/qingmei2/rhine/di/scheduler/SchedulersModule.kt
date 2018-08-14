package com.qingmei2.rhine.di.scheduler

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

/**
 * Created by QingMei on 2017/11/17.
 * desc:
 */
@Module
class SchedulersModule {

    @Singleton
    @Provides
    internal fun providerSchedulers(): SchedulerProvider {
        return AppSchedulers()
    }
}
