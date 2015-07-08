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
public class ClientesSql {
    public ClientesSql(){
    }
    
    public String consultarClientes(){
        return "SELECT id_persona, fecha_inicio FROM clientes";
    }
    
    public String insertarCliente(){
        return "INSERT INTO clientes (id_persona, fecha_inicio) VALUES (?, ?)";
    }
    
    public String actualizarCliente(){
        return "UPDATE clientes SET fecha_inicio = ? WHERE id_persona = ?";
    }
    
    public String eliminarCliente(){
        return "DELETE FROM clientes WHERE id_persona = ?";
    }
}
