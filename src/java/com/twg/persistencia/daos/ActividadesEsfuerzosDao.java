package com.twg.persistencia.daos;

import com.twg.persistencia.beans.ActividadesEsfuerzosBean;
import com.twg.persistencia.sqls.ActividadesEsfuerzosSql;
import com.twg.utilidades.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jorman
 */
public class ActividadesEsfuerzosDao {
    private final ActividadesEsfuerzosSql sql = new ActividadesEsfuerzosSql();

    public List<ActividadesEsfuerzosBean> consultarActividadesEsfuerzos(Integer id) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<ActividadesEsfuerzosBean> listaActividadesEsfuerzos = new ArrayList();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarActividades_Esfuerzos(id));
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
            ActividadesEsfuerzosBean actividadEsfuerzo = new ActividadesEsfuerzosBean();
            actividadEsfuerzo.setId(rs.getInt("id"));
            actividadEsfuerzo.setActividad(rs.getInt("actividad"));
            actividadEsfuerzo.setEmpleado(rs.getInt("empleado"));
            actividadEsfuerzo.setFecha(rs.getDate("fecha"));            
            actividadEsfuerzo.setTiempo(rs.getDouble("tiempo"));
            actividadEsfuerzo.setDescripcion(rs.getString("descripcion"));
            actividadEsfuerzo.setFechaEliminacion(rs.getDate("fecha_eliminacion"));

            listaActividadesEsfuerzos.add(actividadEsfuerzo);
        }
        rs.close();
        ps.close();
        con.close();
        return listaActividadesEsfuerzos;
    }
    
    public int crearActividadEsfuerzo(ActividadesEsfuerzosBean actividadEsfuerzo) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarActividad_Esfuerzo());
        ps.setInt(1, actividadEsfuerzo.getActividad());
        ps.setInt(2, actividadEsfuerzo.getEmpleado());
        ps.setDate(3, new java.sql.Date(actividadEsfuerzo.getFecha().getTime()));
        ps.setDouble(4, actividadEsfuerzo.getTiempo());
        ps.setString(5, actividadEsfuerzo.getDescripcion());
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }
    
    public int actualizarActividadEsfuerzo(ActividadesEsfuerzosBean actividadEsfuerzo) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actualizarActividad_Esfuerzo());
        ps.setInt(1, actividadEsfuerzo.getActividad());
        ps.setInt(2, actividadEsfuerzo.getEmpleado());
        ps.setDate(3, (java.sql.Date)actividadEsfuerzo.getFecha());
        ps.setDouble(4, actividadEsfuerzo.getTiempo());
        ps.setString(5, actividadEsfuerzo.getDescripcion());
        ps.setInt(6, actividadEsfuerzo.getId());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }
    
    public int eliminarActividadEsfuerzo(Integer idActividadEsfuerzo) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.eliminarActividad_Esfuerzo());
        ps.setInt(1, idActividadEsfuerzo);
        int eliminacion = ps.executeUpdate();
        ps.close();
        con.close();
        return eliminacion;
    }
}
