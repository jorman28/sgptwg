package com.twg.persistencia.daos;

import com.twg.persistencia.beans.ComentariosBean;
import com.twg.persistencia.sqls.ComentariosSql;
import com.twg.utilidades.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Pipe
 */
public class ComentariosDao {

    private final ComentariosSql sql = new ComentariosSql();

    public List<ComentariosBean> consultarComentarios(Integer id, String tipoDestino, Integer idDestino) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<ComentariosBean> listaComentarios = new ArrayList();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarComentarios(id));
        if(id==null){
            ps.setString(1, tipoDestino);
            ps.setInt(2, idDestino);
        }
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
            ComentariosBean comentario = new ComentariosBean();
            comentario.setId(rs.getInt("id"));
            comentario.setIdPersona(rs.getInt("id_persona"));
            comentario.setNombres(rs.getString("nombres"));
            comentario.setApellidos(rs.getString("apellidos"));
            comentario.setComentario(rs.getString("comentario"));
            comentario.setFechaCreacion((Date)rs.getObject("fecha_creacion"));
            comentario.setTipoDestino(rs.getString("tipo_destino"));
            comentario.setIdDestino(rs.getInt("id_destino"));
            comentario.setFechaEliminacion((Date)rs.getObject("fecha_eliminacion"));

            listaComentarios.add(comentario);
        }
        rs.close();
        ps.close();
        con.close();
        return listaComentarios;
    }

    public int crearComentario(ComentariosBean comentario) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarComentario());
        ps.setInt(1, comentario.getIdPersona());
        ps.setString(2, comentario.getComentario());
        ps.setString(3, comentario.getTipoDestino());
        ps.setInt(4, comentario.getIdDestino());
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }

    public int actualizarComentario(ComentariosBean comentario) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actualizarComentario());
        ps.setInt(1, comentario.getIdPersona());
        ps.setString(2, comentario.getComentario());
        ps.setString(3, comentario.getTipoDestino());
        ps.setInt(4, comentario.getIdDestino());
        ps.setInt(5, comentario.getId());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }

    public int eliminarComentario(Integer idComentario) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.eliminarComentario());
        ps.setInt(1, idComentario);
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }
}
