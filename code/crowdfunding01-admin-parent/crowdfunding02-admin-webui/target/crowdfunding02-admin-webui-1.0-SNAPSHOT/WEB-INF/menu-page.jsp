<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="include-head.jsp" %>
<link rel="stylesheet" href="/ztree/zTreeStyle.css" />
<script type="text/javascript" src="/ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="/crowd/my-menu.js"></script>
<script type="text/javascript">

    $(function () {

        // 1、调用专门封装好的函数初始化树形结构
        generateTree();

        // 2、给添加子节点按钮绑定单击响应函数
        $("#treeDemo").on("click",".addBtn",function () {

            // 将当前节点的 id，作为新节点的 pid 保存到全局变量中
            window.pid = this.id;

            // 打开模态框
            $("#menuAddModal").modal('show');
           return false;
        });

        // 3、给添加子节点的模态框中的保存按钮绑定单击响应函数
        $("#menuSaveBtn").click(function () {

            // 收集表单项中用户输入的数据
            let name = $.trim($("#menuAddModal [name=name]").val())
            let url = $.trim($("#menuAddModal [name=url]").val())
            // 单选按钮要定位到被选中的那一个
            let icon = $.trim($("#menuAddModal [name=icon]:checked").val())

            // 封装 menu 对象，与后端 java 对象相对应
            let menu = {
                pid: window.pid,
                name: name,
                url: url,
                icon: icon
            };

            // 将 menu 对象转换成 JSON 对象
            menu = JSON.stringify(menu);

            // 发送 ajax 请求
            $.ajax({
                url: "menu/save.json",
                type: "post",
                data: menu,
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                success: function (response){

                    let result = response.result;

                    if(result == "SUCCESS") {
                        layer.msg("操作成功！");

                        // 重新获取添加后的树形结构，要在确认服务器端完成保存后重新加载树形结构
                        generateTree();
                    }else {
                        layer.msg("操作失败！" + response.message);
                    }

                    // 重新加载分页数据
                    generatePage();
                },
                error: function (response){
                    layer.msg(response.status + " " + response.statusText);
                }
            });

            // 清空表单
            // jQuery 对象调用，里面不传任何参数，相当于用户点击了一下
            $("#menuResetBtn").click();
        });

        // 4、给编辑按钮绑定单击响应函数
        $("#treeDemo").on("click",".editBtn",function () {

            // 将当前结点的 id 保存到全局变量
            window.id = this.id;

            // 打开模态框
            $("#menuEditModal").modal('show');

            // 获取 zTreeObj 对象
            let zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");

            // 根据 id 属性查询节点对象
            // 用来搜索节点的属性名
            let key = "id";

            // 用来搜索节点的属性值
            let value = window.id;

            // 得到当前节点
            let currentNode = zTreeObj.getNodeByParam(key,value);

            // 回显表单数据
            $("#menuEditModal [name=name]").val(currentNode.name);
            $("#menuEditModal [name=url]").val(currentNode.url);

            // 回显 radio 可以这样理解：被选中的 radio 的 value 属性可以组成一个数组
            $("#menuEditModal [name=icon]").val([currentNode.icon]);

            return false;
        });

        // 5、给更新模态框中的更新按钮绑定单击响应函数
        $("#menuEditBtn").click(function () {

            // 收集表单项中用户输入的数据
            let name = $.trim($("#menuEditModal [name=name]").val())
            let url = $.trim($("#menuEditModal [name=url]").val())
            // 单选按钮要定位到被选中的那一个
            let icon = $.trim($("#menuEditModal [name=icon]:checked").val())

            // 封装 menu 对象，与后端 java 对象相对应
            let menu = {
                id: window.id,
                name: name,
                url: url,
                icon: icon
            };

            // 将 menu 对象转换成 JSON 对象
            menu = JSON.stringify(menu);

            // 发送 ajax 请求
            $.ajax({
                url: "menu/update.json",
                type: "put",
                data: menu,
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                success: function (response){

                    let result = response.result;

                    if(result == "SUCCESS") {
                        layer.msg("操作成功！");

                        // 重新获取添加后的树形结构，要在确认服务器端完成保存后重新加载树形结构
                        generateTree();
                    }else {
                        layer.msg("操作失败！" + response.message);
                    }

                    // 重新加载分页数据
                    generatePage();
                },
                error: function (response){
                    layer.msg(response.status + " " + response.statusText);
                }
            });
        });

        // 6、给 “x” 按钮绑定单击响应函数
        $("#treeDemo").on("click",".removeBtn",function () {

            // 将当前结点的 id 保存到全局变量
            window.id = this.id;

            // 打开模态框
            $("#menuConfirmModal").modal('show');

            // 获取 zTreeObj 对象
            let zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");

            // 根据 id 属性查询节点对象
            // 用来搜索节点的属性名
            let key = "id";

            // 用来搜索节点的属性值
            let value = window.id;

            // 得到当前节点
            let currentNode = zTreeObj.getNodeByParam(key,value);

            $("#removeNodeSpan").html("【<i class='"+currentNode.icon+"'></i>" + currentNode.name + "】");

            return false;

        })

        // 7、给删除确认模态框中的 ok 按钮绑定单击响应函数
        $("#confirmBtn").click(function () {

            // 将全局的当前节点的 id 转换成 JSON 字符串
            let id = JSON.stringify(window.id);

            // 发送 ajax 请求
            $.ajax({
                url: "menu/remove.json",
                type: "delete",
                data: id,
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                success: function (response){

                    let result = response.result;

                    if(result == "SUCCESS") {
                        layer.msg("操作成功！");

                        // 重新获取添加后的树形结构，要在确认服务器端完成保存后重新加载树形结构
                        generateTree();
                    }else {
                        layer.msg("操作失败！" + response.message);
                    }

                    // 重新加载分页数据
                    generatePage();
                },
                error: function (response){
                    layer.msg(response.status + " " + response.statusText);
                }
            });
        });
    })

</script>
<body>
<%@include file="include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i>
                    </div>
                </div>
                <div class="panel-body">
                    <%-- 这个 ul 标签是 zTree 动态生成的节点所依附的静态节点 --%>
                    <ul id="treeDemo" class="ztree">
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="modal-menu-add.jsp"%>
<%@include file="modal-menu-edit.jsp"%>
<%@include file="modal-menu-confirm.jsp"%>
</body>
</html>

