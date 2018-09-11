package com.qingmei2.rhine.data.core

interface IThrowableDelegate {

    fun statusCode(): Int

    fun statusMessage(): String
}