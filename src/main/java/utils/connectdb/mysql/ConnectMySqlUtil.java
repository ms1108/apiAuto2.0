package utils.connectdb.mysql;

import lombok.SneakyThrows;

import java.sql.*;

public class ConnectMySqlUtil {
    private Connection conn = null;
    private Statement stmt = null;

    @SneakyThrows
    public ResultSet executeQuery(ConnectEnum connectEnum, String sql) {
        connect(connectEnum);
        ResultSet resultSet = stmt.executeQuery(sql);
        return resultSet;
    }


    public void connect(ConnectEnum connectEnum) {

        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 打开链接
            conn = DriverManager.getConnection(connectEnum.getDbUrl(), connectEnum.getAccount(), connectEnum.getPwd());

            stmt = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SneakyThrows
    public void close() {
        // 完成后关闭
        stmt.close();
        conn.close();
    }

    public static void main(String[] args) {
        ConnectMySqlUtil connectMySqlUtil = new ConnectMySqlUtil();
        ResultSet rs = connectMySqlUtil.executeQuery(ConnectEnum.Force, "SELECT * FROM db");
        try {
            while (rs.next()) {
                System.out.println(rs.getString("Db"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost:3306/mysql?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";


        // 数据库的用户名与密码，需要根据自己的设置
        String USER = "root";
        String PASS = "root";


        Connection conn = null;
        Statement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM db";
             rs = stmt.executeQuery(sql);

            // 展开结果集数据库
            while (rs.next()) {
                System.out.println(rs.getString("Db"));
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

}
