package Pruebas;

import Modelo.Cliente;
import Modelo.Contacto;
import Modelo.Empleado;
import Modelo.Empresa;
import Modelo.PersonaNatural;
import Modelo.Proyecto;
import Vista.UsaProyecto;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JTextField;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas unitarias con JUnit 4 para las operaciones de UsaProyecto:
 * código del proyecto, registro, consultas generales, consultas con filtro y actualización de estado.
 *
 * Nota. Los campos de texto de la ventana son privados; las pruebas los llenan por reflexión
 * y presionan el botón (invocan su manejador) como lo haría una persona.
 */
public class PruebasOperacionesProyectoTest {

    private UsaProyecto vista;
    private CerradorDeVentanas cerrador;
    private ArrayList<Proyecto> lista;
    private Cliente empresa;
    private Cliente persona;
    private Empleado gerente1;
    private Empleado gerente2;
    private Proyecto p1;
    private Proyecto p2;
    private Proyecto p3;

    /**
     * Se ejecuta ANTES de cada prueba: crea la ventana, cierra los mensajes automáticamente
     * y prepara tres proyectos: p1 (empresa, gerente 55, INICIADO), p2 (persona, gerente 66, EN CURSO)
     * y p3 (persona, gerente 55, EN CURSO).
     */
    @Before
    public void setUp() throws Exception {
        cerrador = new CerradorDeVentanas();
        cerrador.iniciar();
        vista = new UsaProyecto();
        campoEstatico("losProyectos").clear();

        gerente1 = new Empleado("55", "Marta Gomez", "Ingeniera", (byte) 8);
        gerente2 = new Empleado("66", "Pedro Ruiz", "Arquitecto", (byte) 12);
        empresa = new Empresa(new Contacto("Ana Ruiz", "Gerente", "3001112222", "ana@acme.com"),
                "900123", "ACME SAS", "Cali");
        persona = new PersonaNatural("1144", "Luis Perez", "Bogota", "3105556666", "luis@mail.com");

        p1 = new Proyecto("2026-001", "Sistema web de inventarios", LocalDate.of(2026, 11, 1),
                "meses", (byte) 6, empresa, gerente1, "INICIADO");
        p2 = new Proyecto("2026-002", "Aplicacion movil de domicilios", LocalDate.of(2026, 12, 5),
                "semanas", (byte) 2, persona, gerente2, "EN CURSO");
        p3 = new Proyecto("2026-003", "Intranet corporativa de la empresa", LocalDate.of(2027, 1, 10),
                "meses", (byte) 3, persona, gerente1, "EN CURSO");

        lista = new ArrayList<>();
        lista.add(p1);
        lista.add(p2);
        lista.add(p3);
    }

    /**
     * Se ejecuta DESPUÉS de cada prueba: libera la ventana y deja vacía la lista de la ventana.
     */
    @After
    public void tearDown() throws Exception {
        campoEstatico("losProyectos").clear();
        cerrador.detener();
        vista.dispose();
    }

    // ==================== AYUDAS PARA LA REFLEXIÓN ====================

    @SuppressWarnings("unchecked")
    private ArrayList<Proyecto> campoEstatico(String nombre) throws Exception {
        Field campo = UsaProyecto.class.getDeclaredField(nombre);
        campo.setAccessible(true);
        return (ArrayList<Proyecto>) campo.get(null);
    }

    private JTextField campoTexto(String nombre) throws Exception {
        Field campo = UsaProyecto.class.getDeclaredField(nombre);
        campo.setAccessible(true);
        return (JTextField) campo.get(vista);
    }

    private void escribir(String nombreCampo, String texto) throws Exception {
        campoTexto(nombreCampo).setText(texto);
    }

    private void presionar(String manejador) throws Exception {
        Method metodo = UsaProyecto.class.getDeclaredMethod(manejador, ActionEvent.class);
        metodo.setAccessible(true);
        metodo.invoke(vista, new Object[]{null});
    }

