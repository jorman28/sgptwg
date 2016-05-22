package com.twg.utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Esta clase define métodos para obtener la conexión a la base de datos.
 * 
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class ConexionBaseDatos {
    
    private final String host = "localhost";
    private final String port = "3306";
    private final String dataBase = "sgptwg";
    private final String user = "adminyCU5jj8";
    private final String password = "UYkkahX37sYj";
    
    /**
     * Método encargado de crear una nueva instancia para conectarse a la 
     * base de datos.
     * 
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException 
     */
    public ConexionBaseDatos() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        Class.forName("com.mysql.jdbc.Driver").newInstance();
    }
    
    /**
     * Método encargado de enviar las credenciales de la base de datos 
     * y obtener la conexión.
     * 
     * @return
     * @throws SQLException 
     */
    public Connection obtenerConexion() throws SQLException{
        return DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+dataBase, user, password);
    }
    
    /**
     * Método utilizado para probar la conexión a la base de datos
     * en caso de ser requerido.
     * 
     * @param args 
     */
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
