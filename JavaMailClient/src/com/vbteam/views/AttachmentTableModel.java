/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.views;

import com.vbteam.models.Attachment;
import com.vbteam.models.Mail;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ubuntu
 */
public class AttachmentTableModel extends AbstractTableModel {

    private String[] columnNames = {"Dosya Adı", "Dosya Tipi", "Dosya boyutu"};

    private List<Attachment> myList = new ArrayList();

    public AttachmentTableModel(List<Attachment> attachmentList) {
        myList = attachmentList;
    }

    public List<Attachment> getList() {
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
        Attachment attachment = myList.get(row);
        switch (col) {
            case 0:
                return attachment.getAttachmentName();
            case 1:
                return attachment.getAttachmentType();
            case 2:
                return attachment.getAttachmentSize();
            case 3:
                return attachment.getAttachmentContent();
            case 4:
                return attachment.getId();
            default:
                break;
        }
        return attachment;
    }

}
