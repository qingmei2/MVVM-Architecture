package com.qingmei2.architecture.core.ext

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.jumpBrowser(url: String) {
    val uri = Uri.parse(url)
    Intent(Intent.ACTION_VIEW, uri).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(this)
    }
}