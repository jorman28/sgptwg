package com.twg.negocio;

import com.twg.controladores.UsuariosController;
import com.twg.persistencia.beans.PerfilesBean;
import com.twg.persistencia.daos.PerfilesDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PerfilesNegocio {
    private final PerfilesDao perfilesDao = new PerfilesDao();
    
    public List<PerfilesBean> consultarPerfiles(){
        List<PerfilesBean> perfiles = new ArrayList<>();
        try {
            perfiles = perfilesDao.consultarPerfiles();
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return perfiles;
    }
}
