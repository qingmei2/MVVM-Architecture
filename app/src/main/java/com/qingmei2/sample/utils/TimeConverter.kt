package com.qingmei2.sample.utils

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

object TimeConverter {

    /**
     * 2018-08-13T08:51:43Z -> 2 days ago
     */
    fun tramsTimeAgo(time: String?): String =
            transTimeStamp(time).let {
                DateUtils.getRelativeTimeSpanString(it).toString()
            }

    /**
     * 2018-08-13T08:51:43Z -> time stamp
     */
    fun transTimeStamp(time: String?): Long =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
                    .let {
                        it.timeZone = TimeZone.getTimeZone("GMT+1")
                        it.parse(time).time
                    }
}