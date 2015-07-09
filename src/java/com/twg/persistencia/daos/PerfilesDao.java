package com.twg.persistencia.daos;

import com.twg.persistencia.beans.PerfilesBean;
import com.twg.persistencia.sqls.PerfilesSql;
import com.twg.utilidades.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pipe
 */
public class PerfilesDao {
    
    private final PerfilesSql sql = new PerfilesSql();
    
    public PerfilesDao(){
    }

    public List<PerfilesBean> consultarPerfiles() throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        List<PerfilesBean> listaPerfiles = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarPerfiles());
        ResultSet rs;
        rs = ps.executeQuery();
        while(rs.next()){
            PerfilesBean perfil = new PerfilesBean();
            perfil.setId(rs.getInt("id"));
            perfil.setNombre(rs.getString("nombre"));
            
            listaPerfiles.add(perfil);
        }
        rs.close();
        ps.close();
        con.close();
        return listaPerfiles;
    }
    
    public int insertarPerfil(PerfilesBean perfil) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarPerfil());
        ps.setString(1, perfil.getNombre());
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }
    
    public int actualizarPerfil(PerfilesBean perfil) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actualizarPerfil());
        ps.setString(1, perfil.getNombre());
        ps.setInt(2, perfil.getId());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }
    
    public int eliminarUsuario(Integer idPerfil) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.eliminarPerfil());
        ps.setInt(1, idPerfil);
        int eliminacion = ps.executeUpdate();
        ps.close();
        con.close();
        return eliminacion;
    }
}
