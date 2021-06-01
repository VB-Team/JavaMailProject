/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.views;

import com.vbteam.models.Command;
import com.vbteam.models.User;

/**
 *
 * @author ubuntu
 */
public class UIHandler {

    private FrmAuth authFrame;
    private FrmDashboard dashboardFrame;
    
    
    /**
     * @return the authFrame
     */
    public FrmAuth getAuthFrame() {
        return authFrame;
    }

    /**
     * @param authFrame the authFrame to set
     */
    public void setAuthFrame(FrmAuth authFrame) {
        this.authFrame = authFrame;
    }

    /**
     * @return the dashboardFrame
     */
    public FrmDashboard getDashboardFrame() {
        return dashboardFrame;
    }

    /**
     * @param dashboardFrame the dashboardFrame to set
     */
    public void setDashboardFrame(FrmDashboard dashboardFrame) {
        this.dashboardFrame = dashboardFrame;
    }
    
    public void registerComplete(User _user){
        authFrame.registerCompleted(_user);
    }
    
    public void loginComplete(User _user){
        authFrame.loginCompleted(_user);
    }
    
    public void userExist(Command _command){
        authFrame.userExist(_command);
    }
    
    public void mailSentResponse(boolean bool){
        dashboardFrame.mailSent(bool);
    }
    
    public void setMailResponse(Command _command){
        dashboardFrame.setMails(_command);
    }
    
    public void showManagementPanel(Command _command){
        dashboardFrame.showManagementPanel(_command);
    }
    
    public void popupMessage(String type,String message){
        dashboardFrame.popupDialog(type, message, null);
    }
}
