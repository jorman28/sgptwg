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

    public String consultarActividades(Integer id, Integer version, String descripcion, Date fecha_estimada_inicio, Date fecha_estimada_terminacion, Date fecha_real_inicio, Date fecha_real_terminacion, Integer tiempo_estimado, Integer tiempo_invertido, Integer estado) {
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
        if (fecha_estimada_inicio != null && !fecha_estimada_inicio.toString().isEmpty()) {
            sql += "AND fecha_estimada_inicio = '" + fecha_estimada_inicio + "' ";
        }
        if (fecha_estimada_terminacion != null && !fecha_estimada_terminacion.toString().isEmpty()) {
            sql += "AND fecha_estimada_terminacion = '" + fecha_estimada_terminacion + "' ";
        }
        if (fecha_real_inicio != null && !fecha_real_inicio.toString().isEmpty()) {
            sql += "AND fecha_real_inicio = '" + fecha_real_inicio + "' ";
        }
        if (fecha_real_terminacion != null && !fecha_real_terminacion.toString().isEmpty()) {
            sql += "AND fecha_real_terminacion = '" + fecha_real_terminacion + "' ";
        }
        if (tiempo_estimado != null && !tiempo_estimado.toString().isEmpty()) {
            sql += "AND tiempo_estimado = '" + tiempo_estimado + "' ";
        }
        if (tiempo_invertido != null && !tiempo_invertido.toString().isEmpty()) {
            sql += "AND tiempo_invertido = '" + tiempo_invertido + "' ";
        }
        if (estado != null && !estado.toString().isEmpty()) {
            sql += "AND estado = '" + estado + "' ";
        }
        return sql;
    }
    
    public String consultarActividades(String id, Integer version, String descripcion, Date fecha_estimada_inicio, Date fecha_estimada_terminacion, Date fecha_real_inicio, Date fecha_real_terminacion, Integer estado) {
        String sql = "SELECT * FROM actividades WHERE fecha_eliminacion IS NULL ";
        if (id != null && !id.equals("")) {
            sql += "AND id in (" + id + ") ";
        }
        if (version != null && !version.toString().isEmpty()) {
            sql += "AND version = '" + version + "' ";
        }
        if (descripcion != null && !descripcion.isEmpty()) {
            sql += "AND descripcion LIKE '%" + descripcion + "%' ";
        }
        if (fecha_estimada_inicio != null && !fecha_estimada_inicio.toString().isEmpty()) {
            sql += "AND fecha_estimada_inicio = '" + fecha_estimada_inicio + "' ";
        }
        if (fecha_estimada_terminacion != null && !fecha_estimada_terminacion.toString().isEmpty()) {
            sql += "AND fecha_estimada_terminacion = '" + fecha_estimada_terminacion + "' ";
        }
        if (fecha_real_inicio != null && !fecha_real_inicio.toString().isEmpty()) {
            sql += "AND fecha_real_inicio = '" + fecha_real_inicio + "' ";
        }
        if (fecha_real_terminacion != null && !fecha_real_terminacion.toString().isEmpty()) {
            sql += "AND fecha_real_terminacion = '" + fecha_real_terminacion + "' ";
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
