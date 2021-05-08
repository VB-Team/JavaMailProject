/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.utils;

import com.vbteam.models.Command;
import com.vbteam.models.User;
import com.vbteam.services.authenticate.AuthService;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author schea
 */
public class CommandHandler {

    private static AuthService service=new AuthService();
    
    public static void Handler(ObjectInputStream objInput, ObjectOutputStream objOutput, Command cmd) {
        if (cmd.getType().indexOf("auth")==0) {            
            Auth(cmd);
        }
    }

    private static void Auth(Command cmd) {
        if (cmd.getType().equals("auth-exist")) {
            service.UserExist(cmd.getUser().getUserName());
            System.out.println("exist");
        }
        if (cmd.getType().equals("auth-register")) {            
            service.Register(cmd.getUser());
            System.out.println("register");
        }
        if (cmd.getType().equals("auth-login")) {
            try {                
                service.Login(cmd.getUser().getUserName(), cmd.getUser().getPassword());
                System.out.println("login");
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
