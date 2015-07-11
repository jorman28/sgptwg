package com.twg.controladores;

import com.twg.persistencia.beans.PerfilesBean;
import com.twg.persistencia.beans.TiposDocumentosBean;
import com.twg.persistencia.beans.UsuariosBean;
import com.twg.persistencia.daos.PerfilesDao;
import com.twg.persistencia.daos.PersonasDao;
import com.twg.persistencia.daos.TiposDocumentosDao;
import com.twg.persistencia.daos.UsuariosDao;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pipe
 */
public class UsuariosController extends HttpServlet {

    private final TiposDocumentosDao tiposDocumentosDao = new TiposDocumentosDao();
    private final PerfilesDao perfilesDao = new PerfilesDao();
    private final UsuariosDao usuariosDao = new UsuariosDao();
    private final PersonasDao personasDao = new PersonasDao();
    private String mensajeAlerta;
    private String mensajeExito;
    private String mensajeError;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        mensajeAlerta = "";
        mensajeExito = "";
        mensajeError = "";

        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "";
        }

        String idPersonaStr = request.getParameter("idPersona");
        String documento = request.getParameter("documento");
        String tipoDocumento = request.getParameter("tipoDocumento");
        String nombreUsuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");
        String clave2 = request.getParameter("clave2");
        String perfilStr = request.getParameter("perfil");
        String activo = request.getParameter("activo");

        Integer idPersona = null;
        try {
            idPersona = Integer.valueOf(idPersonaStr);
        } catch (NumberFormatException e) {
        }

        Integer perfil = null;
        try {
            perfil = Integer.valueOf(perfilStr);
        } catch (NumberFormatException e) {
        }

        List<UsuariosBean> listaUsuarios = null;
        try {
            switch (accion) {
                case "consultar":
                    listaUsuarios = usuariosDao.consultarUsuarios(idPersona, nombreUsuario, perfil, activo, documento, tipoDocumento);
                    UsuariosBean usuario = new UsuariosBean();
                    usuario.setDocumento(documento);
                    usuario.setTipoDocumento(tipoDocumento);
                    usuario.setUsuario(nombreUsuario);
                    usuario.setClave(clave);
                    usuario.setPerfil(perfil);
                    usuario.setActivo(activo);
                    enviarDatos(request, usuario);
                    break;
                case "editar":
                    usuario = new UsuariosBean();
                    if (idPersona != null) {
                        List<UsuariosBean> usuarios = usuariosDao.consultarUsuarios(idPersona);
                        if (usuarios != null && !usuarios.isEmpty()) {
                            usuario = usuarios.get(0);
                        }
                    }
                    enviarDatos(request, usuario);
                    break;
                case "guardar":
                    usuario = new UsuariosBean();
                    usuario.setUsuario(nombreUsuario);
                    usuario.setClave(clave);
                    usuario.setPerfil(perfil);
                    usuario.setActivo(activo);
                    usuario.setDocumento(documento);
                    usuario.setTipoDocumento(tipoDocumento);
                    usuario.setIdPersona(idPersona);

                    mensajeError = validarDatos(usuario, clave2);
                    if (mensajeError.isEmpty()) {
                        if (idPersona != null) {
                            int actualizacion = usuariosDao.actualizarUsuario(usuario);
                            if (actualizacion > 0) {
                                mensajeExito = "El usuario ha sido guardado con éxito";
                                usuario = new UsuariosBean();
                            } else {
                                mensajeError = "El usuario no pudo ser guardado";
                            }
                        } else {
                            idPersona = personasDao.consultarIdPersona(documento, tipoDocumento);
                            if (idPersona != null) {
                                List<UsuariosBean> existente = usuariosDao.consultarUsuarios(idPersona);
                                if (existente != null && !existente.isEmpty()) {
                                    mensajeError = "La persona seleccionada ya tiene un usuario asignado";
                                } else {
                                    usuario.setIdPersona(idPersona);
                                    int insercion = usuariosDao.insertarUsuario(usuario);
                                    if (insercion > 0) {
                                        mensajeExito = "El usuario ha sido guardado con éxito";
                                        usuario = new UsuariosBean();
                                    } else {
                                        mensajeError = "El usuario no pudo ser guardado";
                                    }
                                }

                            } else {
                                mensajeError = "La persona seleccionada no está registrada en el sistema";
                            }
                        }
                    }

                    enviarDatos(request, usuario);
                    break;
                case "eliminar":
                    if (idPersona != null) {
                        int eliminacion = usuariosDao.eliminarUsuario(idPersona);
                        if (eliminacion > 0) {
                            mensajeExito = "El usuario fue eliminado con éxito";
                        } else {
                            mensajeError = "El usuario no pudo ser eliminado";
                        }
                    } else {
                        mensajeError = "El usuario no pudo ser eliminado";
                    }
                    enviarDatos(request, new UsuariosBean());
                    break;
                default:
                    enviarDatos(request, new UsuariosBean());
                    break;
            }
            if (listaUsuarios == null) {
                listaUsuarios = usuariosDao.consultarUsuarios();
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
            mensajeError = "Ocurrió un error procesando los datos. Revise el log de aplicación.";
        }

        request.setAttribute("mensajeAlerta", mensajeAlerta);
        request.setAttribute("mensajeExito", mensajeExito);
        request.setAttribute("mensajeError", mensajeError);
        request.setAttribute("tiposDocumentos", obtenerTiposDocumentos());
        request.setAttribute("usuarios", listaUsuarios);
        request.setAttribute("perfiles", obtenerPerfiles());
        request.getRequestDispatcher("jsp/usuarios.jsp").forward(request, response);
    }

    private void enviarDatos(HttpServletRequest request, UsuariosBean usuario) {
        request.setAttribute("idPersona", usuario.getIdPersona());
        request.setAttribute("tipoDocumento", usuario.getTipoDocumento());
        request.setAttribute("documento", usuario.getDocumento());
        request.setAttribute("usuario", usuario.getUsuario());
        request.setAttribute("clave", "");
        request.setAttribute("perfil", usuario.getPerfil());
        request.setAttribute("activo", usuario.getActivo());
    }

    private String validarDatos(UsuariosBean usuario, String clave2) {
        String error = "";
        if (usuario.getDocumento() == null || usuario.getDocumento().isEmpty()) {
            error += "El campo 'Documento' es obligatorio <br/>";
        }

        if (usuario.getTipoDocumento() == null || usuario.getTipoDocumento().isEmpty() || usuario.getTipoDocumento().equals("0")) {
            error += "El campo 'Tipo de documento' es obligatorio <br/>";
        }

        if (usuario.getUsuario() == null || usuario.getUsuario().isEmpty()) {
            error += "El campo 'Usuario' es obligatorio <br/>";
        } else {
            try {
                List<UsuariosBean> usuarios = usuariosDao.consultarUsuarios(usuario.getUsuario());
                if (usuarios != null && !usuarios.isEmpty()) {
                    if (usuario.getIdPersona() != null) {
                        if (usuario.getIdPersona().intValue() != usuarios.get(0).getIdPersona().intValue()) {
                            error += "El usuario a ingresar no está disponible <br/>";
                        }
                    } else {
                        error += "El usuario a ingresar no está disponible <br/>";
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (usuario.getClave() == null || usuario.getClave().isEmpty()) {
            error += "El campo 'Clave' es obligatorio <br/>";
        } else {
            if (clave2 == null || clave2.isEmpty()) {
                error += "El campo 'Confirmar clave' es obligatorio <br/>";
            } else if (!usuario.getClave().equals(clave2)) {
                error += "El valor en el campo 'Clave' y 'Confirmar clave' deben ser iguales <br/>";
            }
        }

        if (usuario.getPerfil() == null) {
            error += "El campo 'Perfil' es obligatorio <br/>";
        }

        if (usuario.getActivo() == null || usuario.getActivo().isEmpty()) {
            error += "El campo 'Estado' es obligatorio <br/>";
        }
        return error;
    }

    private List<TiposDocumentosBean> obtenerTiposDocumentos() {
        List<TiposDocumentosBean> tiposDocumentos = new ArrayList<>();
        try {
            tiposDocumentos = tiposDocumentosDao.consultarTiposDocumentos();
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tiposDocumentos;
    }

    private List<PerfilesBean> obtenerPerfiles() {
        List<PerfilesBean> perfiles = new ArrayList<>();
        try {
            perfiles = perfilesDao.consultarPerfiles();
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return perfiles;
    }

    @Override
    protected void doGet(HttpServletRequest reqeust, HttpServletResponse response) throws ServletException, IOException {
        processRequest(reqeust, response);
    }

    @Override
    protected void doPost(HttpServletRequest reqeust, HttpServletResponse response) throws ServletException, IOException {
        processRequest(reqeust, response);
    }

    protected void init(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
