/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.models;

/**
 *
 * @author schea
 */
public interface IMail {

    public int getId();

    public String getRecipientUser();

    public String getSenderUser();

    public String getSubject();

    public String getBody();
    
    public byte[] getAttachment();
    
    public String getAttachmentType();
}
