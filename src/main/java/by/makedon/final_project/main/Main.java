package by.makedon.final_project.main;

import by.makedon.final_project.connectionpool.ConnectionPool;
import by.makedon.final_project.connectionpool.ProxyConnection;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        ConnectionPool.getInstance().getConnection();
        ConnectionPool.getInstance().getConnection();
        ConnectionPool.getInstance().getConnection();
        ConnectionPool.getInstance().getConnection();
        ConnectionPool.getInstance().getConnection();
        ConnectionPool.getInstance().getConnection();
        ConnectionPool.getInstance().getConnection();
        ConnectionPool.getInstance().getConnection();
        ConnectionPool.getInstance().getConnection();
        ConnectionPool.getInstance().getConnection();
        ConnectionPool.getInstance().getConnection();
        ConnectionPool.getInstance().getConnection();
        ConnectionPool.getInstance().getConnection();
        ConnectionPool.getInstance().getConnection();
        ConnectionPool.getInstance().getConnection();
        ConnectionPool.getInstance().getConnection();
        ConnectionPool.getInstance().getConnection();
        ProxyConnection proxyConnection1 = ConnectionPool.getInstance().getConnection();
        ProxyConnection proxyConnection2 = ConnectionPool.getInstance().getConnection();
        try {
            proxyConnection1.close();
            proxyConnection2.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionPool.getInstance().getConnection();
        ConnectionPool.getInstance().getConnection();
        System.out.println("1");
    }
}
