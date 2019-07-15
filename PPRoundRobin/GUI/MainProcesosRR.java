package GUI;

import java.awt.EventQueue;

public class MainProcesosRR {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaProcesosRR frame = new VentanaProcesosRR();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
