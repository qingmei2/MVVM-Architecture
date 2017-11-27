package com.qingmei2.library.di.scheduler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by QingMei on 2017/11/17.
 * desc:
 */
@Module
public class SchedulersModule {

    public SchedulersModule() {
    }

    @Singleton
    @Provides
    SchedulerProvider providerSchedulers() {
        return new AppSchedulers();
    }
}
