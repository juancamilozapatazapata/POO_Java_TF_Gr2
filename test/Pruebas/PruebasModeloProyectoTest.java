package Pruebas;

import Modelo.Cliente;
import Modelo.Contacto;
import Modelo.Empleado;
import Modelo.Empresa;
import Modelo.PersonaNatural;
import Modelo.Proyecto;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas unitarias con JUnit 4 para las clases del modelo:
 * Proyecto (fecha de finalización, equipo), Cliente (Empresa y PersonaNatural) y Contacto.
 *
 * Cada prueba sigue el patrón: preparar los datos, ejecutar el método y verificar el resultado.
 */
public class PruebasModeloProyectoTest {

    private Empleado gerente;
    private Cliente empresa;
    private Cliente persona;
    private Empleado integrante1;
    private Empleado integrante2;

    /**
     * Se ejecuta ANTES de cada prueba: deja datos nuevos y limpios.
     */
    @Before
    public void setUp() {
        gerente = new Empleado("55", "Marta Gomez", "Ingeniera", (byte) 8);
        empresa = new Empresa(new Contacto("Ana Ruiz", "Gerente", "3001112222", "ana@acme.com"),
                "900123", "ACME SAS", "Cali");
        persona = new PersonaNatural("1144", "Luis Perez", "Bogota", "3105556666", "luis@mail.com");
        integrante1 = new Empleado("10", "Pedro Soto", "Ingeniero", (byte) 6);
        integrante2 = new Empleado("11", "Sara Mora", "Disenadora", (byte) 4);
    }

    private Proyecto proyecto(LocalDate inicio, String unidad, int duracion) {
        return new Proyecto("2026-001", "Sistema web de inventarios", inicio, unidad, (byte) duracion,
                empresa, gerente, "INICIADO");
    }

    // ==================== FECHA DE FINALIZACIÓN ====================

    @Test
    public void testFechaFinalizacionEnSemanas() {
        Proyecto p = proyecto(LocalDate.of(2026, 12, 5), "semanas", 2);
        assertEquals(LocalDate.of(2026, 12, 19), p.determinarFechaFinalizacion());
    }

    @Test
    public void testFechaFinalizacionEnMeses() {
        Proyecto p = proyecto(LocalDate.of(2026, 11, 1), "meses", 6);
        assertEquals(LocalDate.of(2027, 5, 1), p.determinarFechaFinalizacion());
    }

    @Test
    public void testFechaFinalizacionEnAnios() {
        Proyecto p = proyecto(LocalDate.of(2026, 2, 15), "años", 3);
        assertEquals(LocalDate.of(2029, 2, 15), p.determinarFechaFinalizacion());
    }

    @Test
    public void testFechaFinalizacionIgnoraMayusculas() {
        Proyecto p = proyecto(LocalDate.of(2026, 11, 1), "MESES", 6);
        assertEquals(LocalDate.of(2027, 5, 1), p.determinarFechaFinalizacion());
    }

    @Test
    public void testFechaFinalizacionConUnidadDesconocidaDevuelveLaFechaDeInicio() {
        Proyecto p = proyecto(LocalDate.of(2026, 11, 1), "dias", 10);
        assertEquals(LocalDate.of(2026, 11, 1), p.determinarFechaFinalizacion());
    }

    @Test
    public void testFechaFinalizacionSinFechaInicioDevuelveNull() {
        Proyecto p = proyecto(null, "meses", 6);
        assertNull(p.determinarFechaFinalizacion());
    }

    @Test
    public void testFechaFinalizacionSinUnidadDevuelveNull() {
        Proyecto p = proyecto(LocalDate.of(2026, 11, 1), null, 6);
        assertNull(p.determinarFechaFinalizacion());
    }

    @Test
    public void testFechaFinalizacionConMesesQueCambianDeAnio() {
        Proyecto p = proyecto(LocalDate.of(2026, 11, 30), "meses", 3);
        assertEquals(LocalDate.of(2027, 2, 28), p.determinarFechaFinalizacion());
    }

    // ==================== CONSTRUCTORES, GETTERS Y SETTERS ====================

    @Test
    public void testConstructorSinEquipoDejaElEquipoSinCrear() {
        Proyecto p = proyecto(LocalDate.of(2026, 11, 1), "meses", 6);
        assertNull(p.getSuEquipo());
        assertEquals("INICIADO", p.getEstado());
    }

    @Test
    public void testConstructorConEquipo() {
        ArrayList<Empleado> equipo = new ArrayList<>();
        equipo.add(integrante1);
        Proyecto p = new Proyecto("2026-002", "Aplicacion movil de domicilios", LocalDate.of(2026, 12, 5),
                "semanas", (byte) 2, persona, gerente, equipo, "EN CURSO");

        assertEquals(1, p.getSuEquipo().size());
        assertEquals("EN CURSO", p.getEstado());
        assertSame(persona, p.getSuCliente());
        assertSame(gerente, p.getSuGerente());
    }

    @Test
    public void testSettersYGettersDeProyecto() {
        Proyecto p = proyecto(LocalDate.of(2026, 11, 1), "meses", 6);
        p.setEstado("FINALIZADO");
        p.setTitulo("Titulo nuevo del proyecto de prueba");
        p.setDuracionEstimada((byte) 9);

        assertEquals("FINALIZADO", p.getEstado());
        assertEquals("Titulo nuevo del proyecto de prueba", p.getTitulo());
        assertEquals(9, p.getDuracionEstimada());
    }

