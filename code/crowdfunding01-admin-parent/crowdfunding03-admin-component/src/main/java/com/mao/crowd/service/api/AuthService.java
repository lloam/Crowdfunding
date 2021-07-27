package com.mao.crowd.service.api;

import com.mao.crowd.dto.AuthIdRoleIdDto;
import com.mao.crowd.entity.Auth;

import java.util.List;

/**
 * Author: Administrator
 * Date: 2021/7/14 16:32
 * Description:
 */
public interface AuthService {

    List<Auth> getAll();

    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    void saveRoleAuthRelationship(AuthIdRoleIdDto authIdRoleIdDto);

    List<String> getAssignedAuthNameByAdminId(Integer adminId);
}
