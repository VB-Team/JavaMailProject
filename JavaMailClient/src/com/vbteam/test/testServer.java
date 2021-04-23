/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author BatuPC
 */
public class testServer {

    private int port;
    // User Array List Fieldı getirilecek
    private ServerSocket server;

    public testServer(int port) {
        this.port = port;
    }

    public void start() {
        try {
            server = new ServerSocket(port);

            System.out.println("[ TARIH ]" + "Port " + port + " aktif.");

            while (true) {
                Socket client = server.accept();
                System.out.println("Bağlantı Kuruldu INFO : "+client.getInetAddress());
                InputStream inputStream = client.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

                OutputStream outputStream = client.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        testServer tS = new testServer(1443);
        tS.start();
    }
}
