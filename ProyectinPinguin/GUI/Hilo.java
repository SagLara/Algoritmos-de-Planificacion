package GUI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase donde se recrea el movimiento del juego. A partir de la sobreescrtura
 * de run() se establece el movimiento de los elementos dibujables del juego.
 *
 * @author cardenasmh
 */
public class Hilo extends Thread {

    /**
     * Constructor del Hilo.
     */
    public Hilo() {

    }

    @Override
    public void run() {
        while (true) {
            try {
                super.run();
                VentanaProcesosRR.getPanelmenu().Simulacion(VentanaProcesosRR.getPaneldiagrama().getDiagrama());
                Hilo.sleep(30);
            } catch (InterruptedException ex) {
                System.out.println("Ocurrio algo inesperado :(");
            }
        }

    }
}
