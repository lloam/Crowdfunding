package com.mao.crowd.mvc.controller;

import com.mao.crowd.dto.AuthIdRoleIdDto;
import com.mao.crowd.entity.Auth;
import com.mao.crowd.entity.Role;
import com.mao.crowd.service.api.AdminService;
import com.mao.crowd.service.api.AuthService;
import com.mao.crowd.service.api.RoleService;
import com.mao.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: Administrator
 * Date: 2021/7/14 11:39
 * Description: 权限分配控制器
 */
@Controller
public class AssignController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    /**
     * 根据管理员 id 获取 对应的已分配权限和未分配权限
     * @param adminId
     * @param model
     * @return
     */
    @GetMapping("/assign/to/assign/role/page.html")
    public String toAssignRolePage(
            @RequestParam("adminId") Integer adminId,
            Model model) {

        // 1、查询已分配的角色
        List<Role> assignedRoleList =  roleService.getAssignedRole(adminId);

        // 2、查询未分配的角色
        List<Role> unAssignedRoleList =  roleService.getUnAssignedRole(adminId);

        // 3、存入模型
        model.addAttribute("assignedRoleList",assignedRoleList);
        model.addAttribute("unAssignedRoleList",unAssignedRoleList);

        return "assign-role";
    }


    /**
     * post 请求保存角色与权限的关系
     * @param adminId
     * @param pageNum
     * @param keyword
     * @param roleIdList
     * @return
     */
    @PostMapping("/assign/do/role/assign.html")
    public String saveAdminRoleRelationship(
            @RequestParam("adminId") Integer adminId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam(value = "keyword",defaultValue = "") String keyword,
            // 允许用户在页面上取消所有已分配角色再提交表单，所以可以不提供 roleIdList 请求参数
            // required = false 表示这个参数不是必要的
            @RequestParam(value = "roleIdList",required = false) List<Integer> roleIdList
    ) {

        // 在 adminService 中保存权限分配
        adminService.saveAdminRoleRelationship(adminId, roleIdList);


        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    /**
     * get 请求获取所有的权限
     * @return
     */
    @ResponseBody
    @GetMapping("/assign/get/all/auth.json")
    public ResultEntity<List<Auth>> getAllAuth() {

        List<Auth> authList = authService.getAll();

        return ResultEntity.successWithData(authList);
    }

    @ResponseBody
    @GetMapping("/assign/get/assigned/auth/id/by/role/id.json")
    public ResultEntity<List<Integer>> getAssignedAuthIdByRoleId(
            @RequestParam("roleId") Integer roleId
    ) {

        List<Integer> authIdList = authService.getAssignedAuthIdByRoleId(roleId);

        return ResultEntity.successWithData(authIdList);
    }

    @ResponseBody
    @PostMapping("/assign/do/role/assign/auth.json")
    public ResultEntity<String> saveRoleAuthRelationship(
            @RequestBody AuthIdRoleIdDto authIdRoleIdDto
            ) {

        authService.saveRoleAuthRelationship(authIdRoleIdDto);

        return ResultEntity.successWithoutData();
    }
}
