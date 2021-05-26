/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.models;

import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;

/**
 *
 * @author schea
 */
public class DraftMail implements IMail, Serializable {

  

    private static final long serialVersionUID = 45087846;
    
    private int Id;
    private String SenderUser;
    private String RecipientUser;
    private String Body;
    private byte[] Attachment;
    private String Subject;
    private Timestamp CreateDate;
    private String AttachmentType;

    public DraftMail() {
    }

    public DraftMail(String SenderUser, String SentUser, String Body, byte[] Attachment, String Subject, Timestamp CreateDate) {
        this.SenderUser = SenderUser;
        this.RecipientUser = SentUser;
        this.Body = Body;
        this.Attachment = Attachment;
        this.Subject = Subject;
        this.CreateDate = CreateDate;
    }

    /**
     * @return the Id
     */
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
     * @return the SenderUser
     */
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
     * @return the RecipientUser
     */
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
     * @return the Body
     */
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
     * @return the Subject
     */
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
     * @param CreateDate the CreateDate to set
     */
    public void setCreateDate(Timestamp CreateDate) {
        this.CreateDate = CreateDate;
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
