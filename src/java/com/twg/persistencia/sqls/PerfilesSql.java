package com.twg.persistencia.sqls;

import java.util.List;

/**
 * Esta clase define métodos para contruír los SQLs utilizados en el DAO.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class PerfilesSql {

    /**
     * Constructor de la clase.
     */
    public PerfilesSql() {
    }

    /**
     * Método encargado de consultar los perfiles, aplicando diferentes filtros
     * según los parámetros que lleguen distintos de nulos.
     *
     * @param idPerfil
     * @param nombrePerfil
     * @param nombreExacto
     * @param limite
     * @return El SQL de la sentencia de base de datos
     */
    public String consultarPerfiles(Integer idPerfil, String nombrePerfil, boolean nombreExacto, String limite) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM perfiles WHERE 1 = 1 ");
        if (idPerfil != null) {
            sql.append("AND id = ").append(idPerfil).append(" ");
        }
        if (nombrePerfil != null && !nombrePerfil.isEmpty()) {
            if (nombreExacto) {
                sql.append("AND nombre = \"").append(nombrePerfil).append("\" ");
            } else {
                sql.append("AND nombre LIKE \"%").append(nombrePerfil).append("%\" ");
            }
        }
        if (limite != null && !limite.isEmpty()) {
            sql.append("LIMIT ").append(limite).append(" ");
        }
        return sql.toString();
    }

    /**
     * Método encargado de consultar los perfiles, aplicando diferentes filtros
     * según los parámetros que lleguen distintos de nulos.
     *
     * @param idPerfil
     * @param nombrePerfil
     * @param nombreExacto
     * @return El SQL de la sentencia de base de datos
     */
    public String cantidadPerfiles(Integer idPerfil, String nombrePerfil, boolean nombreExacto) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) AS cantidadPerfiles FROM perfiles WHERE 1 = 1 ");
        if (idPerfil != null) {
            sql.append("AND id = ").append(idPerfil).append(" ");
        }
        if (nombrePerfil != null && !nombrePerfil.isEmpty()) {
            if (nombreExacto) {
                sql.append("AND nombre = \"").append(nombrePerfil).append("\" ");
            } else {
                sql.append("AND nombre LIKE \"%").append(nombrePerfil).append("%\" ");
            }
        }
        return sql.toString();
    }
    
    /**
     * Método encargado de consultar un perfil específico según el parámetro que
     * envíen.
     *
     * @param id
     * @return El SQL de la sentencia de base de datos
     */
    public String consultarPerfil(int id) {
        return "SELECT id, nombre FROM perfiles WHERE id = " + id;
    }

    /**
     * Método encargado de retornar el SQL para insertar un nuevo perfil.
     *
     * @return El SQL de la sentencia de base de datos
     */
    public String insertarPerfil() {
        return "INSERT INTO perfiles (nombre) VALUES (?)";
    }

    /**
     * Método encargado de retornar el SQL para actualizar un perfil existente.
     *
     * @return El SQL de la sentencia de base de datos
     */
    public String actualizarPerfil() {
        return "UPDATE perfiles SET nombre = ? WHERE id = ?";
    }

    /**
     * Método encargado de retornar el SQL para eliminar todos los permisos
     * asociados a un perfil específico.
     *
     * @return El SQL de la sentencia de base de datos
     */
    public String eliminarPermisosPorPerfil() {
        return "DELETE FROM permisos_perfiles WHERE perfil = ?";
    }

    /**
     * Método encargado de retornar el SQL para eliminar físicamente un perfil
     * de la base de datos.
     *
     * @return El SQL de la sentencia de base de datos
     */
    public String eliminarPerfil() {
        return "DELETE FROM perfiles WHERE id = ?";
    }

    /**
     * Método encargado de insertar todos los permisos de un perfil.
     *
     * @param idPerfil
     * @param permisos
     * @return El SQL de la sentencia de base de datos
     */
    public String insertarPermisos(Integer idPerfil, List<Integer> permisos) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO permisos_perfiles (permiso, perfil) VALUES ");
        //Se recorre la lista de permisos para armar el SQL, asociando uno por uno al perfil.
        for (Integer permiso : permisos) {
            sql.append("(").append(permiso).append(", ").append(idPerfil).append("),");
        }
        return sql.toString().substring(0, sql.toString().length() - 1);
    }

    /**
     * Método encargado de consultar los permisos para un perfil específico.
     *
     * @param idPerfil
     * @return El SQL de la sentencia de base de datos
     */
    public String consultarPermisos(Integer idPerfil) {
        String sql = "SELECT DISTINCT\n"
                + "    pag.id idPagina,\n"
                + "    pag.nombre AS pagina,\n"
                + "    pag.url,\n"
                + "    pag.grupo paginaPadre,\n"
                + "    perm.permiso\n"
                + "FROM\n"
                + "    perfiles perf\n"
                + "        INNER JOIN\n"
                + "    permisos_perfiles pxp ON pxp.perfil = perf.id\n"
                + "        INNER JOIN\n"
                + "    permisos perm ON pxp.permiso = perm.id\n"
                + "        INNER JOIN\n"
                + "    paginas pag ON perm.pagina = pag.id\n";
        if (idPerfil != null) {
            sql += "WHERE perf.id = " + idPerfil + " ";
        }
        sql += "ORDER BY paginaPadre, idPagina ASC;";
        return sql;
    }

    /**
     * Método encargado de consultar los permisos de un perfil específico.
     *
     * @return El SQL de la sentencia de base de datos
     */
    public String obtenerPermisosPerfil() {
        return "SELECT DISTINCT permiso FROM permisos_perfiles WHERE perfil = ?";
    }
}
