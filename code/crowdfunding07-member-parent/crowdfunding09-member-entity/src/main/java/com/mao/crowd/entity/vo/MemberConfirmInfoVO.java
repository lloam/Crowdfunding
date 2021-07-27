package com.mao.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Author: Administrator
 * Date: 2021/7/22 16:03
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberConfirmInfoVO implements Serializable {
    private static final long serialVersionUID = -782789478264098982L;

    // 易付宝账号
    private String paynum;
    // 法人身份证号
    private String cardnum;
}
