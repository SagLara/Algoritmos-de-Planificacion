package GUI;

import java.awt.*;
import javax.swing.*;

public class VentanaProcesosRR extends JFrame {

    private PanelTablaRR paneltabla;
    private static PanelMenuRR panelmenu;
    private static PanelDiagramaRR paneldiagrama;

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

	public PanelTablaRR getPaneltabla() {
		return paneltabla;
	}

	public void setPaneltabla(PanelTablaRR paneltabla) {
		this.paneltabla = paneltabla;
	}

	public static PanelMenuRR getPanelmenu() {
		return panelmenu;
	}

	public void setPanelmenu(PanelMenuRR panelmenu) {
		this.panelmenu = panelmenu;
	}

	public static PanelDiagramaRR getPaneldiagrama() {
		return paneldiagrama;
	}

	public void setPaneldiagrama(PanelDiagramaRR paneldiagrama) {
		this.paneldiagrama = paneldiagrama;
	}
    
    
}
