package com.qingmei2.rhine.http.base.error;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.text.ParseException;

import retrofit2.adapter.rxjava2.HttpException;


/**
 * Created by QingMei on 2017/6/19.
 * desc:
 */

public class ExceptionHandle {
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ResponeThrowable handleException(Throwable e) {
        ResponeThrowable ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponeThrowable(e, ERROR.HTTP_ERROR, "服务器访问异常");
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.code = httpException.code();
                    break;
            }
            return ex;
        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ResponeThrowable(resultException, resultException.code, resultException.message);
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ResponeThrowable(e, ERROR.PARSE_ERROR, "Parse异常");
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ResponeThrowable(e, ERROR.NETWORD_ERROR, "网络连接异常");
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ResponeThrowable(e, ERROR.SSL_ERROR, "证书异常");
            return ex;
        } else {
            ex = new ResponeThrowable(e, ERROR.UNKNOWN, "未知错误");
            return ex;
        }
    }

    public class ERROR {
        public static final int UNKNOWN = 1000;

        public static final int PARSE_ERROR = 1001;

        public static final int NETWORD_ERROR = 1002;

        public static final int HTTP_ERROR = 1003;

        public static final int SSL_ERROR = 1005;
    }

    public static class ResponeThrowable extends Exception {
        public int code;
        public String message;

        public int getCode() {
            return code;
        }

        @Override
        public String getMessage() {
            return message;
        }

        public ResponeThrowable(Throwable throwable, int code, String message) {
            super(throwable);
            this.code = code;
            this.message = message;
        }
    }

    public class ServerException extends RuntimeException {
        int code;
        String message;
    }
}
