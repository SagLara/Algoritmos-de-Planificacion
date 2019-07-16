package GUIProcesoPR;

import java.awt.*;
import javax.swing.*;

public class VentanaProcesosPR extends JFrame {

    private PanelTablaPR paneltabla;
    private PanelMenuPR panelmenu;
    private PanelDiagramaPR paneldiagrama;

    public VentanaProcesosPR() {
        setResizable(false);
        setTitle("Procesos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        getContentPane().setLayout(null);

        paneltabla = new PanelTablaPR();
        paneldiagrama = new PanelDiagramaPR();
        panelmenu = new PanelMenuPR(paneltabla.getTabla(), paneldiagrama.getDiagrama());
        panelmenu.setFont(new Font("Gulim", Font.PLAIN, 12));

        getContentPane().add(paneltabla);
        getContentPane().add(panelmenu);
        getContentPane().add(paneldiagrama);
    }

	public PanelTablaPR getPaneltabla() {
		return paneltabla;
	}

	public void setPaneltabla(PanelTablaPR paneltabla) {
		this.paneltabla = paneltabla;
	}

	public PanelMenuPR getPanelmenu() {
		return panelmenu;
	}

	public void setPanelmenu(PanelMenuPR panelmenu) {
		this.panelmenu = panelmenu;
	}

	public PanelDiagramaPR getPaneldiagrama() {
		return paneldiagrama;
	}

	public void setPaneldiagrama(PanelDiagramaPR paneldiagrama) {
		this.paneldiagrama = paneldiagrama;
	}
    
    
    
}
