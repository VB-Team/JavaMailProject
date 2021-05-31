/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.utils;

import com.vbteam.models.Command;
import com.vbteam.models.User;
import com.vbteam.services.authenticate.AuthService;
import com.vbteam.services.mail.MailService;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import com.vbteam.models.Mail;
import com.vbteam.services.UserManagement.UserManagementService;

/**
 *
 * @author schea
 */
public class CommandHandler {

    private static AuthService authService = new AuthService();
    private static MailService mailService = new MailService();
    private static UserManagementService managerService = new UserManagementService();

    private static List<Mail> emailList;

    public static void Handler(ObjectInputStream objInput, ObjectOutputStream objOutput, Command cmd) {
        if (cmd.getType().indexOf("auth") == 0) {
            Auth(objInput, objOutput, cmd);
        }
        if (cmd.getType().indexOf("mail") == 0) {
            Mail(objInput, objOutput, cmd);
        }
        if (cmd.getType().indexOf("manager") == 0) {
            Manager(objInput, objOutput, cmd);
        }
    }

    private static void Mail(ObjectInputStream objInput, ObjectOutputStream objOutput, Command cmd) {
        try {
            if (cmd.getType().equals("mail-send")) {
                emailList = new ArrayList<>();
                mailService.addMails(cmd.getMail());
            }
            if (cmd.getType().equals("mail-income")) {
                System.out.println("Income Mail Debug - ID : " + cmd.getUser().getId());
                int userId = cmd.getUser().getId();

                cmd = new Command();
                cmd.setType("mail-income");
                List<Mail> mails = mailService.getIncomingMails(userId);
                for (Mail mail : mails) {
                    mail.setAttachments(mailService.getMailAttachments(mail.getId()));
                    mail.setHeaders(mailService.getMailHeaders(mail.getId()));
                }
                cmd.setMailList(mails);

                objOutput.writeObject(cmd);
            }
            if (cmd.getType().equals("mail-outgoing")) {
                System.out.println("Outgoing Mail Debug - ID : " + cmd.getUser().getId());
                int userId = cmd.getUser().getId();

                cmd = new Command();
                cmd.setType("mail-outgoing");
                List<Mail> mails = mailService.getOutgoingMails(userId);
                for (Mail mail : mails) {
                    mail.setAttachments(mailService.getMailAttachments(mail.getId()));
                    mail.setHeaders(mailService.getMailHeaders(mail.getId()));
                }
                cmd.setMailList(mails);

                objOutput.writeObject(cmd);
            }

            if (cmd.getType().equals("mail-draft")) {
                System.out.println("Draft Mail Debug - ID : " + cmd.getUser().getId());
                int userId = cmd.getUser().getId();

                cmd = new Command();
                cmd.setType("mail-draft");
                List<Mail> mails = mailService.getAnyMails(userId, "Draft");
                for (Mail mail : mails) {
                    mail.setAttachments(mailService.getMailAttachments(mail.getId()));
                    mail.setHeaders(mailService.getMailHeaders(mail.getId()));
                }
                cmd.setMailList(mails);

                objOutput.writeObject(cmd);
            }

            if (cmd.getType().equals("mail-trash")) {
                System.out.println("Deleted Mail Debug - ID : " + cmd.getUser().getId());
                int userId = cmd.getUser().getId();

                cmd = new Command();
                cmd.setType("mail-trash");
                List<Mail> mails = mailService.getAnyMails(userId, "Deleted");
                for (Mail mail : mails) {
                    mail.setAttachments(mailService.getMailAttachments(mail.getId()));
                    mail.setHeaders(mailService.getMailHeaders(mail.getId()));
                }
                cmd.setMailList(mails);

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

    private static void Manager(ObjectInputStream objInput, ObjectOutputStream objOutput, Command cmd) {
        try {
            switch (cmd.getType().toString()) {
                case "manager-adduser":
                    User addUser = managerService.addUser(cmd.getUser());
                    cmd = new Command();
                    if (addUser == null) {
                        cmd.setType("response-error");
                        cmd.setBoolResponse(false);
                    } else {
                        cmd.setType("response-ok");
                        cmd.setBoolResponse(true);
                        cmd.setUser(addUser);
                    }
                    objOutput.writeObject(cmd);
                    break;
                case "manager-deleteuser":
                    boolean bool = managerService.deletedUser(cmd.getUser().getId());
                    cmd = new Command();
                    cmd.setType("response");
                    cmd.setBoolResponse(bool);
                    objOutput.writeObject(cmd);
                    break;
                case "manager-updateuser":
                    try {
                    User user = managerService.updateUser(cmd.getUser());
                    cmd = new Command();
                    if (user == null) {
                        cmd.setType("response-error");
                        cmd.setBoolResponse(false);
                    } else {
                        cmd.setType("response-ok");
                        cmd.setBoolResponse(true);
                        cmd.setUser(user);
                    }
                    objOutput.writeObject(cmd);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                break;
                case "manager-listuser":
                    try {
                    List<User> users;
                    users = managerService.listUser();
                    cmd = new Command();
                    if (users == null) {
                        cmd.setType("response-error");
                        cmd.setBoolResponse(false);
                    } else {
                        cmd.setType("response-ok");
                        cmd.setBoolResponse(true);
                    }
                    objOutput.writeObject(cmd);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                break;
                case "manager-income":
                    try {
                    int mailCount = managerService.IncomingMailCount(cmd.getUser().getId());
                    cmd = new Command();
                    cmd.setType("response-ok");
                    objOutput.writeObject(cmd);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                break;
                case "manager-outgoing":
                    try {
                    int mailCount = managerService.IncomingMailCount(cmd.getUser().getId());
                    cmd = new Command();
                    cmd.setType("response-ok");
                    objOutput.writeObject(cmd);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                break;
                default:
                    break;
            }
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
}
