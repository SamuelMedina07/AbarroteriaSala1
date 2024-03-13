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
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Clientes;
import modelo.ConsultaBD;
import modelo.ConsultaInventario;
import modelo.ConsultaProductos;
import modelo.ConsultaVentas;
import modelo.Inventario;
import modelo.Productos;
import modelo.Ventas;
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
     private Ventas venta;
     private ConsultaVentas consVentas;
     private ConsultaInventario consInventario;
     private Inventario inventario;
      double precio;
        int cantidad;
        int v=1;
    
    public VentasControlador(Ventas venta,Clientes cliente, frm_Ventas form, ConsultaBD conDB,Productos producto,ConsultaProductos consProductos,ConsultaVentas consVentas,Inventario inventario, ConsultaInventario consInventario) {
       
        this.venta=venta;
        this.cliente = cliente;
        this.producto = producto;
        this.inventario = inventario;
        this.form = form;
        this.conDB = conDB;
        this.consProductos = consProductos;
        this.consVentas= consVentas;
        this.consInventario = consInventario;
        this.form.btn_buscarCodigo.addActionListener(this);
        this.form.btnSalir.addActionListener(this);
        this.form.btn_buscarProducto.addActionListener(this);
        this.form.btn_AgregarEnTabla.addActionListener(this);
        this.form.btn_GenerarVenta.addActionListener(this);
        fecha();
        
    }
    
    public void limpiar(){
        String t="0";
        form.txtCodigo.setText(t);
        form.txtNombre.setText(t);
        form.txtCodigoProducto.setText(t);
        form.txtDescripcion.setText(t);
        form.txtCosto.setText(t);
        form.txtStock.setText(t);
        form.txt_NumeroSerie.setText(t);
         form.txtCantidad.setValue(0);
        form.txtFecha.setText(t);
        form.txtImpuesto.setText(t);
        form.txtTotalPagar.setText(t);
        
        
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
    double impuesto;
    impuesto=0;
    double subtotal;
    subtotal=0;
    
    for(int i=0;i<form.tbl_registroFactura.getRowCount();i++)
    {
    cantidad=(int) Double.parseDouble(form.tbl_registroFactura.getValueAt(i,3).toString());
     precio=(int) Double.parseDouble(form.tbl_registroFactura.getValueAt(i,4).toString());
    subtotal=(cantidad*precio);
     impuesto=impuesto+(subtotal*0.18);
     tpagar=tpagar+(subtotal+impuesto);
    }
    form.txtImpuesto.setText(""+impuesto);
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
               form.txtCosto.setText(String.valueOf(producto.getPrecioVenta()));
               form.txtStock.setText(10+"");
               
               
               
               
         
           }else{
               JOptionPane.showMessageDialog(null, "Producto no encontrado");
           }
        }
        
          if(e.getSource()==form.btn_AgregarEnTabla){
           agregarproducto();
        }
          
          //boton generar venta
           if(e.getSource()==form.btn_GenerarVenta){
               
               
        
           
           // venta.setId(Integer.parseInt(form.txtCodigo.getText())); // arreglar por que lo puse repetido
            venta.setSerie(form.txt_NumeroSerie.getText());
            venta.setIdCliente(Integer.parseInt(form.txtCodigo.getText())); 
            venta.setFecha(form.txtFecha.getText());
            venta.setMonto(Double.parseDouble(form.txtTotalPagar.getText()));
            venta.setEstado("Aprobado");
            
            
            if(consVentas.registrarVenta(venta)){
                JOptionPane.showMessageDialog(null, "FACTURA CREADO");
                
            }
            else{
                JOptionPane.showMessageDialog(null, "FACTURA NO APROBADA");
            }
                  
        
        }
       
        //boton eliminar
        
        if(e.getSource()==form.btnSalir){
           limpiar();
        }
        
        if(e.getSource()==form.btnSalir){
           this.form.dispose();
        }
    }
    
}
