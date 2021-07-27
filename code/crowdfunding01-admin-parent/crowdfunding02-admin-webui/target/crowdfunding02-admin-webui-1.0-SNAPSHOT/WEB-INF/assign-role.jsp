<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="include-head.jsp" %>
<script type="text/javascript">

    $(function () {

        // 1、为向右分配权限的按钮绑定单击响应函数
        $("#toRightBtn").click(function () {

            // 选择到第一个 select 元素将其选中的元素追加到第二个 select
            /**
             * select 是标签选择器
             * :eq(0) 表示选择页面上的第一个
             * :eq(1) 表示选择页面上的第二个
             * ”>“ 表示选择子元素
             * :selected 表示选择 ”被选中的“ option
             * appendTo() 能够将 jQuery 对象追加到指定的位置
             */
            $("select:eq(0)>option:selected").appendTo("select:eq(1)")

        });

        // 2、为向左收回分配权限的按钮绑定单击响应函数
        $("#toLeftBtn").click(function () {

            // 选择到第一个 select 元素将其选中的元素追加到第二个 select
            /**
             * select 是标签选择器
             * :eq(0) 表示选择页面上的第一个
             * :eq(1) 表示选择页面上的第二个
             * ”>“ 表示选择子元素
             * :selected 表示选择 ”被选中的“ option
             * appendTo() 能够将 jQuery 对象追加到指定的位置
             */
            $("select:eq(1)>option:selected").appendTo("select:eq(0)")
        });

        // 3、要把已分配的角色全部选中
        $("#assignBtn").click(function () {

            $("select:eq(1)>option").prop("selected","selected");

        });
    });

</script>
<body>
<%@include file="include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="admin/to/main/page.html">首页</a></li>
                <li><a href="admin/get/page.html">数据列表</a></li>
                <li class="active">分配角色</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form action="assign/do/role/assign.html" method="post" role="form" class="form-inline">
                        <input type="hidden" name="adminId" value="${param.adminId}" />
                        <input type="hidden" name="pageNum" value="${param.pageNum}" />
                        <input type="hidden" name="keyword" value="${param.keyword}" />
                        <div class="form-group">
                            <label>未分配角色列表</label><br>
                            <select class="form-control" multiple="" size="10" style="width:100px;overflow-y:auto;">
                                <c:forEach items="${requestScope.unAssignedRoleList}" var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <ul>
                                <li id="toRightBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                                <br>
                                <li id="toLeftBtn" class="btn btn-default glyphicon glyphicon-chevron-left" style="margin-top:20px;"></li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin-left:40px;">
                            <label>已分配角色列表</label><br>
                            <select name="roleIdList" class="form-control" multiple="multiple" size="10" style="width:100px;overflow-y:auto;">
                                <c:forEach items="${requestScope.assignedRoleList}" var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <button id="assignBtn" type="submit" class="btn btn-success btn-block" style="width: 150px;margin-left: 180px;">保存</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

