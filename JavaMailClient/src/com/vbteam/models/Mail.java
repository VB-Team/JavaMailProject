/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.models;

/**
 *
 * @author BatuPC
 */
public class Mail {
    private String mailAuthor;
    private String mailTime;
    private String mailHeaderMessage;
    
    public Mail(String author,String time ,String message){
        this.mailAuthor = author;
        this.mailTime = time;
        this.mailHeaderMessage = message;
    }
    
    public String getAuthor(){
        return mailAuthor;
    }
    
    public String getTime(){
        return mailTime;
    }
    
    public String getHeaderMessage(){
        return mailHeaderMessage;
    }
}
