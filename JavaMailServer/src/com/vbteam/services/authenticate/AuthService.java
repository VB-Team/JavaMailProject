/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.services.authenticate;

import com.vbteam.models.Log;
import com.vbteam.models.User;
import com.vbteam.services.logger.Logger;
import com.vbteam.utils.BCrypt;
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
    private static AuthService instance = null;
    private Logger logger;
    private AuthService() {
    }
    public static AuthService getInstance(){
        if(instance==null)
            instance=new AuthService();
        
        return instance;
    }

    @Override
    public User login(String UserName, String Password) {
        try {
            PreparedStatement statement;

            context = new DbContext();
            connection = context.getConnection();

            String query = "Select u.Id,u.LastLoginDate,u.UserName,u.Password,ud.FirstName,ud.LastName,ur.Role,u.RegisterDate\n"
                    + "From Users u join UserDetails ud on ud.UserId=u.Id\n"
                    + "join UserRoles ur on u.RoleId=ur.Id \n"
                    + "where u.UserName=?";
            statement = connection.prepareStatement(query);
            statement.setString(1, UserName);
            ResultSet rs = statement.executeQuery();
            User user = new User();
            while (rs.next()) {
                user.setId(rs.getInt("Id"));
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setUserName(rs.getString("UserName"));
                user.setPassword(rs.getString("Password"));
                user.setRole(rs.getString("Role"));
                user.setRegisterDate(rs.getDate("RegisterDate"));
                user.setLastLogin(rs.getTimestamp("LastLoginDate"));
            }
            if (BCrypt.checkpw(Password, user.getPassword())) {
                if (UpdateLastLoginDate(UserName)) {
                    return user;
                } else {
                    return null;
                }
            } else {
                statement.close();
                connection.close();
                rs.close();
                return null;
            }
        } catch (Exception ex) {
            logger=Logger.getInstance();
            logger.addLog(new Log(new java.sql.Timestamp(new java.util.Date().getTime()), "Exception", "Server Login Exception : "+ex.getMessage()));
            System.err.println("AuthService Exception : " + ex.getMessage());
            return null;
        }
    }

    private boolean UpdateLastLoginDate(String userName) {
        try {
            CallableStatement statement;
            context = new DbContext();
            connection = context.getConnection();
            String updateQuery = "Update Users set LastLoginDate=? where Users.UserName=?";
            statement = connection.prepareCall(updateQuery);
            statement.setTimestamp(1, new java.sql.Timestamp(new java.util.Date().getTime()));
            statement.setString(2, userName);
            int affectedRow = statement.executeUpdate();
            statement.close();
            connection.close();
            if (affectedRow > 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {
            logger=Logger.getInstance();
            logger.addLog(new Log(new java.sql.Timestamp(new java.util.Date().getTime()), "Exception", "Server Update Last Login Date Exception : "+ex.getMessage()));
            System.err.println("AuthService Exception : " + ex.toString());
            return false;
        }

    }

    @Override
    public User register(User user) {
        try {
            CallableStatement statement;
            context = new DbContext();
            connection = context.getConnection();

            String query = "{call AddUser(?,?,?,?,?,?)}";
            statement = connection.prepareCall(query);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getUserName());
            statement.setString(4, hashPassword(user.getPassword()));
            statement.setString(5, user.getRole());
            statement.setTimestamp(6, user.getLastLogin());

            int affectedRow = statement.executeUpdate();
            System.out.println("Etkilenen satır sayısı " + affectedRow);
            statement.close();
            connection.close();
            
            user.setId(context.getUserId(user.getUserName()));
            
            if (affectedRow > 0) {
                return user;
            } else {
                return null;
            }
        } catch (Exception ex) {
            logger=Logger.getInstance();
            logger.addLog(new Log(new java.sql.Timestamp(new java.util.Date().getTime()), "Exception", "Server Register Exception : "+ex.getMessage()));
            System.err.println("AuthService Exception : " + ex.toString());
            return null;
        }

    }

    @Override
    public boolean userExist(String UserName) {
        try {
            PreparedStatement statement;
            context = new DbContext();
            connection = context.getConnection();
            String selectQuery = "Select TOP 1 u.Id from Users u where u.UserName=?";
            statement = connection.prepareStatement(selectQuery);
            statement.setString(1, UserName);
            ResultSet rs = statement.executeQuery();
            int UserId = 0;
            while (rs.next()) {
                UserId = rs.getInt(1);
            }
            statement.close();
            connection.close();
            if (UserId > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.err.println("AuthService Exception" + e.getMessage());
            return false;
        }
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


    public User updateUser(User user) {
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
            statement.setString(5, user.getPassword());
            statement.setString(6, "User");
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
}
