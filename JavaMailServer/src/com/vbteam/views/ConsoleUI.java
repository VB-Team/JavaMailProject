package com.vbteam.views;

import com.vbteam.models.DeletedMail;
import com.vbteam.models.DraftMail;
import com.vbteam.models.IMail;
import com.vbteam.models.SentMail;
import com.vbteam.services.mail.DeletedMailService;
import com.vbteam.services.mail.DraftMailService;
import com.vbteam.services.mail.SentMailService;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author schea
 */
public class ConsoleUI {

    public static void main(String args[]) {
        Server server = new Server();
        server.Connect();
        //DeleteMail();
        //SentMail();
        //DraftMail();
    }

    private static void DraftMail() {
        DraftMail mail=new DraftMail();
        mail.setAttachment(new byte[] { -56, -123, -109, -109, -106, 64, -26,
            -106, -103, -109, -124, 90 });
        mail.setAttachmentType("Deneme");
        mail.setRecipientUser("capuluos");
        mail.setSenderUser("asdasd");
        mail.setBody("sdfsf");
        mail.setSubject("dnesdfsdfemee");
        DraftMailService sm=new DraftMailService();
        List<IMail> mails=new ArrayList<IMail>();
        mails.add(mail);
        sm.addDraftMail(mail);
        mails.clear();
        mails=sm.getDraftMails(8);
        for (IMail mail1 : mails) {
            System.out.println(mail1.getRecipientUser());
        }
    }

    private static void SentMail() {
        SentMail mail=new SentMail();
        mail.setAttachment(new byte[] { -56, -123, -109, -109, -106, 64, -26,
            -106, -103, -109, -124, 90 });
        mail.setAttachmentType("Deneme");
        mail.setRecipientUser("capuluos");
        mail.setSenderUser("asdasd");
        mail.setBody("sdfsf");
        mail.setSubject("dnesdfsdfemee");
        SentMailService sm=new SentMailService();
        List<IMail> mails=new ArrayList<IMail>();
        mails.add(mail);
        sm.addMail(mails);
        mails.clear();
        mails=sm.getIncomingMail(8);
        for (IMail mail1 : mails) {
            System.out.println(mail1.getRecipientUser());
        }
    }

    private static void DeleteMail() {
        
        DeletedMail mail=new DeletedMail();
        mail.setAttachment(new byte[] { -56, -123, -109, -109, -106, 64, -26,
            -106, -103, -109, -124, 90 });
        mail.setAttachmentType("Deneme");
        mail.setRecipientUser("capuluos");
        mail.setSenderUser("asdasd");
        mail.setBody("sdfsf");
        mail.setSubject("dnesdfsdfemee");
        DeletedMailService sm=new DeletedMailService();
        List<IMail> mails=new ArrayList<IMail>();
        mails.add(mail);
        sm.addDeletedMail(mail);
        mails=sm.getDeletedMail(8);
        for (IMail mail1 : mails) {
            System.out.println(mail1.getRecipientUser());
        }
    }

}
