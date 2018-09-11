package com.qingmei2.rhine.data.core

open class ThrowableDelegate(
        private val customStatusCode: Int,
        private val customErrorMessage: String
) : Throwable(), IThrowableDelegate {

    override fun statusCode(): Int {
        return customStatusCode
    }

    override fun statusMessage(): String {
        return customErrorMessage
    }

    companion object {

        val EMPTY = ThrowableDelegate(0, "")
    }
}