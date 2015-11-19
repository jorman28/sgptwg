package com.twg.persistencia.beans;

import java.util.Date;

/**
 *
 * @author Erika Jhoana
 */
public class ClientesBean extends PersonasBean{

    private Date fecha_inicio;
    
    public ClientesBean(){
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }
}
