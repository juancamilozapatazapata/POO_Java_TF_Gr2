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
import javax.swing.JTextArea;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas unitarias con JUnit 4 para las operaciones de UsaProyecto:
 * código del proyecto, registro, consultas generales, consultas con filtro y actualización de estado.
 *
 * Nota. Los datos que piden las ventanas (JOptionPane) los contesta CerradorDeVentanas con las respuestas
 * preparadas en cada prueba. Los botones se presionan invocando su manejador por reflexión.
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

    // ==================== AYUDAS PARA LA REFLEXIÓN Y PARA LAS RESPUESTAS ====================

    @SuppressWarnings("unchecked")
    private ArrayList<Proyecto> campoEstatico(String nombre) throws Exception {
        Field campo = UsaProyecto.class.getDeclaredField(nombre);
        campo.setAccessible(true);
        return (ArrayList<Proyecto>) campo.get(null);
    }

    private JTextArea areaResultados() throws Exception {
        Field campo = UsaProyecto.class.getDeclaredField("jTextAreaResultados");
        campo.setAccessible(true);
        return (JTextArea) campo.get(vista);
    }

    private void presionar(String manejador) throws Exception {
        Method metodo = UsaProyecto.class.getDeclaredMethod(manejador, ActionEvent.class);
        metodo.setAccessible(true);
        metodo.invoke(vista, new Object[]{null});
    }

    /**
     * Respuestas, en el orden en que las pide registrarProyecto, para una empresa válida:
     * 0 título, 1 fecha, 2 unidad, 3 duración, 4 tipo de cliente, 5 id cliente, 6 nombre cliente, 7 ciudad,
     * 8 nombre contacto, 9 cargo contacto, 10 teléfono, 11 correo, 12 id gerente, 13 nombre gerente,
     * 14 título gerente, 15 años de experiencia.
     */
    private String[] respuestasEmpresaValida() {
        return new String[]{"Sistema de gestion de inventarios", "2026-11-01", "meses", "6", "Empresa", "900123", "ACME SAS", "Cali",
            "Ana Ruiz", "Gerente", "3001112222", "ana@acme.com", "55", "Marta Gomez", "Ingeniera", "8"};
    }

    /**
     * Respuestas para una persona natural válida (no se pide contacto):
     * 0 título, 1 fecha, 2 unidad, 3 duración, 4 tipo, 5 id, 6 nombre, 7 ciudad, 8 teléfono, 9 correo,
     * 10 id gerente, 11 nombre gerente, 12 título gerente, 13 años de experiencia.
     */
    private String[] respuestasPersonaValida() {
        return new String[]{"Aplicacion movil para domicilios", "2026-12-05", "semanas", "2", "personanatural", "1144", "Luis Perez", "Bogota",
            "3105556666", "luis@mail.com", "55", "Marta Gomez", "Ingeniera", "8"};
    }

    /** Prepara las respuestas y ejecuta registrarProyecto sobre una lista nueva; devuelve la lista resultante. */
    private ArrayList<Proyecto> registrar(String... respuestas) {
        ArrayList<Proyecto> registrados = new ArrayList<>();
        cerrador.agregarEntradas(respuestas);
        return vista.registrarProyecto(registrados);
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
    public void testRegistrarProyectoConEmpresaValida() {
        ArrayList<Proyecto> registrados = registrar(respuestasEmpresaValida());

        assertEquals(1, registrados.size());
        assertEquals(LocalDate.now().getYear() + "-001", registrados.get(0).getCodigo());
        assertEquals("INICIADO", registrados.get(0).getEstado());
        assertEquals("Sistema de gestion de inventarios", registrados.get(0).getTitulo());
        assertEquals(LocalDate.of(2026, 11, 1), registrados.get(0).getFechaInicio());
        assertTrue(registrados.get(0).getSuCliente() instanceof Empresa);
        assertEquals("Marta Gomez", registrados.get(0).getSuGerente().getNombreCompleto());
        assertEquals(0, cerrador.entradasPendientes()); // se usaron todas las respuestas
    }

    @Test
    public void testRegistrarProyectoConPersonaNatural() {
        ArrayList<Proyecto> registrados = registrar(respuestasPersonaValida());

        assertEquals(1, registrados.size());
        assertTrue(registrados.get(0).getSuCliente() instanceof PersonaNatural);
        assertEquals("1144", registrados.get(0).getSuCliente().getNumeroIdentificacion());
        assertEquals("semanas", registrados.get(0).getUnidadDuracion());
    }

    @Test
    public void testRegistrarDosProyectosGeneraCodigosConsecutivos() {
        ArrayList<Proyecto> registrados = new ArrayList<>();
        cerrador.agregarEntradas(respuestasEmpresaValida());
        vista.registrarProyecto(registrados);
        cerrador.agregarEntradas(respuestasEmpresaValida());
        vista.registrarProyecto(registrados);

        assertEquals(2, registrados.size());
        assertTrue(registrados.get(0).getCodigo().endsWith("-001"));
        assertTrue(registrados.get(1).getCodigo().endsWith("-002"));
    }

    @Test
    public void testRegistrarConTituloCortoVuelveAPedirloYNoRegistraSiSeCancela() {
        ArrayList<Proyecto> registrados = registrar("corto");   // luego no hay más respuestas: se cancela

        assertTrue(registrados.isEmpty());
        assertTrue(cerrador.seMostro("REGISTRAR PROYECTO: título")); // avisó el error
    }

    @Test
    public void testRegistrarConTituloMuyLargoVuelveAPedirloYNoRegistraSiSeCancela() {
        ArrayList<Proyecto> registrados = registrar("x".repeat(101));

        assertTrue(registrados.isEmpty());
        assertTrue(cerrador.seMostro("REGISTRAR PROYECTO: título"));
    }

    @Test
    public void testRegistrarConTituloInvalidoYLuegoValidoSiRegistra() {
        String[] respuestas = respuestasEmpresaValida();
        String[] conErrorAntes = new String[respuestas.length + 1];
        conErrorAntes[0] = "corto";                      // primer intento: inválido
        System.arraycopy(respuestas, 0, conErrorAntes, 1, respuestas.length);

        ArrayList<Proyecto> registrados = registrar(conErrorAntes);

        assertEquals(1, registrados.size());
        assertEquals("Sistema de gestion de inventarios", registrados.get(0).getTitulo());
    }

    @Test
    public void testRegistrarConUnidadInvalidaVuelveAPedirlaYNoRegistraSiSeCancela() {
        ArrayList<Proyecto> registrados = registrar("Sistema de gestion de inventarios", "2026-11-01", "dias");

        assertTrue(registrados.isEmpty());
        assertTrue(cerrador.seMostro("REGISTRAR PROYECTO: unidad de duración"));
    }

    @Test
    public void testRegistrarConDuracionFueraDeRangoNoRegistra() {
        String[][] casos = {{"semanas", "4"}, {"semanas", "0"}, {"años", "4"}, {"meses", "12"}, {"meses", "24"}, {"meses", "36"}};
        for (String[] caso : casos) {
            // después del valor inválido no hay más respuestas: la ventana se vuelve a pedir y se cancela
            ArrayList<Proyecto> registrados = registrar("Sistema de gestion de inventarios", "2026-11-01", caso[0], caso[1]);

            assertTrue("No debía registrar " + caso[1] + " " + caso[0], registrados.isEmpty());
        }
        assertTrue(cerrador.seMostro("REGISTRAR PROYECTO: duración estimada"));
    }

    @Test
    public void testRegistrarConDuracionEnLosLimitesSiRegistra() {
        String[][] casos = {{"semanas", "1"}, {"semanas", "3"}, {"años", "1"}, {"años", "3"}, {"meses", "1"}, {"meses", "11"},
            {"meses", "13"}, {"meses", "23"}, {"meses", "25"}, {"meses", "35"}};
        for (String[] caso : casos) {
            String[] respuestas = respuestasEmpresaValida();
            respuestas[2] = caso[0];
            respuestas[3] = caso[1];

            ArrayList<Proyecto> registrados = registrar(respuestas);

            assertEquals("Debía registrar " + caso[1] + " " + caso[0], 1, registrados.size());
        }
    }

    @Test
    public void testRegistrarConTipoDeClienteInvalidoVuelveAPedirloYNoRegistraSiSeCancela() {
        ArrayList<Proyecto> registrados = registrar("Sistema de gestion de inventarios", "2026-11-01", "meses", "6", "Otro");

        assertTrue(registrados.isEmpty());
        assertTrue(cerrador.seMostro("REGISTRAR PROYECTO: tipo de cliente"));
    }

    @Test
    public void testRegistrarSeCancelaSiElUsuarioCancelaEnCualquierPregunta() {
        String[] completas = respuestasEmpresaValida();
        for (int hasta = 0; hasta < completas.length; hasta++) {
            String[] parciales = new String[hasta];
            System.arraycopy(completas, 0, parciales, 0, hasta);   // las demás preguntas se cancelan

            ArrayList<Proyecto> registrados = registrar(parciales);

            assertTrue("Debía cancelarse al contestar solo " + hasta + " preguntas", registrados.isEmpty());
        }
    }

    /**
     * Devuelve las respuestas válidas con uno o más datos inválidos insertados antes de la posición indicada:
     * la ventana rechaza esos datos y vuelve a pedir el mismo dato hasta recibir el válido.
     */
    private String[] conErrorAntes(String[] validas, int posicion, String... invalidos) {
        String[] resultado = new String[validas.length + invalidos.length];
        System.arraycopy(validas, 0, resultado, 0, posicion);
        System.arraycopy(invalidos, 0, resultado, posicion, invalidos.length);
        System.arraycopy(validas, posicion, resultado, posicion + invalidos.length, validas.length - posicion);
        return resultado;
    }

    @Test
    public void testRegistrarConFechaMalFormadaVuelveAPedirlaYLuegoRegistra() {
        ArrayList<Proyecto> registrados = registrar(conErrorAntes(respuestasEmpresaValida(), 1, "01/11/2026", "2026-13-45", "hoy"));

        assertEquals(1, registrados.size());
        assertEquals(LocalDate.of(2026, 11, 1), registrados.get(0).getFechaInicio());
        assertTrue(cerrador.seMostro("REGISTRAR PROYECTO: fecha de inicio"));
        assertEquals(0, cerrador.entradasPendientes());
    }

    @Test
    public void testRegistrarConFechaMalFormadaYSeCancelaNoRegistra() {
        ArrayList<Proyecto> registrados = registrar("Sistema de gestion de inventarios", "01/11/2026");

        assertTrue(registrados.isEmpty());
        assertTrue(cerrador.seMostro("REGISTRAR PROYECTO: fecha de inicio"));
    }

    @Test
    public void testRegistrarConDuracionNoNumericaVuelveAPedirlaYLuegoRegistra() {
        ArrayList<Proyecto> registrados = registrar(conErrorAntes(respuestasEmpresaValida(), 3, "abc", "", "2.5"));

        assertEquals(1, registrados.size());
        assertEquals(6, registrados.get(0).getDuracionEstimada());
        assertTrue(cerrador.seMostro("REGISTRAR PROYECTO: duración estimada"));
        assertEquals(0, cerrador.entradasPendientes());
    }

    @Test
    public void testRegistrarConDuracionNoNumericaYSeCancelaNoRegistra() {
        ArrayList<Proyecto> registrados = registrar("Sistema de gestion de inventarios", "2026-11-01", "meses", "abc");

        assertTrue(registrados.isEmpty());
    }

    @Test
    public void testRegistrarConExperienciaInvalidaVuelveAPedirlaYLuegoRegistra() {
        // no numérica, negativa y fuera del rango de un byte
        ArrayList<Proyecto> registrados = registrar(conErrorAntes(respuestasEmpresaValida(), 15, "ocho", "-2", "200"));

        assertEquals(1, registrados.size());
        assertEquals(8, registrados.get(0).getSuGerente().getAniosExperiencia());
        assertTrue(cerrador.seMostro("REGISTRAR PROYECTO: años de experiencia"));
        assertEquals(0, cerrador.entradasPendientes());
    }

    @Test
    public void testRegistrarConExperienciaInvalidaYSeCancelaNoRegistra() {
        String[] respuestas = respuestasEmpresaValida();
        respuestas[15] = "ocho";   // después no hay más respuestas: se cancela

        assertTrue(registrar(respuestas).isEmpty());
    }

    @Test
    public void testRegistrarEmpresaConDatosObligatoriosVaciosLosVuelveAPedir() {
        // 5 id cliente, 6 nombre, 7 ciudad, 8 nombre contacto, 9 cargo, 10 teléfono, 11 correo, 12 id gerente, 13 nombre gerente, 14 título
        for (int posicion = 5; posicion <= 14; posicion++) {
            String[] validas = respuestasEmpresaValida();

            ArrayList<Proyecto> registrados = registrar(conErrorAntes(validas, posicion, "", "   "));

            assertEquals("Debía registrar tras pedir de nuevo el dato " + posicion, 1, registrados.size());
            assertEquals("No se usaron todas las respuestas en el dato " + posicion, 0, cerrador.entradasPendientes());
        }
        assertTrue(cerrador.seMostro("REGISTRAR PROYECTO: nombre del contacto"));
    }

    @Test
    public void testRegistrarPersonaConDatosObligatoriosVaciosLosVuelveAPedir() {
        // 5 id cliente, 6 nombre, 7 ciudad, 8 teléfono, 9 correo, 10 id gerente, 11 nombre gerente, 12 título
        for (int posicion = 5; posicion <= 12; posicion++) {
            ArrayList<Proyecto> registrados = registrar(conErrorAntes(respuestasPersonaValida(), posicion, "", "   "));

            assertEquals("Debía registrar tras pedir de nuevo el dato " + posicion, 1, registrados.size());
            assertEquals(0, cerrador.entradasPendientes());
        }
    }

    @Test
    public void testRegistrarConDatoObligatorioVacioYSeCancelaNoRegistra() {
        String[] respuestas = respuestasEmpresaValida();
        respuestas[12] = "";   // id del gerente vacío; después no hay más respuestas

        assertTrue(registrar(respuestas).isEmpty());
        assertTrue(cerrador.seMostro("REGISTRAR PROYECTO: identificación del gerente"));
    }

    @Test
    public void testAlCorregirUnDatoSeConservanLosAnteriores() {
        ArrayList<Proyecto> registrados = registrar(conErrorAntes(respuestasEmpresaValida(), 15, "ocho"));

        assertEquals(1, registrados.size());
        Proyecto registrado = registrados.get(0);
        assertEquals("Sistema de gestion de inventarios", registrado.getTitulo());
        assertEquals("ACME SAS", registrado.getSuCliente().getNombre());
        assertEquals("Marta Gomez", registrado.getSuGerente().getNombreCompleto());
    }

    @Test
    public void testElBotonRegistrarAgregaElProyectoALaLista() throws Exception {
        cerrador.agregarEntradas(respuestasEmpresaValida());

        presionar("jButtonRegistrarActionPerformed");

        assertEquals(1, campoEstatico("losProyectos").size());
        assertEquals("INICIADO", campoEstatico("losProyectos").get(0).getEstado());
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
    public void testActualizarEstadoConListaVacia() {
        String resultado = vista.actualizarEstadoProyectoDadoCodigo(new ArrayList<Proyecto>(), "2026-001");
        assertTrue(resultado.contains("No hay proyectos registrados."));
    }

    @Test
    public void testActualizarEstadoDeUnProyecto() {
        cerrador.agregarEntradas("EN CURSO");

        String resultado = vista.actualizarEstadoProyectoDadoCodigo(lista, "2026-001");

        assertTrue(resultado.contains("Operación exitosa"));
        assertEquals("EN CURSO", p1.getEstado());
    }

    @Test
    public void testActualizarEstadoSoloCambiaElProyectoIndicado() {
        cerrador.agregarEntradas("SUSPENDIDO");

        vista.actualizarEstadoProyectoDadoCodigo(lista, "2026-002");

        assertEquals("SUSPENDIDO", p2.getEstado());
        assertEquals("INICIADO", p1.getEstado());
        assertEquals("EN CURSO", p3.getEstado());
    }

    @Test
    public void testActualizarEstadoAceptaMinusculasYEspacios() {
        cerrador.agregarEntradas("  finalizado ");

        vista.actualizarEstadoProyectoDadoCodigo(lista, "2026-001");

        assertEquals("FINALIZADO", p1.getEstado());
    }

    @Test
    public void testActualizarEstadoAceptaLosSeisEstadosDelEnunciado() {
        String[] estados = {"INICIADO", "EN CURSO", "CANCELADO", "FINALIZADO", "SUSPENDIDO", "APLAZADO"};
        for (String estado : estados) {
            cerrador.agregarEntradas(estado);

            vista.actualizarEstadoProyectoDadoCodigo(lista, "2026-001");

            assertEquals(estado, p1.getEstado());
        }
    }

    @Test
    public void testActualizarEstadoInvalidoVuelveAPedirloYNoCambiaSiSeCancela() {
        cerrador.agregarEntradas("PAUSADO");   // luego no hay más respuestas: se cancela

        String resultado = vista.actualizarEstadoProyectoDadoCodigo(lista, "2026-001");

        assertTrue(resultado.contains("Actualización cancelada."));
        assertEquals("INICIADO", p1.getEstado());
        assertTrue(cerrador.seMostro("ACTUALIZAR ESTADO: nuevo estado"));
    }

    @Test
    public void testActualizarEstadoInvalidoYLuegoValidoSiCambia() {
        cerrador.agregarEntradas("PAUSADO", "APLAZADO");

        vista.actualizarEstadoProyectoDadoCodigo(lista, "2026-001");

        assertEquals("APLAZADO", p1.getEstado());
    }

    @Test
    public void testActualizarEstadoDeCodigoInexistente() {
        cerrador.agregarEntradas("EN CURSO");

        String resultado = vista.actualizarEstadoProyectoDadoCodigo(lista, "2026-999");

        assertTrue(resultado.contains("No hay proyectos con el código indicado."));
        assertEquals("INICIADO", p1.getEstado());
        assertEquals(1, cerrador.entradasPendientes()); // ni siquiera pidió el nuevo estado
    }

    @Test
    public void testElBotonEstadoCambiaElProyecto() throws Exception {
        campoEstatico("losProyectos").add(p1);
        cerrador.agregarEntradas("2026-001", "CANCELADO");

        presionar("jButtonEstadoActionPerformed");

        assertEquals("CANCELADO", p1.getEstado());
    }

    @Test
    public void testElBotonEstadoConCodigoVacioNoCambiaNada() throws Exception {
        campoEstatico("losProyectos").add(p1);
        cerrador.agregarEntradas("   ", "CANCELADO");

        presionar("jButtonEstadoActionPerformed");

        assertEquals("INICIADO", p1.getEstado());
        assertTrue(cerrador.seMostro("ENTRADA INVÁLIDA"));
    }

    // ==================== BOTÓN EQUIPO (MENÚ) ====================

    @Test
    public void testElBotonEquipoRegistraUnIntegrante() throws Exception {
        campoEstatico("losProyectos").add(p1);
        cerrador.agregarEntradas("1", "2026-001", "10", "Pedro Soto", "Ingeniero", "6");

        presionar("jButtonEquipoActionPerformed");

        assertEquals(1, p1.getSuEquipo().size());
        assertEquals("Pedro Soto", p1.getSuEquipo().get(0).getNombreCompleto());
    }

    @Test
    public void testElBotonEquipoRegistraElEquipo() throws Exception {
        campoEstatico("losProyectos").add(p1);
        // opción 2, código, cantidad 2 y los datos de dos integrantes; la pregunta "otro adicional" se cierra como "no"
        cerrador.agregarEntradas("2", "2026-001", "2", "10", "Pedro Soto", "Ingeniero", "6", "11", "Sara Mora", "Disenadora", "4");

        presionar("jButtonEquipoActionPerformed");

        assertEquals(2, p1.getSuEquipo().size());
    }

    @Test
    public void testElBotonEquipoConsultaElEquipo() throws Exception {
        campoEstatico("losProyectos").add(p1);
        p1.agregarIntegrante(new Empleado("10", "Pedro Soto", "Ingeniero", (byte) 6));
        cerrador.agregarEntradas("3", "2026-001");

        presionar("jButtonEquipoActionPerformed");

        assertTrue(areaResultados().getText().contains("Pedro Soto"));
        assertTrue(areaResultados().getText().contains("INTEGRANTES DEL EQUIPO (1)"));
    }

    @Test
    public void testElBotonEquipoEliminaUnIntegrante() throws Exception {
        campoEstatico("losProyectos").add(p1);
        p1.agregarIntegrante(new Empleado("10", "Pedro Soto", "Ingeniero", (byte) 6));
        p1.agregarIntegrante(new Empleado("11", "Sara Mora", "Disenadora", (byte) 4));
        cerrador.agregarEntradas("4", "2026-001", "10");

        presionar("jButtonEquipoActionPerformed");

        assertEquals(1, p1.getSuEquipo().size());
        assertEquals("11", p1.getSuEquipo().get(0).getNumeroIdentificacion());
    }

    @Test
    public void testElBotonEquipoConOpcionInvalidaNoHaceNada() throws Exception {
        campoEstatico("losProyectos").add(p1);
        cerrador.agregarEntradas("9", "2026-001");

        presionar("jButtonEquipoActionPerformed");

        assertNull(p1.getSuEquipo());
        assertTrue(cerrador.seMostro("EQUIPO DEL PROYECTO"));
        assertEquals(1, cerrador.entradasPendientes()); // no llegó a pedir el código
    }

    @Test
    public void testElBotonEquipoConOpcionNoNumericaNoHaceNada() throws Exception {
        campoEstatico("losProyectos").add(p1);
        cerrador.agregarEntradas("abc");

        presionar("jButtonEquipoActionPerformed");

        assertNull(p1.getSuEquipo());
    }
}
