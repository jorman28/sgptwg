package com.twg.negocio;

import com.twg.persistencia.beans.AuditoriasBean;
import com.twg.persistencia.daos.AuditoriasDao;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 * Clase encargada de realizar la conexión entre la vista y las operaciones en
 * base de datos.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class AuditoriasNegocio {

    private static final AuditoriasDao auditoriasDao = new AuditoriasDao();
    public final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Método encargado de consultar los datos relacionados con una auditoria
     * específica y setearlos en un JSON para su pintado en pantalla
     *
     * @param idAuditoria
     * @return El JSON con los datos que coinciden con identificador de
     * auditoria enviado
     */
    public JSONObject consultarAuditoria(Integer idAuditoria) {
        JSONObject auditoriaObject = new JSONObject();
        List<AuditoriasBean> listaAuditorias = consultarAuditorias(idAuditoria, null, null, null, null, null, null);
        if (listaAuditorias != null && !listaAuditorias.isEmpty()) {
            AuditoriasBean auditoria = listaAuditorias.get(0);
            if (auditoria != null) {
                auditoriaObject.put("id", auditoria.getId());
                auditoriaObject.put("id_persona", auditoria.getIdPersona());
                auditoriaObject.put("nombrePersona", auditoria.getNombrePersona());
                auditoriaObject.put("fecha_creacion", sdf.format(auditoria.getFechaCreacion()));
                auditoriaObject.put("clasificacion", auditoria.getClasificacion());
                auditoriaObject.put("accionAud", auditoria.getAccion());
                auditoriaObject.put("descripcion", auditoria.getDescripcion());
            }
        }
        return auditoriaObject;
    }

    /**
     * Método encargado de contar la cantidad total de registros que se
     * encuentran en base de datos con base en los filtros ingresados
     *
     * @param idAuditoria
     * @param clasificacion
     * @param accion
     * @param contiene
     * @param fecha
     * @param idPersona
     * @return La cantidad de registros que hay creados en auditorías, 
     * según los filtros aplicados.
     */
    public int cantidadAuditorias(Integer idAuditoria, String clasificacion, String accion, String contiene, Date fecha, Integer idPersona) {
        int cantidadAuditorias = 0;
        try {
            cantidadAuditorias = auditoriasDao.cantidadAuditorias(idAuditoria, clasificacion, accion, contiene, fecha, idPersona);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(TiposDocumentoNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantidadAuditorias;
    }
    
    /**
     * Método encargado de consultar la lista de auditorias que coinciden con
     * los filtros de búsqueda ingresados
     *
     * @param idAuditoria
     * @param clasificacion
     * @param accion
     * @param contiene
     * @param fecha
     * @param idPersona
     * @param limite
     * @return La lista de auditorias que coinciden con los parámetros de
     * búsqueda ingresados
     */
    public List<AuditoriasBean> consultarAuditorias(Integer idAuditoria, String clasificacion, String accion, String contiene, Date fecha, Integer idPersona, String limite) {
        List<AuditoriasBean> listaAuditorias = new ArrayList<>();
        try {
            listaAuditorias = auditoriasDao.consultarAuditorias(idAuditoria, clasificacion, accion, contiene, fecha, idPersona, limite);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(AuditoriasNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaAuditorias;
    }

    /**
     * Método en cargado de realizar la eliminación lógica de una auditoria
     * asignando una fecha de eliminación
     *
     * @param idAuditoria
     * @param personaSesion
     * @return Una cadena de texto con el error. En caso de ser vacío indica que
     * no se presentaron errores en el proceso
     */
    public String eliminarAuditoria(Integer idAuditoria, Integer personaSesion) {
        String errorEliminacion = "";
        try {
            if (auditoriasDao.eliminarAuditoria(idAuditoria) <= 0) {
                errorEliminacion = "La auditoría no pudo ser eliminada";
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(AuditoriasNegocio.class.getName()).log(Level.SEVERE, null, ex);
            errorEliminacion = "Ocurrió un error eliminando la auditoría";
        }
        return errorEliminacion;
    }

    /**
     * Método encargado de almacenar o editar el registro de auditoria en base
     * de datos
     *
     * @param idPersona
     * @param clasificacion
     * @param accion
     * @param descripcion
     * @return Una cadena con el mensaje de error en caso de que el proceso
     * que guarda la auditoría tenga un fallo.
     */
    public static String guardarAuditoria(Integer idPersona, String clasificacion, String accion, String descripcion) {
        String error = "";
        AuditoriasBean auditoria = new AuditoriasBean();
        auditoria.setIdPersona(idPersona);
        auditoria.setFechaCreacion(new Date());
        auditoria.setClasificacion(clasificacion);
        auditoria.setAccion(accion);
        auditoria.setDescripcion(descripcion);
        try {
            if (auditoriasDao.insertarAuditoria(auditoria) <= 0) {
                error = "La auditoría no pudo ser guardada";
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(AuditoriasNegocio.class.getName()).log(Level.SEVERE, null, ex);
            error = "Ocurrió un error guardando la auditoría";
        }
        return error;
    }
}
