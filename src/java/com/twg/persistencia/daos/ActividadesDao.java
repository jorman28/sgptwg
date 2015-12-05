/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twg.persistencia.daos;

import com.twg.persistencia.beans.ActividadesBean;
import com.twg.persistencia.sqls.ActividadesSql;
import com.twg.utilidades.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jorman Rincón
 */
public class ActividadesDao {

    private final ActividadesSql sql = new ActividadesSql();

    public List<ActividadesBean> consultarActividades(Integer id, Integer version, String descripcion, java.util.Date fecha_estimada_inicio, java.util.Date fecha_estimada_terminacion, java.util.Date fecha_real_inicio, java.util.Date fecha_real_terminacion, Integer tiempo_estimado, Integer tiempo_invertido, Integer estado) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<ActividadesBean> listaActividades = new ArrayList();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarActividades(id, version, descripcion, fecha_estimada_inicio, fecha_estimada_terminacion, fecha_real_inicio, fecha_real_terminacion, tiempo_estimado, tiempo_invertido, estado));
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
            ActividadesBean actividad = new ActividadesBean();
            actividad.setId(rs.getInt("id"));
            actividad.setVersion(rs.getInt("version"));
            actividad.setDescripcion(rs.getString("descripcion"));
            actividad.setFecha_estimada_inicio(rs.getDate("fecha_estimada_inicio"));
            actividad.setFecha_estimada_terminacion(rs.getDate("fecha_estimada_terminacion"));
            actividad.setFecha_real_inicio(rs.getDate("fecha_real_inicio"));
            actividad.setFecha_real_terminacion(rs.getDate("fecha_real_terminacion"));
            actividad.setTiempo_estimado(rs.getInt("tiempo_estimado"));
            actividad.setTiempo_invertido(rs.getInt("tiempo_invertido"));
            actividad.setEstado(rs.getInt("estado"));
            listaActividades.add(actividad);
        }
        rs.close();
        ps.close();
        con.close();
        return listaActividades;
    }
    
    public List<ActividadesBean> consultarActividades2(String id, Integer version, String descripcion, java.util.Date fecha_estimada_inicio, 
            java.util.Date fecha_estimada_terminacion, java.util.Date fecha_real_inicio, java.util.Date fecha_real_terminacion, 
            Integer estado, String responsable) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<ActividadesBean> listaActividades = new ArrayList();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarActividades(id, version, descripcion, fecha_estimada_inicio, fecha_estimada_terminacion, fecha_real_inicio, 
                fecha_real_terminacion, estado));
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
            ActividadesBean actividad = new ActividadesBean();
            actividad.setId(rs.getInt("id"));
            actividad.setVersion(rs.getInt("version"));
            actividad.setDescripcion(rs.getString("descripcion"));
            actividad.setFecha_estimada_inicio(rs.getDate("fecha_estimada_inicio"));
            actividad.setFecha_estimada_terminacion(rs.getDate("fecha_estimada_terminacion"));
            actividad.setFecha_real_inicio(rs.getDate("fecha_real_inicio"));
            actividad.setFecha_real_terminacion(rs.getDate("fecha_real_terminacion"));
            actividad.setTiempo_estimado(rs.getInt("tiempo_estimado"));
            actividad.setTiempo_invertido(rs.getInt("tiempo_invertido"));
            actividad.setEstado(rs.getInt("estado"));
            listaActividades.add(actividad);
        }
        rs.close();
        ps.close();
        con.close();
        return listaActividades;
    }

    public int crearActividad(ActividadesBean actividad) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarActividad());
        ps.setInt(1, actividad.getVersion());
        ps.setString(2, actividad.getDescripcion());
        ps.setDate(3, new Date(actividad.getFecha_estimada_inicio().getTime()));
        ps.setDate(4, new Date(actividad.getFecha_estimada_terminacion().getTime()));
        ps.setDate(5, new Date(actividad.getFecha_real_inicio().getTime()));
        ps.setDate(6, new Date(actividad.getFecha_real_terminacion().getTime()));
        ps.setDate(7, new Date(actividad.getTiempo_estimado()));
        ps.setDate(8, new Date(actividad.getTiempo_invertido()));
        ps.setInt(9, actividad.getEstado());
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }

    public int actualizarActividad(ActividadesBean actividad) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actualizarActividad());
        ps.setString(1, actividad.getDescripcion());
        ps.setDate(2, new Date(actividad.getFecha_estimada_inicio().getTime()));
        ps.setDate(3, new Date(actividad.getFecha_estimada_terminacion().getTime()));
        ps.setDate(4, new Date(actividad.getFecha_real_inicio().getTime()));
        ps.setDate(5, new Date(actividad.getFecha_real_terminacion().getTime()));
        ps.setDate(6, new Date(actividad.getTiempo_estimado()));
        ps.setDate(7, new Date(actividad.getTiempo_invertido()));
        ps.setInt(8, actividad.getEstado());
        ps.setInt(9, actividad.getId());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }

    public int eliminarActividad(Integer idActividad) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.eliminarActividad());
        ps.setInt(1, idActividad);
        int eliminacion = ps.executeUpdate();
        ps.close();
        con.close();
        return eliminacion;
    }
}
