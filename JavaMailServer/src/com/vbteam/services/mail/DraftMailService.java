/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.services.mail;

import com.vbteam.models.DeletedMail;
import com.vbteam.models.DraftMail;
import com.vbteam.models.IMail;
import com.vbteam.models.SentMail;
import com.vbteam.utils.DbContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author schea
 */
public class DraftMailService {
    DbContext context;
    Connection connection;
    public void addDraftMail(DraftMail mail) {
        try {
            Integer fromId =null;
            Integer sendId=null;
            PreparedStatement statement;
            
            context = new DbContext();
            connection = context.getConnection();
            if (mail.getSentUser()!=null) {
                fromId = context.getUser(mail.getSentUser());
            }else if(mail.getSenderUser()!=null)
            {
                sendId = context.getUser(mail.getSenderUser());    
            }                    
            String insertQuery="Insert into DraftMail(SentId,SenderId,Subject,Body,Attachment,CreateDate,AttachmentType)values(?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(insertQuery);  
            if (fromId!=null) {
                statement.setInt(1, fromId);
            }else{statement.setNull(1, Types.NULL);}
            if (sendId!=null) {
                statement.setInt(2, sendId);
            }else{statement.setNull(2, Types.NULL);}
            if (mail.getSubject()!=null) {
                statement.setString(3, mail.getSubject());
            }else{statement.setNull(3, Types.NULL);}
            if (mail.getBody()!=null) {
                statement.setString(4, mail.getBody());
            }else{statement.setNull(4, Types.NULL);}
            if (mail.getAttachment()!=null) {
                statement.setBytes(5, mail.getAttachment());
            }else{statement.setNull(5, Types.NULL);}
            if (mail.getCreateDate()!=null) {
                statement.setTimestamp(6,mail.getCreateDate());
            }else{statement.setNull(6, Types.NULL);}
            if(mail.getAttachmentType()!=null)
                statement.setString(7, mail.getAttachmentType());
            else
                statement.setNull(7, Types.NULL);
            int a=statement.executeUpdate();
            System.out.println("Etkilenen satır sayısı "+a);
            statement.close();
            connection.close();            
        } catch (Exception ex) {
            System.err.println("Server Draft Mail Exception : "+ex.getLocalizedMessage());
        }
    }
    public void deleteDraftMail(int mailId) {
        try {
            PreparedStatement statement;
            context = new DbContext();
            connection = context.getConnection();
            String selectQuery="Select * From DraftMail sm where sm.Id=?";
            statement = connection.prepareStatement(selectQuery);
            statement.setString(1, Integer.toString(mailId));
            ResultSet rs = statement.executeQuery();
            DraftMail mail = new DraftMail();
            while (rs.next()) {                
                mail.setId(rs.getInt("Id"));
                mail.setSentUser(rs.getString("SentId"));
                mail.setSubject(rs.getString("Subject"));
                mail.setBody(rs.getString("Body"));
                //mail.setCreateDate(rs.getTimestamp("CreateDate"));//DateTimeFix
                mail.setSenderUser(rs.getString("SenderId")); 
                mail.setAttachment(rs.getBytes("Attachment"));
                mail.setAttachmentType(rs.getString("AttachmentType"));
            }            
            String deleteQuery = "Delete From DraftMail where Id=?";
            statement = connection.prepareStatement(deleteQuery);
            statement.setInt(1, mail.getId());
            statement.executeUpdate();
            DeletedMail deleteMail = new DeletedMail();
            deleteMail.setId(mail.getId());
            deleteMail.setSentUser(mail.getSentUser());
            deleteMail.setSenderUser(mail.getSenderUser());
            deleteMail.setBody(mail.getBody());
            deleteMail.setDeletedDate(new java.sql.Timestamp(new java.util.Date().getTime()));
            deleteMail.setSubject(mail.getSubject());
            deleteMail.setAttachment(mail.getAttachment());
            DeletedMailService mailService=new DeletedMailService();
            mailService.addDeletedMail(deleteMail);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public List<IMail> getDraftMails(int userId) {
        try {
            PreparedStatement statement;
            context = new DbContext();
            connection = context.getConnection();
            String query = "Select dm.Id,u.UserName as SendedUser,dm.SentId,dm.Subject,dm.Body,dm.CreateDate,dm.Attachment,dm.AttachmentType From DraftMail dm "
                    + "inner join Users u on u.Id=dm.SenderId where dm.SenderId=?";
            statement = connection.prepareStatement(query);
            statement.setString(1, Integer.toString(userId));
            ResultSet rs = statement.executeQuery();
            List<IMail> mails = new ArrayList<IMail>();
            while (rs.next()) {
                DraftMail mail = new DraftMail();
                int fromId = rs.getInt("SentId");
                String query2 = "Select u.UserName from Users u where u.Id=?";
                statement = connection.prepareStatement(query2);
                statement.setString(1, Integer.toString(fromId));
                mail.setId(rs.getInt("Id"));
                mail.setSentUser(rs.getString("SendedUser"));
                mail.setSubject(rs.getString("Subject"));
                mail.setBody(rs.getString("Body"));
                //mail.setCreateDate(rs.getTimestamp("CreateDate"));//DateTimeFix
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
            System.out.println("Server Get Draft Mail Exception : " + ex.getMessage());
            return null;
        }
    }
}
