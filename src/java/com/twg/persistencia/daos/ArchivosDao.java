package com.twg.persistencia.daos;

import com.twg.persistencia.beans.ArchivosBean;
import com.twg.persistencia.sqls.ArchivosSql;
import com.twg.utilidades.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase encargada de obtener la conexión con la base de datos y ejecutar las
 * sentencias con base en los datos enviados desde el negocio
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class ArchivosDao {

    private final ArchivosSql sql = new ArchivosSql();

    /**
     * Método encargado de consultar los archivos que existen en la tabla de
     * archivos en la base de datos de acuerdo a los filtros enviados
     *
     * @param idArchivo
     * @param contiene
     * @param fecha
     * @param idPersona
     * @param limite
     * @return La lista de los archivos existentes en base de datos
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public List<ArchivosBean> consultarArchivos(Integer idArchivo, String contiene, Date fecha, Integer idPersona, String limite) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<ArchivosBean> listaArchivos = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarArchivos(idArchivo, contiene, fecha, idPersona, limite));
        if (fecha != null) {
            ps.setObject(1, fecha);
        }
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
            ArchivosBean archivo = new ArchivosBean();
            archivo.setId((Integer) rs.getObject("id"));
            archivo.setNombre(rs.getString("nombre"));
            archivo.setDescripcion(rs.getString("descripcion"));
            archivo.setFechaCreacion((Date) rs.getObject("fecha_creacion"));
            archivo.setRuta(rs.getString("ruta"));
            archivo.setIdPersona((Integer) rs.getObject("id_persona"));
            archivo.setNombrePersona(rs.getString("nombre_persona"));
            archivo.setTipo(rs.getString("tipo"));
            listaArchivos.add(archivo);
        }
        rs.close();
        ps.close();
        con.close();
        return listaArchivos;
    }

        /**
     * Método encargado de consultar la cantidad de archivos existentes en base
     * de datos relacionados con los filtros ingresados
     *
     * @param idArchivo
     * @param contiene
     * @param fecha
     * @param idPersona
     * @return Cantidad de archivos según los filtros aplicados.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public int cantidadArchivos(Integer idArchivo, String contiene, Date fecha, Integer idPersona) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        int cantidadArchivos = 0;
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.cantidadArchivos(idArchivo, contiene, fecha, idPersona));
        ResultSet rs;
        rs = ps.executeQuery();
        if (rs.next()) {
            cantidadArchivos = rs.getInt("cantidadArchivos");
        }
        rs.close();
        ps.close();
        con.close();
        return cantidadArchivos;
    }
    
    /**
     * Método encargado de insertar un nuevo archivo en la tabla de archivos de
     * la base de datos
     *
     * @param archivo Objeto con las propiedades del archivo a almacenar
     * @return Número para indicar que el registro de insertó o no de forma
     * correcta.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public int insertarArchivo(ArchivosBean archivo) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarArchivo());
        ps.setString(1, archivo.getNombre());
        ps.setString(2, archivo.getDescripcion());
        ps.setObject(3, archivo.getFechaCreacion());
        ps.setString(4, archivo.getRuta());
        ps.setObject(5, archivo.getIdPersona());
        ps.setString(6, archivo.getTipo());
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }

    /**
     * Método encargado de modificar un archivo existente en la tabla de
     * archivos de la base de datos
     *
     * @param archivo Objeto con las propiedades del archivo a modificar
     * @return Número para indicar que el registro se actualizó o no de forma correcta.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public int actualizarArchivo(ArchivosBean archivo) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actualizarArchivo());
        ps.setString(1, archivo.getNombre());
        ps.setString(2, archivo.getDescripcion());
        ps.setInt(3, archivo.getId());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }

    /**
     * Método encargado de eliminar lógicamente un archivo existente en la tabla
     * de archivos de la base de datos
     *
     * @param id Identificador del archivo que se desea eliminar
     * @return Número para indicar que el registro se eliminó o no de forma correcta.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public int eliminarArchivo(Integer id) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.eliminarArchivo());
        ps.setInt(1, id);
        int eliminacion = ps.executeUpdate();
        ps.close();
        con.close();
        return eliminacion;
    }
}
