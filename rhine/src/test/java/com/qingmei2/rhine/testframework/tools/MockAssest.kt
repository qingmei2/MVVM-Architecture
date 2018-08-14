package com.qingmei2.rhine.testframework.tools

import java.io.File

/**
 * Created by QingMei on 2017/11/6.
 * desc:
 */
object MockAssest {

    private val BASE_PATH = "app/src/test/java/cn/com/fenrir_inc/FireProtectionClient_HS/testframework/tools/data"
    val USER_DATA = BASE_PATH + "/userJson_test"

    fun readFile(path: String): String {
        val content = file2String(File(path))
        return content
    }

    fun file2String(f: File, charset: String = "UTF-8"): String {
        return f.readText(Charsets.UTF_8)
    }

}