package com.mao.crowd.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectPO implements Serializable {
    private static final long serialVersionUID = 5054872035946691621L;
    private Integer id;

    private String projectName;

    private String projectDescription;

    private Integer money;

    private Integer day;

    private Integer status;

    private String deploydate;

    private Long supportmoney;

    private Integer supporter;

    private Integer completion;

    private Integer memberid;

    private String createdate;

    private Integer follower;

    private String headerPicturePath;
}