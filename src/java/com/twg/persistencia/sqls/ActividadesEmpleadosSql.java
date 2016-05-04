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
public class ActividadesEmpleadosSql {
    
    /**
     * Constructor de la clase
     */
    public ActividadesEmpleadosSql(){
    }
    
    /**
     * Método encargado de retornar el SQL para consultar las actividades de 
     * un empleado específico.
     * @param idActividad
     * @param idEmpleado
     * @return 
     */
    public String consultarEmpleadosxActividad(Integer idActividad, Integer idEmpleado) {
        String sql = "SELECT * FROM actividades_empleados WHERE 1 = 1 ";
        if (idActividad != null && !idActividad.toString().isEmpty()) {
            sql += "AND actividad = " + idActividad + " ";
        }
        if (idEmpleado != null && !idEmpleado.toString().isEmpty()) {
            sql += "AND empleado = '" + idEmpleado + "'";
        }
        return sql;
    }
    
    /**
     * Método encargado de retornar el SQL para insertar una actividad a un
     * empleado específico
     * @return 
     */
    public String insertarActividad_Empleado() {
        return "INSERT INTO actividades_empleados (actividad, empleado) VALUES (? , ?)";
    }
    
    /**
     * Método encargado de retornar el SQL para eliminar lógicamente una actividad
     * de un empleado, actualizando la fecha de eliminación con la fecha actual.
     * @param idActividad
     * @param idEmpleado
     * @return 
     */
    public String eliminarActividad_Empleado(Integer idActividad, Integer idEmpleado) {
        String sql = "UPDATE actividades_empleados SET fecha_eliminacion = now() WHERE 1 = 1 ";
        if (idActividad != null && !idActividad.toString().isEmpty()) {
            sql += "AND actividad = " + idActividad + " ";
        }
        if (idEmpleado != null && !idEmpleado.toString().isEmpty()) {
            sql += "AND empleado = '" + idEmpleado + "'";
        }
        return sql;
    }
    
    /**
     * Método encargado de retornar el SQL para eliminar todas las actividades
     * de los empleados diferentes al que se envíe como parámetro.
     * @return 
     */
    public String eliminarActividades_Empleados() {
        return "DELETE FROM actividades_empleados WHERE 1 = 1 AND actividad = ? AND empleado NOT IN (?)";
    }
}
