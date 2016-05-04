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

    //Definición de atributos tipo String: son iguales a los anteriores, pero ayudan para obtener en gestion de actividades
    //aquellas actividades de los empleados para ser enviados por request.setAttribute
    private String strActividad;
    private String strEmpleado;
    private String strFecha_estimada_inicio;
    private String strFecha_estimada_terminacion;
    private String strFecha_real_inicio;
    private String strFecha_real_terminacion;
    private String strTiempo_estimado;
    private String strTiempo_invertido;
    private String strNombrePersona;

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

    /*
     * Declaración de métodos Get y Set de los atributos String de la clase.
     */
    public String getStrActividad() {
        return strActividad;
    }

    public void setStrActividad(String strActividad) {
        this.strActividad = strActividad;
    }

    public String getStrEmpleado() {
        return strEmpleado;
    }

    public void setStrEmpleado(String strEmpleado) {
        this.strEmpleado = strEmpleado;
    }

    public String getStrFecha_estimada_inicio() {
        return strFecha_estimada_inicio;
    }

    public void setStrFecha_estimada_inicio(String strFecha_estimada_inicio) {
        this.strFecha_estimada_inicio = strFecha_estimada_inicio;
    }

    public String getStrFecha_estimada_terminacion() {
        return strFecha_estimada_terminacion;
    }

    public void setStrFecha_estimada_terminacion(String strFecha_estimada_terminacion) {
        this.strFecha_estimada_terminacion = strFecha_estimada_terminacion;
    }

    public String getStrFecha_real_inicio() {
        return strFecha_real_inicio;
    }

    public void setStrFecha_real_inicio(String strFecha_real_inicio) {
        this.strFecha_real_inicio = strFecha_real_inicio;
    }

    public String getStrFecha_real_terminacion() {
        return strFecha_real_terminacion;
    }

    public void setStrFecha_real_terminacion(String strFecha_real_terminacion) {
        this.strFecha_real_terminacion = strFecha_real_terminacion;
    }

    public String getStrTiempo_estimado() {
        return strTiempo_estimado;
    }

    public void setStrTiempo_estimado(String strTiempo_estimado) {
        this.strTiempo_estimado = strTiempo_estimado;
    }

    public String getStrTiempo_invertido() {
        return strTiempo_invertido;
    }

    public void setStrTiempo_invertido(String strTiempo_invertido) {
        this.strTiempo_invertido = strTiempo_invertido;
    }

    public String getStrNombrePersona() {
        return strNombrePersona;
    }

    public void setStrNombrePersona(String strNombrePersona) {
        this.strNombrePersona = strNombrePersona;
    }
}
