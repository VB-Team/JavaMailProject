package com.vbteam.views;

import com.vbteam.models.IMail;
import com.vbteam.models.SentMail;
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
        //server.Connect();
        SentMail mail=new SentMail();
        mail.setAttachment(new byte[] { -56, -123, -109, -109, -106, 64, -26,
        -106, -103, -109, -124, 90 });
        mail.setAttachmentType("Deneme");
        mail.setSentUser("capuluos");
        mail.setSenderUser("asdasd");
        mail.setBody("sdfsf");
        mail.setSubject("dnesdfsdfemee");
        SentMailService sm=new SentMailService();
        List<IMail> mails=new ArrayList<IMail>();
        mails.add(mail);
        //sm.addMail(mails);
        mails=sm.getOutgoingMail(9);
        for (IMail mail1 : mails) {
            System.out.println(mail1.getSentUser());
        }
    }

}
