package com.twg.persistencia.beans;

/**
 * Esta clase se encarga de enumerar los permisos que existen en el sistema.
 * 
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public enum Permisos {

    ACCESO("ACCESO"),
    COMENTAR("COMENTAR"),
    CONSULTAR("CONSULTAR"),
    CREAR("CREAR"),
    GUARDAR("GUARDAR"),
    ELIMINAR("ELIMINAR"),
    PERMISOS("PERMISOS"),
    CREAR_PROYECTO("CREAR_PROYECTO"),
    GUARDAR_PROYECTO("GUARDAR_PROYECTO"),
    ELIMINAR_PROYECTO("ELIMINAR_PROYECTO"),
    CREAR_VERSION("CREAR_VERSION"),
    GUARDAR_VERSION("GUARDAR_VERSION"),
    ELIMINAR_VERSION("ELIMINAR_VERSION"),
    ELIMINAR_COMENTARIOS("ELIMINAR_COMENTARIOS"),
    ELIMINAR_COMENTARIO_PROPIO("ELIMINAR_COMENTARIO_PROPIO"),
    INGRESAR_TIEMPOS("INGRESAR_TIEMPOS"),
    INGRESAR_TIEMPO_PROPIO("INGRESAR_TIEMPO_PROPIO"),
    ELIMINAR_TIEMPOS("ELIMINAR_TIEMPOS"),
    ELIMINAR_TIEMPO_PROPIO("ELIMINAR_TIEMPO_PROPIO");

    //Declaración del atributo Nombre, para un permiso.
    private final String nombre;

    /*
    * Declaración de los métodos Get y Set para el atributo nombre.
    */
    
    private Permisos(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }
}
