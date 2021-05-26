/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.models;

import java.sql.Timestamp;

/**
 *
 * @author schea
 */
public class SentMail implements IMail {

    private int Id;
    private String FromUser;
    private String SendUser;
    private String Subject;
    private Timestamp SendDate;
    private String Body;
    private byte[] Attachment;

    public SentMail() {
    }

    public SentMail(int Id, String FromUser, String SendUser, String Subject, Timestamp SendDate, String Body, byte[] Attachment) {
        this.Id = Id;
        this.FromUser = FromUser;
        this.SendUser = SendUser;
        this.Subject = Subject;
        this.SendDate = SendDate;
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
     * @return the SendDate
     */
    public Timestamp getSendDate() {
        return SendDate;
    }

    /**
     * @param SendDate the SendDate to set
     */
    public void setSendDate(Timestamp SendDate) {
        this.SendDate = SendDate;
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

}
