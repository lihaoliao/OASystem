
<%@page contentType="text/html;charset=utf-8" isELIgnored="false" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <title>部门详情</title>

  </head>
  <body>
  <h3>欢迎用户:${username}!</h3>
    <h1>部门详情</h1>
    部门编号:${dept.deptno} <br />
    部门名称:${dept.dname}<br />
    部门位置:${dept.location}<br />
    <input type="button" value="back" onclick="window.history.back()" />
  </body>
</html>
