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
public class EstadosVersionesSql {

    public EstadosVersionesSql() {
    }
    
    public String consultarEstadosActividades(Integer id, String nombre) {
        String sql = "";

        if (id != null && nombre != null) {
            sql += "SELECT * FROM estados_versiones";
        } else {
            if (id != null) {
                sql += "SELECT * FROM estados_versiones Where id = " + id + " ";
            } else {
                if (nombre != null) {
                    sql += "SELECT * FROM estados_versiones Where nombre = " + nombre + " ";
                }
            }
        }
        return sql;
    }

    public String insertarEstadoVersion() {
        return "INSERT INTO estados_versiones (id, nombre) VALUES (?, ?)";
    }

    public String actualizarEstadoVersion() {
        return "UPDATE estados_versiones SET nombre = ? WHERE id = ?";
    }

    public String eliminarEstadoVersion() {
        return "DELETE FROM estados_versiones WHERE id = ?";
    }
}
