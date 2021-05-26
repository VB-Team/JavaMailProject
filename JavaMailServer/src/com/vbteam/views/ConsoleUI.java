package com.vbteam.views;

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
        server.Connect();     
        /*AuthService service = new AuthService();
        User user = new User();
        user.setFirstName("Veysel");
        user.setLastName("Veysel");
        user.setUserName("Veysel");
        user.setPassword("Veysel");
        user.setRole("Admin");
        User user2 = service.Login("Veysel","Veysel");
<<<<<<< Updated upstream
<<<<<<< Updated upstream
        System.out.println(user2.getRegisterDate().toString());*/
        /*UserManagementService management=new UserManagementService();
        List<User> users=management.ListUser();
        for (User user : users) {
            System.out.println(user.getFirstName());
        }*/
        SentMailService service=new SentMailService();
        List<SentMail> mails=new ArrayList<SentMail>();
        SentMail mail=new SentMail();
        mail.setSendUser("Update");
        mail.setFromUser("veysel");
        mail.setBody("1. Mail");
        mail.setSubject("1. Mail");
        SentMail mail2=new SentMail();
        mail2.setSendUser("veysel");
        mail2.setFromUser("asdas");
        mail2.setBody("2. Mail");
        mail2.setSubject("2. Mail");             
        mails.add(mail);
        mails.add(mail2);
        service.AddMail(mails);
        /*ILogger logger=new Logger();
        Log log =new Log();
        log.setType("Deneme");
        log.setExceptionMessage("başarılı");
        List<Log> logs=logger.getLog();
        for (Log log1 : logs) {
            System.out.println(log1.getType());
        }*/

=======
=======
>>>>>>> Stashed changes
        System.out.println(user2.getRegisterDate().toString());
        UserManagementService management=new UserManagementService();
        List<User> users=management.ListUser();
        for (User user : users) {
            System.out.println(user.getFirstName());
        }
        */
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
    }

}
