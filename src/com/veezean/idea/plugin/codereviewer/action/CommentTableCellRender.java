package com.veezean.idea.plugin.codereviewer.action;

import com.intellij.ui.Gray;
import com.intellij.ui.JBColor;
import com.veezean.idea.plugin.codereviewer.consts.Constants;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * 评审操作render逻辑处理类
 *
 * @author Veezean
 * @since 2019/10/1
 */
public class CommentTableCellRender extends DefaultTableCellRenderer {

    private int targetColumnIndex = 0;


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        String columnName = table.getModel().getColumnName(column);
        if (targetColumnIndex == 0 && "确认结果".equals(columnName)) {
            // 定位确认结果列坐标
            targetColumnIndex = column;
        }
        if (targetColumnIndex != 0) {
            int modelRow = table.convertRowIndexToModel(row);
            Object rowValue = table.getModel().getValueAt(modelRow, targetColumnIndex);

            if (!isSelected && rowValue != null) { // 如果不想覆盖选中颜色，可以加此判断
                String valueStr = rowValue.toString();
                comp.setBackground(Gray._232);
                if ("未确认".equals(valueStr)) {
                    comp.setForeground(Constants.COLOR_UNCONFIRMED);
                } else if ("已修改".equals(valueStr)) {
                    comp.setForeground(Constants.COLOR_EDITED);
                } else if ("待修改".equals(valueStr)) {
                    comp.setForeground(JBColor.BLUE);
                } else if ("拒绝".equals(valueStr)) {
                    comp.setForeground(JBColor.red);
                } else {
                    comp.setForeground(JBColor.BLUE);
                }
            }
        }

        // 5. 可选：保留原有的基于 cellEditable 的背景色提示
//        boolean cellEditable = table.isCellEditable(row, column);
//        if (cellEditable) {
//            comp.setBackground(JBColor.ORANGE);
//            comp.setForeground(JBColor.BLUE);
//        } else {
//            // 如果不是可编辑单元格，设置默认白色背景（注意不要与选中背景冲突）
//            if (!isSelected) {
//                comp.setBackground(JBColor.WHITE);
//                comp.setForeground(JBColor.BLACK);
//            }
//        }

        return comp;
    }
}
