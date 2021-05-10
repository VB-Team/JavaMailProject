/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.views;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author BatuPC
 */
public class TableRenderer extends DefaultTableCellRenderer {

    public TableRenderer() {
        setHorizontalAlignment(CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column
    ) {
        Component c = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column
        );

        c.setBackground(new Color(20,20,22));
        c.setForeground(Color.WHITE);

        return c;
    }
}
