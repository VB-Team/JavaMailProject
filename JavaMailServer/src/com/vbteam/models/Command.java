/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.models;

import java.io.Serializable;

/**
 *
 * @author schea
 */
public class Command implements Serializable {

    private static final long serialVersionUID = 45087848;

    private String type, commandText;
    private User user;
    private boolean boolResponse;
    private IMail mail;

    public Command() {
    }

    public Command(String type, String commandText, User user, IMail mail) {
        this.type = type;
        this.commandText = commandText;
        this.user = user;
        this.mail = mail;
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
    public IMail getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(IMail mail) {
        this.mail = mail;
    }
}
