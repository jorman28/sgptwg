/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twg.persistencia.sqls;

/**
 *
 * @author Jorman
 */
public class EstadosActividadesSql {

    public EstadosActividadesSql() {
    }

    public String consultarEstadosActividades() {
        return "SELECT * FROM estados_actividades";
    }

    public String consultarEstadosActividades(String nombre) {
        String sql = "SELECT * FROM estados_actividades";

        if (nombre != null) {
            sql += "Where nombre = " + nombre + "";
        } 
        return sql;
    }

    public String insertarEstadoActividad() {
        return "INSERT INTO estados_actividades (nombre) VALUES (?)";
    }

    public String actualizarEstadoActividad() {
        return "UPDATE estados_actividades SET nombre = ? WHERE id = ?";
    }

    public String eliminarEstadoActividad() {
        return "DELETE FROM estados_actividades WHERE id = ?";
    }
}
