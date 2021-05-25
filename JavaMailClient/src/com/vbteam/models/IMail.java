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
public interface IMail {
    public int getId();
    public String getFromUser();
    public String getSendUser();
    public String getSubject();
    public String getBody();
}
