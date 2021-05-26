/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.utils;

import com.vbteam.models.Command;
import com.vbteam.models.IMail;
import com.vbteam.models.SentMail;
import com.vbteam.models.User;
import com.vbteam.services.authenticate.AuthService;
import com.vbteam.services.mail.SentMailService;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author schea
 */
public class CommandHandler {

    private static AuthService authService = new AuthService();
    private static SentMailService sentMailService = new SentMailService();
    private static List<IMail> emailList;

    public static void Handler(ObjectInputStream objInput, ObjectOutputStream objOutput, Command cmd) {
        if (cmd.getType().indexOf("auth") == 0) {
            Auth(objInput, objOutput, cmd);
        }
        if (cmd.getType().indexOf("mail") == 0) {
            Mail(objInput, objOutput, cmd);
        }
    }

    private static void Mail(ObjectInputStream objInput, ObjectOutputStream objOutput, Command cmd) {
        try {
            if (cmd.getType().equals("mail-send")) {
                System.out.println("Mail Debug " + cmd.getMail());
                emailList = new ArrayList<>();
                emailList.add((SentMail) cmd.getMail());
                sentMailService.addMail(emailList);
            }
            if (cmd.getType().equals("mail-get-sent")) {
                System.out.println("Get Mail Debug - ID : " + cmd.getUser().getId());
                int userId = cmd.getUser().getId();

                cmd = new Command();
                cmd.setType("mail-get-sent");
                cmd.setMailList(sentMailService.getOutgoingMail(userId));

                objOutput.writeObject(cmd);
            }
        } catch (Exception ex) {
            System.out.println("Mail Delivery Service Exception : " + ex.getMessage());
        }

    }

    private static void Auth(ObjectInputStream objInput, ObjectOutputStream objOutput, Command cmd) {
        try {
            if (cmd.getType().equals("auth-exist")) {
                boolean bool = authService.userExist(cmd.getUser().getUserName());

                cmd = new Command();
                cmd.setType("response");
                cmd.setBoolResponse(bool);

                objOutput.writeObject(cmd);
            }
            if (cmd.getType().equals("auth-register")) {
                User _user = authService.register(cmd.getUser());
                System.out.println("register");

                cmd = new Command();
                cmd.setType("response");
                cmd.setUser(_user);

                objOutput.writeObject(cmd);

            }
            if (cmd.getType().equals("auth-login")) {
                try {
                    User _user = authService.login(cmd.getUser().getUserName(), cmd.getUser().getPassword());
                    System.out.println("login");

                    cmd = new Command();
                    cmd.setType("response");
                    cmd.setUser(_user);

                    objOutput.writeObject(cmd);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
}
