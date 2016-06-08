package com.twg.persistencia.daos;

import com.twg.persistencia.beans.VersionesBean;
import com.twg.persistencia.sqls.VersionesSql;
import com.twg.utilidades.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de realizar la conexión con la base de datos y ejecutar las
 * sentencias SQL especificadas
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class VersionesDao {

    private final VersionesSql sql = new VersionesSql();

    /**
     * Método encargado de consultar las versiones del sistema según los parámetros
     * de búsqueda.
     * @param id
     * @param idProyecto
     * @param nombre
     * @param nombreExacto
     * @return Listado con las versiones consultadas.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public List<VersionesBean> consultarVersiones(Integer id, Integer idProyecto, String nombre, boolean nombreExacto) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<VersionesBean> listaVersiones = new ArrayList();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarVersiones(id, idProyecto, nombre, nombreExacto));
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
            VersionesBean version = new VersionesBean();
            version.setId(rs.getInt("id"));
            version.setNombre(rs.getString("nombre"));
            version.setFechaInicio(rs.getDate("fecha_inicio"));
            version.setFechaTerminacion(rs.getDate("fecha_terminacion"));
            version.setAlcance(rs.getString("alcance"));
            version.setProyecto(rs.getInt("proyecto"));
            version.setNombreProyecto(rs.getString("nombre_proyecto"));
            version.setEstado(rs.getInt("estado"));
            version.setCosto(rs.getDouble("costo"));
            version.setNombreEstado(rs.getString("nombre_estado"));
            version.setFechaEliminacion(rs.getDate("fecha_eliminacion"));
            version.setFechaInicioProyecto(rs.getDate("fecha_proyecto"));

            listaVersiones.add(version);
        }
        rs.close();
        ps.close();
        con.close();
        return listaVersiones;
    }

    /**
     * Método encargado de consultar las versiones dentro de una fecha específica.
     * @param idProyecto
     * @param fecha
     * @return Valor booleano para indicar si hay versiones dentro de una fecha
     * específica.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public boolean versionesPorFecha(Integer idProyecto, java.util.Date fecha) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.versionesPorFecha());
        ps.setInt(1, idProyecto);
        ps.setDate(2, new Date(fecha.getTime()));
        ResultSet rs;
        rs = ps.executeQuery();
        boolean versionesExistentes = false;
        if (rs.next()) {
            versionesExistentes = true;
        }
        rs.close();
        ps.close();
        con.close();
        return versionesExistentes;
    }

    /**
     * Método encargado de crear una nueva versión.
     * @param version
     * @return Valor numérico para indicar si el proceso se realizó o no correctamente.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public int crearVersion(VersionesBean version) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarVersion());
        ps.setString(1, version.getNombre());
        ps.setDate(2, new Date(version.getFechaInicio().getTime()));
        ps.setDate(3, new Date(version.getFechaTerminacion().getTime()));
        ps.setString(4, version.getAlcance());
        ps.setInt(5, version.getProyecto());
        ps.setInt(6, version.getEstado());
        ps.setDouble(7, version.getCosto());
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }

    /**
     * Método encargado de actualizar la información de una versión.
     * @param version
     * @return Valor numérico para indicar si el proceso se realizó o no correctamente.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public int actualizarVersion(VersionesBean version) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actualizarVersion());
        ps.setString(1, version.getNombre());
        ps.setDate(2, new Date(version.getFechaInicio().getTime()));
        ps.setDate(3, new Date(version.getFechaTerminacion().getTime()));
        ps.setString(4, version.getAlcance());
        ps.setInt(5, version.getProyecto());
        ps.setInt(6, version.getEstado());
        ps.setDouble(7, version.getCosto());
        ps.setInt(8, version.getId());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }

    /**
     * Método encargado de eliminar una versión específica.
     * @param idVersion
     * @param idProyecto
     * @return Valor numérico para indicar si el proceso se realizó o no correctamente.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public int eliminarVersion(Integer idVersion, Integer idProyecto) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.eliminarVersion(idVersion, idProyecto));
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }
}
