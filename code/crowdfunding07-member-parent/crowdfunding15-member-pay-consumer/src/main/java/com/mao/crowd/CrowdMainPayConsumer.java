package com.mao.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Author: Administrator
 * Date: 2021/7/27 16:06
 * Description:
 */
@EnableFeignClients("com.mao.crowd.api")
@SpringBootApplication
public class CrowdMainPayConsumer {
    public static void main(String[] args) {
        SpringApplication.run(CrowdMainPayConsumer.class, args);
    }
}
