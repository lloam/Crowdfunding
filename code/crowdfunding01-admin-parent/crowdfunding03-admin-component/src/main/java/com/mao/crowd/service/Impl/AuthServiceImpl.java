package com.mao.crowd.service.Impl;

import com.mao.crowd.dto.AuthIdRoleIdDto;
import com.mao.crowd.entity.Auth;
import com.mao.crowd.entity.AuthExample;
import com.mao.crowd.mapper.AuthMapper;
import com.mao.crowd.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: Administrator
 * Date: 2021/7/14 16:32
 * Description:
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    /**
     * 获取全部的权限
     * @return
     */
    public List<Auth> getAll() {
        return authMapper.selectByExample(new AuthExample());
    }

    /**
     * 根据 roleId 查询到所有的 authId
     * @param roleId
     * @return
     */
    public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {
        return authMapper.selectAssignedAuthIdByRoleId(roleId);
    }

    /**
     * 保存角色 id 与权限 id 的关系
     * @param authIdRoleIdDto
     */
    public void saveRoleAuthRelationship(AuthIdRoleIdDto authIdRoleIdDto) {

        // 1、获取 roleId 的值
        Integer roleId = authIdRoleIdDto.getRoleId();

        // 2、删除旧的关联关系数据
        authMapper.deleteOldRelationship(roleId);

        // 3、获取 authIdList
        Integer[] authIdArray = authIdRoleIdDto.getAuthIdArray();
        List<Integer> authIdList = Arrays.asList(authIdArray);

        // 4、判断 authIdList 是否有效
        if(authIdList != null && authIdList.size() > 0) {
            authMapper.insertNewRelationship(roleId, authIdList);
        }
    }

    /**
     * 根据 adminId 查询出权限的名称
     * @param adminId
     * @return
     */
    public List<String> getAssignedAuthNameByAdminId(Integer adminId) {
        return authMapper.selectAssignedAuthNameByAdminId(adminId);
    }
}
