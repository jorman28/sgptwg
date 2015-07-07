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
public class PersonasSql {
    public PersonasSql(){
    }
    
    public String consultarPersonas(){
        return "SELECT * FROM personas";
    }
    
    public String insertarPersona(){
        return "INSERT INTO personas (id, documento, tipo_documento, nombres, apellidos, direccion, "
                + "telefono, celular, correo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }
    
    public String actualizarPersona(){
        return "UPDATE personas SET documento = ?, tipo_documento = ?, nombres = ?, apellidos = ?, "
                + "direccion = ?, telefono = ?, celular = ?, correo = ? WHERE id = ?";
    }
    
    public String eliminarPersona(){
        return "DELETE FROM personas WHERE id = ?";
    }
}
