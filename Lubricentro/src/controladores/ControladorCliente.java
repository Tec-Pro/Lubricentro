/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import abm.ABMCliente;
import busqueda.Busqueda;
import interfaz.AplicacionGui;
import interfaz.ClienteGui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelos.Cliente;
import modelos.Pago;
import org.javalite.activejdbc.Base;

/**
 *
 * @author jacinto
 */
public class ControladorCliente implements ActionListener {

    private ClienteGui clienteGui;
    private AplicacionGui aplicacionGui;
    private DefaultTableModel tablaCliDefault;
    private DefaultTableModel tablaCobrosDefault;
    private DefaultTableModel tablaVentasDefault;
    private java.util.List<Cliente> listClientes;
    //  private java.util.List<Pago> listPagos;
    private JTable tablaCliente;
    private JTable tablaCobros;
    private JTable tablaCompras;
    private ABMCliente abmCliente;
    private Boolean isNuevo;
    private Boolean editandoInfo;
    private Cliente cliente;
    //   private RealizarPagoGui realizarPagoGui;
    Busqueda busqueda;

    public ControladorCliente(ClienteGui clienteGui, AplicacionGui aplicacionGui) {
        this.aplicacionGui = aplicacionGui;
        this.clienteGui = clienteGui;
        isNuevo = true;
        editandoInfo = false;
        busqueda = new Busqueda();
        tablaCliDefault = clienteGui.getClientes();
        tablaCobrosDefault = clienteGui.getCobrosDefault();
        tablaVentasDefault = clienteGui.getVentasDefault();
        tablaCliente = clienteGui.getTablaClientes();
        tablaCobros = clienteGui.getCobrosRealizados();
        listClientes = new LinkedList();
        //listPagos = new LinkedList();
        abmCliente = new ABMCliente();
        cliente = new Cliente();
        abrirBase();
        listClientes = Cliente.findAll();
        cerrarBase();
        actualizarLista();
        clienteGui.getBusqueda().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busquedaKeyReleased(evt);
            }
        });
        clienteGui.getTablaClientes().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        clienteGui.getCobrosRealizados().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaCobrosClicked(evt);
            }
        });

    }

    private void busquedaKeyReleased(KeyEvent evt) {
        System.out.println("apreté el caracter: " + evt.getKeyChar());
        realizarBusqueda();
    }

    private void tablaCobrosClicked(MouseEvent evt) {
        clienteGui.getBorrarCobro().setEnabled(true);
    }

    private void tablaMouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            clienteGui.habilitarCampos(false);
            clienteGui.getBorrar().setEnabled(true);
            clienteGui.getModificar().setEnabled(true);
            clienteGui.getGuardar().setEnabled(false);
            clienteGui.getNuevo().setEnabled(true);
            clienteGui.getRealizarCobro().setEnabled(true);
            clienteGui.getBorrarCobro().setEnabled(false);
            System.out.println("hice doble click en un cliente");
            clienteGui.limpiarCampos();
            abrirBase();
            cliente = busqueda.buscarCliente(tablaCliente.getValueAt(tablaCliente.getSelectedRow(), 0));
            clienteGui.CargarCampos(cliente);
