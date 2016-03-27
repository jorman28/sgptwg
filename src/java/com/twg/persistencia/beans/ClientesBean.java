package com.twg.persistencia.beans;

import java.util.Date;

/**
 * Esta clase define los atributos del objeto Clientes, 
 * junto con los métodos get y set.
 * 
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class ClientesBean extends PersonasBean{

    //Definición de atributos.
    private Date fecha_inicio;
    
    /**
     * Constructor de la clase
     */
    public ClientesBean(){
    }

    /*
     * Declaración de métodos Get y Set para cada atributo de la clase.
     */
    
    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }
}
