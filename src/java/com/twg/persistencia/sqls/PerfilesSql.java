package com.twg.persistencia.sqls;

import java.util.List;

/**
 *
 * @author Pipe
 */
public class PerfilesSql {
    
    public PerfilesSql(){
    }
    
    public String consultarPerfiles(Integer idPerfil, String nombrePerfil, boolean nombreExacto){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM perfiles WHERE 1 = 1 ");
        if(idPerfil != null){
            sql.append("AND id = ").append(idPerfil).append(" ");
        }
        if(nombrePerfil != null && !nombrePerfil.isEmpty()){
            if(nombreExacto){
                sql.append("AND nombre = \"").append(nombrePerfil).append("\" ");
            } else {
                sql.append("AND nombre LIKE \"%").append(nombrePerfil).append("%\" ");
            }
        }
        return sql.toString();
    }
    
    public String consultarPerfil(int id){
        return "SELECT id, nombre FROM perfiles WHERE id = "+id;
    }

    public String insertarPerfil(){
        return "INSERT INTO perfiles (nombre) VALUES (?)";
    }
    
    public String actualizarPerfil(){
        return "UPDATE perfiles SET nombre = ? WHERE id = ?";
    }
    
    public String eliminarPermisosPorPerfil(){
        return "DELETE FROM permisos_perfiles WHERE perfil = ?";
    }
    
    public String eliminarPerfil(){
        return "DELETE FROM perfiles WHERE id = ?";
    }
    
    public String insertarPermisos(Integer idPerfil, List<Integer> permisos){
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO permisos_perfiles (permiso, perfil) VALUES ");
        for (Integer permiso : permisos) {
            sql.append("(").append(permiso).append(", ").append(idPerfil).append("),");
        }
        return sql.toString().substring(0, sql.toString().length()-1);
    }
    
    public String consultarPermisos(Integer idPerfil){
        String sql = "SELECT DISTINCT\n" +
                "    pag.id idPagina,\n" +
                "    pag.nombre AS pagina,\n" +
                "    pag.url,\n" +
                "    pag.grupo paginaPadre,\n" +
                "    perm.permiso\n" +
                "FROM\n" +
                "    perfiles perf\n" +
                "        INNER JOIN\n" +
                "    permisos_perfiles pxp ON pxp.perfil = perf.id\n" +
                "        INNER JOIN\n" +
                "    permisos perm ON pxp.permiso = perm.id\n" +
                "        INNER JOIN\n" +
                "    paginas pag ON perm.pagina = pag.id\n";
        if(idPerfil != null){
            sql +=  "WHERE perf.id = " + idPerfil + " ";
        }
        sql +=  "ORDER BY paginaPadre, idPagina ASC;";
        return sql;
    }
    
    public String obtenerPermisosPerfil(){
        return "SELECT DISTINCT permiso FROM permisos_perfiles WHERE perfil = ?";
    }
}
