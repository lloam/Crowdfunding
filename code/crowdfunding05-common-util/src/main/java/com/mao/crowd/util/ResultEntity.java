package com.mao.crowd.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: Administrator
 * Date: 2021/7/11 16:12
 * Description:
 */

/**
 * 统一整个项目中 Ajax 请求返回的结果（未来也可以用于分布式架构各个模块间调用时返回同一类型）
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultEntity<T> {

    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";


    // 用来封装当前请求处理的结果是成功还是失败
    private String result;

    // 请求处理失败时返回的错误消息
    private String message;

    // 要返回的数据
    private T data;

    /**
     * 请求处理成功且不需要返回数据时使用的工具方法，一般用于增删改
     * @param <Type> 第一个<Type>表示声明一个泛型Type，第二个和return中的<Type>表示使用该泛型
     * @return
     */
    public static <Type> ResultEntity<Type> successWithoutData() {
        return new ResultEntity<Type>(SUCCESS,null,null);
    }

    /**
     * 请求处理成功需要返回数据时使用的工具方法，一般用于查询
     * @param data  要返回的数据
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> successWithData(Type data) {
        return new ResultEntity<>(SUCCESS,null,data);
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
