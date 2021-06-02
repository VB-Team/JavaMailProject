/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.socket;


import com.vbteam.utils.BasicConnectionPool;
import com.vbteam.utils.ConnectionPool;
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
    public static ConnectionPool connectionPool;
    

    public void Connect() {
        try {
            connectionPool = BasicConnectionPool.create("jdbc:sqlserver://localhost:1453;databasename=MailServer", "sa", "Password1!");
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
            System.err.println(e.getMessage());
        }
    }
    
    
    
    public static void main(String[] args) {
        Server server=new Server();
        server.Connect();
    }
}
