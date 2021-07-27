package com.mao.crowd.filter;

import com.mao.crowd.constant.AccessPassResources;
import com.mao.crowd.constant.CrowdConstant;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Author: Administrator
 * Date: 2021/7/21 22:55
 * Description: zuul 请求过滤
 */
@Component
public class CrowdAccessFilter extends ZuulFilter {

    /**
     * 判断请求的路径是否需要进行过滤
     * @return
     *      true：不放行，需要过滤执行 run() 方法
     *      false：放行，不需要过滤执行 run() 方法
     */
    public boolean shouldFilter() {

        // 1、获取 RequestContext 对象
        RequestContext requestContext = RequestContext.getCurrentContext();

        // 2、通过 RequestContext 对象获取当前请求对象（框架底层是使用 ThreadLocal 对象事先绑定了 RequestContext 对象
        HttpServletRequest request = requestContext.getRequest();

        // 3、获取 ServletPath 请求路径
        String servletPath = request.getServletPath();

        // 4、根据 ServletPath 判断当前请求是否对应可以直接放行的特定功能
        boolean containResult = AccessPassResources.PASS_RES_SET.contains(servletPath);

        if (containResult) {

            // 5、如果当前请求是可以直接放行的特定功能的请求则返回 false 放行
            return false;
        }

        // 6、判断当前请求是否为静态资源，如果是静态资源就不需要过滤，返回 false 直接放行
        // 工具方法返回 true：说明当前请求是静态资源，取反为 false，表示放行，不过滤做登录检查
        // 工具方法返回 false：说明当前请求不是静态资源，取反为 true，表示不放行，需要过滤做登录检查
        return !AccessPassResources.judgeCurrentServletPathWhetherStaticResource(servletPath);
    }


    /**
     * 在这里执行相关的验证，判断 session 是否存在
     * 存在，则通过
     * 不存在，则要提示登录
     * @return
     * @throws ZuulException
     */
    public Object run() throws ZuulException {

        // 1、获取当前请求对象
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        // 2、获取当前 Session 对象
        HttpSession session = request.getSession();

        // 3、尝试从 Session 对象获取已登录的用户
        Object loginMember = session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);

        // 4、判断 loginMember 是否为空
        if(loginMember == null) {
            // loginMember 为空说明没有登录用户

            // 5、从 requestContext 对象中获取 Response 对象
            HttpServletResponse response = requestContext.getResponse();

            // 6、将提示消息存入 Session 域
            session.setAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_ACCESS_FORBIDDEN);

            // 7、重定向到 auth-consumer 工程中的登录页面
            try {
                response.sendRedirect("/auth/member/to/login/page");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    public String filterType() {

        // 这里返回 “pre” 意思是在目标为服务前执行过滤
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }




}
