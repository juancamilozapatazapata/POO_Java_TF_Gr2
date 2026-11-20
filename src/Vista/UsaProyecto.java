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

    
    /*****************************************************/
    /*Constructor*/

    
 /************************************/   
    public UsaProyecto() throws HeadlessException{
        initComponents();
    }

    public UsaProyecto(GraphicsConfiguration gc) {
        super(gc);
    }

    public UsaProyecto(String title) throws HeadlessException {
        super(title);
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
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        jLabel4.setText("Leer");

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
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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
    private javax.swing.JButton jButtonConsultaGERENTE;
    private javax.swing.JButton jButtonConsultaIDCLIENTE;
    private javax.swing.JButton jButtonConsultaIDGERENTE;
    private javax.swing.JButton jButtonConsultaTIPOCLIENTE;
    private javax.swing.JButton jButtonConsultarTodos;
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
