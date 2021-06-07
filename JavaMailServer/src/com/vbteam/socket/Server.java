/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.socket;


import com.vbteam.controller.logger.Logger;
import com.vbteam.controller.logger.ILogger;
import com.vbteam.models.Log;
import com.vbteam.utils.DbContext;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import com.vbteam.utils.IConnectionPool;

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
    public static IConnectionPool connectionPool;
    private ILogger logger;
    

    public void Connect() {
        try {
            connectionPool = DbContext.createConnections();
            server = new ServerSocket(port);
            while (true) {
                
                client = server.accept();
                OutputStream outputStream = client.getOutputStream();
                objOutStream = new ObjectOutputStream(outputStream);
                InputStream inputStream = client.getInputStream();
                objInStream = new ObjectInputStream(inputStream);
                System.out.println("Bağlanan Client "+client.getChannel());
                new Thread(new ClientListener(objInStream, objOutStream)).start();
            }
        } catch (Exception ex) {
            logger=Logger.getInstance();
            logger.addLog(new Log(new java.sql.Timestamp(new java.util.Date().getTime()), "Exception", "Server Connect exception : " + ex.getMessage()));
            System.err.println("Socket Exception : "+ex.getMessage());
        }
    }
    
    
    
    public static void main(String[] args) {
        Server server=new Server();
        server.Connect();
    }
}
