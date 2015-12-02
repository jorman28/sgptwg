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
        String sql = "SELECT * FROM comentarios WHERE fecha_eliminacion IS NOT NULL AND tipo_destino = ? AND id_destino = ? ";
        return sql;
    }

    public String insertarComentario() {
        return "INSERT INTO comentarios (id_persona, comentario, fecha_creacion, tipo_destino, id_destino) VALUES (?,?,?,?,?)";
    }

    public String actualizarComentario() {
        return "UPDATE comentarios SET id_persona = ?, comentario = ?, fecha_creacion = ?, tipo_destino = ?, id_destino = ? WHERE id = ?";
    }

    public String eliminarComentario() {
        String sql = "UPDATE comentarios SET fecha_eliminacion = now() WHERE fecha_eliminacion IS NULL AND id = ? ";
        return sql;
    }
}
