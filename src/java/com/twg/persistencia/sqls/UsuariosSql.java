package com.twg.persistencia.sqls;

/**
 *
 * @author Pipe
 */
public class UsuariosSql {
    
    public UsuariosSql(){
    }
    
    public String consultarUsuarios(){
        return "SELECT * FROM usuarios";
    }
    
    public String insertarUsuario(){
        return "INSERT INTO usuarios (id_persona, usuario, clave, perfil) VALUES (?, ?, ?, ?)";
    }
    
    public String actualizarUsuario(){
        return "UPDATE usuarios SET usuario = ?, clave = ?, perfil = ? WHERE id_persona = ?";
    }
    
    public String eliminarUsuario(){
        return "DELETE FROM usuarios WHERE id_persona = ?";
    }

}
