package com.twg.persistencia.daos;

import com.twg.persistencia.beans.PerfilesBean;
import com.twg.persistencia.sqls.PerfilesSql;
import com.twg.utilidades.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Pipe
 */
public class PerfilesDao {
    
    private final PerfilesSql sql = new PerfilesSql();
    
    public PerfilesDao(){
    }

    /* Eliminar cuando se arregle la pantalla de personas */
    public PerfilesBean consultarPerfil(Integer idPerfil) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        List<PerfilesBean> listaPerfiles = consultarPerfiles(idPerfil, null, false);
        return listaPerfiles.get(0);
    }
    
    public List<PerfilesBean> consultarPerfiles(Integer idPerfil, String nombrePerfil, boolean nombreExacto) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        List<PerfilesBean> listaPerfiles = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarPerfiles(idPerfil, nombrePerfil, nombreExacto));
        ResultSet rs;
        rs = ps.executeQuery();
        while(rs.next()){
            PerfilesBean perfil = new PerfilesBean();
            perfil.setId(rs.getInt("id"));
            perfil.setNombre(rs.getString("nombre"));
            listaPerfiles.add(perfil);
        }
        rs.close();
        ps.close();
        con.close();
        return listaPerfiles;
    }
    
    public List<Integer> obtenerPermisosPerfil(Integer idPerfil) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        List<Integer> permisos = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.obtenerPermisosPerfil());
        ps.setInt(1, idPerfil);
        ResultSet rs;
        rs = ps.executeQuery();
        while(rs.next()){
            permisos.add(rs.getInt("permiso"));
        }
        rs.close();
        ps.close();
        con.close();
        return permisos;
    }
    
    public int insertarPerfil(PerfilesBean perfil) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarPerfil());
        ps.setString(1, perfil.getNombre());
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }
    
    public int actualizarPerfil(PerfilesBean perfil) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actualizarPerfil());
        ps.setString(1, perfil.getNombre());
        ps.setInt(2, perfil.getId());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }
    
    public int eliminarPerfil(Integer idPerfil) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        con.setAutoCommit(false);
        PreparedStatement ps;
        ps = con.prepareStatement(sql.eliminarPermisosPorPerfil());
        ps.setInt(1, idPerfil);
        ps.executeUpdate();
        ps = con.prepareStatement(sql.eliminarPerfil());
        ps.setInt(1, idPerfil);
        int eliminacion = ps.executeUpdate();
        con.commit();
        ps.close();
        con.close();
        return eliminacion;
    }
    
    public int insertarPermisos(Integer idPerfil, List<Integer> permisos) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        con.setAutoCommit(false);
        PreparedStatement ps;
        ps = con.prepareStatement(sql.eliminarPermisosPorPerfil());
        ps.setInt(1, idPerfil);
        ps.executeUpdate();
        ps = con.prepareStatement(sql.insertarPermisos(idPerfil, permisos));
        int insercion = ps.executeUpdate();
        con.commit();
        ps.close();
        con.close();
        return insercion;
    }
    
    public Map<Integer, Map<String, Object>> consultarPermisosPorPagina(Integer idPerfil) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Map<Integer, Map<String, Object>> resultado = new LinkedHashMap<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarPermisos(idPerfil));
        ResultSet rs;
        rs = ps.executeQuery();
        while(rs.next()){
            Integer idPagina = rs.getInt("idPagina");
            if(resultado.get(idPagina) == null){
                resultado.put(idPagina, new HashMap<String, Object>());
            }
            resultado.get(idPagina).put("nombre", rs.getString("pagina"));
            resultado.get(idPagina).put("url", rs.getString("url"));
            List<String> listaPermisos;
            String permiso = rs.getString("permiso");
            if(resultado.get(idPagina).get("permisos") != null){
                listaPermisos = (List<String>) resultado.get(idPagina).get("permisos");
                if(!listaPermisos.contains(permiso)){
                    listaPermisos.add(permiso);
                }
            } else {
                listaPermisos = new ArrayList<>();
                listaPermisos.add(permiso);
            }
            resultado.get(idPagina).put("permisos", listaPermisos);
            Integer paginaPadre = rs.getInt("paginaPadre");
            List<Integer> listaHijos;
            if(resultado.get(paginaPadre) != null){
                if(resultado.get(paginaPadre).get("hijos") != null){
                    listaHijos = (List<Integer>) resultado.get(paginaPadre).get("hijos");
                    if(!listaHijos.contains(idPagina)){
                        listaHijos.add(idPagina);
                    }
                } else {
                    listaHijos = new ArrayList<>();
                    listaHijos.add(idPagina);
                }
                resultado.get(paginaPadre).put("hijos", listaHijos);
            }
        }
        rs.close();
        ps.close();
        con.close();
        return resultado;
    }
}
