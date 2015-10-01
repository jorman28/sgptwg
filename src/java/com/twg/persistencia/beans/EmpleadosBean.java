package com.twg.persistencia.beans;

/**
 *
 * @author Erika Jhoana
 */
public class EmpleadosBean extends PersonasBean{

    private Integer cargo;
    private String descripcionCargo;
    
    public EmpleadosBean(){
    }

    public Integer getCargo() {
        return cargo;
    }

    public void setCargo(Integer cargo) {
        this.cargo = cargo;
    }
    public String getDescripcionCargo() {
        return descripcionCargo;
    }

    public void setDescripcionCargo(String descripcionCargo) {
        this.descripcionCargo = descripcionCargo;
    }
}
