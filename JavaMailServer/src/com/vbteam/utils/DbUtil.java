/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.utils;

import com.vbteam.controller.logger.ILogger;
import com.vbteam.controller.logger.Logger;
import com.vbteam.models.Log;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *
 * @author schea
 */
public class DbUtil {

    public String sunucu;
    public String port;
    public String database;
    public String user;
    public String pass;
    public static String appDir = "";
    private ILogger logger;
    public DbUtil() {
        Config();
    }    
    public void Config() {
        Path currentRelativePath = Paths.get("");
        appDir = currentRelativePath.toAbsolutePath().getParent().toString();
        String path = appDir;
        File f = new File(path + "\\Config.txt");

        if (!f.exists()) {
            System.out.println("Dosya Bulunamadı");
            return;
        }

        try {
            Scanner sc = new Scanner(f);
            String line;

            line = sc.nextLine();
            if (line != null && !line.equals("")) {
                sunucu = parsString(line, "=")[1].trim();
            } else {
                sunucu = "localhost";
            }

            line = sc.nextLine();
            if (line != null && !line.equals("")) {
                port = parsString(line, "=")[1].trim();
            } else {
                port = "1433";
            }

            line = sc.nextLine();
            if (line != null && !line.equals("")) {
                database = parsString(line, "=")[1].trim();
            } else {
                database = "MailServer";
            }

            line = sc.nextLine();
            if (line != null && !line.equals("")) {
                user = parsString(line, "=")[1].trim();
            } else {
                user = "sa";
            }
            line = sc.nextLine();
            if (line != null && !line.equals("")) {
                pass = parsString(line, "=")[1].trim();
            } else {
                pass = "";
            }        
        } catch (IOException ex) {
            logger=Logger.getInstance();
            logger.addLog(new Log(new java.sql.Timestamp(new java.util.Date().getTime()), "Exception", "Server Config Exception : " + ex.getMessage()));
            System.err.println(ex.getMessage());
        }
    }

    private static String[] parsString(String line, String delimater) {
        return line.split(delimater);
    }
    public static void main(String[] args) {
        DbUtil u=new DbUtil();
        u.Config();
    }
}
