package com.qingmei2.sample.utils

import com.google.gson.reflect.TypeToken
import java.io.*

object JsonFileReader {

    private val DEFAULT_FILE_PATH = "${File.separator}src${File.separator}test${File.separator}assets${File.separator}json${File.separator}"

    fun read(fileName: String): String {
        var jsonStr = ""
        try {
            val jsonFile = File(File("").absolutePath + DEFAULT_FILE_PATH + fileName)

            val fileReader = FileReader(jsonFile)
            val reader: Reader = InputStreamReader(FileInputStream(jsonFile), "utf-8")
            var ch = 0
            val sb = StringBuffer()
            while (reader.read().also { ch = it } != -1) {
                sb.append(ch.toChar())
            }
            fileReader.close()
            reader.close()
            jsonStr = sb.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return jsonStr
    }
}

const val REPO_LIST_SIZE10 = "repo_list_qingmei2_size10.json"

inline fun <reified T> readLocalJson(fileName: String): T {
    val json = JsonFileReader.read(fileName)
    val type = object : TypeToken<T>() {}.type
    return GsonUtils.INSTANCE.fromJson(json, type)
}
