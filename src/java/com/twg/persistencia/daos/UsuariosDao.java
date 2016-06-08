package com.twg.persistencia.daos;

import com.twg.persistencia.beans.UsuariosBean;
import com.twg.persistencia.sqls.UsuariosSql;
import com.twg.utilidades.ConexionBaseDatos;
import java.sql.Connection;
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
public class UsuariosDao {

    private final UsuariosSql sql = new UsuariosSql();

    /**
     * Método constructor de la clase.
     */
    public UsuariosDao() {
    }

    /**
     * Método encargado de consultar todos los usuarios existentes en la base de
     * datos
     *
     * @return Listado de usuarios según los filtros aplicados.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public List<UsuariosBean> consultarUsuarios() throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        return consultarUsuarios(null, null, null, null, null, null, null);
    }

    /**
     * Método encargado de consultar los usuarios relacionados con un nombre de
     * usuario
     *
     * @param nombreUsuario
     * @return Listado de usuarios según los filtros aplicados.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public List<UsuariosBean> consultarUsuarios(String nombreUsuario) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        return consultarUsuarios(null, nombreUsuario, null, null, null, null, null);
    }

    /**
     * Método encargado de consultar el usuario de una persona por medio del id
     * de dicha persona
     *
     * @param idPersona
     * @return Listado con la información de un usuario específico.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public List<UsuariosBean> consultarUsuarios(Integer idPersona) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        return consultarUsuarios(idPersona, null, null, null, null, null, null);
    }

    /**
     * Método encargado de consultar la cantidad de usuarios existentes en base
     * de datos relacionados con los filtros ingresados
     *
     * @param idPersona
     * @param nombreUsuario
     * @param perfil
     * @param activo
     * @param documento
     * @param tipoDocumento
     * @return Cantdad de usuarios según los filtros aplicados.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public int cantidadUsuarios(Integer idPersona, String nombreUsuario, Integer perfil, String activo, String documento, String tipoDocumento) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        int cantidadUsuarios = 0;
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.cantidadUsuarios(idPersona, nombreUsuario, perfil, activo, documento, tipoDocumento));
        ResultSet rs;
        rs = ps.executeQuery();
        if (rs.next()) {
            cantidadUsuarios = rs.getInt("cantidadUsuarios");
        }
        rs.close();
        ps.close();
        con.close();
        return cantidadUsuarios;
    }

    /**
     * Método encargado de consultar la lista de usuarios relacionados con los
     * filtros ingresados que existen en la base de datos
     *
     * @param idPersona
     * @param nombreUsuario
     * @param perfil
     * @param activo
     * @param documento
     * @param tipoDocumento
     * @param limite
     * @return Listado de usuarios según los filtros aplicados.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public List<UsuariosBean> consultarUsuarios(Integer idPersona, String nombreUsuario, Integer perfil, String activo, String documento, String tipoDocumento, String limite) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<UsuariosBean> listaUsuarios = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarUsuarios(idPersona, nombreUsuario, perfil, activo, documento, tipoDocumento, limite));
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
            UsuariosBean usuario = new UsuariosBean();
            usuario.setIdPersona(rs.getInt("id_persona"));
            usuario.setDocumento(rs.getString("documento"));
            usuario.setTipoDocumento(rs.getString("id_tipo_documento"));
            usuario.setDescripcionTipoDocumento(rs.getString("descripcion_tipo_documento"));
            usuario.setUsuario(rs.getString("usuario"));
            usuario.setClave(rs.getString("clave"));
            usuario.setPerfil(rs.getInt("id_perfil"));
            usuario.setDescripcionPerfil(rs.getString("descripcion_perfil"));
            usuario.setActivo(rs.getString("activo"));
            usuario.setFechaEliminacion(rs.getString("fecha_eliminacion"));

            listaUsuarios.add(usuario);
        }
        rs.close();
        ps.close();
        con.close();
        return listaUsuarios;
    }

    /**
     * Método encargado de insertar un registro de usuario en la base de datos
     *
     * @param usuario
     * @return Número para indicar que un registro se insertó o no de forma correcta.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public int insertarUsuario(UsuariosBean usuario) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarUsuario());
        ps.setInt(1, usuario.getIdPersona());
        ps.setString(2, usuario.getUsuario());
        ps.setString(3, usuario.getClave());
        ps.setInt(4, usuario.getPerfil());
        ps.setString(5, usuario.getActivo());
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }

    /**
     * Método encargado de actualizar la información de un usuario previamente
     * insertado en la base de datos
     *
     * @param usuario
     * @return Número para indicar que un registro se actualizó o no de forma correcta.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public int actualizarUsuario(UsuariosBean usuario) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actualizarUsuario());
        ps.setString(1, usuario.getUsuario());
        ps.setString(2, usuario.getClave());
        ps.setInt(3, usuario.getPerfil());
        ps.setString(4, usuario.getActivo());
        ps.setString(5, usuario.getFechaEliminacion());
        ps.setInt(6, usuario.getIdPersona());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }

    /**
     * Método encargado de eliminar un usuario previamente insertado en base de
     * datos
     *
     * @param idPersona
     * @return Número para indicar que un registro se eliminó o no de forma correcta.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public int eliminarUsuario(Integer idPersona) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.eliminarUsuario());
        ps.setInt(1, idPersona);
        int eliminacion = ps.executeUpdate();
        ps.close();
        con.close();
        return eliminacion;
    }
}
