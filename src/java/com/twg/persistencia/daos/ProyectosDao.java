package com.twg.persistencia.daos;

import com.twg.persistencia.beans.ProyectoBean;
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

    public List<ProyectoBean> consultarProyectos() throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<ProyectoBean> listaProyectos = new ArrayList();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarProyectos());
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
            ProyectoBean proyecto = new ProyectoBean();
            proyecto.setId(rs.getInt("id"));
            proyecto.setNombre(rs.getString("nombre"));
            proyecto.setFechaInicio(rs.getDate("fecha_inicio"));
            proyecto.setIdPersona(rs.getInt("id_persona"));
            proyecto.setFechaEliminacion(rs.getDate("fecha_eliminacion"));

            listaProyectos.add(proyecto);
        }
        rs.close();
        ps.close();
        con.close();
        return listaProyectos;
    }
    
    public int crearProyecto(ProyectoBean proyecto) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarProyecto());
        ps.setString(1, proyecto.getNombre());
        ps.setDate(2, new Date(proyecto.getFechaInicio().getTime()));
        ps.setInt(3, proyecto.getIdPersona());
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }
    
    public int actualizarProyecto(ProyectoBean proyecto) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actualizarProyecto());
        ps.setString(1, proyecto.getNombre());
        ps.setDate(2, new Date(proyecto.getFechaInicio().getTime()));
        ps.setInt(3, proyecto.getIdPersona());
        ps.setInt(4, proyecto.getId());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }
}
