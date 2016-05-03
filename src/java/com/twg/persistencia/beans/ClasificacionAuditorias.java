package com.twg.persistencia.beans;

/**
 * Esta clase se encarga de enumerar las diferentes categorías de auditorías que
 * existen en el sistema.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public enum ClasificacionAuditorias {

    USUARIO("USUARIO"),
    PERMISO("PERMISO"),
    PERSONA("PERSONA"),
    CARGO("CARGO"),
    ESTADO("ESTADO"),
    PROYECTO("PROYECTO"),
    VERSION("VERSIÓN"),
    ACTIVIDAD("ACTIVIDAD"),
    DOCUMENTO("DOCUMENTO"),
    COMENTARIO("COMENTARIO");

    /**
     * Declaración del atributo Nombre para la definición de una clasificación.
     */
    private final String nombre;

    /**
     * Método encargado se asignar un nombre a un elemento de la enumeración.
     *
     * @param nombre
     */
    private ClasificacionAuditorias(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método encargado de obtener el nombre que tenga asociado un elemento de
     * la enumeración
     *
     * @return
     */
    public String getNombre() {
        return this.nombre;
    }
}
