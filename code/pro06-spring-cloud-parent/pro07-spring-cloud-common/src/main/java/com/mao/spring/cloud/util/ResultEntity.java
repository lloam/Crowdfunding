package com.mao.spring.cloud.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: Administrator
 * Date: 2021/7/19 15:03
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultEntity<T> {

    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";
    public static final String NO_MESSAGE = "NO_MESSAGE";
    public static final String NO_DATA = "NO_DATA";

    // 用来封装当前请求处理的结果是成功还是失败
    private String result;

    // 请求处理失败时返回的错误消息
    private String message;

    // 要返回的数据
    private T data;

    /**
     * 请求处理成功且不需要返回数据时使用的工具方法，一般用于增删改
     * @return
     */
    public static ResultEntity<String> successWithoutData() {
        return new ResultEntity<String>(SUCCESS,NO_MESSAGE,NO_DATA);
    }

    /**
     * 请求处理成功需要返回数据时使用的工具方法，一般用于查询
     * @param data  要返回的数据
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> successWithData(Type data) {
        return new ResultEntity<>(SUCCESS,NO_MESSAGE,data);
    }

    /**
     * 请求处理失败后使用的工具方法
     * @param message  失败的错误信息
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> failed(String message) {
        return new ResultEntity<Type>(FAILED,message,null);
    }
}
