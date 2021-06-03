/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.views.visualUtils;


import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import com.vbteam.models.Mail;

/**
 *
 * @author ubuntu
 */
public class MailTableModel extends AbstractTableModel {

    private String[] gonderencolumnNames = {"Gönderen", "Konu", "İçerik", "Tarih"};
    private String[] gidencolumnNames = {"Giden", "Konu", "İçerik", "Tarih"};
    private List<Mail> myList = new ArrayList();
    private String type = "";

    public MailTableModel(List<Mail> mailList, String _type) {
        myList = mailList;
        type = _type;
    }

    public String getType() {
        return type;
    }

    public List<Mail> getList() {
        return myList;
    }

    public String mailHeaderChooser(Mail mail) {
        String User = "";
        if(type.equals("outgoing")){
            System.out.println(type);
            User += mail.getHeaders().get(0).getRecipientUser();
            if (mail.getHeaders().size() > 1) {
                for (int i = 1; i < mail.getHeaders().size(); i++) {
                    User += " , " + mail.getHeaders().get(i).getRecipientUser();
                }
            }
        }else{            
            /*
            for (Header header : mail.getHeaders()) {
            recipientUser += header.getRecipientUser() + " , ";
            }
             */
            User += mail.getHeaders().get(0).getRecipientUser();
            if (mail.getHeaders().size() > 1) {
                for (int i = 1; i < mail.getHeaders().size(); i++) {
                    User += " , " + mail.getHeaders().get(i).getSenderUser();
                }
            }
        }        
        return User;
    }


    @Override
    public String getColumnName(int col) {
        if(type.equals("outgoing")){
            return gidencolumnNames[col];
        }else{
            return gonderencolumnNames[col];
        }      
    }

    @Override
    public int getColumnCount() {
        return gonderencolumnNames.length;
    }

    @Override
    public int getRowCount() {
        int size;
        if (myList == null) {
            size = 0;
        } else {
            size = myList.size();
        }
        return size;
    }

    @Override
    public Object getValueAt(int row, int col) {
        Mail mail = myList.get(row);
        switch (col) {
            case 0:
                String recipientUser = "";
                recipientUser = mailHeaderChooser(mail);
                return recipientUser;
            case 1:
                return mail.getSubject();
            case 2:
                return mail.getBody();
            case 3:
                return mail.getCreateDate();
            case 4:
                return mail.getId();
            case 5:
                return mail.getAttachments();
            default:
                break;
        }
        return mail;
    }
}
