<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="include-head.jsp" %>
<link rel="stylesheet" href="css/pagination.css">
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript">

    $(function (){

        // 调用后面声明的函数对页码导航条进行初始化操作
        initPagination();


        // 删除管理员
        $(".delete_btn").click(function () {
            // alert($(this).parents("tr").find("td:eq(0)").text());

            // 获取要删除管理员的 id
            let adminId = $(this).parents("tr").find("td:eq(0)").text();

            // 获取 keyword
            let keyword = $("#keyword").val();

            // 获取当前页码
            let pageNum = ${pageInfo.pageNum};
            // alert("管理员id：" + adminId + "  keyword:" + keyword + "  pageNum:" + pageNum);

            // 发送请求
            window.location.href = "admin/remove/" + adminId + "/" + pageNum + "/" + keyword + ".html";
        });
    });

    function initPagination() {

        // 获取总记录数
        let totalRecord = ${requestScope.pageInfo.total};

        // 声明一个 JSON 对象来存储 Pagination 要设置的属性
        let properties = {
            num_edge_entries: 3,    // 边缘页数
            num_display_entries: 5, // 主体页数
            callback: pageselectCallback,   // 指定用户点击“翻页”的按钮时跳转页面的回调函数
            items_per_page: ${requestScope.pageInfo.pageSize},  // 每页要显示的数量
            current_page: ${requestScope.pageInfo.pageNum - 1}, // Pagination 内部使用 pageIndex 来管理页码，pageIndex 从 0 开始，pageNum 从 1 开始，所以要减一
            prev_text: "上一页",   // 上一页按钮上的文本
            next_text: "下一页"    // 下一页按钮上的文本
        };

        // 生成页码导航条
        $("#Pagination").pagination(totalRecord, properties);

    }

    // 回调函数的含义：声明出来以后不是自己调用，而是交给系统或框架调用
    // 用户点击 “上一页、下一页、1、2、3......”这样的页码时调用这个函数实现页面跳转
    // pageIndex 是 Pagination 传给我们的那个“从0开始”的页码
    function pageselectCallback(pageIndex, jQuery) {

        // 根据 pageIndex 计算得到 pageNum
        let pageNum = pageIndex + 1;

        // 跳转页面
        window.location.href = "admin/get/page.html?pageNum=" + pageNum + "&keyword=${param.keyword}";

        // 由于每一页码按钮都是超链接，所以在这个函数最后取消超链接的默认行为
        return false;

    };

</script>
<body>
<%@include file="include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form action="admin/get/page.html" method="post" class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input value="${param.keyword}" name="keyword" class="form-control has-success" id="keyword" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <a class="btn btn-primary" style="float:right;" href="admin/to/add/page.html"><i class="glyphicon glyphicon-plus"></i> 新增</a>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:if test="${empty requestScope.pageInfo.list}">
                                <tr>
                                    <td colspan="6" align="center">抱歉！没有查询到您要的数据！</td>
                                </tr>
                            </c:if>
                            <c:if test="${!empty requestScope.pageInfo.list}">
                                <c:forEach items="${requestScope.pageInfo.list}" var="admin" varStatus="myStstus">
                                    <tr>
                                        <td class="hidden">${admin.id}</td>
                                        <td>${myStstus.count}</td>
                                        <td><input type="checkbox"></td>
                                        <td>${admin.loginAcct}</td>
                                        <td>${admin.userName}</td>
                                        <td>${admin.email}</td>
                                        <td>
                                            <a href="assign/to/assign/role/page.html?adminId=${admin.id}&pageNum=${requestScope.pageInfo.pageNum}&keyword=${param.keyword}" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></a>
                                            <a href="admin/to/edit/page.html?adminId=${admin.id}&pageNum=${requestScope.pageInfo.pageNum}&keyword=${param.keyword}" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></a>
                                            <button type="button" class="btn btn-danger btn-xs" data-toggle="modal" data-target="#${admin.loginAcct}"><i class=" glyphicon glyphicon-remove"></i></button>
                                            <%-- 模态框弹出 --%>
                                            <div class="modal fade" id="${admin.loginAcct}" tabindex="-1" role="dialog">
                                                <div class="modal-dialog" role="document">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                                            <h4 class="modal-title">删除管理员</h4>
                                                        </div>
                                                        <div class="modal-body">
                                                            <p class="text-danger">确定删除[${admin.userName}]管理员吗？</p>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-danger delete_btn" data-dismiss="modal">确定删除</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>

                            </tbody>
                            <tfoot>
                                <tr>
                                    <td colspan="6" align="center">
                                        <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
                                    </td>
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

