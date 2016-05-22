package com.twg.persistencia.sqls;

/**
 * Esta clase define métodos para contruír los SQLs utilizados en el DAO.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class UsuariosSql {

    /**
     * Constructor de la clase.
     */
    public UsuariosSql() {
    }

    /**
     * Método encargado de consultar los usuarios, aplicando diferentes filtros
     * según los parámetros que reciba distintos de nulos.
     *
     * @param idPersona
     * @param usuario
     * @param perfil
     * @param activo
     * @param documento
     * @param tipoDocumento
     * @param limite
     * @return
     */
    public String consultarUsuarios(Integer idPersona, String usuario, Integer perfil, String activo, String documento, String tipoDocumento, String limite) {
        String sql = "SELECT "
                + "   per.id AS id_persona, "
                + "	per.documento, "
                + "	per.tipo_documento AS id_tipo_documento, "
                + "	tip.nombre AS descripcion_tipo_documento, "
                + "	perf.id AS id_perfil, "
                + "	perf.nombre AS descripcion_perfil, "
                + "	usu.usuario, "
                + "	usu.activo, "
                + "	usu.clave, "
                + "	usu.fecha_eliminacion "
                + "FROM "
                + "    usuarios usu "
                + "        INNER JOIN "
                + "    personas per ON per.id = usu.id_persona "
                + "        INNER JOIN "
                + "    tipos_documentos tip ON tip.tipo = per.tipo_documento "
                + "        INNER JOIN "
                + "    perfiles perf ON perf.id = usu.perfil "
                + "WHERE "
                + "	1 = 1 ";
        if (idPersona != null) {
            sql += "	AND usu.id_persona = " + idPersona + " ";
        }else{
            sql += "    AND usu.fecha_eliminacion is null ";
        }
        if (usuario != null && !usuario.isEmpty()) {
            sql += "	AND BINARY usu.usuario = '" + usuario + "' ";
        }
        if (activo != null && !activo.isEmpty()) {
            sql += "	AND usu.activo = '" + activo + "' ";
        }
        if (documento != null && !documento.isEmpty()) {
            sql += "	AND per.documento = '" + documento + "' ";
        }
        if (tipoDocumento != null && !tipoDocumento.isEmpty() && !tipoDocumento.endsWith("0")) {
            sql += "	AND per.tipo_documento = '" + tipoDocumento + "' ";
        }
        if (perfil != null) {
            sql += "	AND perf.id = " + perfil + " ";
        }
        if (limite != null && !limite.isEmpty()) {
            sql += "	LIMIT " + limite + " ";
        }
        return sql;
    }

    public String cantidadUsuarios(Integer idPersona, String usuario, Integer perfil, String activo, String documento, String tipoDocumento) {
        String sql = "SELECT COUNT(*) AS cantidadUsuarios "
                + "FROM "
                + "    usuarios usu "
                + "        INNER JOIN "
                + "    personas per ON per.id = usu.id_persona "
                + "        INNER JOIN "
                + "    tipos_documentos tip ON tip.tipo = per.tipo_documento "
                + "        INNER JOIN "
                + "    perfiles perf ON perf.id = usu.perfil "
                + "WHERE "
                + "	1 = 1 ";
        if (idPersona != null) {
            sql += "	AND usu.id_persona = " + idPersona + " ";
        }else{
            sql += "    AND usu.fecha_eliminacion is null ";
        }
        if (usuario != null && !usuario.isEmpty()) {
            sql += "	AND BINARY usu.usuario = '" + usuario + "' ";
        }
        if (activo != null && !activo.isEmpty()) {
            sql += "	AND usu.activo = '" + activo + "' ";
        }
        if (documento != null && !documento.isEmpty()) {
            sql += "	AND per.documento = '" + documento + "' ";
        }
        if (tipoDocumento != null && !tipoDocumento.isEmpty() && !tipoDocumento.endsWith("0")) {
            sql += "	AND per.tipo_documento = '" + tipoDocumento + "' ";
        }
        if (perfil != null) {
            sql += "	AND perf.id = " + perfil + " ";
        }
        return sql;
    }

    /**
     * Método encargado de retornar el SQL para insertar uno nuevo usuario.
     *
     * @return
     */
    public String insertarUsuario() {
        return "INSERT INTO usuarios (id_persona, usuario, clave, perfil, activo) VALUES (?, ?, ?, ?, ?)";
    }

    /**
     * Método encargado de retornar el SQL para actualizar un usuario existente.
     *
     * @return
     */
    public String actualizarUsuario() {
        return "UPDATE usuarios SET usuario = ?, clave = ?, perfil = ?, activo = ?, fecha_eliminacion = ? WHERE id_persona = ?";
    }

    /**
     * Método encargado de eliminar lógicamente un usuario, actualizando la
     * fecha de eliminación con la fecha actual.
     *
     * @return
     */
    public String eliminarUsuario() {
        return "UPDATE usuarios SET fecha_eliminacion = now(), activo = 'F' WHERE id_persona = ?";
    }

}
