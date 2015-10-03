package com.twg.persistencia.daos;

import com.twg.persistencia.beans.EstadosVersionesBean;
import com.twg.persistencia.sqls.EstadosVersionesSql;
import com.twg.utilidades.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jorman Rincón
 */
public class EstadosVersionesDao {
    private final EstadosVersionesSql sql = new EstadosVersionesSql();
    
    public EstadosVersionesDao(){
    }

    public List<EstadosVersionesBean> consultarEstadosVersiones() throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        return consultarEstadosVersiones(null, null);
    }
    
    public List<EstadosVersionesBean> consultarEstadosVersiones(String nombre) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        return consultarEstadosVersiones(null, nombre);
    }
    
    public List<EstadosVersionesBean> consultarEstadosVersiones(Integer id) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        return consultarEstadosVersiones(id, null);
    }
    
    public List<EstadosVersionesBean> consultarEstadosVersiones(Integer id, String nombre) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        List<EstadosVersionesBean> listaEstadosVersiones = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarEstadosActividades(id, nombre));
        ResultSet rs;
        rs = ps.executeQuery();
        while(rs.next()){
            EstadosVersionesBean estadoActividad = new EstadosVersionesBean();
            estadoActividad.setId(rs.getInt("id"));
            estadoActividad.setNombre(rs.getString("nombre"));            
            listaEstadosVersiones.add(estadoActividad);
        }
        rs.close();
        ps.close();
        con.close();
        return listaEstadosVersiones;
    }
    
    public int insertarEstadoVersion(EstadosVersionesBean estadoVersion) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarEstadoVersion());
        ps.setInt(1, estadoVersion.getId());
        ps.setString(2, estadoVersion.getNombre());
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }
    
    public int actualizarEstadoVersion(EstadosVersionesBean estadoVersion) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actualizarEstadoVersion());
        ps.setInt(1, estadoVersion.getId());
        ps.setString(2, estadoVersion.getNombre());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }
    
    public int eliminarEstadoVersion(Integer id) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.eliminarEstadoVersion());
        ps.setInt(1, id);
        int eliminacion = ps.executeUpdate();
        ps.close();
        con.close();
        return eliminacion;
    }
}