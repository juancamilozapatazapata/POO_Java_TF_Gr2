/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

import Modelo.Cliente;
import Modelo.Proyecto;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import Modelo.Empresa;
import Modelo.PersonaNatural;
import Modelo.Contacto;
import Modelo.Empleado;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Juan D
 */
public class UsaProyecto extends javax.swing.JFrame {
    
    /*
     * Definir y crear el contenedor (colección ArrayList)
     */
    private static ArrayList<Proyecto> losProyectos = new ArrayList<>();
    private static ArrayList<Cliente> losClientes = new ArrayList<>();

    boolean registroValido = true;
    private javax.swing.JDialog jDialogEquipo;
    private javax.swing.JDialog jDialogEstado;
    private JTextField jTextFieldCodigoEstado;
    private JTextField jTextFieldNuevoEstado;
    private JTextField jTextFieldCodigoEquipo;
    private javax.swing.JDialog jDialogRegistro;
    private JTextField jTextFieldTitulo;
    private JTextField jTextFieldFechaInicio;
    private JTextField jTextFieldUnidad;
    private JTextField jTextFieldDuracion;
    private JTextField jTextFieldTipoCliente;
    private JTextField jTextFieldIdCliente;
    private JTextField jTextFieldNombreCliente;
    private JTextField jTextFieldCiudadCliente;
    private JTextField jTextFieldTelefono;
    private JTextField jTextFieldCorreo;
    private JTextField jTextFieldNombreContacto;
    private JTextField jTextFieldCargoContacto;
    private JTextField jTextFieldIdGerente;
    private JTextField jTextFieldNombreGerente;
    private JTextField jTextFieldTituloGerente;
    private JTextField jTextFieldExperienciaGerente;

    
    /*****************************************************/
    /*Constructor*/

    
 /************************************/   
    public UsaProyecto() throws HeadlessException{
        initComponents();
        crearDialogoRegistro();
        crearDialogoEquipo();
        crearDialogoEstado();
    }

    public UsaProyecto(GraphicsConfiguration gc) {
        super(gc);
    }

    public UsaProyecto(String title) throws HeadlessException {
        super(title);
    }



    /***********************************************************************************/
    /*registro de proyectos*/

    /*
     * Método generarCodigoProyecto. Genera el código del nuevo proyecto: año actual, guión y consecutivo (inicia en 1 cada año).
    */
    public String generarCodigoProyecto(ArrayList<Proyecto> datos){
        //1. Obtener el año actual y definir el consecutivo
        int anio = LocalDate.now().getYear();
        int consecutivo = 1;

        //2. Contar los proyectos registrados en el año actual
        for(Proyecto objeto : datos){
            if(objeto.getCodigo().startsWith(anio + "-")){
                consecutivo++;
            }
        }

        //3. Conformar el código: año, guión y consecutivo de 3 dígitos
        String codigo = anio + "-";
        if(consecutivo < 10){
            codigo += "00" + consecutivo;
        } else if(consecutivo < 100){
            codigo += "0" + consecutivo;
        } else{
            codigo += consecutivo;
        }
        return codigo;
    }

