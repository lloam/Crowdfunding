package com.mao.crowd.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagPO implements Serializable {
    private static final long serialVersionUID = 4823780950682693519L;
    private Integer id;

    private String name;
}