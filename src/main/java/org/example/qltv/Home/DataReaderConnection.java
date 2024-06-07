package org.example.qltv.Home;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataReaderConnection{ // singleton pattern

    private static DataReaderConnection instance;

    private final static String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/borrow_return_mana";
    private final static String username = "root";
    private final static String password = "123456";

    private Connection connection; // tạo đối tượng kết nối tới cơ sở dữ liệu

    private DataReaderConnection() {
        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);// kết nối đến cơ sở dữ liệu
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static DataReaderConnection getInstance() {
        if(instance == null) {//kiểm tra xem có đối tượng nào được tạo chưa
            synchronized (DataReaderConnection.class) {  // dùng để khoá đoạn code bên trong nó
                if (instance == null) {  // kiểm tra xem có đối tượng nào được tạo chưa
                    instance = new DataReaderConnection();  // nếu chưa thì tạo 1 đối tượng
                }
            }
        }
        return instance;
    }

    public ResultSet dbSelect (String sql) { // nhung cau query co tra ve gia tri select
        try {
            //Statement statement = connection.prepareStatement(sql);
            Statement statement = connection.createStatement();
            /*
Tạo một đối tượng Statement từ đối tượng connection đã được khai báo trước đó (đối tượng kết nối đến cơ sở dữ liệu).
Statement là một giao diện trong JDBC (Java Database Connectivity) cho phép thực thi các câu lệnh SQL.
         */
            ResultSet resultSet = statement.executeQuery(sql);  // câu lệnh dùng để thực thi câu truy vấn sql truền vào
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void dbAdd(String sql) { // nhung cau query khong tra ve gia tri // update, insert, delete
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Connection getConnection() {
        return this.connection;
    }

}

