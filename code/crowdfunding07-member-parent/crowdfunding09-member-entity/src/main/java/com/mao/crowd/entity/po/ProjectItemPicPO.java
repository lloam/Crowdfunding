package com.mao.crowd.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectItemPicPO implements Serializable {
    private static final long serialVersionUID = -427517038776837040L;
    private Integer id;

    private Integer projectid;

    private String itemPicPath;

}