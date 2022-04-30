
<%@page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <title>欢迎使用OA系统</title>
  </head>
  <body>
    <%-- String path = request.getContextPath(); %>
    <a href="<%= path %>/dept/list">查看部门列表</a>--%>
    <h1>用户登录</h1>
    <hr>
    <form action="<%=request.getContextPath()%>/user/login" method="POST">
		username: <input type="text" name="username" /><br>
		password: <input type="password" name="password"  /><br>
        <input type="checkbox" name="remember" value="1">十天内免登录<br>
		<input type="submit" value="login">
	</form>
  </body>
</html>
