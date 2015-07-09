package com.twg.persistencia.sqls;

/**
 *
 * @author Pipe
 */
public class PerfilesSql {
    
    public PerfilesSql(){
    }
    
    public String consultarPerfiles(){
        return "SELECT * FROM perfiles";
    }

    public String insertarPerfil(){
        return "INSERT INTO perfiles (nombre) VALUES (?)";
    }
    
    public String actualizarPerfil(){
        return "UPDATE perfiles SET nombre = ? WHERE id = ?";
    }
    
    public String eliminarPerfil(){
        return "DELETE FROM perfiles WHERE id = ?";
    }
}
