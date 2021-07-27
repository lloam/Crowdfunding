package com.mao.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Author: Administrator
 * Date: 2021/7/19 9:51
 * Description:
 */
// 扫描 common 模块下的 feign 客户端接口，加载到 Spring 容器中
@EnableFeignClients(basePackages = "com.mao.spring.cloud.api")
@SpringBootApplication
public class FeignServerConsumer {

    public static void main(String[] args) {
        SpringApplication.run(FeignServerConsumer.class, args);
    }
}
