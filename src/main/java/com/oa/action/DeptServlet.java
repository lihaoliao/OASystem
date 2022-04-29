package com.oa.action;

import com.oa.bean.Dept;
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
import java.util.ArrayList;

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

    private void doEdit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        //获取部门编号
        //http://localhost:8080/oa/dept/detail?deptno=10
        String deptno = request.getParameter("deptno");

        Connection conn = null;
        PreparedStatement ps =  null;
        ResultSet rs = null;
        Dept d = new Dept();

        try {
            conn = DBUtil.getConnection();
            String sql = "select dname,location from dept where deptno=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,deptno);
            rs=DBUtil.executeSQL(ps);
            if(rs.next()){
                String dname = rs.getString("dname");
                String location = rs.getString("location");
                d.setDeptno(deptno);
                d.setDname(dname);
                d.setLocation(location);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn,ps,rs);
        }
            request.setAttribute("dept",d);
            request.getRequestDispatcher("/edit.jsp").forward(request,response);


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

    private void doDetail(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        //获取部门编号
        //http://localhost:8080/oa/dept/detail?deptno=10
        String deptno = request.getParameter("deptno");

        Connection conn = null;
        PreparedStatement ps =  null;
        ResultSet rs = null;
        Dept dept = new Dept();

        try {
            conn = DBUtil.getConnection();
            String sql = "select dname,location from dept where deptno=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,deptno);
            rs=DBUtil.executeSQL(ps);
            if(rs.next()){
                String dname = rs.getString("dname");
                String location = rs.getString("location");
                dept.setDname(dname);
                dept.setLocation(location);
                dept.setDeptno(deptno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn,ps,rs);
        }
        request.setAttribute("dept",dept);
        request.getRequestDispatcher("/detail.jsp").forward(request,response);

    }

    private void doList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //连接数据库，查询所有部门
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        //获取应用的根路径
        String path = request.getContextPath();

        ArrayList<Dept> depts = new ArrayList<>();
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

                Dept dept = new Dept(deptno,dname,location);
                //存放部门对象
                depts.add(dept);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn,ps,rs);
        }


        //将集合放到请求域当中
        request.setAttribute("deptList",depts);
        //转发
        request.getRequestDispatcher("/list.jsp").forward(request,response);
    }


}
