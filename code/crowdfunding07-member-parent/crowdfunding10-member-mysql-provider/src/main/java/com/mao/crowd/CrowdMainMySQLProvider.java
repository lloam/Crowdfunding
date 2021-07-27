package com.mao.crowd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Author: Administrator
 * Date: 2021/7/20 18:47
 * Description: MySQL 服务提供者主启动类
 */
// 扫描 MyBatis 的 Mapper 接口所在的包
// @MapperScan("com.mao.crowd.mapper")
@SpringBootApplication
public class CrowdMainMySQLProvider {
    public static void main(String[] args) {
        SpringApplication.run(CrowdMainMySQLProvider.class, args);
    }
}
