package com.twg.persistencia.beans;

import java.util.Date;

/**
 * Esta clase define los atributos del objeto Actividades, junto con los métodos
 * get y set.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class ActividadesBean {

    //Definición de atributos.
    private Integer id;
    private Integer version;
    private String nombreVersion;
    private String nombre;
    private String descripcion;
    private Integer estado;
    private String nombreEstado;
    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaRealInicio;
    private Date ultimaModificacion;
    private Double tiempoEstimado;
    private Double tiempoInvertido;
    private Integer proyecto;
    private String nombreProyecto;

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

    public String getNombreVersion() {
        return nombreVersion;
    }

    public void setNombreVersion(String nombreV) {
        this.nombreVersion = nombreV;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public void setNombreEstado(String nombreE) {
        this.nombreEstado = nombreE;
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

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Double getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(Double tiempo) {
        this.tiempoEstimado = tiempo;
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

    public Double getTiempoInvertido() {
        return tiempoInvertido;
    }

    public void setTiempoInvertido(Double tiempoInvertido) {
        this.tiempoInvertido = tiempoInvertido;
    }

    public Date getFechaRealInicio() {
        return fechaRealInicio;
    }

    public void setFechaRealInicio(Date fechaRealInicio) {
        this.fechaRealInicio = fechaRealInicio;
    }

    public Date getUltimaModificacion() {
        return ultimaModificacion;
    }

    public void setUltimaModificacion(Date ultimaModificacion) {
        this.ultimaModificacion = ultimaModificacion;
    }

}
