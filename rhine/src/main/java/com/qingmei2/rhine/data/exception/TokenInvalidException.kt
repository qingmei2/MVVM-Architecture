package com.qingmei2.rhine.data.exception

import com.qingmei2.rhine.data.core.ThrowableDelegate

class TokenInvalidException(
        statusCode: Int,
        errorMessage: String
) : ThrowableDelegate(statusCode, errorMessage)
