package com.twg.utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Pipe
 */
public class ConexionBaseDatos {
    
    public ConexionBaseDatos() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        Class.forName("com.mysql.jdbc.Driver").newInstance();
    }
    
    public Connection obtenerConexion() throws SQLException{
        return DriverManager.getConnection("jdbc:mysql://mysql.hostinger.es:3306/u101442387_sgptw","u101442387_sgptw","ZbmVVoFDmL4kPsQCdl");
    }
    
    public static void main(String[] args) {
        try{
            ConexionBaseDatos conexion = new ConexionBaseDatos();
            Connection con;
            con = conexion.obtenerConexion();
            if(con!=null){
                System.out.println("Conexion exitosa");
            }
            else{
                System.out.println("Conexion fallida");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
}
