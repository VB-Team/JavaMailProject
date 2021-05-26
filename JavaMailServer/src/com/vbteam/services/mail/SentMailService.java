/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.services.mail;

import com.vbteam.services.mail.DeletedMailService;
import com.vbteam.models.DeletedMail;
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

    public void AddMail(List<SentMail> mails) {
        try {
            
            int affectedRow=0;
            PreparedStatement statement;
            context = new DbContext();
<<<<<<< Updated upstream
            connection = context.getConnection();    
            int fromId, sendId;
            for (SentMail sentMail : mails) {            
            fromId = context.getUser(sentMail.getFromUser());
            sendId =  context.getUser(sentMail.getSendUser());
            String insertQuery = "Insert into SentMail(FromId,SendId,Subject,Body,Attachment)values(?,?,?,?,?)";
            statement = connection.prepareStatement(insertQuery);            
            statement.setInt(1, fromId);            
            statement.setInt(2, sendId);            
            statement.setString(3, sentMail.getSubject());            
            statement.setString(4, sentMail.getBody());
            statement.setBytes(5, sentMail.getAttachment());
            affectedRow += statement.executeUpdate();
=======
            connection = context.getConnection();
            String selectQuery = "Select u.Id from Users u where u.UserName=? or u.UserName=?";
            statement = connection.prepareStatement(selectQuery);
            statement.setString(1, mail.getFromUser());
            statement.setString(2, mail.getSendUser());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Id.add(rs.getInt("Id"));
            }
            fromId = Id.get(0);
            sendId = Id.get(1);
            String insertQuery = "Insert into SentMail(FromId,SendId,Subject,Body,Attachment)values(?,?,?,?,?)";
            statement = connection.prepareStatement(insertQuery);
            statement.setInt(1, fromId);
            statement.setInt(2, sendId);
            statement.setString(3, mail.getSubject());
            statement.setString(4, mail.getBody());
            statement.setBytes(5, mail.getAttachment());
            //statement.setTimestamp(6, new java.sql.Timestamp(new java.util.Date().getTime()));
            int a = statement.executeUpdate();
            System.out.println("Etkilenen satır sayısı " + a);
>>>>>>> Stashed changes
            statement.close();
            }
            System.out.println("Etkilenen satır sayısı " + affectedRow);            
            connection.close();
        } catch (Exception ex) {
            System.out.println("Sent Mail Service Exception : "+ex.getLocalizedMessage());
        }
    }

    public List<SentMail> GetSentMail(int userId) {
        try {
            PreparedStatement statement;
            context = new DbContext();
            connection = context.getConnection();
            String query = "Select sm.Id,u.UserName as FromUser,sm.FromId,sm.Subject,sm.Body,sm.SendDate,sm.Attachment From SentMail sm "
                    + "inner join Users u on u.Id=sm.SendId where sm.SendId=?";
            statement = connection.prepareStatement(query);
            statement.setString(1, Integer.toString(userId));
            ResultSet rs = statement.executeQuery();
            List<SentMail> mails = new ArrayList<SentMail>();
            while (rs.next()) {
                SentMail mail = new SentMail();
                int fromId = rs.getInt("FromId");
                String query2 = "Select u.UserName from Users u where u.Id=?";
                statement = connection.prepareStatement(query2);
                statement.setString(1, Integer.toString(fromId));
                mail.setId(rs.getInt("Id"));
                mail.setFromUser(rs.getString("FromUser"));
                mail.setSubject(rs.getString("Subject"));
                mail.setBody(rs.getString("Body"));
                mail.setSendDate(rs.getTimestamp("SendDate"));
                ResultSet rs2 = statement.executeQuery();

                while (rs2.next()) {
                    mail.setSendUser(rs2.getString("UserName"));
                }
                mails.add(mail);
            }
            statement.close();
            connection.close();
            rs.close();
            return mails;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<SentMail> GetFromMail(int userId) {
        try {
            PreparedStatement statement;
            context = new DbContext();
            connection = context.getConnection();
            String query = "Select sm.Id,u.UserName as FromUser,sm.FromId,sm.Subject,sm.Body,sm.SendDate,sm.Attachment From SentMail sm "
                    + "inner join Users u on u.Id=sm.SendId where sm.FromId=?";
            statement = connection.prepareStatement(query);
            statement.setString(1, Integer.toString(userId));
            ResultSet rs = statement.executeQuery();
            List<SentMail> mails = new ArrayList<SentMail>();
            while (rs.next()) {
                SentMail mail = new SentMail();
                int fromId = rs.getInt("FromId");
                String query2 = "Select u.UserName from Users u where u.Id=?";
                statement = connection.prepareStatement(query2);
                statement.setString(1, Integer.toString(fromId));
                mail.setId(rs.getInt("Id"));
                mail.setFromUser(rs.getString("FromUser"));
                mail.setSubject(rs.getString("Subject"));
                mail.setBody(rs.getString("Body"));
                mail.setSendDate(rs.getTimestamp("SendDate"));
                ResultSet rs2 = statement.executeQuery();

                while (rs2.next()) {
                    mail.setSendUser(rs2.getString("UserName"));
                }
                mails.add(mail);
            }
            statement.close();
            connection.close();
            rs.close();
            return mails;
        } catch (Exception ex) {
            return null;
        }
    }

    public void DeletedMail(int mailId) {
        try {
            PreparedStatement statement;
            context = new DbContext();
            connection = context.getConnection();
            String selectQuery="Select * From SentMail sm where sm.Id=?";
            statement = connection.prepareStatement(selectQuery);
            statement.setString(1, Integer.toString(mailId));
            ResultSet rs = statement.executeQuery();
            SentMail mail = new SentMail();
            while (rs.next()) {                
                mail.setId(rs.getInt("Id"));
                mail.setFromUser(rs.getString("FromId"));
                mail.setSubject(rs.getString("Subject"));
                mail.setBody(rs.getString("Body"));
                mail.setSendDate(rs.getTimestamp("SendDate"));
                mail.setSendUser(rs.getString("SendId"));                
            }            
            String deleteQuery = "Delete From SentMail where Id=?";
            statement = connection.prepareStatement(deleteQuery);
            statement.setInt(1, mail.getId());
            statement.executeUpdate();
            DeletedMail deleteMail = new DeletedMail();
            deleteMail.setId(mail.getId());
            deleteMail.setFromUser(mail.getFromUser());
            deleteMail.setSendUser(mail.getSendUser());
            deleteMail.setBody(mail.getBody());
            deleteMail.setDeletedDate(new java.sql.Timestamp(new java.util.Date().getTime()));
            deleteMail.setSubject(mail.getSubject());
            deleteMail.setAttachment(mail.getAttachment());
            DeletedMailService mailService=new DeletedMailService();
            mailService.AddMail(deleteMail);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
