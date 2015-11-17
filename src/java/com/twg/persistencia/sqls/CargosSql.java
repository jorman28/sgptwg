package com.twg.persistencia.sqls;

/**
 *
 * @author Erika Jhoana
 */
public class CargosSql {
    public CargosSql(){
    }
    
    public String consultarCargos(String nombre){
        String sql = "SELECT id, nombre FROM cargos WHERE 1 = 1 ";
        if(nombre != null && !nombre.isEmpty()){
            sql = "AND nombre like '%"+nombre+"%'";
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
        return "UPDATE cargos SET fecha_eliminacion = now() WHERE id = ?";
    }
}
