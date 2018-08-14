package com.qingmei2.rhine.di.module;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;

/**
 * Created by QingMei on 2017/9/1.
 * desc:
 */
@Module
public class CacheModule {

    private final File cacheDir;

    public CacheModule(File cacheDir) {
        this.cacheDir = cacheDir;
    }

    @Provides
    @Singleton
    RxCache provideRxCache() {
        return new RxCache.Builder()
                .persistence(cacheDir, new GsonSpeaker());
    }

}
