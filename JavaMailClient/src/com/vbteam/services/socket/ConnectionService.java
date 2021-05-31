/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.services.socket;

import com.vbteam.models.Command;
import com.vbteam.models.User;
import com.vbteam.services.controller.ConnectionController;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

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

    public ConnectionService(String hostName) {
        this.hostName = hostName;
    }

    public void connectServer() {
        try {
            clientSocket = new Socket(hostName, socketPort);
            clientSocket.setTcpNoDelay(true);
            clientSocket.setSendBufferSize(1024);
            OutputStream outputStream = clientSocket.getOutputStream();
            InputStream inputStream = clientSocket.getInputStream();

            objOutStream = new ObjectOutputStream(new BufferedOutputStream(outputStream));
            objInStream = new ObjectInputStream(new BufferedInputStream(inputStream));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

    public void Auth(String authType) {
        try {
            if (clientSocket == null && clientSocket.isConnected() == false) {
                return;
            } else {
                Date date = new Date();
                //user = new User(userName, hashedPassword, 0, date, date, "BATU", "San");
                new Thread(new ConnectionController(authType, objInStream, objOutStream, this, user)).start();
            }
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
    
    public ObjectInputStream getInputStream(){
        return this.objInStream;
    }
    
    public ObjectOutputStream getOutputStream(){
        return this.objOutStream;
    }

    public void SendCommand(Command command) {
        try {
            objOutStream.writeObject(command);
            objOutStream.flush();
        }catch(Exception ex){
            System.out.println(ex.getLocalizedMessage());
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

    public static void main(String[] args) {
        try {
            ConnectionService conService = new ConnectionService("127.0.0.1");
            conService.connectServer();
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
}
