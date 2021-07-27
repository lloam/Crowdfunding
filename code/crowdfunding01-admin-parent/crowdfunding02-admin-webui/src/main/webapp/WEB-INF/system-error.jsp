<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/"/>
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="css/login.css">
    <script src="jquery/jquery-2.1.1.min.js"></script>
    <script src="bootstrap/js/bootstrap.min.js"></script>
    <script>
        $(function () {
            $("#goBackBtn").click(function () {
                window.history.back();
            });
        })
    </script>
    <style>

    </style>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
    </div>
</nav>

<div class="container">

    <h2 class="form-signin-heading" style="text-align: center">
        <i class="glyphicon glyphicon-log-in"></i> 尚筹网系统消息
    </h2>

    <!--
        requestScope 对应的是存放 request 域数据的 Map
        requestScope.exception 相当于 request.getAttribute("exception")
        requestScope.exception.message 相当于 exception.getMessage()
    -->
    <h3 style="text-align: center">请求出现错误：</h3>
    <h3 class="text-danger text-center">${requestScope.exception}</h3>
    <br/>
    <button id="goBackBtn" class="btn btn-lg btn-success btn-block" style="width: 150px;margin: 50px auto 0px auto ">点我返回上一步</button>

</div>
</body>
</html>
