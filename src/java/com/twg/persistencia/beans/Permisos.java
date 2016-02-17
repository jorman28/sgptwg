package com.twg.persistencia.beans;

public enum Permisos {

    ACCESO("ACCESO"),
    COMENTAR("COMENTAR"),
    CONSULTAR("CONSULTAR"),
    CREAR("CREAR"),
    CREAR_PROYECTO("CREAR_PROYECTO"),
    CREAR_VERSION("CREAR_VERSION"),
    ELIMINAR("ELIMINAR"),
    ELIMINAR_PROYECTO("ELIMINAR_PROYECTO"),
    ELIMINAR_VERSION("ELIMINAR_VERSION"),
    GUARDAR("GUARDAR"),
    PERMISOS("PERMISOS");

    private final String nombre;

    private Permisos(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }
}
