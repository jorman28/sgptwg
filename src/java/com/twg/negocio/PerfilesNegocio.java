package com.twg.negocio;

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

public class PerfilesNegocio {

    private final PerfilesDao perfilesDao = new PerfilesDao();
    private final UsuariosDao usuariosDao = new UsuariosDao();

    public List<PerfilesBean> consultarPerfiles() {
        return consultarPerfiles(null, null);
    }

    public List<PerfilesBean> consultarPerfiles(Integer idPerfil, String nombrePersona) {
        List<PerfilesBean> perfiles = new ArrayList<>();
        try {
            perfiles = perfilesDao.consultarPerfiles(idPerfil, nombrePersona, false);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(PerfilesNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return perfiles;
    }

    public JSONObject consultarPerfil(Integer idPerfil, String nombrePersona) {
        JSONObject perfil = new JSONObject();
        try {
            List<PerfilesBean> perfiles = perfilesDao.consultarPerfiles(idPerfil, nombrePersona, false);
            if (perfiles != null && !perfiles.isEmpty()) {
                perfil.put("idPerfil", perfiles.get(0).getId());
                perfil.put("nombrePerfil", perfiles.get(0).getNombre());
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(PerfilesNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return perfil;
    }

    public Map<Integer, Map<String, Object>> consultarPermisosPorPagina(Integer idPerfil) {
        Map<Integer, Map<String, Object>> permisos = new TreeMap<>();
        try {
            permisos = perfilesDao.consultarPermisosPorPagina(idPerfil);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(PerfilesNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return permisos;
    }

    public Map<String, Object> guardarPerfil(Integer idPerfil, String nombrePerfil) {
        String mensajeExito = "";
        String mensajeError = "";
        if (nombrePerfil == null || nombrePerfil.isEmpty()) {
            mensajeError = "El campo 'Perfil' es obligatorio";
        } else {
            if (nombrePerfil.length() > 50) {
                mensajeError += "El campo 'Perfil' no debe contener más de 50 caracteres, has dígitado " + nombrePerfil.length() + " caracteres <br />";
            }
        }

        if (mensajeError.isEmpty()) {
            PerfilesBean perfil = new PerfilesBean();
            perfil.setId(idPerfil);
            perfil.setNombre(nombrePerfil);
            try {
                if (idPerfil != null) {
                    List<PerfilesBean> existente = perfilesDao.consultarPerfiles(null, nombrePerfil, true);
                    if (existente != null && !existente.isEmpty() && existente.get(0).getId().intValue() != idPerfil) {
                        mensajeError = "El nombre de perfil seleccionado ya está registrado en el sistema";
                    } else {
                        int actualizacion = perfilesDao.actualizarPerfil(perfil);
                        if (actualizacion > 0) {
                            mensajeExito = "El perfil ha sido guardado con éxito";
                        } else {
                            mensajeError = "El perfil no pudo ser guardado";
                        }
                    }
                } else {
                    List<PerfilesBean> existente = perfilesDao.consultarPerfiles(null, nombrePerfil, true);
                    if (existente != null && !existente.isEmpty()) {
                        mensajeError = "El nombre de perfil seleccionado ya está registrado en el sistema";
                    } else {
                        int insercion = perfilesDao.insertarPerfil(perfil);
                        if (insercion > 0) {
                            mensajeExito = "El perfil ha sido guardado con éxito";
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

    public Map<String, Object> guardarPermisos(Integer idPerfil, String[] permisos) {
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

        if (!listaPermisos.isEmpty()) {
            try {
                int insercion = perfilesDao.insertarPermisos(idPerfil, listaPermisos);
                if (insercion > 0) {
                    mensajeExito = "Los permisos del perfil han sido guardados con éxito";
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

    public Map<String, Object> eliminarPerfil(Integer idPerfil) {
        String mensajeExito = "";
        String mensajeError = "";
        if (idPerfil != null) {
            try {
                List<UsuariosBean> listaUsuarios = usuariosDao.consultarUsuarios(null, null, idPerfil, null, null, null);
                if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
                    mensajeError = "No se puede eliminar el perfil porque está asociado a usuarios del sistema";
                } else {
                    int eliminacion = perfilesDao.eliminarPerfil(idPerfil);
                    if (eliminacion > 0) {
                        mensajeExito = "El perfil fue eliminado con éxito";
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
