package com.qingmei2.rhine.http.cache


import com.qingmei2.rhine.http.entity.UserInfo

import java.util.concurrent.TimeUnit

import io.reactivex.Maybe
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictDynamicKey
import io.rx_cache2.LifeCache


/**
 * Created by QingMei on 2017/8/17.
 * desc:UserInfo缓存
 */

interface UserInfoCacheProviders {

    @LifeCache(duration = 10, timeUnit = TimeUnit.SECONDS)
    fun getUserInfo(
            userInfoObservable: Maybe<UserInfo>,
            dynamicKey: DynamicKey,
            evictDynamicKey: EvictDynamicKey
    ): Maybe<UserInfo>

}
