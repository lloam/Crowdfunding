package com.mao.spring.cloud.controller;

import com.mao.spring.cloud.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Author: Administrator
 * Date: 2021/7/19 10:01
 * Description:
 */
@RestController
public class HumanResourceController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("consumer/ribbon/get/employee")
    public Employee getEmployeeRemote() {

        // 1、声明远程微服务的主机地址家端口号
        // String host = "http://localhost:1000";

        // 将远程微服务调用地址从 “IP地址+端口号“ 改成为服务名称
        String host = "http://mao-server-provider";

        // 2、声明具体要调用的功能的 URL 地址
        String url = "/provider/get/employee/remote";

        // 3、通过 RestTemplate 调用远程微服务
        return restTemplate.getForObject(host+url,Employee.class);

    }
}
