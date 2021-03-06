package com.twg.persistencia.daos;

import com.twg.persistencia.beans.ActividadesBean;
import com.twg.persistencia.sqls.ActividadesSql;
import com.twg.utilidades.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase encargada de obtener la conexión con la base de datos y ejecutar las
 * sentencias con base en los datos enviados desde el negocio
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class ActividadesDao {

    private final ActividadesSql sql = new ActividadesSql();

    /**
     * Método encargado de consultar el id de la última actividad
     *
     * @return Id de la última actividad insertada en base de datos
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public int consultarUtimaActividad() throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        int ultimaActividad = 0;
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarUtimaActividad());
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
            ultimaActividad = rs.getInt("id");
        }
        rs.close();
        ps.close();
        con.close();
        return ultimaActividad;
    }

    /**
     * Método encargado de consultar la lista de actividades relacionada a los
     * filtros ingresados
     *
     * @param idActividad
     * @param proyecto
     * @param version
     * @param contiene
     * @param fecha
     * @param estado
     * @param responsable
     * @param limite
     * @return Lista de actividades relacionadas con los parámetros enviados
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public List<ActividadesBean> consultarActividades(Integer idActividad, Integer proyecto, Integer version, String contiene, java.util.Date fecha, Integer estado, Integer responsable, String limite) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<ActividadesBean> listaActividades = new ArrayList();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarActividades(idActividad, proyecto, version, contiene, fecha, estado, responsable, limite));
        if (fecha != null) {
            ps.setObject(1, fecha);
        }
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
            ActividadesBean actividad = new ActividadesBean();
            actividad.setId(rs.getInt("id"));
            actividad.setProyecto((Integer) rs.getObject("proyecto"));
            actividad.setNombreProyecto(rs.getString("nombre_proyecto"));
            actividad.setVersion(rs.getInt("version"));
            actividad.setNombreVersion(rs.getString("nombre_version"));
            actividad.setNombre(rs.getString("nombre"));
            actividad.setDescripcion(rs.getString("descripcion"));
            actividad.setEstado(rs.getInt("estado"));
            actividad.setNombreEstado(rs.getString("nombre_estado"));
            actividad.setFechaInicio(rs.getDate("fecha_estimada_inicio"));
            actividad.setFechaFin(rs.getDate("fecha_estimada_terminacion"));
            actividad.setFechaRealInicio(rs.getDate("fecha_real_inicio"));
            actividad.setUltimaModificacion(rs.getDate("ultima_modificacion"));
            actividad.setTiempoEstimado(rs.getDouble("tiempo_estimado"));
            actividad.setTiempoInvertido(rs.getDouble("tiempo_invertido"));
            listaActividades.add(actividad);
        }
        rs.close();
        ps.close();
        con.close();
        return listaActividades;
    }

    /**
     * Método encargado de retornar la cantidad de actividades relacionada con
     * los filtros de búsqueda ingresados
     *
     * @param proyecto
     * @param version
     * @param contiene
     * @param fecha
     * @param estado
     * @param responsable
     * @return Cantidad de actividades relacionadas con los parámetros de
     * búsqueda
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public int contarActividades(Integer proyecto, Integer version, String contiene, java.util.Date fecha, Integer estado, Integer responsable) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        int actividades = 0;
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.contarActividades(proyecto, version, contiene, fecha, estado, responsable));
        if (fecha != null) {
            ps.setObject(1, fecha);
        }
        ResultSet rs;
        rs = ps.executeQuery();
        if (rs.next()) {
            actividades = rs.getInt("cantidad_actividades");
        }
        rs.close();
        ps.close();
        con.close();
        return actividades;
    }

    /**
     * Metodo encargado de consultar en base de datos, la información
     * correspondiente a los filtros ingresados en pantalla para el reporte
     * detallado de actividades
     *
     * @param proyecto
     * @param version
     * @param contiene
     * @param fecha
     * @param estado
     * @param responsable
     * @return Lista de mapas con la información de las actividades por persona
     * asociada
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public List<Map<String, Object>> detalleActividades(Integer proyecto, Integer version, String contiene, java.util.Date fecha, Integer estado, Integer responsable) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<Map<String, Object>> listaActividades = new ArrayList();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.detalleActividades(proyecto, version, contiene, fecha, estado, responsable));
        if (fecha != null) {
            ps.setObject(1, fecha);
        }
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
            Map<String, Object> actividad = new HashMap<>();
            actividad.put("proyecto", rs.getString("nombre_proyecto"));
            actividad.put("version", rs.getString("nombre_version"));
            actividad.put("actividad", rs.getString("nombre_actividad"));
            actividad.put("estado", rs.getString("nombre_estado"));
            actividad.put("responsable", rs.getString("nombre_persona"));
            actividad.put("documento", rs.getString("documento"));
            actividad.put("fechaInicio", rs.getObject("fecha_estimada_inicio"));
            actividad.put("fechaFin", rs.getObject("fecha_estimada_terminacion"));
            actividad.put("tiempoEstimado", rs.getDouble("tiempo_estimado"));
            actividad.put("tiempoInvertido", rs.getDouble("tiempo_invertido"));
            listaActividades.add(actividad);
        }
        rs.close();
        ps.close();
        con.close();
        return listaActividades;
    }

    /**
     * Método encargado de insertar un nuevo registro de actividad en base de
     * datos
     *
     * @param actividad
     * @return Cantidad de registros insertados en base de datos
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public int crearActividad(ActividadesBean actividad) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarActividad());
        ps.setObject(1, actividad.getVersion());
        ps.setString(2, actividad.getNombre());
        ps.setString(3, actividad.getDescripcion());
        ps.setObject(4, actividad.getEstado());
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }

    /**
     * Método encargado de actualizar una registro de actividad previamente
     * existente en base de datos
     *
     * @param actividad
     * @return Cantidad de registros actualizados en base de datos
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public int actualizarActividad(ActividadesBean actividad) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actualizarActividad());
        ps.setObject(1, actividad.getVersion());
        ps.setString(2, actividad.getNombre());
        ps.setString(3, actividad.getDescripcion());
        ps.setObject(4, actividad.getEstado());
        ps.setObject(5, actividad.getId());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }

    /**
     * Método encargado de eliminar un registro de actividad existente en base
     * de datos marcando la fecha de eliminación de dicho registro con la fecha
     * de la eliminación
     *
     * @param idActividad
     * @return Cantidad de registros actualizados en base de datos
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
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

    /**
     * Método encargado de contar las actividades existes para cada estado
     * creado
     *
     * @param proyecto
     * @param version
     * @param persona
     * @return Lista de mapas con la información de cada estado y la cantidad de
     * actividades relacionadas a cada uno
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws SQLException
     */
    public List<Map<String, Object>> actividadesPorEstado(Integer proyecto, Integer version, Integer persona) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        List<Map<String, Object>> listaActividadesPorEstado = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actividadesPorEstados(proyecto, version, persona));
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
            Map<String, Object> actividadesPorEstado = new HashMap<>();
            actividadesPorEstado.put("id_estado", rs.getObject("id_estado"));
            actividadesPorEstado.put("estado", rs.getString("estado"));
            actividadesPorEstado.put("actividades", ((Long) rs.getObject("actividades")).intValue());
            listaActividadesPorEstado.add(actividadesPorEstado);
        }
        return listaActividadesPorEstado;
    }

    /**
     * Método encargado de consultar en base de datos la lista de proyectos o
     * versiones relacionados con los parámetros de búsqueda con el fin de
     * definir el tiempo estimado e invertido de los mismos a manera de
     * consolidado
     *
     * @param proyecto
     * @param version
     * @param responsable
     * @return Lista de las actividades agrupadas en proyectos o versiones para
     * evaluar el tiempo estimado vs el tiempo invertido
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws SQLException
     */
    public List<ActividadesBean> consolidadoActividades(Integer proyecto, Integer version, Integer responsable) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        List<ActividadesBean> listaActividades = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consolidadoActividades(proyecto, version, responsable));
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
            ActividadesBean actividad = new ActividadesBean();
            actividad.setProyecto(rs.getInt("proyecto"));
            actividad.setNombreProyecto(rs.getString("nombre_proyecto"));
            actividad.setVersion(rs.getInt("version"));
            actividad.setNombreVersion(rs.getString("nombre_version"));
            actividad.setTiempoEstimado(rs.getDouble("tiempo_estimado"));
            actividad.setTiempoInvertido(rs.getDouble("tiempo_invertido"));
            listaActividades.add(actividad);
        }
        rs.close();
        ps.close();
        con.close();
        return listaActividades;
    }
}
