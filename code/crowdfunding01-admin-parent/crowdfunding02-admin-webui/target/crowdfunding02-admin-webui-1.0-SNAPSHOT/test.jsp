<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp" %>
<script type="text/javascript">

    $(function () {

        $("#asyncBtn").click(function () {

            console.log("ajax 函数之前");

            $.ajax({
                url: "test/ajax/async.html",
                type: "post",
                dataType: "text",
                async: false,       // 关闭异步工作模式，使用同步方式进行，此时所有操作都在一个线程内执行
                success: function (response) {
                    // success 是接收到服务器端响应后执行
                    console.log("ajax 函数内部的 success 函数" + response);
                }
            });

            console.log("ajax 函数之后");
/*            setTimeout(function () {

            },5000);*/
        });

    })

</script>
<body>
<%@include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <button id="asyncBtn">发送 Ajax 请求</button>
        </div>
    </div>
</div>
</body>
</html>

