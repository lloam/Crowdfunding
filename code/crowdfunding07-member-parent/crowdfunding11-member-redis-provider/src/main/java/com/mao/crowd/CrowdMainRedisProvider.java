package com.mao.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Author: Administrator
 * Date: 2021/7/20 22:20
 * Description: Redis 服务提供者主启动类
 */
@SpringBootApplication
public class CrowdMainRedisProvider {
    public static void main(String[] args) {
        SpringApplication.run(CrowdMainRedisProvider.class, args);
    }
}
