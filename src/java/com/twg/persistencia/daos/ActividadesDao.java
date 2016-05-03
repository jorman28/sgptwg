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
            actividad.setEstado(rs.getInt("estado"));
            listaActividades.add(actividad);
        }
        rs.close();
        ps.close();
        con.close();
        return listaActividades;
    }

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

    public List<ActividadesBean> consultarActividades(Integer proyecto, Integer version, String descripcion, java.util.Date fecha,
            Integer estado, Integer responsable) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<ActividadesBean> listaActividades = new ArrayList();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarActividades(proyecto, version, descripcion, fecha, estado, responsable));
        if (fecha != null) {
            ps.setObject(1, fecha);
            ps.setObject(2, fecha);
            ps.setObject(3, fecha);
            ps.setObject(4, fecha);
        }
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
            ActividadesBean actividad = new ActividadesBean();
            actividad.setId(rs.getInt("id"));
            actividad.setVersion(rs.getInt("version"));
            actividad.setNombreVersion(rs.getString("nombrev"));
            actividad.setDescripcion(rs.getString("descripcion"));
            actividad.setEstado(rs.getInt("estado"));
            actividad.setNombreEstado(rs.getString("nombree"));
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
        ps.setObject(1, actividad.getVersion());
        ps.setString(2, actividad.getNombre());
        ps.setString(3, actividad.getDescripcion());
        ps.setObject(4, actividad.getEstado());
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
}
