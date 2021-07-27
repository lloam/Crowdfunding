package com.mao.crowd.exception;

/**
 * Author: Administrator
 * Date: 2021/7/12 18:09
 * Description: 保存或更新 Admin 时如果检测到登录账号重复抛出这个异常
 */
public class LoginAcctAlreadyInUseException extends RuntimeException {


    private static final long serialVersionUID = 2205720330015709076L;

    public LoginAcctAlreadyInUseException() {
    }

    public LoginAcctAlreadyInUseException(String message) {
        super(message);
    }

    public LoginAcctAlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyInUseException(Throwable cause) {
        super(cause);
    }

    public LoginAcctAlreadyInUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
