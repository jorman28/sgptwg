package com.twg.persistencia.sqls;

/**
 * Clase que contiene las sentencias SQL para la interacción con la base de
 * datos
 *
 * @author Andrés Giraldo
 */
public class ArchivosSql {

    public String consultarArchivos() {
        return "SELECT * FROM archivos WHERE fecha_eliminacion IS NULL";
    }

    public String insertarArchivo() {
        return "INSERT INTO archivos (nombre, descripcion, fecha_creacion, ruta, id_persona, tipo) VALUES (?,?,?,?,?,?)";
    }

    public String actualizarArchivo() {
        return "UPDATE archivos SET nombre = ?, descripcion = ?, fecha_creacion = ?, ruta = ?, id_persona = ?, tipo = ? WHERE id = ?";
    }

    public String eliminarArchivo() {
        return "UPDATE archivos SET fecha_eliminacion = now() WHERE id = ? AND fecha_eliminacion IS NULL";
    }
}
