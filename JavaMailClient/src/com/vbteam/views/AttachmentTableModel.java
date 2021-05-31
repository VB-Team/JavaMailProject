/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.views;

import com.vbteam.models.Mail;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ubuntu
 */
public class AttachmentTableModel {

    private String[] columnNames = {"Dosya Adı", "Dosya Tipi", "Dosya boyutu", "Tarih"};
    
    /*private List<Attachment> myList = new ArrayList();

    public AttachmentTableModel(List<Attachment> mailList) {
        myList = mailList;
    }
    
    
    public List<Mail> getList(){
        return myList;
    }
    
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
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
                return mail.getRecipientUser();
            case 1:
                return mail.getSubject();
            case 2:
                return mail.getBody();
            case 3:
                return mail.getCreateDate();
            case 4:
                return mail.getId();
            default:
                break;
        }
        return mail;
    }
    */
}
