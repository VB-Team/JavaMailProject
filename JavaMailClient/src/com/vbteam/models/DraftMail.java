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
public class DraftMail implements IMail,Serializable{
    private int Id;
    private String SendUser;
    private String FromUser;
    private String Body;
    private byte[] Attachment;
    private String Subject;
    private Timestamp CreateDate;
    
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
     * @return the SendUser
     */
    public String getSendUser() {
        return SendUser;
    }

    /**
     * @param SendUser the SendUser to set
     */
    public void setSendUser(String SendUser) {
        this.SendUser = SendUser;
    }

    /**
     * @return the FromUser
     */
    public String getFromUser() {
        return FromUser;
    }

    /**
     * @param FromUser the FromUser to set
     */
    public void setFromUser(String FromUser) {
        this.FromUser = FromUser;
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

}
