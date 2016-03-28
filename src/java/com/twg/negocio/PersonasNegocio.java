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

    public JSONArray completarPersonas(String busqueda) {
        JSONArray array = new JSONArray();
        List<PersonasBean> listaPersonas = consultarPersonas(null, null, null, null, null, null, null, null, busqueda);
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

    //Jara 23/02/2015 - Método para consultar el grupo de personas en determinado proyecto
    public JSONArray consultarPersonasProyecto(String idProyecto, String Busqueda) {
        JSONArray array = new JSONArray();
        List<PersonasBean> listaPersonas = consultarPersonasxProyecto(idProyecto, Busqueda);
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

    public List<PersonasBean> consultarPersonasxProyecto(String idProyecto, String Busqueda) {
        List<PersonasBean> listaPersonas = new ArrayList<>();
        try {
            listaPersonas = personasDao.consultarPersonasProyecto(idProyecto, Busqueda);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(PersonasNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaPersonas;
    }

    public List<PersonasBean> consultarPersonasActividad(String idActividad) {
        List<PersonasBean> listaPersonas = new ArrayList<>();
        try {
            listaPersonas = personasDao.consultarPersonasActividad(idActividad);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(PersonasNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaPersonas;
    }

    //Jara 25/03/2016 - Método para consultar el grupo de personas ocupadas en en las fechas seleccionadas
    public JSONArray consultarPersonasAsignadasActividad(String idPersonas, java.util.Date fechaEstimadaInicio, java.util.Date fechaEstimadaFin, String idActividad) {
        JSONArray array = new JSONArray();
        try {
            List<PersonasBean> listaPersonas = personasDao.consultarPersonasAsignadasActividad(idPersonas, fechaEstimadaInicio, fechaEstimadaFin, idActividad);
            if (listaPersonas != null && !listaPersonas.isEmpty()) {
                for (PersonasBean persona : listaPersonas) {
                    JSONObject object = new JSONObject();
                    object.put("id", persona.getId());
                    object.put("nombre", persona.getTipoDocumento() + persona.getDocumento() + " " + persona.getNombres() + " " + persona.getApellidos());
                    object.put("cargo", persona.getNombreCargo().equalsIgnoreCase("Cliente") ? "Cliente" : persona.getNombreCargo());
                    array.add(object);
                }
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(PersonasNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return array;
    }
}
