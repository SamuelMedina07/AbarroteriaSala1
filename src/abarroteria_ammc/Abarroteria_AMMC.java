/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abarroteria_ammc;

import controlador.ClienteControlador;
import controlador.InventarioControlador;
import controlador.PrincipalControlador;
import controlador.ProductoControlador;
import controlador.ProveedorControlador;
import controlador.VentasControlador;
import modelo.Clientes;
import modelo.ConsultaBD;
import modelo.ConsultaInventario;
import modelo.ConsultaProductos;
import modelo.ConsultaProveedores;
import modelo.Inventario;
import modelo.Productos;
import modelo.Proveedores;
import modelo.Ventas;
import vista.frm_Clientes;
import vista.frm_Consulta_Producto;
import vista.frm_Inicio;
import vista.frm_Inventario;
import vista.frm_Principal;
import vista.frm_Productos;
import vista.frm_Proveedores;
import vista.frm_Ventas;
import vista.frm_login;

/**
 *
 * @author ammcp
 */
public class Abarroteria_AMMC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        frm_Principal frm_pri = new frm_Principal(("Arington"));//frm principal clase padre
        
        frm_Inicio formInicio = new frm_Inicio(new frm_login(frm_pri));
        formInicio.setVisible(true);
        
        //CLIENTES
        Clientes cliente = new Clientes();//objeto
        frm_Clientes frmClie = new frm_Clientes(frm_pri, true, "");//formulario de c lientes, jdialog el primer parametro necesita la clase padre
        ConsultaBD con = new ConsultaBD();// clase consulta de clientes
        ClienteControlador contCliente = new ClienteControlador(cliente, frmClie, con);//clase controlador
        
        //PROVEEDORES
        Proveedores proveedor = new Proveedores();
        frm_Proveedores frmProveedor = new frm_Proveedores(frm_pri, true, ""); // el valor booleano lo veremos  en lo reportes
        ConsultaProveedores conProveedores = new ConsultaProveedores();
        ProveedorControlador contProveedor = new ProveedorControlador(proveedor,frmProveedor,conProveedores);
        
        
        //PRODUCTOS
        Productos producto = new Productos();
        frm_Productos frmProducto = new frm_Productos(frm_pri, true);
        ConsultaProductos consProducto = new ConsultaProductos();
        frm_Consulta_Producto formConsProducto = new frm_Consulta_Producto(frmProducto, true);
        ProductoControlador contProducto = new ProductoControlador(producto,frmProducto,consProducto,conProveedores,formConsProducto);
       
        //Inventario
        Inventario inventario = new Inventario();
        frm_Inventario frmINventario = new frm_Inventario(frmProducto, true);
        ConsultaInventario consInventario = new ConsultaInventario();
        frm_Consulta_Producto formConsProductoInventario = new frm_Consulta_Producto(frmINventario, true);
        InventarioControlador contInventario = new InventarioControlador(inventario, consInventario, frmINventario, formConsProductoInventario);
        
        
        //Factura
        
//         Ventas vent = new Ventas();//objeto
        frm_Ventas frmventas = new frm_Ventas(frm_pri, true);//formulario de c lientes, jdialog el primer parametro necesita la clase padre
        //ConsultaBD con = new ConsultaBD();// clase consulta de clientes
        VentasControlador contVent = new VentasControlador(cliente, frmventas,con);//clase controlador
        
        
        PrincipalControlador contPri = new PrincipalControlador(frm_pri, frmClie, frmProveedor,frmProducto,frmINventario,frmventas);
        contPri.inciar();
        //frm_pri.setVisible(true);
                
        
    }
    
}
