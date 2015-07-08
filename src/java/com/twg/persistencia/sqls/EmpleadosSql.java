/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.twg.persistencia.sqls;

/**
 *
 * @author Erika Jhoana
 */
public class EmpleadosSql {
    public EmpleadosSql(){
    }
    
    public String consultarEmpleados(){
        return "SELECT id_persona, cargo FROM empleados";
    }
    
    public String insertarEmpleado(){
        return "INSERT INTO empleados (id_persona, cargo) VALUES (?, ?)";
    }
    
    public String actualizarEmpleado(){
        return "UPDATE empleados SET cargo = ? WHERE id_persona = ?";
    }
    
    public String eliminarEmpleado(){
        return "DELETE FROM empleados WHERE id_persona = ?";
    }
}
