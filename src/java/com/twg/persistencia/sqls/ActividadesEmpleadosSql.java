/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twg.persistencia.sqls;

import java.util.List;

/**
 *
 * @author erikasta07
 */
public class ActividadesEmpleadosSql {
    
    public ActividadesEmpleadosSql(){
    }
       
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
    
    public String insertarActividad_Empleado() {
        return "INSERT INTO actividades_empleados (actividad, empleado) VALUES (? , ?)";
    }
    
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
    
    public String eliminarActividades_Empleados() {
        return "DELETE FROM actividades_empleados WHERE 1 = 1 AND actividad = ? AND empleado NOT IN (?)";
    }
}
