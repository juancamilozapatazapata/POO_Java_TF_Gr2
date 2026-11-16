/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Juan Camilo Zapata
 * @version 1 - 16 nov. 2026
 */
/**
 * Ventana principal y menú de coordinación del proyecto.
 *
 * <p>Esta primera entrega define la navegación para las 16 operaciones del
 * diagrama. La lógica de negocio se conectará cuando estén integrados los
 * módulos de modelo y gestión.</p>
 */
public class UsaProyecto extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final Color COLOR_FONDO = new Color(244, 247, 251);
    private static final Color COLOR_AZUL = new Color(28, 49, 79);
    private static final Color COLOR_TEXTO = new Color(43, 55, 72);
    private static final Color COLOR_SECUNDARIO = new Color(102, 117, 137);

    private static final String[] CATEGORIAS = {
        "Proyectos", "Consultas", "Equipo", "Archivos"
    };

    private static final String[][] OPCIONES = {
        {
            "Registrar un proyecto",
            "Consultar todos los proyectos",
            "Consultar los proyectos en curso",
            "Consultar el primer proyecto registrado",
            "Consultar el último proyecto registrado"
        },
        {
            "Consultar el gerente de un proyecto",
            "Consultar el equipo de un proyecto",
            "Consultar los proyectos de un gerente",
            "Consultar los proyectos de un cliente"
        },
        {
            "Registrar un integrante a un proyecto",
            "Registrar el equipo a un proyecto",
            "Eliminar un integrante de un proyecto"
        },
        {
            "Generar archivo de todos los proyectos",
            "Generar archivo de todos los clientes",
            "Leer archivo de proyectos",
            "Leer archivo de clientes"
        }
    };

    public UsaProyecto() {
        super("Gestión de proyectos | POO Java TF Gr2");
        configurarVentana();
        setJMenuBar(crearMenu());
        setContentPane(crearContenido());
    }

    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(820, 600));
        setSize(1080, 760);
        setLocationRelativeTo(null);
    }

    private JMenuBar crearMenu() {
        JMenuBar barra = new JMenuBar();

        for (int categoria = 0; categoria < CATEGORIAS.length; categoria++) {
            JMenu menu = new JMenu(CATEGORIAS[categoria]);
            final int indiceCategoria = categoria;

            for (int indiceOpcion = 0; indiceOpcion < OPCIONES[categoria].length; indiceOpcion++) {
                final int numeroOpcion = numeroDeOpcion(indiceCategoria, indiceOpcion);
                String descripcion = OPCIONES[categoria][indiceOpcion];
                JMenuItem elemento = new JMenuItem(descripcion);
                elemento.addActionListener((ActionEvent evento) ->
                        mostrarPendiente(numeroOpcion, descripcion));
                menu.add(elemento);
            }

            barra.add(menu);
        }

        return barra;
    }

    private JPanel crearContenido() {
        JPanel contenido = new JPanel(new BorderLayout(0, 22));
        contenido.setBackground(COLOR_FONDO);
        contenido.setBorder(new EmptyBorder(24, 28, 20, 28));
        contenido.add(crearEncabezado(), BorderLayout.NORTH);

        JPanel tarjetas = new JPanel(new GridLayout(2, 2, 16, 16));
        tarjetas.setOpaque(false);
        for (int i = 0; i < CATEGORIAS.length; i++) {
            tarjetas.add(crearTarjeta(i));
        }
        contenido.add(tarjetas, BorderLayout.CENTER);

        JLabel pie = new JLabel(
                "Interfaz coordinada · 16 opciones · lógica de negocio pendiente de integración",
                SwingConstants.CENTER);
        pie.setForeground(COLOR_SECUNDARIO);
        pie.setFont(new Font("SansSerif", Font.PLAIN, 12));
        contenido.add(pie, BorderLayout.SOUTH);

        return contenido;
    }

    private JPanel crearEncabezado() {
        JPanel encabezado = new JPanel();
        encabezado.setLayout(new BoxLayout(encabezado, BoxLayout.Y_AXIS));
        encabezado.setBackground(COLOR_AZUL);
        encabezado.setBorder(new EmptyBorder(23, 26, 23, 26));

        JLabel etiqueta = new JLabel("COORDINACIÓN E INTERFAZ");
        etiqueta.setForeground(new Color(184, 207, 235));
        etiqueta.setFont(new Font("SansSerif", Font.BOLD, 12));

        JLabel titulo = new JLabel("Gestión de proyectos");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 27));
        titulo.setBorder(new EmptyBorder(7, 0, 3, 0));

        JLabel subtitulo = new JLabel("POO Java TF Gr2 · Panel de operaciones");
        subtitulo.setForeground(new Color(222, 230, 240));
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));

        encabezado.add(etiqueta);
        encabezado.add(titulo);
        encabezado.add(subtitulo);
        return encabezado;
    }

    private JPanel crearTarjeta(int indiceCategoria) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 228, 236)),
                new EmptyBorder(15, 17, 15, 17)));

        JLabel titulo = new JLabel(CATEGORIAS[indiceCategoria]);
        titulo.setForeground(COLOR_TEXTO);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 17));
        titulo.setAlignmentX(LEFT_ALIGNMENT);
        tarjeta.add(titulo);
        tarjeta.add(Box.createVerticalStrut(9));

        for (int i = 0; i < OPCIONES[indiceCategoria].length; i++) {
            final int numeroOpcion = numeroDeOpcion(indiceCategoria, i);
            String descripcion = OPCIONES[indiceCategoria][i];
            JButton boton = new JButton(numeroOpcion + ".  " + descripcion);
            boton.setFont(new Font("SansSerif", Font.PLAIN, 13));
            boton.setForeground(COLOR_TEXTO);
            boton.setBackground(Color.WHITE);
            boton.setHorizontalAlignment(JButton.LEFT);
            boton.setAlignmentX(LEFT_ALIGNMENT);
            boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
            boton.setFocusPainted(false);
            boton.addActionListener((ActionEvent evento) ->
                    mostrarPendiente(numeroOpcion, descripcion));
            tarjeta.add(boton);
            if (i < OPCIONES[indiceCategoria].length - 1) {
                tarjeta.add(Box.createVerticalStrut(4));
            }
        }

        return tarjeta;
    }

    private int numeroDeOpcion(int indiceCategoria, int indiceOpcion) {
        int numero = indiceOpcion + 1;
        for (int i = 0; i < indiceCategoria; i++) {
            numero += OPCIONES[i].length;
        }
        return numero;
    }

    private void mostrarPendiente(int numeroOpcion, String descripcion) {
        JOptionPane.showMessageDialog(
                this,
                "La opción " + numeroOpcion + " («" + descripcion + "») está disponible en la interfaz.\n\n"
                + "Su lógica se conectará al integrar los módulos de gestión del proyecto.",
                "Opción preparada para integración",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception excepcion) {
            // Si el aspecto del sistema no está disponible, se usa el predeterminado de Swing.
        }

        SwingUtilities.invokeLater(() -> new UsaProyecto().setVisible(true));
    }
}
