package com.qingmei2.sample.utils

import com.google.gson.Gson

object GsonUtils {

    val INSTANCE: Gson = Gson()
}

fun <T> T.toJson(): String {
    return GsonUtils.INSTANCE.toJson(this)
}

inline fun <reified T> String.fromJson(): T {
    return GsonUtils.INSTANCE.fromJson(this, T::class.java)
}