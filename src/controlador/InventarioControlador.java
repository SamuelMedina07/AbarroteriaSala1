/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import modelo.ConsultaInventario;
import modelo.ConsultaProductos;
import modelo.ConsultaProveedores;
import modelo.Inventario;
import modelo.Productos;
import vista.frm_Consulta_Producto;
import vista.frm_Inventario;

/**
 *
 * @author ammcp
 */
public class InventarioControlador implements ActionListener {

    private Inventario inventario;
    private ConsultaInventario consInventario;
    private frm_Inventario formInventario;
    private Object datos[] = new Object[10];
    Productos producto = new Productos();
    private ConsultaProveedores consProveedores = new ConsultaProveedores();
    private ConsultaProductos consProductos = new ConsultaProductos();
    private frm_Consulta_Producto formConsProducto;
    DefaultTableModel modelo;
    // Obtener la fecha actual
   Date fechaSQL = Date.valueOf(LocalDate.now());
   
    public InventarioControlador(Inventario inventario, ConsultaInventario consInventario, frm_Inventario formInventario, frm_Consulta_Producto formConsProducto) {
        this.inventario = inventario;
        this.consInventario = consInventario;
        this.formInventario = formInventario;
        this.formConsProducto = formConsProducto;

        inicializarEventos();

    }

    private void inicializarEventos() {
        this.formInventario.btnCrear.addActionListener(this);
        this.formInventario.btnLeer.addActionListener(this);
        this.formInventario.btn_buscarProducto.addActionListener(this);
        //para el formulario de consultas
        this.formInventario.btnConsultarProductos.addActionListener(this);
        this.formConsProducto.btn_buscarProducto.addActionListener(this);

        formConsProducto.tbl_registrosProductos.getSelectionModel().addListSelectionListener(this::manejarSeleccionTabla);
    }

    // OPERACIONES EN FORMULARIOS DE CONSULTAR PRODUCTOS *****************************************
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

    //TABLA DEL FORMULARIO DE CONSULTA PRODUCTOS
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
            datos[5] = consProveedores.obtenerNnombreSegunIdProveedor(productoTemporal.getIdProveedor());
            datos[6] = productoTemporal.getStockMinimo();
            datos[7] = productoTemporal.getStockMaximo();

