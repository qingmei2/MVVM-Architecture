package com.qingmei2.sample.utils

import com.qingmei2.sample.base.Results
import com.qingmei2.sample.http.Errors
import retrofit2.Response
import java.io.IOException

fun <T> processApiResponse(response: Response<T>): Results<T> {
    return try {
        val responseCode = response.code()
        val responseMessage = response.message()
        if (response.isSuccessful) {
            Results.success(response.body()!!)
        } else {
            Results.failure(Errors.NetworkError(responseCode, responseMessage))
        }
    } catch (e: IOException) {
        Results.failure(Errors.NetworkError())
    }
}