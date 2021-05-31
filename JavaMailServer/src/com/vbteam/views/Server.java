/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.views;

import com.vbteam.utils.ClientListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author schea
 */
public class Server {

    private int port = 1443;
    private String host = "127.0.0.1";
    private ServerSocket server;
    private Socket client;
    private ObjectOutputStream objOutStream;
    private ObjectInputStream objInStream;

    public void Connect() {
        try {
            server = new ServerSocket(port);
            while (true) {
                System.out.println("Server Açık");
                client = server.accept();
                OutputStream outputStream = client.getOutputStream();
                objOutStream = new ObjectOutputStream(outputStream);
                InputStream inputStream = client.getInputStream();
                objInStream = new ObjectInputStream(inputStream);
                System.out.println("Client Server'a Başarıyla bağlandı!");
                new Thread(new ClientListener(objInStream, objOutStream)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
