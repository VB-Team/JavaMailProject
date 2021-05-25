/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.services.UserManagement;

import com.vbteam.models.User;
import com.vbteam.utils.DbContext;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author schea
 */
public class UserManagementService implements IUserManagementService {

    DbContext context;
    Connection connection;

    @Override
    public User AddUser(User user) {
        try {
            CallableStatement statement;
            context = new DbContext();
            connection = context.getConnection();
            String query = "{call AddUser(?,?,?,?,?)}";
            statement = connection.prepareCall(query);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getUserName());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole());
            statement.setObject(6, new java.util.Date());
            user.setLastLogin(new java.sql.Timestamp(new java.util.Date().getTime()));
            user.setRegisterDate(new java.sql.Date(new java.util.Date().getTime()));
            System.out.println(user.getLastLogin());

            int affectedRow = statement.executeUpdate();
            System.out.println("Etkilenen satır sayısı " + affectedRow);
            statement.close();
            connection.close();
            if (affectedRow > 0) {
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("User Manager Service Exception : " + e.getMessage());
            return null;
        }

    }

    @Override
    public boolean DeletedUser(int userId) {
        try {
            CallableStatement statement;
            context = new DbContext();
            connection = context.getConnection();
            String query = "{call DeleteUser(?)}";
            statement = connection.prepareCall(query);
            statement.setInt(1, userId);
            int affectedRow = statement.executeUpdate();
            System.out.println("Etkilenen satır sayısı " + affectedRow);
            statement.close();
            connection.close();
            if (affectedRow > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("User Manager Service Exception : " + e.getMessage());
            return false;
        }
    }

    @Override
    public User UpdateUser(User user) {
        try {
            CallableStatement statement;
            context = new DbContext();
            connection = context.getConnection();
            String query = "{call UpdateUser(?,?,?,?,?,?)}";
            statement = connection.prepareCall(query);
            statement.setInt(1, user.getId());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getUserName());
            statement.setString(6, user.getRole());
            int affectedRow = statement.executeUpdate();
            System.out.println("Etkilenen satır sayısı " + affectedRow);
            statement.close();
            connection.close();
            if (affectedRow > 0) {
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("User Manager Service Exception : " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<User> ListUser() {
        try{
        PreparedStatement statement;
            context = new DbContext();
            connection = context.getConnection();
        String query="Select u.Id,u.UserName,u.Password,ud.FirstName,ud.LastName,ur.Role\n" +
                "From Users u join UserDetail ud on ud.UserId=u.Id\n" +
                "join UserRoles ur on u.RoleId=ur.Id ";
        statement = connection.prepareStatement(query);        
            ResultSet rs = statement.executeQuery();    
             List<User> users=new ArrayList<User>();
            while (rs.next()) {
                User user = new User(); 
                user.setId(rs.getInt("Id"));
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setUserName(rs.getString("UserName"));
                user.setPassword(rs.getString("Password"));
                user.setRole(rs.getString("Role"));
                user.setRegisterDate(rs.getDate("RegisterDate"));
                users.add(user);

            }
            statement.close();
            connection.close();
            rs.close();
            return users;
        } catch (Exception ex) {
            System.err.println("AuthService Exception : " + ex.getMessage());
            return null;
        }
    }

    @Override
    public int FromUserMailCount(int userId) {
        try{
        PreparedStatement statement;
            context = new DbContext();
            connection = context.getConnection();
        String query="Select Count(*) as TotalMail From SentMail sm where sm.FromId=?";        
        statement = connection.prepareStatement(query);     
        statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            int mailCount=0;
            while (rs.next()) {
                mailCount=rs.getInt("TotalMail");
            }
            statement.close();
            connection.close();
            rs.close();
            return mailCount;
        } catch (Exception ex) {
            System.err.println("AuthService Exception : " + ex.getMessage());
            return 0;
        }
    }

    @Override
    public int SendUserMailCount(int userId) {
       try{
        PreparedStatement statement;
            context = new DbContext();
            connection = context.getConnection();
        String query="Select Count(*) as TotalMail From SentMail sm where sm.SendId=?";        
        statement = connection.prepareStatement(query);     
        statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            int mailCount=0;
            while (rs.next()) {
                mailCount=rs.getInt("TotalMail");
            }
            statement.close();
            connection.close();
            rs.close();
            return mailCount;
        } catch (Exception ex) {
            System.err.println("AuthService Exception : " + ex.getMessage());
            return 0;
        }
    }

}
