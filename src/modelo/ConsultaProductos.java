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

public class ConsultaProductos extends Conexion {

    PreparedStatement ps = null;
    String sentenciaSQL;
    ResultSet rs;
    private Productos producto;

    public boolean crearProducto(Productos producto) {
        Connection con = getConnection();
        sentenciaSQL = "INSERT INTO tbl_productos (descripcion, costo, utilidad, precio_V, id_Proveedor, proveedor, stock_minimo, stock_maximo, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            ps = con.prepareStatement(sentenciaSQL);
            ps.setString(1, producto.getDescripcion());
            ps.setDouble(2, producto.getCosto());
            ps.setDouble(3, producto.getUtilidad());
            ps.setDouble(4, producto.getPrecioVenta());
            ps.setInt(5, producto.getIdProveedor());
            ps.setString(6, producto.getProveedor());
            ps.setInt(7, producto.getStockMinimo());
            ps.setInt(8, producto.getStockMaximo());
            ps.setString(9, producto.getEstado());

            ps.execute();
            JOptionPane.showMessageDialog(null, "Producto creado correctamente");
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo crear el producto" + ex.getMessage());
            return false;
        } finally {
            closeConnection(con);
        }
    }

    public void setearProductoCompleto(ResultSet rs, Productos producto) throws SQLException {
        producto.setIdProducto(rs.getInt(1));
        producto.setDescripcion(rs.getString(2));
        producto.setCosto(rs.getDouble(3));
        producto.setUtilidad(rs.getDouble(4));
        producto.setPrecioVenta(rs.getDouble(5));
        producto.setIdProveedor(rs.getInt(6));
        producto.setProveedor(rs.getString(7));
        producto.setStockMinimo(rs.getInt(8));
        producto.setStockMaximo(rs.getInt(9));
        producto.setEstado(rs.getString(10));
    }

    public ArrayList<Productos> leerTodosProductos() {
        ArrayList<Productos> listaProductos = new ArrayList<>();
        Connection con = getConnection();
        sentenciaSQL = "SELECT * FROM tbl_productos";

        try {
            ps = con.prepareStatement(sentenciaSQL);
            rs = ps.executeQuery();

            while (rs.next()) {
                producto = new Productos();
                producto.setIdProducto(rs.getInt(1));
                producto.setDescripcion(rs.getString(2));
                producto.setCosto(rs.getDouble(3));
                producto.setUtilidad(rs.getDouble(4));
                producto.setPrecioVenta(rs.getDouble(5));
                producto.setIdProveedor(rs.getInt(6));
                producto.setProveedor(rs.getString(7));
                producto.setStockMinimo(rs.getInt(8));
                producto.setStockMaximo(rs.getInt(9));
                producto.setEstado(rs.getString(10));

                listaProductos.add(producto);
            }
            closeConnection(con);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo leer los productos" + ex.getMessage());
        }
        return listaProductos;
    }
    
    //BUSCAR PRODUCTOS POR ID
    public ArrayList<Productos> buscarProductosPoriD(int id) {
        ArrayList<Productos> listaProductos = new ArrayList<>();
        Connection con = getConnection();
        sentenciaSQL = "SELECT * FROM tbl_productos WHERE idProcucto LIKE ?";
        try {
            ps = con.prepareStatement(sentenciaSQL);
            ps.setString(1, "%" + id + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                producto = new Productos();
                setearProductoCompleto(rs,producto);
                listaProductos.add(producto);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo buscar el producto" + ex.getMessage());
        } finally {
            closeConnection(con);
        }
        return listaProductos;
    }
    
    
    //BUSCAR PRODUCTOS POR DESCRIPCION
    public ArrayList<Productos> buscarProductosPorDecripcion(String descripcion) {
        ArrayList<Productos> listaProductos = new ArrayList<>();
        Connection con = getConnection();
        sentenciaSQL = "SELECT * FROM tbl_productos WHERE descripcion LIKE ?";
        try {
            ps = con.prepareStatement(sentenciaSQL);
            ps.setString(1, "%" + descripcion + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                producto = new Productos();
                setearProductoCompleto(rs,producto);
                listaProductos.add(producto);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo buscar el producto" + ex.getMessage());
        } finally {
            closeConnection(con);
        }
        return listaProductos;
    }

    public ArrayList<Productos> buscarProductosPorProveedor(String nombreProveedor) {
        ArrayList<Productos> listaProductos = new ArrayList<>();
        Connection con = getConnection();
        sentenciaSQL = "SELECT * FROM tbl_productos WHERE proveedor LIKE ?";
        try {
            ps = con.prepareStatement(sentenciaSQL);
            ps.setString(1, "%" + nombreProveedor + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                producto = new Productos();
                setearProductoCompleto(rs,producto);
                listaProductos.add(producto);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo buscar el producto" + ex.getMessage());
        } finally {
            closeConnection(con);
        }
        return listaProductos;
    }
    
    public ArrayList<Productos> buscarProductosActivos() {
        ArrayList<Productos> listaProductos = new ArrayList<>();
        Connection con = getConnection();
        sentenciaSQL = "SELECT * FROM tbl_productos WHERE estado = 'Activo'";
        try {
            ps = con.prepareStatement(sentenciaSQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                producto = new Productos();
                setearProductoCompleto(rs,producto);
                listaProductos.add(producto);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo buscar el producto" + ex.getMessage());
        } finally {
            closeConnection(con);
        }
        return listaProductos;
    }
    public Productos obtenerProductoSegunIdProducto(int id){
        Connection con = getConnection();
        sentenciaSQL = "SELECT * FROM tbl_productos WHERE idProcucto=?";
        
        try{
            ps = con.prepareStatement(sentenciaSQL);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if (rs.next()) {
                producto.setIdProducto(rs.getInt(1));
                producto.setDescripcion(rs.getString(2));
                producto.setCosto(rs.getDouble(3));
                producto.setUtilidad(rs.getDouble(4));
                producto.setPrecioVenta(rs.getDouble(5));
                producto.setIdProveedor(rs.getInt(6));
                producto.setProveedor(rs.getString(7));
                producto.setStockMinimo(rs.getInt(8));
                producto.setStockMaximo(rs.getInt(9));
                producto.setEstado(rs.getString(10));
                return producto;
            }
            
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "No se pudo leer datos " + ex.getMessage());
        } finally {
            closeConnection(con);
        }
        return null;
        
    }
    public Productos obtenerValoresProducto(Productos _producto){
        Connection con = getConnection();
        sentenciaSQL = "SELECT * FROM tbl_productos WHERE idProcucto=?";
        
        try{
            ps = con.prepareStatement(sentenciaSQL);
            ps.setInt(1,_producto.getIdProducto());
            rs = ps.executeQuery();
            if (rs.next()) {
                _producto.setIdProducto(rs.getInt(1));
                _producto.setDescripcion(rs.getString(2));
                _producto.setCosto(rs.getDouble(3));
                _producto.setUtilidad(rs.getDouble(4));
                _producto.setPrecioVenta(rs.getDouble(5));
                _producto.setIdProveedor(rs.getInt(6));
                _producto.setProveedor(rs.getString(7));
                _producto.setStockMinimo(rs.getInt(8));
                _producto.setStockMaximo(rs.getInt(9));
                _producto.setEstado(rs.getString(10));
                return _producto;
            }
            
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "No se pudo leer datos " + ex.getMessage());
        } finally {
            closeConnection(con);
        }
        return null;
        
    }
    public boolean buscarProducto(Productos producto) {

        Connection con = getConnection();
        sentenciaSQL = "SELECT * FROM tbl_productos WHERE idProcucto=?";

        try {
            ps = con.prepareStatement(sentenciaSQL);
            ps.setInt(1, producto.getIdProducto());
            rs = ps.executeQuery();

            if (rs.next()) {
                producto.setIdProducto(rs.getInt(1));
                producto.setDescripcion(rs.getString(2));
                producto.setCosto(rs.getDouble(3));
                producto.setUtilidad(rs.getDouble(4));
                producto.setPrecioVenta(rs.getDouble(5));
                producto.setIdProveedor(rs.getInt(6));
                producto.setProveedor(rs.getString(7));
                producto.setStockMinimo(rs.getInt(8));
                producto.setStockMaximo(rs.getInt(9));
                producto.setEstado(rs.getString(10));
                return true;
            }
            return false;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo buscar el producto" + ex.getMessage());
            return false;
        } finally {
            closeConnection(con);
        }
    }
    
    public boolean modificarProducto(Productos producto) {
        Connection con = getConnection();
        sentenciaSQL = "UPDATE  tbl_productos SET descripcion=?, costo=?, utilidad=?, precio_V=?, id_Proveedor=?, proveedor=?, stock_minimo=?, stock_maximo=? WHERE idProcucto =?";

        try {
            ps = con.prepareStatement(sentenciaSQL);
            ps.setString(1, producto.getDescripcion());
            ps.setDouble(2, producto.getCosto());
            ps.setDouble(3, producto.getUtilidad());
            ps.setDouble(4, producto.getPrecioVenta());
            ps.setInt(5, producto.getIdProveedor());
            ps.setString(6, producto.getProveedor());
            ps.setInt(7, producto.getStockMinimo());
            ps.setInt(8, producto.getStockMaximo());
            ps.setInt(9, producto.getIdProducto());

            ps.execute();
            JOptionPane.showMessageDialog(null, "Producto modificado correctamente");
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo modificar el producto" + ex.getMessage());
            return false;
        } finally {
            closeConnection(con);
        }
    }
    
    public boolean eliminarProducto(Productos producto) {
        Connection con = getConnection();
        sentenciaSQL = "UPDATE  tbl_productos SET estado=? WHERE idProcucto=?";
        
        try {
            ps = con.prepareStatement(sentenciaSQL);
            ps.setString(1, "Inactivo");
            ps.setInt(2, producto.getIdProducto());
            ps.execute();
            JOptionPane.showMessageDialog(null, "producto desactivado correctamente");
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "producto no ha sido desactivado" + ex.getMessage());
            return false;
        }finally {
            closeConnection(con);
        }
    }
    
    public boolean tieneExistencia(int idProducto) {
    Connection con = getConnection();
    sentenciaSQL = "SELECT COUNT(*) FROM tbl_inventario WHERE idProcucto = ? AND cantidad > 0";

    try {
        ps = con.prepareStatement(sentenciaSQL);
        ps.setInt(1, idProducto);
        rs = ps.executeQuery();

        if (rs.next()) {
            int cantidad = rs.getInt(1);
            return cantidad > 0;
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al verificar existencia del producto: " + ex.getMessage());
    } finally {
        closeConnection(con);
    }

    return false;
}

}