    /*
     * Método registrarProyecto. Obtiene los datos desde los campos de texto, crea el proyecto y lo agrega a la colección.
     * Nota. Se utiliza "registroValido" como variable de control para validaciones y gestión de excepciones de datos de entrada.
    */
    public ArrayList<Proyecto> registrarProyecto(ArrayList<Proyecto> datos){
        //1. Obtención de los datos del proyecto
        String codigo = generarCodigoProyecto(datos);
        String titulo;
        LocalDate fechaInicio = null;
        String unidadDuracion;
        int duracionEstimada = 0;
        String tipoCliente;
        String idCliente;
        String nombreCliente;
        String ciudadCliente;
        String telefono;
        String correo;
        String nombreContacto;
        String cargoContacto;
        String idGerente;
        String nombreGerente;
        String tituloGerente;
        int aniosExperiencia = 0;

        titulo = jTextFieldTitulo.getText();
        if(titulo.isEmpty() || titulo.isBlank() || titulo.length()<20 || titulo.length()>100){
            JOptionPane.showMessageDialog(null, "Error, campo vacío o longitud incorrecta.", "TÍTULO: ENTRADA INVÁLIDA", JOptionPane.ERROR_MESSAGE);
            registroValido = false;
        }

        try{
            fechaInicio = LocalDate.parse(jTextFieldFechaInicio.getText());
        } catch(DateTimeException dtex){
            JOptionPane.showMessageDialog(null, "Error, formato de fecha incorrecto (aaaa-mm-dd).", "FECHA INICIO: ENTRADA INVÁLIDA", JOptionPane.ERROR_MESSAGE);
            registroValido = false;
        }

        unidadDuracion = jTextFieldUnidad.getText().toLowerCase();
        if(!unidadDuracion.equals("semanas") && !unidadDuracion.equals("meses") && !unidadDuracion.equals("años")){
            JOptionPane.showMessageDialog(null, "Error, valor incorrecto.", "UNIDAD DURACIÓN: ENTRADA INVÁLIDA", JOptionPane.ERROR_MESSAGE);
            registroValido = false;
        }

        try{
            duracionEstimada = Integer.parseInt(jTextFieldDuracion.getText());
        } catch(NumberFormatException nfex){
            JOptionPane.showMessageDialog(null, "Error en tipo de dato", "DURACIÓN: ENTRADA INVÁLIDA", JOptionPane.ERROR_MESSAGE);
            registroValido = false;
        }
        if(unidadDuracion.equals("semanas") || unidadDuracion.equals("años")){
            if(duracionEstimada<1 || duracionEstimada>3){
                JOptionPane.showMessageDialog(null, "Error, valor incorrecto", "DURACIÓN: ENTRADA INVÁLIDA", JOptionPane.ERROR_MESSAGE);
                registroValido = false;
            }
        }
        if(unidadDuracion.equals("meses")){
            if(duracionEstimada<1 || duracionEstimada>35 || duracionEstimada==12 || duracionEstimada==24){
                JOptionPane.showMessageDialog(null, "Error, valor incorrecto", "DURACIÓN: ENTRADA INVÁLIDA", JOptionPane.ERROR_MESSAGE);
                registroValido = false;
            }
        }

        tipoCliente = jTextFieldTipoCliente.getText().toUpperCase();
        if(!tipoCliente.equals("EMPRESA") && !tipoCliente.equals("PERSONANATURAL")){
            JOptionPane.showMessageDialog(null, "Error, valor incorrecto.", "TIPO CLIENTE: ENTRADA INVÁLIDA", JOptionPane.ERROR_MESSAGE);
            registroValido = false;
        }

        idCliente = jTextFieldIdCliente.getText();
        nombreCliente = jTextFieldNombreCliente.getText();
        ciudadCliente = jTextFieldCiudadCliente.getText();
        telefono = jTextFieldTelefono.getText();
        correo = jTextFieldCorreo.getText();
        nombreContacto = jTextFieldNombreContacto.getText();
        cargoContacto = jTextFieldCargoContacto.getText();
        idGerente = jTextFieldIdGerente.getText();
        nombreGerente = jTextFieldNombreGerente.getText();
        tituloGerente = jTextFieldTituloGerente.getText();

        try{
            aniosExperiencia = Integer.parseInt(jTextFieldExperienciaGerente.getText());
        } catch(NumberFormatException nfex){
            JOptionPane.showMessageDialog(null, "Error en tipo de dato", "EXPERIENCIA: ENTRADA INVÁLIDA", JOptionPane.ERROR_MESSAGE);
            registroValido = false;
        }

        if(registroValido){
            //2. Crear el objeto proyecto (con su cliente y su gerente)
            Cliente suCliente;
            if(tipoCliente.equals("EMPRESA")){
                Contacto suContacto = new Contacto(nombreContacto, cargoContacto, telefono, correo);
                suCliente = new Empresa(suContacto, idCliente, nombreCliente, ciudadCliente);
            } else{
                suCliente = new PersonaNatural(idCliente, nombreCliente, ciudadCliente, telefono, correo);
            }
            Empleado suGerente = new Empleado(idGerente, nombreGerente, tituloGerente, (byte) aniosExperiencia);
            Proyecto objeto = new Proyecto(codigo, titulo, fechaInicio, unidadDuracion, (byte) duracionEstimada, suCliente, suGerente, "INICIADO");

            //3. Agregar el objeto proyecto a la colección
            datos.add(objeto);

        }
        //4. Devolver la colección
        return datos;
    }

    /*
     * Ventana con los campos de texto para registrar un proyecto. Se abre con el botón "Registrar".
    */
    private void crearDialogoRegistro(){
        jTextFieldTitulo = new JTextField();
        jTextFieldFechaInicio = new JTextField();
        jTextFieldUnidad = new JTextField();
        jTextFieldDuracion = new JTextField();
        jTextFieldTipoCliente = new JTextField();
        jTextFieldIdCliente = new JTextField();
        jTextFieldNombreCliente = new JTextField();
        jTextFieldCiudadCliente = new JTextField();
        jTextFieldTelefono = new JTextField();
        jTextFieldCorreo = new JTextField();
        jTextFieldNombreContacto = new JTextField();
        jTextFieldCargoContacto = new JTextField();
        jTextFieldIdGerente = new JTextField();
        jTextFieldNombreGerente = new JTextField();
        jTextFieldTituloGerente = new JTextField();
        jTextFieldExperienciaGerente = new JTextField();

        String[] etiquetas = {"Título (20 a 100 caracteres)", "Fecha inicio (aaaa-mm-dd)", "Unidad (semanas, meses, años)", "Duración estimada",
                              "Tipo de cliente (Empresa, PersonaNatural)", "ID cliente", "Nombre cliente", "Ciudad cliente",
                              "Teléfono (cliente o contacto)", "Correo (cliente o contacto)", "Nombre contacto (solo Empresa)", "Cargo contacto (solo Empresa)",
                              "ID gerente", "Nombre gerente", "Título profesional gerente", "Años experiencia gerente"};
        JTextField[] campos = {jTextFieldTitulo, jTextFieldFechaInicio, jTextFieldUnidad, jTextFieldDuracion,
                               jTextFieldTipoCliente, jTextFieldIdCliente, jTextFieldNombreCliente, jTextFieldCiudadCliente,
                               jTextFieldTelefono, jTextFieldCorreo, jTextFieldNombreContacto, jTextFieldCargoContacto,
                               jTextFieldIdGerente, jTextFieldNombreGerente, jTextFieldTituloGerente, jTextFieldExperienciaGerente};

        JPanel panelCampos = new JPanel(new java.awt.GridLayout(0, 2, 8, 6));
        for(int i = 0; i < campos.length; i++){
            panelCampos.add(new JLabel(etiquetas[i]));
            panelCampos.add(campos[i]);
        }
        panelCampos.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton botonGuardar = new JButton("Guardar proyecto");
        botonGuardar.addActionListener(this::jButtonGuardarProyectoActionPerformed);

        jDialogRegistro = new javax.swing.JDialog(this, "REGISTRAR PROYECTO", true);
        jDialogRegistro.getContentPane().add(panelCampos, java.awt.BorderLayout.CENTER);
        jDialogRegistro.getContentPane().add(botonGuardar, java.awt.BorderLayout.SOUTH);
        jDialogRegistro.pack();
        jDialogRegistro.setLocationRelativeTo(this);

        jButtonRegistrar.addActionListener(evt -> jDialogRegistro.setVisible(true));
    }

