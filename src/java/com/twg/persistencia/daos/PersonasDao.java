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

    public PersonasDao() {
    }

    public List<PersonasBean> consultarPersonas(String idPersona, String documento, String tipoDocumento, String nombres, String apellidos, String correo, String usuario, String perfil, String cargo, String nombreCompleto) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<PersonasBean> listaPersonas = new ArrayList<>();
        PreparedStatement ps;
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        ps = con.prepareStatement(sql.consultarPersonas(idPersona, documento, tipoDocumento, nombres, apellidos, correo, usuario, perfil, cargo, nombreCompleto));
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
            PersonasBean persona = new PersonasBean();
            persona.setId(rs.getInt("id"));
            persona.setDocumento(rs.getString("documento"));
            persona.setTipoDocumento(rs.getString("tipo_documento"));
            persona.setNombreTipoDocumento(rs.getString("nombre_tipo_documento"));
            persona.setNombres(rs.getString("nombres"));
            persona.setApellidos(rs.getString("apellidos"));
            persona.setDireccion(rs.getString("direccion"));
            persona.setTelefono(rs.getString("telefono"));
            persona.setCelular(rs.getString("celular"));
            persona.setCorreo(rs.getString("correo"));
            persona.setUsuario(rs.getString("usuario"));
            persona.setPerfil(rs.getInt("id_perfil"));
            persona.setNombrePerfil(rs.getString("nombre_perfil"));
            persona.setCargo(rs.getInt("cargo"));
            persona.setNombreCargo(rs.getString("nombre_cargo"));
            persona.setNombre(persona.getTipoDocumento() + persona.getDocumento() + " " + persona.getNombres() + " " + persona.getApellidos());
            listaPersonas.add(persona);
        }
        rs.close();
        ps.close();
        con.close();
        return listaPersonas;
    }
    
    public List<PersonasBean> consultarPersonasProyecto(String idProyecto) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<PersonasBean> listaPersonas = new ArrayList<>();
        PreparedStatement ps;
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        ps = con.prepareStatement(sql.consultarPersonasProyecto(idProyecto));
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
            PersonasBean persona = new PersonasBean();
            persona.setId(rs.getInt("id"));
            persona.setDocumento(rs.getString("documento"));
            persona.setTipoDocumento(rs.getString("tipo_documento"));
            persona.setNombreTipoDocumento(rs.getString("nombre_tipo_documento"));
            persona.setNombres(rs.getString("nombres"));
            persona.setApellidos(rs.getString("apellidos"));
            persona.setDireccion(rs.getString("direccion"));
            persona.setTelefono(rs.getString("telefono"));
            persona.setCelular(rs.getString("celular"));
            persona.setCorreo(rs.getString("correo"));
            persona.setUsuario(rs.getString("usuario"));
            persona.setPerfil(rs.getInt("id_perfil"));
            persona.setNombrePerfil(rs.getString("nombre_perfil"));
            persona.setCargo(rs.getInt("cargo"));
            persona.setNombreCargo(rs.getString("nombre_cargo"));
            listaPersonas.add(persona);
        }
        rs.close();
        ps.close();
        con.close();
        return listaPersonas;
    }
    
    
    public List<PersonasBean> consultarPersonasActividad(String idActividad) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        List<PersonasBean> listaPersonas = new ArrayList<>();
        PreparedStatement ps;
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        ps = con.prepareStatement(sql.consultarPersonasActividad(idActividad));
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()) {
            PersonasBean persona = new PersonasBean();
            persona.setId(rs.getInt("id"));
            persona.setDocumento(rs.getString("documento"));
            persona.setTipoDocumento(rs.getString("tipo_documento"));
            persona.setNombreTipoDocumento(rs.getString("nombre_tipo_documento"));
            persona.setNombres(rs.getString("nombres"));
            persona.setApellidos(rs.getString("apellidos"));
            persona.setDireccion(rs.getString("direccion"));
            persona.setTelefono(rs.getString("telefono"));
            persona.setCelular(rs.getString("celular"));
            persona.setCorreo(rs.getString("correo"));
            persona.setUsuario(rs.getString("usuario"));
            persona.setPerfil(rs.getInt("id_perfil"));
            persona.setNombrePerfil(rs.getString("nombre_perfil"));
            persona.setCargo(rs.getInt("cargo"));
            persona.setNombreCargo(rs.getString("nombre_cargo"));
            persona.setNombre(persona.getTipoDocumento() + persona.getDocumento() + " " + persona.getNombres() + " " + persona.getApellidos());
            listaPersonas.add(persona);
        }
        rs.close();
        ps.close();
        con.close();
        return listaPersonas;
    }

    public Integer consultarIdPersona(String documento, String tipoDocumento) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Integer idPersona = null;
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.consultarIdPersona(documento, tipoDocumento));
        ResultSet rs;
        rs = ps.executeQuery();
        if (rs.next()) {
            idPersona = rs.getInt("id");
        }
        rs.close();
        ps.close();
        con.close();
        return idPersona;
    }

    public int insertarPersona(PersonasBean persona) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.insertarPersona());
        ps.setString(1, persona.getDocumento());
        ps.setString(2, persona.getTipoDocumento());
        ps.setString(3, persona.getNombres());
        ps.setString(4, persona.getApellidos());
        ps.setString(5, persona.getDireccion());
        ps.setString(6, persona.getTelefono());
        ps.setString(7, persona.getCelular());
        ps.setString(8, persona.getCorreo());
        ps.setInt(9, persona.getCargo());
        int insercion = ps.executeUpdate();
        ps.close();
        con.close();
        return insercion;
    }

    public int actualizarPersona(PersonasBean persona) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Connection con;
        con = new ConexionBaseDatos().obtenerConexion();
        PreparedStatement ps;
        ps = con.prepareStatement(sql.actualizarPersona());
        ps.setString(1, persona.getDocumento());
        ps.setString(2, persona.getTipoDocumento());
        ps.setString(3, persona.getNombres());
        ps.setString(4, persona.getApellidos());
        ps.setString(5, persona.getDireccion());
        ps.setString(6, persona.getTelefono());
        ps.setString(7, persona.getCelular());
        ps.setString(8, persona.getCorreo());
        ps.setInt(9, persona.getCargo());
        ps.setInt(10, persona.getId());
        int actualizacion = ps.executeUpdate();
        ps.close();
        con.close();
        return actualizacion;
    }

    public int eliminarPersona(Integer id) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
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
