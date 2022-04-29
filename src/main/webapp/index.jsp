<%@page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <title>欢迎使用OA系统</title>
  </head>
  <body>
    <% String path = request.getContextPath(); %>
    <a href="<%= path %>/dept/list">查看部门列表</a>
  </body>
</html>
