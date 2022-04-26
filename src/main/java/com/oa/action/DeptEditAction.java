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

public class DeptEditAction extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        //获取部门编号
        //http://localhost:8080/oa/dept/detail?deptno=10
        String deptno = request.getParameter("deptno");

        Connection conn = null;
        PreparedStatement ps =  null;
        ResultSet rs = null;
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("	<head>");
        out.println("		<meta charset='utf-8'>");
        out.println("		<title>部门详情</title>");
        out.println("	</head>");
        out.println("	<body>");
        out.println("		<h1>修改部门详情</h1>");


        try {
            conn = DBUtil.getConnection();
            String sql = "select dname,location from dept where deptno=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,deptno);
            rs=DBUtil.executeSQL(ps);
            if(rs.next()){
                String dname = rs.getString("dname");
                String location = rs.getString("location");

                out.println("<form action='"+request.getContextPath()+"/dept/modify' method='POST'>");
                out.println("   部门编号<input type='text' name='deptno' value='"+deptno+"' readonly/><br>");
                out.println("   部门名称<input type='text' name='dname' value='"+dname+"'/><br>");
                out.println("   部门位置<input type='text' name='location' value='"+location+"'/><br>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn,ps,rs);
        }
        out.println("	<input type='submit' value='修改'/>");
        out.println("   <input type='button' value='取消修改' onclick='window.history.back()'/>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }
}
