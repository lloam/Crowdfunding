<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
    <!-- http://localhost:8080/test/ssm.html -->
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />
      <%@include file="WEB-INF/include-head.jsp"%>
    <script>
      $(function (){
        $("#btn1").click(function (){
          // ajax 请求
          $.ajax({
            url: "send/array/one.html",        // 请求目标资源的地址
            type: "post",                  // 请求方式
            data: {
              array: [5,8,12]
            },                // 要发送的请求参数
            dataType: "text",              // 如何对待服务器端返回的数据
            success: function (response) {     // 服务器端成功处理请求后调用的回调函数，response 是响应体
              alert(response);
            },
            error: function (response) {      // 服务器端处理请求失败后调用的回调函数，response 是响应体
              alert(response);
            }
          });
        });
        $("#btn2").click(function (){
            // ajax 请求
            $.ajax({
                url: "send/array/two.html",        // 请求目标资源的地址
                type: "post",                  // 请求方式
                data: {
                    "array[0]": 5,
                    "array[1]": 8,
                    "array[2]": 12
                },                // 要发送的请求参数
                dataType: "text",              // 如何对待服务器端返回的数据
                success: function (response) {     // 服务器端成功处理请求后调用的回调函数，response 是响应体
                    alert(response);
                },
                error: function (response) {      // 服务器端处理请求失败后调用的回调函数，response 是响应体
                    alert(response);
                }
            });
        });
        $("#btn3").click(function (){

              // 准备好要发送到服务器端的数组
              let array = [5,8,12];
              console.log(array.length);
              // 将 JSON 数组转换为 JSON 字符串
              let requestBody = JSON.stringify(array);
              console.log(requestBody.length);

              // ajax 请求
              $.ajax({
                  url: "send/array/three.html",        // 请求目标资源的地址
                  type: "post",                  // 请求方式
                  data: requestBody,                // 要发送的请求参数
                  contentType: "application/json;charset=UTF-8",  // 设置请求体的内容类型
                  dataType: "text",              // 如何对待服务器端返回的数据
                  success: function (response) {     // 服务器端成功处理请求后调用的回调函数，response 是响应体
                      alert(response);
                  },
                  error: function (response) {      // 服务器端处理请求失败后调用的回调函数，response 是响应体
                      alert(response);
                  }
              });
          });
        $("#btn4").click(function () {
           // 准备发送的数据
           let student = {
               stuId: 5,
               stuName: "tom",
               address: {
                   province: "广东",
                   city: "深圳",
                   street: "后瑞"
               },
               subjectList: [
                   {
                       subjectName: "JavaSE",
                       subjectScore: 100
                   },{
                       subjectName: "SSM",
                       subjectScore: 99
                   }
               ],
               map: {
                   k1: "v1",
                   k2: "v2"
               }
           }
           // 将 JSON 对象转换为 JSON 字符串
           let requestBody = JSON.stringify(student);

           // 发送 Ajax 请求
           $.ajax({
               url: "send/compose/object.json",
               type: "post",
               data: requestBody,
               contentType: "application/json;charset=UTF-8",
               dataType: "json",
               success: function (response) {
                   console.log(response);
               },
               error: function (response) {
                   console.log(response);
               }
           })
       });

        $("#btn5").click(function () {
            layer.msg("test layer");
        });

        $("#btn6").click(function (){
            layer.msg("删除成功");
        });
      })
    </script>
  </head>
  <body>

      <a href="test/ssm.html">测试 ssm 整合环境</a>

      <br />
      <br />

      <button id="btn1">Send [5,8,12] One</button>

      <br />
      <br />

      <button id="btn2">Send [5,8,12] Two</button>

      <br />
      <br />

      <button id="btn3">Send [5,8,12] Three</button>

      <br />
      <br />

      <button id="btn4">Send Compose Object</button>

      <br />
      <br />

      <button id="btn5">点我弹框</button>

      <!-- Small modal -->
      <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">Small modal</button>

      <div class="modal fade" id="myModal" tabindex="-1" role="dialog">
          <div class="modal-dialog" role="document">
              <div class="modal-content">
                  <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                      <h4 class="modal-title">删除管理员</h4>
                  </div>
                  <div class="modal-body">
                      <p>确定删除xxx管理员</p>
                  </div>
                  <div class="modal-footer">
                      <button id="btn6" type="button" class="btn btn-danger" data-dismiss="modal">确定删除</button>
                  </div>
              </div>
          </div>
      </div>
  </body>
</html>
