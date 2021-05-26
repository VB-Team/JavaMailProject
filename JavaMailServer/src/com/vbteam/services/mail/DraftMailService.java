/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.services.mail;

import com.vbteam.models.DeletedMail;
import com.vbteam.models.DraftMail;
import com.vbteam.utils.DbContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

/**
 *
 * @author schea
 */
public class DraftMailService {
    DbContext context;
    Connection connection;
    public void AddMail(DraftMail mail) {
        try {
            Integer fromId =null;
            Integer sendId=null;
            PreparedStatement statement;
            
            context = new DbContext();
            connection = context.getConnection();
            if (mail.getFromUser()!=null) {
                fromId = context.getUser(mail.getFromUser());
            }else if(mail.getSendUser()!=null)
            {
                sendId = context.getUser(mail.getSendUser());    
            }                    
            String insertQuery="Insert into DraftMail(FromId,SendId,Subject,Body,Attachment,CreateDate)values(?,?,?,?,?,?)";
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
            int a=statement.executeUpdate();
            System.out.println("Etkilenen satır sayısı "+a);
            statement.close();
            connection.close();            
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    public void DeletedMail(int mailId) {
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
                mail.setFromUser(rs.getString("FromId"));
                mail.setSubject(rs.getString("Subject"));
                mail.setBody(rs.getString("Body"));
                mail.setCreateDate(rs.getTimestamp("CreateDate"));
                mail.setSendUser(rs.getString("SendId"));                
            }            
            String deleteQuery = "Delete From DraftMail where Id=?";
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
