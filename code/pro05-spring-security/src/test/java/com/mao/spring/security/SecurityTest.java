package com.mao.spring.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Author: Administrator
 * Date: 2021/7/17 11:31
 * Description:
 */
public class SecurityTest {


    public static void main(String[] args) {

        // 1、创建 BCryptPasswordEncoder 对象
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // 2、准备明文字符串
        String rawPassword = "999999";

        // 3、加密
        String encode = passwordEncoder.encode(rawPassword);
        System.out.println(encode);
        // $2a$10$tMxzfh3G7S/xCEaUT2R1k.kA1/ugbWbB0UFdJ1Rm.ktJMynck3PGe
        // $2a$10$UdTnV5u6ffWTU9eCUT06W.6MBxw8W0T28N7dfyK0NtGmxDvWhPLxi
        // $2a$10$W1qhHl5YkmMQ.9T4BJqDHesWyjDJHtxXPDhrYS3F3R5oowJjRfvzq
        // $2a$10$NC/Yy18rfCEnks5drzDjGe5uiSGJ5FS7eQ7JgMg/j7IIZHFUv0nOK
        // $2a$10$EikjunzYSgF1HJAxbX5HTuxHYYTo682sOG2GKWy/f8t.go61OD0mK

    }
}


class EncodeTest {

    public static void main(String[] args) {

        // 1、准备明文字符串
        String rawPassword = "123123";

        // 2、准备密文字符串
        String encodedPassword = "$2a$10$EikjunzYSgF1HJAxbX5HTuxHYYTo682sOG2GKWy/f8t.go61OD0mK";

        // 3、创建 BCryptPasswordEncoder
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // 4、比较
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);

        System.out.println(matches ? "一致" : "不一致");
    }
}
