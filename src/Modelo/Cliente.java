/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Gabriela imbachi
 */
public  abstract class Cliente {
    protected String numeroIdentificacion ;
    protected String nombre ;
    protected String nombreCuidad ;

    public Cliente(String numeroIdentificacion, String nombre, String nombreCuidad) {
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombre = nombre;
        this.nombreCuidad = nombreCuidad;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreCuidad() {
        return nombreCuidad;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNombreCuidad(String nombreCuidad) {
        this.nombreCuidad = nombreCuidad;
    }

    @Override
    public String toString() {
        return "Cliente{" + "numeroIdentificacion=" + numeroIdentificacion + ", nombre=" + nombre + ", nombreCuidad=" + nombreCuidad + '}';
    }
    
    
}
