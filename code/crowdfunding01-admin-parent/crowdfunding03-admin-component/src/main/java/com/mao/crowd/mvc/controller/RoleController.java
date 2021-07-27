package com.mao.crowd.mvc.controller;

import com.github.pagehelper.PageInfo;
import com.mao.crowd.entity.Role;
import com.mao.crowd.service.api.RoleService;
import com.mao.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: Administrator
 * Date: 2021/7/12 21:42
 * Description: 使用 ajax 请求
 */
@RestController
public class RoleController {


    @Autowired
    private RoleService roleService;

    /**
     * 根据关键字查询角色
     * @param pageNum   查询页码
     * @param pageSize  分页数量
     * @param keyword   关键字
     * @return
     */
    @PreAuthorize("hasRole('部长')")
    @GetMapping("/role/get/page/info.json")
    public ResultEntity<PageInfo<Role>> getPageInfo(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "keyword",defaultValue = "") String keyword
    ) {

        // 调用 Service 方法获取分页数据
        // 这里我们可以会出现异常，但是在异常处理类中已经处理了，因为我们是 ajax 请求，直接处理
        PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);

        // 封装到 ResultEntity 对象中返回，如果上面的操作抛出异常，交给异常映射机制处理
        return ResultEntity.successWithData(pageInfo);
    }


    /**
     * 添加 role
     * @param role
     * @return
     */
    @PostMapping("/role/save.json")
    public ResultEntity<String> saveRole(Role role) {

        roleService.saveRole(role);

        return ResultEntity.successWithoutData();
    }

    /**
     * 修改 role
     * @param role
     * @return
     */
    @PutMapping(value = "/role/update.json")
    public ResultEntity<String> updateRole(@RequestBody Role role) {

        System.out.println(role);
        roleService.updateRole(role);

        return ResultEntity.successWithoutData();
    }


    /**
     * 根据 roleId 数组删除一系列 roleId
     * @return
     */
    @DeleteMapping("/role/delete/by/role/id/array.json")
    public ResultEntity<String> removeByRoleIdArray(@RequestBody List<Integer> roleIdList) {

        roleService.removeRole(roleIdList);

        return ResultEntity.successWithoutData();
    }
}
