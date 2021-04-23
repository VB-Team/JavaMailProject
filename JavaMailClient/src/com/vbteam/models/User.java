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
public class User {

    /**
     * DB Constructor username,password,roleid,registerdate,lastlogin ,First
     * name ,lastname
     */

    private String userName, password, firstName, lastName;
    private int roleId;
    private Date registerDate, lastLogin;

    public User() {}

    public User(String userName, String password, int roleId, Date registerDate, Date lastLogin, String firstName, String lastName) {
        this.userName = userName;
        this.password = password;
        this.roleId = roleId;
        this.registerDate = registerDate;
        this.lastLogin = lastLogin;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }
}
