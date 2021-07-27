/***************封装自定义 js *****************/
/**
 * 执行分页，生成页面效果，任何时候调用这个函数都会重新加载页面
 */
function generatePage() {

    // 1、获取分页数据
    let pageInfo = getPageInfoRemote();

    // 2、填充表格
    fillTableBody(pageInfo);

    // 搜索后保证输入框有值
    $("#keywordInput").val(window.keyword);

}

/**
 * 远程访问服务器端程序获取 pageInfo 数据
 */
function getPageInfoRemote() {

    let ajaxResult = $.ajax({
        url: "role/get/page/info.json",
        type: "get",
        data: {
            pageNum: window.pageNum,
            pageSize: window.pageSize,
            keyword: window.keyword
        },
        dataType: "json",
        async: false
    });

    // 判断当前响应状态码是否为 200
    let statusCode = ajaxResult.status;

    // 如果当前响应状态码不是 200，说明发生了错误或其他意外情况，显示提示消息，让当前函数停止执行
    if (statusCode != 200) {
        layer.msg("失败！响应状态码=" + statusCode + " 说明信息=" + ajaxResult.statusText);
        return null;
    }

    // 如果响应状态码是 200，说明请求处理成功，获取 pageInfo
    let resultEntity = ajaxResult.responseJSON;

    // 从 resultEntity 中获取 result 属性
    let result = resultEntity.result;

    // 判断 result 是否成功
    if (result == "FAILED") {
        layer.msg(resultEntity.message);
        return null;
    }

    // 确认 result 为成功后获取 pageInfo
    let pageInfo = resultEntity.data;

    // 返回 pageInfo
    return pageInfo;
}

/**
 * 填充表格
 * @param pageInfo
 */
function fillTableBody(pageInfo) {

    // 每次请求要先清楚 tbody 中的数据
    $("#rolePageBody").empty();

    // 这里清空是为了让没有搜索结果时不显示页码导航条
    $("#Pagination").empty();

    // 判断 pageInfo 是否有效
    if(pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0) {
        $("#rolePageBody").append("<tr><td colspan='4' class='text-center'>抱歉！没有查询到您搜索的数据</td></tr>");
        return ;
    }
    
    // 使用 pageInfo 的 list 属性填充 tbody
    for (let i = 0;  i < pageInfo.list.length; i++) {

        let role = pageInfo.list[i];

        let roleId = role.id;

        let roleName = role.name;

        let numberTd = "<td>" + (i+1) + "</td>";
        let checkboxTd = "<td><input id='"+roleId+"' class='itemBox' type='checkbox'></td>";
        let roleNameTd = "<td>"+ roleName +"</td>";

        // 通过 button 标签的 id 属性（别的属性也可以）把 roleId 值传递到 button 按钮的单击响应函数中，在单击响应函数中使用 this.id
        let checkBtn = "<button type='button' id='"+roleId+"' class='btn btn-success btn-xs checkBtn'><i class=' glyphicon glyphicon-check'></i></button>";
        // 通过 button 标签的 id 属性（别的属性也可以）把 roleId 值传递到 button 按钮的单击响应函数中，在单击响应函数中使用 this.id
        let pencilBtn = "<button type='button' id='"+roleId+"' class='btn btn-primary btn-xs pencilBtn'><i class=' glyphicon glyphicon-pencil'></i></button>";
        // 通过 button 标签的 id 属性（别的属性也可以）把 roleId 值传递到 button 按钮的单击响应函数中，在单击响应函数中使用 this.id
        let removeBtn = "<button type='button' id='"+roleId+"' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>";

        let buttonTd = "<td>" + checkBtn + " " + pencilBtn + " " + removeBtn + "</td>";

        let tr = "<tr>" + numberTd + checkboxTd + roleNameTd + buttonTd + "</tr>"

        $("#rolePageBody").append(tr);
    }

    // 生成分页导航条
    generateNavigator(pageInfo);
}

/**
 * 生成分页页码导航条
 */
