/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.controller.socket;

import com.vbteam.models.Command;
import com.vbteam.models.User;

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
