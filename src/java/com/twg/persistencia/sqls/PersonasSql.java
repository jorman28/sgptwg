package com.twg.persistencia.sqls;

/**
 *
 * @author Erika Jhoana
 */
public class PersonasSql {

    public PersonasSql() {
    }

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
            sql += "and (CONCAT(p.nombres, ' ', p.apellidos) LIKE '%" + nombreCompleto + "%' OR p.documento like '%"+nombreCompleto+"%')";
        }
        return sql;
    }

    public String insertarPersona() {
        return "INSERT INTO personas (documento, tipo_documento, nombres, apellidos, direccion, telefono, celular, correo, cargo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    public String actualizarPersona() {
        return "UPDATE personas "
                + "SET documento = ?, tipo_documento = ?, nombres = ?, apellidos = ?, direccion = ?, telefono = ?, celular = ?, correo = ?, cargo = ? "
                + "WHERE id = ?";
    }

    public String eliminarPersona() {
        return "UPDATE personas SET fecha_eliminacion = now() WHERE id = ?";
    }

    public String consultarIdPersona(String documento, String tipoDocumento) {
        return "SELECT id FROM personas WHERE documento = '" + documento + "' AND tipo_documento = '" + tipoDocumento + "'";
    }
}
