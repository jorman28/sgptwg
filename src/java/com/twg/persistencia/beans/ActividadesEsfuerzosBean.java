package com.twg.persistencia.beans;

import java.util.Date;

/**
 * Esta clase define los atributos del objeto ActividadesEsfuerzos, junto con
 * los métodos get y set.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class ActividadesEsfuerzosBean {

    //Definición de atributos.
    private Integer id;
    private Integer actividad;
    private Integer empleado;
    private Date fecha;
    private Double tiempo;
    private String descripcion;

    /*
     * Declaración de métodos Get y Set para cada atributo de la clase.
     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getTiempo() {
        return tiempo;
    }

    public void setTiempo(Double tiempo) {
        this.tiempo = tiempo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
