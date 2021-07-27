package com.mao.crowd.mvc.config;

import com.mao.crowd.constant.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: Administrator
 * Date: 2021/7/17 11:57
 * Description: springSecurity 配置类
 */
// 表示当前类是一个配置类
@Configuration
// 开启 web 环境下的权限控制功能
@EnableWebSecurity

// 启用全局方法权限控制功能，并且设置 prePostEnabled = true。保证 @PreAuthority、@PostAuthority、@PreFilter、@PostFilter 生效
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * 用户验证
     * @param builder
     * @throws Exception
     */
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
    //        // 临时使用内存版登录的模式测试代码
    //        builder.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("lloam").password(new BCryptPasswordEncoder().encode("999999")).roles("ADMIN")
        // 正式功能中使用基于数据库的认证
        builder
                .userDetailsService(userDetailsService)
                // 密码加密
                .passwordEncoder(passwordEncoder);
    }

    /**
     * 请求认证授权
     * @param security
     * @throws Exception
     */
    protected void configure(HttpSecurity security) throws Exception {
        security
                // 对请求进行授权
                .authorizeRequests()
                // 针对登录页进行授权
                .antMatchers("/admin/to/login/page.html")
                // 无条件访问
                .permitAll()
                .antMatchers("/bootstrap/**")       // 针对静态资源进行设置，无条件访问
                .permitAll()
                .antMatchers("/crowd/**")           // 针对静态资源进行设置，无条件访问
                .permitAll()
                .antMatchers("/css/**")             // 针对静态资源进行设置，无条件访问
                .permitAll()
                .antMatchers("/fonts/**")           // 针对静态资源进行设置，无条件访问
                .permitAll()
                .antMatchers("/img/**")             // 针对静态资源进行设置，无条件访问
                .permitAll()
                .antMatchers("/jquery/**")          // 针对静态资源进行设置，无条件访问
                .permitAll()
                .antMatchers("/layer/**")           // 针对静态资源进行设置，无条件访问
                .permitAll()
                .antMatchers("/script/**")          // 针对静态资源进行设置，无条件访问
                .permitAll()
                .antMatchers("/ztree/**")           // 针对静态资源进行设置，无条件访问
                .permitAll()
                .antMatchers("/admin/get/page.html")    // 针对管理员分页显示这个页面需要有经理这个角色
                // .hasRole("经理")                                 // 要求具备经理这个角色
                .access("hasRole('经理') or hasAuthority('user:get')")    // 具备"经理"角色或者有查询用户的权限"user:get"
                .anyRequest()                                    // 其他任何请求需要授权才能访问
                .authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
                        request.setAttribute("exception",new Exception(CrowdConstant.MESSAGE_ACCESS_DENIED));
                        request.getRequestDispatcher("/WEB-INF/system-error.jsp").forward(request,response);
                    }
                })
                .and()
                .csrf()                                         // 防跨站请求伪造的功能
                .disable()                                      // 禁用 csrf
                .formLogin()                                    // 开启表单登录的功能
                .loginPage("/admin/to/login/page.html")         // 指定登录的页面
                .loginProcessingUrl("/security/do/login.html")  // 指定处理登录请求的地址
                .successForwardUrl("/admin/to/main/page.html")  // 指定登录成功前往的页面
                .permitAll()
                .failureForwardUrl("/admin/to/login/page.html") // 失败重定向的地址
                .usernameParameter("loginAcct")                 // 账号的请求参数名称
                .passwordParameter("userPswd")                  // 密码的请求参数名称
                .and()
                .logout()                                       // 开启推出登录功能
                .logoutUrl("/security/do/logout.html")          // 指定退出登录地址
                .logoutSuccessUrl("/admin/to/login/page.html")  // 指定退出成功以后前往的地址
                ;
    }
}
