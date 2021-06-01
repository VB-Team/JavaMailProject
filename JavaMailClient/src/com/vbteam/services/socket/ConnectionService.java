/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.services.socket;

import com.vbteam.models.Command;
import com.vbteam.models.User;

import com.vbteam.services.controller.ConnectionController;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.swing.JFrame;


/**
 *
 * @author BatuPC
 */
public class ConnectionService {

    private String hostName;
    private Socket clientSocket;
    private int socketPort = 1443;
    private ObjectOutputStream objOutStream;
    private ObjectInputStream objInStream;
    private User user;
    private JFrame frameReference;



    public ConnectionService(String hostName,JFrame frame) {
        this.hostName = hostName;
        this.frameReference = frame;
    }

    public void connectServer() {
        try {
            clientSocket = new Socket(hostName, socketPort);
            OutputStream outputStream = clientSocket.getOutputStream();
            InputStream inputStream = clientSocket.getInputStream();

            objOutStream = new ObjectOutputStream(outputStream);
            objInStream = new ObjectInputStream(inputStream);
            
            new Thread(new ConnectionController(objInStream, objOutStream, this,frameReference)).start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void Auth(String authType) {
        try {
            if (clientSocket == null && clientSocket.isConnected() == false) {
                return;
            } else {
                //user = new User(userName, hashedPassword, 0, date, date, "BATU", "San");
                //new Thread(new ConnectionController(objInStream, objOutStream, this)).start();

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ObjectInputStream getInputStream() {
        return this.objInStream;
    }

    public ObjectOutputStream getOutputStream() {
        return this.objOutStream;
    }

    public void SendCommand(Command command) {
        try {
            objOutStream.writeObject(command);
            objOutStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public boolean isConnected() {
        boolean returnValue = false;
        if (clientSocket != null && clientSocket.isConnected()) {
            returnValue = true;
        } else {
            connectServer();
        }
        return returnValue;
    }

}
