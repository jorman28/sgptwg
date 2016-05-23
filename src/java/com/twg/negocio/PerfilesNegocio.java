package com.twg.negocio;

import com.twg.persistencia.beans.AccionesAuditadas;
import com.twg.persistencia.beans.ClasificacionAuditorias;
import com.twg.persistencia.beans.Paginas;
import com.twg.persistencia.beans.PerfilesBean;
import com.twg.persistencia.beans.UsuariosBean;
import com.twg.persistencia.daos.PerfilesDao;
import com.twg.persistencia.daos.UsuariosDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.json.simple.JSONObject;

/**
 * Clase encargada de realizar la conexión entre la vista y las operaciones en
 * base de datos, para la tabla de perfiles.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class PerfilesNegocio {

    private final PerfilesDao perfilesDao = new PerfilesDao();
    private final UsuariosDao usuariosDao = new UsuariosDao();
    private final AuditoriasNegocio auditoria = new AuditoriasNegocio();

    /**
     * Método encargado de invocar otro método para consultar los perfiles,
     * sin enviar parámetros de búsqueda
     * @return Listado de perfiles registrados en la Base de Datos.
     */
    public List<PerfilesBean> consultarPerfiles() {
        return consultarPerfiles(null, null, null);
    }

    /**
     * Método encargado de contar la cantidad total de registros que se
     * encuentran en base de datos con base en los filtros ingresados
     *
     * @param idPerfil
     * @param nombrePerfil
     * @return Cantidad de perfiles según los parámetros de búsqueda.
     */
    public int cantidadPerfiles(Integer idPerfil, String nombrePerfil) {
        int cantidadPerfiles = 0;
        try {
            cantidadPerfiles = perfilesDao.cantidadPerfiles(idPerfil, nombrePerfil, false);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(TiposDocumentoNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantidadPerfiles;
    }
    
    /**
     * Método encargado de consultar los perfiles según los parámetros de búsqueda.
     * @param idPerfil
     * @param nombrePersona
     * @param limite
     * @return Listado con todos los perfiles que resultan de la búsqueda, según 
     * los parámetros de búsqueda.
     */
    public List<PerfilesBean> consultarPerfiles(Integer idPerfil, String nombrePersona, String limite) {
        List<PerfilesBean> perfiles = new ArrayList<>();
        try {
            perfiles = perfilesDao.consultarPerfiles(idPerfil, nombrePersona, false, limite);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(PerfilesNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return perfiles;
    }

    /**
     * Método encargado de consultar un perfil específico.
     * @param idPerfil
     * @param nombrePersona
     * @return Objeto con la información del perfil consultado.
     */
    public JSONObject consultarPerfil(Integer idPerfil, String nombrePersona) {
        JSONObject perfil = new JSONObject();
        try {
            List<PerfilesBean> perfiles = perfilesDao.consultarPerfiles(idPerfil, nombrePersona, false, null);
            if (perfiles != null && !perfiles.isEmpty()) {
                perfil.put("idPerfil", perfiles.get(0).getId());
                perfil.put("nombrePerfil", perfiles.get(0).getNombre());
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(PerfilesNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return perfil;
    }

    /**
     * Método encargado de consultar los permisos que tiene un perfil asignados
     * según la página donde se encuentre navegando.
     * @param idPerfil
     * @return Mapa con todos los permisos que tiene un perfil sobre una página.
     */
    public Map<Integer, Map<String, Object>> consultarPermisosPorPagina(Integer idPerfil) {
        Map<Integer, Map<String, Object>> permisos = new TreeMap<>();
        try {
            permisos = perfilesDao.consultarPermisosPorPagina(idPerfil);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(PerfilesNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return permisos;
    }

    /**
     * Método encargado de guardar o actualizar la información de un perfil.
     * @param idPerfil
     * @param nombrePerfil
     * @param personaSesionStr
     * @return Mapa con un mensaje de éxito o error según el resultado del proceso.
     */
    public Map<String, Object> guardarPerfil(Integer idPerfil, String nombrePerfil, String personaSesionStr) {
        String mensajeExito = "";
        String mensajeError = "";
        if (nombrePerfil == null || nombrePerfil.isEmpty()) {
            mensajeError = "El campo 'Perfil' es obligatorio";
        } else {
            if (nombrePerfil.length() > 50) {
                mensajeError += "El campo 'Perfil' no debe contener más de 50 caracteres, has dígitado " + nombrePerfil.length() + " caracteres <br />";
            }
        }
        
        Integer personaSesion = null;
        try {
            personaSesion = Integer.parseInt(personaSesionStr);
        } catch (Exception e) {
        }

        if (mensajeError.isEmpty()) {
            PerfilesBean perfil = new PerfilesBean();
            perfil.setId(idPerfil);
            perfil.setNombre(nombrePerfil);
            try {
                if (idPerfil != null) {
                    List<PerfilesBean> existente = perfilesDao.consultarPerfiles(null, nombrePerfil, true, null);
                    if (existente != null && !existente.isEmpty() && existente.get(0).getId().intValue() != idPerfil) {
                        mensajeError = "El nombre de perfil seleccionado ya está registrado en el sistema";
                    } else {
                        PerfilesBean perfilAntes = perfilesDao.consultarPerfil(idPerfil);
                        int actualizacion = perfilesDao.actualizarPerfil(perfil);
                        if (actualizacion > 0) {
                            mensajeExito = "El perfil ha sido guardado con éxito";
                            //AUDITORIA
                            try {
                                String descripcioAudit = "Se modificó un perfil. Antes ("+perfilAntes.getNombre()+") Después ("+perfil.getNombre()+")";
                                String guardarAuditoria = auditoria.guardarAuditoria(personaSesion, ClasificacionAuditorias.PERMISO.getNombre(), AccionesAuditadas.EDICION.getNombre(), descripcioAudit);
                            } catch (Exception e) {
                                Logger.getLogger(PerfilesNegocio.class.getName()).log(Level.SEVERE, null, e);
                            }
                        } else {
                            mensajeError = "El perfil no pudo ser guardado";
                        }
                    }
                } else {
                    List<PerfilesBean> existente = perfilesDao.consultarPerfiles(null, nombrePerfil, true, null);
                    if (existente != null && !existente.isEmpty()) {
                        mensajeError = "El nombre de perfil seleccionado ya está registrado en el sistema";
                    } else {
                        int insercion = perfilesDao.insertarPerfil(perfil);
                        if (insercion > 0) {
                            mensajeExito = "El perfil ha sido guardado con éxito";
                            //AUDITORIA
                            try {
                                String descripcioAudit = "Se guardó un perfil nuevo llamado: "+perfil.getNombre();
                                String guardarAuditoria = auditoria.guardarAuditoria(personaSesion, ClasificacionAuditorias.PERMISO.getNombre(), AccionesAuditadas.CREACION.getNombre(), descripcioAudit);
                            } catch (Exception e) {
                                Logger.getLogger(PerfilesNegocio.class.getName()).log(Level.SEVERE, null, e);
                            }
                        } else {
                            mensajeError = "El perfil no pudo ser guardado";
                        }
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                Logger.getLogger(PerfilesNegocio.class.getName()).log(Level.SEVERE, null, ex);
                mensajeError = "Ocurrió un error insertando el perfil. Revise el log de aplicación.";
            }
        }
        Map<String, Object> result = new HashMap<>();
        if (!mensajeError.isEmpty()) {
            result.put("mensajeError", mensajeError);
        }
        if (!mensajeExito.isEmpty()) {
            result.put("mensajeExito", mensajeExito);
        }
        return result;
    }

    /**
     * Método encargado de guardar los permisos que se seleccionen para un perfil.
     * @param idPerfil
     * @param permisos
     * @param personaSesionStr
     * @return Mapa con un mensaje de éxito o error según el resultado del proceso.
     */
    public Map<String, Object> guardarPermisos(Integer idPerfil, String[] permisos, String personaSesionStr) {
        String mensajeExito = "";
        String mensajeError = "";

        List<Integer> listaPermisos = new ArrayList<>();
        if (permisos != null) {
            for (String permiso : permisos) {
                try {
                    listaPermisos.add(Integer.valueOf(permiso));
                } catch (NumberFormatException e) {
                }
            }
        }
        
        Integer personaSesion = null;
        try {
            personaSesion = Integer.parseInt(personaSesionStr);
        } catch (Exception e) {
        }

        if (!listaPermisos.isEmpty()) {
            try {
                int insercion = perfilesDao.insertarPermisos(idPerfil, listaPermisos);
                if (insercion > 0) {
                    mensajeExito = "Los permisos del perfil han sido guardados con éxito";
                    //AUDITORIA
                    try {
                        PerfilesBean perfil = perfilesDao.consultarPerfil(idPerfil);
                        String descripcioAudit = "Se guardaron los permisos para el pefil "+perfil.getNombre();
                        String guardarAuditoria = auditoria.guardarAuditoria(personaSesion, ClasificacionAuditorias.PERMISO.getNombre(), AccionesAuditadas.CREACION.getNombre(), descripcioAudit);
                    } catch (Exception e) {
                        Logger.getLogger(PerfilesNegocio.class.getName()).log(Level.SEVERE, null, e);
                    }
                } else {
                    mensajeError = "Los permisos del perfil no pudieron ser guardados";
                }
            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                Logger.getLogger(PerfilesNegocio.class.getName()).log(Level.SEVERE, null, ex);
                mensajeError = "Ocurrió un error insertando los permisos al perfil. Revise el log de aplicación.";
            }
        } else {
            mensajeError = "No se seleccionó ningún permiso para agregar al perfil";
        }

        Map<String, Object> result = new HashMap<>();
        if (!mensajeError.isEmpty()) {
            result.put("mensajeError", mensajeError);
        }
        if (!mensajeExito.isEmpty()) {
            result.put("mensajeExito", mensajeExito);
        }
        return result;
    }

    /**
     * Método encargado de eliminar un perfil junto con todos sus permisos.
     * @param idPerfil
     * @param personaSesionStr
     * @return Mapa con un mensaje de exito o error según el resultado del proceso.
     */
    public Map<String, Object> eliminarPerfil(Integer idPerfil, String personaSesionStr) {
        String mensajeExito = "";
        String mensajeError = "";
        
        Integer personaSesion = null;
        try {
            personaSesion = Integer.parseInt(personaSesionStr);
        } catch (Exception e) {
        }
        
        if (idPerfil != null) {
            try {
                List<UsuariosBean> listaUsuarios = usuariosDao.consultarUsuarios(null, null, idPerfil, null, null, null, null);
                if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
                    mensajeError = "No se puede eliminar el perfil porque está asociado a usuarios del sistema";
                } else {
                    PerfilesBean perfilEliminar = perfilesDao.consultarPerfil(idPerfil);
                    int eliminacion = perfilesDao.eliminarPerfil(idPerfil);
                    if (eliminacion > 0) {
                        mensajeExito = "El perfil fue eliminado con éxito";
                        //AUDITORIA
                        try {
                            String descripcioAudit = "Se elimino el perfil "+perfilEliminar.getNombre();
                            String guardarAuditoria = auditoria.guardarAuditoria(personaSesion, ClasificacionAuditorias.PERMISO.getNombre(), AccionesAuditadas.ELIMINACION.getNombre(), descripcioAudit);
                        } catch (Exception e) {
                            Logger.getLogger(PerfilesNegocio.class.getName()).log(Level.SEVERE, null, e);
                        }
                    } else {
                        mensajeError = "El perfil no pudo ser eliminado";
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                Logger.getLogger(PerfilesNegocio.class.getName()).log(Level.SEVERE, null, ex);
                mensajeError = "Ocurrió un error eliminando el usuario. Revise el log de aplicación.";
            }
        } else {
            mensajeError = "El perfil no pudo ser eliminado";
        }
        Map<String, Object> result = new HashMap<>();
        if (!mensajeError.isEmpty()) {
            result.put("mensajeError", mensajeError);
        }
        if (!mensajeExito.isEmpty()) {
            result.put("mensajeExito", mensajeExito);
        }
        return result;
    }

    /**
     * Método encargado de construír todo el árbol de navegación o el menú al 
     * que puede acceder una persona.
     * @param permisos
     * @param context
     * @return Cadena con el html del menú que aparece en la parte superior del 
     * sistema.
     */
    public String construccionMenuNavegacion(Map<Integer, Map<String, Object>> permisos, String context) {
        StringBuilder menu = new StringBuilder();
        menu.append("<nav class=\"navbar navbar-default\">\n");
        menu.append("   <div class=\"container-fluid\">\n");
        menu.append("       <div class=\"navbar-header\">\n");
        menu.append("           <button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#bs-example-navbar-collapse-1\" aria-expanded=\"false\">\n");
        menu.append("               <span class=\"sr-only\">Toggle navigation</span>\n");
        menu.append("               <span class=\"icon-bar\"></span>\n");
        menu.append("               <span class=\"icon-bar\"></span>\n");
        menu.append("               <span class=\"icon-bar\"></span>\n");
        menu.append("           </button>\n");
        menu.append("       </div>\n");
        menu.append("       <div class=\"collapse navbar-collapse\" id=\"bs-example-navbar-collapse-1\">\n");
        menu.append("           <ul class=\"nav navbar-nav\">\n");
        List<Integer> paginasAgregadas = new ArrayList<>();
        for (Map.Entry<Integer, Map<String, Object>> entry : permisos.entrySet()) {
            if (!paginasAgregadas.contains(entry.getKey())) {
                Map<String, Object> map = entry.getValue();
                List<Integer> listaHijos = (List<Integer>) map.get("hijos");
                if (listaHijos != null && !listaHijos.isEmpty()) {
                    menu.append("       <li class=\"dropdown\">\n");
                    menu.append("           <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">").append(map.get("nombre")).append(" <span class=\"caret\"></span></a>\n");
                    menu.append("           <ul class=\"dropdown-menu\">\n");
                    for (Integer h : listaHijos) {
                        Map<String, Object> hijo = permisos.get(h);
                        if (hijo.get("url") != null) {
                            menu.append("       <li><a href=\"").append(context).append(hijo.get("url")).append("\">").append(hijo.get("nombre")).append("</a></li>\n");
                        } else {
                            menu.append("       <li><a href=\"#\">").append(hijo.get("nombre")).append("</a></li>\n");
                        }
                        paginasAgregadas.add(h);
                    }
                    menu.append("           </ul>\n");
                    menu.append("       </li>\n");
                } else {
                    if (map.get("url") != null) {
                        menu.append("       <li><a href=\"").append(context).append(map.get("url")).append("\">").append(map.get("nombre")).append("</a></li>\n");
                    } else {
                        menu.append("       <li><a href=\"#\">").append(map.get("nombre")).append("</a></li>\n");
                    }
                }
                paginasAgregadas.add(entry.getKey());
            }
        }
        menu.append("               <li class=\"dropdown\">\n");
        menu.append("                   <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">").append("Ayuda").append(" <span class=\"caret\"></span></a>\n");
        menu.append("                   <ul class=\"dropdown-menu\">\n");
        menu.append("                       <li><a href=\"").append("#").append("\" data-toggle=\"modal\" data-target=\"#administracionPersonas\">").append("Administración de personas").append("</a></li>\n");
        menu.append("                       <li><a href=\"").append("#").append("\" data-toggle=\"modal\" data-target=\"#acerca\">").append("Acerca de").append("</a></li>\n");
        menu.append("                   </ul>\n");
        menu.append("               </li>\n");
        menu.append("               <li><a href=\"").append(context).append("/CerrarSesionController\">Cerrar sesión</a></li>\n");
        menu.append("           </ul>\n");
        menu.append("       </div>\n");
        menu.append("   </div>\n");
        menu.append("</nav>");

        return menu.toString();
    }

    /**
     * Método encargado de obtener los permisos de un perfil específico.
     * @param idPerfil
     * @return Objeto con los permisos que tiene el perfil enviado como parámetro.
     */
    public JSONObject obtenerPermisosPerfil(Integer idPerfil) {
        JSONObject resultado = new JSONObject();
        try {
            List<Integer> permisos = perfilesDao.obtenerPermisosPerfil(idPerfil);
            if (permisos != null && !permisos.isEmpty()) {
                for (Integer permiso : permisos) {
                    resultado.put("permiso_" + permiso, true);
                }
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(PerfilesNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    /**
     * Método encargado de construír el listado de permisos o de accesos que tiene
     * un perfil por página.
     * @param request
     * @param pagina
     * @return Listado de permisos por página.
     */
    public static List<String> permisosPorPagina(HttpServletRequest request, Paginas pagina) {
        List<String> permisos = null;
        Map<Integer, Map<String, Object>> datosPagina = (Map<Integer, Map<String, Object>>) request.getSession().getAttribute("permisos");
        if (datosPagina != null) {
            Map<String, Object> accesos = datosPagina.get(pagina.getIdPagina());
            if (accesos != null) {
                permisos = (List<String>) accesos.get("permisos");
            }
        }
        return permisos;
    }
}
