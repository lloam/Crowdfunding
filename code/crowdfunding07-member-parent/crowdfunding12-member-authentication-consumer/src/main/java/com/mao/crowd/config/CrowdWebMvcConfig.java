package com.mao.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Author: Administrator
 * Date: 2021/7/21 10:57
 * Description:
 */
@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {


    /**
     * 简单的请求跳转页面就在这里配置
     * @param registry
     */
    public void addViewControllers(ViewControllerRegistry registry) {

        // 浏览器访问的地址
        String urlPath = "/auth/member/to/reg/page";

        // 目标视图的名称
        String viewName = "member-reg";

        // 添加 view-controller
        registry.addViewController(urlPath).setViewName(viewName);
        registry.addViewController("/auth/member/to/login/page").setViewName("member-login");
        registry.addViewController("/auth/member/to/center/page").setViewName("member-center");
        registry.addViewController("/member/my/crowd").setViewName("member-crowd");
    }
}
