package com.twg.persistencia.sqls;

/**
 *
 * @author Erika Jhoana
 */
public class PersonasSql {

    public PersonasSql() {
    }

    public String consultarPersonas(String tipoPersona, Integer idPersona, String documento, String tipoDocumento, String nombres, String apellidos, String correo, String usuario, String perfil) {
        String sql = "";
        sql += "SELECT  " +
                "    p.id, " +
                "    p.documento, " +
                "    p.tipo_documento, " +
                "    d.nombre AS nombre_tipo_documento, " +
                "    p.nombres, " +
                "    p.apellidos, " +
                "    p.telefono, " +
                "    p.celular, " +
                "    p.correo, " +
                "    p.direccion, " +
                "    u.usuario, " +
                "    u.perfil AS id_perfil, " +
                "    pf.nombre AS nombre_perfil, " +
                "    u.clave ";
        if(tipoPersona.equals("EMPLEADO")){
            sql += " ,car.id AS cargo, " +
                   " car.nombre AS nombre_cargo " ;
        } else {
            sql += " ,cli.fecha_inicio ";
        }
        sql +=  "FROM " +
                "    personas p " +
                "        INNER JOIN " +
                "    tipos_documentos d ON d.tipo = p.tipo_documento ";
        if(tipoPersona.equals("EMPLEADO")){
            sql += "     INNER JOIN " +
                "    empleados emp ON emp.id_persona = p.id " +
                "        INNER JOIN " +
                "    cargos car ON car.id = emp.cargo ";
        } else {
            sql += "     INNER JOIN " +
                "    clientes cli ON cli.id_persona = p.id ";
        }
        sql +=  "        LEFT JOIN " +
                "    usuarios u ON p.id = u.id_persona " +
                "        LEFT JOIN " +
                "    perfiles pf ON pf.id = u.perfil " +
                "WHERE " +
                "    1 = 1 ";
        if (idPersona != null && idPersona != 0) {
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
            sql += "and pf.nombre like '%" + perfil + "%' ";
        }
        return sql;
    }

    public String insertarPersona() {
        return "INSERT INTO personas (id, documento, tipo_documento, nombres, apellidos, direccion, telefono, celular, correo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    public String actualizarPersona() {
        return "UPDATE personas "
                + "SET documento = ?, tipo_documento = ?, nombres = ?, apellidos = ?, direccion = ?, telefono = ?, celular = ?, correo = ? "
                + "WHERE id = ?";
    }

    public String eliminarPersona() {
        return "DELETE FROM personas WHERE id = ?";
    }

    public String consultarIdPersona(String documento, String tipoDocumento) {
        return "SELECT id FROM personas WHERE documento = '" + documento + "' AND tipo_documento = '" + tipoDocumento + "'";
    }
}
