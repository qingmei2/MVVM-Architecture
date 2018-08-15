package com.qingmei2.rhine.http.base.error

class ExceptionHandle {

//    object ERROR {
//        val UNKNOWN = 1000
//
//        val PARSE_ERROR = 1001
//
//        val NETWORD_ERROR = 1002
//
//        val HTTP_ERROR = 1003
//
//        val SSL_ERROR = 1005
//    }
//
//    class ResponeThrowable(throwable: Throwable, var code: Int, var _message: String) : Exception(throwable) {
//
//        fun getMessage(): String {
//            return _message
//        }
//    }
//
//    inner class ServerException : RuntimeException() {
//        internal var code: Int = 0
//        internal var message: String? = null
//    }
//
//    companion object {
//        private val UNAUTHORIZED = 401
//        private val FORBIDDEN = 403
//        private val NOT_FOUND = 404
//        private val REQUEST_TIMEOUT = 408
//        private val INTERNAL_SERVER_ERROR = 500
//        private val BAD_GATEWAY = 502
//        private val SERVICE_UNAVAILABLE = 503
//        private val GATEWAY_TIMEOUT = 504
//
//        fun handleException(e: Throwable): ResponeThrowable {
//            val ex: ResponeThrowable
//            if (e is HttpException) {
//                ex = ResponeThrowable(e, ERROR.HTTP_ERROR, "服务器访问异常")
//                when (e.code()) {
//                    UNAUTHORIZED, FORBIDDEN, NOT_FOUND, REQUEST_TIMEOUT, GATEWAY_TIMEOUT, INTERNAL_SERVER_ERROR, BAD_GATEWAY, SERVICE_UNAVAILABLE -> ex.code = e.code()
//                    else -> ex.code = e.code()
//                }
//                return ex
//            } else if (e is ServerException) {
//                ex = ResponeThrowable(e, e.code, e.message!!)
//                return ex
//            } else if (e is JsonParseException
//                    || e is JSONException
//                    || e is ParseException) {
//                ex = ResponeThrowable(e, ERROR.PARSE_ERROR, "Parse异常")
//                return ex
//            } else if (e is ConnectException) {
//                ex = ResponeThrowable(e, ERROR.NETWORD_ERROR, "网络连接异常")
//                return ex
//            } else if (e is javax.net.ssl.SSLHandshakeException) {
//                ex = ResponeThrowable(e, ERROR.SSL_ERROR, "证书异常")
//                return ex
//            } else {
//                ex = ResponeThrowable(e, ERROR.UNKNOWN, "未知错误")
//                return ex
//            }
//        }
//    }
}
