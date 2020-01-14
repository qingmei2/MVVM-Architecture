package com.qingmei2.sample.entity

import com.qingmei2.sample.http.Errors

// A generic class that contains data and status about loading this data.
sealed class Resource<T>(
        val data: T? = null,
        val error: Errors? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class DataError<T>(error: Errors) : Resource<T>(null, error)
}