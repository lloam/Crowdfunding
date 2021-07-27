package com.mao.crowd.mvc.config;

import com.google.gson.Gson;
import com.mao.crowd.constant.CrowdConstant;
import com.mao.crowd.exception.*;
import com.mao.crowd.util.CrowdUtil;
import com.mao.crowd.util.ResultEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: Administrator
 * Date: 2021/7/11 17:35
 * Description: 异常映射处理
 */

/**
 * @ControllerAdvice，是Spring3.2提供的新注解,它是一个Controller增强器,
 * 可对controller中被 @RequestMapping注解的方法加一些逻辑处理。最常用的就是异常处理
 */
@ControllerAdvice
public class CrowdExceptionResolver {

    /**
     * 将异常处理整合成方法提高代码复用
     * @return
     */
    private ModelAndView commonResolver(
            // 出现异常后需要跳转的视图
            String viewName,
            // 实际捕获的异常类型
            Exception exception,
            // 当前请求对象
            HttpServletRequest request,
            // 当前响应对象
            HttpServletResponse response
    ) throws IOException {
        // 1、判断当前请求类型
        boolean judgeResult = CrowdUtil.judgeRequestType(request);
        // 2、如果是 Ajax 请求
        if(judgeResult) {
            // 3、创建 ResultEntity 对象
            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());
            // 4、创建 Gson 对象
            Gson gson = new Gson();
            // 5、将 ResultEntity 对象转换为 JSON 字符串
            String json = gson.toJson(resultEntity);
            // 6、将 JSON 字符串作为响应体返回给浏览器
            response.getWriter().write(json);
            // 7、由于上面已经通过原生的 response 对象返回了响应，所以不提供 ModelAndView
            return null;
        }
        // 8、如果不是 Ajax 请求则创建 ModelAndView 对象
        ModelAndView mv = new ModelAndView();
        // 9、将 Exception 对象存入模型
        mv.addObject(CrowdConstant.ATTR_NAME_EXCEPTION,exception);
        // 10、设置对应的视图名称
        mv.setViewName(viewName);
        // 11、返回视图 ModelAndView 对象
        return mv;
    }


    /**
     * 空指针异常处理
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ModelAndView resolverNullPointException(
            // 实际捕获的异常类型
            NullPointerException exception,
            // 当前请求对象
            HttpServletRequest request,
            // 当前响应对象
            HttpServletResponse response
    ) throws IOException {
        // 发生异常后需要返回的视图
        String viewName = "system-error";
        return commonResolver(viewName,exception,request,response);
    }

    /**
     * 登录失败异常处理，还有未登录拦截异常处理，跳转的页面一样就写到一起了
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(value = {LoginFailedException.class, AccessForbiddenException.class})
    public ModelAndView resolverLoginFailedException(
            // 实际捕获的异常类型
            Exception exception,
            // 当前请求对象
            HttpServletRequest request,
            // 当前响应对象
            HttpServletResponse response
    ) throws IOException {
        // 发生异常后需要返回的视图
        String viewName = "admin-login";
        return commonResolver(viewName,exception,request,response);
    }

    /**
     * 用户名重复名异常处理
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(value = LoginAcctAlreadyInUseException.class)
    public ModelAndView resolverLoginAcctAlreadyInUseException(
            // 实际捕获的异常类型
            LoginAcctAlreadyInUseException exception,
            // 当前请求对象
            HttpServletRequest request,
            // 当前响应对象
            HttpServletResponse response
    ) throws IOException {
        // 发生异常后需要返回的视图
        String viewName = "admin-add";
        return commonResolver(viewName,exception,request,response);
    }

    /**
     * 用户名重复名异常处理
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(value = LoginAcctAlreadyInUseForUpdateException.class)
    public ModelAndView resolverLoginAcctAlreadyInUseForUpdateException(
            // 实际捕获的异常类型
            LoginAcctAlreadyInUseForUpdateException exception,
            // 当前请求对象
            HttpServletRequest request,
            // 当前响应对象
            HttpServletResponse response
    ) throws IOException {
        // 发生异常后需要返回的视图
        String viewName = "system-error";
        return commonResolver(viewName,exception,request,response);
    }


    /**
     * 角色名重复异常处理
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(value = RoleNameAlreadyExistException.class)
    public ModelAndView resolverRoleNameAlreadyExistException(
            // 实际捕获的异常类型
            RoleNameAlreadyExistException exception,
            // 当前请求对象
            HttpServletRequest request,
            // 当前响应对象
            HttpServletResponse response
    ) throws IOException {
        // 发生异常后需要返回的视图
        String viewName = "system-error";
        return commonResolver(viewName,exception,request,response);
    }


    /**
     * 当 springSecurity 发生权限拒绝访问时抛出的异常处理
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public ModelAndView resolverAccessDeniedException(
            // 实际捕获的异常类型
            AccessDeniedException exception,
            // 当前请求对象
            HttpServletRequest request,
            // 当前响应对象
            HttpServletResponse response
    ) throws IOException {
        // 发生异常后需要返回的视图
        String viewName = "system-error";
        return commonResolver(viewName,exception,request,response);
    }
}
