package com.mao.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Author: Administrator
 * Date: 2021/7/24 10:44
 * Description: 首页展示数据的 VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortalProjectVO implements Serializable {
    private static final long serialVersionUID = -3504721770338386785L;


    private Integer projectId;
    private String projectName;
    private String headerPicturePath;
    private Integer money;
    private String  deployDate;
    private Integer percentage;
    private Integer supporter;


}
