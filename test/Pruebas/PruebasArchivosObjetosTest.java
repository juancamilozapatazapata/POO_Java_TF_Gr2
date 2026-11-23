package Pruebas;

import Modelo.Cliente;
import Modelo.Contacto;
import Modelo.Empleado;
import Modelo.Empresa;
import Modelo.PersonaNatural;
import Modelo.Proyecto;
import Vista.UsaProyecto;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas unitarias con JUnit 4 para el Punto 5 (Archivos y pruebas):
 * generarArchivoDeObjetosProyectos, leerArchivoDeObjetosProyectos,
 * generarArchivoDeObjetosClientes y leerArchivoDeObjetosClientes.
 *
 * Casos pedidos en el enunciado: listas vacías, registros existentes,
 * archivos inexistentes y clientes repetidos.
 *
 * Nota. Los archivos "datosProyectos.obj" y "datosClientes.obj" se crean en la carpeta de trabajo.
 * Si ya existían (datos reales), se guardan como respaldo antes de cada prueba y se restauran al terminar.
 */
public class PruebasArchivosObjetosTest {

    private static final String ARCHIVO_PROYECTOS = "datosProyectos.obj";
    private static final String ARCHIVO_CLIENTES = "datosClientes.obj";
    private static final String RESPALDO = ".respaldo_pruebas";

    private CerradorDeVentanas cerrador;
    private Cliente empresa;
    private Cliente persona;
    private Empleado gerente;
    private Proyecto proyecto1;
    private Proyecto proyecto2;
    private Proyecto proyecto3;

    /**
     * Se ejecuta ANTES de cada prueba: cierra las ventanas de mensaje automáticamente,
     * respalda los archivos reales (si existen) y prepara datos nuevos.
     */
    @Before
    public void setUp() throws Exception {
        cerrador = new CerradorDeVentanas();
        cerrador.iniciar();

        respaldar(ARCHIVO_PROYECTOS);
        respaldar(ARCHIVO_CLIENTES);

        empresa = new Empresa(new Contacto("Ana Ruiz", "Gerente", "3001112222", "ana@acme.com"),
                "900123", "ACME SAS", "Cali");
        persona = new PersonaNatural("1144", "Luis Perez", "Bogota", "3105556666", "luis@mail.com");
        gerente = new Empleado("55", "Marta Gomez", "Ingeniera", (byte) 8);

        proyecto1 = new Proyecto("2026-001", "Sistema web de inventarios", LocalDate.of(2026, 11, 1),
                "meses", (byte) 6, empresa, gerente, "EN CURSO");
        proyecto2 = new Proyecto("2026-002", "Aplicacion movil de domicilios", LocalDate.of(2026, 12, 5),
                "semanas", (byte) 2, persona, gerente, "INICIADO");
        proyecto3 = new Proyecto("2026-003", "Intranet corporativa de la empresa", LocalDate.of(2027, 1, 10),
                "meses", (byte) 3, empresa, gerente, "INICIADO");
    }

    /**
     * Se ejecuta DESPUÉS de cada prueba: borra los archivos de prueba y restaura los reales.
     */
    @After
    public void tearDown() throws Exception {
        cerrador.detener();
        new File(ARCHIVO_PROYECTOS).delete();
        new File(ARCHIVO_CLIENTES).delete();
        restaurar(ARCHIVO_PROYECTOS);
        restaurar(ARCHIVO_CLIENTES);
    }

