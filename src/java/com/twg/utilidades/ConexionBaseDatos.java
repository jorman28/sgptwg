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
        return DriverManager.getConnection("jdbc:mysql://127.9.4.2:3306/sgptwg","adminyCU5jj8","UYkkahX37sYj");
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
