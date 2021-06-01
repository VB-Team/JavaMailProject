/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author schea
 */
public class DbContext {
    
    
    // DB Exception , Port check
    static Connection connection;
    static String fullurl = "jdbc:sqlserver://localhost:1453;databasename=MailServer;user=sa;password=6165";
    static String conurl = "jdbc:sqlserver://localhost:1453;databasename=MailServer";

    static String user = "sa";
    static String batuPass = "Password1!";
    static String veyselPass = "6165";

    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection(conurl, user, batuPass);
        } catch (SQLException e) {
            System.out.println("DbContext Exception : " + e.getMessage());
        }
        return connection;
    }

    public int getUserId(String userName) {
        try {
            PreparedStatement statement;
            connection = DriverManager.getConnection(conurl, user, batuPass);
            String selectQuery = "Select u.Id from Users u where u.UserName=?";
            statement = connection.prepareStatement(selectQuery);
            statement.setString(1, userName);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) 
                return rs.getInt(1);
            else
                return -1;
        } catch (Exception e) {
             System.out.println("DbContext Exception : " + e.getMessage());
             return -1;
        }
    }
     public String getUserName(int userId) {
        try {
            PreparedStatement statement;
            connection = DriverManager.getConnection(conurl, user, batuPass);
            String selectQuery = "Select u.UserName from Users u where u.Id=?";
            statement = connection.prepareStatement(selectQuery);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) 
                return rs.getString("UserName");
            else
                return null;
        } catch (Exception e) {
             System.out.println("DbContext Exception : " + e.getMessage());
             return null;
        }
    }
}
