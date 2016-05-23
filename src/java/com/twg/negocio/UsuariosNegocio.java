package com.twg.negocio;

import com.twg.controladores.UsuariosController;
import com.twg.persistencia.beans.AccionesAuditadas;
import com.twg.persistencia.beans.ClasificacionAuditorias;
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

/**
 * Esta clase permite la comunicación entre el controlador de usuarios y la base
 * de datos.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class UsuariosNegocio {

    private final UsuariosDao usuariosDao = new UsuariosDao();
    private final PersonasDao personasDao = new PersonasDao();
    private final AuditoriasNegocio auditoria = new AuditoriasNegocio();
    private final PerfilesNegocio perfilesNegocio = new PerfilesNegocio();

    /**
     * Consulta de los datos relacionados a un usuario por medio del id de la
     * persona.
     *
     * @param idPersona
     * @return Objeto con todos los atributos de un usuario específico.
     */
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

    /**
     * Método encargado de contar la cantidad total de registros que se
     * encuentran en base de datos con base en los filtros ingresados
     *
     * @param idPersona
     * @param nombreUsuario
     * @param perfil
     * @param activo
     * @param documento
     * @param tipoDocumento
     * @return Cantidad de registros de usuarios, según los parámetros de búsqueda.
     */
    public int cantidadUsuarios(Integer idPersona, String nombreUsuario, Integer perfil, String activo, String documento, String tipoDocumento) {
        int cantidadUsuarios = 0;
        try {
            cantidadUsuarios = usuariosDao.cantidadUsuarios(idPersona, nombreUsuario, perfil, activo, documento, tipoDocumento);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(TiposDocumentoNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantidadUsuarios;
    }

    /**
     * Método encargado de consultar la lista de usuarios que coinciden con los
     * filtros ingresados y la página seleccionada
     *
     * @param idPersona
     * @param nombreUsuario
     * @param perfil
     * @param activo
     * @param documento
     * @param tipoDocumento
     * @param limite
     * @return Listado con todos los usuarios consultados, dependiendo de los
     * parámetros de búsqueda.
     */
    public List<UsuariosBean> consultarUsuarios(Integer idPersona, String nombreUsuario, Integer perfil, String activo, String documento, String tipoDocumento, String limite) {
        List<UsuariosBean> listaUsuarios = new ArrayList<>();
        try {
            listaUsuarios = usuariosDao.consultarUsuarios(idPersona, nombreUsuario, perfil, activo, documento, tipoDocumento, limite);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(TiposDocumentoNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaUsuarios;
    }

    /**
     * Método encargado de insertar un registro de usuario en la base de datos
     *
     * @param idPersona
     * @param nombreUsuario
     * @param clave
     * @param clave2
     * @param perfil
     * @param activo
     * @param documento
     * @param tipoDocumento
     * @param personaSesionStr
     * @return Mapa con un mensaje de error o éxito dependiendo del resultado del 
     * proceso.
     */
    public Map<String, Object> crearUsuario(Integer idPersona, String nombreUsuario, String clave, String clave2, Integer perfil, String activo, String documento, String tipoDocumento, Integer personaSesion) {
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
                List<UsuariosBean> usuarios = new ArrayList<>();
                if (idPersona != null) {
                    if (usuario.getClave() == null || usuario.getClave().isEmpty()) {
                        usuarios = usuariosDao.consultarUsuarios(idPersona);
                        if (usuarios != null && !usuarios.isEmpty()) {
                            usuario.setClave(usuarios.get(0).getClave());
                        }
                    }
                    usuario.setFechaEliminacion(null);
                    int actualizacion = usuariosDao.actualizarUsuario(usuario);
                    if (actualizacion > 0) {
                        mensajeExito = "El usuario ha sido guardado con éxito";
                        //AUDITORIA
                        try {
                            List<UsuariosBean> usuarioActual = usuariosDao.consultarUsuarios(idPersona);
                            String descripcioAudit = "Se actualizó la información del usuario asociado a la persona con documento "+
                                    usuarios.get(0).getDocumento()+
                                    ": Antes (Nombre usuario: "+usuarios.get(0).getUsuario()+
                                    ", Perfil: "+usuarios.get(0).getDescripcionPerfil()+
                                    ", Estado: "+(usuarios.get(0).getActivo().equals("T")?"Activo":"Inactivo")+
                                    ") Después (Nombre usuario: "+usuario.getUsuario()+
                                    ", Perfil: "+usuarioActual.get(0).getDescripcionPerfil()+
                                    ", Estado: "+(usuario.getActivo().equals("T")?"Activo":"Inactivo")+")";
                            String guardarAuditoria = auditoria.guardarAuditoria(personaSesion, ClasificacionAuditorias.USUARIO.getNombre(), AccionesAuditadas.EDICION.getNombre(), descripcioAudit);
                        } catch (Exception e) {
                            Logger.getLogger(UsuariosNegocio.class.getName()).log(Level.SEVERE, null, e);
                        }
                    } else {
                        mensajeError = "El usuario no pudo ser guardado";
                    }
                } else {
                    idPersona = personasDao.consultarIdPersona(documento, tipoDocumento);
                    if (idPersona != null) {
                        usuario.setIdPersona(idPersona);
                        List<UsuariosBean> existente = usuariosDao.consultarUsuarios(idPersona);
                        if (existente != null && !existente.isEmpty()) {
                            if(existente.get(0).getFechaEliminacion() != null){
                                usuario.setFechaEliminacion(null);
                                int actualizarUsuario = usuariosDao.actualizarUsuario(usuario);
                                if (actualizarUsuario > 0) {
                                    mensajeExito = "El usuario ha sido guardado con éxito";
                                    //AUDITORIA
                                    try {
                                        usuarios = usuariosDao.consultarUsuarios(idPersona);
                                        String descripcioAudit = "Se creó un nuevo usuario para la persona con documento "+documento+", actualizando "
                                                + "la informacion del usuario anterior ("+existente.get(0).getUsuario()+") que se encontraba eliminado. Nuevo usuario: ("+usuario.getUsuario()+", "+usuarios.get(0).getDescripcionPerfil()+", "
                                                +(usuario.getActivo().equals("T")?"Activo":"Inactivo")+")";
                                        String guardarAuditoria = auditoria.guardarAuditoria(personaSesion, ClasificacionAuditorias.USUARIO.getNombre(), AccionesAuditadas.CREACION.getNombre(), descripcioAudit);
                                    } catch (Exception e) {
                                        Logger.getLogger(UsuariosNegocio.class.getName()).log(Level.SEVERE, null, e);
                                    }
                                } else {
                                    mensajeError = "El usuario no pudo ser guardado";
                                }
                            }else{
                                mensajeError = "Ya existe un usuario registrado para ese documento";
                            }
                        } else {
                            int insercion = usuariosDao.insertarUsuario(usuario);
                            if (insercion > 0) {
                                mensajeExito = "El usuario ha sido guardado con éxito";
                                //AUDITORIA
                                try {
                                    usuarios = usuariosDao.consultarUsuarios(idPersona);
                                    String descripcioAudit = "Se creó un nuevo usuario para la persona con documento "+documento+" ("+usuario.getUsuario()+", "+usuarios.get(0).getDescripcionPerfil()+", "+(usuario.getActivo().equals("T")?"Activo":"Inactivo")+")";
                                    String guardarAuditoria = auditoria.guardarAuditoria(personaSesion, ClasificacionAuditorias.USUARIO.getNombre(), AccionesAuditadas.CREACION.getNombre(), descripcioAudit);
                                } catch (Exception e) {
                                    Logger.getLogger(UsuariosNegocio.class.getName()).log(Level.SEVERE, null, e);
                                }
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
        result.put("documento", usuario.getDocumento());
        result.put("tipoDocumento", usuario.getTipoDocumento());
        return result;
    }

    /**
     * Método encargado de marcar un usuario como eliminado seteando la fecha
     * actual como fecha de eliminación
     *
     * @param idPersona
     * @return Mapa con un mensaje de error o de éxito dependiendo del resultado
     * del proceso.
     */
    public Map<String, Object> eliminarUsuario(Integer idPersona, Integer personaSesion) {
        String mensajeExito = "";
        String mensajeError = "";
        if (idPersona != null) {
            try {
                List<UsuariosBean> usuarios = usuariosDao.consultarUsuarios(idPersona);
                int eliminacion = usuariosDao.eliminarUsuario(idPersona);
                if (eliminacion > 0) {
                    mensajeExito = "El usuario fue eliminado con éxito";
                    //AUDITORIA
                    try {
                        String descripcioAudit = "Se eliminó el usuario "+usuarios.get(0).getUsuario()+" asociado a la persona con documento "+usuarios.get(0).getDocumento();
                        String guardarAuditoria = auditoria.guardarAuditoria(personaSesion, ClasificacionAuditorias.USUARIO.getNombre(), AccionesAuditadas.ELIMINACION.getNombre(), descripcioAudit);
                    } catch (Exception e) {
                        Logger.getLogger(UsuariosNegocio.class.getName()).log(Level.SEVERE, null, e);
                    }
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

    /**
     * Método encargado de validar los datos de usuario que se insertarán en
     * base de datos antes de realizar la inserción o modificación de registros
     *
     * @param usuario
     * @param clave2
     * @return Cadena con un mensaje de error en caso de que el proceso falle.
     */
    private String validarDatos(UsuariosBean usuario, String clave2) {
        String error = "";

        if (usuario.getIdPersona() == null && (usuario.getTipoDocumento() == null || usuario.getTipoDocumento().isEmpty() || usuario.getTipoDocumento().equals("0"))) {
            error += "El campo 'Tipo de documento' es obligatorio <br />";
        }

        if (usuario.getIdPersona() == null && (usuario.getDocumento() == null || usuario.getDocumento().isEmpty())) {
            error += "El campo 'Documento' es obligatorio <br />";
        } else {
            if (usuario.getDocumento()!=null && usuario.getDocumento().length() > 15) {
                error += "El campo 'Documento' no debe contener más de 15 caracteres, has dígitado " + usuario.getDocumento().length() + " caracteres <br />";
            }
        }

        if (usuario.getUsuario() == null || usuario.getUsuario().isEmpty()) {
            error += "El campo 'Usuario' es obligatorio <br />";
        } else {
            if (usuario.getUsuario().length() > 15) {
                error += "El campo 'Usuario' no debe contener más de 15 caracteres, has dígitado " + usuario.getUsuario().length() + " caracteres <br />";
            } else {
                try {
                    List<UsuariosBean> usuarios = usuariosDao.consultarUsuarios(usuario.getUsuario());
                    if (usuarios != null && !usuarios.isEmpty()) {
                        if (usuario.getIdPersona() != null) {
                            if (usuario.getIdPersona().intValue() != usuarios.get(0).getIdPersona().intValue()) {
                                error += "El usuario a ingresar no está disponible <br />";
                            }
                        } else {
                            error += "El usuario a ingresar no está disponible <br />";
                        }
                    }
                } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                    Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        if (usuario.getPerfil() == null) {
            error += "El campo 'Perfil' es obligatorio <br />";
        }

        UsuariosBean objetoUsuario = null;
        if (usuario.getIdPersona() == null && usuario.getDocumento() != null && !usuario.getDocumento().isEmpty()
                && usuario.getTipoDocumento() != null && !usuario.getTipoDocumento().equals("0")) {
            try {
                List<UsuariosBean> listaUsuarios = usuariosDao.consultarUsuarios(null, null, null, null, usuario.getDocumento(), usuario.getTipoDocumento(), null);
                if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
                    objetoUsuario = listaUsuarios.get(0);
                }
            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                Logger.getLogger(PersonasNegocio.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (usuario.getIdPersona() != null) {
            try {
                List<UsuariosBean> listaUsuarios = usuariosDao.consultarUsuarios(usuario.getIdPersona());
                if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
                    objetoUsuario = listaUsuarios.get(0);
                    usuario.setDocumento(objetoUsuario.getDocumento());
                    usuario.setTipoDocumento(objetoUsuario.getTipoDocumento());
                }
            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                Logger.getLogger(PersonasNegocio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (usuario.getClave() == null || usuario.getClave().isEmpty()) {
            if (objetoUsuario == null) {
                error += "El campo 'Clave' es obligatorio <br />";
            } else if (clave2 != null && !clave2.isEmpty()) {
                error += "El campo 'Clave' es obligatorio <br />";
            }
        } else {
            if (usuario.getClave().length() > 15) {
                error += "El campo 'Clave' no debe contener más de 15 caracteres <br />";
            } else {
                if (clave2 == null || clave2.isEmpty()) {
                    error += "El campo 'Confirmar clave' es obligatorio <br />";
                } else {
                    if (!usuario.getClave().equals(clave2)) {
                        error += "El valor en el campo 'Clave' y 'Confirmar clave' deben ser iguales <br />";
                    }
                }
            }
        }

        if (usuario.getActivo() == null || usuario.getActivo().isEmpty()) {
            error += "El campo 'Estado' es obligatorio <br />";
        }
        return error;
    }

    /**
     * Método encargado de realizar el inicio de sesión al sistema consultando
     * las páginas disponibles para determinado rol y la información de la
     * persona relacionada al usuario ingresado
     *
     * @param contexto
     * @param usuario
     * @param clave
     * @return Mapa con un mensaje de error o éxito, dependiendo del resultado
     * del proceso.
     */
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
                                resultado.put("personaSesion", listaUsuarios.get(0).getIdPersona());
                                resultado.put("usuarioSesion", listaUsuarios.get(0).getUsuario());
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
