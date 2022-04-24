package com.oa.utils;

import java.sql.*;
import java.util.ResourceBundle;

public class DBUtil {
    //属性资源文件绑定
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("jdbc");
    private static String driver = resourceBundle.getString("driver");
    private static String url = resourceBundle.getString("url");
    private static String name = resourceBundle.getString("user");
    private static String password = resourceBundle.getString("password");

    static {
        //注册驱动
        try {
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws SQLException {
        //获取链接
        Connection conn = DriverManager.getConnection(url,name,password);
        return conn;
    }

    public static PreparedStatement getPreparedStatement(String sql) throws SQLException {
        //获取数据库操作对象
        return getConnection().prepareStatement(sql);
    }

    public static ResultSet executeSQL(PreparedStatement ps) throws SQLException {
        return ps.executeQuery();
    }

    public static void close(Connection conn,Statement statement,ResultSet rs){
        //释放资源
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(statement!=null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            if(conn!=null)
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
