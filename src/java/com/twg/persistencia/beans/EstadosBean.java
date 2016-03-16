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
    private Integer estado_prev;
    private Integer estado_sig;
    private String e_final;

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
        return estado_prev;
    }

    public void setEstadoPrev(Integer estado_prev) {
        this.estado_prev = estado_prev;
    }

    public Integer getEstadoSig() {
        return estado_sig;
    }

    public void setEstadoSig(Integer estado_sig) {
        this.estado_sig = estado_sig;
    }

    public String geteFinal() {
        return e_final;
    }

    public void seteFinal(String e_final) {
        this.e_final = e_final;
    }
}
