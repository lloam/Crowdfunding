package com.mao.crowd.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnPO implements Serializable {
    private static final long serialVersionUID = -5645661810307805804L;
    private Integer id;

    private Integer projectid;

    private Integer type;

    private Integer supportmoney;

    private String content;

    private Integer count;

    private Integer signalpurchase;

    private Integer purchase;

    private Integer freight;

    private Integer invoice;

    private Integer returndate;

    private String describPicPath;
}