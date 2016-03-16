/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twg.persistencia.daos;

import com.twg.persistencia.beans.ActividadesEmpleadosBean;
import com.twg.persistencia.sqls.ActividadesEmpleadosSql;
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
public class ActividadesEmpleadosDao {

    private final ActividadesEmpleadosSql sql = new ActividadesEmpleadosSql();

    public ActividadesEmpleadosDao() {
    }

    public List<ActividadesEmpleadosBean> consultarActividadesEmpleados(Integer actividad, Integer empleado) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<ActividadesEmpleadosBean> listaEmpleados = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarEmpleadosxActividad(actividad, empleado));
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
            ActividadesEmpleadosBean acti = new ActividadesEmpleadosBean();
            acti.setActividad(rs.getInt("actividad"));
            acti.setEmpleado(rs.getInt("empleado"));
            listaEmpleados.add(acti);
        }
        rs.close();
        ps.close();
        con.close();
        return listaEmpleados;
    }

    public ActividadesEmpleadosBean consultarActividadEmpleado(Integer idActividad, Integer idEmpleado) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        ActividadesEmpleadosBean ActividadEmpleado = new ActividadesEmpleadosBean();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarEmpleadosxActividad(idActividad, idEmpleado));
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
            ActividadesEmpleadosBean acti = new ActividadesEmpleadosBean();
            acti.setActividad(rs.getInt("actividad"));
            acti.setEmpleado(rs.getInt("empleado"));
            ActividadEmpleado = acti;
        }
        rs.close();
        ps.close();
        con.close();
        return ActividadEmpleado;
    }

    public int insertarActividadEmpleado(ActividadesEmpleadosBean actividad_empleado) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarActividad_Empleado());
        //ps.setInt(1, estado.getId());
        ps.setInt(1, actividad_empleado.getActividad());
        ps.setInt(2, actividad_empleado.getEmpleado());
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }

    public int eliminarActividadEmpleado(Integer idActividad, Integer idEmpleado) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.eliminarActividad_Empleado(idActividad, idEmpleado));
//        ps.setInt(1, idActividad);
//        ps.setInt(2, idEmpleado);
        int eliminacion = ps.executeUpdate();
        ps.close();
        con.close();
        return eliminacion;
    }

    public int eliminarActividadesEmpleados(Integer Actividad, String[] empleados) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        int eliminacion = 0;

        if (empleados != null && empleados.length > 0) {
            StringBuilder strEmpleados = new StringBuilder("");
            for (int i = 0; i < empleados.length; i++) {
                strEmpleados.append(empleados[i]);
                if (i < empleados.length - 1) {
                    strEmpleados.append(',');
                }
            }

            PreparedStatement ps;
            ps = con.prepareStatement(sql.eliminarActividades_Empleados());
            ps.setInt(1, Actividad);
            ps.setString(2, strEmpleados.toString());
            eliminacion = ps.executeUpdate();
            ps.close();

        } else {
            PreparedStatement ps;
            ps = con.prepareStatement(sql.eliminarActividad_Empleado(Actividad, null));
            eliminacion = ps.executeUpdate();
            ps.close();
        }

        con.close();
        return eliminacion;
    }
}
