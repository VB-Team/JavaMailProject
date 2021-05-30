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
public class Attachment implements Serializable{
private static final long serialVersionUID = 45087846;
    
    private int Id;
    private String AttachmentName;
    private int AttachmentSize;
    private byte[] AttachmentContent;
    private String AttachmentType;
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
     * @return the AttachmentName
     */
    public String getAttachmentName() {
        return AttachmentName;
    }

    /**
     * @param AttachmentName the AttachmentName to set
     */
    public void setAttachmentName(String AttachmentName) {
        this.AttachmentName = AttachmentName;
    }

    /**
     * @return the AttachmentSize
     */
    public int getAttachmentSize() {
        return AttachmentSize;
    }

    /**
     * @param AttachmentSize the AttachmentSize to set
     */
    public void setAttachmentSize(int AttachmentSize) {
        this.AttachmentSize = AttachmentSize;
    }

    /**
     * @return the AttachmentContent
     */
    public byte[] getAttachmentContent() {
        return AttachmentContent;
    }

    /**
     * @param AttachmentContent the AttachmentContent to set
     */
    public void setAttachmentContent(byte[] AttachmentContent) {
        this.AttachmentContent = AttachmentContent;
    }

    /**
     * @return the AttachmentType
     */
    public String getAttachmentType() {
        return AttachmentType;
    }

    /**
     * @param AttachmentType the AttachmentType to set
     */
    public void setAttachmentType(String AttachmentType) {
        this.AttachmentType = AttachmentType;
    }
    
}
