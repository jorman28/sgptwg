package com.twg.persistencia.sqls;

/**
 * Esta clase define métodos para contruír los SQLs utilizados en el DAO.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class PersonasSql {

    /**
     * Constructor de la clase
     */
    public PersonasSql() {
    }

    /**
     * Método encargado de consultar las personas, aplicando diferentes filtros
     * según los parámetros que lleguen distintos de nulos.
     *
     * @param idPersona
     * @param documento
     * @param tipoDocumento
     * @param nombres
     * @param apellidos
     * @param correo
     * @param usuario
     * @param perfil
     * @param cargo
     * @param nombreCompleto
     * @param busquedaExacta
     * @param idProyecto
     * @param limite
     * @return El SQL de la sentencia de base de datos
     */
    public String consultarPersonas(Integer idPersona, String documento, String tipoDocumento, String nombres, String apellidos, String correo, String usuario, String perfil, String cargo, String nombreCompleto, Boolean busquedaExacta, Integer idProyecto, String limite) {
        String sql = "";
        sql += "SELECT DISTINCT "
                + "    p.id, "
                + "    p.documento, "
                + "    p.tipo_documento, "
                + "    d.nombre AS nombre_tipo_documento, "
                + "    p.nombres, "
                + "    p.apellidos, "
                + "    p.telefono, "
                + "    p.celular, "
                + "    p.correo, "
                + "    p.direccion, "
                + "    p.cargo, "
                + "    car.nombre nombre_cargo, "
                + "    u.usuario, "
                + "    u.perfil AS id_perfil, "
                + "    pf.nombre AS nombre_perfil "
                + "FROM "
                + "    personas p "
                + "        INNER JOIN "
                + "    tipos_documentos d ON d.tipo = p.tipo_documento "
                + "        INNER JOIN "
                + "    cargos car ON car.id = p.cargo "
                + "        LEFT JOIN "
                + "    usuarios u ON p.id = u.id_persona AND u.fecha_eliminacion IS NULL "
                + "        LEFT JOIN "
                + "    perfiles pf ON pf.id = u.perfil AND pf.fecha_eliminacion IS NULL "
                + "        LEFT JOIN "
                + "    personas_proyectos pro ON pro.id_persona = p.id  "
                + "WHERE "
                + "    1 = 1 AND p.fecha_eliminacion IS NULL ";
        if (idPersona != null) {
            sql += "and p.id = " + idPersona + " ";
        }
        if (documento != null && !documento.isEmpty() && !busquedaExacta) {
            sql += "and p.documento like '%" + documento + "%' ";
        } else if (documento != null && !documento.isEmpty() && busquedaExacta) {
            sql += "and p.documento = " + documento + " ";
        }
        if (tipoDocumento != null && !tipoDocumento.isEmpty() && !tipoDocumento.equals("0")) {
            sql += "and d.tipo = '" + tipoDocumento + "' ";
        }
        if (nombres != null && !nombres.isEmpty()) {
            sql += "and p.nombres like '%" + nombres + "%' ";
        }
        if (apellidos != null && !apellidos.isEmpty()) {
            sql += "and p.apellidos like '%" + apellidos + "%' ";
        }
        if (correo != null && !correo.isEmpty()) {
            sql += "and p.correo like '%" + correo + "%' ";
        }
        if (usuario != null && !usuario.isEmpty()) {
            sql += "and u.usuario like '%" + usuario + "%' ";
        }
        if (perfil != null && !perfil.isEmpty() && !perfil.equals("0")) {
            sql += "and u.perfil = " + perfil + " ";
        }
        if (cargo != null && !cargo.isEmpty() && !cargo.equals("0")) {
            sql += "and p.cargo = " + cargo + " ";
        }
        if (nombreCompleto != null && !nombreCompleto.isEmpty()) {
            sql += "and (CONCAT(p.nombres, ' ', p.apellidos) LIKE '%" + nombreCompleto + "%' OR p.documento like '%" + nombreCompleto + "%')";
        }
        if (idProyecto != null && idProyecto.intValue() != 0) {
            sql += "and pro.id_proyecto = " + idProyecto + " ";
        }
        if (limite != null && !limite.isEmpty()) {
            sql += "	LIMIT " + limite + " ";
        }
        return sql;
    }

    /**
     * Método encargado de consultar la cantidad de personas, aplicando
     * diferentes filtros según los parámetros que lleguen distintos de nulos.
     *
     * @param idPersona
     * @param documento
     * @param tipoDocumento
     * @param nombres
     * @param apellidos
     * @param correo
     * @param usuario
     * @param perfil
     * @param cargo
     * @param nombreCompleto
     * @return El SQL de la sentencia de base de datos
     */
    public String cantidadPersonas(Integer idPersona, String documento, String tipoDocumento, String nombres, String apellidos, String correo, String usuario, String perfil, String cargo, String nombreCompleto) {
        String sql = "";
        sql += "SELECT COUNT(*) AS cantidadPersonas "
                + "FROM "
                + "    personas p "
                + "        INNER JOIN "
                + "    tipos_documentos d ON d.tipo = p.tipo_documento "
                + "        INNER JOIN "
                + "    cargos car ON car.id = p.cargo "
                + "        LEFT JOIN "
                + "    usuarios u ON p.id = u.id_persona AND u.fecha_eliminacion IS NULL "
                + "        LEFT JOIN "
                + "    perfiles pf ON pf.id = u.perfil AND pf.fecha_eliminacion IS NULL "
                + "WHERE "
                + "    1 = 1 AND p.fecha_eliminacion IS NULL ";
        if (idPersona != null) {
            sql += "and p.id = " + idPersona + " ";
        }
        if (documento != null && !documento.isEmpty()) {
            sql += "and p.documento like '%" + documento + "%' ";
        }
        if (tipoDocumento != null && !tipoDocumento.isEmpty() && !tipoDocumento.equals("0")) {
            sql += "and d.tipo = '" + tipoDocumento + "' ";
        }
        if (nombres != null && !nombres.isEmpty()) {
            sql += "and p.nombres like '%" + nombres + "%' ";
        }
        if (apellidos != null && !apellidos.isEmpty()) {
            sql += "and p.apellidos like '%" + apellidos + "%' ";
        }
        if (correo != null && !correo.isEmpty()) {
            sql += "and p.correo like '%" + correo + "%' ";
        }
        if (usuario != null && !usuario.isEmpty()) {
            sql += "and u.usuario like '%" + usuario + "%' ";
        }
        if (perfil != null && !perfil.isEmpty() && !perfil.equals("0")) {
            sql += "and u.perfil = " + perfil + " ";
        }
        if (cargo != null && !cargo.isEmpty() && !cargo.equals("0")) {
            sql += "and p.cargo = " + cargo + " ";
        }
        if (nombreCompleto != null && !nombreCompleto.isEmpty()) {
            sql += "and (CONCAT(p.nombres, ' ', p.apellidos) LIKE '%" + nombreCompleto + "%' OR p.documento like '%" + nombreCompleto + "%')";
        }
        return sql;
    }

    /**
     * Método encargado de retornar el SQL para insertar una nueva persona.
     *
     * @return El SQL de la sentencia de base de datos
     */
    public String insertarPersona() {
        return "INSERT INTO personas (documento, tipo_documento, nombres, apellidos, direccion, telefono, celular, correo, cargo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    /**
     * Método encargado de retornar el SQL para actualizar una persona
     * existente.
     *
     * @return El SQL de la sentencia de base de datos
     */
    public String actualizarPersona() {
        return "UPDATE personas "
                + "SET documento = ?, tipo_documento = ?, nombres = ?, apellidos = ?, direccion = ?, telefono = ?, celular = ?, correo = ?, cargo = ?, fecha_eliminacion = null "
                + "WHERE id = ?";
    }

    /**
     * Método encargado de eliminar lógicamente una persona, actualizando la
     * fecha de eliminación con la fecha actual.
     *
     * @return El SQL de la sentencia de base de datos
     */
    public String eliminarPersona() {
        return "UPDATE personas SET fecha_eliminacion = now() WHERE id = ?";
    }

    /**
     * Mètodo encargado de retornar el SQL para consultar a una persona por el
     * número y tipo de documento.
     *
     * @param documento
     * @param tipoDocumento
     * @return El SQL de la sentencia de base de datos
     */
    public String consultarIdPersona(String documento, String tipoDocumento) {
        String sql = "SELECT id, fecha_eliminacion FROM personas WHERE documento = '" + documento + "'";
        if (tipoDocumento != null && !tipoDocumento.isEmpty() && !tipoDocumento.equals("0")) {
            sql += "AND tipo_documento = '" + tipoDocumento + "' ";
        }
        return sql;
    }
}
