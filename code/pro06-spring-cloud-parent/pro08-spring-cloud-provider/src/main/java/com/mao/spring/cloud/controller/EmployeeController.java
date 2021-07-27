package com.mao.spring.cloud.controller;

import com.mao.spring.cloud.entity.Employee;
import com.mao.spring.cloud.util.ResultEntity;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Administrator
 * Date: 2021/7/19 9:53
 * Description:
 */
@RestController
public class EmployeeController {


    private Logger logger = LoggerFactory.getLogger(EmployeeController.class);

//    @GetMapping("/provider/get/employee/remote")
//    public Employee getEmployeeRemote(HttpServletRequest request) {
//
//        // 获取当前服务的端口号
//        int serverPort = request.getServerPort();
//
//        return new Employee(555,"tom555" + serverPort,555.55);
//    }


    @GetMapping("/provider/get/employee/remote")
    public Employee getEmployeeRemote() {

        return new Employee(555,"tom555",555.55);
    }

    @GetMapping("/provider/get/empList/remote")
    List<Employee> getEmpListRemote(@RequestParam("keyword") String keyword) {

        logger.info("keyword="+keyword);

        List<Employee> empList = new ArrayList<>();

        empList.add(new Employee(33,"tom33",333.33));
        empList.add(new Employee(44,"tom44",444.44));
        empList.add(new Employee(55,"tom55",555.55));

        return empList;
    }

    //  @HystrixCommand 注解指定当前方法出问题时调用的备份方法（使用 fallbackMethod 属性指定的方法）
    @HystrixCommand(fallbackMethod = "getEmpWithCircuitBreakerBackup")
    @GetMapping("/provider/get/emp/with/circuit/breaker")
    public ResultEntity<Employee> getEmpWithCircuitBreaker(@RequestParam("signal") String signal) throws InterruptedException {

        if("quick-bang".equals(signal)) {
            throw new RuntimeException();
        }

        if("slow-bang".equals(signal)) {
            Thread.sleep(5000);
        }

        return ResultEntity.successWithData(new Employee(666,"emp666",666.66));
    }

    public ResultEntity<Employee> getEmpWithCircuitBreakerBackup(@RequestParam("signal") String signal) {

        String message = "方法执行出现问题，执行断路="+signal;

        return ResultEntity.failed(message);
    }
}
