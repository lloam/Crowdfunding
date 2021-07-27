package com.mao.crowd.exception;

/**
 * Author: Administrator
 * Date: 2021/7/13 15:58
 * Description: 角色已经存在异常
 */
public class RoleNameAlreadyExistException extends RuntimeException {


    private static final long serialVersionUID = 3694129185491894989L;

    public RoleNameAlreadyExistException() {
    }

    public RoleNameAlreadyExistException(String message) {
        super(message);
    }

    public RoleNameAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleNameAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public RoleNameAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
