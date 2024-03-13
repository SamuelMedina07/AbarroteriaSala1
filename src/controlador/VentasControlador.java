/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.time.LocalDate;
import java.util.ArrayList;

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
      double precio;
        int cantidad;
    
    public VentasControlador(Clientes cliente, frm_Ventas form, ConsultaBD conDB,Productos producto,ConsultaProductos consProductos) {
       
        this.cliente = cliente;
        this.producto = producto;
        this.form = form;
        this.conDB = conDB;
        this.consProductos = consProductos;
        this.form.btn_buscarCodigo.addActionListener(this);
        this.form.btnLimpiar.addActionListener(this);
        this.form.btn_buscarProducto.addActionListener(this);
        this.form.btn_AgregarEnTabla.addActionListener(this);
        fecha();
        
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
    
    public void agregarproducto()
    {
        int item=0;
        item=item+1;
        int idp;
        String descripcionproducto;
       
        int stock;
    double total;
    
    modelo =(DefaultTableModel)form.tbl_registroFactura.getModel();
    
    
    idp= Integer.parseInt(form.txtCodigoProducto.getText());
    descripcionproducto= form.txtDescripcion.getText();
    precio = Double.parseDouble(form.txtCosto.getText());
    cantidad = Integer.parseInt(form.txtCantidad.getValue().toString());
    stock=Integer.parseInt(form.txtStock.getText());
    total=precio*cantidad;
    
    ArrayList lista = new ArrayList();
    if(stock > 0)
    {
    lista.add(item);
    lista.add(idp);
    lista.add(descripcionproducto);
    lista.add(cantidad);
    lista.add(precio);
    lista.add(total);
    Object[] ob = new Object[6];
    ob[0]=lista.get(0);
    ob[1]=lista.get(1);
    ob[2]=lista.get(2);
    ob[3]=lista.get(3);
    ob[4]=lista.get(4);
    ob[5]=lista.get(5);
    modelo.addRow(ob);
    form.tbl_registroFactura.setModel(modelo);
    calcularTotal();
            }
    else 
    {
    JOptionPane.showMessageDialog(null,"PRODUCTO NO DISPONIBLE EN STOCK");
    }
    

    }
    
    public void calcularTotal()
    {
    double tpagar;
    tpagar=0;
    
    for(int i=0;i<form.tbl_registroFactura.getRowCount();i++)
    {
    cantidad=(int) Double.parseDouble(form.tbl_registroFactura.getValueAt(i,3).toString());
     precio=(int) Double.parseDouble(form.tbl_registroFactura.getValueAt(i,4).toString());
     tpagar=tpagar+(cantidad*precio);
    }
    form.txtTotalPagar.setText(""+tpagar);
    }
    
    public void fecha()
    {
        java.sql.Date fechaSQL = java.sql.Date.valueOf(LocalDate.now());
    form.txtFecha.setText(fechaSQL+"");
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
        
          if(e.getSource()==form.btn_AgregarEnTabla){
           agregarproducto();
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
