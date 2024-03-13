/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Clientes;
import modelo.ConsultaBD;
import vista.frm_Clientes;

/**
 *
 * @author ammcp
 */
public class ClienteControlador implements ActionListener {
    
    private Clientes cliente;
    private frm_Clientes form;
    private ConsultaBD conDB;
    private Object datos[] = new Object[4];
    DefaultTableModel modelo;
    

    public ClienteControlador(Clientes cliente, frm_Clientes form, ConsultaBD conDB) {
        this.cliente = cliente;
        this.form = form;
        this.conDB = conDB;
        this.form.btnCrear.addActionListener(this);
        this.form.btnLeer.addActionListener(this);
        this.form.btn_buscarCodigo.addActionListener(this);
        this.form.btnModificar.addActionListener(this);
        this.form.btnEliminar.addActionListener(this);
        this.form.btnLimpiar.addActionListener(this);
        this.form.btnSalir.addActionListener(this);
    }
    
    public void llenarTabla(){
        modelo = (DefaultTableModel) form.tbl_registrosClientes.getModel();
        modelo.setRowCount(0);
        int registros = conDB.leerTodosClientes().size();
        for(int i = 0; i < registros; i++){
            datos[0] = conDB.leerTodosClientes().get(i).getCodigo();
            datos[1] = conDB.leerTodosClientes().get(i).getNombre();
            datos[2] = conDB.leerTodosClientes().get(i).getDireccion();
            datos[3] = conDB.leerTodosClientes().get(i).getTelefono();
            modelo.addRow(datos);
        }
        form.tbl_registrosClientes.setModel(modelo);
    }
    
    public void limpiar(){
        form.txtCodigo.setText("0");
        form.txtNombre.setText(null);
        form.txtDireccion.setText(null);
        form.txtTelefono.setText(null);
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
        //BOTON CREAR
        if(e.getSource()==form.btnCrear){
            cliente.setCodigo(Integer.parseInt(form.txtCodigo.getText()));
            cliente.setNombre(form.txtNombre.getText());
            cliente.setDireccion(form.txtDireccion.getText());
            cliente.setTelefono(form.txtTelefono.getText());
            cliente.setEstado("Activo");
            if(conDB.crearClientes(cliente)){
                JOptionPane.showMessageDialog(null, "CLIENTE CREADO");
                limpiar();
            }
            else{
                JOptionPane.showMessageDialog(null, "NO SE PUDO CREAR CLIENTE");
            }
                  
        }
        //BOTON LEER TODOS
        if(e.getSource()==form.btnLeer){
            
           llenarTabla();
        }
        
         //BOTON BUSCAR CODIGO
        if(e.getSource()==form.btn_buscarCodigo){
            
           cliente.setCodigo(Integer.parseInt(form.txtCodigo.getText()));
           if(conDB.buscarCliente(cliente)){
               JOptionPane.showMessageDialog(null, "Cliente Encontrado");
               form.txtNombre.setText(cliente.getNombre());
               form.txtDireccion.setText(cliente.getDireccion());
               form.txtTelefono.setText(cliente.getTelefono());
           }else{
               JOptionPane.showMessageDialog(null, "Cliente no encontrado");
           }
        }
        //boton actualizar
        if(e.getSource()==form.btnModificar){
            
            cliente.setCodigo(Integer.parseInt(form.txtCodigo.getText()));
            cliente.setNombre(form.txtNombre.getText());
            cliente.setDireccion(form.txtDireccion.getText());
            cliente.setTelefono(form.txtTelefono.getText());
            if(conDB.modificarClientes(cliente)){
                JOptionPane.showMessageDialog(null, "CLIENTE ACUALIZADO");
                limpiar();
            }
            else{
                JOptionPane.showMessageDialog(null, "NO SE PUDO ACTUALIZAR CLIENTE");
            }
        }
        //boton eliminar
        if(e.getSource()==form.btnEliminar){
            
            cliente.setCodigo(Integer.parseInt(form.txtCodigo.getText()));
            if(conDB.eliminarClientes(cliente)){
                JOptionPane.showMessageDialog(null, "CLIENTE ELIMINADO");
                limpiar();
            }
            else{
                JOptionPane.showMessageDialog(null, "NO SE PUDO ELIMINAR CLIENTE");
            }
        }
        
        if(e.getSource()==form.btnLimpiar){
           limpiar();
        }
        
        if(e.getSource()==form.btnSalir){
           this.form.dispose();
        }
    }
    
}
