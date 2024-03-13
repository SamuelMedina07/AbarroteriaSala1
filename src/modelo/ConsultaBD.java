/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class ConsultaBD extends Conexion {

    PreparedStatement ps = null;
    String sentenciaSQL;
    ResultSet rs;
    Clientes cli;

    /*
    Este método se utiliza para insertar un nuevo cliente en la base de datos.
    Utiliza la conexión a la base de datos obtenida de la clase padre Conexion.
    Se prepara una sentencia SQL para la inserción con placeholders ? y se establecen los valores utilizando el objeto cliente.
    Luego, se ejecuta la sentencia y se muestra un mensaje de éxito o de error en caso de una excepción.
    La conexión se cierra en el bloque finally para liberar recursos
    */
    public boolean crearClientes(Clientes cliente) {
        Connection con = getConnection();
        sentenciaSQL = "INSERT INTO tbl_clientes (id, nombreCliente, direccion, telefono, estado)VALUES (?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sentenciaSQL);
            ps.setInt(1, cliente.getCodigo());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getDireccion());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getEstado());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Cliente creado correctamente");
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Cliente no ha sido creado" + ex.getMessage());
            return false;
        }finally {
            closeConnection(con);
        }
        
        
    }

    /*
    Este método obtiene todos los clientes de la base de datos y los devuelve como un ArrayList de objetos Clientes.
    Utiliza la conexión a la base de datos obtenida de la clase padre Conexion.
    Se prepara una sentencia SQL de selección y se ejecuta. Luego, se recorre el resultado y se crea un objeto Clientes para cada registro.
    Los objetos Clientes se agregan a la lista lista_clientes, que se devuelve al final.
    La conexión se cierra en el bloque finally para liberar recursos.
    */
    public ArrayList<Clientes> leerTodosClientes() {
        ArrayList lista_clientes = new ArrayList();
        Connection con = getConnection();
        sentenciaSQL = "SELECT * FROM tbl_clientes";
        try {
            ps = con.prepareStatement(sentenciaSQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                cli = new Clientes();
                cli.setCodigo(rs.getInt(1));
                cli.setNombre(rs.getString(2));
                cli.setDireccion(rs.getString(3));
                cli.setTelefono(rs.getString(4));
                cli.setEstado(rs.getString(5));
                lista_clientes.add(cli);
            }
            closeConnection(con);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo leer clientes" + ex.getMessage());
        }
        return lista_clientes;
    }
    
    /*
    Este método busca un cliente específico por su código en la base de datos.
    Utiliza la conexión a la base de datos obtenida de la clase padre Conexion.
    Se prepara una sentencia SQL de selección con un filtro WHERE por el código del cliente.
    Se ejecuta la sentencia y, si se encuentra un resultado, se actualiza el objeto cliente con los valores obtenidos.
    Devuelve true si se encontró el cliente, de lo contrario, devuelve false.
    La conexión se cierra en el bloque finally para liberar recursos.
    */

    public boolean buscarCliente(Clientes cliente) {
        Connection con = getConnection();
        sentenciaSQL = "SELECT * FROM tbl_clientes WHERE id=?";
        try {
            
            ps = con.prepareStatement(sentenciaSQL);
            ps.setInt(1, cliente.getCodigo());
            rs = ps.executeQuery();
            if (rs.next()) {
                cliente.setCodigo(rs.getInt(1));
                cliente.setNombre(rs.getString(2));
                cliente.setDireccion(rs.getString(3));
                cliente.setTelefono(rs.getString(4));
                cliente.setEstado(rs.getString(5));
                return true;

            }
            return false;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo leer datos" + e.getMessage());
            return false;
        } finally {
            closeConnection(con);
        }
        
    }
    
    public boolean modificarClientes(Clientes cliente) {
        Connection con = getConnection();
        sentenciaSQL = "UPDATE  tbl_clientes SET nombreCliente=?, direccion=?, telefono=? WHERE id=?";
        
        try {
            ps = con.prepareStatement(sentenciaSQL);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getDireccion());
            ps.setString(3, cliente.getTelefono());
            ps.setInt(4, cliente.getCodigo());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Cliente actualizado correctamente");
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Cliente no ha sido actualizado" + ex.getMessage());
            return false;
        }finally {
            closeConnection(con);
        }
        
        
    }

    public boolean eliminarClientes(Clientes cliente) {
        Connection con = getConnection();
        sentenciaSQL = "UPDATE  tbl_clientes SET estado=? WHERE id=?";
        
        try {
            ps = con.prepareStatement(sentenciaSQL);
            ps.setString(1, "Inactivo");
            ps.setInt(2, cliente.getCodigo());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Cliente desactivado correctamente");
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Cliente no ha sido desactivado" + ex.getMessage());
            return false;
        }finally {
            closeConnection(con);
        }
    }

}
