package GUIProcesosSO;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MiRender extends DefaultTableCellRenderer {
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if ("--".equals((String) value)) {
			this.setOpaque(true);
			this.setBackground(Color.RED);
			this.setForeground(Color.RED);
		} else if ("XX".equals((String) value)) {
			this.setBackground(Color.GREEN);
			this.setForeground(Color.GREEN);
		} else if ("||".equals((String) value)) {
			this.setBackground(Color.MAGENTA);
			this.setForeground(Color.MAGENTA);
		} else {
			this.setBackground(Color.WHITE);
			this.setForeground(Color.BLACK);
		}
		return this;
	}

}
//class Tarea extends Thread {
//	public Tarea(String str) {
//	super(str);
//	}
//	
//	public void run() {
//		for(int i=0;i<10;i++) {
//			System.out.println(i+"\t GUAUUUUUUUUUUUUUUUU");
//		}
//	}
//	
//}


