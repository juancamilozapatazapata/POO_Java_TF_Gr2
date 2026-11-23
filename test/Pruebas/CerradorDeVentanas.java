package Pruebas;

import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * Clase de apoyo para las pruebas.
 *
 * Los métodos de UsaProyecto muestran ventanas (JOptionPane) que esperan un clic o un dato escrito.
 * En una prueba automática nadie puede hacerlo, así que esta clase corre en segundo plano y atiende
 * cada ventana que aparece:
 *  - ventana de mensaje: la cierra y guarda su título, para que la prueba verifique que fue mostrada;
 *  - ventana que pide un dato: escribe la siguiente respuesta preparada con agregarEntradas(...);
 *    si no quedan respuestas, la cancela (como si el usuario presionara Cancelar).
 */
public class CerradorDeVentanas {

    private final List<String> titulos = Collections.synchronizedList(new ArrayList<String>());
    private final ConcurrentLinkedQueue<String> entradas = new ConcurrentLinkedQueue<>();
    private volatile boolean activo = false;
    private Thread hilo;

    /** Empieza a atender las ventanas que vayan apareciendo. */
    public void iniciar() {
        activo = true;
        hilo = new Thread(() -> {
            while (activo) {
                for (Window ventana : Window.getWindows()) {
                    if (ventana instanceof JDialog && ventana.isVisible() && ((JDialog) ventana).isModal()) {
                        atender((JDialog) ventana);
                    }
                }
                try {
                    Thread.sleep(3);
                } catch (InterruptedException ex) {
                    return;
                }
            }
        });
        hilo.setDaemon(true);
        hilo.start();
    }

    private void atender(JDialog ventana) {
        titulos.add(ventana.getTitle());
        JOptionPane panel = buscarPanel(ventana.getContentPane());
        if (panel != null && panel.getWantsInput()) {
            String respuesta = entradas.poll();
            if (respuesta == null) {
                panel.setValue(JOptionPane.CANCEL_OPTION);   // sin respuesta preparada: se cancela
            } else {
                panel.setInputValue(respuesta);
                panel.setValue(JOptionPane.OK_OPTION);
            }
        } else {
            ventana.dispose();
        }
    }

    private JOptionPane buscarPanel(Container contenedor) {
        for (Component componente : contenedor.getComponents()) {
            if (componente instanceof JOptionPane) {
                return (JOptionPane) componente;
            }
            if (componente instanceof Container) {
                JOptionPane encontrado = buscarPanel((Container) componente);
                if (encontrado != null) {
                    return encontrado;
                }
            }
        }
        return null;
    }

    /** Prepara, en orden, las respuestas para las próximas ventanas que pidan un dato. */
    public void agregarEntradas(String... respuestas) {
        for (String respuesta : respuestas) {
            entradas.add(respuesta);
        }
    }

    /** Cantidad de respuestas preparadas que todavía no se usaron. */
    public int entradasPendientes() {
        return entradas.size();
    }

    /** Deja de atender ventanas y descarta las respuestas que sobraron. */
    public void detener() {
        activo = false;
        entradas.clear();
        if (hilo != null) {
            hilo.interrupt();
        }
    }

    /** Indica si apareció al menos una ventana con ese título. */
    public boolean seMostro(String titulo) {
        return titulos.contains(titulo);
    }

    /** Cantidad de ventanas que aparecieron. */
    public int cantidadMostradas() {
        return titulos.size();
    }
}
