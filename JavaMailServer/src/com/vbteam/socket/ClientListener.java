/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.socket;

import com.vbteam.models.Command;
import com.vbteam.utils.ConnectionPool;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author schea
 */
public class ClientListener implements Runnable {

    private ObjectInputStream objInStream;
    private ObjectOutputStream objOutStream;

    public ClientListener(ObjectInputStream objInStream, ObjectOutputStream objOutStream ) {
        this.objInStream = objInStream;
        this.objOutStream = objOutStream;
    }

    @Override
    public void run() {
        try {
            boolean whileControl = true;
            while (whileControl) {
                Command command = (Command) objInStream.readObject();
                CommandHandler.Handler(objInStream, objOutStream, command);
            }

        } catch (Exception e) {
            System.err.println("Client Listener Exception: " + e.getMessage());
        }
    }

}
