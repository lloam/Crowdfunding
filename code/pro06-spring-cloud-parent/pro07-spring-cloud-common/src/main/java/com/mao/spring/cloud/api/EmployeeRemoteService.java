package com.mao.spring.cloud.api;

import com.mao.spring.cloud.entity.Employee;
import com.mao.spring.cloud.factory.MyFallBackFactory;
import com.mao.spring.cloud.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Author: Administrator
 * Date: 2021/7/19 12:22
 * Description: feign 远程调用客户端
 */
// @FeignClient 这个注解表示当前接口和一个 Provider 对应，
//              注解中 value 属性指定要调用的微服务的名称
//              注解中 fallbackFactory 属性指定 Provider 不可用时提供备用方案的工厂类型
@FeignClient(value = "mao-server-provider",fallbackFactory = MyFallBackFactory.class)
public interface EmployeeRemoteService {

    // 远程调用的接口方法，
    // 要求 @GetMapping 注解映射的地址一致
    // 要求方法声明一致
    // 用来获取请求参数的 @RequestParam、@PathVariable、@RequestBody 不能省略，与服务提供端 controller 一致
    @GetMapping("/provider/get/employee/remote")
    public Employee getEmployeeRemote();

    @GetMapping("/provider/get/empList/remote")
    List<Employee> getEmpListRemote(@RequestParam("keyword") String keyword);

    @GetMapping("/provider/get/emp/with/circuit/breaker")
    public ResultEntity<Employee> getEmpWithCircuitBreaker(@RequestParam("signal") String signal);
}
