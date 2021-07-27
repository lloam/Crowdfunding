package com.mao.crowd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: Administrator
 * Date: 2021/7/14 18:21
 * Description: 用于接收前端发送给后端的 json 数据
 *
 * 这个类是在为角色分配权限的时候创建的，目的是接受前端 json 数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthIdRoleIdDto {

    // 用于接收权限 id 数组
    private Integer[] authIdArray;

    // 用于接收角色 id
    private Integer roleId;
}
