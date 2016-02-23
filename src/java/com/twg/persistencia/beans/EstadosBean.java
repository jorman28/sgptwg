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
    private String fecha_eliminacion;
    private String tipo_estado;
    private Integer estadoPrev;
    private Integer estadoSig;
    private String eFinal;

    public EstadosBean(){
    }

    public String getFecha_eliminacion() {
        return fecha_eliminacion;
    }

    public void setFecha_eliminacion(String fecha_eliminacion) {
        this.fecha_eliminacion = fecha_eliminacion;
    }

    public String getTipo_estado() {
        return tipo_estado;
    }

    public void setTipo_estado(String tipo_estado) {
        this.tipo_estado = tipo_estado;
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
    
    public Integer getEstadoPrev() {
        return estadoPrev;
    }

    public void setEstadoPrev(Integer estadoPrev) {
        this.estadoPrev = estadoPrev;
    }

    public Integer getEstadoSig() {
        return estadoSig;
    }

    public void setEstadoSig(Integer estadoSig) {
        this.estadoSig = estadoSig;
    }

    public String geteFinal() {
        return eFinal;
    }

    public void seteFinal(String eFinal) {
        this.eFinal = eFinal;
    }
}
