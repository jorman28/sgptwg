package com.twg.persistencia.sqls;

import java.util.Date;

/**
 * Clase que contiene las sentencias SQL para la interacción con la tabla de
 * auditorías de la base de datos
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class AuditoriasSql {

    public String consultarAuditorias(Integer idAuditoria, String clasificacion, String accion, String contiene, Date fecha, Integer idPersona) {
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
        if (idAuditoria != null && idAuditoria.intValue() != 0) {
            sql += "AND aud.id = " + idAuditoria + " ";
        }
        if (clasificacion != null && !clasificacion.isEmpty()) {
            sql += "AND aud.clasificacion = '" + clasificacion + "' ";
        }
        if (accion != null && !accion.isEmpty()) {
            sql += "AND aud.accion = '" + accion + "' ";
        }
        if (contiene != null && !contiene.isEmpty()) {
            sql += "AND aud.contine LIKE '%" + contiene + "%' ";
        }
        if (fecha != null) {
            sql += "AND aud.fecha_creacion = ? ";
        }
        if (idPersona != null && idPersona.intValue() != 0) {
            sql += "AND aud.id_persona = " + idPersona + " ";
        }
        return sql;
    }

    public String insertarAuditoria() {
        return "INSERT INTO auditorias (id_persona, fecha_creacion, clasificacion, accion, descripcion) VALUES (?,?,?,?,?)";
    }

    public String eliminarAuditoria() {
        return "UPDATE auditorias SET fecha_eliminacion = now() WHERE id = ? AND fecha_eliminacion IS NULL";
    }
}
