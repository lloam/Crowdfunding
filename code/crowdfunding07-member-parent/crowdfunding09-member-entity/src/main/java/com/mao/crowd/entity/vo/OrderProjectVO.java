package com.mao.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Author: Administrator
 * Date: 2021/7/25 11:26
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProjectVO implements Serializable {

    private static final long serialVersionUID = 3608552541402969002L;
    private Integer id;

    private String projectName;

    private String launchName;

    private String returnContent;

    private Integer returnCount;

    private Integer supportPrice;

    private Integer freight;

    private Integer orderId;

    private Integer signalPurchase;

    private Integer purchase;
}