    private void jButtonGuardarProyectoActionPerformed(java.awt.event.ActionEvent evt){
        losProyectos = registrarProyecto(losProyectos);
        if(registroValido){
            JOptionPane.showMessageDialog(null, "Proyecto registrado con éxito.");
            jTextFieldTitulo.setText("");
            jTextFieldFechaInicio.setText("");
            jTextFieldUnidad.setText("");
            jTextFieldDuracion.setText("");
            jTextFieldTipoCliente.setText("");
            jTextFieldIdCliente.setText("");
            jTextFieldNombreCliente.setText("");
            jTextFieldCiudadCliente.setText("");
            jTextFieldTelefono.setText("");
            jTextFieldCorreo.setText("");
            jTextFieldNombreContacto.setText("");
            jTextFieldCargoContacto.setText("");
            jTextFieldIdGerente.setText("");
            jTextFieldNombreGerente.setText("");
            jTextFieldTituloGerente.setText("");
            jTextFieldExperienciaGerente.setText("");
            jDialogRegistro.setVisible(false);
        } else{
            JOptionPane.showMessageDialog(null, "Proyecto no registrado, entrada(s) inválidas.");
            registroValido = true;
        }
    }

    /***********************************************************************************/
    /*consultas generales*/

    /*
     * Método consultarTodosProyectos. Conforma una cadena con la información de cada uno de los proyectos registrados.
    */
    public String consultarTodosProyectos(ArrayList<Proyecto> datos){
        String resultado = "CONSULTA DE TODOS LOS PROYECTOS\n";
        if(datos.isEmpty()) {
            resultado += "No hay proyectos registrados.";
            return resultado;
        }
        for(Proyecto objeto : datos){
            resultado += objeto.toString();
            resultado += ", fecha de finalización=" + objeto.determinarFechaFinalizacion();
            resultado += "\n";
        }
        return resultado;
    }

    /*
     * Método consultarProyectosEnCurso. Conforma una cadena con la información de cada uno de los proyectos con estado "EN CURSO".
    */
    public String consultarProyectosEnCurso(ArrayList<Proyecto> datos){
        String resultado = "CONSULTA DE PROYECTOS EN CURSO\n";
        if(datos.isEmpty()) {
            resultado += "No hay proyectos registrados.";
            return resultado;
        }
        boolean proyectoExiste = false;
        for(Proyecto objeto : datos){
            if(objeto.getEstado().equals("EN CURSO")){
                proyectoExiste = true;
                resultado += objeto.toString();
                resultado += ", fecha de finalización=" + objeto.determinarFechaFinalizacion();
                resultado += "\n";
            }
        }
        if(!proyectoExiste) {
            resultado += "No hay proyectos en curso.";
        }
        return resultado;
    }

    /*
     * Método consultarPrimerProyecto. Conforma una cadena con la información del primer proyecto registrado.
    */
    public String consultarPrimerProyecto(ArrayList<Proyecto> datos){
        //----- 1. Definir e inicializar la cadena a devolver
        String resultado = "CONSULTA DEL PRIMER PROYECTO REGISTRADO\n";

        //----- 2. Manejo de la validación por restricción de lista vacía

        //2a. Si la lista está vacía: agregar mensaje indicando la situación al resultado y devolverlo
        if(datos.isEmpty()){
            resultado += "No hay proyectos registrados.";
            return resultado;
        }

        //2b. Si la lista está llena: agregar la información solicitada al resultado y devolverlo
        Proyecto objeto = datos.getFirst();
        resultado += objeto.toString();
        resultado += ", fecha de finalización=" + objeto.determinarFechaFinalizacion();
        resultado += "\n";
        return resultado;
    }

    /*
     * Método consultarUltimoProyecto. Conforma una cadena con la información del último proyecto registrado.
    */
    public String consultarUltimoProyecto(ArrayList<Proyecto> datos){
        //----- 1. Definir e inicializar la cadena a devolver
        String resultado = "CONSULTA DEL ÚLTIMO PROYECTO REGISTRADO\n";

        //----- 2. Manejo de la validación por restricción de lista vacía

        //2a. Si la lista está vacía: agregar mensaje indicando la situación al resultado y devolverlo
        if(datos.isEmpty()){
            resultado += "No hay proyectos registrados.";
            return resultado;
        }

        //2b. Si la lista está llena: agregar la información solicitada al resultado y devolverlo
        Proyecto objeto = datos.getLast();
        resultado += objeto.toString();
        resultado += ", fecha de finalización=" + objeto.determinarFechaFinalizacion();
        resultado += "\n";
        return resultado;
    }

