package com.twg.persistencia.daos;

import com.twg.persistencia.beans.EstadosBean;
import com.twg.persistencia.sqls.EstadosSql;
import com.twg.utilidades.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de obtener la conexión con la base de datos y ejecutar las
 * sentencias con base en los datos enviados desde el negocio
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class EstadosDao {

    private final EstadosSql sql = new EstadosSql();

    /**
     * Método constructor de la clase.
     */
    public EstadosDao() {
    }

    /**
     * Método encargado de consultar los estados de la base de datos.
     * @param id
     * @return Listado con los estados del sistema.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public List<EstadosBean> consultarEstados(Integer id) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        return consultarEstados(id, null, null, null, null, null, null);
    }

    /**
     * Método encargado de consultar la cantidad de usuarios existentes en base
     * de datos relacionados con los filtros ingresados
     *
     * @param id
     * @param tipoEstado
     * @param nombre
     * @param estadoPrev
     * @param estadoSig
     * @param eFinal
     * @return Cantidad de estados según los filtros aplicados.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public int cantidadEstados(Integer id, String tipoEstado, String nombre, Integer estadoPrev,
            Integer estadoSig, String eFinal) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        int cantidadEstados = 0;
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.cantidadEstados(id, tipoEstado, nombre, estadoPrev, estadoSig, eFinal));
        ResultSet rs;
        rs = ps.executeQuery();
        if (rs.next()) {
            cantidadEstados = rs.getInt("cantidadEstados");
        }
        rs.close();
        ps.close();
        con.close();
        return cantidadEstados;
    }

    /**
     * Método encargado de consultar los estados del sistema según los parámetros
     * de búsqueda.
     * @param id
     * @param tipoEstado
     * @param nombre
     * @param estadoPrev
     * @param estadoSig
     * @param eFinal
     * @param limite
     * @return Listado con los estados del sistema según los parámetros de búsqueda.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public List<EstadosBean> consultarEstados(Integer id, String tipoEstado, String nombre, Integer estadoPrev,
            Integer estadoSig, String eFinal, String limite) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<EstadosBean> listaEstados = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarEstados(id, tipoEstado, nombre, estadoPrev, estadoSig, eFinal, limite));
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
            EstadosBean estado = new EstadosBean();
            estado.setId(rs.getInt("id"));
            estado.setTipoEstado(rs.getString("tipo_estado"));
            estado.setNombre(rs.getString("nombre"));
            estado.setEstadoPrevio(rs.getInt("estado_previo"));
            estado.setEstadoSiguiente(rs.getInt("estado_siguiente"));
            estado.setEstadoFinal(rs.getString("estado_final"));
            listaEstados.add(estado);
        }
        rs.close();
        ps.close();
        con.close();
        return listaEstados;
    }

    /**
     * Método encargado de consultar los estados previos y siguientes de un 
     * estado específico.
     * @param id
     * @return Listado de estados previos o siguientes.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public List<EstadosBean> consultarEstadosPS(Integer id) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<EstadosBean> listaEstados = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarEstadosPS(id));
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
            EstadosBean estado = new EstadosBean();
            estado.setId(rs.getInt("id"));
            estado.setTipoEstado(rs.getString("tipo_estado"));
            estado.setNombre(rs.getString("nombre"));
            estado.setEstadoPrevio(rs.getInt("estado_previo"));
            estado.setEstadoSiguiente(rs.getInt("estado_siguiente"));
            estado.setEstadoFinal(rs.getString("estado_final"));
            listaEstados.add(estado);
        }
        rs.close();
        ps.close();
        con.close();
        return listaEstados;
    }

    /**
     * Método encargado de consultar el id de un estado específico.
     * @param nombre
     * @return El ID de un estado específico según el nombre.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public Integer consultarId(String nombre) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Integer idPersona = null;
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarId(nombre));
        ResultSet rs;
        rs = ps.executeQuery();
        if (rs.next()) {
            idPersona = rs.getInt("id");
        }
        rs.close();
        ps.close();
        con.close();
        return idPersona;
    }

    /**
     * Método encargado de insertar un nuevo estado en el sistema.
     * @param estado
     * @return Un número indicando si el proceso se realizó o no.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public int insertarEstado(EstadosBean estado) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarEstado());
        //ps.setInt(1, estado.getId());
        ps.setString(1, estado.getTipoEstado());
        ps.setString(2, estado.getNombre());
        ps.setInt(3, estado.getEstadoPrevio());
        ps.setInt(4, estado.getEstadoSiguiente());
        ps.setString(5, estado.getEstadoFinal());
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }

    /**
     * Método encargado de actualizar la información de un estado.
     * @param estado
     * @return Un número indicando si el proceso se realizó o no.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public int actualizarEstado(EstadosBean estado) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actualizarEstado());
        ps.setInt(6, estado.getId());
        ps.setString(1, estado.getTipoEstado());
        ps.setString(2, estado.getNombre());
        ps.setInt(3, estado.getEstadoPrevio());
        ps.setInt(4, estado.getEstadoSiguiente());
        ps.setString(5, estado.getEstadoFinal());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }

    /**
     * Método encargado de eliminar un estado específico.
     * @param id
     * @return Un número indicando si el proceso se realizó o no.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public int eliminarEstado(Integer id) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.eliminarEstado());
        ps.setInt(1, id);
        int eliminacion = ps.executeUpdate();
        ps.close();
        con.close();
        return eliminacion;
    }
}
