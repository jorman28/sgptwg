package com.twg.persistencia.beans;

import java.util.Date;

/**
 * Esta clase define los atributos del objeto Versiones, 
 * junto con los métodos get y set.
 * 
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class VersionesBean {

    //Definición de atributos.
    private Integer id;
    private String nombre;
    private Date fechaInicio;
    private Date fechaFin;
    private String alcance;
    private Integer proyecto;
    private String nombreProyecto;
    private Integer estado;
    private String nombreEstado;
    private Date fechaEliminacion;
    private Date fechaInicioProyecto;
    private Double costo;

    /*
     * Declaración de métodos Get y Set para cada atributo de la clase.
     */
    
    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaTerminacion() {
        return fechaFin;
    }

    public void setFechaTerminacion(Date fechaTerminacion) {
        this.fechaFin = fechaTerminacion;
    }

    public String getAlcance() {
        return alcance;
    }

    public void setAlcance(String alcance) {
        this.alcance = alcance;
    }

    public Integer getProyecto() {
        return proyecto;
    }

    public void setProyecto(Integer proyecto) {
        this.proyecto = proyecto;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public Date getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setFechaEliminacion(Date fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }

    public Date getFechaInicioProyecto() {
        return fechaInicioProyecto;
    }

    public void setFechaInicioProyecto(Date fechaInicioProyecto) {
        this.fechaInicioProyecto = fechaInicioProyecto;
    }
}
