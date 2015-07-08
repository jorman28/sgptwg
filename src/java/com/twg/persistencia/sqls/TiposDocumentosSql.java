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
public class TiposDocumentosSql {
    public TiposDocumentosSql(){
    }
    
    public String consultarTiposDocumentos(){
        return "SELECT tipo, nombre FROM tipos_documentos";
    }
    
    public String insertarTipoDocumento(){
        return "INSERT INTO tipos_documentos (tipo, nombre) VALUES (?, ?)";
    }
    
    public String actualizarTipoDocumento(){
        return "UPDATE tipos_documentos SET nombre = ? WHERE tipo = ?";
    }
    
    public String eliminarTipoDocumento(){
        return "DELETE FROM tipos_documentos WHERE tipo = ?";
    }
}
