/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.services.socket;

import com.vbteam.models.User;
import com.vbteam.services.controller.ConnectionController;
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

    private String hostName, userName, hashedPassword;
    private Socket clientSocket;
    private int socketPort;
    private ObjectOutputStream objOutStream;
    private ObjectInputStream objInStream;
    private User user;

    public ConnectionService(String hostName, String userName, String hashedPassword, int socketPort) {
        this.hostName = hostName;
        this.userName = userName;
        this.hashedPassword = hashedPassword;
        this.socketPort = socketPort;
    }

    public void connectServer() {
        try {
            clientSocket = new Socket(hostName, socketPort);
            OutputStream outputStream = clientSocket.getOutputStream();
            InputStream inputStream = clientSocket.getInputStream();

            objOutStream = new ObjectOutputStream(outputStream);
            objInStream = new ObjectInputStream(inputStream);
            System.out.println("Server connected");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void Auth(String authType) {
        try {
            if (clientSocket != null && clientSocket.isConnected() == false) {
                return;
            } else {
                Date date = new Date();
                user = new User(userName, hashedPassword, 0, date, date, "BATU", "San");
                new Thread(new ConnectionController(authType, objInStream, objOutStream, this, user)).start();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            ConnectionService conService = new ConnectionService("localhost", "batuhan", "test", 1443);
            conService.Auth("auth-login");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
}
