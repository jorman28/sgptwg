package com.twg.persistencia.sqls;

/**
 * Esta clase define métodos para contruír los SQLs utilizados en el DAO.
 * 
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class ComentariosSql {

    /**
     * Método encargado de retornar el SQL para contar el número de comentarios
     * que existen.
     * @return 
     */
    public String contarComentarios() {
        return "SELECT COUNT(*) FROM comentarios WHERE fecha_eliminacion IS NULL";
    }

    /**
     * Método encargado de retornar el SQL para consultar los comentarios según
     * el lugar donde ses vayan a visualizar.
     * @param id
     * @return 
     */
    public String consultarComentarios(Integer id) {
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
                + "         com.fecha_eliminacion IS NULL ";
                if (id != null) {
                    sql += "         AND com.id = " + id + " ";
                }else{
                    sql += "         AND com.tipo_destino = ? AND com.id_destino = ? ";
                }
                sql += "     ORDER BY com.fecha_creacion, com.id";
        return sql;
    }

    /**
     * Método encargado de retornar el SQL para insertar un nuevo comentario.
     * @return 
     */
    public String insertarComentario() {
        return "INSERT INTO comentarios (id_persona, comentario, fecha_creacion, tipo_destino, id_destino) VALUES (?,?,now(),?,?)";
    }

    /**
     * Método encargado de retornar el SQL para actualizar un comentario existente.
     * @return 
     */
    public String actualizarComentario() {
        return "UPDATE comentarios SET id_persona = ?, comentario = ?, fecha_creacion = now(), tipo_destino = ?, id_destino = ? WHERE id = ?";
    }

    /**
     * Método encargado de retornar el SQL para eliminar lógicamente un 
     * comentario, actualizando la fecha de eliminación por la fecha actual.
     * @return 
     */
    public String eliminarComentario() {
        String sql = "UPDATE comentarios SET fecha_eliminacion = now() WHERE fecha_eliminacion IS NULL AND id = ? ";
        return sql;
    }
}
