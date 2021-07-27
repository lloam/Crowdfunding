package com.mao.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Author: Administrator
 * Date: 2021/7/22 17:39
 * Description:
 */
@EnableFeignClients("com.mao.crowd.api")
@SpringBootApplication
public class CrowdMainProjectConsumer {
    public static void main(String[] args) {
        SpringApplication.run(CrowdMainProjectConsumer.class, args);
    }
}
