package com.twg.utilidades;

import java.io.IOException;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Pipe
 */
public class FiltroSesion implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession sesion = req.getSession(false);
        String context = req.getContextPath();
        String uri = req.getRequestURI();
        if ((uri.endsWith("Controller") && !uri.equals(context + "/CerrarSesionController")) || uri.equals(context + "/")) {
            System.out.println("Se ingresa al filtro con URI: " + uri + ". Contexto: " + context);
            if (uri.equals(context + "/") || uri.equals(context + "/InicioSesionController")) {
                if (sesion != null && sesion.getAttribute("permisos") != null) {
                    /* "Se redirige a la primera página que tenga asociada el perfil"); */
                    Map<Integer, Map<String, Object>> permisos = (Map<Integer, Map<String, Object>>) sesion.getAttribute("permisos");
                    String url = "/";
                    for (Map<String, Object> pagina : permisos.values()) {
                        if (pagina.get("url") != null && !pagina.get("url").toString().isEmpty()) {
                            url = pagina.get("url").toString();
                        }
                    }
                    request.getRequestDispatcher(url).forward(request, response);
                    return;
                }
            } else {
                if (sesion == null || sesion.getAttribute("permisos") == null) {
                    /* "No hay sesión y se debe mostrar formulario de autenticación" */
                    request.getRequestDispatcher("/jsp/inicioSesion.jsp").forward(request, response);
                    return;
                } else {
                    Map<Integer, Map<String, Object>> permisos = (Map<Integer, Map<String, Object>>) sesion.getAttribute("permisos");
                    boolean accesoDenegado = true;
                    for (Map<String, Object> pagina : permisos.values()) {
                        if (pagina.get("url") != null && (context + pagina.get("url").toString()).equals(uri)) {
                            accesoDenegado = false;
                            break;
                        }
                    }
                    if (accesoDenegado) {
                        /* "Se está intentando acceder a una página a la cual no se tiene permisos"); */
                        request.getRequestDispatcher("/jsp/general/autenticacion.jsp").forward(request, response);
                        return;
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("Se destruye el filtro de sesión");
    }

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("Se inicia el filtro");
    }
}
