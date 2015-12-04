package com.twg.persistencia.sqls;

/**
 *
 * @author Andrés Giraldo
 */
public class ComentariosSql {

    public String contarComentarios() {
        return "SELECT COUNT(*) FROM comentarios WHERE fecha_eliminacion IS NULL";
    }

    public String consultarComentarios() {
        String sql = "  SELECT \n"
                + "         com.id,\n"
                + "         com.id_persona,\n"
                + "         com.comentario,\n"
                + "         per.nombres,\n"
                + "         per.apellidos,\n"
                + "         com.fecha_creacion,\n"
                + "         com.tipo_destino,\n"
                + "         com.id_destino,\n"
                + "         com.fecha_eliminacion\n"
                + "     FROM\n"
                + "         comentarios com\n"
                + "             INNER JOIN\n"
                + "         personas per ON per.id = com.id_persona\n"
                + "     WHERE "
                + "         com.fecha_eliminacion IS NULL "
                + "         AND com.tipo_destino = ? AND com.id_destino = ? "
                + "     ORDER BY com.fecha_creacion DESC, com.id DESC";
        return sql;
    }

    public String insertarComentario() {
        return "INSERT INTO comentarios (id_persona, comentario, fecha_creacion, tipo_destino, id_destino) VALUES (?,?,now(),?,?)";
    }

    public String actualizarComentario() {
        return "UPDATE comentarios SET id_persona = ?, comentario = ?, fecha_creacion = now(), tipo_destino = ?, id_destino = ? WHERE id = ?";
    }

    public String eliminarComentario() {
        String sql = "UPDATE comentarios SET fecha_eliminacion = now() WHERE fecha_eliminacion IS NULL AND id = ? ";
        return sql;
    }
}
