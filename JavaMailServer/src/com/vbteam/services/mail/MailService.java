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
import com.vbteam.models.Mail;
import java.util.Random;

/**
 *
 * @author schea
 */
public class MailService {

    DbContext context;
    Connection connection;

    public void addMails(List<Mail> mails) {
        try {

            int affectedRow = 0;
            PreparedStatement statement;
            context = new DbContext();
            connection = context.getConnection();
            int fromId, sendId;
            for (Mail mail : mails) {
                fromId = context.getUser(mail.getRecipientUser());
                sendId = context.getUser(mail.getSenderUser());

                String insertQuery = "Insert into Mails"
                        + "(RecipientId,SenderId,Subject,Body,Attachment,AttachmentDetail,CreateDate,Type,State"
                        + ")values(?,?,?,?,?,?,?,?,?)";
                statement = connection.prepareStatement(insertQuery);
                statement.setInt(1, fromId);
                statement.setInt(2, sendId);
                statement.setString(3, mail.getSubject());
                statement.setString(4, mail.getBody());
                statement.setBytes(5, mail.getAttachment());
                statement.setString(6, mail.getAttachmentDetail());
                statement.setTimestamp(7, mail.getCreateDate());
                statement.setString(8, mail.getType());
                statement.setBoolean(9, mail.isState());
                affectedRow += statement.executeUpdate();
                statement.close();
            }
            System.out.println("Etkilenen satır sayısı " + affectedRow);
            connection.close();
        } catch (Exception ex) {
            System.out.println("Server Sent Mail Service Exception : " + ex.getLocalizedMessage());
        }
    }

    public void addRandomMails(int mailCount) {
        try {

            int affectedRow = 0;
            PreparedStatement statement;
            context = new DbContext();
            connection = context.getConnection();
            int fromId, sendId;
            for (int i = 0; i < mailCount; i++) {
                RandomBytes();

                String insertQuery = "Insert into Mails"
                        + "(RecipientId,SenderId,Subject,Body,Attachment,AttachmentDetail,CreateDate,Type,State"
                        + ")values(?,?,?,?,?,?,?,?,?)";
                statement = connection.prepareStatement(insertQuery);
                if (i % 3 == 0) {
                    statement.setInt(1, 1);
                    statement.setInt(2, 2);
                } else {
                    statement.setInt(1, 2);
                    statement.setInt(2, 1);
                }
                String[] mailType=new String[3];
                mailType[0]="Normal";
                mailType[1]="Deleted";
                mailType[2]="Draft";
                Random rnd=new Random();
                statement.setString(3, RandomString(108));
                statement.setString(4, RandomString(200));
                statement.setBytes(5, RandomBytes());
                statement.setString(6, RandomString(100));
                statement.setTimestamp(7, new java.sql.Timestamp(new java.util.Date().getTime()));
                statement.setString(8, mailType[rnd.nextInt(3)]);
                if (i % 2 == 0) {
                    statement.setBoolean(9, true);
                } else {
                    statement.setBoolean(9, false);
                }

                affectedRow += statement.executeUpdate();
                statement.close();
            }
            System.out.println("Etkilenen satır sayısı " + affectedRow);
            connection.close();
        } catch (Exception ex) {
            System.err.println("Server Mail Service Exception : " + ex.getLocalizedMessage());
        }
    }

    private byte[] RandomBytes() {
        byte[] array = new byte[7]; // length is bounded by 7
        return array;
    }

    private String RandomString(int length) {
        //fromId = context.getUser(mail.getRecipientUser());
        //sendId = context.getUser(mail.getSenderUser());
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = length;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int j = 0; j < targetStringLength; j++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        return generatedString;
    }

    public List<Mail> getOutgoingMails(int userId) {
        try {
            PreparedStatement statement;
            context = new DbContext();
            connection = context.getConnection();
            String query = "Select u.UserName,m.* From Mails m inner join Users u on u.Id=RecipientId where m.SenderId=? AND m.State=1 AND m.Type='Normal' ";
            statement = connection.prepareStatement(query);
            statement.setString(1, Integer.toString(userId));
            ResultSet rs = statement.executeQuery();
            List<Mail> mails = new ArrayList<Mail>();
            while (rs.next()) {
                Mail mail = new Mail();
                mail.setId(rs.getInt("Id"));
                mail.setRecipientUser(rs.getString("UserName"));
                mail.setSubject(rs.getString("Subject"));
                mail.setBody(rs.getString("Body"));
                mail.setCreateDate(rs.getTimestamp("CreateDate"));//DateTimeFix
                mail.setAttachment(rs.getBytes("Attachment"));
                mail.setAttachmentDetail(rs.getString("AttachmentDetail"));
                mails.add(mail);
            }
            statement.close();
            connection.close();
            rs.close();
            return mails;
        } catch (Exception ex) {
            System.out.println("Get Sent Mail Exception : " + ex.getMessage());
            return null;
        }
    }

