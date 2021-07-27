package com.mao.crowd.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberLaunchInfoPO implements Serializable {
    private static final long serialVersionUID = -4289386967389402886L;
    private Integer id;

    private Integer memberid;

    private String descriptionSimple;

    private String descriptionDetail;

    private String phoneNum;

    private String serviceNum;
}