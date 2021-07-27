package com.mao.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginVO implements Serializable {


    private static final long serialVersionUID = 4004692265193395564L;

    private Integer id;

    private String username;

    private String email;

}