    /** Llena la ventana de registro con una empresa válida. */
    private void llenarRegistroValido() throws Exception {
        escribir("jTextFieldTitulo", "Sistema de gestion de inventarios");
        escribir("jTextFieldFechaInicio", "2026-11-01");
        escribir("jTextFieldUnidad", "meses");
        escribir("jTextFieldDuracion", "6");
        escribir("jTextFieldTipoCliente", "Empresa");
        escribir("jTextFieldIdCliente", "900123");
        escribir("jTextFieldNombreCliente", "ACME SAS");
        escribir("jTextFieldCiudadCliente", "Cali");
        escribir("jTextFieldTelefono", "3001112222");
        escribir("jTextFieldCorreo", "ana@acme.com");
        escribir("jTextFieldNombreContacto", "Ana Ruiz");
        escribir("jTextFieldCargoContacto", "Gerente");
        escribir("jTextFieldIdGerente", "55");
        escribir("jTextFieldNombreGerente", "Marta Gomez");
        escribir("jTextFieldTituloGerente", "Ingeniera");
        escribir("jTextFieldExperienciaGerente", "8");
    }

    // ==================== GENERAR CÓDIGO ====================

    @Test
    public void testGenerarCodigoConListaVacia() {
        String esperado = LocalDate.now().getYear() + "-001";
        assertEquals(esperado, vista.generarCodigoProyecto(new ArrayList<Proyecto>()));
    }

    @Test
    public void testGenerarCodigoContinuaElConsecutivoDelAnio() {
        int anio = LocalDate.now().getYear();
        ArrayList<Proyecto> delAnio = new ArrayList<>();
        delAnio.add(new Proyecto(anio + "-001", "Proyecto uno de este anio", LocalDate.now(), "meses", (byte) 1, empresa, gerente1, "INICIADO"));
        delAnio.add(new Proyecto(anio + "-002", "Proyecto dos de este anio", LocalDate.now(), "meses", (byte) 1, empresa, gerente1, "INICIADO"));

        assertEquals(anio + "-003", vista.generarCodigoProyecto(delAnio));
    }

    @Test
    public void testGenerarCodigoReiniciaElConsecutivoCadaAnio() {
        ArrayList<Proyecto> deOtroAnio = new ArrayList<>();
        deOtroAnio.add(new Proyecto("1999-001", "Proyecto antiguo de otro anio", LocalDate.of(1999, 1, 1), "meses", (byte) 1, empresa, gerente1, "FINALIZADO"));
        deOtroAnio.add(new Proyecto("1999-002", "Otro proyecto antiguo del mismo", LocalDate.of(1999, 2, 1), "meses", (byte) 1, empresa, gerente1, "FINALIZADO"));

        assertEquals(LocalDate.now().getYear() + "-001", vista.generarCodigoProyecto(deOtroAnio));
    }

