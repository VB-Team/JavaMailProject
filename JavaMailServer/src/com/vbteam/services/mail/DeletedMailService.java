/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.services.mail;

import com.vbteam.models.DeletedMail;
import com.vbteam.models.IMail;
import com.vbteam.utils.DbContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

/**
 *
 * @author schea
 */
public class DeletedMailService {
    DbContext context;
    Connection connection;
    List<IMail> mails = new ArrayList<IMail>();
    public void deleteMail(DeletedMail mail){
    try {            
            PreparedStatement statement;            
            context = new DbContext();
            connection = context.getConnection();            
            String deleteQuery="Delete From DeletedMail where Id=?";
            statement = connection.prepareStatement(deleteQuery);
            statement.setInt(1, mail.getId());
            int a=statement.executeUpdate();
            System.out.println("Etkilenen Satır Sayısı "+a);
            statement.close();
            connection.close();
    }catch(Exception ex){System.out.println("Server Deleted Mail Service Exception : "+ex.toString());}
    }
    public List<IMail> getDeletedMail(int userId){
    try {
            PreparedStatement statement;
            context = new DbContext();
            connection = context.getConnection();
            String query = "Select dm.Id,u.UserName as RecipientUser,dm.RecipientId,dm.Subject,dm.Body,dm.DeletedDate,dm.Attachment,dm.AttachmentType From DeletedMail"
                    + " dm inner join Users u on u.Id=dm.SenderId where dm.SenderId=?";
            statement = connection.prepareStatement(query);
            statement.setString(1, Integer.toString(userId));
            ResultSet rs = statement.executeQuery();
            
            while (rs.next()) {
                DeletedMail mail = new DeletedMail();
                int fromId = rs.getInt("RecipientId");
                String query2 = "Select u.UserName from Users u where u.Id=?";
                statement = connection.prepareStatement(query2);
                statement.setString(1, Integer.toString(fromId));
                mail.setId(rs.getInt("Id"));
                mail.setRecipientUser(rs.getString("RecipientUser"));
                mail.setSubject(rs.getString("Subject"));
                mail.setBody(rs.getString("Body"));
                //mail.setDeletedDate(rs.getTimestamp("DeletedDate"));//DateTimeFix
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
            System.out.println("Server Deleted Mail Service Exception : "+ex.toString());
            return mails;            
        }
    }
    public void addDeletedMail(DeletedMail mail){
        try {
            List<Integer> Id = new ArrayList<Integer>();
            PreparedStatement statement;
            context = new DbContext();
            connection = context.getConnection();            
            String deleteQuery="Insert into DeletedMail(RecipientId,SenderId,Subject,Body,Attachment,DeletedDate,AttachmentType)values(?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(deleteQuery);            
            statement.setString(1, mail.getRecipientUser());
            statement.setString(2, mail.getSenderUser());
            statement.setString(3, mail.getSubject());
            statement.setString(4, mail.getBody());
            statement.setBytes(5, mail.getAttachment());
            statement.setTimestamp(6, mail.getDeletedDate());
            statement.setString(7, mail.getAttachmentType());
            int a = statement.executeUpdate();
            System.out.println("Etkilenen satır sayısı " + a);
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Server Deleted Mail Service Exception : "+e.toString());
        }
    }
}
