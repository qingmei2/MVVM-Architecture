package com.qingmei2.sample.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

private const val SERIALIZABLE_MODULE_TAG = "SerializableModule"

val serializableModule = Kodein.Module(SERIALIZABLE_MODULE_TAG) {

    bind<Gson>() with singleton {
        GsonBuilder().create()
    }
}