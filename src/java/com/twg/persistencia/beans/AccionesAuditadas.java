package com.twg.persistencia.beans;

/**
 * Esta clase se encarga de enumerar las acciones auditadas que existen en el
 * sistema.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public enum AccionesAuditadas {

    CREACION("CREACIÓN"),
    EDICION("EDICIÓN"),
    ELIMINACION("ELIMINACIÓN");

    /**
     * Declaración del atributo Nombre para la definición de una acción.
     */
    private final String nombre;

    /**
     * Método encargado se asignar un nombre a un elemento de la enumeración.
     *
     * @param nombre
     */
    private AccionesAuditadas(String nombre) {
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
