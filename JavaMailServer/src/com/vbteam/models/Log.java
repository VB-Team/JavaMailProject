/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.models;

import java.util.Date;
import java.sql.Timestamp;
/**
 *
 * @author schea
 */
public class Log {
    private static final long serialVersionUID = 45087843;
    private int Id;
    private Timestamp createDate;
    private String Type;
    private String ExceptionMessage;

    public Log() {
    }

    public Log( Timestamp createDate, String Type, String ExceptionMessage) {
        this.createDate = createDate;
        this.Type = Type;
        this.ExceptionMessage = ExceptionMessage;
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
     * @return the createDate
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
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
     * @return the ExceptionMessage
     */
    public String getExceptionMessage() {
        return ExceptionMessage;
    }

    /**
     * @param ExceptionMessage the ExceptionMessage to set
     */
    public void setExceptionMessage(String ExceptionMessage) {
        this.ExceptionMessage = ExceptionMessage;
    }
}
