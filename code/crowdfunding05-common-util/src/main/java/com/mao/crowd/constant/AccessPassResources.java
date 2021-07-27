package com.mao.crowd.constant;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Administrator
 * Date: 2021/7/21 22:35
 * Description: 登录验证的一些常量，准备好 zuul 组件可以放行的一些资源
 */
public class AccessPassResources {

    public static final Set<String> PASS_RES_SET = new HashSet<>();

    static {
        // 可以放行的请求路径
        PASS_RES_SET.add("/");
        PASS_RES_SET.add("/auth/member/to/reg/page");
        PASS_RES_SET.add("/auth/member/to/login/page");
        PASS_RES_SET.add("/auth/member/logout");
        PASS_RES_SET.add("/auth/member/do/login");
        PASS_RES_SET.add("/auth/do/member/register");
        PASS_RES_SET.add("/auth/member/send/short/message.json");
    }

    public static final Set<String> STATIC_RES_SET = new HashSet<>();

    static {
        // 可以放行的静态资源
        STATIC_RES_SET.add("bootstrap");
        STATIC_RES_SET.add("css");
        STATIC_RES_SET.add("fonts");
        STATIC_RES_SET.add("img");
        STATIC_RES_SET.add("jquery");
        STATIC_RES_SET.add("layer");
        STATIC_RES_SET.add("script");
        STATIC_RES_SET.add("ztree");
    }


    /**
     * 判断一个请求是否是请求工程的静态资源
     * @param servletPaht
     * @return
     *          true：请求的是静态资源
     *          false：请求的不是静态资源
     */
    public static boolean judgeCurrentServletPathWhetherStaticResource(String servletPaht) {

        // 1、排除字符串无效的情况
        if (servletPaht == null || servletPaht.length() == 0) {
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }

        // 2、根据 “/” 拆分 ServletPath 字符串
        String[] split = servletPaht.split("/");

        // 3、考虑到第一个斜杠左边经过拆分后得到一个空字符串是数组的第一个元素，所以我们要取数组的第二个元素来判断是否请求的是静态资源
        String firstLevelPath = split[1];

        // 4、判断请求的的第一个路径单词是否在静态资源目录下
        return STATIC_RES_SET.contains(firstLevelPath);
    }

//    public static void main(String[] args) {
//        String servletPath = "/css/bbb/ccc";
//
//        boolean result = judgeCurrentServletPathWhetherStaticResource(servletPath);
//        System.out.println(result);
//    }
}
