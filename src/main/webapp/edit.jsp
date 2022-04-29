<%@ page import="com.oa.bean.Dept" %>
<%@page contentType="text/html;charset=utf-8"%>
<%
  Dept d = (Dept)request.getAttribute("dept");
%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <title>修改部门页面</title>
  </head>
  <body>
    <h1>修改部门</h1>
    <hr />
    <form action="<%=request.getContextPath() %>/dept/modify" method="post">
      部门编号<input type="text" name="deptno" value="<%=d.getDeptno()%>" readonly /><br />
      部门名称<input type="text" name="dname" value="<%=d.getDname()%>" /><br />
      部门位置<input type="text" name="location" value="<%=d.getLocation()%>" /><br />
      <input type="submit" value="修改" /><br />
    </form>
  </body>
</html>
