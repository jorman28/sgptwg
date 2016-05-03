package com.twg.persistencia.beans;

import java.util.Date;

/**
 * Esta clase define los atributos del objeto ActividadesEmpleados, junto con
 * los métodos get y set.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class ActividadesEmpleadosBean {

    //Definición de atributos.
    private Integer actividad;
    private Integer empleado;
    private Date fechaEstimadaInicio;
    private Date fechaEstimadaTerminacion;
    private Double tiempoEstimado;

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

    public Date getFechaEstimadaInicio() {
        return fechaEstimadaInicio;
    }

    public void setFechaEstimadaInicio(Date fechaEstimadaInicio) {
        this.fechaEstimadaInicio = fechaEstimadaInicio;
    }

    public Date getFechaEstimadaTerminacion() {
        return fechaEstimadaTerminacion;
    }

    public void setFechaEstimadaTerminacion(Date fechaEstimadaTerminacion) {
        this.fechaEstimadaTerminacion = fechaEstimadaTerminacion;
    }

    public Double getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(Double tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }
}
