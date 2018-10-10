package com.qingmei2.sample.utils

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

object TimeConverter {

    fun eventTimeToString(eventTime: String?): String {
        val timestamp = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
                .parse(eventTime).time
        return DateUtils.getRelativeTimeSpanString(timestamp).toString()
    }
}