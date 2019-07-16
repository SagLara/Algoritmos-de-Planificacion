package GUIProcesosSO;

import java.awt.EventQueue;

public class MainProcesos {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaProcesos frame = new VentanaProcesos();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
