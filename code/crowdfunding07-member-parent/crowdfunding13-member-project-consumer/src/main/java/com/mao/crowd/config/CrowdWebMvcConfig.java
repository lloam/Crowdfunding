package com.mao.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Author: Administrator
 * Date: 2021/7/22 17:40
 * Description:
 */
@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {


    public void addViewControllers(ViewControllerRegistry registry) {

        // 添加 view-controller，这个是在 project-consumer 内部定义的，所以这里是一个不经过 Zuul 访问的地址，所以不需要添加 Zuul 中所配置的前缀 "/project/**"
        registry.addViewController("/launch/project/page").setViewName("project-launch");
        registry.addViewController("/agree/protocol/page").setViewName("project-agree");
        registry.addViewController("/return/info/page").setViewName("project-return");
        registry.addViewController("/create/confirm/page").setViewName("project-confirm");
        registry.addViewController("/create/success").setViewName("project-success");
    }
}
