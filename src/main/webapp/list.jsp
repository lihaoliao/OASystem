<%@page contentType="text/html;charset=utf-8" isELIgnored="false" %>
<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <title>部门列表页面</title>
      <%--设置整个页面的基础路径,只会对路径中没有以“/”开头的路径有效 --%>
      <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
  </head>
  <body>
    <script type="text/javascript">
      function del(dno) {
        if (window.confirm("确定要删除吗？")) {
          document.location.href = "${pageContext.request.contextPath}dept/delete?deptno=" + dno;
        }
      }
    </script>
    <h3>欢迎用户:${username}!</h3>
    <a href="user/logout">退出登录</a>
    <h1 align="center">部门列表</h1>
    <hr />
    <table border="1px" align="center" width="50%">
      <tr>
        <th>序号</th>
        <th>部门编号</th>
        <th>部门名称</th>
        <th>操作</th>
      </tr>
      <!--以上是固定代码-->
        <core:forEach items="${deptList}" var="d" varStatus="i">
            <tr>
                    <td>${i.count}</td>
                    <td>${d.deptno}</td>
                    <td>${d.dname}</td>
                    <td>
                      <a href="javascript:void(0)" onclick="del(${d.deptno})">删除</a>
                      <a href="${pageContext.request.contextPath}/dept/edit?deptno=${d.deptno}">修改</a>
                      <a href="${pageContext.request.contextPath}/dept/detail?deptno=${d.deptno}">详情</a>
                    </td>
                  </tr>
        </core:forEach>
      <!--以下是固定代码-->
    </table>
    <a href="add.jsp">新增部门</a>
  </body>
</html>
