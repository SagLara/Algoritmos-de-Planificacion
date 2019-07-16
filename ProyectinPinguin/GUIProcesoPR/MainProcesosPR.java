package GUIProcesoPR;

import java.awt.EventQueue;

public class MainProcesosPR {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaProcesosPR frame = new VentanaProcesosPR();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
