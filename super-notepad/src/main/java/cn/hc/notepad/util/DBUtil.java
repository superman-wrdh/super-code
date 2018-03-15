package cn.hc.notepad.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    public static void main(String[] args) {
        DBUtil dbUtil = new DBUtil();
        try {
            Connection con =  dbUtil.con();
            dbUtil.DBclose(con);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public Connection con () throws Exception
    {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/notepad", "root", "19931015");
        System.out.println("连接成功");
        return con;
    }

    public  void DBclose(Connection con) throws SQLException {
        if (con!=null) {

            System.out.println();
            System.out.println("数据库关闭");
            con.close();
        }
    }
}

