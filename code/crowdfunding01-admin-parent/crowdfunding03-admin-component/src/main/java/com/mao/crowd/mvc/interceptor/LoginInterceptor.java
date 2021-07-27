package com.mao.crowd.mvc.interceptor;

import com.mao.crowd.constant.CrowdConstant;
import com.mao.crowd.entity.Admin;
import com.mao.crowd.exception.AccessForbiddenException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Author: Administrator
 * Date: 2021/7/11 22:04
 * Description: 登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 1、通过 request 对象获取 Session 对象
        HttpSession session = request.getSession();

        // 2、尝试从 Session 域中获取 Admin 对象
        Admin admin = (Admin) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);

        // 3、判断 Admin 对象是否为 null
        if(admin == null) {

            // 4、抛出异常
            throw new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDDEN);

        }

        // 5、如果 Admin 对象不为 null ，就放行
        return true;
    }
}
