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
 *
 * @author Pipe
 */
public class ProyectosDao {

    private final ProyectosSql sql = new ProyectosSql();

    public List<ProyectosBean> consultarProyectos(Integer id) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<ProyectosBean> listaProyectos = new ArrayList();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarProyectos(id));
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

    public List<PersonasBean> consultarPersonasProyecto(Integer idProyecto) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<PersonasBean> listaPersonas = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarPersonasProyecto());
        ps.setInt(0, idProyecto);
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

    public int eliminarPersonasProyecto(Integer idProyecto) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.eliminarPersonasProyecto());
        ps.setInt(0, idProyecto);
        int eliminacion = ps.executeUpdate();
        ps.close();
        con.close();
        return eliminacion;
    }

    public int insertarPersonaProyecto(Integer idProyecto, Integer idPersona) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarPersonaProyecto());
        ps.setInt(0, idProyecto);
        ps.setInt(1, idPersona);
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }
}
