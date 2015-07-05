package com.twg.persistencia.beans;

/**
 *
 * @author Pipe
 */
public class UsuariosBean {
    
    private Integer idPersona;
    private String usuario;
    private String clave;
    private Integer perfil;
    
    public UsuariosBean(){
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Integer getPerfil() {
        return perfil;
    }

    public void setPerfil(Integer perfil) {
        this.perfil = perfil;
    }
}
