<%@page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*"%>
<%@page import="com.oa.bean.Dept"%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <title>部门列表页面</title>
  </head>
  <body>
    <script type="text/javascript">
      function del(dno) {
        if (window.confirm("确定要删除吗？")) {
          document.location.href = "<%=request.getContextPath() %>/dept/delete?deptno=" + dno;
        }
      }
    </script>
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

       <%
           //从request域取出集合
           ArrayList<Dept> depts =(ArrayList<Dept>)request.getAttribute("deptList");
           int i=1;
           for(Dept d:depts){
                //out.write(d.getName());
       %>
            <tr>
                    <td><%=i++%></td>
                    <td><%=d.getDeptno()%></td>
                    <td><%=d.getDname()%></td>
                    <td>
                      <a href="javascript:void(0)" onclick="del(<%=d.getDeptno()%>)">删除</a>
                      <a href="<%=request.getContextPath() %>/dept/edit?deptno=<%=d.getDeptno()%>">修改</a>
                      <a href="<%=request.getContextPath() %>/dept/detail?deptno=<%=d.getDeptno()%>">详情</a>
                    </td>
                  </tr>
       <%
           }
       %>


      <!--以下是固定代码-->
    </table>
    <a href="<%=request.getContextPath() %>/add.jsp">新增部门</a>
  </body>
</html>
