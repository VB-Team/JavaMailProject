/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author schea
 */
public class DbContext {
    static Connection con;
    static String fullurl = "jdbc:sqlserver://localhost:1433;databasename=MailServer;user=sa;password=6165";
    static String conurl = "jdbc:sqlserver://localhost:1433;databasename=MailServer";

    static String user = "sa";
    static String pass = "6165";

    public  Connection getConnection() {
        try {
            con = DriverManager.getConnection(conurl, user, pass);
            System.out.println("Bağlantı Kuruldu");            
        } catch (SQLException e) {
            //System.out.println("BAĞLANTI HATASI: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Hata: " + e.getMessage());
            //Bir Dialog Frame/Form üzerinde Bu hata kullanıcıya bildirilecek
        }
        return con;
    }
}
