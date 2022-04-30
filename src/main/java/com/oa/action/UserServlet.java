package com.oa.action;

import com.oa.utils.DBUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.CollationKey;

@WebServlet({"/user/login","/user/logout"})
public class UserServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if(servletPath.equals("/user/login")){
            doLogin(request,response);
        }else if (servletPath.equals("/user/logout")){
            doLogout(request,response);
        }
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //销毁session对象
        HttpSession session = request.getSession(false);
        if(session!=null){
            //手动销毁
            session.invalidate();
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        }
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //前端提交的数据username=admin&password=123456
        //获取用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //连接数据库获取用户名和密码
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Boolean flag = false;
        String remember = request.getParameter("remember");
        //登陆成功且选了免登录功能
        if("1".equals(remember)){
            Cookie cookie = new Cookie("username",username);
            Cookie cookie1 = new Cookie("password",password);
            cookie.setMaxAge(60 * 60 * 24 * 10);
            cookie1.setMaxAge(60 * 60 * 24 * 10);
            cookie.setPath("/oa");
            cookie1.setPath("/oa");
            response.addCookie(cookie);
            response.addCookie(cookie1);
        }
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from t_user where username = ? and password = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);
            rs = ps.executeQuery();
            if(rs.next()){
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn,ps,rs);
        }
        if(flag){
            HttpSession session = request.getSession();
            session.setAttribute("username",username);
            response.sendRedirect(request.getContextPath()+"/dept/list");
        }else {
            response.sendRedirect(request.getContextPath()+"/loginFail.jsp");
        }
    }
}