    private void respaldar(String nombre) throws Exception {
        File archivo = new File(nombre);
        if (archivo.exists()) {
            Files.move(archivo.toPath(), new File(nombre + RESPALDO).toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private void restaurar(String nombre) throws Exception {
        File respaldo = new File(nombre + RESPALDO);
        if (respaldo.exists()) {
            Files.move(respaldo.toPath(), new File(nombre).toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private ArrayList<Proyecto> tresProyectos() {
        ArrayList<Proyecto> lista = new ArrayList<>();
        lista.add(proyecto1);
        lista.add(proyecto2);
        lista.add(proyecto3);
        return lista;
    }

    // ==================== LISTAS VACÍAS ====================

    @Test
    public void testGenerarProyectosConListaVaciaNoCreaArchivo() {
        UsaProyecto.generarArchivoDeObjetosProyectos(new ArrayList<Proyecto>());

        assertFalse(new File(ARCHIVO_PROYECTOS).exists());
        assertTrue(cerrador.seMostro("GENERACION DE ARCHIVO")); // avisó la situación
    }

    @Test
    public void testGenerarClientesConListaVaciaNoCreaArchivo() {
        UsaProyecto.generarArchivoDeObjetosClientes(new ArrayList<Proyecto>());

        assertFalse(new File(ARCHIVO_CLIENTES).exists());
        assertTrue(cerrador.seMostro("GENERACION DE ARCHIVO"));
    }

    // ==================== ARCHIVOS INEXISTENTES ====================

    @Test
    public void testLeerProyectosConArchivoInexistente() {
        assertFalse(new File(ARCHIVO_PROYECTOS).exists());

        ArrayList<Proyecto> leidos = UsaProyecto.leerArchivoDeObjetosProyectos();

        assertNotNull(leidos);
        assertTrue(leidos.isEmpty());
        assertTrue(cerrador.seMostro("LECTURA DE ARCHIVO")); // avisó el error
    }

    @Test
    public void testLeerClientesConArchivoInexistente() {
        assertFalse(new File(ARCHIVO_CLIENTES).exists());

        ArrayList<Cliente> leidos = UsaProyecto.leerArchivoDeObjetosClientes();

        assertNotNull(leidos);
        assertTrue(leidos.isEmpty());
        assertTrue(cerrador.seMostro("LECTURA DE ARCHIVO"));
    }

    // ==================== GENERAR Y LEER PROYECTOS ====================

    @Test
    public void testGenerarProyectosCreaElArchivo() {
        UsaProyecto.generarArchivoDeObjetosProyectos(tresProyectos());

        File archivo = new File(ARCHIVO_PROYECTOS);
        assertTrue(archivo.exists());
        assertTrue(archivo.length() > 0);
    }

    @Test
    public void testGenerarYLeerProyectosRecuperaLosRegistros() {
        UsaProyecto.generarArchivoDeObjetosProyectos(tresProyectos());

        ArrayList<Proyecto> leidos = UsaProyecto.leerArchivoDeObjetosProyectos();

        assertEquals(3, leidos.size());
        assertEquals("2026-001", leidos.get(0).getCodigo());
        assertEquals("Sistema web de inventarios", leidos.get(0).getTitulo());
        assertEquals(LocalDate.of(2026, 11, 1), leidos.get(0).getFechaInicio());
        assertEquals("EN CURSO", leidos.get(0).getEstado());
        assertEquals("2026-002", leidos.get(1).getCodigo());
        assertEquals("2026-003", leidos.get(2).getCodigo());
    }

    @Test
    public void testLosProyectosLeidosConservanClienteYGerente() {
        UsaProyecto.generarArchivoDeObjetosProyectos(tresProyectos());

        ArrayList<Proyecto> leidos = UsaProyecto.leerArchivoDeObjetosProyectos();

        assertTrue(leidos.get(0).getSuCliente() instanceof Empresa);
        assertEquals("900123", leidos.get(0).getSuCliente().getNumeroIdentificacion());
        assertTrue(leidos.get(1).getSuCliente() instanceof PersonaNatural);
        assertEquals("Marta Gomez", leidos.get(0).getSuGerente().getNombreCompleto());
    }

    @Test
    public void testLosProyectosLeidosConservanElEquipo() {
        proyecto1.agregarIntegrante(new Empleado("10", "Pedro Soto", "Ingeniero", (byte) 6));
        proyecto1.agregarIntegrante(new Empleado("11", "Sara Mora", "Disenadora", (byte) 4));
        UsaProyecto.generarArchivoDeObjetosProyectos(tresProyectos());

        ArrayList<Proyecto> leidos = UsaProyecto.leerArchivoDeObjetosProyectos();

        assertEquals(2, leidos.get(0).getSuEquipo().size());
        assertEquals("Pedro Soto", leidos.get(0).getSuEquipo().get(0).getNombreCompleto());
        assertNull(leidos.get(1).getSuEquipo()); // el proyecto 2 no tenía equipo
    }

    @Test
    public void testLosProyectosLeidosConservanLaFechaDeFinalizacion() {
        UsaProyecto.generarArchivoDeObjetosProyectos(tresProyectos());

        ArrayList<Proyecto> leidos = UsaProyecto.leerArchivoDeObjetosProyectos();

        assertEquals(LocalDate.of(2027, 5, 1), leidos.get(0).determinarFechaFinalizacion());
        assertEquals(LocalDate.of(2026, 12, 19), leidos.get(1).determinarFechaFinalizacion());
    }

    // ==================== REGISTROS EXISTENTES ====================

    @Test
    public void testGenerarConArchivoExistenteLoReemplaza() {
        UsaProyecto.generarArchivoDeObjetosProyectos(tresProyectos());
        assertEquals(3, UsaProyecto.leerArchivoDeObjetosProyectos().size());

        ArrayList<Proyecto> soloUno = new ArrayList<>();
        soloUno.add(proyecto2);
        UsaProyecto.generarArchivoDeObjetosProyectos(soloUno);

        ArrayList<Proyecto> leidos = UsaProyecto.leerArchivoDeObjetosProyectos();
        assertEquals(1, leidos.size());
        assertEquals("2026-002", leidos.get(0).getCodigo());
    }

    @Test
    public void testLeerDosVecesDevuelveLosMismosRegistros() {
        UsaProyecto.generarArchivoDeObjetosProyectos(tresProyectos());

        ArrayList<Proyecto> primera = UsaProyecto.leerArchivoDeObjetosProyectos();
        ArrayList<Proyecto> segunda = UsaProyecto.leerArchivoDeObjetosProyectos();

        assertEquals(primera.size(), segunda.size());
        assertNotSame(primera, segunda); // cada lectura devuelve una lista nueva
        assertEquals(primera.get(0).getCodigo(), segunda.get(0).getCodigo());
    }

    // ==================== GENERAR Y LEER CLIENTES ====================

    @Test
    public void testGenerarClientesCreaElArchivo() {
        UsaProyecto.generarArchivoDeObjetosClientes(tresProyectos());

        File archivo = new File(ARCHIVO_CLIENTES);
        assertTrue(archivo.exists());
        assertTrue(archivo.length() > 0);
    }

    @Test
    public void testGenerarYLeerClientesRecuperaLosClientes() {
        ArrayList<Proyecto> dosClientesDistintos = new ArrayList<>();
        dosClientesDistintos.add(proyecto1); // empresa
        dosClientesDistintos.add(proyecto2); // persona natural
        UsaProyecto.generarArchivoDeObjetosClientes(dosClientesDistintos);

        ArrayList<Cliente> leidos = UsaProyecto.leerArchivoDeObjetosClientes();

        assertEquals(2, leidos.size());
        assertTrue(leidos.get(0) instanceof Empresa);
        assertEquals("900123", leidos.get(0).getNumeroIdentificacion());
        assertEquals("ACME SAS", leidos.get(0).getNombre());
        assertTrue(leidos.get(1) instanceof PersonaNatural);
        assertEquals("1144", leidos.get(1).getNumeroIdentificacion());
        assertEquals("Bogota", leidos.get(1).getNombreCiudad());
    }

    // ==================== CLIENTES REPETIDOS ====================

    @Test
    public void testClienteRepetidoSeLeeUnaSolaVez() {
        // proyecto1 y proyecto3 tienen el mismo cliente (empresa 900123)
        UsaProyecto.generarArchivoDeObjetosClientes(tresProyectos());

        ArrayList<Cliente> leidos = UsaProyecto.leerArchivoDeObjetosClientes();

        assertEquals(2, leidos.size()); // empresa 900123 y persona 1144, sin repetir
        int cuantasVecesEmpresa = 0;
        for (Cliente cliente : leidos) {
            if (cliente.getNumeroIdentificacion().equals("900123")) {
                cuantasVecesEmpresa++;
            }
        }
        assertEquals(1, cuantasVecesEmpresa);
    }

    @Test
    public void testClienteRepetidoComoObjetosDistintosSeLeeUnaSolaVez() {
        // mismo número de identificación pero dos objetos distintos
        Cliente otraEmpresaMismoId = new Empresa(new Contacto("Otro Contacto", "Director", "3009998888", "otro@acme.com"),
                "900123", "ACME SAS", "Cali");
        Proyecto proyectoRepetido = new Proyecto("2026-004", "Portal web para clientes nuevos", LocalDate.of(2027, 2, 1),
                "meses", (byte) 4, otraEmpresaMismoId, gerente, "INICIADO");
        ArrayList<Proyecto> lista = new ArrayList<>();
        lista.add(proyecto1);
        lista.add(proyectoRepetido);
        UsaProyecto.generarArchivoDeObjetosClientes(lista);

        ArrayList<Cliente> leidos = UsaProyecto.leerArchivoDeObjetosClientes();

        assertEquals(1, leidos.size());
        assertEquals("900123", leidos.get(0).getNumeroIdentificacion());
    }

    @Test
    public void testTodosLosProyectosConElMismoClienteDanUnSoloCliente() {
        ArrayList<Proyecto> lista = new ArrayList<>();
        lista.add(proyecto1);
        lista.add(proyecto3);
        lista.add(proyecto1);
        UsaProyecto.generarArchivoDeObjetosClientes(lista);

        assertEquals(1, UsaProyecto.leerArchivoDeObjetosClientes().size());
    }

    // ==================== SERIALIZACIÓN ====================

    @Test
    public void testLasClasesDelModeloSonSerializables() {
        assertTrue(proyecto1 instanceof java.io.Serializable);
        assertTrue(empresa instanceof java.io.Serializable);
        assertTrue(persona instanceof java.io.Serializable);
        assertTrue(gerente instanceof java.io.Serializable);
    }
}
