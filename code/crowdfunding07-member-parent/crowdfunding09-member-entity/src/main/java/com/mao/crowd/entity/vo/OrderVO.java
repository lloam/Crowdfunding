package com.mao.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Author: Administrator
 * Date: 2021/7/27 15:57
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVO implements Serializable {

    private static final long serialVersionUID = -7861580763291688780L;
    private Integer id;

    // 订单号
    private String orderNum;

    // 支付宝流水单号
    private String payOrderNum;

    // 订单金额
    private Double orderAmount;

    // 是否开发票
    private Integer invoice;

    // 发票抬头
    private String invoiceTitle;

    // 备注
    private String orderRemark;

    // 收货地址 id
    private String addressId;

    private OrderProjectVO orderProjectVO;
}