            modelo.addRow(datos);
        }
        formConsProducto.tbl_registrosProductos.setModel(modelo);
    }

    //MANEJAR LO QUE PASA AL SELECCIONAR UNA FILA EN EL FORMULARIO DE CONSULTA
    public void manejarSeleccionTabla(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int filaSeleccionada = formConsProducto.tbl_registrosProductos.getSelectedRow();
            DefaultTableModel model = (DefaultTableModel) formConsProducto.tbl_registrosProductos.getModel();
            if (filaSeleccionada >= 0) {
                JOptionPane.showMessageDialog(null, "Producto Encontrado");
                //tambien puedo usar el metodo buscarProducto de la consulta para obtener un producto de un solo
                //como en el metodo buscarProducto de esta clase
                String idProducto = String.valueOf(model.getValueAt(filaSeleccionada, 0));
                Productos productoSeleccionado = consProductos.obtenerProductoSegunIdProducto(Integer.parseInt(idProducto));
                formInventario.txtCodigo.setText(String.valueOf(productoSeleccionado.getIdProducto()));
                formInventario.txtDescripcion.setText(productoSeleccionado.getDescripcion());
                formInventario.txtMin.setText(String.valueOf(productoSeleccionado.getStockMinimo()));
                formInventario.txtMax.setText(String.valueOf(productoSeleccionado.getStockMaximo()));
                formConsProducto.dispose();
            }
        }
    }

    //OPERACIONES EN FORMULARIO DE INVENTARIO****************************
    public void buscarProducto() {

        try {
            producto.setIdProducto(Integer.parseInt(formInventario.txtCodigo.getText()));
            if (consProductos.buscarProducto(producto)) {
                JOptionPane.showMessageDialog(null, "Producto Encontrado");
                formInventario.txtCodigo.setText(String.valueOf(producto.getIdProducto()));
                formInventario.txtDescripcion.setText(producto.getDescripcion());
                formInventario.txtMin.setText(String.valueOf(producto.getStockMinimo()));
                formInventario.txtMax.setText(String.valueOf(producto.getStockMaximo()));
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            formInventario.txtCodigo.setText(""); // Limpiar el campo de costo

        }

    }

    //LLENAR TABLA DE PRODUCTOS DEL INVENTARO PAARA EL BOTON LEER
    public void llenarTablaProductos() {
        modelo = (DefaultTableModel) formInventario.tbl_Productos.getModel();
        modelo.setRowCount(0);
        // Obtener la lista de todos los productos una vez
        ArrayList<Productos> listaProductos = consInventario.obtenerProductosSinInventario();
        int registros = listaProductos.size();

        for (int i = 0; i < registros; i++) {
            Productos productoTemporal = listaProductos.get(i);

            datos[0] = productoTemporal.getIdProducto();
            datos[1] = productoTemporal.getDescripcion();
            datos[2] = productoTemporal.getCosto();
            datos[3] = productoTemporal.getUtilidad();
            datos[4] = productoTemporal.getPrecioVenta();
            datos[5] = consProveedores.obtenerNnombreSegunIdProveedor(productoTemporal.getIdProveedor());
            datos[6] = productoTemporal.getStockMinimo();
            datos[7] = productoTemporal.getStockMaximo();

            modelo.addRow(datos);
        }
        formInventario.tbl_Productos.setModel(modelo);
    }

    //LLENAR TABLA DE INVENTARIO 
    public void llenarTablaInventario() {
        modelo = (DefaultTableModel) formInventario.tbl_registrosInventario.getModel();
        modelo.setRowCount(0);
        // Obtener la lista de todos los productos una vez
        ArrayList<Inventario> listaInventario = consInventario.leerTodoInventario();
        int registros = listaInventario.size();

        for (int i = 0; i < registros; i++) {
            Inventario inventarioTemporal = listaInventario.get(i);
            producto.setIdProducto(inventarioTemporal.getIdProducto());
            datos[0] = inventarioTemporal.getIdProducto();
            datos[1] = consProductos.obtenerValoresProducto(producto).getDescripcion();
            datos[2] = inventarioTemporal.getCantidad();

            modelo.addRow(datos);
        }
        formInventario.tbl_registrosInventario.setModel(modelo);
    }

    //REGISTRAR UN MOVIMIENTO DE ENTRADA EN EL INVENTARIO
    public void registrarInventarioEntrada() {

        if (validarCamposNoVacios()) {
            int idProducto = Integer.parseInt(formInventario.txtCodigo.getText());
            producto = consProductos.obtenerValoresProducto(producto);

            if (consInventario.yaExisteInventario(idProducto)) {
                // Obtener cantidad actual en inventario y máximo permitido
                int cantidadActual = consInventario.obtenerCantidadActualInventario(idProducto);
                int maximoPermitido = producto.getStockMaximo();

                // Verificar si sumar la cantidad ingresada excede el máximo
                int cantidadIngresada = Integer.parseInt(formInventario.txtCantidad.getText());
                if (cantidadActual + cantidadIngresada > maximoPermitido) {
                    JOptionPane.showMessageDialog(null, "La cantidad ingresada excede el stock máximo permitido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            //SI NO EXISTE CONTUNUAR CON EL REGISTRO NORMALMENTE
            if(validarCantidadEnRango(producto)){
                inventario.setIdProducto(idProducto);
                inventario.setFechaMovimiento(fechaSQL);
                inventario.setTipoMovimiento("Entrada");
                inventario.setCantidad(Integer.parseInt(formInventario.txtCantidad.getText()));
                if(consInventario.registrarMovimiento(inventario)){
                    JOptionPane.showMessageDialog(null, "Inventario registrado.");
                }
            }
        }

    }

    //VALIDACIONES *******************************
    public boolean validarCamposNoVacios() {
        boolean camposValidos = true;
        if (formInventario.txtCodigo.getText().trim().isEmpty()) {
            resaltarCampoVacio(formInventario.txtCodigo);
            camposValidos = false;
        }

        if (formInventario.txtCantidad.getText().trim().isEmpty()) {
            resaltarCampoVacio(formInventario.txtCantidad);
            camposValidos = false;
        }
        if (!camposValidos) {
            JOptionPane.showMessageDialog(null, "No se pueden dejar campos vacios.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return camposValidos;
    }

    public boolean validarCantidadEnRango(Productos _producto) {
        int cantidad = Integer.parseInt(formInventario.txtCantidad.getText());
        if (cantidad < _producto.getStockMinimo() || cantidad > _producto.getStockMaximo()) {
            JOptionPane.showMessageDialog(null, "La cantidad debe estar entre el stock mínimo y máximo.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    //FUNCIONES******
    public void resaltarCampoVacio(JTextField campo) {
        Color colorResaltado = new Color(219, 52, 52);
        campo.setBackground(colorResaltado);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //BOTON PARA ABRIR EL FORMULARIO DE CONSULTA PROVEEDORES
        if (e.getSource() == formInventario.btnConsultarProductos) {
            formConsProducto.setVisible(true);
        }
        //BOTON PARA BUSCAR EL PRODUCTO EN FORMULARIO DE CONSULTA DE PROVEEDORES
        if (e.getSource() == formConsProducto.btn_buscarProducto) {
            consultarProductos();
        }
        //BOTON PARA BUSCAR PRODUCTO DIRECTAMENTE EN EL FORMULARIO INVENTARIO
        if (e.getSource() == formInventario.btn_buscarProducto) {
            buscarProducto();
        }
        //BOTON LEER PARA LLENAR TABLAS
        if (e.getSource() == formInventario.btnLeer) {
            llenarTablaProductos();
            llenarTablaInventario();
        }
        //BOTON CREAR PARA REGISTRAR UN MOVIMIENTO DE ENTRADA EN EL INVENTARIO
        if (e.getSource() == formInventario.btnCrear) {
            registrarInventarioEntrada();
        }
    }

}
