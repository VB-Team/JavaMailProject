/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author schea
 */

public class Mail implements Serializable {        
private static final long serialVersionUID = 45087842;
    private int Id;    
    private boolean AttachmentState;
    private String Subject;
    private String Body;  
    private Timestamp CreateDate;
    private List<Attachment> Attachments;
    private List<Header> Headers;

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
     * @return the Subject
     */
    public String getSubject() {
        return Subject;
    }

    /**
     * @param Subject the Subject to set
     */
    public void setSubject(String Subject) {
        this.Subject = Subject;
    }
    /**
     * @return the Attachments
     */
    public List<Attachment> getAttachments() {
        return Attachments;
    }

    /**
     * @param Attachments the Attachments to set
     */
    public void setAttachments(List<Attachment> Attachments) {
        this.Attachments = Attachments;
    }
    /**
     * @return the AttachmentState
     */
    public boolean isAttachmentState() {
        return AttachmentState;
    }

    /**
     * @param AttachmentState the AttachmentState to set
     */
    public void setAttachmentState(boolean AttachmentState) {
        this.AttachmentState = AttachmentState;
    }
    /**
     * @return the Headers
     */
    public List<Header> getHeaders() {
        return Headers;
    }

    /**
     * @param Headers the Headers to set
     */
    public void setHeaders(List<Header> Headers) {
        this.Headers = Headers;
    }
    /**
     * @return the Body
     */
    public String getBody() {
        return Body;
    }

    /**
     * @param Body the Body to set
     */
    public void setBody(String Body) {
        this.Body = Body;
    }
    /**
     * @return the CreateDate
     */
    public Timestamp getCreateDate() {
        return CreateDate;
    }

    /**
     * @param CreateDate the CreateDate to set
     */
    public void setCreateDate(Timestamp CreateDate) {
        this.CreateDate = CreateDate;
    }

}
