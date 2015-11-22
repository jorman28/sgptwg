package com.twg.persistencia.daos;

import com.twg.persistencia.beans.EstadosBean;
import com.twg.persistencia.sqls.EstadosSql;
import com.twg.utilidades.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jorman Rincón
 */
public class EstadosDao {
    private final EstadosSql sql = new EstadosSql();
    
    public EstadosDao(){
    }

    public List<EstadosBean> consultarEstados() throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        return consultarEstados(null, null);
    }
    
    public List<EstadosBean> consultarEstados(String nombre) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        return consultarEstados(null, nombre);
    }
    
    public List<EstadosBean> consultarEstados(Integer id) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        return consultarEstados(id, null);
    }
    
    public List<EstadosBean> consultarEstados(Integer id, String nombre) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        List<EstadosBean> listaEstados = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarEstados(id, nombre));
        ResultSet rs;
        rs = ps.executeQuery();
        while(rs.next()){
            EstadosBean estado = new EstadosBean();
            estado.setId(rs.getInt("id"));
            estado.setNombre(rs.getString("nombre"));            
            listaEstados.add(estado);
        }
        rs.close();
        ps.close();
        con.close();
        return listaEstados;
    }
    
    public Integer consultarId(String nombre) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Integer idPersona = null;
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarId(nombre));
        ResultSet rs;
        rs = ps.executeQuery();
        if(rs.next()){
            idPersona = rs.getInt("id");
        }
        rs.close();
        ps.close();
        con.close();
        return idPersona;
    }
    
    public int insertarEstado(EstadosBean estado) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarEstado());
        //ps.setInt(1, estado.getId());
        ps.setString(1, estado.getNombre());
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }
    
    public int actualizarEstado(EstadosBean estado) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actualizarEstado());
        ps.setInt(2, estado.getId());
        ps.setString(1, estado.getNombre());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }
    
    public int eliminarEstado(Integer id) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.eliminarEstado());
        ps.setInt(1, id);
        int eliminacion = ps.executeUpdate();
        ps.close();
        con.close();
        return eliminacion;
    }
}