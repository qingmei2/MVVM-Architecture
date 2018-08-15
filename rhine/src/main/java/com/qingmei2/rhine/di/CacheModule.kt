package com.qingmei2.rhine.di

import io.rx_cache2.internal.RxCache
import io.victoralbertos.jolyglot.GsonSpeaker
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val CACHE_MODULE_TAG = "CacheModule"

val cacheModule = Kodein.Module(CACHE_MODULE_TAG) {
    bind<RxCache>() with singleton {
        RxCache.Builder()
                .persistence(instance(), GsonSpeaker())
    }
}