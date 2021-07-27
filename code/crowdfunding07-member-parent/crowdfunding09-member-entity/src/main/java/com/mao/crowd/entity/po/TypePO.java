package com.mao.crowd.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypePO implements Serializable {
    private static final long serialVersionUID = -3002656633440232323L;
    private Integer id;

    private String name;

    private String remark;
}