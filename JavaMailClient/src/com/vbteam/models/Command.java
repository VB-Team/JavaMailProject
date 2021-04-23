/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.models;

import java.util.Date;

/**
 *
 * @author BatuPC
 */
public class Command {

    private String type, commandText;
    private User user;
    private Date date;
    private Boolean rValue;

    //Empty Constructor
    public Command() {}

    //Default Constructor
    public Command(String type, User user, String commandText) {
        this.type = type;
        this.user = user;
        this.commandText = commandText;
    }

    //Auth constructor
    public Command(String type, User user) {
        this.type = type;
        this.user = user;
    }

    //Boolean response constructor
    public Command(String type, User user, Boolean rValue) {
        this.type = type;
        this.user = user;
        this.rValue = rValue;
    }

    public Boolean getReturnValue() {
        return this.rValue;
    }

    public String getCommandText() {
        return this.commandText;
    }

    public String getUsername() {
        return this.user.getUsername();
    }

    public User getUserModel() {
        return this.user;
    }

    public String getType() {
        return this.type;
    }

    public Date getDate() {
        return this.date;
    }
}
