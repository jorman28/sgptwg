package com.twg.negocio;

import com.twg.persistencia.beans.ArchivosBean;
import com.twg.persistencia.daos.ArchivosDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase encargada de realizar la conexión entre la vista y las operaciones en
 * base de datos.
 *
 * @author Andrés Giraldo
 */
public class ArchivosNegocio {

    private final ArchivosDao archivosDao = new ArchivosDao();
    
    public List<ArchivosBean> consultarArchivos() {
        List<ArchivosBean> listaArchivos = new ArrayList<>();
        try {
            listaArchivos = archivosDao.consultarArchivos();
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ArchivosNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaArchivos;
    }

}
