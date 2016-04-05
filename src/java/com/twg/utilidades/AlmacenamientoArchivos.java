package com.twg.utilidades;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Part;

/**
 * Clase encargada de almacenar archivos en el servidor por medio de una
 * interfaz gráfica
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class AlmacenamientoArchivos {

    public final String rutaCargas = "D://POLITECNICO JIC/PPI SGPTWG/cargas/";

    public void cargarArchivo(Part archivoTemporal, String nombreArchivo) {
        try {
            InputStream ubicacionArchivo;
            ubicacionArchivo = archivoTemporal.getInputStream();
            File rutaArchivo = new File(rutaCargas + nombreArchivo);
            FileOutputStream archivoFinal;
            archivoFinal = new FileOutputStream(rutaArchivo);
            int datos = ubicacionArchivo.read();
            while (datos != -1) {
                archivoFinal.write(datos);
                datos = ubicacionArchivo.read();
            }
            archivoFinal.close();
            ubicacionArchivo.close();
        } catch (IOException ex) {
            Logger.getLogger(AlmacenamientoArchivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
