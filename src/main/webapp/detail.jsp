<%@ page import="com.oa.bean.Dept" %>
<%@page contentType="text/html;charset=utf-8"%>
<%
  Dept d = (Dept)request.getAttribute("dept");
%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <title>部门详情</title>
  </head>
  <body>
    <h1>部门详情</h1>
    部门编号:<%=d.getDeptno()%> <br />
    部门名称:<%=d.getDname()%><br />
    部门位置:<%=d.getLocation()%><br />
    <input type="button" value="back" onclick="window.history.back()" />
  </body>
</html>
