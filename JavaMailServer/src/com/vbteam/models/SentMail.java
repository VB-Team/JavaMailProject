/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.models;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author schea
 */
public class SentMail implements IMail, Serializable {

   
    private static final long serialVersionUID = 45087845;
    
    private int Id;
    private String RecipientUser;
    private String SenderUser;
    private String Subject;
    private Timestamp CreateDate;
    private String Body;
    private byte[] Attachment;
    private String AttachmentType;

    public SentMail() {
    }

    public SentMail( String SentUser, String SenderUser, String Subject, Timestamp SendDate, String Body, byte[] Attachment) {
        this.Id = Id;
        this.RecipientUser = SentUser;
        this.SenderUser = SenderUser;
        this.Subject = Subject;
        this.CreateDate = SendDate;
        this.Body = Body;
        this.Attachment = Attachment;
    }

    /**
     * @return the Id
     */
    @Override
    public int getId() {
        return Id;
    }

    /**
     * @param Id the Id to set
     */
    public void setId(int Id) {
        this.Id = Id;
    }

    /**
     * @return the RecipientUser
     */
    @Override
    public String getRecipientUser() {
        return RecipientUser;
    }

    /**
     * @param FromUser the RecipientUser to set
     */
    public void setRecipientUser(String FromUser) {
        this.RecipientUser = FromUser;
    }

    /**
     * @return the SenderUser
     */
    @Override
    public String getSenderUser() {
        return SenderUser;
    }

    /**
     * @param SendUser the SenderUser to set
     */
    public void setSenderUser(String SendUser) {
        this.SenderUser = SendUser;
    }

    /**
     * @return the Subject
     */
    @Override
    public String getSubject() {
        return Subject;
    }

    /**
     * @param Subject the Subject to set
     */
    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    /**
     * @return the CreateDate
     */
    public Timestamp getCreateDate() {
        return CreateDate;
    }

    /**
     * @param SendDate the CreateDate to set
     */
    public void setCreateDate(Timestamp SendDate) {
        this.CreateDate = SendDate;
    }

    /**
     * @return the Body
     */
    @Override
    public String getBody() {
        return Body;
    }

    /**
     * @param Body the Body to set
     */
    public void setBody(String Body) {
        this.Body = Body;
    }

    /**
     * @return the Attachment
     */
    @Override
    public byte[] getAttachment() {
        return Attachment;
    }

    /**
     * @param Attachment the Attachment to set
     */
    public void setAttachment(byte[] Attachment) {
        this.Attachment = Attachment;
    }
     /**
     * @return the AttachmentType
     */
    public String getAttachmentType() {
        return AttachmentType;
    }

    /**
     * @param AttachmentType the AttachmentType to set
     */
    public void setAttachmentType(String AttachmentType) {
        this.AttachmentType = AttachmentType;
    }


}
