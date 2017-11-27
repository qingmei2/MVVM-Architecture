package com.qingmei2.library.testframework.tools

import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by QingMei on 2017/11/6.
 * desc:
 */
class MockRetrofit {

    var path: String = ""

    fun <T> create(clazz: Class<T>): T {

        val client = OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain ->
                    val content = MockAssest.readFile(path)
                    val body = ResponseBody.create(MediaType.parse("application/x-www-form-urlencoded"), content)
                    val response = Response.Builder()
                            .request(chain.request())
                            .protocol(Protocol.HTTP_1_1)
                            .code(200)
                            .body(body)
                            .message("Test Message")
                            .build()
                    response
                }).build()

        val retrofit = Retrofit.Builder()
                .baseUrl("http://api.***.com")
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(clazz)
    }
}


