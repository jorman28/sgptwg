package com.twg.persistencia.sqls;

import java.util.Date;

/**
 * Clase que contiene las sentencias SQL para la interacción con la tabla de
 * auditorías de la base de datos
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class AuditoriasSql {

    /**
     * Método encargado de consultar las auditorias, aplicando diferentes filtros
     * según los parámetros que lleguen distintos de nulos.
     * 
     * @param idAuditoria
     * @param clasificacion
     * @param accion
     * @param contiene
     * @param fecha
     * @param idPersona
     * @param limite
     * @return 
     */
    public String consultarAuditorias(Integer idAuditoria, String clasificacion, String accion, String contiene, Date fecha, Integer idPersona, String limite) {
        String sql = "SELECT \n"
                + "    aud.id,\n"
                + "    aud.id_persona,\n"
                + "    CONCAT(per.nombres, ' ', per.apellidos) AS nombre_persona,\n"
                + "    aud.fecha_creacion,\n"
                + "    aud.clasificacion,\n"
                + "    aud.accion,\n"
                + "    aud.descripcion\n"
                + "FROM\n"
                + "    auditorias aud\n"
                + "        INNER JOIN\n"
                + "    personas per ON aud.id_persona = per.id\n"
                + "WHERE\n"
                + "    aud.fecha_eliminacion IS NULL\n";
        if (idAuditoria != null && idAuditoria != 0) {
            sql += "AND aud.id = " + idAuditoria + " ";
        }
        if (clasificacion != null && !clasificacion.isEmpty() && !clasificacion.equals("0")) {
            sql += "AND aud.clasificacion = '" + clasificacion + "' ";
        }
        if (accion != null && !accion.isEmpty() && !accion.equals("0")) {
            sql += "AND aud.accion = '" + accion + "' ";
        }
        if (contiene != null && !contiene.isEmpty()) {
            sql += "AND aud.descripcion LIKE '%" + contiene + "%' ";
        }
        if (fecha != null) {
            sql += "AND aud.fecha_creacion = ? ";
        }
        if (idPersona != null) {
            sql += "AND aud.id_persona = " + idPersona + " ";
        }
        if (limite != null && !limite.isEmpty()) {
            sql += "	LIMIT " + limite + " ";
        }
        return sql;
    }

    /**
     * Método encargado de consultar las auditorias, aplicando diferentes filtros
     * según los parámetros que lleguen distintos de nulos.
     * 
     * @param idAuditoria
     * @param clasificacion
     * @param accion
     * @param contiene
     * @param fecha
     * @param idPersona
     * @return 
     */
    public String cantidadAuditorias(Integer idAuditoria, String clasificacion, String accion, String contiene, Date fecha, Integer idPersona) {
        String sql = "SELECT COUNT(*) AS cantidadAuditorias \n"
                + "FROM\n"
                + "    auditorias aud\n"
                + "        INNER JOIN\n"
                + "    personas per ON aud.id_persona = per.id\n"
                + "WHERE\n"
                + "    aud.fecha_eliminacion IS NULL\n";
        if (idAuditoria != null && idAuditoria != 0) {
            sql += "AND aud.id = " + idAuditoria + " ";
        }
        if (clasificacion != null && !clasificacion.isEmpty() && !clasificacion.equals("0")) {
            sql += "AND aud.clasificacion = '" + clasificacion + "' ";
        }
        if (accion != null && !accion.isEmpty() && !accion.equals("0")) {
            sql += "AND aud.accion = '" + accion + "' ";
        }
        if (contiene != null && !contiene.isEmpty()) {
            sql += "AND aud.descripcion LIKE '%" + contiene + "%' ";
        }
        if (fecha != null) {
            sql += "AND aud.fecha_creacion = ? ";
        }
        if (idPersona != null && idPersona != 0) {
            sql += "AND aud.id_persona = " + idPersona + " ";
        }
        return sql;
    }
    
    /**
     * Método encargado de retornar el SQL para insertar una nueva auditoria.
     * @return 
     */
    public String insertarAuditoria() {
        return "INSERT INTO auditorias (id_persona, fecha_creacion, clasificacion, accion, descripcion) VALUES (?,?,?,?,?)";
    }

    /**
     * Método encargado de retornar el SQL para eliminar lógicamente una auditoria, 
     * actualizando la fecha de eliminación con la fecha actual.
     * @return 
     */
    public String eliminarAuditoria() {
        return "UPDATE auditorias SET fecha_eliminacion = now() WHERE id = ? AND fecha_eliminacion IS NULL";
    }
}
