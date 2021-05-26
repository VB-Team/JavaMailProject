/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.services.mail;

import com.vbteam.services.mail.DeletedMailService;
import com.vbteam.models.DeletedMail;
import com.vbteam.models.IMail;
import com.vbteam.models.SentMail;
import com.vbteam.utils.DbContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author schea
 */
public class SentMailService {

    DbContext context;
    Connection connection;

    public void addMail(List<IMail> mails) {
        try {

            int affectedRow = 0;
            PreparedStatement statement;
            context = new DbContext();
            connection = context.getConnection();
            int fromId, sendId;
            for (IMail sentMail : mails) {
                fromId = context.getUser(sentMail.getRecipientUser());
                sendId = context.getUser(sentMail.getSenderUser());
                String insertQuery = "Insert into SentMail(RecipientId,SenderId,Subject,Body,Attachment,AttachmentType)values(?,?,?,?,?,?)";
                statement = connection.prepareStatement(insertQuery);
                statement.setInt(1, fromId);
                statement.setInt(2, sendId);
                statement.setString(3, sentMail.getSubject());
                statement.setString(4, sentMail.getBody());
                statement.setBytes(5, sentMail.getAttachment());
                statement.setString(6, sentMail.getAttachmentType());
                affectedRow += statement.executeUpdate();
                statement.close();
            }
            System.out.println("Etkilenen satır sayısı " + affectedRow);
            connection.close();
        } catch (Exception ex) {
            System.out.println("Server Sent Mail Service Exception : " + ex.getLocalizedMessage());
        }
    }

    public List<IMail> getOutgoingMail(int userId) {
        try {
            PreparedStatement statement;
            context = new DbContext();
            connection = context.getConnection();
            String query = "Select sm.Id,u.UserName as SendedUser,sm.RecipientId,sm.Subject,sm.Body,sm.CreateDate,sm.Attachment,sm.AttachmentType From SentMail sm "
                    + "inner join Users u on u.Id=sm.SenderId where sm.SenderId=?";
            statement = connection.prepareStatement(query);
            statement.setString(1, Integer.toString(userId));
            ResultSet rs = statement.executeQuery();
            List<IMail> mails = new ArrayList<IMail>();
            while (rs.next()) {
                SentMail mail = new SentMail();
                int fromId = rs.getInt("RecipientId");
                String query2 = "Select u.UserName from Users u where u.Id=?";
                statement = connection.prepareStatement(query2);
                statement.setString(1, Integer.toString(fromId));
                mail.setId(rs.getInt("Id"));
                mail.setRecipientUser(rs.getString("SendedUser"));
                mail.setSubject(rs.getString("Subject"));
                mail.setBody(rs.getString("Body"));
                //mail.setCreateDate(rs.getTimestamp("SendDate"));//DateTimeFix
                mail.setAttachment(rs.getBytes("Attachment"));
                mail.setAttachmentType(rs.getString("AttachmentType"));
                ResultSet rs2 = statement.executeQuery();

                while (rs2.next()) {
                    mail.setSenderUser(rs2.getString("UserName"));
                }
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

    public List<IMail> getIncomingMail(int userId) {
        try {
            PreparedStatement statement;
            context = new DbContext();
            connection = context.getConnection();
            String query = "Select sm.Id,u.UserName as SendedUser,sm.RecipientId,sm.Subject,sm.Body,sm.CreateDate,sm.Attachment,sm.AttachmentType From SentMail sm "
                    + "inner join Users u on u.Id=sm.SenderId where sm.RecipientId=?";
            statement = connection.prepareStatement(query);
            statement.setString(1, Integer.toString(userId));
            ResultSet rs = statement.executeQuery();
            List<IMail> mails = new ArrayList<IMail>();
            while (rs.next()) {
                SentMail mail = new SentMail();
                int fromId = rs.getInt("RecipientId");
                String query2 = "Select u.UserName from Users u where u.Id=?";
                statement = connection.prepareStatement(query2);
                statement.setString(1, Integer.toString(fromId));
                mail.setId(rs.getInt("Id"));
                mail.setRecipientUser(rs.getString("SendedUser"));
                mail.setSubject(rs.getString("Subject"));
                mail.setBody(rs.getString("Body"));
                //mail.setCreateDate(rs.getTimestamp("CreateDate"));//DateTimeFix
                mail.setAttachmentType(rs.getString("AttachmentType"));
                mail.setAttachment(rs.getBytes("Attachment"));
                ResultSet rs2 = statement.executeQuery();
                while (rs2.next()) {
                    mail.setSenderUser(rs2.getString("UserName"));
                }
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

    public void deletedMail(int mailId) {
        try {
            PreparedStatement statement;
            context = new DbContext();
            connection = context.getConnection();
            String selectQuery = "Select * From SentMail sm where sm.Id=?";
            statement = connection.prepareStatement(selectQuery);
            statement.setString(1, Integer.toString(mailId));
            ResultSet rs = statement.executeQuery();
            SentMail mail = new SentMail();
            while (rs.next()) {
                mail.setId(rs.getInt("Id"));
                mail.setRecipientUser(rs.getString("RecipientId"));
                mail.setSubject(rs.getString("Subject"));
                mail.setBody(rs.getString("Body"));
                //mail.setCreateDate(rs.getTimestamp("CreateDate"));//DateTimeFix
                mail.setSenderUser(rs.getString("SendId"));
                mail.setAttachment(rs.getBytes("Attachment"));
                mail.setAttachmentType(rs.getString("AttachmentType"));

            }
            String deleteQuery = "Delete From SentMail where Id=?";
            statement = connection.prepareStatement(deleteQuery);
            statement.setInt(1, mail.getId());
            statement.executeUpdate();
            DeletedMail deleteMail = new DeletedMail();
            deleteMail.setId(mail.getId());
            deleteMail.setRecipientUser(mail.getRecipientUser());
            deleteMail.setSenderUser(mail.getSenderUser());
            deleteMail.setBody(mail.getBody());
            deleteMail.setDeletedDate(new java.sql.Timestamp(new java.util.Date().getTime()));
            deleteMail.setSubject(mail.getSubject());
            deleteMail.setAttachment(mail.getAttachment());
            DeletedMailService mailService = new DeletedMailService();
            mailService.addDeletedMail(deleteMail);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
