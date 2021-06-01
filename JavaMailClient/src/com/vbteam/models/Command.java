/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author schea
 */
public class Command implements Serializable {

    private static final long serialVersionUID = 45087845;

    private String type, commandText;
    private User user;
    private boolean boolResponse;
    private Mail mail;
    private Object data;

    public Command() {
    }

    public Command(String type, String commandText, User user, Mail mail) {
        this.type = type;
        this.commandText = commandText;
        this.user = user;
        this.mail = mail;
    }

    public void setObject(Object _object) {
        data = _object;
    }
    
    public Object getObject(){
        return data;
    }

    public boolean getBoolResponse() {
        return boolResponse;
    }

    public void setBoolResponse(boolean bool) {
        this.boolResponse = bool;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the commandText
     */
    public String getCommandText() {
        return commandText;
    }

    /**
     * @param commandText the commandText to set
     */
    public void setCommandText(String commandText) {
        this.commandText = commandText;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the mail
     */
    public Mail getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(Mail mail) {
        this.mail = mail;
    }
}
