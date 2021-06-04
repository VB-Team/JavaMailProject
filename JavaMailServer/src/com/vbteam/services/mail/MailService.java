/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.services.mail;


import com.vbteam.utils.DbContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.vbteam.models.*;
import com.vbteam.services.logger.Logger;
import com.vbteam.socket.Server;

/**
 *
 * @author schea
 */
public class MailService {

    private DbContext context;
    private Connection connection;
    private static MailService instance = null;
    private Logger logger;

    private MailService() {
    }

    public static MailService getInstance() {
        if (instance == null) {
            instance = new MailService();
        }

        return instance;
    }

    public boolean addMail(Mail mail) {

        context = new DbContext();
        connection = Server.connectionPool.getConnection();
        try {
            int affectedRow = 0;
            PreparedStatement statement;
            int recipientId, senderId;
            String mailInsertQuery = "Insert into Mails (Subject,Body,AttachmentState,CreateDate) values(?,?,?,?);";
            System.out.println("mail Body " + mail.getBody());
            statement = connection.prepareStatement(mailInsertQuery);
            statement.setString(1, mail.getSubject());
            statement.setString(2, mail.getBody());
            statement.setBoolean(3, mail.isAttachmentState());
            statement.setTimestamp(4, mail.getCreateDate());
            affectedRow += statement.executeUpdate();
            for (Header header : mail.getHeaders()) {

                recipientId = context.getUserId(header.getRecipientUser());
                senderId = context.getUserId(header.getSenderUser());
                if (recipientId != -1) {
                    String headerInsertQuery = "Insert INTO Headers(MailId,RecipientId,SenderId,Type,State) values ((Select IDENT_CURRENT('Mails')),?,?,?,?)";
                    statement = connection.prepareStatement(headerInsertQuery);
                    recipientId = context.getUserId(header.getRecipientUser());
                    senderId = context.getUserId(header.getSenderUser());
                    statement.setInt(1, recipientId);
                    statement.setInt(2, senderId);
                    statement.setString(3, header.getType());
                    statement.setBoolean(4, header.isState());
                    affectedRow += statement.executeUpdate();
                }
            }
            if (affectedRow > 1) {
                for (Attachment attachment : mail.getAttachments()) {  
                    String attachmentInsertQuery = "Insert INTO Attachments(MailId,AttachmentName,AttachmentType,AttachmentSize,AttachmentContent) values ((Select IDENT_CURRENT('Mails')),?,?,?,?)";
                    statement = connection.prepareStatement(attachmentInsertQuery);
                    statement.setString(1, attachment.getAttachmentName());
                    statement.setString(2, attachment.getAttachmentType());
                    statement.setInt(3, attachment.getAttachmentSize());
                    statement.setBytes(4, attachment.getAttachmentContent());
                    
                    affectedRow += statement.executeUpdate();
                }
            }
            statement.close();
            if (affectedRow > 2) {
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {
            logger = Logger.getInstance();
            logger.addLog(new Log(new java.sql.Timestamp(new java.util.Date().getTime()), "Exception", "Server Sended Mail exception : " + ex.getMessage()));
            System.err.println("Server Sent Mail Service Exception : " + ex.toString());
            return false;
        } finally {
            try {
                Server.connectionPool.releaseConnection(connection);
            } catch (Exception e) {
            }

        }
    }

    public void addDraftMail(Mail mail) {
        context = new DbContext();
        connection = Server.connectionPool.getConnection();
        try {
            int affectedRow = 0;
            PreparedStatement statement;
            int recipientId, senderId;
            String mailInsertQuery = "Insert into Mails (Subject,Body,AttachmentState,CreateDate) values(?,?,?,?);";
            statement = connection.prepareStatement(mailInsertQuery);
            statement.setString(1, mail.getSubject());
            statement.setString(2, mail.getBody());
            statement.setBoolean(3, mail.isAttachmentState());
            statement.setTimestamp(4, mail.getCreateDate());
            affectedRow += statement.executeUpdate();
            for (Header header : mail.getHeaders()) {
                
                recipientId = context.getUserId(header.getRecipientUser());
                senderId = context.getUserId(header.getSenderUser());
                if (recipientId != -1) {
                    String headerInsertQuery = "Insert INTO Headers(MailId,RecipientId,SenderId,Type,State) values ((Select IDENT_CURRENT('Mails')),?,?,?,?)";
                    statement = connection.prepareStatement(headerInsertQuery);
                    recipientId = context.getUserId(header.getRecipientUser());
                    senderId = context.getUserId(header.getSenderUser());
                    statement.setInt(1, recipientId);
                    statement.setInt(2, senderId);
                    statement.setString(3, header.getType());
                    statement.setBoolean(4, header.isState());
                    affectedRow += statement.executeUpdate();
                }
            }
            for (Attachment attachment : mail.getAttachments()) {
                
                String attachmentInsertQuery = "Insert INTO Attachments(MailId,AttachmentName,AttachmentType,AttachmentSize,AttachmentContent) values ((Select IDENT_CURRENT('Mails')),?,?,?,?)";
                statement = connection.prepareStatement(attachmentInsertQuery);
                statement.setString(1, attachment.getAttachmentName());
                statement.setString(2, attachment.getAttachmentType());
                statement.setInt(3, attachment.getAttachmentSize());
                statement.setBytes(4, attachment.getAttachmentContent());
                affectedRow += statement.executeUpdate();
            }
            System.out.println("affacted row : " + affectedRow);
            statement.close();
        } catch (Exception ex) {
            logger = Logger.getInstance();
            logger.addLog(new Log(new java.sql.Timestamp(new java.util.Date().getTime()), "Exception", "Server Sended Mail exception : " + ex.getMessage()+"User Id : "+mail.getHeaders().get(0).getSenderUser()));
            ex.printStackTrace();
        } finally {
            try {
                Server.connectionPool.releaseConnection(connection);
            } catch (Exception e) {
            }
        }
    }

    public List<Mail> getOutgoingMails(int userId) {
        try {
            PreparedStatement statement;
            context = new DbContext();

            //connection = context.getConnection();
            connection = Server.connectionPool.getConnection();

            String headerMailIdQuery = " Select h.MailId from Headers h ,Users u where h.SenderId=? and u.Id=h.RecipientId and h.Type='Normal' and h.State=1";
            String mailSelectQuery = "Select * From Mails m where m.Id=?";

            statement = connection.prepareStatement(headerMailIdQuery);
            statement.setString(1, Integer.toString(userId));
            ResultSet headerMailIdResultSet = statement.executeQuery();
            List<Mail> mails = new ArrayList<Mail>();
            int mailId = 0;
            while (headerMailIdResultSet.next()) {
                if (mailId != headerMailIdResultSet.getInt("MailId")) {
                    mailId = headerMailIdResultSet.getInt("MailId");

                    statement = connection.prepareStatement(mailSelectQuery);
                    statement.setInt(1, mailId);
                    ResultSet mailResultSet = statement.executeQuery();
                    while (mailResultSet.next()) {
                        Mail mail = new Mail();
                        mail.setId(mailResultSet.getInt(1));
                        mail.setSubject(mailResultSet.getString(2));
                        mail.setBody(mailResultSet.getString(3));
                        mail.setCreateDate(mailResultSet.getTimestamp("CreateDate"));
                        mail.setAttachmentState(mailResultSet.getBoolean(4));
                        mails.add(mail);
                    }
                    mailResultSet.close();
                }
            }
            statement.close();
            //connection.close();
            headerMailIdResultSet.close();
            return mails;
        } catch (Exception ex) {
            logger = Logger.getInstance();
            logger.addLog(new Log(new java.sql.Timestamp(new java.util.Date().getTime()), "Exception", "Server Outgoing Mails exception : " + ex.getMessage()+"User Id : "+userId));
            System.err.println("Get From Mail Exception : " + ex.getMessage());
            return null;
        } finally {
            try {
                //connection.close();
                Server.connectionPool.releaseConnection(connection);
            } catch (Exception e) {
            }

        }
    }

    public List<Mail> getIncomingMails(int userId) {
        try {
            PreparedStatement statement;
            context = new DbContext();

            //connection = context.getConnection();
            connection = Server.connectionPool.getConnection();

            String headerMailIdQuery = " Select h.MailId from Headers h ,Users u where h.RecipientId=? and u.Id=h.SenderId and h.Type='Normal' and h.State=1";
            String mailSelectQuery = "Select * From Mails m where m.Id=?";

            statement = connection.prepareStatement(headerMailIdQuery);
            statement.setString(1, Integer.toString(userId));
            ResultSet headerMailIdResultSet = statement.executeQuery();
            List<Mail> mails = new ArrayList<Mail>();
            int mailId = 0;
            while (headerMailIdResultSet.next()) {
                if (mailId != headerMailIdResultSet.getInt("MailId")) {
                    mailId = headerMailIdResultSet.getInt("MailId");

                    statement = connection.prepareStatement(mailSelectQuery);
                    statement.setInt(1, mailId);
                    ResultSet mailResultSet = statement.executeQuery();
                    while (mailResultSet.next()) {
                        Mail mail = new Mail();
                        mail.setId(mailResultSet.getInt(1));
                        mail.setSubject(mailResultSet.getString(2));
                        mail.setBody(mailResultSet.getString(3));
                        mail.setCreateDate(mailResultSet.getTimestamp("CreateDate"));
                        mail.setAttachmentState(mailResultSet.getBoolean(4));
                        mails.add(mail);
                    }
                    mailResultSet.close();
                }
            }
            statement.close();
            //connection.close();
            headerMailIdResultSet.close();
            return mails;
        } catch (Exception ex) {
            logger = Logger.getInstance();
            logger.addLog(new Log(new java.sql.Timestamp(new java.util.Date().getTime()), "Exception", "Server Incoming Mails exception : " + ex.getMessage()+"User Id : "+userId));
            System.err.println("Get From Mail Exception : " + ex.getMessage());
            return null;
        } finally {
            try {
                //connection.close();
                Server.connectionPool.releaseConnection(connection);
            } catch (Exception e) {
            }

        }
    }

    public List<Header> getMailHeaders(int mailId) {
        try {
            String headerSelectQuery = " Select u.UserName,h.*  from Headers h ,Users u where u.Id=RecipientId and h.MailId=? and h.State=1";
            PreparedStatement statement;
            context = new DbContext();

            //connection = context.getConnection();
            connection = Server.connectionPool.getConnection();
            List<Header> headers = new ArrayList<Header>();
            statement = connection.prepareStatement(headerSelectQuery);
            statement.setInt(1, mailId);
            ResultSet headerResultSet = statement.executeQuery();
            while (headerResultSet.next()) {
                Header header = new Header();
                header.setSenderUser(context.getUserName(headerResultSet.getInt("SenderId")));
                header.setId(headerResultSet.getInt("Id"));
                header.setMailId(headerResultSet.getInt("MailId"));
                header.setRecipientUser(headerResultSet.getString("UserName"));
                header.setType(headerResultSet.getString("Type"));
                header.setState(headerResultSet.getBoolean("State"));
                headers.add(header);
            }
            statement.close();
            //connection.close();
            headerResultSet.close();
            return headers;
        } catch (Exception ex) {
            logger = Logger.getInstance();
            logger.addLog(new Log(new java.sql.Timestamp(new java.util.Date().getTime()), "Exception", "Server Mail Headers exception : " + ex.getMessage()));
            System.err.println("Get From Mail Exception : " + ex.getMessage());
            return null;
        } finally {
            try {
                //connection.close();
                Server.connectionPool.releaseConnection(connection);
            } catch (Exception e) {
            }

        }
    }

    public List<Attachment> getMailAttachments(int mailId) {
        try {
            PreparedStatement statement;
            context = new DbContext();

            //connection = context.getConnection();
            connection = Server.connectionPool.getConnection();
            String query = "Select * From Attachments a where a.MailId=?";
            statement = connection.prepareStatement(query);
            statement.setString(1, Integer.toString(mailId));
            ResultSet rs = statement.executeQuery();
            List<Attachment> attachments = new ArrayList<Attachment>();
            while (rs.next()) {
                Attachment attachment = new Attachment();
                attachment.setId(rs.getInt("Id"));
                attachment.setAttachmentContent(rs.getBytes("AttachmentContent"));
                attachment.setAttachmentType(rs.getString("AttachmentType"));
                attachment.setAttachmentSize(rs.getInt("AttachmentSize"));
                attachment.setAttachmentName(rs.getString("AttachmentName"));
                attachments.add(attachment);
            }
            statement.close();
            //connection.close();
            rs.close();
            return attachments;
        } catch (Exception ex) {
            logger = Logger.getInstance();
            logger.addLog(new Log(new java.sql.Timestamp(new java.util.Date().getTime()), "Exception", "Server Mail Attachments exception : " + ex.getMessage()));
            System.err.println("Get From Mail Exception : " + ex.getMessage());
            return null;
        } finally {
            try {
                //connection.close();
                Server.connectionPool.releaseConnection(connection);
            } catch (Exception e) {
            }

        }
    }

    public List<Mail> getAnyMails(int userId, String mailType) {
        try {
            PreparedStatement statement;
            context = new DbContext();

            //connection = context.getConnection();
            connection = Server.connectionPool.getConnection();

            String headerMailIdQuery = " Select h.MailId from Headers h  where (h.RecipientId=? or h.SenderId=?) and h.Type=?  and h.State=1";
            String mailSelectQuery = "Select * From Mails m where m.Id=?";

            statement = connection.prepareStatement(headerMailIdQuery);
            statement.setString(1, Integer.toString(userId));
            statement.setString(2, Integer.toString(userId));
            statement.setString(3, mailType);
            ResultSet headerMailIdResultSet = statement.executeQuery();
            List<Mail> mails = new ArrayList<Mail>();
            int mailId = 0;
            while (headerMailIdResultSet.next()) {
                if (mailId != headerMailIdResultSet.getInt("MailId")) {
                    mailId = headerMailIdResultSet.getInt("MailId");

                    statement = connection.prepareStatement(mailSelectQuery);
                    statement.setInt(1, mailId);
                    ResultSet mailResultSet = statement.executeQuery();
                    while (mailResultSet.next()) {
                        Mail mail = new Mail();
                        mail.setId(mailResultSet.getInt(1));
                        mail.setSubject(mailResultSet.getString(2));
                        mail.setBody(mailResultSet.getString(3));
                        mail.setCreateDate(mailResultSet.getTimestamp("CreateDate"));
                        mail.setAttachmentState(mailResultSet.getBoolean(4));
                        mails.add(mail);
                    }
                    mailResultSet.close();
                }
            }
            statement.close();
            //connection.close();

            headerMailIdResultSet.close();
            return mails;
        } catch (Exception ex) {
            logger = Logger.getInstance();
            logger.addLog(new Log(new java.sql.Timestamp(new java.util.Date().getTime()), "Exception", "Server Any Mails exception : " + ex.getMessage()+"User Id : "+userId));
            System.err.println("Get From Mail Exception : " + ex.getMessage());
            return null;
        } finally {
            try {
                //connection.close();
                Server.connectionPool.releaseConnection(connection);
            } catch (Exception e) {
            }

        }
    }

    public void deleteMail(int mailId) {
        try {
            PreparedStatement statement;
            context = new DbContext();

            //connection = context.getConnection();
            connection = Server.connectionPool.getConnection();
            String selectQuery = "Select * From Headers h where h.MailId=?";
            String deleteQuery = "Delete From Mails where Id=?";
            String updateQuery = "Update Headers set Headers.Type='Deleted' where Headers.MailId=?";
            statement = connection.prepareStatement(selectQuery);
            statement.setString(1, Integer.toString(mailId));
            ResultSet rs = statement.executeQuery();
            Header header = new Header();
            while (rs.next()) {
                header.setMailId(rs.getInt("MailId"));
                header.setType(rs.getString("Type"));
            }
            if (header.getType().equals("Deleted")) {
                statement = connection.prepareStatement(deleteQuery);
                statement.setInt(1, mailId);
                int affectedRow = statement.executeUpdate();
            } else {
                statement = connection.prepareStatement(updateQuery);
                statement.setInt(1, mailId);
                int affectedRow = statement.executeUpdate();
            }
            //connection.close();
            statement.close();
        } catch (Exception ex) {
            logger = Logger.getInstance();
            logger.addLog(new Log(new java.sql.Timestamp(new java.util.Date().getTime()), "Exception", "Server Delete Mail exception : " + ex.getMessage()));
            System.err.println(ex.getMessage());
        } finally {
            try {
                //connection.close();
                Server.connectionPool.releaseConnection(connection);
            } catch (Exception e) {
            }

        }
    }
}
