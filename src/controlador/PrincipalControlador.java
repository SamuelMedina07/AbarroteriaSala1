/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.frm_Clientes;
import vista.frm_Inventario;
import vista.frm_Principal;
import vista.frm_Productos;
import vista.frm_Proveedores;
import vista.frm_Ventas;

/**
 *
 * @author ammcp
 */
public class PrincipalControlador implements ActionListener{
    private frm_Principal formPrin;
    private frm_Clientes frmCliente;
    private frm_Proveedores frmPreveedores;
    private frm_Productos frmProducto;
    private frm_Inventario frmInventario;
     private frm_Ventas frmventas;

    public PrincipalControlador(frm_Principal formPrin, frm_Clientes frmCliente, frm_Proveedores frmProveedor,frm_Productos frmProducto,frm_Inventario frmInventario,frm_Ventas frmventas) {
        this.formPrin = formPrin;
        this.frmCliente = frmCliente;
        this.frmPreveedores = frmProveedor;
        this.frmProducto = frmProducto;
        this.frmInventario = frmInventario;
        this.frmventas = frmventas;
        
        this.formPrin.btnClientes.addActionListener(this);
        this.formPrin.btnProveedores.addActionListener(this);
        this.formPrin.btnProductos.addActionListener(this);
        this.formPrin.btnSalir.addActionListener(this);
        this.frmProducto.btnInventario.addActionListener(this);
         this.formPrin.btnVentas.addActionListener(this);
    }
    
    public void inciar(){
        formPrin.setLocationRelativeTo(null);
        frmCliente.setLocationRelativeTo(null);
        frmPreveedores.setLocationRelativeTo(null);
        frmProducto.setLocationRelativeTo(null);
        frmInventario.setLocationRelativeTo(null);
        frmventas.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==formPrin.btnClientes){
            frmCliente.lblUsuario.setText(formPrin.lblUsuario.getText());
            frmCliente.setVisible(true);
        }
        if(e.getSource()==formPrin.btnSalir){
            System.exit(0);
        }
        //Proveedores
        if(e.getSource()==formPrin.btnProveedores){
            frmPreveedores.lblUsuario.setText(formPrin.lblUsuario.getText());
            frmPreveedores.setVisible(true);
        }
        
        if(e.getSource()==formPrin.btnProductos){
            frmProducto.lblUsuario.setText(formPrin.lblUsuario.getText());
            frmProducto.setVisible(true);
        }
        
        if(e.getSource()==frmProducto.btnInventario){
            frmInventario.lblUsuario.setText(formPrin.lblUsuario.getText());
            frmInventario.setVisible(true);
        }
         if(e.getSource()==formPrin.btnVentas){
           // frmInventario.lblUsuario.setText(formPrin.lblUsuario.getText());
            frmventas.setVisible(true);
         }
    }
    
    
    
}
