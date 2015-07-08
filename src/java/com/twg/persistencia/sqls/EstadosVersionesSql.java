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

    public String consultarEstadosVersiones() {
        return "SELECT * FROM estados_versiones";
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
