/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.twg.persistencia.beans;

/**
 *
 * @author Jorman
 */
public class EstadosBean {
    
    private Integer id;
    private String nombre;    
    private String fechaEliminacion;
    private String tipoEstado;
    private Integer estadoPrevio;
    private Integer estadoSiguiente;
    private String estadoFinal;

    public EstadosBean(){
    }

    public String getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setFechaEliminacion(String fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }

    public String getTipoEstado() {
        return tipoEstado;
    }

    public void setTipoEstado(String tipoEstado) {
        this.tipoEstado = tipoEstado;
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
    
    public Integer getEstadoPrevio() {
        return estadoPrevio;
    }

    public void setEstadoPrevio(Integer estadoPrevio) {
        this.estadoPrevio = estadoPrevio;
    }

    public Integer getEstadoSiguiente() {
        return estadoSiguiente;
    }

    public void setEstadoSiguiente(Integer estadoSiguiente) {
        this.estadoSiguiente = estadoSiguiente;
    }

    public String getEstadoFinal() {
        return estadoFinal;
    }

    public void setEstadoFinal(String estadoFinal) {
        this.estadoFinal = estadoFinal;
    }
}
