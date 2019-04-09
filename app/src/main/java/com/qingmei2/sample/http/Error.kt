package com.qingmei2.sample.http

import com.github.qingmei2.core.GlobalErrorTransformer
import com.qingmei2.sample.utils.toast
import retrofit2.HttpException

fun <T> globalHandleError(): GlobalErrorTransformer<T> = GlobalErrorTransformer(
        globalDoOnErrorConsumer = { error ->
            when (error) {
                is HttpException -> {
                    when (error.code()) {
                        401 -> toast { "401 Unauthorized" }
                        404 -> toast { "404 failure" }
                        500 -> toast { "500 server failure" }
                        else -> toast { "network failure" }
                    }
                }
                else -> toast { "network failure" }
            }
        }
)