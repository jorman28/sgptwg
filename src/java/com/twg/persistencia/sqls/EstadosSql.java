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
public class EstadosSql {

    public EstadosSql() {
    }

    public String consultarEstados() {
        return "SELECT * FROM estados WHERE fecha_eliminacion IS NULL ";
    }

    public String consultarEstados(Integer id, String tipoEstado, String nombre) {
        String sql = "SELECT * FROM estados WHERE fecha_eliminacion IS NULL ";
        if (id != null) {
            sql += "AND id = " + id + " ";
        }
        if (tipoEstado != null && !tipoEstado.isEmpty()) {
            sql += "AND tipo_estado = '" + tipoEstado + "' ";
        }
        if (nombre != null && !nombre.isEmpty()) {
            sql += "AND nombre LIKE '%" + nombre + "%'";
        }
        return sql;
    }

    public String insertarEstado() {
        return "INSERT INTO estados (tipo_estado, nombre) VALUES (?, ?)";
    }

    public String actualizarEstado() {
        return "UPDATE estados SET tipo_estado=?, nombre = ? WHERE id = ?";
    }

    public String eliminarEstado() {
        return "DELETE FROM estados WHERE id = ?";
    }

    public String consultarId(String nombre) {
        return "SELECT id FROM estados WHERE nombre = '" + nombre + "'";
    }
}
