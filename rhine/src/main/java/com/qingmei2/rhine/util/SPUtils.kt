package com.qingmei2.rhine.util

import android.content.Context
import android.content.SharedPreferences
import com.qingmei2.rhine.SP_NAME_DEFAULT
import com.qingmei2.rhine.base.RhineApplication
import java.util.*

class SPUtils private constructor(spName: String) {

    private val sp: SharedPreferences

    val all: Map<String, *>
        get() = sp.all

    init {
        sp = RhineApplication.INSTANCE.getSharedPreferences(spName, Context.MODE_PRIVATE)
    }

    fun put(key: String, value: String) {
        sp.edit().putString(key, value).apply()
    }

    @JvmOverloads
    fun getString(key: String, defaultValue: String = ""): String {
        return sp.getString(key, defaultValue)
    }

    fun put(key: String, value: Int) {
        sp.edit().putInt(key, value).apply()
    }

    @JvmOverloads
    fun getInt(key: String, defaultValue: Int = -1): Int {
        return sp.getInt(key, defaultValue)
    }

    fun put(key: String, value: Long) {
        sp.edit().putLong(key, value).apply()
    }

    @JvmOverloads
    fun getLong(key: String, defaultValue: Long = -1L): Long {
        return sp.getLong(key, defaultValue)
    }

    fun put(key: String, value: Float) {
        sp.edit().putFloat(key, value).apply()
    }

    @JvmOverloads
    fun getFloat(key: String, defaultValue: Float = -1f): Float {
        return sp.getFloat(key, defaultValue)
    }

    fun put(key: String, value: Boolean) {
        sp.edit().putBoolean(key, value).apply()
    }

    @JvmOverloads
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return sp.getBoolean(key, defaultValue)
    }

    fun put(key: String, values: Set<String>) {
        sp.edit().putStringSet(key, values).apply()
    }

    @JvmOverloads
    fun getStringSet(key: String, defaultValue: Set<String> = emptySet()): Set<String> {
        return sp.getStringSet(key, defaultValue)
    }

    operator fun contains(key: String): Boolean {
        return sp.contains(key)
    }

    fun remove(key: String) {
        sp.edit().remove(key).apply()
    }

    fun clear() {
        sp.edit().clear().apply()
    }

    companion object {

        private val sPMap = HashMap<String, SPUtils>()

        val instance: SPUtils
            get() = getInstance("")

        fun getInstance(spName: String): SPUtils {
            var spName = spName
            if (isSpace(spName)) spName = SP_NAME_DEFAULT
            var sp: SPUtils? = sPMap[spName]
            if (sp == null) {
                sp = SPUtils(spName)
                sPMap[spName] = sp
            }
            return sp
        }

        private fun isSpace(s: String?): Boolean {
            if (s == null) return true
            var i = 0
            val len = s.length
            while (i < len) {
                if (!Character.isWhitespace(s[i])) {
                    return false
                }
                ++i
            }
            return true
        }
    }

}
