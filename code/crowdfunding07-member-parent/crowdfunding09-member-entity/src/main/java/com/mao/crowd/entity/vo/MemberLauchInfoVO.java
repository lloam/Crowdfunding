package com.mao.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Author: Administrator
 * Date: 2021/7/22 16:01
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberLauchInfoVO implements Serializable {
    private static final long serialVersionUID = 3472541931854996688L;

    // 简单介绍
    private String descriptionSimple;
    // 详细介绍
    private String descriptionDetail;
    // 联系电话
    private String phoneNum;
    // 客服电话
    private String serviceNum;
}
