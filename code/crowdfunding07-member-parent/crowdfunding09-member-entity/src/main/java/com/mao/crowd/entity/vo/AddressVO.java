package com.mao.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Author: Administrator
 * Date: 2021/7/27 10:52
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressVO implements Serializable {

    private static final long serialVersionUID = -5724424590875027083L;
    private Integer id;

    private String receiveName;

    private String phoneNum;

    private String address;

    private Integer memberId;
}
