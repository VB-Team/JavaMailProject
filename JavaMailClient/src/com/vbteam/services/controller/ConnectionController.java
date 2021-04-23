/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.services.controller;

import com.vbteam.models.Command;
import com.vbteam.models.User;
import com.vbteam.services.socket.ConnectionService;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author BatuPC
 */
public class ConnectionController implements Runnable {

    private ObjectInputStream objInputStream;
    private ObjectOutputStream objOutputStream;
    private String authType;
    private Boolean isConnected;
    private ConnectionService conService;
    private User user;
    private Command command;

    public ConnectionController(String authType, ObjectInputStream objInputStream, ObjectOutputStream objOutputStream, ConnectionService conService, User user) {
        this.authType = authType;
        this.objInputStream = objInputStream;
        this.objOutputStream = objOutputStream;
        this.conService = conService;
        this.user = user;
    }

    @Override
    public void run() {
        try {            
            isConnected = CommandController.Auth(new Command(this.authType, user), objOutputStream, objInputStream);        
            if (isConnected) {
                while (true) {
                    command = new Command();
                    command = (Command) objInputStream.readObject();
                    CommandController.Handler(objInputStream, objOutputStream, command);
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getLocalizedMessage() + " " + ex.getMessage());
        }
    }

}
