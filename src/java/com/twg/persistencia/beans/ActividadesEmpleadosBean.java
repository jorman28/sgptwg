package com.twg.persistencia.beans;

/**
 * Esta clase define los atributos del objeto ActividadesEmpleados, 
 * junto con los métodos get y set.
 * 
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class ActividadesEmpleadosBean {
    
    //Definición de atributos.
    private Integer actividad;
    private Integer empleado;
    
    /*
     * Declaración de métodos Get y Set para cada atributo de la clase.
     */
    
    public Integer getActividad() {
        return actividad;
    }

    public void setActividad(Integer actividad) {
        this.actividad = actividad;
    }

    public Integer getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Integer empleado) {
        this.empleado = empleado;
    }
    
}
