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

public class ConsultaInventario extends Conexion {

    String sentenciaSQL;
    PreparedStatement ps = null;
    ResultSet rs;
    Inventario inventario;

    public boolean registrarMovimiento(Inventario inventario) {
        Connection con = getConnection();
        sentenciaSQL = "INSERT INTO tbl_inventario (idProcucto, fechaMovimiento, tipoMovimiento, cantidad) VALUES (?, ?, ?, ?)";

        try {
            ps = con.prepareStatement(sentenciaSQL);
            ps.setInt(1, inventario.getIdProducto());
            ps.setDate(2, new java.sql.Date(inventario.getFechaMovimiento().getTime()));
            ps.setString(3, inventario.getTipoMovimiento());
            ps.setInt(4, inventario.getCantidad());
            /*
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
             */ ps.execute();
            JOptionPane.showMessageDialog(null, "inventario registrado correctamente");
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            closeConnection(con);
        }
    }

    public String obtenerDescripcionProducto(int idProducto) {
        String descripcionProducto = null;
        Connection con = getConnection();
        sentenciaSQL = "SELECT descripcion FROM tbl_productos WHERE idProcucto = ?";

        try {
            ps = con.prepareStatement(sentenciaSQL);
            ps.setInt(1, idProducto);
            rs = ps.executeQuery();

            if (rs.next()) {
                descripcionProducto = rs.getString("descripcion");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo obtener la descripción del producto: " + ex.getMessage());
        } finally {
            closeConnection(con);
        }

        return descripcionProducto;
    }

    public ArrayList<Inventario> leerTodoInventario() {
        ArrayList<Inventario> listaInventario = new ArrayList<>();
        Connection con = getConnection();
        sentenciaSQL = "SELECT * FROM tbl_inventario WHERE tipoMovimiento = 'Entrada'";

        try {
            ps = con.prepareStatement(sentenciaSQL);
            rs = ps.executeQuery();

            while (rs.next()) {
                inventario = new Inventario();
                inventario.setIdInventario(rs.getInt(1));
                inventario.setIdProducto(rs.getInt(2));
                inventario.setFechaMovimiento(rs.getDate(3));
                inventario.setTipoMovimiento(rs.getString(4));
                inventario.setCantidad(rs.getInt(5));

                listaInventario.add(inventario);
            }
            closeConnection(con);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo leer los productos" + ex.getMessage());
        }
        return listaInventario;
    }

    /*
    public ArrayList<Inventario> obtenerProductosConInventario() {
        ArrayList<Inventario> listaInventario = new ArrayList<>();
        Connection con = getConnection();
        sentenciaSQL = "SELECT I.idInventario, I.idProducto, I.fechaMovimiento, I.tipoMovimiento, I.cantidad, P.descripcion "
                + "FROM tbl_inventario I "
                + "JOIN tbl_productos P ON I.idProducto = P.idProducto";

        try (PreparedStatement ps = con.prepareStatement(sentenciaSQL);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                inventario = new Inventario();
                inventario.setIdInventario(rs.getInt("idInventario"));
                inventario.setIdProducto(rs.getInt("idProCucto"));
                inventario.setFechaMovimiento(rs.getDate("fechaMovimiento"));
                inventario.setTipoMovimiento(rs.getString("tipoMovimiento"));
                inventario.setCantidad(rs.getInt("cantidad"));

                listaInventario.add(inventario);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(con);
        }

        return listaInventario;
    }
     */
    public ArrayList<Productos> obtenerProductosSinInventario() {
        ArrayList<Productos> productosSinInventario = new ArrayList<>();
        Connection con = getConnection();
        sentenciaSQL = "SELECT * FROM tbl_productos WHERE idProcucto NOT IN (SELECT idProcucto FROM tbl_inventario)";

        try {
            ps = con.prepareStatement(sentenciaSQL);
            rs = ps.executeQuery();

            while (rs.next()) {
                Productos producto = new Productos();
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

                productosSinInventario.add(producto);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo obtener los productos sin inventario: " + ex.getMessage());
        } finally {
            closeConnection(con);
        }

        return productosSinInventario;
    }

    //CONSULTAS PARA VALIDACIONES: 
    public boolean yaExisteInventario(int idProducto) {
        Connection con = getConnection();
        sentenciaSQL = "SELECT COUNT(*) FROM tbl_inventario WHERE idProcucto = ?";

        try {
            ps = con.prepareStatement(sentenciaSQL);
            ps.setInt(1, idProducto);
            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Retorna true si ya existe, false si no existe
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al verificar inventario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            closeConnection(con);
        }

        return false; // En caso de error, retornar false por precaución
    }

    public int obtenerCantidadActualInventario(int idProducto) {
        Connection con = getConnection();
        sentenciaSQL = "SELECT cantidad FROM tbl_inventario WHERE idProcucto = ?";

        try {
            ps = con.prepareStatement(sentenciaSQL);
            ps.setInt(1, idProducto);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener cantidad en inventario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            closeConnection(con);
        }

        return 0; // En caso de error, retornar 0 por precaución
    }

}
