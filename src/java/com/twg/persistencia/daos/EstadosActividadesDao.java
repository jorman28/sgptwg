package com.twg.persistencia.daos;

import com.twg.persistencia.beans.EstadosActividadesBean;
import com.twg.persistencia.sqls.EstadosActividadesSql;
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
public class EstadosActividadesDao {
    private final EstadosActividadesSql sql = new EstadosActividadesSql();
    
    public EstadosActividadesDao(){
    }

    public List<EstadosActividadesBean> consultarEstadosActividades() throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        return consultarEstadosActividades(null, null);
    }
    
    public List<EstadosActividadesBean> consultarEstadosActividades(String nombre) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        return consultarEstadosActividades(null, nombre);
    }
    
    public List<EstadosActividadesBean> consultarEstadosActividades(Integer id) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        return consultarEstadosActividades(id, null);
    }
    
    public List<EstadosActividadesBean> consultarEstadosActividades(Integer id, String nombre) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        List<EstadosActividadesBean> listaEstadosActividades = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarEstadosActividades(id, nombre));
        ResultSet rs;
        rs = ps.executeQuery();
        while(rs.next()){
            EstadosActividadesBean estadoActividad = new EstadosActividadesBean();
            estadoActividad.setId(rs.getInt("id"));
            estadoActividad.setNombre(rs.getString("nombre"));            
            listaEstadosActividades.add(estadoActividad);
        }
        rs.close();
        ps.close();
        con.close();
        return listaEstadosActividades;
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
    
    public int insertarEstadoActividad(EstadosActividadesBean estadoActividad) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarEstadoActividad());
        //ps.setInt(1, estadoActividad.getId());
        ps.setString(1, estadoActividad.getNombre());
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }
    
    public int actualizarEstadoActividad(EstadosActividadesBean estadoActividad) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actualizarEstadoActividad());
        ps.setInt(2, estadoActividad.getId());
        ps.setString(1, estadoActividad.getNombre());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }
    
    public int eliminarEstadoActividad(Integer id) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.eliminarEstadoActividad());
        ps.setInt(1, id);
        int eliminacion = ps.executeUpdate();
        ps.close();
        con.close();
        return eliminacion;
    }
}