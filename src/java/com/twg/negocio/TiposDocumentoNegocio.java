package com.twg.negocio;

import com.twg.persistencia.beans.TiposDocumentosBean;
import com.twg.persistencia.daos.TiposDocumentosDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase encargada de realizar la conexión entre la vista y las operaciones en
 * base de datos, para la tabla de Tipos de documento.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class TiposDocumentoNegocio {
    private final TiposDocumentosDao tiposDocumentosDao = new TiposDocumentosDao();
    
    /**
     * Método encargado de consultar todos los tipos de documento que hay en 
     * el sistema.
     * @return Listado con os tipos de documentos.
     */
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
