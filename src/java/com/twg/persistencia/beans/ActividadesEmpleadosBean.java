package com.twg.persistencia.beans;

import java.util.Date;

/**
 * Esta clase define los atributos del objeto ActividadesEmpleados, junto con
 * los métodos get y set.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class ActividadesEmpleadosBean {

    private Integer actividad;
    private String nombreActividad;
    private String estado;
    private Integer empleado;
    private String tipoDocumento;
    private String documento;
    private String nombrePersona;
    private String cargo;
    private Date fechaEstimadaInicio;
    private Date fechaEstimadaTerminacion;
    private Double tiempoEstimado;
    private Double tiempoInvertido;
    private Date fechaEliminacion;

    /*
     * Declaración de métodos Get y Set para cada atributo de la clase.
     */
    public Integer getActividad() {
        return actividad;
    }

    public void setActividad(Integer actividad) {
        this.actividad = actividad;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Integer empleado) {
        this.empleado = empleado;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
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

    public Double getTiempoInvertido() {
        return tiempoInvertido;
    }

    public void setTiempoInvertido(Double tiempoInvertido) {
        this.tiempoInvertido = tiempoInvertido;
    }

    public Date getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setFechaEliminacion(Date fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }
    
}
