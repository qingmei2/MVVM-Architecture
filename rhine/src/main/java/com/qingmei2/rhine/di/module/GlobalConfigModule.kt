package com.qingmei2.rhine.di.module

import android.text.TextUtils

import com.qingmei2.rhine.http.base.interceptor.HttpRequestHandler

import java.io.File
import java.util.ArrayList

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.Interceptor


/**
 * Created by Glooory on 17/5/15.
 */
@Module
class GlobalConfigModule
/**
 * @author: jess
 * @date 8/5/16 11:03 AM
 * @description: baseurl
 */
private constructor(buidler: Buidler) {

    private val mApiUrl: HttpUrl?
    private val mInterceptors: List<Interceptor>
    private val mCacheFile: File?
    private val mHandler: HttpRequestHandler?

    init {
        this.mApiUrl = buidler.apiUrl
        this.mHandler = buidler.handler
        this.mInterceptors = buidler.interceptors
        this.mCacheFile = buidler.cacheFile
    }

    @Singleton
    @Provides
    internal fun provideInterceptors(): List<Interceptor> {
        return mInterceptors
    }

    @Singleton
    @Provides
    internal fun provideBaseUrl(): HttpUrl? {
        return mApiUrl
    }

    @Singleton
    @Provides
    internal fun provideHttpRequestHandler(): HttpRequestHandler {
        return mHandler ?: HttpRequestHandler.EMPTY//打印请求信息
    }

    class Buidler private constructor() {
        private var apiUrl = HttpUrl.parse("https://api.github.com/")
        private val interceptors = ArrayList<Interceptor>()
        private var handler: HttpRequestHandler? = null
        private var cacheFile: File? = null

        fun baseurl(baseurl: String): Buidler {
            if (TextUtils.isEmpty(baseurl)) {
                throw IllegalArgumentException("baseurl can not be empty")
            }
            this.apiUrl = HttpUrl.parse(baseurl)
            return this
        }

        fun globeHttpHandler(handler: HttpRequestHandler): Buidler {// handle the http response before displaying it to users
            this.handler = handler
            return this
        }

        fun addInterceptor(interceptor: Interceptor): Buidler {
            this.interceptors.add(interceptor)
            return this
        }

        fun cacheFile(cacheFile: File): Buidler {
            this.cacheFile = cacheFile
            return this
        }

        fun build(): GlobalConfigModule {
            return GlobalConfigModule(this)
        }
    }

    companion object {

        fun buidler(): Buidler {
            return Buidler()
        }
    }

}
