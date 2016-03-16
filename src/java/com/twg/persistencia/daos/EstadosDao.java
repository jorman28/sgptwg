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
 *
 * @author Jorman Rincón
 */
public class EstadosDao {
    private final EstadosSql sql = new EstadosSql();
    
    public EstadosDao(){
    }
    
    public List<EstadosBean> consultarEstados(Integer id) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        return consultarEstados(id, null, null, null, null, null);
    }
    
    public List<EstadosBean> consultarEstados(Integer id, String tipoEstado, String nombre, Integer estadoPrev, 
            Integer estadoSig, String eFinal) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        List<EstadosBean> listaEstados = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarEstados(id, tipoEstado, nombre, estadoPrev, estadoSig, eFinal));
        ResultSet rs;
        rs = ps.executeQuery();
        while(rs.next()){
            EstadosBean estado = new EstadosBean();
            estado.setId(rs.getInt("id"));
            estado.setTipo_estado(rs.getString("tipo_estado"));            
            estado.setNombre(rs.getString("nombre"));
            estado.setEstadoPrev(rs.getInt("estado_prev"));
            estado.setEstadoSig(rs.getInt("estado_sig"));
            estado.seteFinal(rs.getString("e_final"));
            listaEstados.add(estado);
        }
        rs.close();
        ps.close();
        con.close();
        return listaEstados;
    }
    
    public List<EstadosBean> consultarEstadosPS(Integer id) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        List<EstadosBean> listaEstados = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarEstadosPS(id));
        ResultSet rs;
        rs = ps.executeQuery();
        while(rs.next()){
            EstadosBean estado = new EstadosBean();
            estado.setId(rs.getInt("id"));
            estado.setTipo_estado(rs.getString("tipo_estado"));            
            estado.setNombre(rs.getString("nombre"));
            estado.setEstadoPrev(rs.getInt("estado_prev"));
            estado.setEstadoSig(rs.getInt("estado_sig"));
            estado.seteFinal(rs.getString("e_final"));
            listaEstados.add(estado);
        }
        rs.close();
        ps.close();
        con.close();
        return listaEstados;
    }
    
    public Integer consultarId(String nombre) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Integer idPersona = null;
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarId(nombre));
        ResultSet rs;
        rs = ps.executeQuery();
        if(rs.next()){
            idPersona = rs.getInt("id");
        }
        rs.close();
        ps.close();
        con.close();
        return idPersona;
    }
    
    public int insertarEstado(EstadosBean estado) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarEstado());
        //ps.setInt(1, estado.getId());
        ps.setString(1, estado.getTipo_estado());        
        ps.setString(2, estado.getNombre());
        ps.setInt(3, estado.getEstadoPrev());
        ps.setInt(4, estado.getEstadoSig());
        ps.setString(5, estado.geteFinal());
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }
    
    public int actualizarEstado(EstadosBean estado) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actualizarEstado());
        ps.setInt(6, estado.getId());
        ps.setString(1, estado.getTipo_estado());        
        ps.setString(2, estado.getNombre());
        ps.setInt(3, estado.getEstadoPrev());
        ps.setInt(4, estado.getEstadoSig());
        ps.setString(5, estado.geteFinal());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }
    
    public int eliminarEstado(Integer id) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
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