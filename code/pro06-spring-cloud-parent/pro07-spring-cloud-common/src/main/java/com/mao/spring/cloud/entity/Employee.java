package com.mao.spring.cloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: Administrator
 * Date: 2021/7/19 9:48
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private Integer empId;
    private String empName;
    private Double empSalary;
}
