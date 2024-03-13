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
import javax.swing.JOptionPane;

/**
 *
 * @author Samuel
 */
public class ConsultaVentas extends Conexion {
    
     PreparedStatement ps = null;
    String sentenciaSQL;
    ResultSet rs;
    Ventas venta;
    
    public boolean registrarMovimiento(Ventas venta) {
        Connection con = getConnection();
        sentenciaSQL = "INSERT INTO tbl_ventas (idVentas, numeroSerie, idCliente, FechaVenta, Monto, Estado) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            ps = con.prepareStatement(sentenciaSQL);
            ps.setInt(1, venta.getId());
            ps.setString(2, venta.getSerie());
            ps.setInt(3, venta.getIdCliente());
            ps.setString(4, venta.getFecha());
            ps.setDouble(5, venta.getMonto());
           ps.setString(6, venta.getEstado());
            
            
            /*
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
             */ ps.execute();
            JOptionPane.showMessageDialog(null, "venta registrado correctamente");
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            closeConnection(con);
        }
    }
    
//     public void setearFacturaCompleto(ResultSet rs,Ventas venta) throws SQLException {
//        venta.setId(rs.getInt(1));
//        venta.setIdCliente(rs.getInt(2));
//        venta.setSerie(rs.getString(3));
//        venta.setFecha(rs.getString(4));
//        venta.setMonto(rs.getDouble(5));
//        venta.setEstado(rs.getString(6));
//        
//    }
    
}
