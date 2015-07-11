package com.twg.persistencia.sqls;

/**
 *
 * @author Pipe
 */
public class UsuariosSql {

    public UsuariosSql() {
    }

    public String consultarUsuarios(Integer idPersona, String usuario, Integer perfil, String activo, String documento, String tipoDocumento) {
        String sql = "SELECT " +
                    "   per.id AS id_persona, " +
                    "	per.documento, " +
                    "	per.tipo_documento AS id_tipo_documento, " +
                    "	tip.nombre AS descripcion_tipo_documento, " +
                    "	perf.id AS id_perfil, " +
                    "	perf.nombre AS descripcion_perfil, " +
                    "	usu.usuario, " +
                    "	usu.activo, " +
                    "	usu.clave " +
                    "FROM " +
                    "    usuarios usu " +
                    "        INNER JOIN " +
                    "    personas per ON per.id = usu.id_persona " +
                    "        INNER JOIN " +
                    "    tipos_documentos tip ON tip.tipo = per.tipo_documento " +
                    "        INNER JOIN " +
                    "    perfiles perf ON perf.id = usu.perfil " +
                    "WHERE " +
                    "	1 = 1 ";
        if(idPersona != null){
            sql +=  "	AND usu.id_persona = "+idPersona+" ";
        }
        if(usuario != null && !usuario.isEmpty()){
            sql +=  "	AND usu.usuario = '"+usuario+"' ";
        }
        if(activo != null && !activo.isEmpty()){
            sql +=  "	AND usu.activo = '"+activo+"' ";
        }
        if(documento != null && !documento.isEmpty()){
            sql +=  "	AND per.documento LIKE '%"+documento+"%' ";
        }
        if(tipoDocumento != null && !tipoDocumento.isEmpty() && !tipoDocumento.endsWith("0")){
            sql +=  "	AND per.tipo_documento = '"+tipoDocumento+"' ";
        }
        if(perfil != null){
            sql +=  "	AND perf.id = "+perfil+" ";
        }
        return sql;
    }

    public String insertarUsuario() {
        return "INSERT INTO usuarios (id_persona, usuario, clave, perfil, activo) VALUES (?, ?, ?, ?, ?)";
    }

    public String actualizarUsuario() {
        return "UPDATE usuarios SET usuario = ?, clave = ?, perfil = ?, activo = ? WHERE id_persona = ?";
    }

    public String eliminarUsuario() {
        return "DELETE FROM usuarios WHERE id_persona = ?";
    }

}
