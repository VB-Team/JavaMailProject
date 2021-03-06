/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.socket;

import com.vbteam.controller.UserManagement.IUserManagementController;
import com.vbteam.controller.logger.Logger;
import com.vbteam.models.Command;
import com.vbteam.models.Log;
import com.vbteam.models.User;
import com.vbteam.controller.authenticate.AuthController;
import com.vbteam.controller.mail.MailController;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import com.vbteam.models.Mail;
import com.vbteam.controller.UserManagement.UserManagementController;
import com.vbteam.controller.authenticate.IAuthController;
import com.vbteam.controller.logger.ILogger;

/**
 *
 * @author schea
 */
public class CommandHandler {

    private static IAuthController authService = AuthController.getInstance();
    private static MailController mailService = MailController.getInstance();
    private static IUserManagementController managerService = UserManagementController.getInstance();
    private static ILogger logger=Logger.getInstance();

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
            switch (cmd.getType()) {
                case "mail-send":
                    boolean isSent = mailService.addMail(cmd.getMail());
                    cmd = new Command();
                    cmd.setType("mail-send-response");
                    cmd.setBoolResponse(isSent);
                    objOutput.writeObject(cmd);
                    break;
                case "mail-add-draft":

                    mailService.addDraftMail(cmd.getMail());
                    cmd = new Command();
                    cmd.setType("mail-adddraft-response");
                    cmd.setBoolResponse(true);
                    objOutput.writeObject(cmd);
                    break;
                case "mail-delete":
                    mailService.deleteMail(cmd.getMail().getId());
                    cmd = new Command();
                    cmd.setType("mail-delete-response");
                    cmd.setBoolResponse(true);
                    objOutput.writeObject(cmd);
                    break;
                case "mail-income":
                    int userId = cmd.getUser().getId();
                    cmd = new Command();
                    cmd.setType("mail-box-response");
                    cmd.setCommandText("income");
                    List<Mail> incomeMails = mailService.getIncomingMails(userId);
                    for (Mail mail : incomeMails) {
                        mail.setAttachments(mailService.getMailAttachments(mail.getId()));
                        mail.setHeaders(mailService.getMailHeaders(mail.getId()));
                    }
                    cmd.setObject(incomeMails);
                    objOutput.writeObject(cmd);
                    break;
                case "mail-outgoing":
                    int outId = cmd.getUser().getId();
                    cmd = new Command();
                    cmd.setType("mail-box-response");
                    cmd.setCommandText("outgoing");
                    List<Mail> outgoingMails = mailService.getOutgoingMails(outId);
                    for (Mail mail : outgoingMails) {
                        mail.setAttachments(mailService.getMailAttachments(mail.getId()));
                        mail.setHeaders(mailService.getMailHeaders(mail.getId()));
                    }
                    cmd.setObject(outgoingMails);

                    objOutput.writeObject(cmd);
                    break;
                case "mail-draft":
                    int draftId = cmd.getUser().getId();
                    cmd = new Command();
                    cmd.setType("mail-box-response");
                    cmd.setCommandText("draft");
                    List<Mail> mails = mailService.getAnyMails(draftId, "Draft");
                    for (Mail mail : mails) {
                        mail.setAttachments(mailService.getMailAttachments(mail.getId()));
                        mail.setHeaders(mailService.getMailHeaders(mail.getId()));
                    }
                    cmd.setObject(mails);

                    objOutput.writeObject(cmd);
                    break;
                case "mail-trash":
                    int deleteId = cmd.getUser().getId();
                    cmd = new Command();
                    cmd.setType("mail-box-response");
                    cmd.setCommandText("trash");
                    List<Mail> trashMails = mailService.getAnyMails(deleteId, "Deleted");
                    for (Mail mail : trashMails) {
                        mail.setAttachments(mailService.getMailAttachments(mail.getId()));
                        mail.setHeaders(mailService.getMailHeaders(mail.getId()));
                    }
                    cmd.setObject(trashMails);

                    objOutput.writeObject(cmd);
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            logger = Logger.getInstance();
            logger.addLog(new Log(new java.sql.Timestamp(new java.util.Date().getTime()), "Exception", "Command Handler Mail exception : " + ex.getMessage()));
            ex.printStackTrace();
        }
    }

    private static void Auth(ObjectInputStream objInput, ObjectOutputStream objOutput, Command cmd) {
        try {
            switch (cmd.getType().toString()) {
                case "auth-exist":
                    boolean userExistState = authService.userExist(cmd.getUser().getUserName());

                    cmd = new Command();
                    cmd.setType("response-exist");
                    cmd.setBoolResponse(userExistState);
                    objOutput.writeObject(cmd);
                    break;
                case "auth-register":
                    User _registerUser = authService.register(cmd.getUser());
                    cmd = new Command();
                    cmd.setType("response-register");
                    cmd.setUser(_registerUser);
                    objOutput.writeObject(cmd);
                    break;
                case "auth-login":
                    try {
                    User _loginUser = authService.login(cmd.getUser().getUserName(), cmd.getUser().getPassword());
                    cmd = new Command();
                    cmd.setType("response-login");
                    cmd.setUser(_loginUser);

                    objOutput.writeObject(cmd);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                break;

                default:
                    break;
            }
        } catch (Exception ex) {
            logger = Logger.getInstance();
            logger.addLog(new Log(new java.sql.Timestamp(new java.util.Date().getTime()), "Exception", "Command Handler Auth exception : " + ex.getMessage()));
            ex.printStackTrace();
        }
    }

    private static void Manager(ObjectInputStream objInput, ObjectOutputStream objOutput, Command cmd) {
        try {
            switch (cmd.getType().toString()) {
                case "manager-adduser":
                    User addUser = managerService.addUser(cmd.getUser());
                    cmd = new Command();
                    cmd.setType("manager-adduser");
                    if (addUser == null) {
                        cmd.setBoolResponse(false);
                    } else {
                        cmd.setBoolResponse(true);
                        cmd.setUser(addUser);
                    }
                    objOutput.writeObject(cmd);
                    break;
                case "manager-deleteuser":
                    boolean bool = managerService.deletedUser(cmd.getUser().getId());
                    cmd = new Command();
                    cmd.setType("manager-deleteuser");
                    cmd.setBoolResponse(bool);
                    objOutput.writeObject(cmd);
                    break;
                case "manager-updateuser":
                    try {
                    User user = managerService.updateUser(cmd.getUser());
                    cmd = new Command();
                    cmd.setType("manager-updateuser");
                    if (user == null) {
                        cmd.setBoolResponse(false);
                    } else {
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
                    cmd.setType("manager-listuser");
                    if (users == null) {
                        cmd.setBoolResponse(false);
                    } else {
                        cmd.setBoolResponse(true);
                        cmd.setObject(users);
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
                    cmd.setType("manager-income");
                    objOutput.writeObject(cmd);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                break;
                case "manager-outgoing":
                    try {
                    int mailCount = managerService.IncomingMailCount(cmd.getUser().getId());
                    cmd = new Command();
                    cmd.setType("manager-outgoing");
                    objOutput.writeObject(cmd);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                break;
                default:
                    break;
            }
        } catch (Exception ex) {
            logger = Logger.getInstance();
            logger.addLog(new Log(new java.sql.Timestamp(new java.util.Date().getTime()), "Exception", "Command Handler Manager exception : " + ex.getMessage()));
            ex.printStackTrace();
        }
    }
}
