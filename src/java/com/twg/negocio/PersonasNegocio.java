package com.twg.negocio;

import com.twg.persistencia.beans.AccionesAuditadas;
import com.twg.persistencia.beans.ClasificacionAuditorias;
import com.twg.persistencia.beans.PersonasBean;
import com.twg.persistencia.beans.UsuariosBean;
import com.twg.persistencia.daos.PersonasDao;
import com.twg.persistencia.daos.UsuariosDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Clase encargada de realizar la conexión entre la vista y las operaciones en
 * base de datos, para la tabla de personas.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class PersonasNegocio {

    private final PersonasDao personasDao = new PersonasDao();
    private final UsuariosDao usuariosDao = new UsuariosDao();
    private final AuditoriasNegocio auditoria = new AuditoriasNegocio();

    /**
     * Método encargado de consultar las personas del sistema según los parámemtros
     * de búsqueda.
     * @param idPersona
     * @param documento
     * @param tipoDocumento
     * @param nombre
     * @param apellidos
     * @param correo
     * @param usuario
     * @param perfil
     * @param cargo
     * @param nombreCompleto
     * @param idProyecto
     * @param limite
     * @return Listado de personas registradas en el sistema según los parámetros
     * de búsqueda.
     */
    public List<PersonasBean> consultarPersonas(Integer idPersona, String documento, String tipoDocumento, String nombre, String apellidos, String correo, String usuario, String perfil, String cargo, String nombreCompleto, Integer idProyecto, String limite) {
        List<PersonasBean> listaPersonas = new ArrayList<>();
        try {
            listaPersonas = personasDao.consultarPersonas(idPersona, documento, tipoDocumento, nombre, apellidos, correo, usuario, perfil, cargo, nombreCompleto, false, idProyecto, limite);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(PersonasNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaPersonas;
    }

    /**
     * Método encargado de contar la cantidad total de registros que se
     * encuentran en base de datos con base en los filtros ingresados
     *
     * @param idPersona
     * @param documento
     * @param tipoDocumento
     * @param perfil
     * @param nombre
     * @param apellidos
     * @param usuario
     * @param correo
     * @param cargo
     * @param nombreCompleto
     * @return Cantidad de personas según los parámetros de búsqueda.
     */
    public int cantidadPersonas(Integer idPersona, String documento, String tipoDocumento, String nombre, String apellidos, String correo, String usuario, String perfil, String cargo, String nombreCompleto) {
        int cantidadPersonas = 0;
        try {
            cantidadPersonas = personasDao.cantidadPersonas(idPersona, documento, tipoDocumento, nombre, apellidos, correo, usuario, perfil, cargo, nombreCompleto);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(TiposDocumentoNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantidadPersonas;
    }

    /**
     * Método encargado de consultar una persona específica.
     * @param idPersona
     * @param documento
     * @param tipoDocumento
     * @return Objeto con todos los atributos de una persona.
     */
    public PersonasBean consultarPersona(Integer idPersona, String documento, String tipoDocumento) {
        PersonasBean persona = null;
        List<PersonasBean> listaPersonas = null;
        try {
            if (idPersona != null) {
                listaPersonas = personasDao.consultarPersonas(idPersona, null, null, null, null, null, null, null, null, null, false, null, null);
            } else if (documento != null && !documento.isEmpty() && tipoDocumento != null && !tipoDocumento.isEmpty()) {
                listaPersonas = personasDao.consultarPersonas(null, documento, tipoDocumento, null, null, null, null, null, null, null, false, null, null);
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(PersonasNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (listaPersonas != null && !listaPersonas.isEmpty()) {
            persona = listaPersonas.get(0);
        }
        return persona;
    }

    /**
     * Método encargado de guardar o actualizar una persona.
     * @param idPersona
     * @param documento
     * @param tipoDocumento
     * @param nombres
     * @param apellidos
     * @param telefono
     * @param celular
     * @param correo
     * @param direccion
     * @param cargo
     * @param nombreUsuario
     * @param perfil
     * @param clave
     * @param clave2
     * @param personaSesion
     * @return Cadena con un mensaje de error en caso de que el proceso falle.
     */
    public String guardarPersona(Integer idPersona, String documento, String tipoDocumento, String nombres, String apellidos,
            String telefono, String celular, String correo, String direccion, String cargo, String nombreUsuario, String perfil, String clave, String clave2, Integer personaSesion) {
        String error = "";
        
        try {
            PersonasBean persona = new PersonasBean();
            persona.setId(idPersona);
            persona.setDocumento(documento);
            persona.setTipoDocumento(tipoDocumento);
            persona.setNombres(nombres);
            persona.setApellidos(apellidos);
            persona.setDireccion(direccion);
            persona.setTelefono(telefono);
            persona.setCelular(celular);
            persona.setCorreo(correo);
            persona.setCargo(Integer.valueOf(cargo));
            int guardado = 0;
            if (idPersona != null) {
                List<PersonasBean> personaAnterior = personasDao.consultarPersonas(idPersona, null, null, null, null, null, null, null, null, null, true, null, null);
                guardado = personasDao.actualizarPersona(persona);
                List<PersonasBean> personaNueva = personasDao.consultarPersonas(null, persona.getDocumento(), persona.getTipoDocumento(), null, null, null, null, null, null, null, true, null, null);
                //AUDITORIA
                try {
                    String descripcioAudit = "Se actualizó la informaión de una persona. ANTES ("+
                            " Documento: "+personaAnterior.get(0).getDocumento()+
                            ", Tipo documento: "+personaAnterior.get(0).getNombreTipoDocumento()+
                            ", Nombres: "+personaAnterior.get(0).getNombre()+
                            ", Apellidos: "+personaAnterior.get(0).getApellidos()+
                            ", Teléfono: "+(personaAnterior.get(0).getTelefono()!=null&&!personaAnterior.get(0).getTelefono().equals("")?personaAnterior.get(0).getTelefono():"Ninguno")+
                            ", Celular: "+(personaAnterior.get(0).getCelular()!=null&&!personaAnterior.get(0).getCelular().equals("")?personaAnterior.get(0).getCelular():"Ninguno")+
                            ", Correo: "+(personaAnterior.get(0).getCorreo()!=null&&!personaAnterior.get(0).getCorreo().equals("")?personaAnterior.get(0).getCorreo():"Ninguno")+
                            ", Dirección: "+(personaAnterior.get(0).getDireccion()!=null&&!personaAnterior.get(0).getDireccion().equals("")?personaAnterior.get(0).getDireccion():"Ninguno")+
                            ", Cargo: "+(personaAnterior.get(0).getNombreCargo()!=null&&!personaAnterior.get(0).getNombreCargo().equals("")?personaAnterior.get(0).getNombreCargo():"Ninguno")+
                            ", Usuario: "+(personaAnterior.get(0).getUsuario()!=null&&!personaAnterior.get(0).getUsuario().equals("")?personaAnterior.get(0).getUsuario():"Ninguno")+
                            ", Perfil: "+(personaAnterior.get(0).getNombrePerfil()!=null&&!personaAnterior.get(0).getNombrePerfil().equals("")?personaAnterior.get(0).getNombrePerfil():"Ninguno")+")"+
                            ") DESPUÉS ( Documento: "+personaNueva.get(0).getDocumento()+
                            ", Tipo documento: "+personaNueva.get(0).getNombreTipoDocumento()+
                            ", Nombre completo: "+personaNueva.get(0).getNombre()+
                            ", Teléfono: "+(personaNueva.get(0).getTelefono()!=null&&!personaNueva.get(0).getTelefono().equals("")?personaNueva.get(0).getTelefono():"Ninguno")+
                            ", Celular: "+(personaNueva.get(0).getCelular()!=null&&!personaNueva.get(0).getCelular().equals("")?personaNueva.get(0).getCelular():"Ninguno")+
                            ", Correo: "+(personaNueva.get(0).getCorreo()!=null&&!personaNueva.get(0).getCorreo().equals("")?personaNueva.get(0).getCorreo():"Ninguno")+
                            ", Dirección: "+(personaNueva.get(0).getDireccion()!=null&&!personaNueva.get(0).getDireccion().equals("")?personaNueva.get(0).getDireccion():"Ninguno")+
                            ", Cargo: "+(personaNueva.get(0).getNombreCargo()!=null&&!personaNueva.get(0).getNombreCargo().equals("")?personaNueva.get(0).getNombreCargo():"Ninguno")+
                            ", Usuario: "+(personaNueva.get(0).getUsuario()!=null&&!personaNueva.get(0).getUsuario().equals("")?personaNueva.get(0).getUsuario():"Ninguno")+
                            ", Perfil: "+(personaNueva.get(0).getNombrePerfil()!=null&&!personaNueva.get(0).getNombrePerfil().equals("")?personaNueva.get(0).getNombrePerfil():"Ninguno")+")";
                    String guardarAuditoria = auditoria.guardarAuditoria(personaSesion, ClasificacionAuditorias.PERSONA.getNombre(), AccionesAuditadas.EDICION.getNombre(), descripcioAudit);
                } catch (Exception e) {
                    Logger.getLogger(PersonasNegocio.class.getName()).log(Level.SEVERE, null, e);
                }
            } else {
                try {
                    Integer person = personasDao.consultarIdPersona(documento, tipoDocumento);
                    if(person!=null){
                        error = "Ya existe una persona con documento: "+documento+" y tipo de documento: "+tipoDocumento; 
                        return error;
                    }
                } catch (Exception e) {
                }
                guardado = personasDao.insertarPersona(persona);
                List<PersonasBean> personaCreada = personasDao.consultarPersonas(null, persona.getDocumento(), persona.getTipoDocumento(), null, null, null, null, null, null, null, true, null, null);
                //AUDITORIA
                try {
                    String descripcioAudit = "Se creó una persona con la siguiente información ("+
                            " Documento: "+personaCreada.get(0).getDocumento()+
                            ", Tipo documento: "+personaCreada.get(0).getNombreTipoDocumento()+
                            ", Nombre completo: "+personaCreada.get(0).getNombre()+
                            ", Teléfono: "+(personaCreada.get(0).getTelefono()!=null&&!personaCreada.get(0).getTelefono().equals("")?personaCreada.get(0).getTelefono():"Ninguno")+
                            ", Celular: "+(personaCreada.get(0).getCelular()!=null&&!personaCreada.get(0).getCelular().equals("")?personaCreada.get(0).getCelular():"Ninguno")+
                            ", Correo: "+(personaCreada.get(0).getCorreo()!=null&&!personaCreada.get(0).getCorreo().equals("")?personaCreada.get(0).getCorreo():"Ninguno")+
                            ", Dirección: "+(personaCreada.get(0).getDireccion()!=null&&!personaCreada.get(0).getDireccion().equals("")?personaCreada.get(0).getDireccion():"Ninguno")+
                            ", Cargo: "+(personaCreada.get(0).getNombreCargo()!=null&&!personaCreada.get(0).getNombreCargo().equals("")?personaCreada.get(0).getNombreCargo():"Ninguno")+
                            ", Usuario: "+(personaCreada.get(0).getUsuario()!=null&&!personaCreada.get(0).getUsuario().equals("")?personaCreada.get(0).getUsuario():"Ninguno")+
                            ", Perfil: "+(personaCreada.get(0).getNombrePerfil()!=null&&!personaCreada.get(0).getNombrePerfil().equals("")?personaCreada.get(0).getNombrePerfil():"Ninguno")+")";
                    String guardarAuditoria = auditoria.guardarAuditoria(personaSesion, ClasificacionAuditorias.PERSONA.getNombre(), AccionesAuditadas.CREACION.getNombre(), descripcioAudit);
                } catch (Exception e) {
                    Logger.getLogger(PersonasNegocio.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            if (guardado == 0) {
                error = "La persona no pudo ser guardada";
                return error;
            }
            
            if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
                PersonasBean personaConsultada = consultarPersona(null, documento, tipoDocumento);
                if (personaConsultada != null) {
                    UsuariosBean usuario = new UsuariosBean();
                    List<UsuariosBean> listaUsuarios = usuariosDao.consultarUsuarios(personaConsultada.getId());
                    if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
                        usuario = listaUsuarios.get(0);
                    }
                    usuario.setUsuario(nombreUsuario);
                    usuario.setPerfil(Integer.valueOf(perfil));
                    if (clave != null) {
                        usuario.setClave(clave);
                    }
                    if (usuario.getIdPersona() != null) {
                        if (usuario.getClave() == null || usuario.getClave().isEmpty()) {
                            List<UsuariosBean> usuarios = usuariosDao.consultarUsuarios(usuario.getIdPersona());
                            if (usuarios != null && !usuarios.isEmpty()) {
                                usuario.setClave(usuarios.get(0).getClave());
                            }
                        }
                        usuario.setFechaEliminacion(null);
                        guardado = usuariosDao.actualizarUsuario(usuario);
                    } else {
                        usuario.setIdPersona(personaConsultada.getId());
                        usuario.setActivo("T");
                        guardado = usuariosDao.insertarUsuario(usuario);
                    }
                    if (guardado == 0) {
                        error = "El usuario no pudo ser guardado";
                        return error;
                    }
                }
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(PersonasNegocio.class.getName()).log(Level.SEVERE, null, ex);
            error = "Ocurrió un error guardando la persona";
        }
        return error;
    }

    /**
     * Método encargado de validar la estructura del correo electrónico de una
     * persona.
     * @param email
     * @return Verdadero o falso en caso de que cumpla o no la validación.
     */
    public boolean validarEmail(String email) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Método encargado de validar la información de una persona para poder
     * ser regsitrada en el sistema.
     * @param documento
     * @param tipoDocumento
     * @param nombres
     * @param apellidos
     * @param telefono
     * @param celular
     * @param correo
     * @param direccion
     * @param cargo
     * @param usuario
     * @param perfil
     * @param clave
     * @param clave2
     * @return Cadena con un mensaje de error en caso de no cumpla con alguna
     * validación.
     */
    public String validarDatos(String documento, String tipoDocumento, String nombres, String apellidos, String telefono, String celular,
            String correo, String direccion, String cargo, String usuario, String perfil, String clave, String clave2) {
        String error = "";

        //Obligatorios
        if (tipoDocumento == null || tipoDocumento.trim().isEmpty() || tipoDocumento.trim().equals("0")) {
            error += "El campo 'Tipo de documento' es obligatorio <br />";
        }
        if (documento == null || documento.trim().isEmpty()) {
            error += "El campo 'Documento' es obligatorio <br />";
        } else {
            if (documento.length() > 15) {
                error += "El campo 'Documento' no debe contener más de 15 caracteres, has dígitado " + documento.length() + " caracteres <br />";
            }
        }
        if (nombres == null || nombres.trim().isEmpty()) {
            error += "El campo 'Nombres' es obligatorio <br />";
        } else {
            if (nombres.length() > 50) {
                error += "El campo 'Nombres' no debe contener más de 50 caracteres, has dígitado " + nombres.length() + " caracteres <br />";
            }
        }
        if (apellidos == null || apellidos.trim().isEmpty()) {
            error += "El campo 'Apellidos' es obligatorio <br />";
        } else {
            if (apellidos.length() > 50) {
                error += "El campo 'Apellidos' no debe contener más de 50 caracteres, has dígitado " + apellidos.length() + " caracteres <br />";
            }
        }
        if (telefono == null || telefono.trim().isEmpty()) {
            error += "El campo 'Teléfono' es obligatorio <br />";
        } else {
            if (telefono.length() > 15) {
                error += "El campo 'Teléfono' no debe contener más de 15 caracteres, has dígitado " + telefono.length() + " caracteres <br />";
            }
        }
        if (direccion == null || direccion.trim().isEmpty()) {
            error += "El campo 'Dirección' es obligatorio <br />";
        } else {
            if (direccion.length() > 50) {
                error += "El campo 'Dirección' no debe contener más de 50 caracteres, has dígitado " + direccion.length() + " caracteres <br />";
            }
        }
        if (cargo == null || cargo.equals("0")) {
            error += "El campo 'Cargo' es obligatorio <br />";
        }

        //NO Obligatorios, pero se valida su longitud, caso celular y correo
        if (celular != null && !celular.trim().isEmpty()) {
            if (celular.length() > 15) {
                error += "El campo 'Celular' no debe contener más de 15 caracteres, has dígitado " + celular.length() + " caracteres <br />";
            }
        }

        if (correo != null && !correo.trim().isEmpty()) {
            if (correo.length() > 50) {
                error += "El campo 'Correo' no debe contener más de 50 caracteres, has dígitado " + correo.length() + " caracteres <br />";
            } else {
                if (!validarEmail(correo)) {
                    error += "El campo 'Correo' no cuenta con el formato válido correo@dominio <br />";
                }
            }
        }

        //Validar Campos de registro de Usuario en caso de que sean diligenciados
        if ((usuario != null && !usuario.isEmpty()) || (perfil != null && !perfil.equals("0")) || (clave != null && !clave.isEmpty()) || (clave2 != null && !clave2.isEmpty())) {
            if (usuario == null || usuario.isEmpty()) {
                error += "El campo 'Usuario' es obligatorio <br />";
            } else {
                if (usuario.length() > 15) {
                    error += "El campo 'Usuario' no debe contener más de 15 caracteres, has dígitado " + usuario.length() + " caracteres <br />";
                }
            }
            if (perfil == null) {
                error += "El campo 'Perfil' es obligatorio <br />";
            }
            UsuariosBean objetoUsuario = null;
            if (documento != null && !documento.isEmpty() && tipoDocumento != null && !tipoDocumento.equals("0")) {
                try {
                    List<UsuariosBean> listaUsuarios = usuariosDao.consultarUsuarios(null, null, null, null, documento, tipoDocumento, null);
                    if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
                        objetoUsuario = listaUsuarios.get(0);
                    }
                } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                    Logger.getLogger(PersonasNegocio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (clave == null || clave.isEmpty()) {
                if (objetoUsuario == null) {
                    error += "El campo 'Clave' es obligatorio <br />";
                } else if (clave2 != null && !clave2.isEmpty()) {
                    error += "El campo 'Clave' es obligatorio <br />";
                }
            } else {
                if (clave.length() > 15) {
                    error += "El campo 'Clave' no debe contener más de 15 caracteres <br />";
                } else {
                    if (clave2 == null || clave2.isEmpty()) {
                        error += "El campo 'Confirmar clave' es obligatorio <br />";
                    } else if (!clave.equals(clave2)) {
                        error += "El valor en el campo 'Clave' y 'Confirmar clave' deben ser iguales <br />";
                    }
                }
            }
        }
        return error;
    }

    /**
     * Método encargado de eliminar una persona específica.
     * @param idPersona
     * @param personaSesion
     * @return Cadena con un mensaje de error en caso de que el proceso falle.
     */
    public String eliminarPersona(Integer idPersona, Integer personaSesion) {
        String error = "";
        
        try {
            List<PersonasBean> personaEliminar = personasDao.consultarPersonas(idPersona, null, null, null, null, null, null, null, null, null, true, null, null);
            usuariosDao.eliminarUsuario(idPersona);
            int eliminacion = personasDao.eliminarPersona(idPersona);
            if (eliminacion == 0) {
                error = "La persona no pudo ser eliminada";
            }else{
                //AUDITORIA
                try {
                    String descripcioAudit = "Se eliminó la persona con documento "+personaEliminar.get(0).getDocumento()+" y tipo de documento "+personaEliminar.get(0).getTipoDocumento();
                    String guardarAuditoria = auditoria.guardarAuditoria(personaSesion, ClasificacionAuditorias.PERSONA.getNombre(), AccionesAuditadas.ELIMINACION.getNombre(), descripcioAudit);
                } catch (Exception e) {
                    Logger.getLogger(PersonasNegocio.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(PersonasNegocio.class.getName()).log(Level.SEVERE, null, ex);
            error = "Ocurrió un error eliminado la persona";
        }
        return error;
    }

    /**
     * Método encargado de consultar las personas asociadas con los datos
     * digitados en los autocompletar de personas del sistema
     *
     * @param busqueda
     * @return Listado de personas según los parámetros de búsqueda.
     */
    public JSONArray completarPersonas(String busqueda) {
        return completarPersonas(busqueda, null);
    }

    /**
     * Método encargado de consultar las personas asociadas con los datos
     * digitados en los autocompletar de personas del sistema que necesitan
     * estar asociados a un proyecto
     *
     * @param busqueda
     * @param idProyecto
     * @return Listado de personas según los parámetros de búsqueda.
     */
    public JSONArray completarPersonas(String busqueda, Integer idProyecto) {
        JSONArray array = new JSONArray();
        List<PersonasBean> listaPersonas = consultarPersonas(null, null, null, null, null, null, null, null, null, busqueda, idProyecto, null);
        if (listaPersonas != null && !listaPersonas.isEmpty()) {
            for (PersonasBean persona : listaPersonas) {
                JSONObject object = new JSONObject();
                object.put("id", persona.getId());
                object.put("nombre", persona.getTipoDocumento() + persona.getDocumento() + " " + persona.getNombres() + " " + persona.getApellidos());
                object.put("cargo", persona.getNombreCargo().equalsIgnoreCase("Cliente") ? "Cliente" : persona.getNombreCargo());
                array.add(object);
            }
        }
        return array;
    }
}
