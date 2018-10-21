package com.qingmei2.rhine.ext

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.jumpBrowser(url: String) =
        Uri.parse(url).run {
            Intent(Intent.ACTION_VIEW, this)
        }.also { intent ->
            startActivity(intent)
        }