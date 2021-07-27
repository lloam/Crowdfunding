<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="include-head.jsp" %>
<link rel="stylesheet" href="css/pagination.css">
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<link rel="stylesheet" href="/ztree/zTreeStyle.css" />
<script type="text/javascript" src="/ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="/crowd/my-role.js"></script>
<script type="text/javascript">

    $(function (){

        // 1、为分页操作准备初始化数据
        window.pageNum = 1;
        window.pageSize = 5;
        window.keyword = "";

        // 2、调用执行分页的函数，显示分页效果
        generatePage();

        // 3、给查询按钮绑定单击响应函数
        $("#searchBtn").click(function () {

            // ① 获取关键词数据复制给对应的全局变量
            window.keyword = $("#keywordInput").val();

            // 调用分页函数刷新页面
            generatePage();
        });

        // 4、点击新增显示模态框
        $("#showAddModalBtn").click(function () {
            $("#addRoleModal").modal('show');
        });

        // 5、点击模态框的添加按钮进行添加 role
        $("#saveRoleBtn").click(function () {

            // ① 获取用户在文本框中输入的角色名称
            // #addRoleModal 表示找到整个模态框
            // 空格表示后代元素中继续查找
            // [name=roleName] 表示匹配 name 属性等于 roleName 的元素
            let roleName = $("#addRoleModal [name=addRoleName]").val();

            $.ajax({
                url: "role/save.json",
                data: {
                    name: roleName
                },
                type: "post",
                dataType: "json",
                success: function (response){

                    let result = response.result;

                    if(result == "SUCCESS") {
                        layer.msg("操作成功！");
                    }else {
                        layer.msg("操作失败！" + response.message);
                    }

                    // 将页码定位到最后一页
                    window.pageNum = 999999;

                    // 重新加载分页数据
                    generatePage();
                },
                error: function (response){
                    layer.msg(response.status + " " + response.statusText);
                }
            });

            // 清理模态框的 input 值
            $("addRoleModal [name=addRoleName]").val("");

        });

        // 6、给页面上的”铅笔“按钮绑定单机响应函数
        // $(".pencilBtn").click(function () {
        //     alert("dfa");
        // });
        // 使用 jQuery 对象的 on() 函数可以解决
        /**
         * click：事件类型
         * pencilBtn：真正要绑定事件的元素选择器
         * function：事件响应函数
         */
        $("#rolePageBody").on("click",".pencilBtn",function (){

            // 打开模态框
            $("#updateRoleModal").modal('show');

            // 获取表格中当前行中的角色名称
            let roleName = $(this).parent().prev().text();

            // 获取当前角色的 id
            // 依据是：let pencilBtn = "<button type='button' id='"+roleId+"'，这段代码中把 roleId 设置成了 id 属性
            // 为了执行更新的按钮能够获取到 roleId 的值，把它放在全局变量上
            window.roleId = this.id;

            // 使用 roleName 的值设置模态框的 文本框
            $("#updateRoleModal [name=updateRoleName]").val(roleName);
        });

        // 7、给更新模态框中的更新按钮绑定单击响应函数
        $("#updateRoleBtn").click(function () {

            // 获取到修改的 roleName 的值
            let roleName = $("#updateRoleModal [name=updateRoleName]").val();

            let role = {
                id: window.roleId,
                name: roleName
            }
            role = JSON.stringify(role);
            $.ajax({
                url: "role/update.json",
                data: role,
                type: "put",
                contentType: "application/json",
                dataType: "json",
                success: function (response){

                    // 获取响应结果
                    let result = response.result;

                    if(result == "SUCCESS") {
                        layer.msg("操作成功！");
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

        //8、 点击确认模态框中的删除按钮响应函数
        $("#removeRoleBtn").click(function () {

            // 从全局变量范围获取 roleIdArray，转换为 JSON 字符串
            let roleIdList = JSON.stringify(window.roleIdArray);

            $.ajax({
                url: "role/delete/by/role/id/array.json",
                type: "delete",
                data: roleIdList,
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                success: function (response){

                    let result = response.result;

                    if(result == "SUCCESS") {
                        layer.msg("操作成功！");
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

            // 如果是全选删除的话需要将全选按钮的状态设为不选定
            $("#summeryBox").prop("checked",false);
        });

        // 9、给单条删除按钮绑定单击响应函数
        $("#rolePageBody").on("click",".removeBtn",function (){

            // 从当前按钮出发获取角色名称
            let roleName = $(this).parent().prev().text();

            // 将要单个删除的 roleId 封装成 数组
            let roleArray = [
                {
                    roleId: this.id,
                    roleName: roleName
                }
            ];

            // 调用专门的函数打开模态框
            showConfirmModal(roleArray);
        });

        // 10、给总的 checkBox 绑定单击响应函数
        $("#summeryBox").click(function (){

            // ① 获取当前多选框的状态
            let currentStatus = this.checked;

            // ② 用当前多选框的状态设置其他多选框
            $(".itemBox").prop("checked",currentStatus);

        });

        // 11、全选、全不选的反向操作
        $("#rolePageBody").on("click",".itemBox",function () {

            // 获取当前已经选中的 .itemBox 的数量
            let checkedBoxCount = $(".itemBox:checked").length;

            // 获取全部 .itemBox 的数量
            let totalBoxCount = $(".itemBox").length;

            // 使用二者比较结果设置全选按钮的状态
            $("#summeryBox").prop("checked",checkedBoxCount == totalBoxCount);
        });

        // 12、给批量删除的按钮绑定单击响应函数
        $("#batchRemoveBtn").click(function () {

            // 存储 role 对象的数组
            let roleArray = [];

            // 遍历当前选中的多选框
            $(".itemBox:checked").each(function () {

                // 使用 this 引用当前遍历得到的多选框
                let roleId = this.id;

                // 通过 DOM 操作获取角色名称
                let roleName = $(this).parent().next().text();

                // 将 role 对象加入 roleArray 数组
                roleArray.push({
                    roleId: roleId,
                    roleName: roleName
                });
            });

            // 检查 roleArray 的长度是否为 0
            if(roleArray.length == 0) {
                layer.msg("请至少选择一个需要删除的角色");
                return ;
            }

            // 调用专门的函数打开模态框
            showConfirmModal(roleArray);

        });

        // 13、给分配权限的图标按钮绑定单击响应函数
        $("#rolePageBody").on("click",".checkBtn",function () {

            // 把当前角色 id 存入全局变量
            window.roleId = this.id;

            // 打开模态框
            $("#assignModal").modal('show');

            // 在模态框中转载树形结构的数据 Auth
            fillAuthTree();
        });

        // 14、给分配权限模态框中的分配按钮绑定单击响应函数
        $("#assignBtn").click(function () {

            // ① 收集树形结构的各个节点中被勾选的节点
            // 【1】 声明一个数组用于存放已勾选权限的 id
            let authIdArray = [];

            // 【2】 获取 zTreeObj 对象
            let zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");

            // 【3】 获取全部已勾选的节点
            let checkedNodes = zTreeObj.getCheckedNodes();

            // 【4】 遍历 checkedNodes 全部已经勾选的节点，得到勾选节点权限的 id，并存入 authIdArray
            for (let i = 0; i < checkedNodes.length; i++) {
                // 获取已勾选的权限节点
                let checkedNode = checkedNodes[i];

                // 获取权限 id
                let authId = checkedNode.id;

                // 放入权限 id 数组
                authIdArray.push(authId);
            }

            // ② 发送请求执行分配
            let authIdRoleIdDto = {
                authIdArray: authIdArray,
                roleId: window.roleId
            }

            // 转换为 json 字符串
            authIdRoleIdDto = JSON.stringify(authIdRoleIdDto);

            // 发送 ajax 请求
            $.ajax({
                url: "assign/do/role/assign/auth.json",
                type: "post",
                data: authIdRoleIdDto,
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                success: function (response){

                    let result = response.result;

                    if(result == "SUCCESS") {
                        layer.msg("操作成功");
                    }else {
                        layer.msg("操作失败" + response.message);
                    }
                },
                error: function (response){
                    layer.msg(response.status + " " + response.statusText);
                }
            });
        });
    });

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
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keywordInput" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button id="batchRemoveBtn" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button type="button" id="showAddModalBtn" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="summeryBox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody">
                            <tr>
                                <td>1</td>
                                <td><input type="checkbox"></td>
                                <td>PM - 项目经理</td>
                                <td>
                                    <button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>
                                    <button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>
                                    <button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>
                                </td>
                            </tr>
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
<%@include file="modal-role-add.jsp"%>
<%@include file="modal-role-update.jsp"%>
<%@include file="modal-role-confirm.jsp"%>
<%@include file="modal-role-assign-auth.jsp"%>
</body>
</html>

