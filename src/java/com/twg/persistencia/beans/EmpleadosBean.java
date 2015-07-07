/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.twg.persistencia.beans;

/**
 *
 * @author Erika Jhoana
 */
public class EmpleadosBean {

    private int id_persona;
    private int cargo;
    
    public EmpleadosBean(){
    }

    public int getId_persona() {
        return id_persona;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }

    public int getCargo() {
        return cargo;
    }

    public void setCargo(int cargo) {
        this.cargo = cargo;
    }
    
    
}
