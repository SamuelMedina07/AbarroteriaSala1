/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexion {
    
    Connection c = null;//Es una instancia de la clase Connection que se utiliza para manejar la conexión a la base de datos
    /*
    String db: Nombre de la base de datos.
    String user: Nombre de usuario para la conexión a la base de datos.
    String pass: Contraseña para la conexión a la base de datos.
    String url: URL de la base de datos, que incluye el nombre de la base de datos, el host y el puerto.
    */
    private String db = "bd_abarroteria_ammc";
    private String user = "root";
    private String pass = "";
    private String url = "jdbc:mysql://localhost:3306/"+db;
    /*
    jdbc:mysql://: Protocolo de conexión para MySQL.
    localhost: El nombre del host donde se encuentra la base de datos. 
    3306: Puerto de conexión. El puerto predeterminado para MySQL es 3306, pero podría ser diferente si el servidor MySQL está configurado para escuchar en otro puerto.
    db: Nombre de la base de datos
    */
    
    public Connection getConnection(){
        try {
            // Cargar el controlador de la base de datos MySQL
            Class.forName("com.mysql.jdbc.Driver");
            // Establecer la conexión utilizando DriverManager
            c = (Connection)DriverManager.getConnection(this.url, this.user,this.pass);
            System.out.print("Conexion Realizada Correctamente");//investigar porque con cada dato son 5 conexiones re...
        } catch (ClassNotFoundException ex) {
            System.out.print("Error no se puso realizar conexion" + ex.getMessage());
        } catch (SQLException ex) {
            System.out.print("Error no se puso realizar conexion" + ex.getMessage());
        }
        return c;
    }
    /*
    Método getConnection():

    Este método intenta establecer una conexión a la base de datos MySQL utilizando JDBC.
    Se utiliza el controlador com.mysql.jdbc.Driver.
    La conexión se realiza mediante el método DriverManager.getConnection.
    Puede ser útil cerrar la conexión (c.close()) al finalizar la operación para liberar recursos.
    */
    
    
    
    public void closeConnection(Connection con) {
        
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("Conexion Cerrada: " );
            }else{
             System.out.println("Conexion ya estaba Cerrada: " );
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    
    public static void main(String[] args) {
        // TODO code application logic here
        Conexion con = new Conexion();
        con.getConnection();
    }
    
}
