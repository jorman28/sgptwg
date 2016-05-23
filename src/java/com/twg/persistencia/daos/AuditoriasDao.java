package com.twg.persistencia.daos;

import com.twg.persistencia.beans.AuditoriasBean;
import com.twg.persistencia.sqls.AuditoriasSql;
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
 * sentencias en la tabla de auditorias de la base en los datos enviados desde
 * el negocio
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class AuditoriasDao {

    private final AuditoriasSql sql = new AuditoriasSql();

    /**
     * Método encargado de consultar las auditorias que existen en la tabla de
     * auditorias en la base de datos de acuerdo a los filtros enviados
     *
     * @param idAuditoria
     * @param clasificacion
     * @param accion
     * @param contiene
     * @param fecha
     * @param idPersona
     * @param limite
     * @return La lista de los auditorias existentes en base de datos
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public List<AuditoriasBean> consultarAuditorias(Integer idAuditoria, String clasificacion, String accion, String contiene, Date fecha, Integer idPersona, String limite) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<AuditoriasBean> listaAuditorias = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarAuditorias(idAuditoria, clasificacion, accion, contiene, fecha, idPersona, limite));
        if (fecha != null) {
            ps.setObject(1, fecha);
        }
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
            AuditoriasBean auditoria = new AuditoriasBean();
            auditoria.setId((Integer) rs.getObject("id"));
            auditoria.setIdPersona((Integer) rs.getObject("id_persona"));
            auditoria.setNombrePersona(rs.getString("nombre_persona"));
            auditoria.setFechaCreacion((Date) rs.getObject("fecha_creacion"));
            auditoria.setClasificacion(rs.getString("clasificacion"));
            auditoria.setAccion(rs.getString("accion"));
            auditoria.setDescripcion(rs.getString("descripcion"));
            listaAuditorias.add(auditoria);
        }
        rs.close();
        ps.close();
        con.close();
        return listaAuditorias;
    }

    /**
     * Método encargado de consultar la cantidad de auditorias existentes en base
     * de datos relacionados con los filtros ingresados
     *
     * @param idAuditoria
     * @param clasificacion
     * @param accion
     * @param contiene
     * @param fecha
     * @param idPersona
     * @return Cantidad de auditorías según los filtros aplicados.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public int cantidadAuditorias(Integer idAuditoria, String clasificacion, String accion, String contiene, Date fecha, Integer idPersona) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        int cantidadAuditorias = 0;
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.cantidadAuditorias(idAuditoria, clasificacion, accion, contiene, fecha, idPersona));
        ResultSet rs;
        rs = ps.executeQuery();
        if (rs.next()) {
            cantidadAuditorias = rs.getInt("cantidadAuditorias");
        }
        rs.close();
        ps.close();
        con.close();
        return cantidadAuditorias;
    }
    
    /**
     * Método encargado de insertar una nueva auditoria en la tabla de
     * auditorias de la base de datos
     *
     * @param auditoria Objeto con las propiedades de la auditoria a almacenar
     * @return Número para indicar que el registro se insertó o no de forma correcta.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public int insertarAuditoria(AuditoriasBean auditoria) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarAuditoria());
        ps.setObject(1, auditoria.getIdPersona());
        ps.setObject(2, auditoria.getFechaCreacion());
        ps.setString(3, auditoria.getClasificacion());
        ps.setString(4, auditoria.getAccion());
        ps.setString(5, auditoria.getDescripcion());
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }

    /**
     * Método encargado de eliminar lógicamente una auditoria existente en la
     * tabla de auditorias de la base de datos
     *
     * @param id Identificador de la auditoria que se desea eliminar
     * @return Número para indicar que el registro se eliminó o no de forma correcta.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public int eliminarAuditoria(Integer id) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.eliminarAuditoria());
        ps.setInt(1, id);
        int eliminacion = ps.executeUpdate();
        ps.close();
        con.close();
        return eliminacion;
    }
}
