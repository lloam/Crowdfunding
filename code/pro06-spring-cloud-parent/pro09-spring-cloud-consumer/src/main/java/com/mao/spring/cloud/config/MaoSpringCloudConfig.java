package com.mao.spring.cloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Author: Administrator
 * Date: 2021/7/19 9:59
 * Description:
 */
@Configuration
public class MaoSpringCloudConfig {


    // 这个注解让 RestTemplate 有负载均衡的功能，通过 Ribbon 访问 服务端集群
    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
