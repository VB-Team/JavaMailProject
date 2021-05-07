/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.services.authenticate;

import com.vbteam.models.User;
import com.vbteam.utils.DbContext;
/**
 *
 * @author schea
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;


public class AuthService implements IAuthService {
    DbContext context;
    Connection connection;
    
    
    @Override
    public User Login(String UserName, String Password) {
        try {
        PreparedStatement statement;
        context=new DbContext();
        connection=context.getConnection();         
        String query="Select u.Id,u.UserName,u.Password,ud.FirstName,ud.LastName,ur.Role,u.RegisterDate\n" +
        "From Users u join UserDetail ud on ud.UserId=u.Id\n" +
        "join UserRoles ur on u.RoleId=ur.Id \n" +
        "where u.UserName=? and u.Password=?";
        statement=connection.prepareStatement(query);
        statement.setString(1, UserName);
        statement.setString(2, Password);
        ResultSet rs=statement.executeQuery();
        User user= new User();
        while (rs.next()) {
            user.setId(rs.getInt("Id"));
            user.setFirstName(rs.getString("FirstName"));
            user.setLastName(rs.getString("LastName"));
            user.setUserName(rs.getString("UserName"));
            user.setPassword(rs.getString("Password"));
            user.setRole(rs.getString("Role"));
            user.setRegisterDate(rs.getDate("RegisterDate"));
            //user.setLastLogin(rs.getDate("LastLogin").toString());
        }    
        statement.close();
        connection.close();
        rs.close();
        return user;       
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public User Register(User user) {
        try{
        CallableStatement statement;
        context=new DbContext();
        connection=context.getConnection();                
        String query="{call AddUser(?,?,?,?,?)}";
        statement=connection.prepareCall(query);
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getUserName());
        statement.setString(4, user.getPassword());
        statement.setString(5, user.getRole());
        int affectedRow=statement.executeUpdate();
        System.out.println("Etkilenen satır sayısı "+affectedRow);
        statement.close();
        connection.close();
        return null;        
        } catch (Exception ex) {
            return null;
        }
        
    }
    
}