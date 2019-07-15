package GUI;

import java.awt.*;
import javax.swing.*;

public class VentanaProcesos extends JFrame {

    private PanelTabla paneltabla;
    private PanelMenu panelmenu;
    private PanelDiagrama paneldiagrama;

    public VentanaProcesos() {
        setResizable(false);
        setTitle("Procesos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        getContentPane().setLayout(null);

        paneltabla = new PanelTabla();
        paneldiagrama = new PanelDiagrama();
        panelmenu = new PanelMenu(paneltabla.getTabla(), paneldiagrama.getDiagrama());
        panelmenu.setFont(new Font("Gulim", Font.PLAIN, 12));

        getContentPane().add(paneltabla);
        getContentPane().add(panelmenu);
        getContentPane().add(paneldiagrama);
    }
}
