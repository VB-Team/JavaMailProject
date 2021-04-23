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

/**
 *
 * @author BatuPC
 */
public class CommandController {

    public static void Handler(ObjectInputStream objInputStream, ObjectOutputStream objOutputStream, Command command) {
        if (command.getType().equals("auth-echo")) {
            System.out.println("Echo : " + command.getReturnValue());
        }
        if (command.getType().equals("auth-register") && command.getType().equals("auth-login")) {
            Auth(command, objOutputStream, objInputStream);
        }
    }

    public static Boolean Auth(Command command, ObjectOutputStream objOutputStream, ObjectInputStream objInputStream) {
        try {
            objOutputStream.writeObject(command);
            command = (Command) objInputStream.readObject();
            Handler(objInputStream, objOutputStream, command);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return true;

    }
}
