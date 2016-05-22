package com.twg.persistencia.beans;

/**
 * Esta clase se encarga de enumerar las páginas que existen en el sistema.
 * 
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
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
    AUDITORIAS("Auditorias", 15);

    //Declaración de los atributos de una página.
    private final String nombrePagina;
    private final Integer idPagina;

    /*
    * Declaración de los métodos Get y Set de los atributos de la clase.
    */

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