    /*
     * Método consultarProyectoDadoCodigo. Conforma una cadena con la información del proyecto que tenga el código indicado.
    */
    public String consultarProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigoProyecto){
        String resultado = "CONSULTA DE PROYECTO DADO CÓDIGO " + codigoProyecto + "\n";
        if(datos.isEmpty()) {
            resultado += "No hay proyectos registrados.";
            return resultado;
        }
        boolean proyectoExiste = false;
        for(Proyecto objeto : datos){
            if(objeto.getCodigo().equals(codigoProyecto)){
                proyectoExiste = true;
                resultado += objeto.toString();
                resultado += ", fecha de finalización=" + objeto.determinarFechaFinalizacion();
                resultado += "\n";
            }
        }
        if(!proyectoExiste) {
            resultado += "No hay proyectos con el código indicado.";
        }
        return resultado;
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
            if (aniosExp < 0) {
                JOptionPane.showMessageDialog(this, "Validación: Los años de experiencia no pueden ser negativos.");
                return null;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Validación: Los años de experiencia deben ser un número entero válido (tipo byte).");
            return null;
        }

        return new Empleado(id, nombre.trim(), titulo.trim(), aniosExp);
    }

    /***********************************************************************************/
    /*ventana "Equipo": botones que llaman a los métodos del equipo*/

    /*
     * Ventana con el código del proyecto y los botones del equipo. Se abre con el botón "Equipo".
    */
    private void crearDialogoEquipo(){
        jTextFieldCodigoEquipo = new JTextField();

        JPanel panelCodigo = new JPanel(new java.awt.GridLayout(0, 2, 8, 6));
        panelCodigo.add(new JLabel("Código del proyecto"));
        panelCodigo.add(jTextFieldCodigoEquipo);
        panelCodigo.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton botonRegistrarIntegrante = new JButton("Registrar integrante");
        botonRegistrarIntegrante.addActionListener(this::jButtonRegistrarIntegranteActionPerformed);
        JButton botonRegistrarEquipo = new JButton("Registrar equipo");
        botonRegistrarEquipo.addActionListener(this::jButtonRegistrarEquipoActionPerformed);
        JButton botonConsultarEquipo = new JButton("Consultar equipo");
        botonConsultarEquipo.addActionListener(this::jButtonConsultaEQUIPOActionPerformed);
        JButton botonEliminarIntegrante = new JButton("Retirar integrante");
        botonEliminarIntegrante.addActionListener(this::jButtonEliminarIntegranteActionPerformed);

        JPanel panelBotones = new JPanel(new java.awt.GridLayout(0, 2, 8, 6));
        panelBotones.add(botonRegistrarIntegrante);
        panelBotones.add(botonRegistrarEquipo);
        panelBotones.add(botonConsultarEquipo);
        panelBotones.add(botonEliminarIntegrante);
        panelBotones.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 10, 10));

        jDialogEquipo = new javax.swing.JDialog(this, "EQUIPO DEL PROYECTO", false);
        jDialogEquipo.getContentPane().add(panelCodigo, java.awt.BorderLayout.CENTER);
        jDialogEquipo.getContentPane().add(panelBotones, java.awt.BorderLayout.SOUTH);
        jDialogEquipo.pack();
        jDialogEquipo.setLocationRelativeTo(this);

        jButtonEquipo.addActionListener(evt -> jDialogEquipo.setVisible(true));
    }

    private void jButtonRegistrarIntegranteActionPerformed(java.awt.event.ActionEvent evt){
        String respuesta = registrarIntegranteProyectoDadoCodigo(losProyectos, jTextFieldCodigoEquipo.getText());
        jTextAreaResultados.setText(respuesta);
        JOptionPane.showMessageDialog(this, respuesta);
    }

    private void jButtonRegistrarEquipoActionPerformed(java.awt.event.ActionEvent evt){
        String respuesta = registrarEquipoProyectoDadoCodigo(losProyectos, jTextFieldCodigoEquipo.getText());
        jTextAreaResultados.setText(respuesta);
        JOptionPane.showMessageDialog(this, respuesta);
    }

    private void jButtonConsultaEQUIPOActionPerformed(java.awt.event.ActionEvent evt){
        jTextAreaResultados.setText(consultarEquipoProyectoDadoCodigo(losProyectos, jTextFieldCodigoEquipo.getText()));
    }

    private void jButtonEliminarIntegranteActionPerformed(java.awt.event.ActionEvent evt){
        String respuesta = eliminarIntegranteProyectoDadoCodigo(losProyectos, jTextFieldCodigoEquipo.getText());
        jTextAreaResultados.setText(respuesta);
        JOptionPane.showMessageDialog(this, respuesta);
    }

    /***********************************************************************************/
    /*estado de los proyectos*/

    /*
     * Método actualizarEstadoProyectoDadoCodigo. Cambia el estado del proyecto con el código indicado por el estado escrito en el campo "Nuevo estado".
    */
    public String actualizarEstadoProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigoProyecto){
        String resultado = "ACTUALIZACIÓN DE ESTADO DEL PROYECTO " + codigoProyecto + "\n";
        if(datos.isEmpty()) {
            resultado += "No hay proyectos registrados.";
            return resultado;
        }
        String nuevoEstado = jTextFieldNuevoEstado.getText().trim().toUpperCase();
        if(!nuevoEstado.equals("INICIADO") && !nuevoEstado.equals("EN CURSO") && !nuevoEstado.equals("CANCELADO")
            && !nuevoEstado.equals("FINALIZADO") && !nuevoEstado.equals("SUSPENDIDO") && !nuevoEstado.equals("APLAZADO")){
            resultado += "Estado inválido. Use INICIADO, EN CURSO, CANCELADO, FINALIZADO, SUSPENDIDO o APLAZADO.";
            return resultado;
        }
        boolean proyectoExiste = false;
        for(Proyecto objeto : datos){
            if(objeto.getCodigo().equals(codigoProyecto)){
                proyectoExiste = true;
                objeto.setEstado(nuevoEstado);
                resultado += "Operación exitosa, el proyecto quedó en estado " + objeto.getEstado();
                resultado += "\n";
            }
        }
        if(!proyectoExiste) {
            resultado += "No hay proyectos con el código indicado.";
        }
        return resultado;
    }

    /*
     * Ventana con el código del proyecto y el nuevo estado. Se abre con el botón "Estado".
    */
    private void crearDialogoEstado(){
        jTextFieldCodigoEstado = new JTextField();
        jTextFieldNuevoEstado = new JTextField();

        JPanel panelCampos = new JPanel(new java.awt.GridLayout(0, 2, 8, 6));
        panelCampos.add(new JLabel("Código del proyecto"));
        panelCampos.add(jTextFieldCodigoEstado);
        panelCampos.add(new JLabel("Nuevo estado"));
        panelCampos.add(jTextFieldNuevoEstado);
        panelCampos.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 4, 10));

        JLabel ayuda = new JLabel("INICIADO, EN CURSO, CANCELADO, FINALIZADO, SUSPENDIDO, APLAZADO");
        ayuda.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 4, 10));

        JButton botonActualizar = new JButton("Actualizar estado");
        botonActualizar.addActionListener(this::jButtonActualizarEstadoActionPerformed);
        JPanel panelBoton = new JPanel(new java.awt.BorderLayout());
        panelBoton.add(ayuda, java.awt.BorderLayout.NORTH);
        panelBoton.add(botonActualizar, java.awt.BorderLayout.CENTER);
        panelBoton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 10, 10));

        jDialogEstado = new javax.swing.JDialog(this, "ESTADO DEL PROYECTO", false);
        jDialogEstado.getContentPane().add(panelCampos, java.awt.BorderLayout.CENTER);
        jDialogEstado.getContentPane().add(panelBoton, java.awt.BorderLayout.SOUTH);
        jDialogEstado.pack();
        jDialogEstado.setLocationRelativeTo(this);

        jButtonEstado.addActionListener(evt -> jDialogEstado.setVisible(true));
    }

    private void jButtonActualizarEstadoActionPerformed(java.awt.event.ActionEvent evt){
        if(jTextFieldCodigoEstado.getText().isBlank() || jTextFieldNuevoEstado.getText().isBlank()){
            JOptionPane.showMessageDialog(null, "Operación inválida, código o estado vacío.", "ENTRADA INVÁLIDA", JOptionPane.ERROR_MESSAGE);
        } else{
            String respuesta = actualizarEstadoProyectoDadoCodigo(losProyectos, jTextFieldCodigoEstado.getText());
            jTextAreaResultados.setText(respuesta);
            JOptionPane.showMessageDialog(this, respuesta);
            if(respuesta.contains("Operación exitosa")){
                jTextFieldCodigoEstado.setText("");
                jTextFieldNuevoEstado.setText("");
            }
        }
    }

    /***********************************************************************************/
    /*consultas*/
