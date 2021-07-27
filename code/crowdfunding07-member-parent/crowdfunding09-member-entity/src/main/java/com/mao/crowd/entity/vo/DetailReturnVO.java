package com.mao.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: Administrator
 * Date: 2021/7/24 13:57
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailReturnVO {

    // 回报信息的主键
    private Integer returnId;

    // 当前档位需支持的金额
    private Integer supportMoney;

    // 单笔限购，取值为 0 时表示无限额，取值为 1 的时候有限额
    private Integer signalPurchase;

    // 具体的限额数量
    private Integer purchase;

    // 当前该档位支持者的数量，设定一个随机数
    private Integer supporterCount;

    // 运费，取值为 0 时表示包邮
    private Integer freight;

    // 众筹成功后多少天发货
    private Integer returnDate;

    // 回报内容
    private String content;
}
