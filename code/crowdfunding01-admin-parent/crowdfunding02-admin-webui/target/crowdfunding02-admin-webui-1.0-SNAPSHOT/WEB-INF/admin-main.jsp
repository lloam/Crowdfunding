<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="include-head.jsp"%>

<body>
<%@include file="include-nav.jsp"%>
<div class="container-fluid">
    <div class="row">
        <%@include file="include-sidebar.jsp"%>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h1 class="page-header">控制面板</h1>
            Credentials:<security:authentication property="credentials"/>
            <p>显示出来才发现，principal 原来是我们自己封装的 SecurityAdmin 对象</p></br>
            <p>SpringSecurity 处理完登录操作之后把登录成功的 User 对象以 Principal 属性名存入 UsernamePasswordAuthenticationToken</p></br>
            Principal:<security:authentication property="principal.class.name"/></br>
            Principal 中的密码:<security:authentication property="principal.password"/></br>
            访问 SecurityAdmin 对象的属性：<security:authentication property="principal.originalAdmin.loginAcct"/></br>
            访问 SecurityAdmin 对象的属性：<security:authentication property="principal.originalAdmin.userPswd"/></br>
            访问 SecurityAdmin 对象的属性：<security:authentication property="principal.originalAdmin.userName"/></br>
            访问 SecurityAdmin 对象的属性：<security:authentication property="principal.originalAdmin.email"/></br>
            访问 SecurityAdmin 对象的属性：<security:authentication property="principal.originalAdmin.createTime"/></br>
            <div class="row placeholders">
                <security:authorize access="hasRole('经理')">
                    <div class="col-xs-6 col-sm-3 placeholder">
                        <img data-src="holder.js/200x200/auto/sky" class="img-responsive" alt="Generic placeholder thumbnail">
                        <h4>角色</h4>
                        <span class="text-muted">经理</span>
                    </div>
                </security:authorize>

                <security:authorize access="hasAuthority('role:delete')">
                    <div class="col-xs-6 col-sm-3 placeholder">
                        <img data-src="holder.js/200x200/auto/vine" class="img-responsive" alt="Generic placeholder thumbnail">
                        <h4>权限</h4>
                        <span class="text-muted">role:delete</span>
                    </div>
                </security:authorize>

                <div class="col-xs-6 col-sm-3 placeholder">
                    <img data-src="holder.js/200x200/auto/sky" class="img-responsive" alt="Generic placeholder thumbnail">
                    <h4>Label</h4>
                    <span class="text-muted">Something else</span>
                </div>
                <div class="col-xs-6 col-sm-3 placeholder">
                    <img data-src="holder.js/200x200/auto/vine" class="img-responsive" alt="Generic placeholder thumbnail">
                    <h4>Label</h4>
                    <span class="text-muted">Something else</span>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

