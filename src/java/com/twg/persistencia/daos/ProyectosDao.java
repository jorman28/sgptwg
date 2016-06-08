package com.twg.persistencia.daos;

import com.twg.persistencia.beans.PersonasBean;
import com.twg.persistencia.beans.ProyectosBean;
import com.twg.persistencia.sqls.ProyectosSql;
import com.twg.utilidades.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.Date;
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
public class ProyectosDao {

    private final ProyectosSql sql = new ProyectosSql();

    /**
     * Método encargado de consultar los proyectos del sistema.
     * @param id
     * @param nombre
     * @param nombreExacto
     * @param idPersona
     * @return Listado con la información de los proyectos consultados.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public List<ProyectosBean> consultarProyectos(Integer id, String nombre, boolean nombreExacto, Integer idPersona) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<ProyectosBean> listaProyectos = new ArrayList();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarProyectos(id, nombre, nombreExacto, idPersona));
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
            ProyectosBean proyecto = new ProyectosBean();
            proyecto.setId(rs.getInt("id"));
            proyecto.setNombre(rs.getString("nombre"));
            proyecto.setFechaInicio(rs.getDate("fecha_inicio"));
            proyecto.setFechaEliminacion(rs.getDate("fecha_eliminacion"));

            listaProyectos.add(proyecto);
        }
        rs.close();
        ps.close();
        con.close();
        return listaProyectos;
    }

    /**
     * Método encargado de consultar los proyectos por versión.
     * @param id
     * @return Listado con la información de los proyectos asociados a una versión.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public List<ProyectosBean> consultarProyectosPorVersion(Integer id) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<ProyectosBean> listaProyectos = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarProyectosPorVersion(id));
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
            ProyectosBean proyectoBean = new ProyectosBean();
            proyectoBean.setId(rs.getInt("id"));
            proyectoBean.setNombre(rs.getString("nombre"));
            proyectoBean.setFechaInicio(rs.getDate("fecha_inicio"));
            listaProyectos.add(proyectoBean);
        }
        rs.close();
        ps.close();
        con.close();
        return listaProyectos;
    }

    /**
     * Método encargado de rear un proyecto.
     * @param proyecto
     * @return Un número indicando si el procceso se realizó o no.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public int crearProyecto(ProyectosBean proyecto) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarProyecto());
        ps.setString(1, proyecto.getNombre());
        ps.setDate(2, new Date(proyecto.getFechaInicio().getTime()));
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }

    /**
     * Método encargado de actualizar la información de un proyecto.
     * @param proyecto
     * @return Un número indicando si el procceso se realizó o no.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public int actualizarProyecto(ProyectosBean proyecto) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actualizarProyecto());
        ps.setString(1, proyecto.getNombre());
        ps.setDate(2, new Date(proyecto.getFechaInicio().getTime()));
        ps.setInt(3, proyecto.getId());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }

    /**
     * Método encargado de eliminar el registro de un proyecto.
     * @param idProyecto
     * @return Un número indicando si el procceso se realizó o no.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public int eliminarProyecto(Integer idProyecto) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.eliminarProyecto());
        ps.setInt(1, idProyecto);
        int eliminacion = ps.executeUpdate();
        ps.close();
        con.close();
        return eliminacion;
    }

    /**
     * Método encargado de consultar las personas asociadas a un proyecto.
     * @param idProyecto
     * @return Listado con la información de las personas que pertenecen a un
     * proyecto.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public List<PersonasBean> consultarPersonasProyecto(Integer idProyecto) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<PersonasBean> listaPersonas = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarPersonasProyecto());
        ps.setInt(1, idProyecto);
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
            PersonasBean persona = new PersonasBean();
            persona.setId(rs.getInt("id"));
            persona.setTipoDocumento(rs.getString("tipo_documento"));
            persona.setDocumento(rs.getString("documento"));
            persona.setNombres(rs.getString("nombres"));
            persona.setApellidos(rs.getString("apellidos"));
            persona.setNombreCargo(rs.getString("cargo"));

            listaPersonas.add(persona);
        }
        rs.close();
        ps.close();
        con.close();
        return listaPersonas;
    }

    /**
     * Método encargado de eliminar las personas que pertenecen a un proyecto específico.
     * @param idProyecto
     * @return Un número indicando si el procceso se realizó o no.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public int eliminarPersonasProyecto(Integer idProyecto) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.eliminarPersonasProyecto());
        ps.setInt(1, idProyecto);
        int eliminacion = ps.executeUpdate();
        ps.close();
        con.close();
        return eliminacion;
    }

    /**
     * Método encargado de asociar las personas a un proyecto específco.
     * @param idProyecto
     * @param idPersona
     * @return Un número indicando si el procceso se realizó o no.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public int insertarPersonaProyecto(Integer idProyecto, Integer idPersona) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarPersonaProyecto());
        ps.setInt(1, idProyecto);
        ps.setInt(2, idPersona);
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }
}
