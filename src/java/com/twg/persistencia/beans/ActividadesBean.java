package com.twg.persistencia.beans;

import java.util.Date;

/**
 * Esta clase define los atributos del objeto Actividades, 
 * junto con los métodos get y set.
 * 
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class ActividadesBean {

    //Definición de atributos.
    private Integer id;
    private Integer version;
    private String nombreV;
    private String descripcion;
    private Date fecha_estimada_inicio;
    private Date fecha_estimada_terminacion;
    private Date fecha_real_inicio;
    private Date fecha_real_terminacion;
    private double tiempo_estimado;
    private double tiempo_invertido;
    private Integer estado;
    private String nombreE;
    private Date fecha_eliminacion;

    /*
     * Declaración de métodos Get y Set para cada atributo de la clase.
     */
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getNombreV() {
        return nombreV;
    }

    public void setNombreV(String nombreV) {
        this.nombreV = nombreV;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public double getTiempo_estimado() {
        return tiempo_estimado;
    }

    public void setTiempo_estimado(double tiempo_estimado) {
        this.tiempo_estimado = tiempo_estimado;
    }

    public double getTiempo_invertido() {
        return tiempo_invertido;
    }

    public void setTiempo_invertido(double tiempo_invertido) {
        this.tiempo_invertido = tiempo_invertido;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Date getFecha_eliminacion() {
        return fecha_eliminacion;
    }

    public void setFecha_eliminacion(Date fecha_eliminacion) {
        this.fecha_eliminacion = fecha_eliminacion;
    }

    public String getNombreE() {
        return nombreE;
    }

    public void setNombreE(String nombreE) {
        this.nombreE = nombreE;
    }

}
