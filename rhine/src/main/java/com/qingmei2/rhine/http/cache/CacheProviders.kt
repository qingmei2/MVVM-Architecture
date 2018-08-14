package com.qingmei2.rhine.http.cache

import javax.inject.Inject
import javax.inject.Singleton

import io.rx_cache2.internal.RxCache
import lombok.Getter

@Singleton
class CacheProviders @Inject
constructor(rxCache: RxCache) {

    @Getter
    private val userInfoCacheProviders: UserInfoCacheProviders

    init {
        this.userInfoCacheProviders = rxCache.using(UserInfoCacheProviders::class.java)
    }

}
