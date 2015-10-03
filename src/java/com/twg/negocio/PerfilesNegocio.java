package com.twg.negocio;

import com.twg.persistencia.beans.PerfilesBean;
import com.twg.persistencia.daos.PerfilesDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PerfilesNegocio {
    private final PerfilesDao perfilesDao = new PerfilesDao();
    
    public List<PerfilesBean> consultarPerfiles(){
        List<PerfilesBean> perfiles = new ArrayList<>();
        try {
            perfiles = perfilesDao.consultarPerfiles();
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(PerfilesNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return perfiles;
    }
    
    public Map<Integer, Map<String, Object>> consultarPermisosPorPagina(Integer idPerfil){
        Map<Integer, Map<String, Object>> permisos = new TreeMap<>();
        try {
            permisos = perfilesDao.consultarPermisosPorPagina(idPerfil);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(PerfilesNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return permisos;
    }
    
    public String construccionMenuNavegacion(Map<Integer, Map<String, Object>> permisos, String context){
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
            if(!paginasAgregadas.contains(entry.getKey())){
                Map<String, Object> map = entry.getValue();
                List<Integer> listaHijos = (List<Integer>) map.get("hijos");
                if(listaHijos != null && !listaHijos.isEmpty()){
                    menu.append("       <li class=\"dropdown\">\n");
                    menu.append("           <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">").append(map.get("nombre")).append(" <span class=\"caret\"></span></a>\n");
                    menu.append("           <ul class=\"dropdown-menu\">\n");
                    for (Integer h : listaHijos) {
                        Map<String, Object> hijo = permisos.get(h);
                        if(hijo.get("url") != null){
                            menu.append("       <li><a href=\"").append(context).append(hijo.get("url")).append("\">").append(hijo.get("nombre")).append("</a></li>\n");
                        } else {
                            menu.append("       <li><a href=\"#\">").append(hijo.get("nombre")).append("</a></li>\n");
                        }
                        paginasAgregadas.add(h);
                    }
                    menu.append("           </ul>\n");
                    menu.append("       </li>\n");
                } else {
                    if(map.get("url") != null){
                        menu.append("       <li><a href=\"").append(context).append(map.get("url")).append("\">").append(map.get("nombre")).append("</a></li>\n");
                    } else {
                        menu.append("       <li><a href=\"#\">").append(map.get("nombre")).append("</a></li>\n");
                    }
                }
                paginasAgregadas.add(entry.getKey());
            }
        }
        menu.append("               <li><a href=\"").append(context).append("/InicioSesionController\">Cerrar sesión</a></li>\n");
        menu.append("           </ul>\n");
        menu.append("       </div>\n");
        menu.append("   </div>\n");
        menu.append("</nav>");
        
        return menu.toString();
    }
}
