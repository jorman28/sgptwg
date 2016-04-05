package com.twg.persistencia.sqls;

import java.util.Date;

/**
 * Clase que contiene las sentencias SQL para la interacción con la base de
 * datos
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class ArchivosSql {

    public String consultarArchivos(Integer idArchivo, String contine, Date fecha, Integer idPersona) {
        String sql = "SELECT \n"
                + "    arc.id,\n"
                + "    arc.nombre,\n"
                + "    arc.descripcion,\n"
                + "    arc.fecha_creacion,\n"
                + "    arc.ruta,\n"
                + "    arc.id_persona,\n"
                + "    arc.tipo,\n"
                + "    CONCAT(per.nombres, ' ', per.apellidos) AS nombre_persona\n"
                + "FROM\n"
                + "    archivos arc\n"
                + "        INNER JOIN\n"
                + "    personas per ON arc.id_persona = per.id\n"
                + "WHERE\n"
                + "    arc.fecha_eliminacion IS NULL\n";
        if (idArchivo != null) {
            sql += "        AND arc.id = " + idArchivo + "\n";
        }
        if (fecha != null) {
            sql += "        AND arc.fecha_creacion = ?\n";
        }
        if (contine != null && !contine.isEmpty()) {
            sql += "        AND (arc.nombre LIKE '%" + contine + "%'\n"
                    + "        OR arc.descripcion LIKE '%" + contine + "%')\n";
        }
        if (idPersona != null) {
            sql += "        AND arc.id_persona = '" + idPersona + "';";
        }
        return sql;
    }

    public String insertarArchivo() {
        return "INSERT INTO archivos (nombre, descripcion, fecha_creacion, ruta, id_persona, tipo) VALUES (?,?,?,?,?,?)";
    }

    public String actualizarArchivo() {
        return "UPDATE archivos SET nombre = ?, descripcion = ? WHERE id = ?";
    }

    public String eliminarArchivo() {
        return "UPDATE archivos SET fecha_eliminacion = now() WHERE id = ? AND fecha_eliminacion IS NULL";
    }
}
