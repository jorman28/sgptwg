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
public class CargosSql {
    public CargosSql(){
    }
    
    public String consultarCargos(){
        return "SELECT * FROM cargos";
    }
    
    public String insertarCargo(){
        return "INSERT INTO cargos (id, nombre) VALUES (?, ?)";
    }
    
    public String actualizarCargo(){
        return "UPDATE cargos SET nombre = ? WHERE id = ?";
    }
    
    public String eliminarCargo(){
        return "DELETE FROM cargos WHERE id = ?";
    }
}
