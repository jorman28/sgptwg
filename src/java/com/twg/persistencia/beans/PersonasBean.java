package com.twg.persistencia.beans;

/**
 *
 * @author Erika Jhoana
 */
public class PersonasBean {

    private int id;
    private String documento;
    private String tipoDocumento;
    private String nombreTipoDocumento;
    private String nombres;
    private String apellidos;
    private String direccion;
    private String telefono;
    private String celular;
    private String correo;
    private String usuario;
    private String clave;
    private Integer perfil;
    private Integer nombrePerfil;
    private Integer cargo;
    private String nombreCargo;

    public PersonasBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getPerfil() {
        return perfil;
    }

    public void setPerfil(Integer perfil) {
        this.perfil = perfil;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombreTipoDocumento() {
        return nombreTipoDocumento;
    }

    public void setNombreTipoDocumento(String nombreTipoDocumento) {
        this.nombreTipoDocumento = nombreTipoDocumento;
    }

    public Integer getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(Integer nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }

    public Integer getCargo() {
        return cargo;
    }

    public void setCargo(Integer cargo) {
        this.cargo = cargo;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

}
