package com.twg.negocio;

import com.twg.persistencia.beans.ComentariosBean;
import com.twg.persistencia.daos.ComentariosDao;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    public final String TIPO_ARCHIVO = "ARCHIVOS";
    public final String TIPO_VERSION = "VERSION";
    public final String TIPO_ACTIVIDAD = "ACTIVIDAD";

    public String guardarComentario(String id, Integer idPersona, String comentarioEscrito, String tipoDestino, Integer idDestino) {
        String error = "";
        ComentariosBean comentario = new ComentariosBean();
        comentario.setIdPersona(idPersona);
        comentario.setComentario(comentarioEscrito);
        comentario.setTipoDestino(tipoDestino);
        comentario.setIdDestino(idDestino);
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

    public String listaComentarios(String tipoDestino, Integer idDestino) {
        String resultado = "";
        List<ComentariosBean> listaComentarios = consultarComentarios(tipoDestino, idDestino);
        if (listaComentarios != null && !listaComentarios.isEmpty()) {
            for (ComentariosBean comentario : listaComentarios) {
                resultado += "  <div id=\"comentario" + comentario.getId() + "\" class=\"list-group\">\n"
                        + "         <div class=\"list-group-item\">\n"
                        + "             <button type=\"button\" class=\"close\" onclick=\"eliminarComentario(" + comentario.getId() + ")\"><span aria-hidden=\"true\">&times;</span></button>\n"
                        + "             <p class=\"list-group-item-heading\"><strong>" + comentario.getNombres() + " " + comentario.getApellidos() + " (" + sdf.format(comentario.getFechaCreacion()) + ")" + "</strong></p>\n"
                        + "             <p class=\"list-group-item-text\">" + comentario.getComentario() + "</p>\n"
                        + "         </div>\n"
                        + "     </div>";
            }
        } else {
            resultado += "No hay comentarios disponibles";
        }
        return resultado;
    }
}
