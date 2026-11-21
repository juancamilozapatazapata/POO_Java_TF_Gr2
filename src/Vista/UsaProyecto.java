/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

import Modelo.Cliente;
import Modelo.Empleado;
import Modelo.Proyecto;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import Modelo.Empresa;
import Modelo.PersonaNatural;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * 
 *
 * @author Juan D, Gabriela Imbachi
 */
public class UsaProyecto extends javax.swing.JFrame {
    
    /*
     * Definir y crear el contenedor (colección ArrayList)
     */
    private static ArrayList<Proyecto> losProyectos = new ArrayList<>();
    private static ArrayList<Cliente> losClientes = new ArrayList<>();

    
    /***Constructor***/
    
    public UsaProyecto() throws HeadlessException{
        initComponents();
        configurarBotonesEquipo();
    }

    /**
     * Conecta los botones del panel "Leer" (jButton6..jButton9) con las
     * operaciones del Punto 3 que me corresponden (gabriela Imbachi). .
     */
    private void configurarBotonesEquipo() {
        jButton6.setText("Consultar equipo");       // Opción 7
        jButton7.setText("Registrar integrante");   // Opción 10
        jButton8.setText("Registrar equipo");       // Opción 11
        jButton9.setText("Eliminar integrante");    // Opción 12

        jButton6.addActionListener(e -> ejecutarOperacionEquipo(7));
        jButton7.addActionListener(e -> ejecutarOperacionEquipo(10));
        jButton8.addActionListener(e -> ejecutarOperacionEquipo(11));
        jButton9.addActionListener(e -> ejecutarOperacionEquipo(12));
        pack();
    }

    /**
     * Valida el filtro (código del proyecto) y ejecuta la opción indicada,
     * mostrando el resultado en jTextAreaResultados.
     */
    private void ejecutarOperacionEquipo(int opcion) {
        String codigo = jTextFieldFiltro.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Escriba el código del proyecto en la caja de filtro.",
                    "ENTRADA INVÁLIDA", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String resultado;
        switch (opcion) {
            case 7:  resultado = consultarEquipoProyectoDadoCodigo(losProyectos, codigo); break;
            case 10: resultado = registrarIntegranteProyectoDadoCodigo(losProyectos, codigo); break;
            case 11: resultado = registrarEquipoProyectoDadoCodigo(losProyectos, codigo); break;
            case 12: resultado = eliminarIntegranteProyectoDadoCodigo(losProyectos, codigo); break;
            default: resultado = "Opción no válida.";
        }
        jTextAreaResultados.setText(resultado);
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
                resultado += ", Proyecto=" + objeto.getTitulo();
                resultado += ", desempeño=" + objeto.getSuGerente(); 
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
        if(losProyectos.isEmpty()) {
            resultado += "No hay gerentes registrados.";
            return resultado;
        }    
        boolean gerenteExiste = false;   
        for(Proyecto objeto : losProyectos){
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
        String resultado = "CONSULTA DE PROYECTOS DADO IDE GERENTE " + numIDECliente + "\n";
        if(losProyectos.isEmpty()) {
            resultado += "No hay clientes registrados.";
            return resultado;
        }    
        boolean clienteExiste = false;   
        for(Proyecto objeto : losProyectos){
            if(objeto.getSuCliente().getNumeroIdentificacion().equalsIgnoreCase(numIDECliente)){ 
                clienteExiste = true;                
                resultado += "\n" + objeto.toString();
                resultado += "\n  fecha de finalización= " + objeto.determinarFechaFinalizacion();
                resultado += "\n";
            }
        }
        if(!clienteExiste) {
            resultado += "No hay gerentes con el IDE indicado.";
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
                                              

   
    
    // =========================================================================
    // MÉTODOS DEL PUNTO 3: EMPLEADO Y OPERACIONES DEL EQUIPO DE CADA PROYECTO
    // =========================================================================

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
        jButtonConsultaGERENTE = new javax.swing.JButton();
        jTextFieldFiltro = new javax.swing.JTextField();
        jButtonConsultaIDCLIENTE = new javax.swing.JButton();
        jButtonConsultaIDGERENTE = new javax.swing.JButton();
        jButtonConsultaTIPOCLIENTE = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();

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
        jButtonRegistrar.addActionListener(this::jButtonRegistrarActionPerformed);

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
                .addComponent(jButtonRegistrar)
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

        jButton3.setText("En curso");

        jButton4.setText("Primero");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        jButton5.setText("Ultimo");

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
                    .addComponent(jButtonConsultarTodos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jButtonConsultaIDGERENTE))
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
        jLabel2.setText("Generar");

        jLabel8.setText("jLabel8");

        jLabel9.setText("jLabel9");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(71, 71, 71)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Leer");

        jButton6.setText("jButton6");

        jButton7.setText("jButton7");

        jButton8.setText("jButton8");

        jButton9.setText("jButton9");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton9))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton8)))
                .addContainerGap(331, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton9))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 775, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bloque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 84, Short.MAX_VALUE)))
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
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

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

    private void jButtonRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistrarActionPerformed
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonRegistrarActionPerformed

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
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButtonConsultaGERENTE;
    private javax.swing.JButton jButtonConsultaIDCLIENTE;
    private javax.swing.JButton jButtonConsultaIDGERENTE;
    private javax.swing.JButton jButtonConsultaTIPOCLIENTE;
    private javax.swing.JButton jButtonConsultarTodos;
    private javax.swing.JButton jButtonRegistrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
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

