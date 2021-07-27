package com.mao.crowd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: Administrator
 * Date: 2021/7/11 15:52
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subject {

    private String subjectName;
    private Integer subjectScore;

}
