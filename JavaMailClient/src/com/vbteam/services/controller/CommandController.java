/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.services.controller;

import com.vbteam.models.Command;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BatuPC
 */
public class CommandController {

    public static void Handler(ObjectInputStream objInputStream, ObjectOutputStream objOutputStream, Command command) {
        System.out.println("handler");
        if (command.getType().equals("auth-echo")) {
            //System.out.println("Echo : " + command.getReturnValue());
        }
        
    }

    private static void Auth(Command command, ObjectOutputStream objOutputStream, ObjectInputStream objInputStream) {
        try {
            command = (Command) objInputStream.readObject();
            command.getUser();
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        } 
    }

    
}
