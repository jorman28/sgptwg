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
public class Actividades_EmpleadosSql {
    
    public Actividades_EmpleadosSql(){
    }
    
    public String consultarEmpleadosxAtividad(Integer empleado) {
        return "SELECT actividad, empleado FROM actividades_empleados WHERE 1 = 1 AND empleado = "+empleado;
    }
}
