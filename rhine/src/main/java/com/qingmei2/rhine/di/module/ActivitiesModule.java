package com.qingmei2.rhine.di.module;


import com.qingmei2.rhine.di.component.BaseActivityComponent;
import com.qingmei2.rhine.di.module.activity.HomeActivityModule;
import com.qingmei2.rhine.di.scope.ActivityScope;
import com.qingmei2.rhine.ui.home.HomeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by QingMei on 2017/8/16.
 * desc:
 */
@Module(subcomponents = {
        BaseActivityComponent.class
})
public abstract class ActivitiesModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = HomeActivityModule.class)
    abstract HomeActivity contributeHomeActivitytInjector();

}
