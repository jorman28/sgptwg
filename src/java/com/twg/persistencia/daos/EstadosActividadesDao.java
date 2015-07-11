/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twg.persistencia.daos;

import com.twg.persistencia.beans.EstadosActividadesBean;
import com.twg.persistencia.beans.UsuariosBean;
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
 * @author Jorman
 */
public class EstadosActividadesDao {

    private final EstadosActividadesSql sql = new EstadosActividadesSql();

    public EstadosActividadesDao() {
    }

    public List<EstadosActividadesBean> consultarEstadosActividades() throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<EstadosActividadesBean> listaEstadosActividades = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarEstadosActividades());
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
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

    public List<EstadosActividadesBean> consultarEstadosActividades(Integer id, String nombre) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<EstadosActividadesBean> listaEstadosActividades = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarEstadosActividades(id, nombre));
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
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

    public List<EstadosActividadesBean> consultarEstadosActividades(Integer id) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        return consultarEstadosActividades(id, null);
    }
    
    public int insertarEstadoActividad(EstadosActividadesBean estadoActividad) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarEstadoActividad());
        ps.setInt(1, estadoActividad.getId());
        ps.setString(2, estadoActividad.getNombre());
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }

    public int actualizarEstadoActividad(EstadosActividadesBean estadoActividad) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actualizarEstadoActividad());
        ps.setString(1, estadoActividad.getNombre());
        ps.setInt(2, estadoActividad.getId());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }

    public int eliminarEstadoActividad(Integer id) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
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
