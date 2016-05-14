package com.twg.persistencia.daos;

import com.twg.persistencia.beans.ActividadesEmpleadosBean;
import com.twg.persistencia.sqls.ActividadesEmpleadosSql;
import com.twg.utilidades.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author erikasta07
 */
public class ActividadesEmpleadosDao {

    private final ActividadesEmpleadosSql sql = new ActividadesEmpleadosSql();

    public ActividadesEmpleadosDao() {
    }

    /**
     * Método encargado de consultar las personas relacionadas con una actividad
     * o las actividades relacionadas a una persona. Se puede consultar además
     * si una persona ya tiene actividades asignadas para unas fechas
     * específicas
     *
     * @param idActividad
     * @param idEmpleado
     * @param fechaEstimadaInicio
     * @param fechaEstimadaFin
     * @param evaluarEstado
     * @param actividadIgual
     * @return
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public List<ActividadesEmpleadosBean> consultarActividadesEmpleados(Integer idActividad, Integer idEmpleado, Date fechaEstimadaInicio, Date fechaEstimadaFin, boolean evaluarEstado, boolean actividadIgual) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<ActividadesEmpleadosBean> listaActividadesEmpleados = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarActividadEmpleado(idActividad, idEmpleado, fechaEstimadaInicio, fechaEstimadaFin, evaluarEstado, actividadIgual));
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
            ActividadesEmpleadosBean actividadEmpleado = new ActividadesEmpleadosBean();
            actividadEmpleado.setActividad(rs.getInt("actividad"));
            actividadEmpleado.setNombreActividad(rs.getString("nombre_actividad"));
            actividadEmpleado.setEstado(rs.getString("nombre_estado"));
            actividadEmpleado.setEmpleado(rs.getInt("empleado"));
            actividadEmpleado.setTipoDocumento(rs.getString("tipo_documento"));
            actividadEmpleado.setDocumento(rs.getString("documento"));
            actividadEmpleado.setNombrePersona(rs.getString("nombre_persona"));
            actividadEmpleado.setCargo(rs.getString("nombre_cargo"));
            actividadEmpleado.setFechaEstimadaInicio((Date) rs.getObject("fecha_estimada_inicio"));
            actividadEmpleado.setFechaEstimadaTerminacion((Date) rs.getObject("fecha_estimada_terminacion"));
            actividadEmpleado.setTiempoEstimado((Double) rs.getObject("tiempo_estimado"));
            listaActividadesEmpleados.add(actividadEmpleado);
        }
        rs.close();
        ps.close();
        con.close();
        return listaActividadesEmpleados;
    }

    /**
     * Método encargado de asociar una persona a una actividad
     *
     * @param actividadEmpleado
     * @return
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public int insertarActividadEmpleado(ActividadesEmpleadosBean actividadEmpleado) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarActividadEmpleado());
        ps.setInt(1, actividadEmpleado.getActividad());
        ps.setInt(2, actividadEmpleado.getEmpleado());
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }

    /**
     * Método encargado de actualizar los tiempos estimados de una persona
     * asociada a una actividad
     *
     * @param actividadEmpleado
     * @return
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public int actualizarActividadEmpleado(ActividadesEmpleadosBean actividadEmpleado) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actualizarActividadEmpleado());
        ps.setObject(1, actividadEmpleado.getFechaEstimadaInicio());
        ps.setObject(2, actividadEmpleado.getFechaEstimadaTerminacion());
        ps.setObject(3, actividadEmpleado.getTiempoEstimado());
        ps.setInt(4, actividadEmpleado.getActividad());
        ps.setInt(5, actividadEmpleado.getEmpleado());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }

    /**
     * Método encargado de eliminar la asociación de una persona a una actividad
     *
     * @param idActividad
     * @param idEmpleado
     * @return
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public int eliminarActividadEmpleado(Integer idActividad, Integer idEmpleado) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.eliminarActividadEmpleado());
        ps.setInt(1, idActividad);
        ps.setInt(2, idEmpleado);
        int eliminacion = ps.executeUpdate();
        ps.close();
        con.close();
        return eliminacion;
    }
}
