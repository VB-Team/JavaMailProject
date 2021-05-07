/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.views;

import com.vbteam.models.DraftMail;
import com.vbteam.models.SentMail;
import com.vbteam.services.mail.DraftMailService;
import com.vbteam.services.mail.SentMailService;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 *
 * @author schea
 */
public class ConsoleUI {

    public static void main(String args[]) {
       Server server=new Server();
       server.Connect();       
    }

}
