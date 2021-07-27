package com.mao.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * Author: Administrator
 * Date: 2021/7/19 18:35
 * Description: 监控页面
 */
// 启用仪表盘监控功能
@EnableHystrixDashboard
@SpringBootApplication
public class HystrixDashboard {
    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboard.class, args);
    }
}
