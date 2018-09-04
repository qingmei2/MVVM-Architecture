package com.qingmei2.rhine.util.prefs

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

inline fun <reified T> SharedPreferences.gson(defaultValue: T, key: String? = null) =
        object : ReadWriteProperty<Any, T> {
            private val gson = Gson()

            override fun getValue(thisRef: Any, property: KProperty<*>): T {

                val s = getString(key ?: property.name, "")

                return if (s.isBlank()) defaultValue else gson.fromJson(s, T::class.java)
            }

            override fun setValue(thisRef: Any, property: KProperty<*>, value: T) =
                    edit().putString(key ?: property.name, gson.toJson(value)).apply()
        }

inline fun <reified T> SharedPreferences.gsonList(key: String? = null) =
        object : ReadWriteProperty<Any, List<T>> {

            private val gson = Gson()

            override fun getValue(thisRef: Any, property: KProperty<*>): List<T> {

                val s = getString(key ?: property.name, "")

                return if (s.isBlank()) emptyList() else gson.fromJson<List<T>>(s, object : TypeToken<List<T>>() {}.type)
            }

            override fun setValue(thisRef: Any, property: KProperty<*>, value: List<T>) =
                    edit().putString(key ?: property.name, gson.toJson(value)).apply()
        }