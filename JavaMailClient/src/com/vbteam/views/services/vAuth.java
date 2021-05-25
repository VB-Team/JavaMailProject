/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.views.services;

import com.vbteam.models.Command;
import com.vbteam.models.User;
import com.vbteam.views.FrmAuth;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.vbteam.utils.BCrypt;

/**
 *
 * @author BatuPC
 */
public class vAuth {

    public static boolean isUsernameExist(String username) {
        // Username gonna checked here to is exist
        boolean bool = true;
        try {
            Command sendCommand = new Command();
            User user = new User();

            user.setUserName(username);

            sendCommand.setType("auth-exist");
            sendCommand.setUser(user);

            FrmAuth.conService.SendCommand(sendCommand);

            sendCommand = (Command) FrmAuth.conService.getInputStream().readObject();
            bool = sendCommand.getBoolResponse();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return bool;
    }

    public static boolean passwordControl(String firstPw, String controlPw) {
        boolean returnValue = false;
        if (!firstPw.isEmpty()) {
            if (firstPw.equals(controlPw)) {
                if (isValidPassword(firstPw)) {
                    returnValue = true;
                } else {
                    FrmAuth.popupDialog("error", "Şifreniz şifre politikamıza uymamaktadır.");
                    returnValue = false;
                }
            } else {
                FrmAuth.popupDialog("error", "Şifreler uyuşmuyor.Lütfen kontrol edin");
                returnValue = false;
            }
        } else {
            FrmAuth.popupDialog("error", "Bir şifre giriniz");
            returnValue = false;
        }
        return returnValue;
    }

    public static boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[.@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(password);

        return m.matches();
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

}
