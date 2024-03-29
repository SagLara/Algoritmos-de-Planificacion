package GUI;

import java.awt.*;
import javax.swing.*;
import java.awt.GridLayout;

import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class PanelDiagrama extends JPanel {

    private JTable diagrama;
    private JScrollPane scrollPane2;

    public PanelDiagrama() {
        this.setLayout(new GridLayout(1, 1));
        this.setBorder(new TitledBorder("Diagrama"));
        this.setBounds(5, 400, 780, 300);

        scrollPane2 = new JScrollPane();
        scrollPane2.setBounds(10, 110, 599, 312);

        DefaultTableModel modelo = new DefaultTableModel();
        diagrama = new JTable(modelo);
        modelo.addColumn("Nom");
        scrollPane2.setViewportView(diagrama);
        this.add(scrollPane2);

    }

    public JTable getDiagrama() {
        return diagrama;
    }

    public void setDiagrama(JTable diagrama) {
        this.diagrama = diagrama;
    }

}
