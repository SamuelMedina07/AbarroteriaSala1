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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class ConsultaProveedores extends Conexion {

    PreparedStatement ps = null;
    String sentenciaSQL;
    ResultSet rs;
    Proveedores proveedor;

    /*
    Este método se utiliza para insertar un nuevo cliente en la base de datos.
    Utiliza la conexión a la base de datos obtenida de la clase padre Conexion.
    Se prepara una sentencia SQL para la inserción con placeholders ? y se establecen los valores utilizando el objeto cliente.
    Luego, se ejecuta la sentencia y se muestra un mensaje de éxito o de error en caso de una excepción.
    La conexión se cierra en el bloque finally para liberar recursos
    */
    public boolean crearProveedor(Proveedores proveedor) {
        Connection con = getConnection();
        sentenciaSQL = "INSERT INTO tbl_proveedores (id, nombreProveedor, direccionProveedor, telefonoProveedor, estadoProveedor)VALUES (?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sentenciaSQL);
            ps.setInt(1, proveedor.getCodigo());
            ps.setString(2, proveedor.getNombre());
            ps.setString(3, proveedor.getDireccion());
            ps.setString(4, proveedor.getTelefono());
            ps.setString(5, proveedor.getEstado());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Proovedor creado correctamente");
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Proovedor no ha sido creado" + ex.getMessage());
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
    public ArrayList<Proveedores> leerTodosProveedores() {
        ArrayList lista_proveedores = new ArrayList();
        Connection con = getConnection();
        sentenciaSQL = "SELECT * FROM tbl_proveedores";
        try {
            ps = con.prepareStatement(sentenciaSQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                proveedor = new Proveedores();
                proveedor.setCodigo(rs.getInt(1));
                proveedor.setNombre(rs.getString(2));
                proveedor.setDireccion(rs.getString(3));
                proveedor.setTelefono(rs.getString(4));
                proveedor.setEstado(rs.getString(5));
                lista_proveedores.add(proveedor);
            }
            closeConnection(con);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo leer clientes" + ex.getMessage());
        }
        return lista_proveedores;
    }
    
    /*
    Este método busca un cliente específico por su código en la base de datos.
    Utiliza la conexión a la base de datos obtenida de la clase padre Conexion.
    Se prepara una sentencia SQL de selección con un filtro WHERE por el código del cliente.
    Se ejecuta la sentencia y, si se encuentra un resultado, se actualiza el objeto cliente con los valores obtenidos.
    Devuelve true si se encontró el cliente, de lo contrario, devuelve false.
    La conexión se cierra en el bloque finally para liberar recursos.
    */

    public boolean buscarProveedor(Proveedores proveedor) {
        Connection con = getConnection();
        sentenciaSQL = "SELECT * FROM tbl_proveedores WHERE id=?";
        try {
            
            ps = con.prepareStatement(sentenciaSQL);
            ps.setInt(1, proveedor.getCodigo());
            rs = ps.executeQuery();
            if (rs.next()) {
                proveedor.setCodigo(rs.getInt(1));
                proveedor.setNombre(rs.getString(2));
                proveedor.setDireccion(rs.getString(3));
                proveedor.setTelefono(rs.getString(4));
                proveedor.setEstado(rs.getString(5));
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
    
    public String obtenerNnombreSegunIdProveedor(int id){
        String nombreProveedor;
        Connection con = getConnection();
        sentenciaSQL = "SELECT * FROM tbl_proveedores WHERE id=?";
        
        try{
            ps = con.prepareStatement(sentenciaSQL);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if (rs.next()) {
                nombreProveedor = rs.getString("nombreProveedor");
                return nombreProveedor;
            }
            
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "No se pudo leer datos " + ex.getMessage());
        } finally {
            closeConnection(con);
        }
        return null;
        
    }
    
    public Proveedores obtenerProveedorSegunIdProveedor(int id){
        Connection con = getConnection();
        sentenciaSQL = "SELECT * FROM tbl_proveedores WHERE id=?";
        
        try{
            ps = con.prepareStatement(sentenciaSQL);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if (rs.next()) {
                proveedor.setCodigo(rs.getInt(1));
                proveedor.setNombre(rs.getString(2));
                proveedor.setDireccion(rs.getString(3));
                proveedor.setTelefono(rs.getString(4));
                proveedor.setEstado(rs.getString(5));
                return proveedor;
            }
            
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "No se pudo leer datos " + ex.getMessage());
        } finally {
            closeConnection(con);
        }
        return null;
        
    }
    
     public boolean modificarProveedor(Proveedores proveedor) {
        Connection con = getConnection();
        sentenciaSQL = "UPDATE  tbl_proveedores SET nombreProveedor=?, direccionProveedor=?, telefonoProveedor=? WHERE id=?";
        
        try {
            ps = con.prepareStatement(sentenciaSQL);
            ps.setString(1, proveedor.getNombre());
            ps.setString(2, proveedor.getDireccion());
            ps.setString(3, proveedor.getTelefono());
            ps.setInt(4, proveedor.getCodigo());
            ps.execute();
            JOptionPane.showMessageDialog(null, "proveedor actualizado correctamente");
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "proveedor no ha sido actualizado" + ex.getMessage());
            return false;
        }finally {
            closeConnection(con);
        }
        
        
    }

    public boolean eliminarProveedor(Proveedores proveedor) {
        Connection con = getConnection();
        sentenciaSQL = "UPDATE  tbl_proveedores SET estadoProveedor=? WHERE id=?";
        
        try {
            ps = con.prepareStatement(sentenciaSQL);
            ps.setString(1, "Inactivo");
            ps.setInt(2, proveedor.getCodigo());
            ps.execute();
            JOptionPane.showMessageDialog(null, "proveedor desactivado correctamente");
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "proveedor no ha sido desactivado" + ex.getMessage());
            return false;
        }finally {
            closeConnection(con);
        }
    }

}
