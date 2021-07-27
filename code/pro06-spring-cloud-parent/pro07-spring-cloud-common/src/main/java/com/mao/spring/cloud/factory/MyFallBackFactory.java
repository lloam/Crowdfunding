package com.mao.spring.cloud.factory;

import com.mao.spring.cloud.api.EmployeeRemoteService;
import com.mao.spring.cloud.entity.Employee;
import com.mao.spring.cloud.util.ResultEntity;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author: Administrator
 * Date: 2021/7/19 15:32
 * Description:
 * 1、实现 Consumer 端服务降级的功能
 * 2、实现 FallBackFactory 接口时要传入 @FeignClient 注解标记的接口类型
 * 3、在 create() 方法中返回 @FeignClient 注解标记的接口类型的对象，当 Provider 调用失败后，会调用这个对象对应的方法
 * 4、这个类必须使用 @Component 注解将当前类的对象加入 IOC 容器，当前类必须能够被扫描到
 */
@Component
public class MyFallBackFactory implements FallbackFactory<EmployeeRemoteService> {


    public EmployeeRemoteService create(Throwable throwable) {
        return new EmployeeRemoteService() {
            @Override
            public Employee getEmployeeRemote() {
                return null;
            }

            @Override
            public List<Employee> getEmpListRemote(String keyword) {
                return null;
            }

            @Override
            public ResultEntity<Employee> getEmpWithCircuitBreaker(String signal) {
                return ResultEntity.failed("降级机制生效：" + throwable.getMessage());
            }
        };
    }
}
