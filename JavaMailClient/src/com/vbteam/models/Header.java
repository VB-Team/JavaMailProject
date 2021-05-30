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
public class Header implements Serializable{
private static final long serialVersionUID = 45087844;
   
   
    private int Id;
    private int MailId;
    private String SenderUser;
    private String RecipientUser;      
    
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
     /**
     * @return the MailId
     */
    public int getMailId() {
        return MailId;
    }

    /**
     * @param MailId the MailId to set
     */
    public void setMailId(int MailId) {
        this.MailId = MailId;
    }

}
