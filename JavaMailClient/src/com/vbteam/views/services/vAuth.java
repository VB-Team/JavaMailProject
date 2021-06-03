/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.views.services;

import com.vbteam.views.FrmAuth;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author BatuPC
 */
public class vAuth {


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
}
