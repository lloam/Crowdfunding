package com.mao.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.mao.crowd.entity.Role;

import java.util.List;

/**
 * Author: Administrator
 * Date: 2021/7/12 21:41
 * Description:
 */
public interface RoleService {

    PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword);

    void saveRole(Role role);

    void updateRole(Role role);

    void removeRole(List<Integer> roleIdList);

    List<Role> getAssignedRole(Integer adminId);

    List<Role> getUnAssignedRole(Integer adminId);

}
