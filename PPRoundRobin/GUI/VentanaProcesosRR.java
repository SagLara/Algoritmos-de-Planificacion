package GUI;

import java.awt.*;
import javax.swing.*;

public class VentanaProcesosRR extends JFrame {

    private PanelTablaRR paneltabla;
    private PanelMenuRR panelmenu;
    private PanelDiagramaRR paneldiagrama;

    public VentanaProcesosRR() {
        setResizable(false);
        setTitle("Procesos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        getContentPane().setLayout(null);

        paneltabla = new PanelTablaRR();
        paneldiagrama = new PanelDiagramaRR();
        panelmenu = new PanelMenuRR(paneltabla.getTabla(), paneldiagrama.getDiagrama());
        panelmenu.setFont(new Font("Gulim", Font.PLAIN, 12));

        getContentPane().add(paneltabla);
        getContentPane().add(panelmenu);
        getContentPane().add(paneldiagrama);
    }
}
