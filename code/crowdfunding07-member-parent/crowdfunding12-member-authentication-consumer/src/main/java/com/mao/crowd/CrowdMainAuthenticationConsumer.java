package com.mao.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Author: Administrator
 * Date: 2021/7/20 23:22
 * Description: 会员认证消费者主启动类
 */
// 启用 feign 客户端的功能，使项目能够扫描到 api 接口
@EnableFeignClients("com.mao.crowd.api")
@SpringBootApplication
public class CrowdMainAuthenticationConsumer {
    public static void main(String[] args) {
        SpringApplication.run(CrowdMainAuthenticationConsumer.class, args);
    }
}
