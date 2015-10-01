package com.twg.utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Pipe
 */
public class ConexionBaseDatos {
    
    private final String host = "localhost";
    private final String port = "3306";
    private final String dataBase = "sgptwg";
    private final String user = "adminyCU5jj8";
    private final String password = "UYkkahX37sYj";
    
    public ConexionBaseDatos() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        Class.forName("com.mysql.jdbc.Driver").newInstance();
    }
    
    public Connection obtenerConexion() throws SQLException{
        return DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+dataBase, user, password);
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
