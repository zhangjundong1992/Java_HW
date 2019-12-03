package utilities;

import java.sql.*;

public class JDBCUtils {

    /*一些静态变量*/
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/bank?serverTimezone=GMT&useUnicode=true&characterEncoding=utf-8";
    private static final String USER = "root";
    private static final String PWD = "root";

    /*获取连接*/
    public static Connection getConnection() {
        try {
            /*注册驱动*/
            Class.forName("com.mysql.cj.jdbc.Driver");
            /*建立连接*/
            return DriverManager.getConnection(CONNECTION_URL, USER, PWD);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /*关闭处理*/
    public static void close(Connection con, Statement statement, ResultSet res) {
        try {
            if (res != null) res.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (statement != null) statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (con != null) con.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
