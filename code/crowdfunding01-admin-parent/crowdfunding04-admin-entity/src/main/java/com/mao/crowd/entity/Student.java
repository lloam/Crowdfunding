package com.mao.crowd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Author: Administrator
 * Date: 2021/7/11 15:50
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private Integer stuId;
    private String stuName;
    private Address address;
    private List<Subject> subjectList;
    private Map<String,String> map;
}
