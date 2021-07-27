package com.mao.crowd.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mao.crowd.constant.CrowdConstant;
import com.mao.crowd.entity.Role;
import com.mao.crowd.entity.RoleExample;
import com.mao.crowd.exception.RoleNameAlreadyExistException;
import com.mao.crowd.mapper.RoleMapper;
import com.mao.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Administrator
 * Date: 2021/7/12 21:41
 * Description:
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;


    /**
     * 通过关键字查询 role 角色
     * @param pageNum   当前查询页码
     * @param pageSize  当前每页数据量
     * @param keyword   关键字
     * @return
     */
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {

        // 1、开启分页功能
        PageHelper.startPage(pageNum,pageSize);

        // 2、执行查询
        List<Role> roles = roleMapper.selectRoleByKeyword(keyword);

        // 3、封装为 PageInfo 对象返回
        return new PageInfo<>(roles);
    }

    /**
     * 添加 role
     * @param role
     */
    public void saveRole(Role role) {

        try {
            roleMapper.insert(role);
        } catch (Exception e) {
            e.printStackTrace();
            // 如果发生的异常时角色名重复就抛出自定义异常
            if(e instanceof DuplicateKeyException) {
                throw new RoleNameAlreadyExistException(CrowdConstant.MESSAGE_ROLE_NAME_ALREADY_EXIST);
            }
        }
    }

    /**
     * 根据主键 id 更新 role
     * @param role
     */
    public void updateRole(Role role) {

        try {
            roleMapper.updateByPrimaryKey(role);
        } catch (Exception e) {
            e.printStackTrace();
            // 如果发生的异常时角色名重复就抛出自定义异常
            if(e instanceof DuplicateKeyException) {
                throw new RoleNameAlreadyExistException(CrowdConstant.MESSAGE_ROLE_NAME_ALREADY_EXIST);
            }
        }
    }

    /**
     * 根据 roleId 的数组删除批量删除 role
     * @param roleIdList
     */
    public void removeRole(List<Integer> roleIdList) {

        RoleExample example = new RoleExample();

        RoleExample.Criteria criteria = example.createCriteria();

        // delete from t_role where id in (1,2,3)
        criteria.andIdIn(roleIdList);

        roleMapper.deleteByExample(example);
    }

    /**
     * 查询管理员已分配的角色
     * @param adminId
     * @return
     */
    public List<Role> getAssignedRole(Integer adminId) {
        return roleMapper.selectAssignedRole(adminId);
    }

    /**
     * 查询管理员未分配的角色
     * @param adminId
     * @return
     */
    public List<Role> getUnAssignedRole(Integer adminId) {
        return roleMapper.selectUnAssignedRole(adminId);
    }

}
