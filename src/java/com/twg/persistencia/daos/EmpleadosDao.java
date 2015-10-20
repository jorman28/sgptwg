/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.twg.persistencia.daos;

import com.twg.persistencia.beans.EmpleadosBean;
import com.twg.persistencia.sqls.EmpleadosSql;
import com.twg.utilidades.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Erika Jhoana
 */
public class EmpleadosDao {
    private final EmpleadosSql sql = new EmpleadosSql();
    
    public EmpleadosDao(){
    }

    public List<EmpleadosBean> consultarEmpleados() throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        List<EmpleadosBean> listaEmpleados = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarEmpleados());
        ResultSet rs;
        rs = ps.executeQuery();
        while(rs.next()){
            EmpleadosBean empleado = new EmpleadosBean();
            empleado.setId(rs.getInt("id_persona"));
            empleado.setCargo(rs.getInt("cargo"));
            
            listaEmpleados.add(empleado);
        }
        rs.close();
        ps.close();
        con.close();
        return listaEmpleados;
    }
    
    public int insertarEmpleado(EmpleadosBean empleado) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarEmpleado());
        ps.setInt(1, empleado.getId());
        ps.setInt(2, empleado.getCargo());
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }
    
    public int actualizarEmpleado(EmpleadosBean empleado) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actualizarEmpleado());
        ps.setInt(1, empleado.getCargo());
        ps.setInt(2, empleado.getId());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }
    
    public int eliminarEmpleado(Integer idPersona) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.eliminarEmpleado());
        ps.setInt(1, idPersona);
        int eliminacion = ps.executeUpdate();
        ps.close();
        con.close();
        return eliminacion;
    }
}
