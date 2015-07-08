/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.twg.persistencia.daos;

import com.twg.persistencia.beans.ClientesBean;
import com.twg.persistencia.sqls.ClientesSql;
import com.twg.utilidades.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Erika Jhoana
 */
public class ClientesDao {
    private final ClientesSql sql = new ClientesSql();
    
    public ClientesDao(){
    }

    public List<ClientesBean> consultarClientes() throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        List<ClientesBean> listaClientes = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarClientes());
        ResultSet rs;
        rs = ps.executeQuery();
        while(rs.next()){
            ClientesBean cliente = new ClientesBean();
            cliente.setId_persona(rs.getInt("id_persona"));
            cliente.setFecha_inicio(rs.getDate("fecha_inicio"));
            
            listaClientes.add(cliente);
        }
        rs.close();
        ps.close();
        con.close();
        return listaClientes;
    }
    
    public int insertarCliente(ClientesBean cliente) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarCliente());
        ps.setInt(1, cliente.getId_persona());
        ps.setDate(2, new Date(cliente.getFecha_inicio().getTime()));
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }
    
    public int actualizarCliente(ClientesBean cliente) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actualizarCliente());
        ps.setDate(1, new Date(cliente.getFecha_inicio().getTime()));
        ps.setInt(2, cliente.getId_persona());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }
    
    public int eliminarCliente(Integer idPersona) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.eliminarCliente());
        ps.setInt(1, idPersona);
        int eliminacion = ps.executeUpdate();
        ps.close();
        con.close();
        return eliminacion;
    }
}
