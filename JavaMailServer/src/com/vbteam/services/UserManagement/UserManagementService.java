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
    public User addUser(User user) {
        try {
            CallableStatement statement;
            context = new DbContext();
            connection = context.getConnection();
            String query = "{call AddUser(?,?,?,?,?,?)}";
            statement = connection.prepareCall(query);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getUserName());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole());
            statement.setTimestamp(6, user.getLastLogin());
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
    public boolean deletedUser(int userId) {
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
    public List<User> listUser() {
        try {
            PreparedStatement statement;
            context = new DbContext();
            connection = context.getConnection();
            String query = "Select u.Id,u.UserName,u.Password,ud.FirstName,ud.LastName,ur.Role\n"
                    + "From Users u join UserDetail ud on ud.UserId=u.Id\n"
                    + "join UserRoles ur on u.RoleId=ur.Id ";
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            List<User> users = new ArrayList<User>();
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
    public int IncomingMailCount(int userId) {
        try {
            PreparedStatement statement;
            context = new DbContext();
            connection = context.getConnection();
            String headerQuery = "Select h.MailId from Headers h ,Users u where h.SenderId=? and u.Id=h.RecipientId and h.State=1";
            String mailSelectQuery = "Select * From Mails m where m.Id=?";
            statement = connection.prepareStatement(headerQuery);
            statement.setInt(1, userId);
            ResultSet headerResultSet = statement.executeQuery();
            int mailId = 0;
            int mailCount = 0;
            while (headerResultSet.next()) {
                if (mailId != headerResultSet.getInt("MailId")) {
                    mailId = headerResultSet.getInt("MailId");
                    statement = connection.prepareStatement(mailSelectQuery);
                    statement.setInt(1, mailId);
                    ResultSet mailResultSet = statement.executeQuery();
                    while (mailResultSet.next()) {
                    mailCount++;
                    }
                }
            }
            statement.close();
            connection.close();
            headerResultSet.close();
            return mailCount;
        } catch (Exception ex) {
            System.err.println("AuthService Exception : " + ex.getMessage());
            return 0;
        }
    }

    @Override
    public int OutgoingMailCount(int userId) {
        try {
            PreparedStatement statement;
            context = new DbContext();
            connection = context.getConnection();
            String query = "Select Count(*) as TotalMail From SentMail sm where sm.SendId=?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            int mailCount = 0;
            while (rs.next()) {
                mailCount = rs.getInt("TotalMail");
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
