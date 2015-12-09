/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twg.persistencia.daos;

import com.twg.persistencia.beans.Actividades_EmpleadosBean;
import com.twg.persistencia.sqls.Actividades_EmpleadosSql;
import com.twg.utilidades.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author erikasta07
 */
public class Actividades_EmpleadosDao {
    private final Actividades_EmpleadosSql sql = new Actividades_EmpleadosSql();

    public Actividades_EmpleadosDao(){
    }
    
    public List<Actividades_EmpleadosBean> consultarActividadesEmpleados(Integer empleado) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        List<Actividades_EmpleadosBean> listaEmpleados = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarEmpleadosxAtividad(empleado));
        ResultSet rs;
        rs = ps.executeQuery();
        while(rs.next()){
            Actividades_EmpleadosBean acti = new Actividades_EmpleadosBean();
            acti.setActividad(rs.getInt("actividad"));
            acti.setEmpleado(rs.getInt("empleado"));
            listaEmpleados.add(acti);
        }
        rs.close();
        ps.close();
        con.close();
        return listaEmpleados;
    }
    
}
