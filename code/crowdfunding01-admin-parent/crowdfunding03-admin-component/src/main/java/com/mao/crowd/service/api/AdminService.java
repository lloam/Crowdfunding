package com.mao.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.mao.crowd.entity.Admin;

import java.util.List;

/**
 * Author: Administrator
 * Date: 2021/7/11 11:12
 * Description:
 */
public interface AdminService {

    void saveAdmin(Admin admin);

    List<Admin> getAll();

    Admin getAdminByLoginAcct(String loginAcct, String userPswd);

    PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    void remove(Integer adminId);

    Admin getAdminById(Integer adminId);

    void updateAdmin(Admin admin);

    void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList);

    Admin getAdminByLoginAcct(String loginAcct);
}
