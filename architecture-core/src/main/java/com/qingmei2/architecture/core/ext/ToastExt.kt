package com.qingmei2.architecture.core.ext

import android.content.Context
import android.widget.Toast

fun Context.toast(value: String) = toast { value }

inline fun Context.toast(value: () -> String) =
        Toast.makeText(this, value(), Toast.LENGTH_SHORT).show()