    @Test
    public void testGenerarCodigoCompletaConCeros() {
        int anio = LocalDate.now().getYear();
        ArrayList<Proyecto> nueve = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            nueve.add(new Proyecto(anio + "-00" + i, "Proyecto numero " + i + " de este anio", LocalDate.now(), "meses", (byte) 1, empresa, gerente1, "INICIADO"));
        }
        assertEquals(anio + "-010", vista.generarCodigoProyecto(nueve));
    }

    // ==================== REGISTRAR PROYECTO ====================

    @Test
    public void testRegistrarProyectoConEmpresaValida() throws Exception {
        llenarRegistroValido();

        presionar("jButtonGuardarProyectoActionPerformed");

        ArrayList<Proyecto> registrados = campoEstatico("losProyectos");
        assertEquals(1, registrados.size());
        assertEquals(LocalDate.now().getYear() + "-001", registrados.get(0).getCodigo());
        assertEquals("INICIADO", registrados.get(0).getEstado());
        assertTrue(registrados.get(0).getSuCliente() instanceof Empresa);
        assertEquals("Marta Gomez", registrados.get(0).getSuGerente().getNombreCompleto());
        assertEquals("", campoTexto("jTextFieldTitulo").getText()); // la ventana quedó limpia
    }

    @Test
    public void testRegistrarProyectoConPersonaNatural() throws Exception {
        llenarRegistroValido();
        escribir("jTextFieldTipoCliente", "personanatural");
        escribir("jTextFieldIdCliente", "1144");
        escribir("jTextFieldNombreCliente", "Luis Perez");
        escribir("jTextFieldNombreContacto", "");
        escribir("jTextFieldCargoContacto", "");

        presionar("jButtonGuardarProyectoActionPerformed");

        assertEquals(1, campoEstatico("losProyectos").size());
        assertTrue(campoEstatico("losProyectos").get(0).getSuCliente() instanceof PersonaNatural);
    }

    @Test
    public void testRegistrarDosProyectosGeneraCodigosConsecutivos() throws Exception {
        llenarRegistroValido();
        presionar("jButtonGuardarProyectoActionPerformed");
        llenarRegistroValido();
        presionar("jButtonGuardarProyectoActionPerformed");

        ArrayList<Proyecto> registrados = campoEstatico("losProyectos");
        assertEquals(2, registrados.size());
        assertTrue(registrados.get(0).getCodigo().endsWith("-001"));
        assertTrue(registrados.get(1).getCodigo().endsWith("-002"));
    }

    @Test
    public void testRegistrarConTituloCortoNoRegistra() throws Exception {
        llenarRegistroValido();
        escribir("jTextFieldTitulo", "corto");

        presionar("jButtonGuardarProyectoActionPerformed");

        assertTrue(campoEstatico("losProyectos").isEmpty());
    }

    @Test
    public void testRegistrarConTituloMuyLargoNoRegistra() throws Exception {
        llenarRegistroValido();
        escribir("jTextFieldTitulo", "x".repeat(101));

        presionar("jButtonGuardarProyectoActionPerformed");

        assertTrue(campoEstatico("losProyectos").isEmpty());
    }

    @Test
    public void testRegistrarConFechaMalFormadaNoRegistra() throws Exception {
        llenarRegistroValido();
        escribir("jTextFieldFechaInicio", "01/11/2026");

        presionar("jButtonGuardarProyectoActionPerformed");

        assertTrue(campoEstatico("losProyectos").isEmpty());
    }

    @Test
    public void testRegistrarConUnidadInvalidaNoRegistra() throws Exception {
        llenarRegistroValido();
        escribir("jTextFieldUnidad", "dias");

        presionar("jButtonGuardarProyectoActionPerformed");

        assertTrue(campoEstatico("losProyectos").isEmpty());
    }

    @Test
    public void testRegistrarConDuracionFueraDeRangoNoRegistra() throws Exception {
        String[][] casos = {{"semanas", "4"}, {"semanas", "0"}, {"años", "4"}, {"meses", "12"}, {"meses", "24"}, {"meses", "36"}};
        for (String[] caso : casos) {
            llenarRegistroValido();
            escribir("jTextFieldUnidad", caso[0]);
            escribir("jTextFieldDuracion", caso[1]);

            presionar("jButtonGuardarProyectoActionPerformed");

            assertTrue("No debía registrar " + caso[1] + " " + caso[0], campoEstatico("losProyectos").isEmpty());
        }
    }

    @Test
    public void testRegistrarConDuracionEnLosLimitesSiRegistra() throws Exception {
        String[][] casos = {{"semanas", "1"}, {"semanas", "3"}, {"años", "1"}, {"años", "3"}, {"meses", "1"}, {"meses", "11"}, {"meses", "13"}, {"meses", "23"}, {"meses", "25"}, {"meses", "35"}};
        for (String[] caso : casos) {
            campoEstatico("losProyectos").clear();
            llenarRegistroValido();
            escribir("jTextFieldUnidad", caso[0]);
            escribir("jTextFieldDuracion", caso[1]);

            presionar("jButtonGuardarProyectoActionPerformed");

            assertEquals("Debía registrar " + caso[1] + " " + caso[0], 1, campoEstatico("losProyectos").size());
        }
    }

    @Test
    public void testRegistrarConDuracionNoNumericaNoRegistra() throws Exception {
        llenarRegistroValido();
        escribir("jTextFieldDuracion", "abc");

        presionar("jButtonGuardarProyectoActionPerformed");

        assertTrue(campoEstatico("losProyectos").isEmpty());
    }

    @Test
    public void testRegistrarConTipoDeClienteInvalidoNoRegistra() throws Exception {
        llenarRegistroValido();
        escribir("jTextFieldTipoCliente", "Otro");

        presionar("jButtonGuardarProyectoActionPerformed");

        assertTrue(campoEstatico("losProyectos").isEmpty());
    }

    @Test
    public void testRegistrarConExperienciaNoNumericaNoRegistra() throws Exception {
        llenarRegistroValido();
        escribir("jTextFieldExperienciaGerente", "ocho");

        presionar("jButtonGuardarProyectoActionPerformed");

        assertTrue(campoEstatico("losProyectos").isEmpty());
    }

    @Test
    public void testDespuesDeUnErrorSePuedeRegistrarDeNuevo() throws Exception {
        llenarRegistroValido();
        escribir("jTextFieldTitulo", "corto");
        presionar("jButtonGuardarProyectoActionPerformed");
        assertTrue(campoEstatico("losProyectos").isEmpty());

        escribir("jTextFieldTitulo", "Sistema de gestion de inventarios");
        presionar("jButtonGuardarProyectoActionPerformed");

        assertEquals(1, campoEstatico("losProyectos").size());
    }

    // ==================== CONSULTAS GENERALES ====================

    @Test
    public void testConsultarTodosConListaVacia() {
        String resultado = vista.consultarTodosProyectos(new ArrayList<Proyecto>());
        assertTrue(resultado.contains("No hay proyectos registrados."));
    }

    @Test
    public void testConsultarTodosMuestraCadaProyectoConSuFechaDeFinalizacion() {
        String resultado = vista.consultarTodosProyectos(lista);

        assertTrue(resultado.contains("2026-001"));
        assertTrue(resultado.contains("2026-002"));
        assertTrue(resultado.contains("2026-003"));
        assertTrue(resultado.contains("2027-05-01")); // fin de p1
        assertTrue(resultado.contains("2026-12-19")); // fin de p2
    }

    @Test
    public void testConsultarEnCursoConListaVacia() {
        String resultado = vista.consultarProyectosEnCurso(new ArrayList<Proyecto>());
        assertTrue(resultado.contains("No hay proyectos registrados."));
    }

    @Test
    public void testConsultarEnCursoMuestraSoloLosQueEstanEnCurso() {
        String resultado = vista.consultarProyectosEnCurso(lista);

        assertTrue(resultado.contains("2026-002"));
        assertTrue(resultado.contains("2026-003"));
        assertFalse(resultado.contains("2026-001"));
    }

    @Test
    public void testConsultarEnCursoSinNingunoEnCurso() {
        p2.setEstado("FINALIZADO");
        p3.setEstado("CANCELADO");

        String resultado = vista.consultarProyectosEnCurso(lista);

        assertTrue(resultado.contains("No hay proyectos en curso."));
    }

    @Test
    public void testConsultarPrimerProyecto() {
        String resultado = vista.consultarPrimerProyecto(lista);

        assertTrue(resultado.contains("2026-001"));
        assertFalse(resultado.contains("2026-002"));
        assertFalse(resultado.contains("2026-003"));
    }

    @Test
    public void testConsultarPrimerProyectoConListaVacia() {
        assertTrue(vista.consultarPrimerProyecto(new ArrayList<Proyecto>()).contains("No hay proyectos registrados."));
    }

    @Test
    public void testConsultarUltimoProyecto() {
        String resultado = vista.consultarUltimoProyecto(lista);

        assertTrue(resultado.contains("2026-003"));
        assertFalse(resultado.contains("2026-001"));
        assertFalse(resultado.contains("2026-002"));
    }

    @Test
    public void testConsultarUltimoProyectoConListaVacia() {
        assertTrue(vista.consultarUltimoProyecto(new ArrayList<Proyecto>()).contains("No hay proyectos registrados."));
    }

    @Test
    public void testConsultarPrimeroYUltimoConUnSoloProyecto() {
        ArrayList<Proyecto> unico = new ArrayList<>();
        unico.add(p2);

        assertTrue(vista.consultarPrimerProyecto(unico).contains("2026-002"));
        assertTrue(vista.consultarUltimoProyecto(unico).contains("2026-002"));
    }

    @Test
    public void testConsultarProyectoDadoCodigoExistente() {
        String resultado = vista.consultarProyectoDadoCodigo(lista, "2026-002");

        assertTrue(resultado.contains("2026-002"));
        assertFalse(resultado.contains("2026-001"));
        assertFalse(resultado.contains("2026-003"));
    }

    @Test
    public void testConsultarProyectoDadoCodigoInexistente() {
        String resultado = vista.consultarProyectoDadoCodigo(lista, "2026-999");
        assertTrue(resultado.contains("No hay proyectos con el código indicado."));
    }

    @Test
    public void testConsultarProyectoDadoCodigoConListaVacia() {
        String resultado = vista.consultarProyectoDadoCodigo(new ArrayList<Proyecto>(), "2026-001");
        assertTrue(resultado.contains("No hay proyectos registrados."));
    }

    // ==================== CONSULTAS CON FILTRO (CLIENTES Y GERENTES) ====================

    @Test
    public void testConsultarGerenteDadoCodigo() {
        String resultado = vista.consultarGerenteProyectoDadoCodigo(lista, "2026-002");

        assertTrue(resultado.contains("gerente="));
        assertTrue(resultado.contains("Pedro Ruiz"));
        assertFalse(resultado.contains("Marta Gomez"));
    }

    @Test
    public void testConsultarGerenteDadoCodigoInexistenteYListaVacia() {
        assertTrue(vista.consultarGerenteProyectoDadoCodigo(lista, "9999").contains("No hay gerente con el código indicado."));
        assertTrue(vista.consultarGerenteProyectoDadoCodigo(new ArrayList<Proyecto>(), "2026-001").contains("No hay proyectos registrados."));
    }

    @Test
    public void testConsultarProyectosDadoIdDeGerente() {
        String resultado = vista.consultarProyectosDadoNumIDEGerente(lista, "55");

        assertTrue(resultado.contains("2026-001"));
        assertTrue(resultado.contains("2026-003"));
        assertFalse(resultado.contains("2026-002"));
    }

    @Test
    public void testConsultarProyectosDadoIdDeGerenteInexistenteYListaVacia() {
        assertTrue(vista.consultarProyectosDadoNumIDEGerente(lista, "999").contains("No hay gerentes con el IDE indicado."));
        assertTrue(vista.consultarProyectosDadoNumIDEGerente(new ArrayList<Proyecto>(), "55").contains("No hay gerentes registrados."));
    }

    @Test
    public void testConsultaPorGerenteUsaLaListaQueRecibe() {
        ArrayList<Proyecto> soloP2 = new ArrayList<>();
        soloP2.add(p2);

        assertTrue(vista.consultarProyectosDadoNumIDEGerente(soloP2, "55").contains("No hay gerentes con el IDE indicado."));
    }

    @Test
    public void testConsultarProyectosDadoIdDeCliente() {
        String resultado = vista.consultarProyectosDadoNumIDECliente(lista, "1144");

        assertTrue(resultado.contains("2026-002"));
        assertTrue(resultado.contains("2026-003"));
        assertFalse(resultado.contains("2026-001"));
        assertTrue(resultado.contains("IDE CLIENTE"));
    }

    @Test
    public void testConsultarProyectosDadoIdDeClienteInexistenteYListaVacia() {
        assertTrue(vista.consultarProyectosDadoNumIDECliente(lista, "000").contains("No hay clientes con el IDE indicado."));
        assertTrue(vista.consultarProyectosDadoNumIDECliente(new ArrayList<Proyecto>(), "1144").contains("No hay clientes registrados."));
    }

    @Test
    public void testConsultarProyectosDadoTipoClienteEmpresa() {
        String resultado = vista.consultarProyectosDadoTipoCliente(lista, "EMPRESA");

        assertTrue(resultado.contains("2026-001"));
        assertFalse(resultado.contains("2026-002"));
        assertFalse(resultado.contains("No hay proyectos con el tipo de cliente indicado."));
    }

    @Test
    public void testConsultarProyectosDadoTipoClientePersonaNatural() {
        String resultado = vista.consultarProyectosDadoTipoCliente(lista, "PERSONANATURAL");

        assertTrue(resultado.contains("2026-002"));
        assertTrue(resultado.contains("2026-003"));
        assertFalse(resultado.contains("2026-001"));
        assertFalse(resultado.contains("No hay proyectos con el tipo de cliente indicado."));
    }

    @Test
    public void testConsultarProyectosDadoTipoClienteSinCoincidenciasInvalidoYListaVacia() {
        ArrayList<Proyecto> soloEmpresas = new ArrayList<>();
        soloEmpresas.add(p1);

        assertTrue(vista.consultarProyectosDadoTipoCliente(soloEmpresas, "PERSONANATURAL").contains("No hay proyectos con el tipo de cliente indicado."));
        assertTrue(vista.consultarProyectosDadoTipoCliente(lista, "OTRO").contains("Tipo de cliente inválido"));
        assertTrue(vista.consultarProyectosDadoTipoCliente(new ArrayList<Proyecto>(), "EMPRESA").contains("lista de proyectos vacía"));
    }

    // ==================== ACTUALIZAR ESTADO ====================

    @Test
    public void testActualizarEstadoConListaVacia() throws Exception {
        escribir("jTextFieldNuevoEstado", "EN CURSO");
        String resultado = vista.actualizarEstadoProyectoDadoCodigo(new ArrayList<Proyecto>(), "2026-001");
        assertTrue(resultado.contains("No hay proyectos registrados."));
    }

    @Test
    public void testActualizarEstadoDeUnProyecto() throws Exception {
        escribir("jTextFieldNuevoEstado", "EN CURSO");

        String resultado = vista.actualizarEstadoProyectoDadoCodigo(lista, "2026-001");

        assertTrue(resultado.contains("Operación exitosa"));
        assertEquals("EN CURSO", p1.getEstado());
        assertEquals("EN CURSO", p2.getEstado()); // no cambió: ya estaba así
        assertEquals("EN CURSO", p3.getEstado());
    }

    @Test
    public void testActualizarEstadoSoloCambiaElProyectoIndicado() throws Exception {
        escribir("jTextFieldNuevoEstado", "SUSPENDIDO");

        vista.actualizarEstadoProyectoDadoCodigo(lista, "2026-002");

        assertEquals("SUSPENDIDO", p2.getEstado());
        assertEquals("INICIADO", p1.getEstado());
        assertEquals("EN CURSO", p3.getEstado());
    }

    @Test
    public void testActualizarEstadoAceptaMinusculasYEspacios() throws Exception {
        escribir("jTextFieldNuevoEstado", "  finalizado ");

        vista.actualizarEstadoProyectoDadoCodigo(lista, "2026-001");

        assertEquals("FINALIZADO", p1.getEstado());
    }

    @Test
    public void testActualizarEstadoAceptaLosSeisEstadosDelEnunciado() throws Exception {
        String[] estados = {"INICIADO", "EN CURSO", "CANCELADO", "FINALIZADO", "SUSPENDIDO", "APLAZADO"};
        for (String estado : estados) {
            escribir("jTextFieldNuevoEstado", estado);

            vista.actualizarEstadoProyectoDadoCodigo(lista, "2026-001");

            assertEquals(estado, p1.getEstado());
        }
    }

    @Test
    public void testActualizarEstadoInvalidoNoCambiaNada() throws Exception {
        escribir("jTextFieldNuevoEstado", "PAUSADO");

        String resultado = vista.actualizarEstadoProyectoDadoCodigo(lista, "2026-001");

        assertTrue(resultado.contains("Estado inválido"));
        assertEquals("INICIADO", p1.getEstado());
    }

    @Test
    public void testActualizarEstadoDeCodigoInexistente() throws Exception {
        escribir("jTextFieldNuevoEstado", "EN CURSO");

        String resultado = vista.actualizarEstadoProyectoDadoCodigo(lista, "2026-999");

        assertTrue(resultado.contains("No hay proyectos con el código indicado."));
        assertEquals("INICIADO", p1.getEstado());
    }

    @Test
    public void testElBotonActualizarEstadoConCamposVaciosNoCambiaNada() throws Exception {
        campoEstatico("losProyectos").add(p1);
        escribir("jTextFieldCodigoEstado", "");
        escribir("jTextFieldNuevoEstado", "EN CURSO");

        presionar("jButtonActualizarEstadoActionPerformed");

        assertEquals("INICIADO", p1.getEstado());
        assertTrue(cerrador.seMostro("ENTRADA INVÁLIDA"));
    }

    @Test
    public void testElBotonActualizarEstadoCambiaElProyecto() throws Exception {
        campoEstatico("losProyectos").add(p1);
        escribir("jTextFieldCodigoEstado", "2026-001");
        escribir("jTextFieldNuevoEstado", "CANCELADO");

        presionar("jButtonActualizarEstadoActionPerformed");

        assertEquals("CANCELADO", p1.getEstado());
        assertEquals("", campoTexto("jTextFieldNuevoEstado").getText()); // la ventana quedó limpia
    }
}
