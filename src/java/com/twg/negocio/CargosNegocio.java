package com.twg.negocio;

import com.twg.persistencia.beans.CargosBean;
import com.twg.persistencia.daos.CargosDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author Pipe
 */
public class CargosNegocio {

    private final CargosDao cargosDao = new CargosDao();

    public List<CargosBean> consultarCargos(String nombre, boolean nombreExacto) {
        List<CargosBean> listaCargos = new ArrayList<>();
        try {
            listaCargos = cargosDao.consultarCargos(nombre, nombreExacto);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(CargosNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaCargos;
    }

    public JSONObject consultarCargo(Integer id) {
        JSONObject object = new JSONObject();
        try {
            CargosBean cargo = cargosDao.consultarCargo(id);
            if (cargo != null) {
                object.put("id", cargo.getId());
                object.put("nombre", cargo.getNombre());
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(CargosNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return object;
    }

    public String guardarCargo(String id, String nombre) {
        String error = "";
        CargosBean cargo = new CargosBean();
        cargo.setNombre(nombre);
        try {
            int guardar = 0;
            if (id != null && !id.isEmpty()) {
                cargo.setId(Integer.valueOf(id));
                guardar = cargosDao.actualizarCargo(cargo);
            } else {
                guardar = cargosDao.insertarCargo(cargo);
            }
            if (guardar == 0) {
                error += "El cargo no pudo ser guardado \n";
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(CargosNegocio.class.getName()).log(Level.SEVERE, null, ex);
            error += "Ocurrieron errores guardando el cargo \n";
        }
        return error;
    }

    public String validarCargo(String id, String nombre) {
        String error = "";
        if (nombre == null || nombre.isEmpty()) {
            error += "El campo 'Nombre del cargo' es obligatorio <br />";
        } else {
            if (nombre.length() > 50) {
                error += "El campo 'Nombre del cargo' no debe contener más de 50 caracteres, has dígitado " + nombre.length() + " caracteres <br />";
            } else {
                try {
                    List<CargosBean> listaCargos = cargosDao.consultarCargos(nombre, true);
                    if (listaCargos != null && !listaCargos.isEmpty()) {
                        if (id == null || id.isEmpty() || !id.equals(listaCargos.get(0).getId().toString())) {
                            error += "El nombre del cargo ingresado ya existe en el sistema \n";
                        }
                    }
                } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                    Logger.getLogger(CargosNegocio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return error;
    }

    public String eliminarCargo(Integer id) {
        String error = "";
        try {
            int eliminacion = cargosDao.eliminarCargo(id);
            if (eliminacion == 0) {
                error += "El cargo no pudo ser eliminado \n";
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(CargosNegocio.class.getName()).log(Level.SEVERE, null, ex);
            error += "Ocurrieron errores eliminando el cargo \n";
        }
        return error;
    }
}
