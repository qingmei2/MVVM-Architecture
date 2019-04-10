package com.qingmei2.sample.utils

import com.google.gson.Gson
import com.qingmei2.sample.base.BaseApplication
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

object GsonUtils : KodeinAware {

    override val kodein: Kodein
        get() = BaseApplication.INSTANCE.kodein

    val INSTANCE: Gson by instance()
}

fun <T> T.toJson(): String {
    return GsonUtils.INSTANCE.toJson(this)
}

inline fun <reified T> String.fromJson(): T {
    return GsonUtils.INSTANCE.fromJson(this, T::class.java)
}