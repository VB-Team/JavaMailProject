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
public class Mail implements Serializable{

    /**
     * @return the SenderUser
     */
    public String getSenderUser() {
        return SenderUser;
    }

    /**
     * @param SenderUser the SenderUser to set
     */
    public void setSenderUser(String SenderUser) {
        this.SenderUser = SenderUser;
    }

    private int Id;
    private String SenderUser;
    private String RecipientUser;
    private String Subject;
    private String Body;
    private byte[] Attachment;
    private String AttachmentDetail;
    private Timestamp CreateDate;
    private String Type;
    private boolean State;

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
     * @return the RecipientUser
     */
    public String getRecipientUser() {
        return RecipientUser;
    }

    /**
     * @param RecipientUser the RecipientUser to set
     */
    public void setRecipientUser(String RecipientUser) {
        this.RecipientUser = RecipientUser;
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
     * @return the AttachmentDetail
     */
    public String getAttachmentDetail() {
        return AttachmentDetail;
    }

    /**
     * @param AttachmentDetail the AttachmentDetail to set
     */
    public void setAttachmentDetail(String AttachmentDetail) {
        this.AttachmentDetail = AttachmentDetail;
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
     * @return the Type
     */
    public String getType() {
        return Type;
    }

    /**
     * @param Type the Type to set
     */
    public void setType(String Type) {
        this.Type = Type;
    }

    /**
     * @return the State
     */
    public boolean isState() {
        return State;
    }

    /**
     * @param State the State to set
     */
    public void setState(boolean State) {
        this.State = State;
    }

}
