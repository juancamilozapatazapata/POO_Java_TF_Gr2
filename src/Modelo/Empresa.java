/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;


/**
 *
 * @author Juan D
 */
public final class Empresa extends Cliente{
    
    private Contacto suContacto;
    
    /* constructor*/

    public Empresa(Contacto suContacto, String numeroIdentificacion, String nombre, String nombreCiudad) {
        super(numeroIdentificacion, nombre, nombreCiudad);/* llama al constructor de Clientes*/
        this.suContacto = suContacto; 
    }
    
    /*  get y set*/

    public Contacto getSuContacto() {
        return suContacto;
    }

    public void setSuContacto(Contacto suContacto) {
        this.suContacto = suContacto;
    }
    
    /* toString */
    
    @Override
    public String toString() {
        return "Tipo de cliente: Empresa\n"
                + super.toString()
                + "\n--- Contacto ---\n" + suContacto;
    }    
    
}
