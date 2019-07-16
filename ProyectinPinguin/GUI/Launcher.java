package GUI;

public class Launcher {

	public static VentanaProcesosRR ventana;
    public static Hilo hilo;
    
    Launcher(){
    	ventana = new VentanaProcesosRR();
        hilo = new Hilo();
        hilo.start();
        ventana.setVisible(true);
    }
    public static void pausarProceso() {
        hilo.suspend();
    }

    /**
     * Reanuda el juego. A partir de la reanudación del hilo.
     */
    public static void reanudarProceso() {
        hilo.resume();
    }
    
}
