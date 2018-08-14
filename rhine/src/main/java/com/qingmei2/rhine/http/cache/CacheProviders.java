package com.qingmei2.rhine.http.cache;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.rx_cache2.internal.RxCache;
import lombok.Getter;

@Singleton
public class CacheProviders {

    @Getter
    private UserInfoCacheProviders userInfoCacheProviders;

    @Inject
    public CacheProviders(RxCache rxCache) {
        this.userInfoCacheProviders = rxCache.using(UserInfoCacheProviders.class);
    }

}
