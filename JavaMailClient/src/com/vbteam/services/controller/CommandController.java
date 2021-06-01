/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.services.controller;

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
    
    private static FrmAuth authFrame;

    public static void Handler(ObjectInputStream objInputStream, ObjectOutputStream objOutputStream, Command command, JFrame frame) {
        System.out.println("handler");
        authFrame = (FrmAuth)frame;
        if (command.getType().indexOf("response") == 0) {
            System.out.println("response");
            Auth(command, frame);
        }
        if (command.getType().indexOf("mail") == 0) {
            System.out.println("mail box");
            Mail(command, frame);
        }
        if(command.getType().indexOf("manager")== 0){
            System.out.println("manager");
            Manager(command,frame);
        }
    }

    private static void Auth(Command command, JFrame frame) {
        try {
            if (command.getType().equals("response-login")) {
                authFrame.uihandler.loginComplete(command.getUser());
            }
            if (command.getType().equals("response-register")) {
                authFrame.uihandler.registerComplete(command.getUser());
            }
            if (command.getType().equals("response-exist")) {
                authFrame.uihandler.userExist(command);
            }
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
    
    private static void Manager(Command command,JFrame frame){
        try{
            if(command.getType().equals("manager-listuser")){
                authFrame.uihandler.showManagementPanel(command);
            }
            if(command.getType().equals("manager-adduser")){
                if(command.getBoolResponse()){
                    authFrame.uihandler.popupMessage("confirm", "Kullanıcı başarıyla eklendi.");
                }else{
                    authFrame.uihandler.popupMessage("error", "Kullanıcı eklenemedi.");
                }                
            }
            if(command.getType().equals("manager-deleteuser")){
                if(command.getBoolResponse()){
                    authFrame.uihandler.popupMessage("confirm", "Kullanıcı başarıyla silindi.");
                }else{
                    authFrame.uihandler.popupMessage("error", "Kullanıcı silinemedi.");
                }
            }
            if(command.getType().equals("manager-updateuser")){
                if(command.getBoolResponse()){
                    authFrame.uihandler.popupMessage("confirm", "Kullanıcı başarıyla düzenlendi.");
                }else{
                    authFrame.uihandler.popupMessage("error", "Kullanıcı düzenlenemedi.");
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private static void Mail(Command command, JFrame frame) {
        try {
            if (command.getType().equals("mail-send-response")) {
                authFrame.uihandler.mailSentResponse(command.getBoolResponse());
            }if (command.getType().equals("mail-box-response")) {
                authFrame.uihandler.setMailResponse(command);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
