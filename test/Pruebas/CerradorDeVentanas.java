package Pruebas;

import java.awt.Window;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JDialog;

/**
 * Clase de apoyo para las pruebas.
 *
 * Los métodos de UsaProyecto muestran ventanas de mensaje (JOptionPane) que esperan un clic.
 * En una prueba automática nadie puede hacer ese clic, así que esta clase corre en segundo plano,
 * cierra cada ventana de mensaje que aparece y guarda su título.
 * Con esos títulos las pruebas pueden verificar que el mensaje correcto fue mostrado.
 */
public class CerradorDeVentanas {

    private final List<String> titulos = Collections.synchronizedList(new ArrayList<String>());
    private volatile boolean activo = false;
    private Thread hilo;

    /** Empieza a cerrar las ventanas de mensaje que vayan apareciendo. */
    public void iniciar() {
        activo = true;
        hilo = new Thread(() -> {
            while (activo) {
                for (Window ventana : Window.getWindows()) {
                    if (ventana instanceof JDialog && ventana.isVisible() && ((JDialog) ventana).isModal()) {
                        titulos.add(((JDialog) ventana).getTitle());
                        ventana.dispose();
                    }
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                    return;
                }
            }
        });
        hilo.setDaemon(true);
        hilo.start();
    }

    /** Deja de cerrar ventanas. */
    public void detener() {
        activo = false;
        if (hilo != null) {
            hilo.interrupt();
        }
    }

    /** Indica si apareció al menos una ventana con ese título. */
    public boolean seMostro(String titulo) {
        return titulos.contains(titulo);
    }

    /** Cantidad de ventanas de mensaje que aparecieron. */
    public int cantidadMostradas() {
        return titulos.size();
    }
}
