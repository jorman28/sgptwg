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
    private final PerfilesNegocio perfilesNegocio = new PerfilesNegocio();

    public JSONObject consultarUsuario(Integer idPersona) {
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

    public List<UsuariosBean> consultarUsuarios(Integer idPersona, String nombreUsuario, Integer perfil, String activo, String documento, String tipoDocumento) {
        List<UsuariosBean> listaUsuarios = new ArrayList<>();
        try {
            listaUsuarios = usuariosDao.consultarUsuarios(idPersona, nombreUsuario, perfil, activo, documento, tipoDocumento);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(TiposDocumentoNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaUsuarios;
    }

    public Map<String, Object> crearUsuario(Integer idPersona, String nombreUsuario, String clave, String clave2, Integer perfil, String activo, String documento, String tipoDocumento) {
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
                    if (usuario.getClave() == null || usuario.getClave().isEmpty()) {
                        List<UsuariosBean> usuarios = usuariosDao.consultarUsuarios(idPersona);
                        if (usuarios != null && !usuarios.isEmpty()) {
                            usuario.setClave(usuarios.get(0).getClave());
                        }
                    }
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
        if (!mensajeError.isEmpty()) {
            result.put("mensajeError", mensajeError);
        }
        if (!mensajeExito.isEmpty()) {
            result.put("mensajeExito", mensajeExito);
        }
        return result;
    }

    public Map<String, Object> eliminarUsuario(Integer idPersona) {
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
        if (!mensajeError.isEmpty()) {
            result.put("mensajeError", mensajeError);
        }
        if (!mensajeExito.isEmpty()) {
            result.put("mensajeExito", mensajeExito);
        }
        return result;
    }

    private String validarDatos(UsuariosBean usuario, String clave2) {
        String error = "";
        if (usuario.getDocumento() == null || usuario.getDocumento().isEmpty()) {
            error += "El campo 'Documento' es obligatorio \n";
        }

        if (usuario.getTipoDocumento() == null || usuario.getTipoDocumento().isEmpty() || usuario.getTipoDocumento().equals("0")) {
            error += "El campo 'Tipo de documento' es obligatorio \n";
        }

        if (usuario.getUsuario() == null || usuario.getUsuario().isEmpty()) {
            error += "El campo 'Usuario' es obligatorio \n";
        } else {
            try {
                List<UsuariosBean> usuarios = usuariosDao.consultarUsuarios(usuario.getUsuario());
                if (usuarios != null && !usuarios.isEmpty()) {
                    if (usuario.getIdPersona() != null) {
                        if (usuario.getIdPersona().intValue() != usuarios.get(0).getIdPersona().intValue()) {
                            error += "El usuario a ingresar no está disponible \n";
                        }
                    } else {
                        error += "El usuario a ingresar no está disponible \n";
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        UsuariosBean objetoUsuario = null;
        if (usuario.getDocumento() != null && !usuario.getDocumento().isEmpty()
                && usuario.getTipoDocumento() != null && !usuario.getTipoDocumento().equals("0")) {
            try {
                List<UsuariosBean> listaUsuarios = usuariosDao.consultarUsuarios(null, null, null, null, usuario.getDocumento(), usuario.getTipoDocumento());
                if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
                    objetoUsuario = listaUsuarios.get(0);
                }
            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                Logger.getLogger(PersonasNegocio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (usuario.getClave() == null || usuario.getClave().isEmpty()) {
            if (objetoUsuario == null) {
                error += "El campo 'Clave' es obligatorio \n";
            } else if (clave2 != null && !clave2.isEmpty()) {
                error += "El campo 'Clave' es obligatorio \n";
            }
        } else {
            if (clave2 == null || clave2.isEmpty()) {
                error += "El campo 'Confirmar clave' es obligatorio \n";
            } else if (!usuario.getClave().equals(clave2)) {
                error += "El valor en el campo 'Clave' y 'Confirmar clave' deben ser iguales \n";
            }
        }

        if (usuario.getPerfil() == null) {
            error += "El campo 'Perfil' es obligatorio \n";
        }

        if (usuario.getActivo() == null || usuario.getActivo().isEmpty()) {
            error += "El campo 'Estado' es obligatorio \n";
        }
        return error;
    }

    public Map<String, Object> validarInicioSession(String contexto, String usuario, String clave) {
        Map<String, Object> resultado = new HashMap<>();
        String mensajeError = "";
        String mensajeAlerta = "";
        if (usuario != null && !usuario.isEmpty()) {
            if (clave != null && !clave.isEmpty()) {
                try {
                    List<UsuariosBean> listaUsuarios = usuariosDao.consultarUsuarios(usuario);
                    if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
                        if (listaUsuarios.get(0).getClave().equals(clave)) {
                            if (listaUsuarios.get(0).getActivo() != null && listaUsuarios.get(0).getActivo().equals("T")) {
                                /* TODO: Agregar consulta de perfil y permisos */
                                Map<Integer, Map<String, Object>> permisos = perfilesNegocio.consultarPermisosPorPagina(listaUsuarios.get(0).getPerfil());
                                String menu = perfilesNegocio.construccionMenuNavegacion(permisos, contexto);
                                resultado.put("permisos", permisos);
                                resultado.put("menu", menu);
                            } else {
                                mensajeError = "El usuario con el que intenta ingresar está inactivo";
                            }
                        } else {
                            mensajeError = "La contraseña es incorrecta para el usuario ingresado";
                        }
                    } else {
                        mensajeError = "El usuario ingresado no está registrado en el sistema";
                    }
                } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                    Logger.getLogger(UsuariosNegocio.class.getName()).log(Level.SEVERE, null, ex);
                    mensajeError = "No hay conexión con la base de datos";
                }
            } else {
                mensajeAlerta = "Debe ingresar la clave";
            }
        } else {
            mensajeAlerta = "Debe ingresar el usuario";
        }
        if (!mensajeAlerta.isEmpty()) {
            resultado.put("mensajeAlerta", mensajeAlerta);
        }
        if (!mensajeError.isEmpty()) {
            resultado.put("mensajeError", mensajeError);
        }
        return resultado;
    }
}
