package com.qingmei2.rhine.di.module;

import com.google.gson.Gson;
import com.qingmei2.rhine.http.base.interceptor.RequestInterceptor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by QingMei on 2017/8/15.
 * desc:
 */
@Module
public class HttpClientModule {

    private static final int TIME_OUT_SECONDS = 20;

    public HttpClientModule() {
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(Retrofit.Builder builder, OkHttpClient client, HttpUrl httpUrl) {
        return builder
                .baseUrl(httpUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    OkHttpClient provideClient(OkHttpClient.Builder okHttpClient, Interceptor intercept
            , List<Interceptor> interceptors) {
        OkHttpClient.Builder builder = okHttpClient
                .connectTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .addNetworkInterceptor(intercept);
        if (interceptors != null && interceptors.size() > 0) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }
        return builder.build();
    }

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideClientBuilder() {
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    Interceptor provideIntercept(RequestInterceptor interceptor) {
        return interceptor;
    }

    @Singleton
    @Provides
    public Gson provideGson() {
        return new Gson();
    }

}
