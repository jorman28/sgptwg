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
 *
 * @author Pipe
 */
public class VersionesDao {

    private final VersionesSql sql = new VersionesSql();

    public List<VersionesBean> consultarVersiones(Integer id, Integer idProyecto) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<VersionesBean> listaVersiones = new ArrayList();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarVersiones(id, idProyecto));
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
            version.setNombreEstado(rs.getString("nombre_estado"));
            version.setFechaEliminacion(rs.getDate("fecha_eliminacion"));

            listaVersiones.add(version);
        }
        rs.close();
        ps.close();
        con.close();
        return listaVersiones;
    }

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
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }

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
        ps.setInt(7, version.getId());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }
}
