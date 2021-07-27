package com.mao.spring.cloud;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

/**
 * Author: Administrator
 * Date: 2021/7/19 9:51
 * Description:
 */
// 下面两个注解功能大致相同
// @EnableDiscoveryClient            启用发现服务的功能，不局限于 Eureka 注册中心
// @EnableEurekaClient               启用 Eureka 客户端功能，必须是 Eureka 注册中心

// 使用 @EnableCircuitBreaker 注解开启断路器功能
@EnableCircuitBreaker

@SpringBootApplication
public class ServerProvider {

    public static void main(String[] args) {
        SpringApplication.run(ServerProvider.class, args);
    }

    //增加一个 Servlet
    @Bean
    public ServletRegistrationBean hystrixMetricsStreamServlet(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new HystrixMetricsStreamServlet());
        //访问该页面就是监控页面
        registrationBean.addUrlMappings("/actuator/hystrix.stream");
        return registrationBean;
    }
}
