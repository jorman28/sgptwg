package com.twg.negocio;

import com.twg.controladores.UsuariosController;
import com.twg.persistencia.beans.UsuariosBean;
import com.twg.persistencia.daos.PersonasDao;
import com.twg.persistencia.daos.UsuariosDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

public class UsuariosNegocio {
    private final UsuariosDao usuariosDao = new UsuariosDao();
    private final PersonasDao personasDao = new PersonasDao();
    
    public JSONObject consultarUsuario(Integer idPersona){
        JSONObject jsonUsuario = new JSONObject();
        if (idPersona != null) {
            try {
                List<UsuariosBean> usuarios = usuariosDao.consultarUsuarios(idPersona);
                if (usuarios != null && !usuarios.isEmpty()) {
                    UsuariosBean usuario = usuarios.get(0);
                    jsonUsuario.put("idPersona", usuario.getIdPersona());
                    jsonUsuario.put("tipoDocumento", usuario.getTipoDocumento());
                    jsonUsuario.put("documento", usuario.getDocumento());
                    jsonUsuario.put("usuario", usuario.getUsuario());
                    jsonUsuario.put("activo", usuario.getActivo());
                    jsonUsuario.put("perfil", usuario.getPerfil());
                }
            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                Logger.getLogger(UsuariosNegocio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return jsonUsuario;
    }
    
    public List<UsuariosBean> consultarUsuarios(Integer idPersona, String nombreUsuario, Integer perfil, String activo, String documento, String tipoDocumento){
        List<UsuariosBean> listaUsuarios = new ArrayList<>();
        try {
            listaUsuarios = usuariosDao.consultarUsuarios(idPersona, nombreUsuario, perfil, activo, documento, tipoDocumento);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(TiposDocumentoNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaUsuarios;
    }
    
    public Map<String, Object> crearUsuario(Integer idPersona, String nombreUsuario, String clave, String clave2, Integer perfil, String activo, String documento, String tipoDocumento){
        UsuariosBean usuario = new UsuariosBean();
        usuario.setUsuario(nombreUsuario);
        usuario.setClave(clave);
        usuario.setPerfil(perfil);
        usuario.setActivo(activo);
        usuario.setDocumento(documento);
        usuario.setTipoDocumento(tipoDocumento);
        usuario.setIdPersona(idPersona);
        
        String mensajeExito = "";
        String mensajeError = validarDatos(usuario, clave2);
        if (mensajeError.isEmpty()) {
            try {
                if (idPersona != null) {
                    int actualizacion = usuariosDao.actualizarUsuario(usuario);
                    if (actualizacion > 0) {
                        mensajeExito = "El usuario ha sido guardado con éxito";
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
                            } else {
                                mensajeError = "El usuario no pudo ser guardado";
                            }
                        }

                    } else {
                        mensajeError = "La persona seleccionada no está registrada en el sistema";
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                Logger.getLogger(UsuariosNegocio.class.getName()).log(Level.SEVERE, null, ex);
                mensajeError = "Ocurrió un error insertando el usuario. Revise el log de aplicación.";
            }
        }
        Map<String, Object> result = new HashMap<>();
        if(!mensajeError.isEmpty()){
            result.put("mensajeError", mensajeError);
        }
        if(!mensajeExito.isEmpty()){
            result.put("mensajeExito", mensajeExito);
        }
        return result;
    }
    
    public Map<String, Object> eliminarUsuario(Integer idPersona){
        String mensajeExito = "";
        String mensajeError = "";
        if (idPersona != null) {
            try {
                int eliminacion = usuariosDao.eliminarUsuario(idPersona);
                if (eliminacion > 0) {
                    mensajeExito = "El usuario fue eliminado con éxito";
                } else {
                    mensajeError = "El usuario no pudo ser eliminado";
                }
            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                Logger.getLogger(UsuariosNegocio.class.getName()).log(Level.SEVERE, null, ex);
                mensajeError = "Ocurrió un error eliminando el usuario. Revise el log de aplicación.";
            }
        } else {
            mensajeError = "El usuario no pudo ser eliminado";
        }
        Map<String, Object> result = new HashMap<>();
        if(!mensajeError.isEmpty()){
            result.put("mensajeError", mensajeError);
        }
        if(!mensajeExito.isEmpty()){
            result.put("mensajeExito", mensajeExito);
        }
        return result;
    }
    
    public String validarDatos(UsuariosBean usuario, String clave2) {
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
}
