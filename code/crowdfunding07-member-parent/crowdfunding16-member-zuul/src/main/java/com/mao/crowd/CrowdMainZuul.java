package com.mao.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Author: Administrator
 * Date: 2021/7/20 23:47
 * Description: zuul 路由网关主启动类
 */
// 启用 Zuul 的网关代理功能
@EnableZuulProxy
@SpringBootApplication
public class CrowdMainZuul {
    public static void main(String[] args) {
        SpringApplication.run(CrowdMainZuul.class, args);
    }
}
