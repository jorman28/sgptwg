package com.twg.negocio;

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
 *
 * @author Pipe
 */
public class PersonasNegocio {

    private final PersonasDao personasDao = new PersonasDao();
    private final UsuariosDao usuariosDao = new UsuariosDao();

    public List<PersonasBean> consultarPersonas(String documento, String tipoDocumento, String nombre, String apellidos, String correo, String usuario, String perfil, String cargo, String nombreCompleto) {
        List<PersonasBean> listaPersonas = new ArrayList<>();
        try {
            listaPersonas = personasDao.consultarPersonas(null, documento, tipoDocumento, nombre, apellidos, correo, usuario, perfil, cargo, nombreCompleto);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(PersonasNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaPersonas;
    }

    public PersonasBean consultarPersona(String idPersona, String documento, String tipoDocumento) {
        PersonasBean persona = null;
        List<PersonasBean> listaPersonas = null;
        try {
            if (idPersona != null) {
                listaPersonas = personasDao.consultarPersonas(idPersona, null, null, null, null, null, null, null, null, null);
            } else if (documento != null && !documento.isEmpty() && tipoDocumento != null && !tipoDocumento.isEmpty()) {
                listaPersonas = personasDao.consultarPersonas(null, documento, tipoDocumento, null, null, null, null, null, null, null);
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(PersonasNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (listaPersonas != null && !listaPersonas.isEmpty()) {
            persona = listaPersonas.get(0);
        }
        return persona;
    }

    public String guardarPersona(Integer idPersona, String documento, String tipoDocumento, String nombres, String apellidos,
            String telefono, String celular, String correo, String direccion, String cargo, String nombreUsuario, String perfil, String clave, String clave2) {
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
                guardado = personasDao.actualizarPersona(persona);
            } else {
                guardado = personasDao.insertarPersona(persona);
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

    public boolean validarEmail(String email) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public String validarDatos(String documento, String tipoDocumento, String nombres, String apellidos,
            String correo, String direccion, String cargo, String usuario, String perfil, String clave, String clave2) {
        String error = "";
        if (tipoDocumento == null || tipoDocumento.trim().isEmpty() || tipoDocumento.trim().equals("0")) {
            error += "El campo 'Tipo de documento' es obligatorio \n";
        }
        if (documento == null || documento.trim().isEmpty()) {
            error += "El campo 'Documento' es obligatorio \n";
        }
        if (nombres == null || nombres.trim().isEmpty()) {
            error += "El campo 'Nombres' es obligatorio \n";
        }
        if (apellidos == null || apellidos.trim().isEmpty()) {
            error += "El campo 'Apellidos' es obligatorio \n";
        }
        if (correo == null || correo.trim().isEmpty()) {
            error += "El campo 'Correo' es obligatorio \n";
        } else if (!validarEmail(correo)) {
            error += "El campo 'Correo' no cuenta con el formato válido correo@dominio \n";
        }
        if (direccion == null || direccion.trim().isEmpty()) {
            error += "El campo 'Dirección' es obligatorio \n";
        }
        if (cargo == null || cargo.equals("0")) {
            error += "El campo 'Cargo' es obligatorio \n";
        }
        if ((usuario != null && !usuario.isEmpty()) || (perfil != null && !perfil.equals("0")) || (clave != null && !clave.isEmpty()) || (clave2 != null && !clave2.isEmpty())) {
            if (usuario == null || usuario.isEmpty()) {
                error += "El campo 'Usuario' es obligatorio \n";
            }
            if (perfil == null) {
                error += "El campo 'Perfil' es obligatorio \n";
            }
            UsuariosBean objetoUsuario = null;
            if (documento != null && !documento.isEmpty() && tipoDocumento != null && !tipoDocumento.equals("0")) {
                try {
                    List<UsuariosBean> listaUsuarios = usuariosDao.consultarUsuarios(null, null, null, null, documento, tipoDocumento);
                    if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
                        objetoUsuario = listaUsuarios.get(0);
                    }
                } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                    Logger.getLogger(PersonasNegocio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (clave == null || clave.isEmpty()) {
                if (objetoUsuario == null) {
                    error += "El campo 'Clave' es obligatorio \n";
                } else if (clave2 != null && !clave2.isEmpty()) {
                    error += "El campo 'Clave' es obligatorio \n";
                }
            } else {
                if (clave2 == null || clave2.isEmpty()) {
                    error += "El campo 'Confirmar clave' es obligatorio \n";
                } else if (!clave.equals(clave2)) {
                    error += "El valor en el campo 'Clave' y 'Confirmar clave' deben ser iguales \n";
                }
            }
        }
        return error;
    }

    public String eliminarPersona(Integer idPersona) {
        String error = "";
        try {
            usuariosDao.eliminarUsuario(idPersona);
            int eliminacion = personasDao.eliminarPersona(idPersona);
            if (eliminacion == 0) {
                error = "La persona no pudo ser eliminada";
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(PersonasNegocio.class.getName()).log(Level.SEVERE, null, ex);
            error = "Ocurrió un error eliminado la persona";
        }
        return error;
    }
    
    public JSONArray completarPersonas(String busqueda){
        JSONArray array = new JSONArray();
        List<PersonasBean> listaPersonas = consultarPersonas(null, null, null, null, null, null, null, null, busqueda);
        if(listaPersonas != null && !listaPersonas.isEmpty()){
            for (PersonasBean persona : listaPersonas) {
                JSONObject object = new JSONObject();
                object.put("texto", persona.getTipoDocumento() + persona.getDocumento() + " " + persona.getNombres() + " " + persona.getApellidos());
                object.put("valor", persona.getId());
                array.add(object);
            }
        }
        return array;
    }
}
