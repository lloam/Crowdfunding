package com.mao.crowd.exception;

/**
 * Author: Administrator
 * Date: 2021/7/12 20:02
 * Description: 更新管理员异常
 */
public class LoginAcctAlreadyInUseForUpdateException extends RuntimeException {


    private static final long serialVersionUID = 2435161736251549448L;

    public LoginAcctAlreadyInUseForUpdateException() {
    }

    public LoginAcctAlreadyInUseForUpdateException(String message) {
        super(message);
    }

    public LoginAcctAlreadyInUseForUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyInUseForUpdateException(Throwable cause) {
        super(cause);
    }

    public LoginAcctAlreadyInUseForUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
