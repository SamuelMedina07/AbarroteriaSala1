/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.ConsultaProductos;
import modelo.ConsultaProveedores;
import modelo.Productos;
import modelo.Proveedores;
import vista.frm_Consulta_Producto;
import vista.frm_Productos;

/**
 *
 * @author ammcp
 */
public class ProductoControlador implements ActionListener {

    private Productos producto;
    private ConsultaProductos consProductos;
    private Object datos[] = new Object[10];
    private ConsultaProveedores conProveedores;
    private frm_Productos formProductos;
    private frm_Consulta_Producto formConsProducto;
    double utilidadPorcentaje = 45.0;
    double utilidad = 0;
    DefaultTableModel modelo;

    public ProductoControlador(Productos producto, frm_Productos form, ConsultaProductos consProductos, ConsultaProveedores consProv, frm_Consulta_Producto formConsProducto) {
        this.producto = producto;
        this.formProductos = form;
        this.consProductos = consProductos;
        this.conProveedores = consProv;
        this.formConsProducto = formConsProducto;

        this.formProductos.btnCrear.addActionListener(this);
        this.formProductos.btnLeer.addActionListener(this);
        this.formProductos.btn_buscarProducto.addActionListener(this);
        this.formProductos.btn_actualizarProveedores.addActionListener(this);
        this.formProductos.btnConsultarProductos.addActionListener(this);
        this.formConsProducto.btn_buscarProducto.addActionListener(this);
        this.formProductos.btnModificar.addActionListener(this);
        this.formProductos.btnEliminar.addActionListener(this);
        this.formProductos.btnLimpiar.addActionListener(this);

        formProductos.cbProveedores.addActionListener(this);
        formProductos.txtCosto.addActionListener(this);
        formConsProducto.tbl_registrosProductos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int filaSeleccionada = formConsProducto.tbl_registrosProductos.getSelectedRow();
                DefaultTableModel model = (DefaultTableModel) formConsProducto.tbl_registrosProductos.getModel();
                if (filaSeleccionada >= 0){
                String idProducto = String.valueOf(model.getValueAt(filaSeleccionada, 0));
                Productos productoSeleccionado = consProductos.obtenerProductoSegunIdProducto(Integer.parseInt(idProducto));
                formProductos.txtCodigo.setText(String.valueOf(productoSeleccionado.getIdProducto()));
                formProductos.cbProveedores.removeAllItems();
                JOptionPane.showMessageDialog(null, "Producto Encontrado");
                formProductos.txtDescripcion.setText(productoSeleccionado.getDescripcion());
                formProductos.txtCosto.setText(String.valueOf(productoSeleccionado.getCosto()));
                utilidad = productoSeleccionado.getUtilidad();
                formProductos.txtPrecio.setText(String.valueOf(productoSeleccionado.getPrecioVenta()));
                formProductos.cbProveedores.addItem(conProveedores.obtenerProveedorSegunIdProveedor(productoSeleccionado.getIdProveedor()));
                formProductos.txtMin.setText(String.valueOf(productoSeleccionado.getStockMinimo()));
                formProductos.txtMax.setText(String.valueOf(productoSeleccionado.getStockMaximo()));
                formConsProducto.dispose();
                }
            }
        });
        formProductos.txtCosto.setToolTipText("Presione enter para calcular el precio de vetna");

        actualizarComboboxProveedores();

    }

    //Llenar Combobox con los proveedores
    public void llenarComboBoxProveedores(JComboBox<Proveedores> combobox) {
        // Obtén la lista de todos los proveedores desde la base de datos
        ArrayList<Proveedores> listaProveedores = conProveedores.leerTodosProveedores();

        // Limpia el ComboBox antes de agregar nuevos elementos
        combobox.removeAllItems();

        // Llena el ComboBox con los nombres de los proveedores
        for (Proveedores proveedor : listaProveedores) {
            combobox.addItem(proveedor);
        }
    }

    //Metodo para ejecutar la accion de llenar el combobox
    public void actualizarComboboxProveedores() {
        llenarComboBoxProveedores(formProductos.cbProveedores);
    }

    //Calcular el precio de venta
    public void calcularPrecioVenta() {
        try {
            double costo = Double.parseDouble(formProductos.txtCosto.getText());
            utilidad = costo * (utilidadPorcentaje / 100);
            double precioVenta = costo + utilidad;
            //Mostrar en el txt
            formProductos.txtPrecio.setText(String.format("%.2f", precioVenta));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            formProductos.txtCosto.setText(""); // Limpiar el campo de costo

        }

    }

    public void resaltarCampoVacio(JTextField campo) {
        Color colorResaltado = new Color(219, 52, 52);
        campo.setBackground(colorResaltado);
    }

    public boolean validarCamposNoVacios() {
        boolean camposValidos = true;
        /* if (formProductos.txtCodigo.getText().trim().isEmpty()) {
            resaltarCampoVacio(formProductos.txtCodigo);
            camposValidos=false;
        }*/
        if (formProductos.txtDescripcion.getText().trim().isEmpty()) {
            resaltarCampoVacio(formProductos.txtDescripcion);
            camposValidos = false;
        }
        if (formProductos.txtCosto.getText().trim().isEmpty()) {
            resaltarCampoVacio(formProductos.txtCosto);
            camposValidos = false;
        }
        if (formProductos.txtPrecio.getText().trim().isEmpty()) {
            resaltarCampoVacio(formProductos.txtPrecio);
            camposValidos = false;
        }
        if (formProductos.txtMin.getText().trim().isEmpty()) {
            resaltarCampoVacio(formProductos.txtMin);
            camposValidos = false;
        }
        if (formProductos.txtMax.getText().trim().isEmpty()) {
            resaltarCampoVacio(formProductos.txtMax);
            camposValidos = false;
        }
        if (!camposValidos) {
            JOptionPane.showMessageDialog(null, "No se pueden dejar campos vacios.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return camposValidos;
    }

    public boolean validarStockMinMax() {
        int stockMinimo = Integer.parseInt(formProductos.txtMin.getText());
        int stockMaximo = Integer.parseInt(formProductos.txtMax.getText());

        if (stockMaximo < stockMinimo) {
            JOptionPane.showMessageDialog(null, "La cantidad maximo no puede ser menor  que la cantidad minima.", "ERROR", JOptionPane.ERROR_MESSAGE);
            formProductos.txtMax.setText("");
            return false;
        }
        return true;
    }

    public void consultarProductos() {
        String campoSeleccionado = (String) formConsProducto.cbBuscarPor.getSelectedItem();
        String campoBuscar = formConsProducto.txtBuscar.getText();

        switch (campoSeleccionado) {
            case "ID":
                try {
                    llenarTablaConsulta(consProductos.buscarProductosPoriD(Integer.parseInt(campoBuscar)));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Ingrese valor id válidos.", "Error", JOptionPane.ERROR_MESSAGE);
                    formConsProducto.txtBuscar.setText("");

                }

                break;
            case "DESCRIPCION":
                llenarTablaConsulta(consProductos.buscarProductosPorDecripcion(campoBuscar));
                break;
            case "PROVEEDOR":
                llenarTablaConsulta(consProductos.buscarProductosPorProveedor(campoBuscar));
                break;
            case "ACTIVOS":
                llenarTablaConsulta(consProductos.buscarProductosActivos());
                break;
        }

    }

    public void limpiar() {
        formProductos.txtCodigo.setText("0");
        formProductos.txtDescripcion.setText(null);
        formProductos.txtCosto.setText(null);
        formProductos.txtPrecio.setText(null);
        formProductos.txtMax.setText(null);
        formProductos.txtMin.setText(null);
        actualizarComboboxProveedores();
        modelo = (DefaultTableModel) formProductos.tbl_registrosProductos.getModel();
        modelo.setRowCount(0);
    }

    public void guardarProducto() {

        if (validarCamposNoVacios() && validarStockMinMax()) {
            producto.setDescripcion(formProductos.txtDescripcion.getText());
            producto.setCosto(Double.parseDouble(formProductos.txtCosto.getText()));
            producto.setUtilidad(utilidad);
            producto.setPrecioVenta(Double.parseDouble(formProductos.txtPrecio.getText()));

            Proveedores proveedorSeleccionado = (Proveedores) formProductos.cbProveedores.getSelectedItem();

            producto.setIdProveedor(proveedorSeleccionado.getCodigo());
            producto.setProveedor(proveedorSeleccionado.getNombre());
            producto.setStockMinimo(Integer.parseInt(formProductos.txtMin.getText()));
            producto.setStockMaximo(Integer.parseInt(formProductos.txtMax.getText()));
            producto.setEstado("Activo");
            if (consProductos.crearProducto(producto)) {
                limpiar();
            }
        }
    }

    public void llenarTabla() {
        modelo = (DefaultTableModel) formProductos.tbl_registrosProductos.getModel();
        modelo.setRowCount(0);
        // Obtener la lista de todos los productos una vez
        ArrayList<Productos> listaProductos = consProductos.leerTodosProductos();
        int registros = listaProductos.size();

        for (int i = 0; i < registros; i++) {
            Productos productoTemporal = listaProductos.get(i);

            datos[0] = productoTemporal.getIdProducto();
            datos[1] = productoTemporal.getDescripcion();
            datos[2] = productoTemporal.getCosto();
            datos[3] = productoTemporal.getUtilidad();
            datos[4] = productoTemporal.getPrecioVenta();
            datos[5] = conProveedores.obtenerNnombreSegunIdProveedor(productoTemporal.getIdProveedor());
            datos[6] = productoTemporal.getStockMinimo();
            datos[7] = productoTemporal.getStockMaximo();

            modelo.addRow(datos);
        }
        formProductos.tbl_registrosProductos.setModel(modelo);
    }

    public void llenarTablaConsulta(ArrayList<Productos> listaProductos) {
        modelo = (DefaultTableModel) formConsProducto.tbl_registrosProductos.getModel();
        modelo.setRowCount(0);
        int registros = listaProductos.size();

        for (int i = 0; i < registros; i++) {
            Productos productoTemporal = listaProductos.get(i);

            datos[0] = productoTemporal.getIdProducto();
            datos[1] = productoTemporal.getDescripcion();
            datos[2] = productoTemporal.getCosto();
            datos[3] = productoTemporal.getUtilidad();
            datos[4] = productoTemporal.getPrecioVenta();
            datos[5] = conProveedores.obtenerNnombreSegunIdProveedor(productoTemporal.getIdProveedor());
            datos[6] = productoTemporal.getStockMinimo();
            datos[7] = productoTemporal.getStockMaximo();

            modelo.addRow(datos);
        }
        formConsProducto.tbl_registrosProductos.setModel(modelo);
    }

    public void buscarProducto() {
        try {
            producto.setIdProducto(Integer.parseInt(formProductos.txtCodigo.getText()));
            if (consProductos.buscarProducto(producto)) {
                formProductos.cbProveedores.removeAllItems();
                JOptionPane.showMessageDialog(null, "Producto Encontrado");
                formProductos.txtDescripcion.setText(producto.getDescripcion());
                formProductos.txtCosto.setText(String.valueOf(producto.getCosto()));
                utilidad = producto.getUtilidad();
                formProductos.txtPrecio.setText(String.valueOf(producto.getPrecioVenta()));
                formProductos.cbProveedores.addItem(conProveedores.obtenerProveedorSegunIdProveedor(producto.getIdProveedor()));
                formProductos.txtMin.setText(String.valueOf(producto.getStockMinimo()));
                formProductos.txtMax.setText(String.valueOf(producto.getStockMaximo()));
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            formProductos.txtCodigo.setText(""); // Limpiar el campo de costo

        }

    }

    /*
    INVENTARIO
    para hacer una salida de un inventario, por ejemplo de un producto vencido
    clave de administrado,justificada, observacion, saida de inventario, quien autorizo la saida
    
     */
    public void modificarProductoa() {
        if (validarCamposNoVacios() && validarStockMinMax()) {
            producto.setIdProducto(Integer.parseInt(formProductos.txtCodigo.getText()));
            producto.setDescripcion(formProductos.txtDescripcion.getText());
            producto.setCosto(Double.parseDouble(formProductos.txtCosto.getText()));
            producto.setUtilidad(utilidad);
            producto.setPrecioVenta(Double.parseDouble(formProductos.txtPrecio.getText()));

            Proveedores proveedorSeleccionado = (Proveedores) formProductos.cbProveedores.getSelectedItem();

            producto.setIdProveedor(proveedorSeleccionado.getCodigo());
            producto.setProveedor(proveedorSeleccionado.getNombre());
            producto.setStockMinimo(Integer.parseInt(formProductos.txtMin.getText()));
            producto.setStockMaximo(Integer.parseInt(formProductos.txtMax.getText()));
            if (consProductos.modificarProducto(producto)) {
                limpiar();
            }
        }
    }

    public void eliminarProductoa() {

        //si tiene existencia no se puede eliminar
        int idProducto = Integer.parseInt(formProductos.txtCodigo.getText());
        if (consProductos.tieneExistencia(idProducto)) {
            JOptionPane.showMessageDialog(null, "No se puede eliminar el producto porque tiene existencia.");
            producto.setIdProducto(idProducto);
        } else {
            int confirmacion = JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminar el producto?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                // Cambiar el estado del producto a "Inactivo" 
                if (consProductos.eliminarProducto(producto)) {
                    JOptionPane.showMessageDialog(null, "Producto eliminado exitosamente.");
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar el producto.");
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //
        if (e.getSource() == formProductos.btnConsultarProductos) {
            formConsProducto.setVisible(true);
        }
        if (e.getSource() == formConsProducto.btn_buscarProducto) {
            consultarProductos();
        }
        //BOTON ACTUALIZAR PROVEEDORES
        if (e.getSource() == formProductos.btn_actualizarProveedores) {
            actualizarComboboxProveedores();
        }

        if (e.getSource() == formProductos.txtCosto) {
            calcularPrecioVenta();
        }

        if (e.getSource() == formProductos.btnCrear) {
            guardarProducto();
        }

        // BOTÓN LEER TODOS
        if (e.getSource() == formProductos.btnLeer) {
            llenarTabla();
        }

        //BUSCAR PRODUCTO
        if (e.getSource() == formProductos.btn_buscarProducto) {
            buscarProducto();
        }

        if (e.getSource() == formProductos.btnModificar) {
            modificarProductoa();
        }

        if (e.getSource() == formProductos.btnLimpiar) {
            limpiar();
        }
        
        
    }
}
