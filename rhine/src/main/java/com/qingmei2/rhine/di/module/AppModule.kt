package com.qingmei2.rhine.di.module


import com.qingmei2.rhine.base.BaseApplication

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

/**
 * Created by QingMei on 2017/8/14.
 * desc:
 */
@Module
class AppModule(private val application: BaseApplication) {

    @Singleton
    @Provides
    fun provideApplication(): BaseApplication {
        return application
    }

}
