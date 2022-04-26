package com.oa.action;

import com.oa.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet({"/dept/list","/dept/detail","/dept/delete","/dept/save","/dept/edit","/dept/modify"})
//@WebServlet("/dept/*")
public class DeptServlet extends HttpServlet {


    //参考service源码
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取Servlet path
        String servletPath = request.getServletPath();
        if(servletPath.equals("/dept/list")){
            doList(request,response);
        }else if(servletPath.equals("/dept/detail")){
            doDetail(request,response);
        }else if(servletPath.equals("/dept/delete")){
            doDel(request,response);
        }else if(servletPath.equals("/dept/save")){
            doSave(request,response);
        }else if(servletPath.equals("/dept/edit")){
            doEdit(request,response);
        }else if(servletPath.equals("/dept/modify")){
            doModify(request,response);
        }
    }

    private void doModify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String deptno = request.getParameter("deptno");
        String dname = request.getParameter("dname");
        String location = request.getParameter("location");
        Connection conn = null;
        PreparedStatement ps =  null;
        ResultSet rs = null;
        int count = 0;
        try {
            conn = DBUtil.getConnection();
            String sql = "update dept set dname = ?,location = ? where deptno = ?";
            ps = DBUtil.getPreparedStatement(sql);
            ps.setString(1,dname);
            ps.setString(2,location);
            ps.setString(3,deptno);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn,ps,rs);
        }

        if(count==1){
            response.sendRedirect(request.getContextPath()+"/dept/list");
        }else {
            response.sendRedirect(request.getContextPath()+"/error.html");
        }
    }

    private void doEdit(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

    private void doSave(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //连接数据库，查询所有部门
        request.setCharacterEncoding("UTF-8");
        String deptno = request.getParameter("deptno");
        String location = request.getParameter("location");
        String dname = request.getParameter("dname");

        Connection conn = null;
        PreparedStatement ps =  null;
        ResultSet rs = null;
        int count = 0;
        try {
            DBUtil.getConnection();
            String sql = "insert into dept(deptno,dname,location) values(?,?,?)";
            ps = DBUtil.getPreparedStatement(sql);
            ps.setString(1,deptno);
            ps.setString(2, dname);
            ps.setString(3,location);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn,ps,rs);
        }

        if(count==1){
            response.sendRedirect(request.getContextPath()+"/dept/list");
        }else {
            response.sendRedirect(request.getContextPath()+"/error.html");
        }
    }

    private void doDel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        //获取部门编号
        //http://localhost:8080/oa/dept/detail?deptno=10
        String deptno = request.getParameter("deptno");

        Connection conn = null;
        PreparedStatement ps =  null;
        ResultSet rs = null;
        int count = 0;
        try {
            conn = DBUtil.getConnection();
            //开启事务
            conn.setAutoCommit(false);
            String sql = "delete from dept where deptno = ?";
            ps = DBUtil.getPreparedStatement(sql);
            ps.setString(1,deptno);
            count = ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            if(conn!=null){
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }finally {
            DBUtil.close(conn,ps,rs);
        }

        if(count==1){
            response.sendRedirect(request.getContextPath()+"/dept/list");
        }else {
            response.sendRedirect(request.getContextPath()+"/error.html");
        }
    }

    private void doDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        out.println("		<h1>部门详情</h1>");


        try {
            conn = DBUtil.getConnection();
            String sql = "select dname,location from dept where deptno=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,deptno);
            rs=DBUtil.executeSQL(ps);
            if(rs.next()){
                String dname = rs.getString("dname");
                String location = rs.getString("location");

                out.println("        部门编号:"+deptno+" <br />");
                out.println("        部门名称:"+dname+"<br />");
                out.println("        部门位置:"+location+"<br />");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn,ps,rs);
        }
        out.println("		<input type='button' value='back' onclick='window.history.back()'/>");
        out.println("	</body>");
        out.println("</html>");
    }

    private void doList(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
