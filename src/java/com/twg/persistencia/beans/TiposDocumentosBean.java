package com.twg.persistencia.beans;

/**
 * Esta clase define los atributos del objeto TiposDocumentos, 
 * junto con los métodos get y set.
 * 
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class TiposDocumentosBean {
    
    //Definición de atributos.
    private String tipo;
    private String nombre;
    
    /**
     * Constructor de la clase
     */
    public TiposDocumentosBean(){
    }

    /*
     * Declaración de métodos Get y Set para cada atributo de la clase.
     */
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
