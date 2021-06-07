/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.controller.socket;

import com.vbteam.models.Command;
import com.vbteam.controller.socket.ConnectionService;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFrame;

/**
 *
 * @author BatuPC
 */
public class ConnectionController implements Runnable {

    private ObjectInputStream objInputStream;
    private ObjectOutputStream objOutputStream;
    private ConnectionService conService;
    private Command command;
    private CommandController controller;
    private JFrame frame;

    public ConnectionController(ObjectInputStream objInputStream, ObjectOutputStream objOutputStream, ConnectionService conService,JFrame frame) {
        this.objInputStream = objInputStream;
        this.objOutputStream = objOutputStream;
        this.conService = conService;
        this.frame = frame;
    }

    @Override
    public void run() {
        try {
            while (true) {
                command = new Command();
                
                controller = new CommandController();
                
                command = (Command) objInputStream.readObject();
                
                controller.Handler(objInputStream, objOutputStream, command,frame);
            }
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage() + " " + ex.getMessage());
        }
    }

}
