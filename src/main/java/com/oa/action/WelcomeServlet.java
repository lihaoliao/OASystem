package com.oa.action;

import com.oa.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        String username = null;
        String password = null;
        if(cookies!=null){
            for(Cookie c:cookies){
                String name = c.getName();
                if("username".equals(name)){
                    username = c.getValue();
                }
                else if("password".equals(name)){
                   password = c.getValue();
                }
            }
            if(username!=null&&password!=null){
                //验证用户名和密码是否正确
                Connection conn = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                Boolean flag = false;
                try {
                    conn = DBUtil.getConnection();
                    String sql = "Select * from t_user where username=? and password = ?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1,username);
                    ps.setString(2,password);
                    rs = ps.executeQuery();
                    if(rs.next()){
                        //登陆成功
                        flag = true;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }finally {
                    DBUtil.close(conn,ps,rs);
                }
                if(flag){
                    //获取session
                    HttpSession session = request.getSession();
                    session.setAttribute("username",username);
                    response.sendRedirect(request.getContextPath()+"/dept/list");
                }else {
                    response.sendRedirect(request.getContextPath()+"/index.jsp");
                }

            }else {
                response.sendRedirect(request.getContextPath()+"/index.jsp");
            }
            //欢迎页面
        }else {
            //登陆页面
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        }
    }
}
