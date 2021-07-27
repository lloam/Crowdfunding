package com.mao.crowd.mvc.config;

import com.mao.crowd.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

/**
 * Author: Administrator
 * Date: 2021/7/17 16:16
 * Description: SpringSecurity 方面 的 Admin 对象
 * 考虑到 User 对象中仅仅包含账号密码、为了能够获取到原始的 Admin 对象，专门创建这个类对 User 类进行扩展
 */
public class SecurityAdmin extends User {

    private static final long serialVersionUID = -7983366909327755071L;

    // 原始的 Admin 对象，包含 Admin 对象的全部属性
    private Admin originalAdmin;

    public SecurityAdmin(
            // 传入原始的 Admin 对象
            Admin originalAdmin,
            // 创建角色、权限信息的集合
            List<GrantedAuthority> authorities) {

        // 调用父类的构造器
        super(originalAdmin.getLoginAcct(), originalAdmin.getUserPswd(), authorities);

        // 给本类的成员变量赋值
        this.originalAdmin = originalAdmin;

        // 将原始 Admin 对象中的密码擦除
        this.originalAdmin.setUserPswd(null);
    }

    // 对外提供的获取 originalAdmin 对象的方法，封装性
    public Admin getOriginalAdmin() {
        return this.originalAdmin;
    }
}
