package com.mao.crowd.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberConfirmInfoPO implements Serializable {
    private static final long serialVersionUID = -1549778677296960446L;
    private Integer id;

    private Integer memberid;

    private String paynum;

    private String cardnum;

}