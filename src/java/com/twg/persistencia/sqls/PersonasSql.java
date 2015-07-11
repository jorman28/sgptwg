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
    
    public String consultarPersonas(int id, String documento, String tipo_documento, String nombres, String apellidos,
                            String telefono, String celular, String correo, String direccion, String usuario, String perfil){
        String sql = "";
        
        sql += "select p.id, p.documento, p.nombres, p.apellidos, p.telefono, p.celular, p.correo, p.direccion, d.nombre as tipo_documento, u.usuario, pf.nombre as perfil " +
                "from personas p " +
                "inner join tipos_documentos d on d.tipo=p.tipo_documento " +
                "left join usuarios u on p.id=u.id_persona " +
                "left join perfiles pf on pf.id=u.perfil " +
                "where 1=1 ";
        if(documento!=null && !documento.isEmpty()){
            sql += "and p.documento like '%"+documento+"%' ";
        }
        if(tipo_documento!=null && !tipo_documento.isEmpty() && !tipo_documento.endsWith("0")){
            sql += "and d.nombre = '"+ tipo_documento +"' ";
        }
        if(nombres!=null && !nombres.isEmpty()){
            sql += "and p.nombres like '%"+nombres+"%' ";
        }
        if(apellidos!=null && !apellidos.isEmpty()){
            sql += "and p.apellidos like '%"+apellidos+"%' ";
        }
        if(telefono!=null && !telefono.isEmpty()){
            sql += "and p.telefono like '%"+telefono+"%' ";
        }
        if(celular!=null && !celular.isEmpty()){
            sql += "and p.celular like '%"+celular+"%' ";
        }
        if(correo!=null && !correo.isEmpty()){
            sql += "and p.correo like '%"+correo+"%' ";
        }
        if(direccion!=null && !direccion.isEmpty()){
            sql += "and p.direccion like '%"+direccion+"%' ";
        }
        if(usuario!=null && !usuario.isEmpty()){
            sql += "and u.usuario like '%"+usuario+"%' ";
        }
        if(perfil!=null && !perfil.isEmpty() && !perfil.endsWith("0")){
            sql += "and pf.nombre like '%"+perfil+"%' ";
        }
        
        return sql;
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
    
    public String consultarIdPersona(String documento, String tipoDocumento){
        return "SELECT id FROM personas WHERE documento = '"+documento+"' AND tipo_documento = '"+tipoDocumento+"'";
    }
}
