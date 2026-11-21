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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
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
import Modelo.Cliente;
import Modelo.Proyecto;
import Modelo.Empleado;
import java.awt.HeadlessException;
import java.io.IOException;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author Juan Camilo Zapata y Dylan Riascos
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
                        ejecutarOpcion(numeroOpcion, descripcion));
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
                    ejecutarOpcion(numeroOpcion, descripcion));
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

    /*
     * Colección de proyectos en memoria.
     */
    private static ArrayList<Proyecto> losProyectos = new ArrayList<>();

    /*
     * las demás siguen como "pendientes" hasta integrar los otros metodos.
     */
    private void ejecutarOpcion(int numeroOpcion, String descripcion) {
        switch (numeroOpcion) {
            case 7: {
                String codigo = JOptionPane.showInputDialog(
                        this, "Ingrese el codigo del proyecto:");

                if (codigo != null) {
                    String resultado = consultarEquipoProyectoDadoCodigo(
                        losProyectos, codigo);

                    JOptionPane.showMessageDialog(this, resultado);
                }
                break;
            }

            case 10: {
                String codigo = JOptionPane.showInputDialog(
                        this, "Ingrese el codigo del proyecto:");

                if (codigo != null) {
                    String resultado = registrarIntegranteProyectoDadoCodigo(
                            losProyectos, codigo);

                    JOptionPane.showMessageDialog(this, resultado);
                }
                break;
            }

            case 11: {
                String codigo = JOptionPane.showInputDialog(
                        this, "Ingrese el codigo del proyecto:");

                if (codigo != null) {
                    String resultado = registrarEquipoProyectoDadoCodigo(
                            losProyectos, codigo);

                    JOptionPane.showMessageDialog(this, resultado);
                }
                break;
            }

            case 12: {
                String codigo = JOptionPane.showInputDialog(
                        this, "Ingrese el codigo del proyecto:");

                if (codigo != null) {
                    String resultado = eliminarIntegranteProyectoDadoCodigo(
                            losProyectos, codigo);

                    JOptionPane.showMessageDialog(this, resultado);
                }
                break;
            }
            case 13: {
                generarArchivoDeObjetosProyectos(losProyectos);
                break;
            }
            case 14: {
                generarArchivoDeObjetosClientes(losProyectos);
                break;
            }
            case 15: {
                ArrayList<Proyecto> proyectosLeidos = leerArchivoDeObjetosProyectos();
                // Si el archivo no existe o falla la lectura, no se pierde lo que hay en memoria.
                if (!proyectosLeidos.isEmpty()) {
                    losProyectos = proyectosLeidos;
                }
                break;
            }
            case 16: {
                ArrayList<Cliente> clientesLeidos = leerArchivoDeObjetosClientes();
                if (!clientesLeidos.isEmpty()) {
                    String resultado = "CLIENTES LEIDOS DEL ARCHIVO\n";
                    for (Cliente cliente : clientesLeidos) {
                        resultado += cliente.toString() + "\n";
                    }
                    JOptionPane.showMessageDialog(this, resultado, "LECTURA DE ARCHIVO", JOptionPane.INFORMATION_MESSAGE);
                }
                break;
            }
            default:
                mostrarPendiente(numeroOpcion, descripcion);
                break;
        }
    }

    /*
     * Gestionar archivo de objetos
    */
        //Generar archivo de objetos de proyectos: almacenar la colección en el archivo
    public static void generarArchivoDeObjetosProyectos( ArrayList<Proyecto> listaPrincipal){
        if(listaPrincipal.isEmpty()){
            JOptionPane.showMessageDialog(null, "Lista vacía, no se puede generar el archivo ...", "GENERACIÓN DE ARCHIVO", JOptionPane.ERROR_MESSAGE);
        } else{
            String nombreArchivo = "datosProyectos.obj";
            ObjectOutputStream salida = null;
            boolean exito = true;
            try{
                salida = new ObjectOutputStream(new FileOutputStream(nombreArchivo));
                salida.writeObject(listaPrincipal);
            } catch(IOException ex){
                exito = false;
                JOptionPane.showMessageDialog(null, """
                                                    Operacion fallida: error almacenando datos en archivo.
                                                     Revisar serializacion de clases.""", "GENERACION DE ARCHIVO", JOptionPane.ERROR_MESSAGE);
            } finally{
                try{
                    if (salida != null) {
                        salida.close();
                        }
                    if(exito){
                        JOptionPane.showMessageDialog(null, "Operacion exitosa, archivo generado correctamente.", "GENERACION DE ARCHIVO", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch(HeadlessException | IOException ex){
                    JOptionPane.showMessageDialog(null,"Operacion fallida: error cerrando archivo.", "GENERACION DE ARCHIVO", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

        //Leer archivo de objetos de proyectos: recuperar los datos desde el archivo
    public static ArrayList<Proyecto> leerArchivoDeObjetosProyectos(){
        String nombreArchivo = "datosProyectos.obj";
        ObjectInputStream entrada = null;
        boolean exito = true;
        ArrayList<Proyecto> listaPrincipal = new ArrayList<>();
        ArrayList<Proyecto> listaNueva = new ArrayList<>();
        try{
            entrada = new ObjectInputStream(new FileInputStream(nombreArchivo));
            listaNueva = (ArrayList<Proyecto>) entrada.readObject();
            listaPrincipal.addAll(listaNueva);
        } catch(Exception ex){
            exito = false;
            JOptionPane.showMessageDialog(null,"Operacion fallida: error recuperado datos desde archivo.", "LECTURA DE ARCHIVO", JOptionPane.ERROR_MESSAGE);
        } finally{
            try{
                if (entrada != null) {
                    entrada.close();
                    }
                if(exito){
                    JOptionPane.showMessageDialog(null, "Operacion exitosa, archivo leído correctamente.", "LECTURA DE ARCHIVO", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Operacion fallida: error cerrando archivo.", "LECTURA DE ARCHIVO", JOptionPane.ERROR_MESSAGE);
            }
        }
        return listaPrincipal;
    }

        //Generar archivo de objetos de clientes: almacenar el cliente de cada proyecto en el archivo
    public static void generarArchivoDeObjetosClientes( ArrayList<Proyecto> datos){
        if(datos.isEmpty()){
            JOptionPane.showMessageDialog(null, "Lista vacia, no se puede generar el archivo ...", "GENERACION DE ARCHIVO", JOptionPane.ERROR_MESSAGE);
        } else{
            String nombreArchivo = "datosClientes.obj";
            ObjectOutputStream salida = null;
            boolean exito = true;
            ArrayList<Cliente> listaPrincipal = new ArrayList<>();
            for(Proyecto objeto : datos){
                listaPrincipal.add(objeto.getSuCliente());
            }
            try{
                salida = new ObjectOutputStream(new FileOutputStream(nombreArchivo));
                salida.writeObject(listaPrincipal);
            } catch(Exception ex){
                exito = false;
                JOptionPane.showMessageDialog(null,"Operacion fallida: error almacenando datos en archivo.\n "
                                                + "Revisar serialización de clases.", "GENERACION DE ARCHIVO", JOptionPane.ERROR_MESSAGE);
            } finally{
                try{
                    salida.close();
                    if(exito){
                        JOptionPane.showMessageDialog(null, "Operacion exitosa, archivo generado correctamente.", "GENERACION DE ARCHIVO", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(null,"Operacion fallida: error cerrando archivo.", "GENERACION DE ARCHIVO", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

        //Leer archivo de objetos de clientes: recuperar los datos desde el archivo, sin repetir clientes
    public static ArrayList<Cliente> leerArchivoDeObjetosClientes(){
        String nombreArchivo = "datosClientes.obj";
        ObjectInputStream entrada = null;
        boolean exito = true;
        ArrayList<Cliente> listaPrincipal = new ArrayList<>();
        ArrayList<Cliente> listaNueva = new ArrayList<>();
        try{
            entrada = new ObjectInputStream(new FileInputStream(nombreArchivo));
            listaNueva = (ArrayList<Cliente>) entrada.readObject();
            for(Cliente objeto : listaNueva){
                boolean clienteExiste = false;
                for(Cliente existente : listaPrincipal){
                    if(existente.getNumeroIdentificacion().equals(objeto.getNumeroIdentificacion())){
                        clienteExiste = true;
                    }
                }
                if(!clienteExiste){
                    listaPrincipal.add(objeto);
                }
            }
        } catch(Exception ex){
            exito = false;
            JOptionPane.showMessageDialog(null,"Operación fallida: error recuperado datos desde archivo.", "LECTURA DE ARCHIVO", JOptionPane.ERROR_MESSAGE);
        } finally{
            try{
                entrada.close();
                if(exito){
                    JOptionPane.showMessageDialog(null, "Operación exitosa, archivo leído correctamente.", "LECTURA DE ARCHIVO", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Operación fallida: error cerrando archivo.", "LECTURA DE ARCHIVO", JOptionPane.ERROR_MESSAGE);
            }
        }
        return listaPrincipal;
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
    //=========================================================================
    // MÉTODOS DEL PUNTO 3: EMPLEADO Y OPERACIONES DEL EQUIPO DE CADA PROYECTO
    // =========================================================================
     // @autor: Gabriela Imbachi
    /**
     * Opción 10 del menú:
     * Realiza el registro de un integrante en el equipo del proyecto indicado por su código.
     * Valida lista de proyectos llena, existencia del proyecto, e identificación válida/no duplicada.
     *
     * @param datos          Lista de proyectos registrados
     * @param codigoProyecto Código del proyecto al que se agregará el integrante
     * @return Mensaje con el resultado de la operación
     */
    public String registrarIntegranteProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigoProyecto) {
        // 1. Verificar si la lista de proyectos está llena
        if (datos == null || datos.isEmpty()) {
            return "No hay proyectos registrados en el sistema (la lista de proyectos está vacía).";
        }

        if (codigoProyecto == null || codigoProyecto.trim().isEmpty()) {
            return "Error: Debe proporcionar un código de proyecto válido.";
        }

        // 2. Recorrer la lista de proyectos y buscar el proyecto que cumpla la condición
        Proyecto proyectoEncontrado = buscarProyectoPorCodigo(datos, codigoProyecto.trim());
        if (proyectoEncontrado == null) {
            return "No existe un proyecto registrado con el código: " + codigoProyecto;
        }

        // 3. Solicitar y validar los datos del integrante (Empleado)
        Empleado nuevoIntegrante = solicitarDatosEmpleadoInteractivo(proyectoEncontrado.getSuEquipo());
        if (nuevoIntegrante == null) {
            return "Operación cancelada o datos del integrante inválidos.";
        }

        // 4. Agregar el integrante al equipo del proyecto y finalizar
        proyectoEncontrado.agregarIntegrante(nuevoIntegrante);
        return "Integrante registrado exitosamente en el proyecto [" + proyectoEncontrado.getCodigo() + " - "
                + proyectoEncontrado.getTitulo() + "]:\n" + nuevoIntegrante.toString();
    }

    /**
     * Opción 11 del menú:
     * Realiza el registro completo de los integrantes del equipo del proyecto indicado.
     *
     * @param datos          Lista de proyectos registrados
     * @param codigoProyecto Código del proyecto al que se registrará el equipo
     * @return Mensaje con el resultado de la operación
     */
    public String registrarEquipoProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigoProyecto) {
        // 1. Verificar si la lista está llena
        if (datos == null || datos.isEmpty()) {
            return "No hay proyectos registrados en el sistema (la lista de proyectos está vacía).";
        }

        if (codigoProyecto == null || codigoProyecto.trim().isEmpty()) {
            return "Error: Debe proporcionar un código de proyecto válido.";
        }

        // 2. Recorrer la lista de proyectos y buscar el proyecto que cumpla la condición
        for (Proyecto proyecto : datos) {
            if (proyecto.getCodigo().equalsIgnoreCase(codigoProyecto.trim())) {
                // Solicitar la cantidad de integrantes a registrar
                String cantStr = JOptionPane.showInputDialog(
                        this,
                        "Proyecto encontrado: " + proyecto.getTitulo()
                        + "\nIngrese la cantidad inicial de integrantes a registrar:",
                        "Registrar Equipo",
                        JOptionPane.QUESTION_MESSAGE
                );

                if (cantStr == null || cantStr.trim().isEmpty()) {
                    return "Registro de equipo cancelado.";
                }

                int cantidad;
                try {
                    cantidad = Integer.parseInt(cantStr.trim());
                    if (cantidad <= 0) {
                        return "Error: La cantidad de integrantes debe ser mayor a cero.";
                    }
                } catch (NumberFormatException ex) {
                    return "Error: La cantidad ingresada no es un número entero válido.";
                }

                // Crear la lista de integrantes (equipo)
                ArrayList<Empleado> nuevoEquipo = new ArrayList<>();

                // Ciclo para llenar la lista de integrantes (equipo)
                int contador = 1;
                boolean continuar = true;
                while (continuar) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Ingresando datos del integrante #" + contador,
                            "Registro de Equipo - Proyecto " + proyecto.getCodigo(),
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    Empleado integrante = solicitarDatosEmpleadoInteractivo(nuevoEquipo);
                    if (integrante != null) {
                        nuevoEquipo.add(integrante);
                        contador++;
                    } else {
                        int cancelarTodo = JOptionPane.showConfirmDialog(
                                this,
                                "No se completó el registro de este integrante. ¿Desea cancelar todo el registro del equipo?",
                                "Confirmar cancelación",
                                JOptionPane.YES_NO_OPTION
                        );
                        if (cancelarTodo == JOptionPane.YES_OPTION) {
                            return "Registro del equipo cancelado por el usuario.";
                        }
                    }

                    // Evaluar si ya completó la cantidad solicitada y si requiere otro integrante
                    if (nuevoEquipo.size() >= cantidad) {
                        int opcionOtro = JOptionPane.showConfirmDialog(
                                this,
                                "¿Desea registrar otro integrante adicional en el equipo?",
                                "Continuar registro de equipo",
                                JOptionPane.YES_NO_OPTION
                        );
                        continuar = (opcionOtro == JOptionPane.YES_OPTION);
                    }
                }

                // Actualizar el equipo del proyecto indicado y finalizar el recorrido
                proyecto.setSuEquipo(nuevoEquipo);
                return "Equipo registrado exitosamente para el proyecto [" + proyecto.getCodigo() + " - "
                        + proyecto.getTitulo() + "].\nTotal de integrantes registrados: " + nuevoEquipo.size()
                        + "\n\n" + consultarEquipoProyectoDadoCodigo(datos, codigoProyecto);
            }
        }

        return "No existe un proyecto registrado con el código: " + codigoProyecto;
    }

    /**
     * Opción 7 del menú:
     * Devuelve la información del equipo de un proyecto dado su código.
     * Conforma una cadena con el título del proyecto y toda la información de cada uno de los integrantes.
     *
     * @param datos          Lista de proyectos registrados
     * @param codigoProyecto Código del proyecto a consultar
     * @return Cadena con el título del proyecto y los datos de su equipo, o mensaje de validación
     */
    public String consultarEquipoProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigoProyecto) {
        // 1. Verificar si la lista está llena
        if (datos == null || datos.isEmpty()) {
            return "No hay proyectos registrados en el sistema (la lista de proyectos está vacía).";
        }

        if (codigoProyecto == null || codigoProyecto.trim().isEmpty()) {
            return "Error: Debe indicar un código de proyecto válido.";
        }

        // 2. Recorrer la lista de proyectos y buscar el proyecto que cumpla la condición
        for (Proyecto proyecto : datos) {
            if (proyecto.getCodigo().equalsIgnoreCase(codigoProyecto.trim())) {
                StringBuilder sb = new StringBuilder();
                sb.append("========================================================\n");
                sb.append("PROYECTO: ").append(proyecto.getCodigo()).append(" - ").append(proyecto.getTitulo()).append("\n");
                sb.append("========================================================\n");

                ArrayList<Empleado> equipo = proyecto.getSuEquipo();
                if (equipo == null || equipo.isEmpty()) {
                    sb.append("El proyecto aún no tiene integrantes registrados en su equipo.\n");
                } else {
                    sb.append("INTEGRANTES DEL EQUIPO (").append(equipo.size()).append("):\n");
                    for (int i = 0; i < equipo.size(); i++) {
                        sb.append(i + 1).append(". ").append(equipo.get(i).toString()).append("\n");
                    }
                }
                return sb.toString();
            }
        }

        return "No se encontró ningún proyecto con el código: " + codigoProyecto;
    }

    /**
     * Opción 12 del menú:
     * Realiza la eliminación de un integrante del equipo del proyecto indicado.
     * Valida que la lista no esté vacía, que el proyecto exista, que la identificación sea válida
     * y que el integrante pertenezca al equipo antes de eliminarlo.
     *
     * @param datos          Lista de proyectos registrados
     * @param codigoProyecto Código del proyecto del cual se retirará el integrante
     * @return Cadena indicando el resultado de la eliminación
     */
    public String eliminarIntegranteProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigoProyecto) {
        // 1. Verificar si la lista de proyectos está llena
        if (datos == null || datos.isEmpty()) {
            return "No hay proyectos registrados en el sistema (la lista de proyectos está vacía).";
        }

        if (codigoProyecto == null || codigoProyecto.trim().isEmpty()) {
            return "Error: Debe proporcionar un código de proyecto válido.";
        }

        // 2. Buscar el proyecto y validar su existencia
        Proyecto proyectoEncontrado = buscarProyectoPorCodigo(datos, codigoProyecto.trim());
        if (proyectoEncontrado == null) {
            return "No existe un proyecto registrado con el código: " + codigoProyecto;
        }

        if (proyectoEncontrado.getSuEquipo() == null || proyectoEncontrado.getSuEquipo().isEmpty()) {
            return "El proyecto [" + proyectoEncontrado.getCodigo() + "] no tiene integrantes en su equipo para eliminar.";
        }

        // 3. Solicitar el número de identificación del integrante (empleado)
        String numId = JOptionPane.showInputDialog(
                this,
                "Ingrese el número de identificación del integrante a eliminar del proyecto "
                + proyectoEncontrado.getCodigo() + ":",
                "Eliminar Integrante del Equipo",
                JOptionPane.QUESTION_MESSAGE
        );

        return eliminarIntegrantePorId(datos, codigoProyecto, numId);
    }

    /**
     * Método sobrecargado auxiliar que permite eliminar un integrante pasando el ID por parámetro
     * (facilita tanto la invocación desde GUI como las pruebas unitarias automatizadas).
     */
    public String eliminarIntegrantePorId(ArrayList<Proyecto> datos, String codigoProyecto, String numIdIntegrante) {
        if (datos == null || datos.isEmpty()) {
            return "No hay proyectos registrados en el sistema (la lista de proyectos está vacía).";
        }
        if (codigoProyecto == null || codigoProyecto.trim().isEmpty()) {
            return "Error: El código del proyecto es inválido.";
        }
        if (numIdIntegrante == null || numIdIntegrante.trim().isEmpty()) {
            return "Error: El número de identificación del integrante no puede estar vacío.";
        }

        for (Proyecto proyecto : datos) {
            if (proyecto.getCodigo().equalsIgnoreCase(codigoProyecto.trim())) {
                if (proyecto.getSuEquipo() == null || proyecto.getSuEquipo().isEmpty()) {
                    return "El proyecto [" + proyecto.getCodigo() + "] no tiene integrantes registrados en su equipo.";
                }

                // Verificar que el integrante pertenezca al equipo y eliminarlo
                Empleado eliminado = proyecto.eliminarIntegrante(numIdIntegrante.trim());
                if (eliminado != null) {
                    return "Integrante eliminado exitosamente del proyecto [" + proyecto.getCodigo() + "]:\n"
                            + eliminado.toString();
                } else {
                    return "Validación fallida: El empleado con identificación '" + numIdIntegrante.trim()
                            + "' NO pertenece al equipo del proyecto [" + proyecto.getCodigo() + "].";
                }
            }
        }

        return "No existe un proyecto registrado con el código: " + codigoProyecto;
    }

    /**
     * Alias compatible con la nota descriptiva del diagrama ("retirarIntegranteProyectoDadoCodigo").
     */
    public void retirarIntegranteProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigoProyecto) {
        String resultado = eliminarIntegranteProyectoDadoCodigo(datos, codigoProyecto);
        JOptionPane.showMessageDialog(this, resultado);
    }

    // ==================== MÉTODOS AUXILIARES Y DE VALIDACIÓN (PUNTO 3) ====================

    private Proyecto buscarProyectoPorCodigo(ArrayList<Proyecto> datos, String codigoProyecto) {
        for (Proyecto p : datos) {
            if (p.getCodigo().equalsIgnoreCase(codigoProyecto)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Verifica si ya existe un integrante con el mismo número de identificación dentro de un equipo.
     */
    public boolean existeIntegranteEnEquipo(ArrayList<Empleado> equipo, String numeroIdentificacion) {
        if (equipo == null || numeroIdentificacion == null) {
            return false;
        }
        for (Empleado emp : equipo) {
            if (emp.getNumeroIdentificacion().equalsIgnoreCase(numeroIdentificacion.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Solicita por cuadros de diálogo los datos de un Empleado validando:
     * - Identificación no vacía y no repetida en el equipo
     * - Nombre completo no vacío
     * - Título profesional no vacío
     * - Años de experiencia numéricos válidos (byte >= 0)
     */
    private Empleado solicitarDatosEmpleadoInteractivo(ArrayList<Empleado> equipoActual) {
        String id = JOptionPane.showInputDialog(this, "Número de identificación del empleado:");
        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Validación: El número de identificación es obligatorio.");
            return null;
        }
        id = id.trim();

        if (existeIntegranteEnEquipo(equipoActual, id)) {
            JOptionPane.showMessageDialog(this, "Validación: Ya existe un integrante con la identificación '" + id + "' en este equipo.");
            return null;
        }

        String nombre = JOptionPane.showInputDialog(this, "Nombre completo del empleado:");
        if (nombre == null || nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Validación: El nombre completo es obligatorio.");
            return null;
        }

        String titulo = JOptionPane.showInputDialog(this, "Título profesional del empleado:");
        if (titulo == null || titulo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Validación: El título profesional es obligatorio.");
            return null;
        }

        String expStr = JOptionPane.showInputDialog(this, "Años de experiencia (0 a 60):");
        if (expStr == null || expStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Validación: Debe ingresar los años de experiencia.");
            return null;
        }

        byte aniosExp;
        try {
            aniosExp = Byte.parseByte(expStr.trim());
            if (aniosExp < 0 || aniosExp > 60) {
                JOptionPane.showMessageDialog(this, "Los años de experiencia deben estar entre 0 y 60.");
            return null;
}
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Validación: Los años de experiencia deben ser un número entero válido (tipo byte).");
            return null;
        }

        return new Empleado(id, nombre.trim(), titulo.trim(), aniosExp);
    }

    // ==================== MAIN ====================

    }