//Consultar el gerente de un proyecto dado "codigoProyecto"
    public String consultarGerenteProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigoProyecto) {
        String resultado = "CONSULTA DEL GERENTE DEL PROYECTO " + codigoProyecto + "\n";
        if(datos.isEmpty()) {
            resultado += "No hay proyectos registrados.";
            return resultado;
        }
        boolean proyectoExiste = false;   
        for(Proyecto objeto : datos){
            if(objeto.getCodigo().equalsIgnoreCase(codigoProyecto)){
                proyectoExiste = true;                
                resultado += "Proyecto=" + objeto.getTitulo();
                resultado += ", gerente=" + objeto.getSuGerente();
                resultado += "\n";
            }
        }
        if(!proyectoExiste) {
            resultado += "No hay gerente con el código indicado.";
        }
        return resultado;
    }
/*Consultar dado "numeroIDGerente" */

    public String consultarProyectosDadoNumIDEGerente(ArrayList<Proyecto> LosProyectos, String numIDEGerente){
        String resultado = "CONSULTA DE PROYECTOS DADO IDE GERENTE " + numIDEGerente + "\n";
        if(LosProyectos.isEmpty()) {
            resultado += "No hay gerentes registrados.";
            return resultado;
        }
        boolean gerenteExiste = false;
        for(Proyecto objeto : LosProyectos){
            if(objeto.getSuGerente().getNumeroIdentificacion().equalsIgnoreCase(numIDEGerente)){ 
                gerenteExiste = true;                
                resultado += "\n" + objeto.toString();
                resultado += "\n  fecha de finalización= " + objeto.determinarFechaFinalizacion();
                resultado += "\n";
            }
        }
        if(!gerenteExiste) {
            resultado += "No hay gerentes con el IDE indicado.";
        }
        return resultado;
    }    
    
