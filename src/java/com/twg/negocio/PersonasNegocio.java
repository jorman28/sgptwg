package com.twg.negocio;

import com.twg.persistencia.daos.PersonasDao;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Pipe
 */
public class PersonasNegocio {

    private final PersonasDao personasDao = new PersonasDao();

    public Map<String, Object> consultarPersona(String tipoPersona, String documento, String tipoDocumento, String nombre, String apellidos, String correo) {
        Map<String, Object> resultado = new HashMap<>();
        return resultado;
    }

    public Map<String, Object> crearPersona(String tipoPersona, Integer idPersona, String documento, String tipoDocumento, String nombres, String apellidos,
            String telefono, String celular, String correo, String direccion, Integer cargo, String fechaInicio, String usuario, Integer perfil, String clave, String clave2) {
        Map<String, Object> resultado = new HashMap<>();
        return resultado;
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private String validarDatos(String tipoPersona, String documento, String tipoDocumento, String nombres, String apellidos,
            String correo, String direccion, Integer cargo, String usuario, Integer perfil, String clave, String clave2) {
        String error = "";
        if (tipoDocumento == null || tipoDocumento.trim().isEmpty() || tipoDocumento.trim().equals("0")) {
            error += "El campo 'Tipo de documento' es obligatorio <br/>";
        }
        if (documento == null || documento.trim().isEmpty()) {
            error += "El campo 'Documento' es obligatorio <br/>";
        }
        if (nombres == null || nombres.trim().isEmpty()) {
            error += "El campo 'Nombres' es obligatorio <br/>";
        }
        if (apellidos == null || apellidos.trim().isEmpty()) {
            error += "El campo 'Apellidos' es obligatorio <br/>";
        }
        if (correo == null || correo.trim().isEmpty()) {
            error += "El campo 'Correo' es obligatorio <br/>";
        } else if (!validarEmail(correo)) {
            error += "El campo 'Correo' no cuenta con el formato válido correo@dominio <br/>";
        }
        if (direccion == null || direccion.trim().isEmpty()) {
            error += "El campo 'Dirección' es obligatorio <br/>";
        }
        if (tipoPersona.equals("EMPLEADO")) {
            if (cargo == null || cargo.intValue() != 0) {
                error += "El campo 'Dirección' es obligatorio <br/>";
            }
        }
        return error;
    }
}
