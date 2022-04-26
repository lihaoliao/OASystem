package com.oa.action;

import com.oa.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeptListAction extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //连接数据库，查询所有部门
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        //获取应用的根路径
        String path = request.getContextPath();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("	<head>");
        out.println("		<meta charset='utf-8'>");
        out.println("		<title>部门列表页面</title>");

        out.println("        <script type='text/javascript'>");
        out.println("                function del(dno){");
        out.println("            if(window.confirm('确定要删除吗？')){");
        out.println("                document.location.href='"+path+"/dept/delete?deptno='+dno;");
        out.println("            }");
        out.println("        }");
        out.println("	    </script>");

        out.println("	</head>");
        out.println("	<body>");
        out.println("		<h1 align='center'>部门列表</h1>");
        out.println("		<hr />");
        out.println("		<table border='1px' align='center' width='50%'>");
        out.println("			<tr>");
        out.println("				<th>序号</th>");
        out.println("				<th>部门编号</th>");
        out.println("				<th>部门名称</th>");
        out.println("				<th>操作</th>");
        out.println("			</tr>");
        //以上固定输出

        Connection conn = null;
        PreparedStatement ps =  null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select deptno,dname,location from dept";
            ps = DBUtil.getPreparedStatement(sql);
            rs = DBUtil.executeSQL(ps);
            int i=1;
            while(rs.next()){
                String deptno = rs.getString("deptno");
                String dname = rs.getString("dname");
                String location = rs.getString("location");

                out.println("			<tr>");
                out.println("				 <td>"+(i++)+"</td>");
                out.println("				 <td>"+deptno+"</td>");
                out.println("				 <td>"+dname+"</td>");
                out.println("				 <td>");
                out.println("					 <a href='javascript:void(0)' onclick='del("+deptno+")'>删除</a>");
                out.println("					 <a href='"+path+"/dept/edit?deptno="+deptno+"'>修改</a>");
                out.println("					 <a href='"+path+"/dept/detail?deptno="+deptno+"'>详情</a>");
                out.println("				 </td>");
                out.println("			</tr>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn,ps,rs);
        }

        //固定输出
        out.println("		</table>");
        out.println("		<a href='"+path+"/add.html'>新增部门</a>");
        out.println("	</body>");
        out.println("</html>");
    }
}
