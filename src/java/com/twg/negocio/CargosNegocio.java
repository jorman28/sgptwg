package com.twg.negocio;

import com.twg.persistencia.beans.CargosBean;
import com.twg.persistencia.daos.CargosDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pipe
 */
public class CargosNegocio {

    private final CargosDao cargosDao = new CargosDao();

    public List<CargosBean> consultarCargos(String nombre) {
        List<CargosBean> listaCargos = new ArrayList<>();
        try {
            listaCargos = cargosDao.consultarCargos(nombre);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(CargosNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaCargos;
    }
}
