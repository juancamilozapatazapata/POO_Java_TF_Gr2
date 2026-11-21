/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Clase Proyecto.
 *
 * Representa un proyecto de la empresa y permite administrar
 * su informacion, cliente, gerente, equipo de trabajo,
 * duracion estimada y estado.
 *
 * @author Gabriela Imbachi y Dylan Riascos
 */
public class Proyecto implements Serializable {

    private static final long serialVersionUID = 1L;

    // Atributos del proyecto
    private String codigo;
    private String titulo;
    private LocalDate fechaInicio;
    private String unidadDuracion;
    private byte duracionEstimada;
    private Cliente suCliente;
    private Empleado suGerente;
    private ArrayList<Empleado> suEquipo;
    private String estado;

    /**
     * Constructor principal.
     * Permite crear un proyecto con equipo y estado.
     */
    public Proyecto(String codigo, String titulo,
            LocalDate fechaInicio, String unidadDuracion,
            byte duracionEstimada, Cliente suCliente,
            Empleado suGerente, ArrayList<Empleado> suEquipo,
            String estado) {

        setCodigo(codigo);
        setTitulo(titulo);
        setFechaInicio(fechaInicio);
        setUnidadDuracion(unidadDuracion);
        setDuracionEstimada(duracionEstimada);
        setSuCliente(suCliente);
        setSuGerente(suGerente);

        this.suEquipo = new ArrayList<>();
        if (suEquipo != null) {
            for (Empleado empleado : suEquipo) {
                agregarIntegrante(empleado);
            }
        }

        setEstado(estado);
    }

    /**
     * Constructor sin equipo.
     * Inicializa una lista vacia de empleados.
     */
    public Proyecto(String codigo, String titulo,
            LocalDate fechaInicio, String unidadDuracion,
            byte duracionEstimada, Cliente suCliente,
            Empleado suGerente, String estado) {

        this(codigo, titulo, fechaInicio, unidadDuracion,
                duracionEstimada, suCliente, suGerente,
                new ArrayList<>(), estado);
    }

    /**
     * Constructor sin equipo ni estado.
     * El estado inicial sera INICIADO.
     */
    public Proyecto(String codigo, String titulo,
            LocalDate fechaInicio, String unidadDuracion,
            byte duracionEstimada, Cliente suCliente,
            Empleado suGerente) {

        this(codigo, titulo, fechaInicio, unidadDuracion,
                duracionEstimada, suCliente, suGerente,
                new ArrayList<>(), "INICIADO");
    }

    /**
     * Constructor con equipo y estado inicial automatico.
     * Conserva compatibilidad con el codigo anterior.
     */
    public Proyecto(String codigo, String titulo,
            LocalDate fechaInicio, String unidadDuracion,
            byte duracionEstimada, Cliente suCliente,
            Empleado suGerente, ArrayList<Empleado> suEquipo) {

        this(codigo, titulo, fechaInicio, unidadDuracion,
                duracionEstimada, suCliente, suGerente,
                suEquipo, "INICIADO");
    }

    // GETTERS: permiten consultar los atributos.

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public String getUnidadDuracion() {
        return unidadDuracion;
    }

    public byte getDuracionEstimada() {
        return duracionEstimada;
    }

    public Cliente getSuCliente() {
        return suCliente;
    }

    public Empleado getSuGerente() {
        return suGerente;
    }

    /**
     * Devuelve una copia de la lista de empleados.
     * Evita modificaciones directas desde otras clases.
     */
    public ArrayList<Empleado> getSuEquipo() {
        return new ArrayList<>(suEquipo);
    }

    public String getEstado() {
        return estado;
    }

    // SETTERS: modifican y validan los atributos.

    /**
     * Valida el codigo con formato AAAA-NNN.
     * Ejemplo: 2026-001.
     * @param codigo
     */
    public final void setCodigo(String codigo) {

        if (codigo == null ||
                !codigo.matches("\\d{4}-\\d{3}")) {

            throw new IllegalArgumentException(
                    "Codigo invalido. Ejemplo: 2026-001");
        }

        this.codigo = codigo;
    }

    /**
     * El titulo debe tener entre 20 y 100 caracteres.
     * @param titulo
     */
    public final void setTitulo(String titulo) {

        if (titulo == null ||
                titulo.trim().length() < 20 ||
                titulo.trim().length() > 100) {

            throw new IllegalArgumentException(
                    "El titulo debe tener entre 20 y 100 caracteres");
        }

        this.titulo = titulo.trim();
    }

    /**
     * La fecha de inicio es obligatoria.
     */
    public final void setFechaInicio(LocalDate fechaInicio) {

        if (fechaInicio == null) {
            throw new IllegalArgumentException(
                    "La fecha de inicio es obligatoria");
        }

        this.fechaInicio = fechaInicio;
    }

    /**
     * Valida la unidad y comprueba que la duracion
     * existente siga siendo valida al cambiarla.
     */
    public final void setUnidadDuracion(String unidadDuracion) {

        if (unidadDuracion == null) {
            throw new IllegalArgumentException(
                    "La unidad de duracion es obligatoria");
        }

        String unidad = unidadDuracion.trim().toLowerCase();

        if (!unidad.equals("semanas") &&
                !unidad.equals("meses") &&
                !unidad.equals("años")) {

            throw new IllegalArgumentException(
                    "La unidad debe ser semanas, meses o años");
        }

        if (this.unidadDuracion != null &&
                !duracionValida(unidad, this.duracionEstimada)) {

            throw new IllegalArgumentException(
                    "La duracion actual no es valida para " + unidad);
        }

        this.unidadDuracion = unidad;
    }

