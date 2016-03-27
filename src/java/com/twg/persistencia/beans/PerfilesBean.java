
package com.twg.persistencia.beans;

/**
 * Esta clase define los atributos del objeto Perfiles, 
 * junto con los métodos get y set.
 * 
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class PerfilesBean {
    
    //Definición de atributos.
    private Integer id;
    private String nombre;
    
    /**
     * Constructor de la clase
     */
    public PerfilesBean(){
    }
    
    /*
     * Declaración de métodos Get y Set para cada atributo de la clase.
     */

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
