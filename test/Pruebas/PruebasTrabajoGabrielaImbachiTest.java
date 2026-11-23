package Pruebas;

import Modelo.Empleado;
import Modelo.Proyecto;
import Vista.UsaProyecto;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas unitarias con JUnit 4 para el Punto 3 (Empleado y equipos).
 *
 * Cada método marcado con @Test es una prueba independiente que sigue el patrón:
 *   1. Preparar los datos      (Arrange)
 *   2. Ejecutar el método      (Act)
 *   3. Verificar el resultado  (Assert)
 */
public class PruebasTrabajoGabrielaImbachiTest {

    private UsaProyecto vista;
    private ArrayList<Proyecto> listaProyectos;
    private Proyecto proyecto;
    private Empleado emp1;
    private Empleado emp2;
    private Empleado gerente;

    /**
     * Se ejecuta ANTES de cada prueba: deja datos nuevos y limpios,
     * para que ninguna prueba dependa de lo que hizo otra.
     */
    @Before
    public void setUp() {
        vista = new UsaProyecto();
        listaProyectos = new ArrayList<>();

        emp1 = new Empleado("10203040", "Carlos Andrés Pérez", "Ingeniero de Software", (byte) 4);
        emp2 = new Empleado("50607080", "María Fernanda Ruiz", "Analista de Calidad QA", (byte) 3);
        gerente = new Empleado("90102030", "Julián David Castro", "Arquitecto de Datos", (byte) 6);

        proyecto = new Proyecto(
                "2026-001",
                "Sistema Integrado de Control Logístico Nacional",
                LocalDate.of(2026, 2, 15),
                "meses",
                (byte) 6,
                null,
                gerente,
                "INICIADO"
        );
    }

    /**
     * Se ejecuta DESPUÉS de cada prueba: libera la ventana creada.
     */
    @After
    public void tearDown() {
        vista.dispose();
    }

    // ==================== PRUEBAS DE LA CLASE EMPLEADO ====================

    @Test
    public void testConstructorYGettersEmpleado() {
        assertEquals("10203040", emp1.getNumeroIdentificacion());
        assertEquals("Carlos Andrés Pérez", emp1.getNombreCompleto());
        assertEquals("Ingeniero de Software", emp1.getTituloProfesional());
        assertEquals(4, emp1.getAniosExperiencia());
    }

    @Test
    public void testSetterAniosExperiencia() {
        emp2.setAniosExperiencia((byte) 5);
        assertEquals(5, emp2.getAniosExperiencia());
    }

    @Test
    public void testToStringEmpleado() {
        String texto = emp1.toString();
        assertTrue(texto.contains("10203040"));
        assertTrue(texto.contains("Carlos Andrés Pérez"));
    }

    // ==================== PRUEBAS DE AGREGAR INTEGRANTES ====================

    @Test
    public void testAgregarIntegrantesAlEquipo() {
        proyecto.agregarIntegrante(emp1);
        proyecto.agregarIntegrante(emp2);
        assertEquals(2, proyecto.getSuEquipo().size());
    }

    @Test
    public void testDetectarIdentificacionRepetidaEnEquipo() {
        proyecto.agregarIntegrante(emp1);
        assertTrue(vista.existeIntegranteEnEquipo(proyecto.getSuEquipo(), "10203040"));
        assertFalse(vista.existeIntegranteEnEquipo(proyecto.getSuEquipo(), "999999"));
    }

    // ==================== PRUEBAS DE CONSULTAR EQUIPO (OPCIÓN 7) ====================

    @Test
    public void testConsultarConListaVacia() {
        String resultado = vista.consultarEquipoProyectoDadoCodigo(listaProyectos, "2026-001");
        assertTrue(resultado.contains("lista de proyectos está vacía"));
    }

    @Test
    public void testConsultarProyectoInexistente() {
        listaProyectos.add(proyecto);
        String resultado = vista.consultarEquipoProyectoDadoCodigo(listaProyectos, "2026-999");
        assertTrue(resultado.contains("No se encontró ningún proyecto"));
    }

    @Test
    public void testConsultarProyectoSinIntegrantes() {
        listaProyectos.add(proyecto);
        String resultado = vista.consultarEquipoProyectoDadoCodigo(listaProyectos, "2026-001");
        assertTrue(resultado.contains("aún no tiene integrantes"));
    }

    @Test
    public void testConsultarEquipoConIntegrantes() {
        proyecto.agregarIntegrante(emp1);
        proyecto.agregarIntegrante(emp2);
        listaProyectos.add(proyecto);

        String resultado = vista.consultarEquipoProyectoDadoCodigo(listaProyectos, "2026-001");

        assertTrue(resultado.contains("Sistema Integrado de Control Logístico Nacional"));
        assertTrue(resultado.contains("INTEGRANTES DEL EQUIPO (2)"));
        assertTrue(resultado.contains("Carlos Andrés Pérez"));
        assertTrue(resultado.contains("María Fernanda Ruiz"));
    }

    // ==================== PRUEBAS DE ELIMINAR INTEGRANTE (OPCIÓN 12) ====================

    @Test
    public void testEliminarConListaVacia() {
        String resultado = vista.eliminarIntegrantePorId(listaProyectos, "2026-001", "1010");
        assertTrue(resultado.contains("lista de proyectos está vacía"));
    }

    @Test
    public void testEliminarConIdentificacionVacia() {
        listaProyectos.add(proyecto);
        String resultado = vista.eliminarIntegrantePorId(listaProyectos, "2026-001", "   ");
        assertTrue(resultado.contains("no puede estar vacío"));
    }

    @Test
    public void testEliminarIntegranteQueNoPertenece() {
        proyecto.agregarIntegrante(emp1);
        listaProyectos.add(proyecto);

        String resultado = vista.eliminarIntegrantePorId(listaProyectos, "2026-001", "999999");

        assertTrue(resultado.contains("NO pertenece"));
        assertEquals(1, proyecto.getSuEquipo().size()); // el equipo no cambió
    }

    @Test
    public void testEliminarIntegranteExistente() {
        proyecto.agregarIntegrante(emp1);
        proyecto.agregarIntegrante(emp2);
        listaProyectos.add(proyecto);

        String resultado = vista.eliminarIntegrantePorId(listaProyectos, "2026-001", "10203040");

        assertTrue(resultado.contains("eliminado exitosamente"));
        assertEquals(1, proyecto.getSuEquipo().size());
        assertFalse(vista.existeIntegranteEnEquipo(proyecto.getSuEquipo(), "10203040"));
    }
}
