package com.qingmei2.rhine.http.cache

import com.qingmei2.rhine.http.entity.QueryUser
import io.reactivex.Maybe
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictDynamicKey
import io.rx_cache2.LifeCache
import java.util.concurrent.TimeUnit

interface UserInfoCacheProviders {

    @LifeCache(duration = 10, timeUnit = TimeUnit.SECONDS)
    fun getUserInfo(
            userinfo: Maybe<QueryUser>,
            dynamicKey: DynamicKey,
            evictDynamicKey: EvictDynamicKey
    ): Maybe<QueryUser>
}
