package com.twg.negocio;

import com.twg.persistencia.beans.TiposDocumentosBean;
import com.twg.persistencia.daos.TiposDocumentosDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TiposDocumentoNegocio {
    private final TiposDocumentosDao tiposDocumentosDao = new TiposDocumentosDao();
    
    public List<TiposDocumentosBean> consultarTiposDocumentos() {
        List<TiposDocumentosBean> tiposDocumentos = new ArrayList<>();
        try {
            tiposDocumentos = tiposDocumentosDao.consultarTiposDocumentos();
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(TiposDocumentoNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tiposDocumentos;
    }
}