    @Test
    public void testToStringDeProyectoIncluyeSusDatos() {
        Proyecto p = proyecto(LocalDate.of(2026, 11, 1), "meses", 6);
        String texto = p.toString();

        assertTrue(texto.contains("2026-001"));
        assertTrue(texto.contains("Sistema web de inventarios"));
        assertTrue(texto.contains("INICIADO"));
    }

    // ==================== AGREGAR INTEGRANTE ====================

    @Test
    public void testAgregarIntegranteCreaElEquipoSiNoExiste() {
        Proyecto p = proyecto(LocalDate.of(2026, 11, 1), "meses", 6);

        p.agregarIntegrante(integrante1);

        assertNotNull(p.getSuEquipo());
        assertEquals(1, p.getSuEquipo().size());
    }

    @Test
    public void testAgregarVariosIntegrantesConservaElOrden() {
        Proyecto p = proyecto(LocalDate.of(2026, 11, 1), "meses", 6);

        p.agregarIntegrante(integrante1);
        p.agregarIntegrante(integrante2);

        assertEquals(2, p.getSuEquipo().size());
        assertSame(integrante1, p.getSuEquipo().get(0));
        assertSame(integrante2, p.getSuEquipo().get(1));
    }

    @Test
    public void testAgregarIntegranteNuloNoSeAgrega() {
        Proyecto p = proyecto(LocalDate.of(2026, 11, 1), "meses", 6);

        p.agregarIntegrante(null);

        assertTrue(p.getSuEquipo().isEmpty());
    }

    // ==================== ELIMINAR INTEGRANTE ====================

    @Test
    public void testEliminarIntegranteExistenteLoDevuelve() {
        Proyecto p = proyecto(LocalDate.of(2026, 11, 1), "meses", 6);
        p.agregarIntegrante(integrante1);
        p.agregarIntegrante(integrante2);

        Empleado eliminado = p.eliminarIntegrante("10");

        assertSame(integrante1, eliminado);
        assertEquals(1, p.getSuEquipo().size());
        assertSame(integrante2, p.getSuEquipo().get(0));
    }

    @Test
    public void testEliminarIntegranteIgnoraEspaciosYMayusculas() {
        Proyecto p = proyecto(LocalDate.of(2026, 11, 1), "meses", 6);
        p.agregarIntegrante(new Empleado("AB12", "Pedro Soto", "Ingeniero", (byte) 6));

        Empleado eliminado = p.eliminarIntegrante("  ab12 ");

        assertNotNull(eliminado);
        assertTrue(p.getSuEquipo().isEmpty());
    }

    @Test
    public void testEliminarIntegranteQueNoPerteneceDevuelveNull() {
        Proyecto p = proyecto(LocalDate.of(2026, 11, 1), "meses", 6);
        p.agregarIntegrante(integrante1);

        assertNull(p.eliminarIntegrante("999"));
        assertEquals(1, p.getSuEquipo().size());
    }

    @Test
    public void testEliminarIntegranteConEquipoSinCrearDevuelveNull() {
        Proyecto p = proyecto(LocalDate.of(2026, 11, 1), "meses", 6);
        assertNull(p.eliminarIntegrante("10"));
    }

    @Test
    public void testEliminarIntegranteConIdentificacionNulaDevuelveNull() {
        Proyecto p = proyecto(LocalDate.of(2026, 11, 1), "meses", 6);
        p.agregarIntegrante(integrante1);

        assertNull(p.eliminarIntegrante(null));
        assertEquals(1, p.getSuEquipo().size());
    }

    // ==================== CLIENTES: HERENCIA Y CONTACTO ====================

    @Test
    public void testEmpresaYPersonaNaturalSonClientes() {
        assertTrue(empresa instanceof Cliente);
        assertTrue(persona instanceof Cliente);
    }

    @Test
    public void testGettersDeCliente() {
        assertEquals("900123", empresa.getNumeroIdentificacion());
        assertEquals("ACME SAS", empresa.getNombre());
        assertEquals("Cali", empresa.getNombreCiudad());
        assertEquals("1144", persona.getNumeroIdentificacion());
        assertEquals("Luis Perez", persona.getNombre());
        assertEquals("Bogota", persona.getNombreCiudad());
    }

    @Test
    public void testSettersDeCliente() {
        persona.setNombre("Luis Alberto Perez");
        persona.setNombreCiudad("Medellin");

        assertEquals("Luis Alberto Perez", persona.getNombre());
        assertEquals("Medellin", persona.getNombreCiudad());
    }

    @Test
    public void testToStringDeClientesIncluyeSusDatos() {
        assertTrue(empresa.toString().contains("900123"));
        assertTrue(empresa.toString().contains("ACME SAS"));
        assertTrue(persona.toString().contains("1144"));
        assertTrue(persona.toString().contains("luis@mail.com"));
    }

    @Test
    public void testToStringDeContacto() {
        Contacto contacto = new Contacto("Ana Ruiz", "Gerente", "3001112222", "ana@acme.com");
        String texto = contacto.toString();

        assertTrue(texto.contains("Ana Ruiz"));
        assertTrue(texto.contains("ana@acme.com"));
    }
}
