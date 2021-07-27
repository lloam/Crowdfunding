package com.mao.crowd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    // 主键
    private Integer id;

    // 父节点id
    private Integer pid;

    // 节点名
    private String name;

    // 节点附带的url地址，点击菜单时跳转的地址
    private String url;

    // 节点图标的样式
    private String icon;

    // 子节点的集合，初始化时为了避免空指针异常
    private List<Menu> children = new ArrayList<>();

    // 控制节点默认是否为打开状态，设置为true表示默认打开
    private Boolean open = true;
}