    /**
     * Comprueba las restricciones de duracion.
     *
     * Semanas: 1 a 3.
     * Meses: 1 a 35, excepto 12 y 24.
     * Años: 1 a 3.
     */
    private boolean duracionValida(String unidad, byte duracion) {

        if (unidad == null) {
            return false;
        }

        switch (unidad) {

            case "semanas":
                return duracion >= 1 && duracion <= 3;

            case "meses":
                return duracion >= 1 && duracion <= 35
                        && duracion != 12 && duracion != 24;

            case "años":
                return duracion >= 1 && duracion <= 3;

            default:
                return false;
        }
    }

    /**
     * Asigna la duracion estimada del proyecto.
     */
    public final void setDuracionEstimada(byte duracionEstimada) {

        if (!duracionValida(unidadDuracion, duracionEstimada)) {

            throw new IllegalArgumentException(
                    "Duracion no permitida para " + unidadDuracion);
        }

        this.duracionEstimada = duracionEstimada;
    }

    public final void setSuCliente(Cliente suCliente) {
        this.suCliente = suCliente;
    }

    public final void setSuGerente(Empleado suGerente) {
        this.suGerente = suGerente;
    }

    /**
     * Reemplaza el equipo por una lista nueva.
     * Valida los integrantes y evita identificaciones repetidas.
     */
    public final void setSuEquipo(ArrayList<Empleado> suEquipo) {

        ArrayList<Empleado> equipoAnterior = this.suEquipo;
        this.suEquipo = new ArrayList<>();

        try {
            if (suEquipo != null) {
                for (Empleado empleado : suEquipo) {
                    agregarIntegrante(empleado);
                }
            }
        } catch (IllegalArgumentException ex) {
            this.suEquipo = equipoAnterior;
            throw ex;
        }
    }

    /**
     * Valida los estados permitidos del proyecto.
     */
    public final void setEstado(String estado) {

        if (estado == null) {
            throw new IllegalArgumentException(
                    "El estado es obligatorio");
        }

        String nuevoEstado = estado.trim().toUpperCase();

        switch (nuevoEstado) {

            case "INICIADO":
            case "EN CURSO":
            case "CANCELADO":
            case "FINALIZADO":
            case "SUSPENDIDO":
            case "APLAZADO":
                this.estado = nuevoEstado;
                break;

            default:
                throw new IllegalArgumentException(
                        "Estado de proyecto no valido");
        }
    }

    // METODOS ESPECIALES DEL PROYECTO

    /**
     * Determina la fecha de finalizacion sumando
     * la duracion estimada a la fecha de inicio.
     *
     * @return Fecha estimada de finalizacion.
     */
    public LocalDate determinarFechaFinalizacion() {

        if (fechaInicio == null || unidadDuracion == null) {
            throw new IllegalStateException(
                    "Faltan datos para calcular la fecha final");
        }

        switch (unidadDuracion) {

            case "semanas":
                return fechaInicio.plusWeeks(duracionEstimada);

            case "meses":
                return fechaInicio.plusMonths(duracionEstimada);

            case "años":
                return fechaInicio.plusYears(duracionEstimada);

            default:
                throw new IllegalStateException(
                        "Unidad de duracion no valida");
        }
    }

    /**
     * Agrega un empleado al equipo del proyecto.
     * No permite empleados nulos ni identificaciones duplicadas.
     */
    public void agregarIntegrante(Empleado nuevoIntegrante) {

        if (nuevoIntegrante == null) {
            throw new IllegalArgumentException(
                    "El integrante no puede ser nulo");
        }

        if (nuevoIntegrante.getNumeroIdentificacion() == null) {
            throw new IllegalArgumentException(
                    "El integrante debe tener identificacion");
        }

        if (suEquipo == null) {
            suEquipo = new ArrayList<>();
        }

        for (Empleado integrante : suEquipo) {

            if (integrante.getNumeroIdentificacion()
                    .equalsIgnoreCase(
                            nuevoIntegrante.getNumeroIdentificacion())) {

                throw new IllegalArgumentException(
                        "El integrante ya pertenece al proyecto");
            }
        }

        suEquipo.add(nuevoIntegrante);
    }

    /**
     * Busca y elimina un empleado por su identificacion.
     *
     * @param numeroIdentificacion Identificacion del empleado.
     * @return Empleado eliminado o null si no existe.
     */
    public Empleado eliminarIntegrante(String numeroIdentificacion) {

        if (numeroIdentificacion == null || suEquipo == null) {
            return null;
        }

        for (int i = 0; i < suEquipo.size(); i++) {

            Empleado integrante = suEquipo.get(i);

            if (integrante.getNumeroIdentificacion()
                    .equalsIgnoreCase(numeroIdentificacion.trim())) {

                return suEquipo.remove(i);
            }
        }

        return null;
    }

    /**
     * Devuelve la informacion del proyecto en formato legible.
     */
    @Override
    public String toString() {

        return "Codigo: " + codigo
                + "\nTitulo: " + titulo
                + "\nFecha de inicio: " + fechaInicio
                + "\nUnidad de duracion: " + unidadDuracion
                + "\nDuracion estimada: " + duracionEstimada
                + "\nFecha de finalizacion: "
                + determinarFechaFinalizacion()
                + "\nCliente: " + suCliente
                + "\nGerente: " + suGerente
                + "\nIntegrantes del equipo: " + suEquipo.size()
                + "\nEstado: " + estado;
    }
}
