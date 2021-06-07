/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.controller.socket;

import com.vbteam.models.Command;
import com.vbteam.views.FrmAuth;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFrame;

/**
 *
 * @author BatuPC
 */
public class CommandController {

    private FrmAuth authFrame;

    public void Handler(ObjectInputStream objInputStream, ObjectOutputStream objOutputStream, Command command, JFrame frame) {
        System.out.println(command.getType());
        authFrame = (FrmAuth) frame;
        if (command.getType().indexOf("response") == 0) {
            Auth(command, frame);
        }
        if (command.getType().indexOf("mail") == 0) {
            Mail(command, frame);
        }
        if (command.getType().indexOf("manager") == 0) {
            System.out.println("manager");
            Manager(command, frame);
        }
    }

    private void Auth(Command command, JFrame frame) {
        try {
            if (command.getType().equals("response-login")) {
                authFrame.uihandler.loginComplete(command.getUser());
            }
            if (command.getType().equals("response-register")) {
                authFrame.uihandler.registerComplete(command.getUser());
            }
            if (command.getType().equals("response-exist")) {
                if (!command.getBoolResponse()) {
                    authFrame.uihandler.userExist();
                } else {
                    authFrame.uihandler.popupMessage("error", "Kullanıcı ismi daha önce alınmış.");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void Manager(Command command, JFrame frame) {
        try {
            if (command.getType().equals("manager-listuser")) {
                authFrame.uihandler.showManagementPanel(command);
            }
            if (command.getType().equals("manager-adduser")) {
                if (command.getBoolResponse()) {
                    authFrame.uihandler.popupMessage("confirm", "Kullanıcı başarıyla eklendi.");
                } else {
                    authFrame.uihandler.popupMessage("error", "Kullanıcı eklenemedi.");
                }
            }
            if (command.getType().equals("manager-deleteuser")) {
                if (command.getBoolResponse()) {
                    authFrame.uihandler.popupMessage("confirm", "Kullanıcı başarıyla silindi.");
                } else {
                    authFrame.uihandler.popupMessage("error", "Kullanıcı silinemedi.");
                }
            }
            if (command.getType().equals("manager-updateuser")) {
                if (command.getBoolResponse()) {
                    authFrame.uihandler.popupMessage("confirm", "Kullanıcı başarıyla düzenlendi.");
                } else {
                    authFrame.uihandler.popupMessage("error", "Kullanıcı düzenlenemedi.");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void Mail(Command command, JFrame frame) {
        try {
            if (command.getType().equals("mail-send-response")) {
                if (command.getBoolResponse()) {
                    authFrame.uihandler.popupMessage("confirm", "Mail başarıyla gönderildi.");
                } else {
                    authFrame.uihandler.popupMessage("error", "Mail gönderilemedi.");
                }
            }
            if (command.getType().equals("mail-box-response")) {
                authFrame.uihandler.setMailResponse(command);
            }
            if (command.getType().equals("mail-delete-response")) {
                if (command.getBoolResponse()) {
                    authFrame.uihandler.popupMessage("confirm", "Mail başarıyla silindi.");
                } else {
                    authFrame.uihandler.popupMessage("error", "Mail silinemedi.");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
