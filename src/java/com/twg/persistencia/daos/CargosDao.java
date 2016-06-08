/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.twg.persistencia.daos;

import com.twg.persistencia.beans.CargosBean;
import com.twg.persistencia.sqls.CargosSql;
import com.twg.utilidades.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de obtener la conexión con la base de datos y ejecutar las
 * sentencias con base en los datos enviados desde el negocio
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class CargosDao {
    private final CargosSql sql = new CargosSql();
    
    /**
     * Método constructor de la clase.
     */
    public CargosDao(){
    }

    /**
     * Método encargado de consultar el listado de cargos que hay en el sistema
     * según los fltros aplicados.
     * @param nombre
     * @param nombreExacto
     * @param limite
     * @return Listado con los cargos consultados según los filtros.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public List<CargosBean> consultarCargos(String nombre, boolean nombreExacto, String limite) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        List<CargosBean> listaCargos = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarCargos(nombre, nombreExacto, limite));
        ResultSet rs;
        rs = ps.executeQuery();
        while(rs.next()){
            CargosBean cargo = new CargosBean();
            cargo.setId(rs.getInt("id"));
            cargo.setNombre(rs.getString("nombre"));
            listaCargos.add(cargo);
        }
        rs.close();
        ps.close();
        con.close();
        return listaCargos;
    }
    
    /**
     * Método encargado de consultar la cantidad de cargos existentes en base
     * de datos relacionados con los filtros ingresados
     *
     * @param nombre
     * @param nombreExacto
     * @return Cantidad de registros de cargos, según los filtros aplicados.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public int cantidadCargos(String nombre, boolean nombreExacto) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        int cantidadCargos = 0;
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.cantidadCargos(nombre, nombreExacto));
        ResultSet rs;
        rs = ps.executeQuery();
        if (rs.next()) {
            cantidadCargos = rs.getInt("cantidadCargos");
        }
        rs.close();
        ps.close();
        con.close();
        return cantidadCargos;
    }
    
    /**
     * Método encargado de consultar la información de un cargo específico.
     * @param id
     * @return Un objeto con todos los atributos del cargo consultado.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public CargosBean consultarCargo(Integer id) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        CargosBean cargo = new CargosBean();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarCargo(id));
        ResultSet rs;
        rs = ps.executeQuery();
        if(rs.next()){
            cargo.setId(rs.getInt("id"));
            cargo.setNombre(rs.getString("nombre"));
        }
        rs.close();
        ps.close();
        con.close();
        return cargo;
    }
    
    /**
     * Método encargado de insertar un nuevo cargo en el sistema.
     * @param cargo
     * @return Un número indicando si el proceso se realizó o no.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public int insertarCargo(CargosBean cargo) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarCargo());
        ps.setString(1, cargo.getNombre());
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }
    
    /**
     * Método encargado de actualizar la información de un cargo específico.
     * @param cargo
     * @return Un número indicando si el proceso se realizó o no.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public int actualizarCargo(CargosBean cargo) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actualizarCargo());
        ps.setString(1, cargo.getNombre());
        ps.setInt(2, cargo.getId());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }
    
    /**
     * Método encargado de eliminar un cargo.
     * @param idCargo
     * @return Un número indicando si el proceso se realizó o no.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public int eliminarCargo(Integer idCargo) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.eliminarCargo());
        ps.setInt(1, idCargo);
        int eliminacion = ps.executeUpdate();
        ps.close();
        con.close();
        return eliminacion;
    }
}
