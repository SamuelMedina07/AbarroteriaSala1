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
import modelo.ConsultaProveedores;
import modelo.Proveedores;
import vista.frm_Proveedores;

/**
 *
 * @author ammcp
 */
public class ProveedorControlador implements ActionListener {
    
    private Proveedores proveedor;
    private frm_Proveedores form;
    private ConsultaProveedores conP;
    private Object datos[] = new Object[4];
    DefaultTableModel modelo;
    

    public ProveedorControlador(Proveedores proveedor, frm_Proveedores form, ConsultaProveedores conP) {
        this.proveedor = proveedor;
        this.form = form;
        this.conP = conP;
        this.form.btnCrear.addActionListener(this);
        this.form.btnLeer.addActionListener(this);
        this.form.btn_buscarCodigo.addActionListener(this);
        this.form.btnModificar.addActionListener(this);
        this.form.btnEliminar.addActionListener(this);
        this.form.btnLimpiar.addActionListener(this);
        this.form.btnSalir.addActionListener(this);
    }
    
    public void llenarTabla(){
        modelo = (DefaultTableModel) form.tbl_registrosProveedores.getModel();
        modelo.setRowCount(0);
        int registros = conP.leerTodosProveedores().size();
        for(int i = 0; i < registros; i++){
            datos[0] = conP.leerTodosProveedores().get(i).getCodigo();
            datos[1] = conP.leerTodosProveedores().get(i).getNombre();
            datos[2] = conP.leerTodosProveedores().get(i).getDireccion();
            datos[3] = conP.leerTodosProveedores().get(i).getTelefono();
            modelo.addRow(datos);
        }
        form.tbl_registrosProveedores.setModel(modelo);
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
        modelo = (DefaultTableModel) form.tbl_registrosProveedores.getModel();
        modelo.setRowCount(0);
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //BOTON CREAR
        if(e.getSource()==form.btnCrear){
            proveedor.setCodigo(Integer.parseInt(form.txtCodigo.getText()));
            proveedor.setNombre(form.txtNombre.getText());
            proveedor.setDireccion(form.txtDireccion.getText());
            proveedor.setTelefono(form.txtTelefono.getText());
            proveedor.setEstado("Activo");
            
            if(conP.crearProveedor(proveedor)){
                JOptionPane.showMessageDialog(null, "Proveedor CREADO");
                limpiar();
            }
            else{
                JOptionPane.showMessageDialog(null, "NO SE PUDO CREAR Proveedor");
            }
                  
        }
        //BOTON LEER TODOS
        if(e.getSource()==form.btnLeer){
            
           llenarTabla();
        }
        
         //BOTON BUSCAR CODIGO
        if(e.getSource()==form.btn_buscarCodigo){
            
           proveedor.setCodigo(Integer.parseInt(form.txtCodigo.getText()));
           if(conP.buscarProveedor(proveedor)){
               JOptionPane.showMessageDialog(null, "Proveedor Encontrado");
               form.txtNombre.setText(proveedor.getNombre());
               form.txtDireccion.setText(proveedor.getDireccion());
               form.txtTelefono.setText(proveedor.getTelefono());
           }else{
               JOptionPane.showMessageDialog(null, "Proveedor no encontrado");
           }
        }
        
        if(e.getSource()==form.btnModificar){
            
            proveedor.setCodigo(Integer.parseInt(form.txtCodigo.getText()));
            proveedor.setNombre(form.txtNombre.getText());
            proveedor.setDireccion(form.txtDireccion.getText());
            proveedor.setTelefono(form.txtTelefono.getText());
            if(conP.modificarProveedor(proveedor)){
                JOptionPane.showMessageDialog(null, "PROVEEDOR ACUALIZADO");
                limpiar();
            }
            else{
                JOptionPane.showMessageDialog(null, "NO SE PUDO ACTUALIZAR PROVEEDOR");
            }
        }
        
        if(e.getSource()==form.btnEliminar){
            
            proveedor.setCodigo(Integer.parseInt(form.txtCodigo.getText()));
            if(conP.eliminarProveedor(proveedor)){
                JOptionPane.showMessageDialog(null, "PROVEEDOR ELIMINADO");
                limpiar();
            }
            else{
                JOptionPane.showMessageDialog(null, "NO SE PUDO ELIMINAR PROVEEDOR");
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
