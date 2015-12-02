package com.twg.negocio;

import com.twg.persistencia.beans.ComentariosBean;
import com.twg.persistencia.daos.ComentariosDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrés Giraldo
 */
public class ComentariosNegocio {

    private final ComentariosDao comentariosDao = new ComentariosDao();

    public String guardarComentario(String id, String idPersona, String comentarioEscrito, String tipoDestino, String idDestino) {
        String error = "";
        ComentariosBean comentario = new ComentariosBean();
        comentario.setIdPersona(Integer.valueOf(idPersona));
        comentario.setComentario(comentarioEscrito);
        comentario.setTipoDestino(tipoDestino);
        comentario.setIdDestino(Integer.valueOf(idDestino));
        comentario.setFechaCreacion(new Date());
        try {
            int guardado = 0;
            if (id != null && !id.isEmpty()) {
                comentario.setId(Integer.valueOf(id));
                guardado = comentariosDao.actualizarComentario(comentario);
            } else {
                guardado = comentariosDao.crearComentario(comentario);
            }
            if (guardado == 0) {
                error += "El comentario no pudo ser guardado";
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ComentariosNegocio.class.getName()).log(Level.SEVERE, null, ex);
            error += "Ocurrió un error guardando el comentario";
        }
        return error;
    }

    public String validarDatos(String comentario) {
        String validacion = "";
        if (comentario == null || comentario.isEmpty()) {
            validacion += "Debe ingresar un comentario para poder realizar la acción";
        }
        return validacion;
    }

    public List<ComentariosBean> consultarComentarios(String tipoDestino, Integer idDestino) {
        List<ComentariosBean> listaComentarios = new ArrayList<>();
        try {
            listaComentarios = comentariosDao.consultarComentarios(tipoDestino, idDestino);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ComentariosNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaComentarios;
    }

    public String eliminarComentario(Integer idComentario) {
        String error = "";
        try {
            int eliminacion = comentariosDao.eliminarComentario(idComentario);
            if (eliminacion == 0) {
                error = "El comentario no pudo ser eliminado";
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ProyectosNegocio.class.getName()).log(Level.SEVERE, null, ex);
            error = "Ocurrió un error eliminando el comentario";
        }
        return error;
    }
}
