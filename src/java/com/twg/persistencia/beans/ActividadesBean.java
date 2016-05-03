package com.twg.persistencia.beans;

/**
 * Esta clase define los atributos del objeto Actividades, junto con los métodos
 * get y set.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class ActividadesBean {

    //Definición de atributos.
    private Integer id;
    private Integer version;
    private String nombreVersion;
    private String nombre;
    private String descripcion;
    private Integer estado;
    private String nombreEstado;

    /*
     * Declaración de métodos Get y Set para cada atributo de la clase.
     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getNombreVersion() {
        return nombreVersion;
    }

    public void setNombreVersion(String nombreV) {
        this.nombreVersion = nombreV;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreE) {
        this.nombreEstado = nombreE;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
