package com.qingmei2.rhine.ext.arrow

import arrow.core.Option

inline fun <T> Option<T>.whenNotNull(consumer: (T) -> Unit) =
        fold({}, consumer)