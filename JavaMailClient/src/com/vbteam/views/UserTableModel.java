/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.views;

import com.vbteam.models.User;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ubuntu
 */
public class UserTableModel extends AbstractTableModel{
    private String[] columnNames = {"Adı", "Soyadı","Kayıt Tarihi", "Rolü"};
    private List<User> myList = new ArrayList();

    public UserTableModel(List<User> userList) {
        myList = userList;
    }

    public List<User> getList() {
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
        User user = myList.get(row);
        switch (col) {
            case 0:
                return user.getFirstName();
            case 1:
                return user.getLastName();
            case 2:
                return user.getRegisterDate();
            case 3:
                return user.getRole();
            case 4:
                return user.getPassword();
            case 5:
                return user.getId();
            default:
                break;
        }
        return user;
    }
    
}
