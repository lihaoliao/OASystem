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

public class DeptSaveAction extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            request.getRequestDispatcher("/dept/list").forward(request,response);
        }else {
            request.getRequestDispatcher("/error.html").forward(request,response);
        }
    }
}
