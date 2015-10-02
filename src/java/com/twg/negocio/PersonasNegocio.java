package com.twg.negocio;

import com.twg.persistencia.beans.PersonasBean;
import com.twg.persistencia.daos.PersonasDao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Pipe
 */
public class PersonasNegocio {
    private final PersonasDao personasDao = new PersonasDao();
    
    public Map<String, Object> consultarPersona(String tipoPersona, String documento, String tipoDocumento, String nombre, String apellidos, String correo){
        Map<String, Object> resultado = new HashMap<>();
        List<PersonasBean> listaPersonas = personasDao.consultarPersonas(idPersona, documento, tipo_documento, nombres, apellidos,
                        telefono, celular, correo, direccion, usuario, perfil);
                    persona = new PersonasBean();
                    persona.setDocumento(documento);
                    persona.setTipo_documento(tipo_documento);
                    persona.setNombres(nombres);
                    persona.setApellidos(apellidos);
                    persona.setTelefono(telefono);
                    persona.setCelular(celular);
                    persona.setCorreo(correo);
                    persona.setDireccion(direccion);
                    enviarDatos(request, persona, null);
        return resultado;
    }
}
