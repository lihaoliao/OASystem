<%@page contentType="text/html;charset=utf-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <title>新增部门</title>
    <h3>欢迎用户:${username}!</h3>
    <%--设置整个页面的基础路径 --%>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
  </head>
  <body>
    <h1>新增部门</h1>
    <hr />
    <form action="dept/save" method="post">
      部门编号<input type="text" name="deptno" /><br />
      部门名称<input type="text" name="dname" /><br />
      部门位置<input type="text" name="location" /><br />
      <input type="submit" value="保存" /><br />
    </form>
  </body>
</html>
