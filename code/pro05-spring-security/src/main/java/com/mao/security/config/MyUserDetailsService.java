package com.mao.security.config;

import com.mao.security.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Administrator
 * Date: 2021/7/15 16:06
 * Description:
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 根据表单提交的用户名查询 User 对象，并装配角色、权限等信息
     * @param username  表单提交的用户名
     * @return
     * @throws UsernameNotFoundException
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1、从数据库查询 Admin 对象
        String sql = "select id,loginacct,userpswd,username,email from t_admin where loginacct = ?";

        List<Admin> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Admin.class), username);

        Admin admin = list.get(0);

        // 2、给 Admin 设置角色权限信息
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_学徒"));
        authorities.add(new SimpleGrantedAuthority("UPDATE"));

        // 3、把 admin 对象和 authorities 封装到 UserDetails 中

        String userpswd = admin.getUserpswd();

        return new User(username, userpswd, authorities);
    }
}
