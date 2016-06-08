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
 * Clase encargada de obtener la conexión con la base de datos y ejecutar las
 * sentencias con base en los datos enviados desde el negocio
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class PerfilesDao {
    
    private final PerfilesSql sql = new PerfilesSql();
    
    /**
     * Método constructor de la clase.
     */
    public PerfilesDao(){
    }

    /**
     * Método encargado de consultar la información de un perfil específico.
     * @param idPerfil
     * @return Un objeto con todos los atributos del perfil consultado.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public PerfilesBean consultarPerfil(Integer idPerfil) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        List<PerfilesBean> listaPerfiles = consultarPerfiles(idPerfil, null, false, null);
        return listaPerfiles.get(0);
    }
    
    /**
     * Método encargado de consultar todos los perfiles del sistema.
     * @return Listado con todos los perfiles existentes.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public List<PerfilesBean> consultarPerfiles() throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        return consultarPerfiles(null, null, false, null);
    }
    
    /**
     * Método encargado de consultar la cantidad de usuarios existentes en base
     * de datos relacionados con los filtros ingresados
     * @param idPerfil
     * @param nombrePerfil
     * @param nombreExacto
     * @return Cantdad de perfiles según los filtros aplicados.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public int cantidadPerfiles(Integer idPerfil, String nombrePerfil, boolean nombreExacto) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        int cantidadPerfiles = 0;
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.cantidadPerfiles(idPerfil, nombrePerfil, nombreExacto));
        ResultSet rs;
        rs = ps.executeQuery();
        if (rs.next()) {
            cantidadPerfiles = rs.getInt("cantidadPerfiles");
        }
        rs.close();
        ps.close();
        con.close();
        return cantidadPerfiles;
    }
    
    /**
     * Método encargado de consultar los perfiles según los parámetros de búsqueda.
     * @param idPerfil
     * @param nombrePerfil
     * @param nombreExacto
     * @param limite
     * @return Listado con la información de los perfiles consultados.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public List<PerfilesBean> consultarPerfiles(Integer idPerfil, String nombrePerfil, boolean nombreExacto, String limite) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        List<PerfilesBean> listaPerfiles = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarPerfiles(idPerfil, nombrePerfil, nombreExacto, limite));
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
    
    /**
     * Método encargado de obtener os permisos de un perfil específico.
     * @param idPerfil
     * @return Un listado con los permisos de un perfil específico.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
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
    
    /**
     * Método encargado de insertar un nuevo perfil.
     * @param perfil
     * @return Un número indicando si el proceso se realizó o no.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
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
    
    /**
     * Método encargado de actualizar la información de un perfil.
     * @param perfil
     * @return Un número indicando si el proceso se realizó o no.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
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
    
    /**
     * Método encargado de eliminar un perfil específico.
     * @param idPerfil
     * @return Un número indicando si el proceso se realizó o no.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
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
    
    /**
     * Método encargado de insertar los permisos de un perfil específico.
     * @param idPerfil
     * @param permisos
     * @return Un número indicando si el proceso se realizó o no.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
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
    
    /**
     * Método encargado de consultar los permisos que tiene un perfil dentro de
     * una página.
     * @param idPerfil
     * @return Mapa con los permisos de un perfil por cada página.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
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
