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

    public String consultarEstadosActividades(Integer id, String nombre) {
        String sql = "";
        if ("".equals(nombre)) nombre = null;
        if (id == null && nombre == null) {
            sql += "SELECT * FROM estados_actividades";
        } else {
            if (id != null) {
                sql += "SELECT * FROM estados_actividades Where id = " + id + "";
            } else {
                if (nombre != null) {
                    sql += "SELECT * FROM estados_actividades Where nombre = '" + nombre + "'";
                }
            }
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
    
    public String consultarId(String nombre){
        return "SELECT id FROM estados_actividades WHERE nombre = '"+nombre+"'";
    }
}