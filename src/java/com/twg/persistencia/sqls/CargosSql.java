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
    
    public String consultarCargos(String param){
        String sql = "";
        if(param==null || param.isEmpty()){
            sql = "SELECT id, nombre FROM cargos";
        }else{
            sql = "SELECT id, nombre FROM cargos WHERE nombre like '%"+param+"%'";
        }
        return sql;
    }
    
    public String consultarCargo(int id){
        return "SELECT id, nombre FROM cargos WHERE id = "+id;
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
