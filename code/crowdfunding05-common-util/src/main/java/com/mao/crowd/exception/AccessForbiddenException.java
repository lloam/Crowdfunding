package com.mao.crowd.exception;

/**
 * Author: Administrator
 * Date: 2021/7/11 22:10
 * Description: 请求被拦截，未登录异常
 */
public class AccessForbiddenException extends RuntimeException {

    private static final long serialVersionUID = 2L;

    public AccessForbiddenException() {
    }

    public AccessForbiddenException(String message) {
        super(message);
    }

    public AccessForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessForbiddenException(Throwable cause) {
        super(cause);
    }

    public AccessForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
