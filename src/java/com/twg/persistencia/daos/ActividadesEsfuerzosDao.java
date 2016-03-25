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

    public List<ActividadesEsfuerzosBean> consultarActividadesEsfuerzos(Integer id, Integer actividad, Integer empleado, String fecha, Double tiempo, String descripcion) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<ActividadesEsfuerzosBean> listaActividadesEsfuerzos = new ArrayList();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarActividades_Esfuerzos(id, actividad, empleado, fecha, tiempo, descripcion));
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

    public ActividadesEsfuerzosBean consultarActividadEsfuerzo(Integer id, Integer actividad, Integer empleado, String fecha, Double tiempo, String descripcion) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        ActividadesEsfuerzosBean ActividadesEsfuerzos = new ActividadesEsfuerzosBean();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarActividades_Esfuerzos(id, actividad, empleado, fecha, tiempo, descripcion));
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
            ActividadesEsfuerzos = actividadEsfuerzo;
        }
        rs.close();
        ps.close();
        con.close();
        return ActividadesEsfuerzos;
    }

    public int crearActividadEsfuerzo(ActividadesEsfuerzosBean actividadEsfuerzo) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
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

    public int actualizarActividadEsfuerzo(ActividadesEsfuerzosBean actividadEsfuerzo) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actualizarActividad_Esfuerzo());
        ps.setDate(1, new java.sql.Date(actividadEsfuerzo.getFecha().getTime()));
        ps.setDouble(2, actividadEsfuerzo.getTiempo());
        ps.setString(3, actividadEsfuerzo.getDescripcion());
        ps.setInt(4, actividadEsfuerzo.getActividad());
        ps.setInt(5, actividadEsfuerzo.getEmpleado());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }

    public int eliminarActividadEsfuerzo(Integer idActividadEsfuerzo) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
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

    public int eliminarActividadEsfuerzo(Integer idActividadEsfuerzo, Integer idActividad, Integer idEmpleado) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.eliminarActividad_Esfuerzo(idActividadEsfuerzo, idActividad, idEmpleado));
        //ps.setInt(1, idActividadEsfuerzo);
        int eliminacion = ps.executeUpdate();
        ps.close();
        con.close();
        return eliminacion;
    }

    public int eliminarActividadesEsfuerzos(Integer actividad, String[] empleados) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;

        StringBuilder strEmpleados = new StringBuilder("");
        for (int i = 0; i < empleados.length; i++) {
            strEmpleados.append(empleados[i]);
            if (i < empleados.length - 1) {
                strEmpleados.append(',');
            }
        }

        ps = con.prepareStatement(sql.eliminarActividades_Esfuerzos());
        ps.setInt(1, actividad);
        ps.setString(2, strEmpleados.toString());
        int eliminacion = ps.executeUpdate();
        ps.close();
        con.close();
        return eliminacion;
    }
}
