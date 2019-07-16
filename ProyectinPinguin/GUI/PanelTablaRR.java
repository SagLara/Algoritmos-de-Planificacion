package GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class PanelTablaRR extends JPanel {

    private JTable tabla;
    private JScrollPane scrollPane;

    public PanelTablaRR() {
        this.setLayout(new GridLayout(1, 1));
        this.setBorder(new TitledBorder("Tabla"));
        this.setBounds(205, 5, 580, 340);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 110, 599, 312);

        DefaultTableModel modelo = new DefaultTableModel(new Object[][]{},
                new String[]{"Proceso", "T. Llegada", "T. Rafaga", "T. Comienzo", "T. Final",
                    "T. Retorno", "T. Espera"});
        tabla = new JTable(modelo);
        scrollPane.setViewportView(tabla);

        this.add(scrollPane);

    }

    public JTable getTabla() {
        return tabla;
    }

    public void setTabla(JTable tabla) {
        this.tabla = tabla;
    }

}
