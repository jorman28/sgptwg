/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twg.persistencia.sqls;

/**
 * Esta clase define métodos para contruír los SQLs utilizados en el DAO.
 * 
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class EstadosSql {

    /**
     * Constructor de la clase.
     */
    public EstadosSql() {
    }

    /**
     * Método encargado de retornar el SQL para consultar todos los estados.
     * @return 
     */
    public String consultarEstados() {
        return "SELECT * FROM estados WHERE fecha_eliminacion IS NULL ORDER BY nombre";
    }
    
    /**
     * Método encargado de retornar el SQL para consultar sólo por estados
     * previos o siguientes.
     * @param id
     * @return 
     */
    public String consultarEstadosPS(Integer id) {
        return "SELECT * FROM estados WHERE estado_previo = " + id + " OR estado_siguiente = " + id;
    }

    /**
     * Método encargado de consultar los estados, aplicando diferentes filtros
     * según los parámetros que lleguen distintos de nulos.
     * 
     * @param id
     * @param tipoEstado
     * @param nombre
     * @param estadoPrev
     * @param estadoSig
     * @param eFinal
     * @return 
     */
    public String consultarEstados(Integer id, String tipoEstado, String nombre, Integer estadoPrev, 
            Integer estadoSig, String eFinal) {
        String sql = "SELECT * FROM estados WHERE fecha_eliminacion IS NULL ";
        if (id != null) {
            sql += " AND id = " + id + " ";
        }
        if (tipoEstado != null && !tipoEstado.isEmpty()) {
            sql += " AND tipo_estado = '" + tipoEstado + "' ";
        }
        if (nombre != null && !nombre.isEmpty()) {
            sql += " AND nombre LIKE '%" + nombre + "%'";
        }
        if (estadoPrev != null && estadoPrev != 0) {
            sql += " AND estado_previo = " + estadoPrev;
        }
        if (estadoSig != null && estadoSig != 0) {
            sql += " AND estado_siguiente = " + estadoSig;
        }
        if (eFinal != null && !eFinal.isEmpty() && !eFinal.equals("0")) {
            sql += " AND estado_final = '" + eFinal + "'";
        }
        sql += " ORDER BY nombre";
        return sql;
    }

    /**
     * Método encargado de retornar el SQL para insertar un nuevo estado.
     * @return 
     */
    public String insertarEstado() {
        return "INSERT INTO estados (tipo_estado, nombre, estado_previo, estado_siguiente, estado_final) VALUES (?, ?, ?, ?, ?)";
    }

    /**
     * Método encargado de retornar el SQL para actualizar la información de un
     * estado existente.
     * @return 
     */
    public String actualizarEstado() {
        return "UPDATE estados SET tipo_estado=?, nombre = ?, estado_previo = ?, estado_siguiente = ?, estado_final = ? WHERE id = ?";
    }

    /**
     * Método encargado de retornar el SQL para eliminar lógicamente un estado, 
     * actualizando la fecha de eliminación con la fecha actual.
     * @return 
     */
    public String eliminarEstado() {
        return "UPDATE estados SET fecha_eliminacion = now() WHERE id = ?";
    }

    /**
     * Método encargado de retornar el SQL para consultar un estado específico.
     * @param nombre
     * @return 
     */
    public String consultarId(String nombre) {
        return "SELECT id FROM estados WHERE nombre = '" + nombre + "'";
    }
}
