package com.mao.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * Author: Administrator
 * Date: 2021/7/14 21:09
 * Description:
 */
// 将当前类标志为配置类
@Configuration
// 启用 web 环境下权限控制功能
@EnableWebSecurity
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // 每次调用这个方法时会检查 IOC 容器中是否有了对应的 bean ，如果有就不会真正执行这个函数，因为 bean
    // 默认是单例的， 可以使用 @Scope(value="") 注解控制是否单例
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    protected void configure(AuthenticationManagerBuilder builder) throws Exception {

/*        builder
                // 在内存中完成账号、密码的检查
                .inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                // 指定账号
                .withUser("tom")
                // 指定密码
                .password(new BCryptPasswordEncoder().encode("123123"))
                // 指定当前用户的角色
                .roles("ADMIN","学徒")
                .and()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("jerry")
                .password(new BCryptPasswordEncoder().encode("123"))
                // 指定当前用户的权限
                .authorities("UPDATE","内门弟子");*/

        // 装配 userDetailsService 对象
        builder
                .userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

    }

    /**
     * 这个方法是处理请求拦截
     * 配置拦截或者放行哪些请求、登录请求等操作
     * @param security
     * @throws Exception
     */
    protected void configure(HttpSecurity security) throws Exception {

        // 准备 JdbcTokenRepositoryImpl 对象
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);

        security
                // 对请求进行授权
                .authorizeRequests()
                // 针对 /index.jsp 路径进行授权
                .antMatchers("/index.jsp")
                // 可以无条件访问
                .permitAll()
                // 针对 layui 目录下所有资源进行授权
                .antMatchers("/layui/**")
                // 可以无条件访问
                .permitAll()
                // 针对 level1 路径设置访问要求
                .antMatchers("/level1/**")
                // 要求是学徒的角色
                .hasRole("学徒")
                // 针对 level2 路径设置访问要求
                .antMatchers("/level2/**")
                // 要求有内门弟子的权限
                .hasAnyAuthority("内门弟子")
                .and()
                // 对请求进行授权
                .authorizeRequests()
                // 任意请求
                .anyRequest()
                // 需要登录后才可以访问
                .authenticated()
                .and()
                // 使用表单进行登录
                .formLogin()
                // 指定登录页面（如果没有指定会使用 springSecurity 自带的登录页面）
                /**
                 * 关于 loginPage() 方法的特殊说明
                 * 指定登录页的同时会影响到：“提交登录表单的地址”、“退出登录的地址”、“登录失败的地址”
                 * /login GET - the login form  去登录页面
                 * /login POST - process the credentials and if valid authenticate the user 提交登录表单
                 * /login?error GET - redirect here for failed authentication attempts  登录失败
                 * /login?logout GET - redirect here after successfully logging out 退出登录
                 */
                .loginPage("/index.jsp")
                // 指定提交登录表单的地址      .loginProcessingUrl() 方法制订了登录的地址，就会覆盖 loginPage() 方法设置的默认值 /index.jsp POST
                .loginProcessingUrl("/do/login.html")
                // 定制登录账号的请求参数名，不定制就会使用 SpringSecurity 默认的参数：username
                .usernameParameter("loginAcct")
                // 定制登录密码的请求参数名
                .passwordParameter("userPswd")
                // 登录成功后页面跳转的地址
                .defaultSuccessUrl("/main.html")
//                .and()
//                .csrf()
//                // 禁用 CSRF 功能
//                .disable()
                .and()
                // 开启退出功能
                .logout()
                // 指定处理退出请求 url 地址
                .logoutUrl("/do/logout.html")
                // 退出成功后前往的页面地址
                .logoutSuccessUrl("/index.jsp")
                .and()
                .exceptionHandling()
                // 访问被拒绝时前往的页面
                // .accessDeniedPage("/to/no/auth/page.html")
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
                        request.setAttribute("message","抱歉！您无法访问这个资源！");
                        request.getRequestDispatcher("/WEB-INF/views/no_auth.jsp").forward(request,response);
                    }
                })
                .and()
                // 开启记住我功能
                .rememberMe()
                // 装备 token 仓库
                .tokenRepository(tokenRepository);
    }
}
