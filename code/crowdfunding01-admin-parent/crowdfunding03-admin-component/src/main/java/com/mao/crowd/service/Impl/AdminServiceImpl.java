package com.mao.crowd.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mao.crowd.constant.CrowdConstant;
import com.mao.crowd.entity.Admin;
import com.mao.crowd.entity.AdminExample;
import com.mao.crowd.exception.LoginAcctAlreadyInUseException;
import com.mao.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.mao.crowd.exception.LoginFailedException;
import com.mao.crowd.mapper.AdminMapper;
import com.mao.crowd.service.api.AdminService;
import com.mao.crowd.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Author: Administrator
 * Date: 2021/7/11 11:12
 * Description:
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * 添加管理员
     * @param admin
     */
    public void saveAdmin(Admin admin) {

        // 1、密码加密
        String userPswd = admin.getUserPswd();
//        userPswd = CrowdUtil.md5(userPswd);
        userPswd = passwordEncoder.encode(userPswd);
        admin.setUserPswd(userPswd);

        // 2、生成创建时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = format.format(date);
        admin.setCreateTime(createTime);

        try {
            // 3、执行保存
            adminMapper.insert(admin);
        } catch (Exception e) {
            e.printStackTrace();

            if(e instanceof DuplicateKeyException) {
                // 如果抛出的异常是用户名重复的异常就抛出一个自定义的异常
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }


    /**
     * 获取全部管理员
     * @return
     */
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    /**
     * 验证管理员账号、密码
     * @param loginAcct 账号
     * @param userPswd  密码
     * @return
     */
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {

        // 1、根据登陆账号查询 Admin 对象
        // ① 创建 AdminExample 对象
        AdminExample adminExample = new AdminExample();

        // ② 创建 Criteria 对象
        AdminExample.Criteria criteria = adminExample.createCriteria();

        // ③ 在 Criteria 对象中封装查询条件
        criteria.andLoginAcctEqualTo(loginAcct);

        // ④ 调用 AdminMapper 的方法执行查询
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        // 2、判断 Admin 对象是否为 null
        if(adminList == null || adminList.size() == 0) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        if(adminList.size() > 1) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }

        Admin admin = adminList.get(0);

        // 3、如果 Admin 对象为 null 则抛出异常
        if(admin == null) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        // 4、如果 Admin 对象不为 null 则将数据库密码从 Admin 对象中取出
        String userPswdDB = admin.getUserPswd();

        // 5、将表单提交的明文密码进行加密
        String userPswdForm = CrowdUtil.md5(userPswd);

        // 6、对密码进行比较
        if(!Objects.equals(userPswdDB,userPswdForm)) {
            // 7、如果比较结果是不一致则抛出异常
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        // 8、如果一致则返回 Admin 对象
        return admin;
    }


    /**
     * 分页查询管理员
     * @param keyword   查询关键字
     * @param pageNum   查询页码
     * @param pageSize  查询页面大小
     * @return
     */
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {

        // 1、调用 PageHelper 的静态方法开启分页功能
        // 这里充分体现了 PageHelper 的 “非侵入式” 设计：原本要做的查询不必有任何修改
        PageHelper.startPage(pageNum,pageSize);

        // 2、执行查询
        List<Admin> list = adminMapper.selectAdminByKeyword(keyword);

        // 3、封装到 PageInfo 对象中
        return new PageInfo<>(list);
    }

    /**
     * 删除管理员
     * @param adminId   管理员 Id
     */
    public void remove(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }

    /**
     * 根据管理员 adminId 查询到管理员
     * @param adminId
     * @return
     */
    public Admin getAdminById(Integer adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }

    /**
     * 更新管理员==有选择的更新
     * @param admin
     */
    public void updateAdmin(Admin admin) {

        try {
            // “Selective” 表示有选择的更新，对于 null 值得字段不更新
            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof DuplicateKeyException) {
                // 如果需改的账户已经存在，就抛出自定义异常提醒管理者
                throw new LoginAcctAlreadyInUseForUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    /**
     * 保存权限分配
     * @param adminId
     * @param roleIdList
     */
    public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {

        // 1、先根据 adminId 删除旧的角色，再将新的角色插入
        adminMapper.deleteOldRelationship(adminId);

        // 2、根据 roleIdList 和 adminId 保存新的角色
        if(roleIdList != null && roleIdList.size() > 0) {
            adminMapper.insertNewRelationship(adminId, roleIdList);
        }
    }

    /**
     * 通过登录账号查询相应的 admin 对象
     * @param loginAcct
     * @return
     */
    public Admin getAdminByLoginAcct(String loginAcct) {

        AdminExample adminExample = new AdminExample();

        AdminExample.Criteria criteria = adminExample.createCriteria();

        criteria.andLoginAcctEqualTo(loginAcct);

        List<Admin> list = adminMapper.selectByExample(adminExample);

        Admin admin = list.get(0);

        return admin;
    }
}
