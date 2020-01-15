package com.qingmei2.sample.utils

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.qingmei2.sample.http.Errors
import retrofit2.Response
import java.io.IOException

fun <T> processApiResponse(response: Response<T>): Either<Errors, T> {
    return try {
        val responseCode = response.code()
        val responseMessage = response.message()
        if (response.isSuccessful) {
            response.body()!!.right()
        } else {
            Errors.NetworkError(responseCode, responseMessage).left()
        }
    } catch (e: IOException) {
        Either.left(Errors.NetworkError())
    }
}