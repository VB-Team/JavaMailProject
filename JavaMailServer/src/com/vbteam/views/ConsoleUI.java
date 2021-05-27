package com.vbteam.views;

import com.vbteam.models.User;
import com.vbteam.services.authenticate.AuthService;
import java.util.ArrayList;
import java.util.List;
import com.vbteam.models.Mail;
import com.vbteam.services.mail.MailService;
import java.security.SecureRandom;
import java.util.Random;

/**
 *
 * @author schea
 */
public class ConsoleUI {

    public static void main(String args[]) {
        Server server = new Server();
        server.Connect();
        //DeleteMail(2);
        //SentMail();
        //DraftMail();
        //LoginAndRegister();
    }

    private static void LoginAndRegister() {

        AuthService service = new AuthService();
        User user = new User();
        user.setUserName("Batu");
        user.setPassword("2");
        user.setFirstName("Batu");
        user.setLastName("Batu");
        user.setRole("Admin");
        user.setLastLogin(new java.sql.Timestamp(new java.util.Date().getTime()));
        service.register(user);
        user = service.login(user.getUserName(), user.getPassword());
        System.out.println(user.getLastLogin());
    }

    private static void DraftMail() {
        Mail mail = new Mail();
        mail.setAttachment(new byte[]{-56, -123, -109, -109, -106, 64, -26,
            -106, -103, -109, -124, 90});
        mail.setAttachmentDetail("Deneme");
        mail.setRecipientUser("Veysel");
        mail.setSenderUser("batu");
        mail.setBody("sdfsf");
        mail.setSubject("dnesdfsdfemee");
        mail.setCreateDate(new java.sql.Timestamp(new java.util.Date().getTime()));
        mail.setType("Draft");
        mail.setState(true);
        MailService sm = new MailService();
        List<Mail> mails = new ArrayList<Mail>();
        mails.add(mail);
        mails.clear();
        mails = sm.getAnyMails(2, "Draft");
        System.out.println("---------------DRAFT MAİLS------------");
        for (Mail mail1 : mails) {
            System.out.println(mail1.getBody());
        }
    }

    private static void SentMail() {
        Mail mail = new Mail();
        mail.setAttachment(new byte[]{-56, -123, -109, -109, -106, 64, -26,
            -106, -103, -109, -124, 90});
        mail.setAttachmentDetail("Deneme");
        mail.setRecipientUser("Veysel");
        mail.setSenderUser("batu");
        mail.setBody("sdfsf");
        mail.setSubject("dnesdfsdfemee");
        mail.setCreateDate(new java.sql.Timestamp(new java.util.Date().getTime()));
        mail.setType("Normal");
        mail.setState(true);
        MailService sm = new MailService();
        List<Mail> mails = new ArrayList<Mail>();
        mails.add(mail);
        mails.clear();
        mails = sm.getIncomingMails(1);
        System.out.println("---------------İNCOMİNG MAİLS------------");
        for (Mail mail1 : mails) {
            System.out.println(mail1.getBody());
        }
        mails.clear();
        mails = sm.getOutgoingMails(1);
        System.out.println("---------------OUTGOİNG MAİLS------------");
        for (Mail mail1 : mails) {
            System.out.println(mail1.getBody());
        }
    }

    private static void DeleteMail(int mailId) {

        Mail mail = new Mail();
        mail.setAttachment(new byte[]{-56, -123, -109, -109, -106, 64, -26,
            -106, -103, -109, -124, 90});
        mail.setAttachmentDetail("Deneme");
        mail.setRecipientUser("Veysel");
        mail.setSenderUser("batu");
        mail.setBody("sdfsf");
        mail.setSubject("dnesdfsdfemee");
        mail.setCreateDate(new java.sql.Timestamp(new java.util.Date().getTime()));
        mail.setType("delete");
        mail.setState(true);
        MailService sm = new MailService();
        List<Mail> mails = new ArrayList<Mail>();
        mails.add(mail);
        //sm.deleteMail(mailId);
        mails.clear();
        mails = sm.getAnyMails(mailId, "Normal");
        System.out.println("---------------DELETED MAİLS------------");
        for (Mail mail1 : mails) {
            System.out.println(mail1.getBody());
        }
    }

}
