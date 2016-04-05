package com.twg.negocio;

import com.twg.persistencia.beans.ArchivosBean;
import com.twg.persistencia.daos.ArchivosDao;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 * Clase encargada de realizar la conexión entre la vista y las operaciones en
 * base de datos.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class ArchivosNegocio {

    private final ArchivosDao archivosDao = new ArchivosDao();
    public final String tipoComentario = "COMENTARIO";
    public final String tipoArchivo = "ARCHIVO";
    public final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Método encargado de consultar los datos relacionados con un archivo
     * específico y setearlos en un JSON para su pintado en pantalla
     *
     * @param idArchivo
     * @return El JSON con los datos que coinciden con identificador de archivo
     * enviado
     */
    public JSONObject consultarArchivo(Integer idArchivo) {
        JSONObject archivoObject = new JSONObject();
        List<ArchivosBean> listaArchivos = consultarArchivos(idArchivo, null, null, null);
        if (listaArchivos != null && !listaArchivos.isEmpty()) {
            ArchivosBean archivo = listaArchivos.get(0);
            if (archivo != null) {
                archivoObject.put("id", archivo.getId());
                archivoObject.put("nombre", archivo.getNombre());
                archivoObject.put("descripcion", archivo.getDescripcion());
                archivoObject.put("persona", archivo.getNombrePersona());
                archivoObject.put("archivo", "<a onclick=\"descargarArchivo('" + archivo.getRuta() + "');\">" + archivo.getRuta() + "</a>");
                archivoObject.put("fecha", sdf.format(archivo.getFechaCreacion()));
            }
        }
        return archivoObject;
    }

    /**
     * Método encargado de consultar la lista de archivos que coinciden con los
     * filtros de búsqueda ingresados
     *
     * @param idArchivo
     * @param contine
     * @param fecha
     * @param idPersona
     * @return La lista de archivos que coinciden con los parámetros de búsqueda
     * ingresados
     */
    public List<ArchivosBean> consultarArchivos(Integer idArchivo, String contine, Date fecha, Integer idPersona) {
        List<ArchivosBean> listaArchivos = new ArrayList<>();
        try {
            listaArchivos = archivosDao.consultarArchivos(idArchivo, contine, fecha, idPersona);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ArchivosNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaArchivos;
    }

    /**
     * Método en cargado de realizar la eliminación lógica de un archivo
     * asignando una fecha de eliminación
     *
     * @param idArchivo
     * @return Una cadena de texto con el error. En caso de ser vacío indica que
     * no se presentaron errores en el proceso
     */
    public String eliminarArchivo(Integer idArchivo) {
        String errorEliminacion = "";
        try {
            if (archivosDao.eliminarArchivo(idArchivo) <= 0) {
                errorEliminacion = "El archivo no pudo ser eliminado";
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ArchivosNegocio.class.getName()).log(Level.SEVERE, null, ex);
            errorEliminacion = "Ocurrió un error eliminando el archivo";
        }
        return errorEliminacion;
    }

    /**
     * Método encargado de realizar la validación de los datos ingresados por
     * pantalla antes de la inserción o modificación en base de datos
     *
     * @param idArchivo
     * @param nombre
     * @param descripcion
     * @param nombreArchivo
     * @return Una cadena de texto con el error. En caso de ser vacío indica que
     * no se presentaron errores en el proceso
     */
    public String validarDatos(Integer idArchivo, String nombre, String descripcion, String nombreArchivo) {
        StringBuilder error = new StringBuilder();
        if (nombre == null || nombre.isEmpty()) {
            error.append("El campo 'Nombre' es obligatorio <br>");
        } else if (nombre.length() > 50) {
            error.append("La longitud del dato ingresado en el campo 'Nombre' debe ser menor a 50 <br>");
        }
        if (descripcion == null || descripcion.isEmpty()) {
            error.append("El campo 'Descripción' es obligatorio <br>");
        } else if (descripcion.length() > 1000) {
            error.append("La longitud del dato ingresado en el campo 'Descripción' debe ser menor a 1000 <br>");
        }
        if (idArchivo == null && (nombreArchivo == null || nombreArchivo.isEmpty())) {
            error.append("Debe seleccionar un archivo para cargar <br>");
        }
        return error.toString();
    }

    /**
     * Método encargado de almacenar o editar el registro de archivo en base de
     * datos y almacenar el archivo cargado en el servidor
     *
     * @param idArchivo
     * @param nombre
     * @param descripcion
     * @param idPersona
     * @param nombreArchivo
     * @return
     */
    public String guardarArchivo(Integer idArchivo, String nombre, String descripcion, Integer idPersona, String nombreArchivo) {
        String error = "";
        ArchivosBean archivo = new ArchivosBean();
        archivo.setId(idArchivo);
        archivo.setNombre(nombre);
        archivo.setDescripcion(descripcion);
        try {
            if (archivo.getId() != null) {
                if (archivosDao.actualizarArchivo(archivo) <= 0) {
                    error = "El archivo no pudo ser guardado";
                }
            } else {
                archivo.setFechaCreacion(new Date());
                archivo.setIdPersona(idPersona);
                archivo.setRuta(nombreArchivo);
                archivo.setTipo(tipoArchivo);
                if (archivosDao.insertarArchivo(archivo) <= 0) {
                    error = "El archivo no pudo ser guardado";
                }
            }

        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ArchivosNegocio.class.getName()).log(Level.SEVERE, null, ex);
            error = "Ocurrió un error guardando el archivo";
        }
        return error;
    }
}
