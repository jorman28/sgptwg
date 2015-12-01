/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twg.persistencia.beans;

import java.util.Date;

/**
 *
 * @author Jorman Rincón
 */
public class ActividadesBean {

    private Integer id;
    private Integer version;
    private String descripcion;
    private Date fecha_estimada_inicio;
    private Date fecha_estimada_terminacion;
    private Date fecha_real_inicio;
    private Date fecha_real_terminacion;
    private Date tiempo_estimado;
    private Date tiempo_invertido;
    private Integer estado;
    private Date fecha_eliminacion;

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

    public Date getTiempo_estimado() {
        return tiempo_estimado;
    }

    public void setTiempo_estimado(Date tiempo_estimado) {
        this.tiempo_estimado = tiempo_estimado;
    }

    public Date getTiempo_invertido() {
        return tiempo_invertido;
    }

    public void setTiempo_invertido(Date tiempo_invertido) {
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

}
