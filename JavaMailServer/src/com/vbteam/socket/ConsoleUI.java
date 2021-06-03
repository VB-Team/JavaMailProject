/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.socket;

import com.vbteam.models.Attachment;
import com.vbteam.models.Header;
import com.vbteam.models.Mail;
import com.vbteam.services.mail.MailService;
import static com.vbteam.socket.Server.connectionPool;
import com.vbteam.utils.ConnectionPool;
import java.util.ArrayList;
import java.util.List;
import com.vbteam.utils.IConnectionPool;

/**
 *
 * @author schea
 */
public class ConsoleUI {

    public static IConnectionPool connectionPool;

    public static void main(String[] args) {
        try {
            connectionPool = ConnectionPool.create("jdbc:sqlserver://localhost:1453;databasename=MailServer", "sa", "Password1!");
        } catch (Exception e) {
        }
        MailService mailser = MailService.getInstance();
        Mail mail = new Mail();
        mail.setBody("asd");
        mail.setSubject("asdad");
        List<Attachment> attachments = new ArrayList<Attachment>();
        Attachment attachment = new Attachment();
        attachments.add(attachment);
        List<Header> headers = new ArrayList<Header>();
        Header header = new Header();
        headers.add(header);
        mail.setAttachmentState(false);
        mail.setAttachments(attachments);
        mail.setHeaders(headers);
        mail.setCreateDate(new java.sql.Timestamp(new java.util.Date().getTime()));
        mailser.addMail(mail);
    }
}
