package com.mao.spring.cloud.controller;

import com.mao.spring.cloud.api.EmployeeRemoteService;
import com.mao.spring.cloud.entity.Employee;
import com.mao.spring.cloud.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Author: Administrator
 * Date: 2021/7/19 10:01
 * Description:
 */
@RestController
public class FeignHumanResourceController {

    // 装配调用远程微服务的接口，后面向调用本地方法一样直接使用
    @Autowired
    private EmployeeRemoteService employeeRemoteService;

    @GetMapping("/feign/consumer/get/employee")
    public Employee getEmployeeRemote() {
        return employeeRemoteService.getEmployeeRemote();
    }


    @GetMapping("/feign/consumer/get/empList")
    public List<Employee> getEmpListRemote(String keyword) {
        return employeeRemoteService.getEmpListRemote(keyword);
    }


    @GetMapping("/feign/consumer/test/fallback")
    public ResultEntity<Employee> testFallBack(@RequestParam("signal") String signal) {
        return employeeRemoteService.getEmpWithCircuitBreaker(signal);
    }
}
