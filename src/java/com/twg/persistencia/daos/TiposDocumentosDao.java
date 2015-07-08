/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.twg.persistencia.daos;

import com.twg.persistencia.beans.TiposDocumentosBean;
import com.twg.persistencia.sqls.TiposDocumentosSql;
import com.twg.utilidades.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Erika Jhoana
 */
public class TiposDocumentosDao {
    private final TiposDocumentosSql sql = new TiposDocumentosSql();
    
    public TiposDocumentosDao(){
    }

    public List<TiposDocumentosBean> consultarTiposDocumentos() throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        List<TiposDocumentosBean> listaTiposDocumentos = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarTiposDocumentos());
        ResultSet rs;
        rs = ps.executeQuery();
        while(rs.next()){
            TiposDocumentosBean tipoDocumento = new TiposDocumentosBean();
            tipoDocumento.setTipo(rs.getString("tipo"));
            tipoDocumento.setNombre(rs.getString("nombre"));
            
            listaTiposDocumentos.add(tipoDocumento);
        }
        rs.close();
        ps.close();
        con.close();
        return listaTiposDocumentos;
    }
    
    public int insertarTipoDocumento(TiposDocumentosBean tipoDocumento) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarTipoDocumento());
        ps.setString(1, tipoDocumento.getTipo());
        ps.setString(2, tipoDocumento.getNombre());
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }
    
    public int actualizarTipoDocumento(TiposDocumentosBean tipoDocumento) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actualizarTipoDocumento());
        ps.setString(1, tipoDocumento.getNombre());
        ps.setString(2, tipoDocumento.getTipo());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }
    
    public int eliminarTipoDocumento(Integer tipoDocumento) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.eliminarTipoDocumento());
        ps.setInt(1, tipoDocumento);
        int eliminacion = ps.executeUpdate();
        ps.close();
        con.close();
        return eliminacion;
    }
}
