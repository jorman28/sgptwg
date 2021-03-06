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
 * Clase encargada de obtener la conexión con la base de datos y ejecutar las
 * sentencias con base en los datos enviados desde el negocio
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class ActividadesEsfuerzosDao {

    private final ActividadesEsfuerzosSql sql = new ActividadesEsfuerzosSql();

    /**
     * Método encargado de consultar el listado de los esfuerzos por
     * actividad, aplicando filtros según los parámetros que lleguen distintos
     * de nulo.
     * @param id
     * @param actividad
     * @param empleado
     * @param fecha
     * @param descripcion
     * @return Listado con todos los esfuerzos por actividad.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public List<ActividadesEsfuerzosBean> consultarActividadesEsfuerzos(Integer id, Integer actividad, Integer empleado, Date fecha, String descripcion) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<ActividadesEsfuerzosBean> listaActividadesEsfuerzos = new ArrayList();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarActividadesEsfuerzos(id, actividad, empleado, fecha, descripcion));
        if (fecha != null) {
            ps.setObject(1, fecha);
        }
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

            listaActividadesEsfuerzos.add(actividadEsfuerzo);
        }
        rs.close();
        ps.close();
        con.close();
        return listaActividadesEsfuerzos;
    }

    /**
     * Método encargado de consultar los esfuerzos asociados a una actividad, 
     * según los filtros aplicados.
     * @param id
     * @param actividad
     * @param empleado
     * @param fecha
     * @param descripcion
     * @return Un objeto con todos los atributos de los esfuerzos que pertenecen
     * a una actividad.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public ActividadesEsfuerzosBean consultarActividadEsfuerzo(Integer id, Integer actividad, Integer empleado, Date fecha, String descripcion) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        ActividadesEsfuerzosBean ActividadesEsfuerzos = new ActividadesEsfuerzosBean();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarActividadesEsfuerzos(id, actividad, empleado, fecha, descripcion));
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
            ActividadesEsfuerzos = actividadEsfuerzo;
        }
        rs.close();
        ps.close();
        con.close();
        return ActividadesEsfuerzos;
    }

    /**
     * Método encargado de insertar un esfuerzo para una actividad.
     * @param actividadEsfuerzo
     * @return Un número indicando si el proceso se realizó o no.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public int crearActividadEsfuerzo(ActividadesEsfuerzosBean actividadEsfuerzo) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarActividadEsfuerzo());
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

    /**
     * Método encargado de actualizar los esfuerzos de una actividad específica.
     * @param actividadEsfuerzo
     * @return Un número indicando si el proceso se realizó o no.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public int actualizarActividadEsfuerzo(ActividadesEsfuerzosBean actividadEsfuerzo) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actualizarActividadEsfuerzo());
        ps.setDate(1, new java.sql.Date(actividadEsfuerzo.getFecha().getTime()));
        ps.setDouble(2, actividadEsfuerzo.getTiempo());
        ps.setString(3, actividadEsfuerzo.getDescripcion());
        ps.setInt(4, actividadEsfuerzo.getId());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }

    /**
     * Método encargado de eliminar el esfuerzo de una actividad específica.
     * @param idActividadEsfuerzo
     * @param idActividad
     * @param idEmpleado
     * @return Un número indicando si el proceso se realizó o no.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public int eliminarActividadEsfuerzo(Integer idActividadEsfuerzo, Integer idActividad, Integer idEmpleado) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.eliminarActividadEsfuerzo(idActividadEsfuerzo, idActividad, idEmpleado));
        int eliminacion = ps.executeUpdate();
        ps.close();
        con.close();
        return eliminacion;
    }
}
