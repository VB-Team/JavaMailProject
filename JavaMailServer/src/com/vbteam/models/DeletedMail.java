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
public class DeletedMail implements IMail, Serializable {

    private static final long serialVersionUID = 45087847;

    private int Id;
    private String RecipientUser;
    private String SenderUser;
    private String Subject;
    private Timestamp DeletedDate;
    private String Body;
    private byte[] Attachment;
    private String AttachmentType;

    public DeletedMail() {
    }

    public DeletedMail(String SentUser, String SenderUser, String Subject, Timestamp DeletedDate, String Body, byte[] Attachment) {
        this.RecipientUser = SentUser;
        this.SenderUser = SenderUser;
        this.Subject = Subject;
        this.DeletedDate = DeletedDate;
        this.Body = Body;
        this.Attachment = Attachment;
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
     * @return the DeletedDate
     */
    public Timestamp getDeletedDate() {
        return DeletedDate;
    }

    /**
     * @param DeletedDate the DeletedDate to set
     */
    public void setDeletedDate(Timestamp DeletedDate) {
        this.DeletedDate = DeletedDate;
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
