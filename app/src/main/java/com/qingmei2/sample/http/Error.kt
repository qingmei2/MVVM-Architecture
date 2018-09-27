package com.qingmei2.sample.http

import com.github.qingmei2.core.GlobalErrorTransformer

fun <T> globalErrorTransformer(): GlobalErrorTransformer<T> = GlobalErrorTransformer()