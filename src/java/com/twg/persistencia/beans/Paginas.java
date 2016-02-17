package com.twg.persistencia.beans;

public enum Paginas {

    INICIO("Inicio", 1),
    SEGURIDAD("Seguridad", 2),
    USUARIOS("Usuarios", 3),
    PERMISOS("Permisos", 4),
    CONFIGURACION("Configuracion", 5),
    PERSONAS("Personas", 6),
    CARGOS("Cargos", 7),
    ESTADOS("Estados", 8),
    PROYECTOS("Proyectos", 9),
    VERSIONES("Versiones", 10),
    ACTIVIDADES("Actividades", 11),
    DOCUMENTACION("Documentacion", 12),
    REPORTES("Reportes", 13);

    private final String nombrePagina;
    private final Integer idPagina;

    private Paginas(String nombrePagina, Integer idPagina) {
        this.nombrePagina = nombrePagina;
        this.idPagina = idPagina;
    }

    public String getNombrePagina() {
        return this.nombrePagina;
    }

    public Integer getIdPagina() {
        return this.idPagina;
    }
}
