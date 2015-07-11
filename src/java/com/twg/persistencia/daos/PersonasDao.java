/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.twg.persistencia.daos;

import com.twg.persistencia.beans.PersonasBean;
import com.twg.persistencia.sqls.PersonasSql;
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
public class PersonasDao {
    private final PersonasSql sql = new PersonasSql();
    
    public PersonasDao(){
    }

    /**
     * Método para consultar a las personas.
     * Se envía [true] y [documento] en los parámetros, cuando se necesita consultar una sola persona.
     * Se envía [false] y [null] para traer el listaro de todas las personas el sistema.
     * @param unica
     * @param documento
     * @return
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException 
     */
    public List<PersonasBean> consultarPersonas(boolean unica, Integer documento) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        List<PersonasBean> listaPersonas = new ArrayList<>();
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarPersonas(unica));
        if(documento!=null && unica){
            ps.setInt(1, documento);
        }
        ResultSet rs;
        rs = ps.executeQuery();
        while(rs.next()){
            PersonasBean persona = new PersonasBean();
            persona.setId(rs.getInt("id"));
            persona.setDocumento(rs.getString("documento"));
            persona.setTipo_documento(rs.getString("tipo_documento"));
            persona.setNombres(rs.getString("nombres"));
            persona.setApellidos(rs.getString("apellidos"));
            persona.setDirecion(rs.getString("direccion"));
            persona.setTelefono(rs.getString("telefono"));
            persona.setCelular(rs.getString("celular"));
            persona.setCorreo(rs.getString("correo"));
            listaPersonas.add(persona);
        }
        rs.close();
        ps.close();
        con.close();
        return listaPersonas;
    }
    
    public int insertarPersona(PersonasBean persona) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarPersona());
        ps.setInt(1, persona.getId());
        ps.setString(2, persona.getDocumento());
        ps.setString(3, persona.getTipo_documento());
        ps.setString(4, persona.getNombres());
        ps.setString(5, persona.getApellidos());
        ps.setString(6, persona.getDirecion());
        ps.setString(7, persona.getTelefono());
        ps.setString(8, persona.getCelular());
        ps.setString(9, persona.getCorreo());
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }
    
    public int actualizarPersona(PersonasBean persona) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actualizarPersona());
        ps.setString(1, persona.getDocumento());
        ps.setString(2, persona.getTipo_documento());
        ps.setString(3, persona.getNombres());
        ps.setString(4, persona.getApellidos());
        ps.setString(5, persona.getDirecion());
        ps.setString(6, persona.getTelefono());
        ps.setString(7, persona.getCelular());
        ps.setString(8, persona.getCorreo());
        ps.setInt(9, persona.getId());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }
    
    public int eliminarPersona(Integer id) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException{
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.eliminarPersona());
        ps.setInt(1, id);
        int eliminacion = ps.executeUpdate();
        ps.close();
        con.close();
        return eliminacion;
    }
}
