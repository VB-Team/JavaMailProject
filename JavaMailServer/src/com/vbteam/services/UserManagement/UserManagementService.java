/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.services.UserManagement;

import com.vbteam.models.Log;
import com.vbteam.models.User;
import com.vbteam.services.logger.Logger;
import com.vbteam.socket.Server;
import com.vbteam.utils.BCrypt;
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
    private static UserManagementService instance = null;
    private Logger logger;

    @Override
    public User addUser(User user) {
        try {
            CallableStatement statement;
            context = new DbContext();

            //connection = context.getConnection();
            connection = Server.connectionPool.getConnection();
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
            //connection.close();
            if (affectedRow > 0) {
                return user;
            } else {
                return null;
            }
        } catch (Exception ex) {
            logger = Logger.getInstance();
            logger.addLog(new Log(new java.sql.Timestamp(new java.util.Date().getTime()), "Exception", "Server User Management Add User Exception : " + ex.getMessage()));
            System.out.println("User Manager Service Exception : " + ex.getMessage());
            return null;
        } finally {
            try {
                //connection.close();
                Server.connectionPool.releaseConnection(connection);
            } catch (Exception e) {
            }

        }
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public boolean deletedUser(int userId) {
        try {
            CallableStatement statement;
            int affectedRow = 0;
            context = new DbContext();

            //connection = context.getConnection();
            connection = Server.connectionPool.getConnection();
            String deleteMailQuery = "Delete From Headers where RecipientId=? or SenderId=?";
            String deleteUserQuery = "{call DeleteUser(?)}";
            statement = connection.prepareCall(deleteMailQuery);
            statement.setInt(1, userId);
            statement.setInt(2, userId);
            affectedRow += statement.executeUpdate();
            statement = connection.prepareCall(deleteUserQuery);
            statement.setInt(1, userId);
            affectedRow += statement.executeUpdate();
            System.out.println("Etkilenen satır sayısı " + affectedRow);
            statement.close();
            //connection.close();
            if (affectedRow > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            logger = Logger.getInstance();
            logger.addLog(new Log(new java.sql.Timestamp(new java.util.Date().getTime()), "Exception", "Server User Management Delete User Exception : " + ex.getMessage()));
            System.out.println("User Manager Service Exception : " + ex.getMessage());
            return false;
        } finally {
            try {
                //connection.close();
                Server.connectionPool.releaseConnection(connection);
            } catch (Exception e) {
            }

        }
    }

    @Override
    public User updateUser(User user) {
        try {
            CallableStatement statement;
            context = new DbContext();

            //connection = context.getConnection();
            connection = Server.connectionPool.getConnection();
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
            //connection.close();
            if (affectedRow > 0) {
                return user;
            } else {
                return null;
            }
        } catch (Exception ex) {
            logger = Logger.getInstance();
            logger.addLog(new Log(new java.sql.Timestamp(new java.util.Date().getTime()), "Exception", "Server User Management Update User Exception : " + ex.getMessage()));
            System.err.println("User Manager Service Exception : " + ex.getMessage());
            return null;
        } finally {
            try {
                //connection.close();
                Server.connectionPool.releaseConnection(connection);
            } catch (Exception e) {
            }

        }
    }

    @Override
    public List<User> listUser() {
        try {
            PreparedStatement statement;
            context = new DbContext();

            //connection = context.getConnection();
            connection = Server.connectionPool.getConnection();
            String query = "Select u.Id,u.UserName,u.Password,ud.FirstName,ud.LastName,ur.Role,u.RegisterDate\n"
                    + "From Users u join UserDetails ud on ud.UserId=u.Id\n"
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
            //connection.close();
            rs.close();
            return users;
        } catch (Exception ex) {
            logger = Logger.getInstance();
            logger.addLog(new Log(new java.sql.Timestamp(new java.util.Date().getTime()), "Exception", "Server User Management List User Exception : " + ex.getMessage()));
            System.err.println("User management Exception : " + ex.getMessage());
            return null;
        } finally {
            try {
                //connection.close();
                Server.connectionPool.releaseConnection(connection);
            } catch (Exception e) {
            }

        }
    }

    @Override
    public int IncomingMailCount(int userId) {
        try {
            PreparedStatement statement;
            context = new DbContext();

            //connection = context.getConnection();
            connection = Server.connectionPool.getConnection();
            String headerQuery = "Select h.MailId from Headers h ,Users u where h.RecipientId=? and u.Id=h.RecipientId and h.State=1";
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
            //connection.close();
            headerResultSet.close();
            return mailCount;
        } catch (Exception ex) {
            logger = Logger.getInstance();
            logger.addLog(new Log(new java.sql.Timestamp(new java.util.Date().getTime()), "Exception", "Server User Management Incoming Mail Exception : " + ex.getMessage()));
            System.err.println("User management Exception : " + ex.getMessage());
            return 0;
        } finally {
            try {
                //connection.close();
                Server.connectionPool.releaseConnection(connection);
            } catch (Exception e) {
            }

        }
    }

    @Override
    public int OutgoingMailCount(int userId) {
        try {
            PreparedStatement statement;
            context = new DbContext();

            //connection = context.getConnection();
            connection = Server.connectionPool.getConnection();
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
            //connection.close();
            headerResultSet.close();
            return mailCount;
        } catch (Exception ex) {
            logger = Logger.getInstance();
            logger.addLog(new Log(new java.sql.Timestamp(new java.util.Date().getTime()), "Exception", "Server User Management Outgoing Mail Exception : " + ex.getMessage()));
            System.err.println("User management Exception : " + ex.getMessage());
            return 0;
        } finally {
            try {
                //connection.close();
                Server.connectionPool.releaseConnection(connection);
            } catch (Exception e) {
            }

        }
    }

}
