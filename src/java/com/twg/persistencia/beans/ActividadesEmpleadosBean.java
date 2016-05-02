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
    private Date fecha_estimada_inicio;
    private Date fecha_estimada_terminacion;
    private Date fecha_real_inicio;
    private Date fecha_real_terminacion;
    private Double tiempo_estimado;
    private Double tiempo_invertido;

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
    
    public Date getFecha_estimada_inicio() {
        return fecha_estimada_inicio;
    }

    public void setFecha_estimada_inicio(Date fecha_estimada_inicio) {
        this.fecha_estimada_inicio = fecha_estimada_inicio;
    }

    public Date getFecha_estimada_terminacion() {
        return fecha_estimada_terminacion;
    }

    public void setFecha_estimada_terminacion(Date fecha_estimada_terminacion) {
        this.fecha_estimada_terminacion = fecha_estimada_terminacion;
    }

    public Date getFecha_real_inicio() {
        return fecha_real_inicio;
    }

    public void setFecha_real_inicio(Date fecha_real_inicio) {
        this.fecha_real_inicio = fecha_real_inicio;
    }

    public Date getFecha_real_terminacion() {
        return fecha_real_terminacion;
    }

    public void setFecha_real_terminacion(Date fecha_real_terminacion) {
        this.fecha_real_terminacion = fecha_real_terminacion;
    }

    public Double getTiempo_estimado() {
        return tiempo_estimado;
    }

    public void setTiempo_estimado(Double tiempo_estimado) {
        this.tiempo_estimado = tiempo_estimado;
    }

    public Double getTiempo_invertido() {
        return tiempo_invertido;
    }

    public void setTiempo_invertido(Double tiempo_invertido) {
        this.tiempo_invertido = tiempo_invertido;
    }

}
