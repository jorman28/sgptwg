package com.twg.persistencia.daos;

import com.twg.persistencia.beans.UsuariosBean;
import com.twg.persistencia.sqls.UsuariosSql;
import com.twg.utilidades.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pipe
 */
public class UsuariosDao {
    private final UsuariosSql sql = new UsuariosSql();
    
    public UsuariosDao(){
    }

    public List<UsuariosBean> consultarUsuarios() throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        return consultarUsuarios(null, null, null, null, null, null);
    }
    
    public List<UsuariosBean> consultarUsuarios(String nombreUsuario, String clave) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        return consultarUsuarios(null, nombreUsuario, clave, null, null, null);
    }
    
    public List<UsuariosBean> consultarUsuarios(Integer idPersona) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        return consultarUsuarios(idPersona, null, null, null, null, null);
    }
    
    public List<UsuariosBean> consultarUsuarios(Integer idPersona, String nombreUsuario, String clave, Integer perfil, String documento, String tipoDocumento) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        List<UsuariosBean> listaUsuarios = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarUsuarios(idPersona, nombreUsuario, clave, perfil, documento, tipoDocumento));
        ResultSet rs;
        rs = ps.executeQuery();
        while(rs.next()){
            UsuariosBean usuario = new UsuariosBean();
            usuario.setIdPersona(rs.getInt("id_persona"));
            usuario.setDocumento(rs.getString("documento"));
            usuario.setTipoDocumento(rs.getString("id_tipo_documento"));
            usuario.setDescripcionTipoDocumento(rs.getString("descripcion_tipo_documento"));
            usuario.setUsuario(rs.getString("usuario"));
            usuario.setClave(rs.getString("clave"));
            usuario.setPerfil(rs.getInt("id_perfil"));
            usuario.setDescripcionPerfil("descripcion_perfil");
            
            listaUsuarios.add(usuario);
        }
        rs.close();
        ps.close();
        con.close();
        return listaUsuarios;
    }
    
    public int insertarUsuario(UsuariosBean usuario) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarUsuario());
        ps.setInt(1, usuario.getIdPersona());
        ps.setString(2, usuario.getUsuario());
        ps.setString(3, usuario.getClave());
        ps.setInt(4, usuario.getPerfil());
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }
    
    public int actualizarUsuario(UsuariosBean usuario) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actualizarUsuario());
        ps.setString(1, usuario.getUsuario());
        ps.setString(2, usuario.getClave());
        ps.setInt(3, usuario.getPerfil());
        ps.setInt(4, usuario.getIdPersona());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }
    
    public int eliminarUsuario(Integer idPersona) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.eliminarUsuario());
        ps.setInt(1, idPersona);
        int eliminacion = ps.executeUpdate();
        ps.close();
        con.close();
        return eliminacion;
    }
}