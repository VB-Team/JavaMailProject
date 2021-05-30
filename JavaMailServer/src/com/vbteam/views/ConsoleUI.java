package com.vbteam.views;

import com.vbteam.models.Attachment;
import com.vbteam.models.Header;
import java.util.ArrayList;
import java.util.List;
import com.vbteam.models.Mail;
import com.vbteam.services.mail.MailService;

/**
 *
 * @author schea
 */
public class ConsoleUI {

    public static void main(String args[]) {
        Server server = new Server();
        server.Connect();
    }

    
}
