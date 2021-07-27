package com.mao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Author: Administrator
 * Date: 2021/7/18 14:54
 * Description:
 */
// 将当前类标记为一个 SpringBoot 应用
@SpringBootApplication
public class SpringBootMainType {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMainType.class, args);
    }
}
