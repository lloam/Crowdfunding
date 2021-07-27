package com.mao.crowd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: Administrator
 * Date: 2021/7/11 15:51
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private String province;
    private String city;
    private String street;
}
