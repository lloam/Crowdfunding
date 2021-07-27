package com.mao.security.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Objects;

/**
 * Author: Administrator
 * Date: 2021/7/17 11:09
 * Description: 自定义密码加密
 */
@Component
public class MyPasswordEncoder implements PasswordEncoder {


    /**
     * 加密
     * @param rawPassword
     * @return
     */
    public String encode(CharSequence rawPassword) {
        return privateEncode(rawPassword);
    }


    /**
     *
     * @param rawPassword
     * @param encodePassword
     * @return
     */
    public boolean matches(CharSequence rawPassword, String encodePassword) {

        // 1、姜明文密码吗进行加密
        String formPassword = privateEncode(rawPassword);

        // 2、声明数据库密码
        String databasePassword = encodePassword;

        // 3、比较
        return Objects.equals(formPassword, databasePassword);
    }


    /**
     * 将加密方法封装在一个方法中
     * @param rawPassword
     * @return
     */
    private String privateEncode(CharSequence rawPassword) {

        try {
            // 1、创建 MessageDigest 对象
            String algorithm = "MD5";
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

            // 2、获取 rawPassword 字节数组
            byte[] input = ((String) rawPassword).getBytes();

            // 3、加密
            byte[] output = messageDigest.digest(input);

            // 4、转换为 16 进制的对应的字符
            String encoded = new BigInteger(1, output).toString(16).toUpperCase();

            return encoded;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {
        String encoded = new MyPasswordEncoder().privateEncode("123123");
        System.out.println(encoded);
    }
}
