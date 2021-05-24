package com.vbteam.views;

import com.vbteam.models.User;
import com.vbteam.services.authenticate.AuthService;

/**
 *
 * @author schea
 */
public class ConsoleUI {

    public static void main(String args[]) {
        Server server = new Server();
        //server.Connect();     
        AuthService service = new AuthService();
        User user = new User();
        user.setFirstName("Veysel");
        user.setLastName("Veysel");
        user.setUserName("Veysel");
        user.setPassword("Veysel");
        user.setRole("Admin");
        User user2 = service.Login("Veysel","Veysel");
        System.out.println(user2.getRegisterDate().toString());

    }

}