function generateNavigator(pageInfo) {

    // 获取总记录数
    let totalRecord = pageInfo.total;

    // 声明一个 JSON 对象来存储 Pagination 要设置的属性
    let properties = {
        num_edge_entries: 3,    // 边缘页数
        num_display_entries: 5, // 主体页数
        callback: paginationCallBack,   // 指定用户点击“翻页”的按钮时跳转页面的回调函数
        items_per_page: pageInfo.pageSize,  // 每页要显示的数量
        current_page: pageInfo.pageNum-1, // Pagination 内部使用 pageIndex 来管理页码，pageIndex 从 0 开始，pageNum 从 1 开始，所以要减一
        prev_text: "上一页",   // 上一页按钮上的文本
        next_text: "下一页"    // 下一页按钮上的文本
    };

    // 生成页码导航条
    $("#Pagination").pagination(totalRecord, properties);

}

/**
 * 翻页时的回调函数
 * @param pageIndex
 * @param jQuery
 */
function paginationCallBack(pageIndex, jQuery) {

    // 修改 window 对象的 pageNum 属性
    window.pageNum = pageIndex + 1;

    // 调用分页函数
    generatePage();

    // 取消页码超链接的默认行为
    return false;
}

/**
 * 声明专门的函数显示确认模态框
 */
function showConfirmModal(roleArray) {

    // 打开模态框
    $("#confirmModal").modal('show');

    // 清除旧的数据
    $("#roleNameDiv").empty();

    // 使用全局变量范围创建数组用来存放角色 id
    window.roleIdArray = [];

    // 遍历 roleArray 数组
    for (let i = 0; i < roleArray.length; i++) {
        let role = roleArray[i];
        let roleName = role.roleName;
        $("#roleNameDiv").append(roleName + "<br/>");

        let roleId = role.roleId;
        // 调用数组对象的 push() 方法
        window.roleIdArray.push(roleId);
    }
}

/**
 * 声明专门的函数显示角色拥有哪些权限
 */
function fillAuthTree() {

    // 1、发送 ajax 请求查询 Auth 数据
    let ajaxReturn = $.ajax({
        url: "assign/get/all/auth.json",
        type: "get",
        dataType: "json",
        async: false,
    });

    if(ajaxReturn.status != 200) {
        layer.msg("请求处理出错！响应状态码是：" + ajaxReturn.status + "说明是：" + ajaxReturn.statusText);
        return ;
    }

    // 2、从响应结果中获取 Auth 的 JSON 数据
    // 从服务器端查询到的 list 不需要组装树形结构，这里交给 zTree 去组装
    let authList = ajaxReturn.responseJSON.data;

    // 3、准备对 zTree 进行设置的 JSON 对象
    let setting = {
        data: {
            simpleData: {
                // 开启简单 JSON 共能
                enable: true,

                // 使用 categoryId 属性去关联父节点，不用 pid
                pIdKey: "categoryId"
            },
            // 使用 title 属性显示节点名称，不用默认的 name 属性作为属性名了
            key: {
                name: "title"
            }
        },
        check: {
            enable: true
        }
    };

    // 4、生成树形结构
    // <ul id="authTreeDemo" class="ztree"></ul>
    let zTreeObj = $.fn.zTree.init($("#authTreeDemo"),setting,authList);

    // 调用 zTreeObj 对象的方法，把节点展开
    zTreeObj.expandAll(true);

    // 5、查询已分配的 Auth 的 id 组成的数组
    ajaxReturn = $.ajax({
        url: "assign/get/assigned/auth/id/by/role/id.json",
        type: "get",
        data: {
            roleId: window.roleId
        },
        dataType: "json",
        async: false
    });

    // 判断请求是否成功
    if(ajaxReturn.status != 200) {
        layer.msg("请求处理出错！响应状态码是：" + ajaxReturn.status + "说明是：" + ajaxReturn.statusText);
        return ;
    }

    // 从响应结果中获取 authIdArray
    let authIdArray = ajaxReturn.responseJSON.data;

    // 6、根据 authIdArray 把树形结构中对应的节点勾选上
    // ① 遍历 authIdArray 数组
    for (let i = 0; i < authIdArray.length; i++) {
        let authId = authIdArray[i];

        // ② 根据 id 查询树形结构中对应的节点
        let treeNode = zTreeObj.getNodeByParam("id",authId);

        // ③ 将 treeNode 设置为被勾选
        // checked 设置为 true 表示节点勾选
        let checked = true;

        // checkTypeFlag 设置为 false，表示不“联动”，不联动为了避免把不该勾选的勾选上
        let checkTypeFlag = false;
        // 执行勾选
        zTreeObj.checkNode(treeNode,checked,checkTypeFlag);
    }

}

