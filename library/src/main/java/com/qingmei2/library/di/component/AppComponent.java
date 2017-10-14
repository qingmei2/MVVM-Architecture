package com.qingmei2.library.di.component;

import com.google.gson.Gson;
import com.qingmei2.library.base.BaseApplication;
import com.qingmei2.library.di.module.ActivitiesModule;
import com.qingmei2.library.di.module.AppModule;
import com.qingmei2.library.di.module.CacheModule;
import com.qingmei2.library.di.module.GlobalConfigModule;
import com.qingmei2.library.di.module.HttpClientModule;
import com.qingmei2.library.di.module.ServiceModule;
import com.qingmei2.library.http.service.ServiceManager;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;
import okhttp3.OkHttpClient;

/**
 * Created by QingMei on 2017/8/14.
 * desc:
 */
@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        AppModule.class,
        HttpClientModule.class,
        GlobalConfigModule.class,
        ServiceModule.class,
        CacheModule.class,
        ActivitiesModule.class
})
public interface AppComponent {

    Gson gson();

    ServiceManager serviceManager();

    OkHttpClient okHttpClient();

    BaseApplication baseApplication();

    void inject(BaseApplication application);
}