//            cargarCobros();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("entro");
        if (e.getSource() == clienteGui.getNuevo()) {
            System.out.println("Boton nuevo pulsado");
            clienteGui.limpiarCampos();
            clienteGui.habilitarCampos(true);
            isNuevo = true;
            editandoInfo = true;
            clienteGui.getBorrar().setEnabled(false);
            clienteGui.getRealizarCobro().setEnabled(false);
            clienteGui.getModificar().setEnabled(false);
            clienteGui.getGuardar().setEnabled(true);
        }
        if (e.getSource() == clienteGui.getGuardar() && editandoInfo && isNuevo) {
            System.out.println("Boton guardar pulsado");
            if (cargarDatosCliente(cliente)) {
                abrirBase();
                if (abmCliente.alta(cliente)) {
                    clienteGui.habilitarCampos(false);
                    clienteGui.limpiarCampos();
                    editandoInfo = false;
                    JOptionPane.showMessageDialog(clienteGui, "¡Cliente guardado exitosamente!");
                    clienteGui.getNuevo().setEnabled(true);
                    clienteGui.getGuardar().setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(clienteGui, "Ocurrió un error, revise los datos", "Error!", JOptionPane.ERROR_MESSAGE);
                }
                cerrarBase();
                realizarBusqueda();
            }
        }
        if (e.getSource() == clienteGui.getBorrar()) {

            System.out.println("Boton borrar pulsado");
            clienteGui.habilitarCampos(false);
            if (cliente.getString("id") != null && !editandoInfo) {
                Integer resp = JOptionPane.showConfirmDialog(clienteGui, "¿Desea borrar el cliente " + clienteGui.getNombre().getText(), "Confirmar borrado", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) {
                    abrirBase();
                    Boolean seBorro = abmCliente.baja(cliente);
                    cerrarBase();
                    if (seBorro) {
                        JOptionPane.showMessageDialog(clienteGui, "¡Cliente borrado exitosamente!");
                        clienteGui.limpiarCampos();
                        realizarBusqueda();
                        clienteGui.getBorrar().setEnabled(false);
                        clienteGui.getModificar().setEnabled(false);
                        clienteGui.getRealizarCobro().setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(clienteGui, "Ocurrió un error, no se borró el cliente", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(clienteGui, "No se seleccionó un cliente");
            }



        }
        if (e.getSource() == clienteGui.getModificar()) {
            System.out.println("Boton modificar pulsado");
            clienteGui.habilitarCampos(true);
            editandoInfo = true;
            isNuevo = false;
            clienteGui.getBorrar().setEnabled(false);
            clienteGui.getGuardar().setEnabled(true);
            clienteGui.getModificar().setEnabled(false);
            clienteGui.getRealizarCobro().setEnabled(false);
        }

        if (e.getSource() == clienteGui.getGuardar() && editandoInfo && !isNuevo) {
            System.out.println("Boton guardar pulsado");
            if (cargarDatosCliente(cliente)) {
                abrirBase();
                if (abmCliente.modificar(cliente)) {
                    clienteGui.habilitarCampos(false);
                    clienteGui.limpiarCampos();
                    editandoInfo = false;
                    JOptionPane.showMessageDialog(clienteGui, "¡Cliente modificado exitosamente!");
                    clienteGui.getNuevo().setEnabled(true);
                    clienteGui.getGuardar().setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(clienteGui, "Ocurrió un error,revise los datos", "Error!", JOptionPane.ERROR_MESSAGE);
                }
                cerrarBase();
                realizarBusqueda();
            }
        }
//        if (e.getSource() == clienteGui.getRealizarCobro()) {
//            System.out.println("realizar pago pulsado");
//            realizarPagoGui = new RealizarPagoGui(aplicacionGui, true, proveedor);
//            realizarPagoGui.setLocationRelativeTo(proveedorGui);
//            realizarPagoGui.setVisible(true);
//            cargarPagos();
//        }
//        if (e.getSource() == clienteGui.getBorrarCobro()) {
//            System.out.println("Borrar pago pulsado");
//            Integer resp = JOptionPane.showConfirmDialog(proveedorGui, "¿Desea borrar el pago seleccionado? " + proveedorGui.getNombre().getText(), "Confirmar borrado", JOptionPane.YES_NO_OPTION);
//            if (resp == JOptionPane.YES_OPTION) {
//                String fecha=tablaPagos.getValueAt(tablaPagos.getSelectedRow(), 0).toString(); //Se le pasa la fecha a la que queremos darle formato
//                String dia= fecha.substring(0, 2);
//                String mes= fecha.substring(3, 5);
//                String anio= fecha.substring(6, 10);
//                String fechaSql= anio+mes+dia;
//                abrirBase();
//                Pago.findFirst("fecha = ? and monto = ? and proveedor_id = ?", fechaSql, tablaPagos.getValueAt(tablaPagos.getSelectedRow(), 1), proveedorGui.getId().getText()).delete();
//                cerrarBase();
//                cargarPagos();
//              
//            }
//        }  
//        
    }

    private void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/lubricentro", "root", "root");
        }
    }

    private void cerrarBase() {
        if (Base.hasConnection()) {
            Base.close();
        }
    }

    private void actualizarLista() {
        abrirBase();
        tablaCliDefault.setRowCount(0);
        Iterator<Cliente> it = listClientes.iterator();
        while (it.hasNext()) {
            Cliente c = it.next();
            Object row[] = new String[6];
            row[0] = c.getString("id");
            row[1] = c.getString("nombre");
            row[2] = c.getString("telefono");
            row[3] = c.getString("celular");
            tablaCliDefault.addRow(row);
            cerrarBase();
        }
    }

    private void realizarBusqueda() {
        abrirBase();
        listClientes = busqueda.buscarCliente(clienteGui.getBusqueda().getText());
        actualizarLista();
        cerrarBase();

    }

    private boolean cargarDatosCliente(Cliente c) {
        boolean ret = true;
        try {
            String nombre = TratamientoString.eliminarTildes(clienteGui.getNombre().getText());
            System.out.println(nombre);
            c.set("nombre", nombre);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(clienteGui, "Error en el nombre", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            String telefono = TratamientoString.eliminarTildes(clienteGui.getTelefono().getText());
            c.set("telefono", telefono);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(clienteGui, "Error en el telefono", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            String celular = TratamientoString.eliminarTildes(clienteGui.getCelular().getText());
            c.set("celular", celular);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(clienteGui, "Error en el celular", "Error!", JOptionPane.ERROR_MESSAGE);
        }

        return ret;
    }
//       private void cargarCobros() {
//        abrirBase();
//        listPagos = cliente.getAll(Pago.class);
//        tablaPagosDefault.setRowCount(0);
//        Iterator<Pago> it = listPagos.iterator();
//        while (it.hasNext()) {
//            Pago pago = it.next();
//            Object row[] = new String[2];
//            Date sqlFecha = pago.getDate("fecha");
//            SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
//            row[0] = sdf.format(sqlFecha);
//            row[1] = pago.getBigDecimal("monto").setScale(2, RoundingMode.CEILING).toString();
//            tablaPagosDefault.addRow(row);
//            cerrarBase();
//        }
//    }
}
