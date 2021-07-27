package com.mao.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Author: Administrator
 * Date: 2021/7/24 10:46
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortalTypeVO implements Serializable {
    private static final long serialVersionUID = -3532695660112665946L;

    private Integer id;
    private String name;
    private String remark;

    private List<PortalProjectVO> portalProjectVOList;
}
