/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.models;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author schea
 */
public class User implements Serializable {
    
    private static final long serialVersionUID = 45087844;

    private int Id;
    private String Role;
    private String UserName;
    private String Password;
    private Date RegisterDate;
    private Timestamp LastLogin;
    private String FirstName;
    private String LastName;

    public User() {
    }

    public User(int Id, String Role, String UserName, String Password, Date RegisterDate, Timestamp LastLogin, String FirstName, String LastName) {
        this.Id = Id;
        this.Role = Role;
        this.UserName = UserName;
        this.Password = Password;
        this.RegisterDate = RegisterDate;
        this.LastLogin = LastLogin;
        this.FirstName = FirstName;
        this.LastName = LastName;
    }

    /**
     * @return the FirstName
     */
    public String getFirstName() {
        return FirstName;
    }

    /**
     * @param FirstName the FirstName to set
     */
    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    /**
     * @return the LastName
     */
    public String getLastName() {
        return LastName;
    }

    /**
     * @param LastName the LastName to set
     */
    public void setLastName(String LastName) {
        this.LastName = LastName;
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
     * @return the RoleId
     */
    public String getRole() {
        return Role;
    }

    /**
     * @param RoleId the RoleId to set
     */
    public void setRole(String Role) {
        this.Role = Role;
    }

    /**
     * @return the UserName
     */
    public String getUserName() {
        return UserName;
    }

    /**
     * @param UserName the UserName to set
     */
    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    /**
     * @return the Password
     */
    public String getPassword() {
        return Password;
    }

    /**
     * @param Password the Password to set
     */
    public void setPassword(String Password) {
        this.Password = Password;
    }

    /**
     * @return the RegisterDate
     */
    public Date getRegisterDate() {
        return this.RegisterDate;
    }

    /**
     * @param RegisterDate the RegisterDate to set
     */
    public void setRegisterDate(Date RegisterDate) {
        this.RegisterDate = RegisterDate;
    }

    /**
     * @return the LastLogin
     */
    public Timestamp getLastLogin() {
        return LastLogin;
    }

    /**
     * @param LastLogin the LastLogin to set
     */
    public void setLastLogin(Timestamp LastLogin) {
        this.LastLogin = LastLogin;
    }
}
