/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.twg.persistencia.sqls;

/**
 * Esta clase define métodos para contruír los SQLs utilizados en el DAO.
 * 
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class TiposDocumentosSql {
    
    /**
     * Constructor de la clase.
     */
    public TiposDocumentosSql(){
    }
    
    /**
     * Método encargado de retornar el SQL para consultar todos los tipos
     * de documentos.
     * 
     * @return El SQL de la sentencia de base de datos
     */
    public String consultarTiposDocumentos(){
        return "SELECT tipo, nombre FROM tipos_documentos";
    }
    
    /**
     * Método encargado de retornar el SQL para insertar un nuevo tipo de 
     * documento.
     * @return El SQL de la sentencia de base de datos
     */
    public String insertarTipoDocumento(){
        return "INSERT INTO tipos_documentos (tipo, nombre) VALUES (?, ?)";
    }
    
    /**
     * Método encargado de retornar el SQL para actualizar un tipo de 
     * documento existente.
     * @return El SQL de la sentencia de base de datos
     */
    public String actualizarTipoDocumento(){
        return "UPDATE tipos_documentos SET nombre = ? WHERE tipo = ?";
    }
    
    /**
     * Método encargado de retornar el SQL para eliminar 
     * @return El SQL de la sentencia de base de datos
     */
    public String eliminarTipoDocumento(){
        return "DELETE FROM tipos_documentos WHERE tipo = ?";
    }
}
