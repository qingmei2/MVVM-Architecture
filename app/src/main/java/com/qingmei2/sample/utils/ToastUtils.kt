package com.qingmei2.sample.utils

import com.qingmei2.architecture.core.ext.toast
import com.qingmei2.sample.base.BaseApplication

inline fun toast(value: () -> String): Unit =
        BaseApplication.INSTANCE.toast(value)