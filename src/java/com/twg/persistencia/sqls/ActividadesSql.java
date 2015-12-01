/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twg.persistencia.sqls;

import java.util.Date;

/**
 *
 * @author Jorman Rincón
 */
public class ActividadesSql {

    public ActividadesSql() {
    }

    public String consultarActividades() {
        return "SELECT * FROM actividades WHERE fecha_eliminacion IS NULL ";
    }

    public String consultarActividades(Integer id, Integer version, String descripcion, Date fechaEstimadaInicio, Date fechaEstimadaTerminacion, Date fechaRealInicio, Date fechaRealTerminacion, Date tiempoEstimado, Date tiempoInvertido, Integer estado) {
        String sql = "SELECT * FROM actividades WHERE fecha_eliminacion IS NULL ";
        if (id != null) {
            sql += "AND id = " + id + " ";
        }
        if (version != null && !version.toString().isEmpty()) {
            sql += "AND version = '" + version + "' ";
        }
        if (descripcion != null && !descripcion.isEmpty()) {
            sql += "AND descripcion LIKE '%" + descripcion + "%' ";
        }
        if (fechaEstimadaInicio != null && !fechaEstimadaInicio.toString().isEmpty()) {
            sql += "AND fecha_estimada_inicio = '" + fechaEstimadaInicio + "' ";
        }
        if (fechaEstimadaTerminacion != null && !fechaEstimadaTerminacion.toString().isEmpty()) {
            sql += "AND fecha_estimada_terminacion = '" + fechaEstimadaTerminacion + "' ";
        }
        if (fechaRealInicio != null && !fechaRealInicio.toString().isEmpty()) {
            sql += "AND fecha_real_inicio = '" + fechaRealInicio + "' ";
        }
        if (fechaRealTerminacion != null && !fechaRealTerminacion.toString().isEmpty()) {
            sql += "AND fecha_real_terminacion = '" + fechaRealTerminacion + "' ";
        }
        if (tiempoEstimado != null && !tiempoEstimado.toString().isEmpty()) {
            sql += "AND tiempo_estimado = '" + tiempoEstimado.getTime() + "' ";
        }
        if (tiempoInvertido != null && !tiempoInvertido.toString().isEmpty()) {
            sql += "AND tiempo_invertido = '" + tiempoInvertido.getTime() + "' ";
        }
        if (estado != null && !estado.toString().isEmpty()) {
            sql += "AND estado = '" + estado + "' ";
        }
        return sql;
    }

    public String insertarActividad() {
        return "INSERT INTO actividades (version, descripcion, fecha_estimada_inicio, fecha_estimada_terminacion, fecha_real_inicio, fecha_real_terminacion, tiempo_estimado, tiempo_invertido, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    public String actualizarActividad() {
        return "UPDATE actividades SET version=?, descripcion = ?, fecha_estimada_inicio=?, fecha_estimada_terminacion=?, fecha_real_inicio=?, fecha_real_terminacion=?, tiempo_estimado=?, tiempo_invertido=?, estado=?  WHERE id = ?";
    }

    public String eliminarActividad() {
        return "UPDATE actividades SET fecha_eliminacion = now() WHERE id = ?";
    }

}
