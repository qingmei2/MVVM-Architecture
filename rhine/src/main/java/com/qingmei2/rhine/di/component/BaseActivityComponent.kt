package com.qingmei2.rhine.di.component


import com.qingmei2.rhine.base.acitivty.BaseActivity

import dagger.Subcomponent
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

/**
 * Created by QingMei on 2017/8/14.
 * desc:
 */
@Subcomponent(modules = arrayOf(AndroidInjectionModule::class))
interface BaseActivityComponent : AndroidInjector<BaseActivity<*, *>> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<BaseActivity<*, *>>()

}
