package com.twg.negocio;

import com.twg.persistencia.daos.PersonasDao;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Pipe
 */
public class PersonasNegocio {
    private final PersonasDao personasDao = new PersonasDao();
    
    public Map<String, Object> consultarPersona(String tipoPersona, String documento, String tipoDocumento, String nombre, String apellidos, String correo){
        Map<String, Object> resultado = new HashMap<>();
        return resultado;
    }
}
