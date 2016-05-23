package com.twg.negocio;

import com.twg.persistencia.beans.AccionesAuditadas;
import com.twg.persistencia.beans.ClasificacionAuditorias;
import com.twg.persistencia.beans.ComentariosBean;
import com.twg.persistencia.beans.Permisos;
import com.twg.persistencia.daos.ComentariosDao;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase encargada de realizar la conexión entre la vista y las operaciones en
 * base de datos, para la tabla de comentarios.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class ComentariosNegocio {

    private final ComentariosDao comentariosDao = new ComentariosDao();
    private final AuditoriasNegocio auditoria = new AuditoriasNegocio();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    public final String TIPO_ARCHIVO = "ARCHIVOS";
    public final String TIPO_VERSION = "VERSION";
    public final String TIPO_ACTIVIDAD = "ACTIVIDAD";

    /**
     * Método encargado de guardar o actualizar un comentario dentro de una 
     * actividad o versión.
     * @param id
     * @param idPersona
     * @param comentarioEscrito
     * @param tipoDestino
     * @param idDestino
     * @return Cadena con un mensaje de error en caso de que el proceso falle.
     */
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
                List<ComentariosBean> comentarioAntes = comentariosDao.consultarComentarios(Integer.valueOf(id), null, null);
                guardado = comentariosDao.actualizarComentario(comentario);
                //AUDITORIA
                try {
                    String descripcioAudit = "Se actualizó un comentario. ANTES ("
                            + " Comentario: " + comentarioAntes.get(0).getComentario()
                            + ", Destino: " + comentarioAntes.get(0).getTipoDestino()
                            + ") DESPUÉS ( Comentario: " + comentario.getComentario()
                            + ", Destino: " + comentario.getTipoDestino() + ")";
                    String guardarAuditoria = auditoria.guardarAuditoria(idPersona, ClasificacionAuditorias.COMENTARIO.getNombre(), AccionesAuditadas.EDICION.getNombre(), descripcioAudit);
                } catch (Exception e) {
                    Logger.getLogger(ComentariosNegocio.class.getName()).log(Level.SEVERE, null, e);
                }
            } else {
                guardado = comentariosDao.crearComentario(comentario);
                //AUDITORIA
                try {
                    String descripcioAudit = "Se creó el siguiente comentario (" + comentario.getComentario() + ") en (" + comentario.getTipoDestino() + ")";
                    String guardarAuditoria = auditoria.guardarAuditoria(idPersona, ClasificacionAuditorias.COMENTARIO.getNombre(), AccionesAuditadas.CREACION.getNombre(), descripcioAudit);
                } catch (Exception e) {
                    Logger.getLogger(ComentariosNegocio.class.getName()).log(Level.SEVERE, null, e);
                }
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

    /**
     * Método encargado de hacer las validaciones correspondientes para agregar 
     * un comentario.
     * @param comentario
     * @return Cadena con un mensaje de error en caso de que alguna validación
     * no cumpla.
     */
    public String validarDatos(String comentario) {
        String validacion = "";
        if (comentario == null || comentario.isEmpty()) {
            validacion += "Debe ingresar un comentario para poder realizar la acción";
        }
        return validacion;
    }

    /**
     * Método encargado de consultar el listado de comentarios según el destino,
     * ya sea en actividades o versiones.
     * @param tipoDestino
     * @param idDestino
     * @return Listado de comentarios según los parámetros de búsqueda.
     */
    public List<ComentariosBean> consultarComentarios(String tipoDestino, Integer idDestino) {
        List<ComentariosBean> listaComentarios = new ArrayList<>();
        try {
            listaComentarios = comentariosDao.consultarComentarios(null, tipoDestino, idDestino);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ComentariosNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaComentarios;
    }

    /**
     * Método encargado de eliminar un comentario específico.
     * @param idComentario
     * @param personaSesion
     * @return Cadena con un mensaje de error en caso de que el proceso de 
     * eliminación falle.
     */
    public String eliminarComentario(Integer idComentario, Integer personaSesion) {
        String error = "";
        try {
            List<ComentariosBean> comentarioEliminar = comentariosDao.consultarComentarios(idComentario, null, null);
            int eliminacion = comentariosDao.eliminarComentario(idComentario);
            if (eliminacion == 0) {
                error = "El comentario no pudo ser eliminado";
            } else {
                //AUDITORIA
                try {
                    String descripcioAudit = "Se eliminó el comentario (" + comentarioEliminar.get(0).getComentario() + ") realizado en (" + comentarioEliminar.get(0).getTipoDestino() + ")";
                    String guardarAuditoria = auditoria.guardarAuditoria(personaSesion, ClasificacionAuditorias.COMENTARIO.getNombre(), AccionesAuditadas.ELIMINACION.getNombre(), descripcioAudit);
                } catch (Exception e) {
                    Logger.getLogger(ComentariosNegocio.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ProyectosNegocio.class.getName()).log(Level.SEVERE, null, ex);
            error = "Ocurrió un error eliminando el comentario";
        }
        return error;
    }

    /**
     * Método encargado de construír el html que pinta el listado de comentarios
     * realizados en actividades o versiones.
     * @param tipoDestino
     * @param idDestino
     * @return Cadena con el html que pinta todo el listado de comentarios, según
     * los parámetros de búsqueda.
     */
    public String listaComentarios(List<String> permisos, Integer idPersona, String tipoDestino, Integer idDestino) {
        String resultado = "";
        List<ComentariosBean> listaComentarios = consultarComentarios(tipoDestino, idDestino);
        if (listaComentarios != null && !listaComentarios.isEmpty()) {
            for (ComentariosBean comentario : listaComentarios) {
                resultado += "  <div id=\"comentario" + comentario.getId() + "\" class=\"list-group\">\n"
                        + "         <div class=\"list-group-item\">\n";
                try {
                    if (permisos.contains(Permisos.ELIMINAR_COMENTARIOS.getNombre())
                            || (permisos.contains(Permisos.ELIMINAR_COMENTARIO_PROPIO.getNombre()) && idPersona.intValue() == comentario.getIdPersona().intValue())) {
                        resultado += "  <button type=\"button\" class=\"close\" data-toggle=\"modal\" data-target=\"#eliminacionComentarios\" onclick=\"$('#idComentario').val(" + comentario.getId() + ");\"><span aria-hidden=\"true\">&times;</span></button>\n";
                    }
                } catch (Exception e) {
                }
                resultado += "          <p class=\"list-group-item-heading\"><strong>" + comentario.getNombres() + " " + comentario.getApellidos() + " (" + sdf.format(comentario.getFechaCreacion()) + ")" + "</strong></p>\n"
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