/*Consultar dado "numIDECliente"*/
    public String consultarProyectosDadoNumIDECliente(ArrayList<Proyecto> LosProyectos, String numIDECliente){
        String resultado = "CONSULTA DE PROYECTOS DADO IDE CLIENTE " + numIDECliente + "\n";
        if(LosProyectos.isEmpty()) {
            resultado += "No hay clientes registrados.";
            return resultado;
        }
        boolean clienteExiste = false;
        for(Proyecto objeto : LosProyectos){
            if(objeto.getSuCliente().getNumeroIdentificacion().equalsIgnoreCase(numIDECliente)){ 
                clienteExiste = true;                
                resultado += "\n" + objeto.toString();
                resultado += "\n  fecha de finalización= " + objeto.determinarFechaFinalizacion();
                resultado += "\n";
            }
        }
        if(!clienteExiste) {
            resultado += "No hay clientes con el IDE indicado.";
        }
        return resultado;
    }    
     
 /*Consultar dado "tipoCliente"*/
 

    public String consultarProyectosDadoTipoCliente(ArrayList<Proyecto> losProyectos, String tipoCliente){
        String resultado = "CONSULTAR LOS CLIENTES DADA CATEGORÍA " + tipoCliente + "\n";
        if(losProyectos.isEmpty()){
            resultado += "\n lista de proyectos vacía.";
            return resultado;
        }
        boolean existe = false;
        for(Proyecto objeto : losProyectos){
            switch(tipoCliente){
                case "EMPRESA":
                    if(objeto.getSuCliente() instanceof Empresa){
                        existe = true;
                        resultado += "\n" + objeto.toString();
                        resultado += "\n  fecha de finalización= " + objeto.determinarFechaFinalizacion();
                        resultado += "\n";                    
                    }                    
                    break;
                case "PERSONANATURAL":
                    if(objeto.getSuCliente() instanceof PersonaNatural){
                        existe = true;
                        resultado += "\n" + objeto.toString();
                        resultado += "\n  fecha de finalización= " + objeto.determinarFechaFinalizacion();
                        resultado += "\n";                     
                    }                     
                    break;
                default:
                    return resultado + "\nTipo de cliente inválido. Use Empresa o PersonaNatural.";
            }
        }
        if(!existe){
            resultado += "\nNo hay proyectos con el tipo de cliente indicado.";
            
        }
        return resultado;
    }
                                              

   
    
    /***********************************************************************************/
    
    
    /***********************************************************************************/
    /*Gestionar archivo de objetos*/
        //Generar archivo de objetos de proyectos: almacenar la coleccion en el archivo
    public static void generarArchivoDeObjetosProyectos( ArrayList<Proyecto> listaPrincipal){
        if(listaPrincipal.isEmpty()){
            JOptionPane.showMessageDialog(null, "Lista vacia, no se puede generar el archivo ...", "GENERACION DE ARCHIVO", JOptionPane.ERROR_MESSAGE);
        } else{
            String nombreArchivo = "datosProyectos.obj";
            ObjectOutputStream salida = null;
            boolean exito = true;
            try{
                salida = new ObjectOutputStream(new FileOutputStream(nombreArchivo));
                salida.writeObject(listaPrincipal);
            } catch(Exception ex){
                exito = false;
                JOptionPane.showMessageDialog(null,"Operacion fallida: error almacenando datos en archivo.\n "
                                                + "Revisar serializacion de clases.", "GENERACION DE ARCHIVO", JOptionPane.ERROR_MESSAGE);
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
                entrada.close();
                if(exito){
                    JOptionPane.showMessageDialog(null, "Operacion exitosa, archivo leido correctamente.", "LECTURA DE ARCHIVO", JOptionPane.INFORMATION_MESSAGE);
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
                                                + "Revisar serializacion de clases.", "GENERACION DE ARCHIVO", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null,"Operacion fallida: error recuperado datos desde archivo.", "LECTURA DE ARCHIVO", JOptionPane.ERROR_MESSAGE);
        } finally{
            try{
                entrada.close();
                if(exito){
                    JOptionPane.showMessageDialog(null, "Operacion exitosa, archivo leido correctamente.", "LECTURA DE ARCHIVO", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Operacion fallida: error cerrando archivo.", "LECTURA DE ARCHIVO", JOptionPane.ERROR_MESSAGE);
            }
        }
        return listaPrincipal;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bloque = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButtonRegistrar = new javax.swing.JButton();
        jButtonEquipo = new javax.swing.JButton();
        jButtonEstado = new javax.swing.JButton();
        jTextFieldProyecto = new javax.swing.JTextField();
        jTextFieldIntegrante = new javax.swing.JTextField();
        jTextFieldEquipo = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaResultados = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jButtonConsultarTodos = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButtonConsultaCODIGO = new javax.swing.JButton();
        jButtonConsultaGERENTE = new javax.swing.JButton();
        jTextFieldFiltro = new javax.swing.JTextField();
        jButtonConsultaIDCLIENTE = new javax.swing.JButton();
        jButtonConsultaIDGERENTE = new javax.swing.JButton();
        jButtonConsultaTIPOCLIENTE = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButtonGenerarProyectos = new javax.swing.JButton();
        jButtonGenerarClientes = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jButtonLeerProyectos = new javax.swing.JButton();
        jButtonLeerClientes = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GESTIÓN DE PROYECTOS");

        bloque.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Registrar");
        jLabel1.setToolTipText("");

        jLabel5.setText("Proyecto");
        jLabel5.setToolTipText("");

        jLabel6.setText("Integrante");
        jLabel6.setToolTipText("");

        jLabel7.setText("Equipo");

        jButtonRegistrar.setText("Registrar");

        jButtonEquipo.setText("Equipo");

        jButtonEstado.setText("Estado");

        javax.swing.GroupLayout bloqueLayout = new javax.swing.GroupLayout(bloque);
        bloque.setLayout(bloqueLayout);
        bloqueLayout.setHorizontalGroup(
            bloqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bloqueLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bloqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bloqueLayout.createSequentialGroup()
                        .addGroup(bloqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(9, 9, 9)
                        .addGroup(bloqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldIntegrante)
                            .addComponent(jTextFieldProyecto)))
                    .addGroup(bloqueLayout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldEquipo, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bloqueLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonEstado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonEquipo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRegistrar)))
                .addContainerGap())
        );
        bloqueLayout.setVerticalGroup(
            bloqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bloqueLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bloqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bloqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldIntegrante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bloqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(bloqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonRegistrar)
                    .addComponent(jButtonEquipo)
                    .addComponent(jButtonEstado))
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Consultar");

        jTextAreaResultados.setColumns(20);
        jTextAreaResultados.setRows(5);
        jScrollPane1.setViewportView(jTextAreaResultados);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel10.setText("Proyecto");

        jButtonConsultarTodos.setText("Todos");
        jButtonConsultarTodos.addActionListener(this::jButtonConsultarTodosActionPerformed);

        jButton3.setText("En curso");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jButton4.setText("Primero");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        jButton5.setText("Ultimo");
        jButton5.addActionListener(this::jButton5ActionPerformed);

        jButtonConsultaCODIGO.setText("Por código");
        jButtonConsultaCODIGO.addActionListener(this::jButtonConsultaCODIGOActionPerformed);

        jButtonConsultaGERENTE.setText("Gerente (código)");
        jButtonConsultaGERENTE.addActionListener(this::jButtonConsultaGERENTEActionPerformed);

        jTextFieldFiltro.addActionListener(this::jTextFieldFiltroActionPerformed);

        jButtonConsultaIDCLIENTE.setText("Proyectos de Cliente (ID)");
        jButtonConsultaIDCLIENTE.addActionListener(this::jButtonConsultaIDCLIENTEActionPerformed);

        jButtonConsultaIDGERENTE.setText("Proyectos de Gerente (ID)");
        jButtonConsultaIDGERENTE.addActionListener(this::jButtonConsultaIDGERENTEActionPerformed);

        jButtonConsultaTIPOCLIENTE.setText("Tipo de Cliente");
        jButtonConsultaTIPOCLIENTE.addActionListener(this::jButtonConsultaTIPOCLIENTEActionPerformed);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonConsultarTodos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonConsultaCODIGO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonConsultaGERENTE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonConsultaIDGERENTE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonConsultaIDCLIENTE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(5, 5, 5)
                .addComponent(jButtonConsultaTIPOCLIENTE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextFieldFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonConsultarTodos)
                        .addComponent(jButton4)
                        .addComponent(jButtonConsultaGERENTE, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonConsultaTIPOCLIENTE))
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton5)
                    .addComponent(jButtonConsultaIDCLIENTE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonConsultaIDGERENTE)
                    .addComponent(jButtonConsultaCODIGO)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Generar archivos");

        jButtonGenerarProyectos.setText("Proyectos");
        jButtonGenerarProyectos.addActionListener(this::jButtonGenerarProyectosActionPerformed);

        jButtonGenerarClientes.setText("Clientes");
        jButtonGenerarClientes.addActionListener(this::jButtonGenerarClientesActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButtonGenerarProyectos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonGenerarClientes)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonGenerarProyectos)
                    .addComponent(jButtonGenerarClientes))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Leer archivos");

        jButtonLeerProyectos.setText("Proyectos");
        jButtonLeerProyectos.addActionListener(this::jButtonLeerProyectosActionPerformed);

        jButtonLeerClientes.setText("Clientes");
        jButtonLeerClientes.addActionListener(this::jButtonLeerClientesActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButtonLeerProyectos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonLeerClientes)))
                .addContainerGap(331, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonLeerProyectos)
                    .addComponent(jButtonLeerClientes))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 780, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bloque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 4, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(bloque, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        jTextAreaResultados.setText(consultarPrimerProyecto(losProyectos));
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jTextAreaResultados.setText(consultarProyectosEnCurso(losProyectos));
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        jTextAreaResultados.setText(consultarUltimoProyecto(losProyectos));
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButtonConsultaCODIGOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultaCODIGOActionPerformed
        if(jTextFieldFiltro.getText().isBlank()){
            JOptionPane.showMessageDialog(null, "Operación inválida, filtro vacío.", "ENTRADA INVÁLIDA", JOptionPane.ERROR_MESSAGE);
        } else{
            jTextAreaResultados.setText(consultarProyectoDadoCodigo(losProyectos, jTextFieldFiltro.getText()));
            jTextFieldFiltro.setText("");
        }
    }//GEN-LAST:event_jButtonConsultaCODIGOActionPerformed

    private void jButtonConsultaGERENTEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultaGERENTEActionPerformed
    String filtroCodigo = jTextFieldFiltro.getText().trim();

    if(filtroCodigo.isEmpty()){
        JOptionPane.showMessageDialog(this, "Operación inválida, filtro vacío.",
                "ENTRADA INVÁLIDA", JOptionPane.ERROR_MESSAGE);
    } else{
        jTextAreaResultados.setText(consultarGerenteProyectoDadoCodigo(losProyectos, filtroCodigo));
    }
    }//GEN-LAST:event_jButtonConsultaGERENTEActionPerformed

    private void jButtonConsultaIDCLIENTEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultaIDCLIENTEActionPerformed
        if(jTextFieldFiltro.getText().isBlank() || jTextFieldFiltro.getText().isBlank()){
            JOptionPane.showMessageDialog(null,"Operación inválida, filtro vacío.", "ENTRADA INVÁLIDA", JOptionPane.ERROR_MESSAGE);
        } else{
                jTextAreaResultados.setText(consultarProyectosDadoNumIDECliente(losProyectos, jTextFieldFiltro.getText()));
                jTextFieldFiltro.setText("");
        }                             
    }//GEN-LAST:event_jButtonConsultaIDCLIENTEActionPerformed

    private void jButtonConsultaIDGERENTEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultaIDGERENTEActionPerformed
        if(jTextFieldFiltro.getText().isBlank() || jTextFieldFiltro.getText().isBlank()){
            JOptionPane.showMessageDialog(null,"Operación inválida, filtro vacío.", "ENTRADA INVÁLIDA", JOptionPane.ERROR_MESSAGE);
        } else{
                jTextAreaResultados.setText(consultarProyectosDadoNumIDEGerente(losProyectos, jTextFieldFiltro.getText()));
                jTextFieldFiltro.setText("");
        }                                    
    }//GEN-LAST:event_jButtonConsultaIDGERENTEActionPerformed

    private void jButtonConsultaTIPOCLIENTEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultaTIPOCLIENTEActionPerformed
        if(jTextFieldFiltro.getText().isBlank() || jTextFieldFiltro.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "¡Operación fallida!\nNo se puede realizar la consulta.\nCategoría: entrada vacía.", "GESTIÓN CLIENTES", JOptionPane.ERROR_MESSAGE);            
        } else{
            jTextAreaResultados.setText(consultarProyectosDadoTipoCliente(losProyectos, jTextFieldFiltro.getText().toUpperCase()));    
            jTextFieldFiltro.setText("");
        }       
    }//GEN-LAST:event_jButtonConsultaTIPOCLIENTEActionPerformed

    private void jTextFieldFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFiltroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFiltroActionPerformed

    private void jButtonConsultarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarTodosActionPerformed
        jTextAreaResultados.setText(consultarTodosProyectos(losProyectos));
    }//GEN-LAST:event_jButtonConsultarTodosActionPerformed

    private void jButtonGenerarProyectosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGenerarProyectosActionPerformed
        generarArchivoDeObjetosProyectos(losProyectos);
    }//GEN-LAST:event_jButtonGenerarProyectosActionPerformed

    private void jButtonGenerarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGenerarClientesActionPerformed
        generarArchivoDeObjetosClientes(losProyectos);
    }//GEN-LAST:event_jButtonGenerarClientesActionPerformed

    private void jButtonLeerProyectosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLeerProyectosActionPerformed
        ArrayList<Proyecto> proyectosLeidos = leerArchivoDeObjetosProyectos();
        if(!proyectosLeidos.isEmpty()){
            losProyectos = proyectosLeidos;
            String resultado = "PROYECTOS LEIDOS DEL ARCHIVO\n";
            for(Proyecto objeto : losProyectos){
                resultado += "\n" + objeto.toString() + "\n";
            }
            jTextAreaResultados.setText(resultado);
        }
    }//GEN-LAST:event_jButtonLeerProyectosActionPerformed

    private void jButtonLeerClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLeerClientesActionPerformed
        ArrayList<Cliente> clientesLeidos = leerArchivoDeObjetosClientes();
        if(!clientesLeidos.isEmpty()){
            losClientes = clientesLeidos;
            String resultado = "CLIENTES LEIDOS DEL ARCHIVO\n";
            for(Cliente objeto : losClientes){
                resultado += "\n" + objeto.toString() + "\n";
            }
            jTextAreaResultados.setText(resultado);
        }
    }//GEN-LAST:event_jButtonLeerClientesActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
    java.util.logging.Logger.getLogger(UsaProyecto.class.getName())
            .log(java.util.logging.Level.SEVERE, null, ex);
}
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new UsaProyecto().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bloque;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButtonConsultaCODIGO;
    private javax.swing.JButton jButtonConsultaGERENTE;
    private javax.swing.JButton jButtonConsultaIDCLIENTE;
    private javax.swing.JButton jButtonConsultaIDGERENTE;
    private javax.swing.JButton jButtonConsultaTIPOCLIENTE;
    private javax.swing.JButton jButtonConsultarTodos;
    private javax.swing.JButton jButtonEquipo;
    private javax.swing.JButton jButtonEstado;
    private javax.swing.JButton jButtonGenerarClientes;
    private javax.swing.JButton jButtonGenerarProyectos;
    private javax.swing.JButton jButtonLeerClientes;
    private javax.swing.JButton jButtonLeerProyectos;
    private javax.swing.JButton jButtonRegistrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaResultados;
    private javax.swing.JTextField jTextFieldEquipo;
    private javax.swing.JTextField jTextFieldFiltro;
    private javax.swing.JTextField jTextFieldIntegrante;
    private javax.swing.JTextField jTextFieldProyecto;
    // End of variables declaration//GEN-END:variables
}
