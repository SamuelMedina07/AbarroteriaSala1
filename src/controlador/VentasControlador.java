/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Clientes;
import modelo.ConsultaBD;
import modelo.ConsultaProductos;
import modelo.Productos;
import vista.frm_Ventas;

/**
 *
 * @author Samuel
 */
public class VentasControlador implements ActionListener{
     private Clientes cliente;
    private frm_Ventas form;
    private ConsultaBD conDB;
    private Object datos[] = new Object[4];
    DefaultTableModel modelo;
    
     private Productos producto;
     private ConsultaProductos consProductos;
    
    public VentasControlador(Clientes cliente, frm_Ventas form, ConsultaBD conDB,Productos producto,ConsultaProductos consProductos) {
       
        this.cliente = cliente;
        this.producto = producto;
        this.form = form;
        this.conDB = conDB;
        this.consProductos = consProductos;
        this.form.btn_buscarCodigo.addActionListener(this);
        this.form.btnLimpiar.addActionListener(this);
        this.form.btn_buscarProducto.addActionListener(this);
        
    }
    
    public void limpiar(){
        form.txtCodigo.setText("0");
        
        /*
        int fila = form.tbl_registrosClientes.getRowCount();
        
        for(int i=fila-1;i>=0;i--){
        modelo.removeRow(fila);
    }
         */
        modelo.setRowCount(0);
    }
    
     @Override
    public void actionPerformed(ActionEvent e) {
      
        //BOTON LEER TODOS
        
         //BOTON BUSCAR CODIGO CLIENTE
        if(e.getSource()==form.btn_buscarCodigo){
            
           cliente.setCodigo(Integer.parseInt(form.txtCodigo.getText()));
           if(conDB.buscarCliente(cliente)){
               JOptionPane.showMessageDialog(null, "Cliente Encontrado");
               form.txtNombre.setText(cliente.getNombre());
         
           }else{
               JOptionPane.showMessageDialog(null, "Cliente no encontrado");
           }
        }
        
        //BOTON BUSCAR CODIGO PRODUCTO
        if(e.getSource()==form.btn_buscarProducto){
            
            
           producto.setIdProducto(Integer.parseInt(form.txtCodigoProducto.getText()));
           if(consProductos.buscarProducto(producto)){
               JOptionPane.showMessageDialog(null, "Producto Encontrado");
               form.txtDescripcion.setText(producto.getDescripcion());
               form.txtCosto.setText(String.valueOf(producto.getCosto()));
               
               
         
           }else{
               JOptionPane.showMessageDialog(null, "Producto no encontrado");
           }
        }
       
        //boton eliminar
        
        if(e.getSource()==form.btnLimpiar){
           limpiar();
        }
        
       // if(e.getSource()==form.btnSalir){
         //  this.form.dispose();
        //}
    }
    
}
