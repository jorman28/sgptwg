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
     * @return
     */
    public String consultarPersonas(String idPersona, String documento, String tipoDocumento, String nombres, String apellidos, String correo, String usuario, String perfil, String cargo, String nombreCompleto) {
        String sql = "";
        sql += "SELECT  "
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
                + "WHERE "
                + "    1 = 1 AND p.fecha_eliminacion IS NULL ";
        if (idPersona != null && !idPersona.isEmpty() && !idPersona.equals("0")) {
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
     * Método encargado de retornar el SQL para consultar las personas
     * pertenecientes a un proyecto, por nombres, apellidos o documento.
     *
     * @param idProyecto
     * @param Busqueda
     * @return
     */
    public String consultarPersonasProyecto(String idProyecto, String Busqueda) {
        String sql = "";
        sql += "SELECT p.id,\n"
                + "        p.documento,\n"
                + "        p.tipo_documento,\n"
                + "        d.nombre AS nombre_tipo_documento,\n"
                + "        p.nombres,\n"
                + "        p.apellidos, \n"
                + "        p.telefono, \n"
                + "        p.celular, \n"
                + "        p.correo, \n"
                + "        p.direccion, \n"
                + "        p.cargo, \n"
                + "        car.nombre nombre_cargo, \n"
                + "        u.usuario, \n"
                + "        u.perfil AS id_perfil, \n"
                + "        pf.nombre AS nombre_perfil \n"
                + "FROM    personas p \n"
                + "		INNER JOIN personas_proyectos pro ON pro.id_persona = p.id \n"
                + "		INNER JOIN tipos_documentos d ON d.tipo = p.tipo_documento \n"
                + "        INNER JOIN cargos car ON car.id = p.cargo \n"
                + "        LEFT JOIN usuarios u ON p.id = u.id_persona AND u.fecha_eliminacion IS NULL \n"
                + "        LEFT JOIN perfiles pf ON pf.id = u.perfil AND pf.fecha_eliminacion IS NULL \n"
                + "WHERE   1 = 1 AND p.fecha_eliminacion IS NULL ";

        if (idProyecto != null && !idProyecto.isEmpty()) {
            sql += "AND pro.id_proyecto = " + idProyecto + " ";
        }

        if (Busqueda != null && !Busqueda.isEmpty()) {
            sql += "AND (CONCAT(p.nombres, ' ', p.apellidos) LIKE '%" + Busqueda + "%' OR p.documento like '%" + Busqueda + "%')";
        }

        return sql;
    }

    /**
     * Método encargado de retornar el SQL para consultar las personas que
     * pertenezcan a una actividad en específico.
     *
     * @param idActividad
     * @return
     */
    public String consultarPersonasActividad(Integer idActividad) {
        String sql = "";
        sql += "SELECT  p.id,\n"
                + "		p.documento,\n"
                + "		p.tipo_documento,\n"
                + "		d.nombre AS nombre_tipo_documento,\n"
                + "		p.nombres,\n"
                + "		p.apellidos, \n"
                + "		p.telefono, \n"
                + "		p.celular, \n"
                + "		p.correo, \n"
                + "		p.direccion, \n"
                + "		p.cargo, \n"
                + "		car.nombre nombre_cargo, \n"
                + "		u.usuario, \n"
                + "		u.perfil AS id_perfil, \n"
                + "		pf.nombre AS nombre_perfil \n"
                + "FROM    personas p \n"
                + "		INNER JOIN actividades_empleados ae ON ae.empleado = p.id \n"
                + "		INNER JOIN tipos_documentos d ON d.tipo = p.tipo_documento \n"
                + "		INNER JOIN cargos car ON car.id = p.cargo \n"
                + "		LEFT JOIN usuarios u ON p.id = u.id_persona AND u.fecha_eliminacion IS NULL \n"
                + "		LEFT JOIN perfiles pf ON pf.id = u.perfil AND pf.fecha_eliminacion IS NULL \n"
                + "WHERE   1 = 1 AND p.fecha_eliminacion IS NULL AND ae.actividad = " + idActividad + "";
        return sql;
    }

    /**
     * Método encargado de consultas las actividades asociadas a una persona.
     *
     * @param idPersona
     * @param idActividad
     * @return
     */
    public String consultarPersonasAsignadasActividad(Integer idPersona, Integer idActividad) {
        String sql = "";
        sql += "SELECT DISTINCT p.id,\n"
                + "        p.documento,\n"
                + "        p.tipo_documento,\n"
                + "        d.nombre AS nombre_tipo_documento,\n"
                + "        p.nombres,\n"
                + "        p.apellidos, \n"
                + "        p.telefono, \n"
                + "        p.celular, \n"
                + "        p.correo, \n"
                + "        p.direccion, \n"
                + "        p.cargo, \n"
                + "        car.nombre nombre_cargo, \n"
                + "        u.usuario, \n"
                + "        u.perfil AS id_perfil, \n"
                + "        pf.nombre AS nombre_perfil \n"
                + "FROM    personas p \n"
                + "	   INNER JOIN actividades_empleados ae ON ae.empleado = p.id \n"
                + "	   INNER JOIN actividades act ON act.id = ae.actividad\n"
                + "	   INNER JOIN tipos_documentos d ON d.tipo = p.tipo_documento \n"
                + "        INNER JOIN cargos car ON car.id = p.cargo \n"
                + "        LEFT JOIN usuarios u ON p.id = u.id_persona AND u.fecha_eliminacion IS NULL \n"
                + "        LEFT JOIN perfiles pf ON pf.id = u.perfil AND pf.fecha_eliminacion IS NULL \n"
                + "WHERE   1 = 1 AND p.fecha_eliminacion IS NULL AND act.fecha_estimada_inicio >= ? AND act.fecha_estimada_terminacion <= ? AND ae.empleado = " + idPersona + " ";

        if (idActividad != null && idActividad.intValue() != 0) {
            sql += "AND act.id != " + idActividad + "";
        }

        return sql;
    }

    /**
     * Método encargado de retornar el SQL para insertar una nueva persona.
     *
     * @return
     */
    public String insertarPersona() {
        return "INSERT INTO personas (documento, tipo_documento, nombres, apellidos, direccion, telefono, celular, correo, cargo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    /**
     * Método encargado de retornar el SQL para actualizar una persona
     * existente.
     *
     * @return
     */
    public String actualizarPersona() {
        return "UPDATE personas "
                + "SET documento = ?, tipo_documento = ?, nombres = ?, apellidos = ?, direccion = ?, telefono = ?, celular = ?, correo = ?, cargo = ? "
                + "WHERE id = ?";
    }

    /**
     * Método encargado de eliminar lógicamente una persona, actualizando la
     * fecha de eliminación con la fecha actual.
     *
     * @return
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
     * @return
     */
    public String consultarIdPersona(String documento, String tipoDocumento) {
        String sql = "";
        sql += "SELECT id FROM personas WHERE documento = '" + documento + "'";
        if (tipoDocumento != null && !tipoDocumento.isEmpty() && !tipoDocumento.equals("0")) {
            sql += "AND tipo_documento = '" + tipoDocumento + "' ";
        }
        return sql;
    }
}
