package com.mao.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Author: Administrator
 * Date: 2021/7/20 18:12
 * Description: 注册中心主启动类
 */
@EnableEurekaServer
@SpringBootApplication
public class CrowdMainEureka {
    public static void main(String[] args) {
        SpringApplication.run(CrowdMainEureka.class, args);
    }
}