    public List<Mail> getIncomingMails(int userId) {
        try {
            PreparedStatement statement;
            context = new DbContext();
            connection = context.getConnection();
            String query = "Select u.UserName,m.* From Mails m inner join Users u on u.Id=SenderId where m.RecipientId=? AND m.State=1 AND m.Type='Normal'";
            statement = connection.prepareStatement(query);
            statement.setString(1, Integer.toString(userId));
            ResultSet rs = statement.executeQuery();
            List<Mail> mails = new ArrayList<Mail>();
            while (rs.next()) {
                Mail mail = new Mail();
                mail.setId(rs.getInt("Id"));
                mail.setRecipientUser(rs.getString("UserName"));
                mail.setSubject(rs.getString("Subject"));
                mail.setBody(rs.getString("Body"));
                mail.setCreateDate(rs.getTimestamp("CreateDate"));//DateTimeFix
                mail.setAttachmentDetail(rs.getString("AttachmentDetail"));
                mail.setAttachment(rs.getBytes("Attachment"));
                mail.setType(rs.getString("Type"));
                mail.setState(rs.getBoolean("State"));
                mails.add(mail);
            }
            statement.close();
            connection.close();
            rs.close();
            return mails;
        } catch (Exception ex) {
            System.out.println("Get From Mail Exception : " + ex.getMessage());
            return null;
        }
    }

    public List<Mail> getAnyMails(int userId, String mailType) {
        try {
            PreparedStatement statement;
            context = new DbContext();
            connection = context.getConnection();
            String selectQuery = "Select u.UserName,m.* From Mails m inner join Users u on u.Id=RecipientId where m.SenderId=? AND m.State=1 AND m.Type=?";
            statement = connection.prepareStatement(selectQuery);
            statement.setInt(1, userId);
            statement.setString(2, mailType);
            List<Mail> mails = new ArrayList<Mail>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Mail mail = new Mail();
                mail.setId(resultSet.getInt("Id"));
                mail.setRecipientUser(resultSet.getString("UserName"));
                mail.setSubject(resultSet.getString("Subject"));
                mail.setBody(resultSet.getString("Body"));
                mail.setCreateDate(resultSet.getTimestamp("CreateDate"));//DateTimeFix
                mail.setAttachmentDetail(resultSet.getString("AttachmentDetail"));
                mail.setAttachment(resultSet.getBytes("Attachment"));
                mail.setType(resultSet.getString("Type"));
                mail.setState(resultSet.getBoolean("State"));
                mails.add(mail);
            }
            connection.close();
            statement.close();
            return mails;
        } catch (Exception e) {
            System.out.println("Mail Service Exception : " + e.getLocalizedMessage());
            return null;
        }

    }

    public void deleteMail(int mailId) {
        try {
            PreparedStatement statement;
            context = new DbContext();
            connection = context.getConnection();
            String selectQuery = "Select * From Mails m where m.Id=?";
            String deleteQuery = "Delete From Mails where Id=?";
            String updateQuery = "Update Mails set Mails.Type='Deleted' where Mails.Id=?";
            statement = connection.prepareStatement(selectQuery);
            statement.setString(1, Integer.toString(mailId));
            ResultSet rs = statement.executeQuery();
            Mail mail = new Mail();
            while (rs.next()) {
                /* mail.setId(rs.getInt("Id"));
                mail.setRecipientUser(rs.getString("RecipientId"));
                mail.setSubject(rs.getString("Subject"));
                mail.setBody(rs.getString("Body"));
                mail.setCreateDate(rs.getTimestamp("CreateDate"));
                mail.setSenderUser(rs.getString("SendId"));
                mail.setAttachment(rs.getBytes("Attachment"));
                mail.setAttachmentDetail(rs.getString("AttachmentDetail"));*/
                mail.setType(rs.getString("Type"));

            }
            if (mail.getType().equals("Deleted")) {
                statement = connection.prepareStatement(deleteQuery);
                statement.setInt(1, mailId);
                int affectedRow = statement.executeUpdate();
                System.out.println(affectedRow);
            } else {                
                statement = connection.prepareStatement(updateQuery);     
                statement.setInt(1, mailId);                
                int affectedRow = statement.executeUpdate();
            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
