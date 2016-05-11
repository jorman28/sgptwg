package com.twg.persistencia.daos;

import com.twg.persistencia.beans.ActividadesEmpleadosBean;
import com.twg.persistencia.sqls.ActividadesEmpleadosSql;
import com.twg.utilidades.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author erikasta07
 */
public class ActividadesEmpleadosDao {

    private final ActividadesEmpleadosSql sql = new ActividadesEmpleadosSql();

    public ActividadesEmpleadosDao() {
    }

    public List<ActividadesEmpleadosBean> consultarActividadesEmpleados(Integer idActividad, Integer idEmpleado) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<ActividadesEmpleadosBean> listaActividadesEmpleados = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarActividadEmpleado(idActividad, idEmpleado));
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
            ActividadesEmpleadosBean actividadEmpleado = new ActividadesEmpleadosBean();
            actividadEmpleado.setActividad(rs.getInt("actividad"));
            actividadEmpleado.setEmpleado(rs.getInt("empleado"));
            actividadEmpleado.setFechaEstimadaInicio((Date)rs.getObject("fecha_estimada_inicio"));
            actividadEmpleado.setFechaEstimadaTerminacion((Date)rs.getObject("fecha_estimada_terminacion"));
            actividadEmpleado.setTiempoEstimado((Double)rs.getObject("tiempo_estimado"));
            listaActividadesEmpleados.add(actividadEmpleado);
        }
        rs.close();
        ps.close();
        con.close();
        return listaActividadesEmpleados;
    }

    public int insertarActividadEmpleado(ActividadesEmpleadosBean actividadEmpleado) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarActividadEmpleado());
        ps.setInt(1, actividadEmpleado.getActividad());
        ps.setInt(2, actividadEmpleado.getEmpleado());
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }
    
    public int actualizarActividadEmpleado(ActividadesEmpleadosBean actividadEmpleado) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actualizarActividadEmpleado());
        ps.setObject(1, actividadEmpleado.getFechaEstimadaInicio());
        ps.setObject(2, actividadEmpleado.getFechaEstimadaTerminacion());
        ps.setObject(3, actividadEmpleado.getTiempoEstimado());
        ps.setInt(4, actividadEmpleado.getActividad());
        ps.setInt(5, actividadEmpleado.getEmpleado());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }

    public int eliminarActividadEmpleado(Integer idActividad, Integer idEmpleado) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.eliminarActividadEmpleado(idActividad, idEmpleado));
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
            ps = con.prepareStatement(sql.eliminarActividadesEmpleados());
            ps.setInt(1, Actividad);
            ps.setString(2, strEmpleados.toString());
            eliminacion = ps.executeUpdate();
            ps.close();

        } else {
            PreparedStatement ps;
            ps = con.prepareStatement(sql.eliminarActividadEmpleado(Actividad, null));
            eliminacion = ps.executeUpdate();
            ps.close();
        }

        con.close();
        return eliminacion;
    }
}
