package com.twg.persistencia.sqls;

/**
 *
 * @author Pipe
 */
public class PerfilesSql {
    
    public PerfilesSql(){
    }
    
    public String consultarPerfiles(){
        return "SELECT * FROM perfiles";
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
    
    public String eliminarPerfil(){
        return "DELETE FROM perfiles WHERE id = ?";
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
}
