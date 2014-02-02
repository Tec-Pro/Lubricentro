    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import abm.ABMCliente;
import abm.ABMVenta;
import busqueda.Busqueda;
import interfaz.AplicacionGui;
import interfaz.ClienteGui;
import interfaz.VentaGui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelos.Articulo;
import modelos.ArticulosVentas;
import modelos.Cliente;
import modelos.Venta;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

/**
 *
 * @author jacinto
 */
public class ControladorCliente implements ActionListener {

    private JTextField nomcli;
    private ClienteGui clienteGui;
    private AplicacionGui aplicacionGui;
    private DefaultTableModel tablaCliDefault;
    private DefaultTableModel tablaVentasDefault;
    private java.util.List<Cliente> listClientes;
    private JTable tablaCliente;
    private JTable tablaVentas;
    private ABMCliente abmCliente;
    private Boolean isNuevo;
    private Boolean editandoInfo;
    private Cliente cliente;
    private JComboBox ver;
    Busqueda busqueda;
    VentaGui ventaGui;

    public ControladorCliente(ClienteGui clienteGui, AplicacionGui aplicacionGui,VentaGui ventaGui) {
        this.aplicacionGui = aplicacionGui;
        this.clienteGui = clienteGui;
        this.ventaGui= ventaGui;
        this.clienteGui.setActionListener(this);
        isNuevo = true;
        editandoInfo = false;
        busqueda = new Busqueda();
        tablaCliDefault = clienteGui.getClientes();
        tablaVentasDefault = clienteGui.getVentasDefault();
        tablaVentas = clienteGui.getVentasRealizadas();
        tablaCliente = clienteGui.getTablaClientes();;
        listClientes = new LinkedList();
        abmCliente = new ABMCliente();
        cliente = new Cliente();
        abrirBase();
        listClientes = Cliente.findAll();
        cerrarBase();
        actualizarLista();
        nomcli = clienteGui.getBusqueda();
        nomcli.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busquedaKeyReleased(evt);
            }
        });
        tablaCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaClienteMouseClicked(evt);
            }
        });
        ver = clienteGui.getVer();
    }

    private void busquedaKeyReleased(KeyEvent evt) {
        System.out.println("apreté el caracter: " + evt.getKeyChar());
        realizarBusqueda();
    }

    public void cargarTodos() {
        abrirBase();
        listClientes = Cliente.findAll();
        if (!listClientes.isEmpty()) {
            realizarBusqueda();
        }
        cerrarBase();
    }

    private void tablaClienteMouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            clienteGui.habilitarCampos(false);
            clienteGui.getBorrar().setEnabled(true);
            clienteGui.getModificar().setEnabled(true);
            clienteGui.getGuardar().setEnabled(false);
            clienteGui.getNuevo().setEnabled(true);
            clienteGui.getRealizarCobro().setEnabled(true);
            System.out.println("hice doble click en un cliente");
            clienteGui.limpiarCampos();
            abrirBase();
            cliente = busqueda.buscarCliente(tablaCliente.getValueAt(tablaCliente.getSelectedRow(), 0));
            clienteGui.CargarCampos(cliente);
            cargarVentas();
            clienteGui.habilitarCamposVentas(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
                realizarBusqueda();
                cerrarBase();
            }
        }
        if (e.getSource() == clienteGui.getRealizarCobro()) {
            int row = tablaVentas.getSelectedRow();
            if (row > -1) {
                abrirBase();
                String id = (String) tablaVentas.getValueAt(row, 0);
                BigDecimal monto = new BigDecimal((String) tablaVentas.getValueAt(row, 2));
                Venta v = Venta.findById(id);
                ABMVenta ambV = new ABMVenta();
                if (ambV.pagar(v, monto)) {
                    JOptionPane.showMessageDialog(clienteGui, "¡Cobro registrado exitosamente!");
                    cargarVentas();
                } else {
                    JOptionPane.showMessageDialog(clienteGui, "Ocurrió un error, el cobro no ha sido registrado", "Error!", JOptionPane.ERROR_MESSAGE);

                }
                cerrarBase();
            }
        }
        if (e.getSource() == clienteGui.getEliminarVenta()) {
            int row = tablaVentas.getSelectedRow();
            if (row > -1) {
                abrirBase();
                String id = (String) tablaVentas.getValueAt(row, 0);
                Venta v = Venta.findById(id);
                ABMVenta abmV = new ABMVenta();
                if (abmV.baja(v)) {
                    JOptionPane.showMessageDialog(clienteGui, "¡Venta eliminada exitosamente!");
                    cargarVentas();
                } else {
                    JOptionPane.showMessageDialog(clienteGui, "Ocurrió un error, la venta no ha sido eliminada", "Error!", JOptionPane.ERROR_MESSAGE);

                }
                cerrarBase();
            }
        }
        if (e.getSource() == clienteGui.getVerFactura()) {
        }
        if (e.getSource() == clienteGui.getVer()) {
            cargarVentas();
        }
         if ((e.getSource() == clienteGui.getVerFactura())) {
            System.out.println("entre");
            Integer idFac = Integer.valueOf((String) clienteGui.getVentasRealizadas().getValueAt(clienteGui.getVentasRealizadas().getSelectedRow(), 0));
            System.out.println("factura:" + idFac);
            abrirBase();
            Venta factura = Venta.findById(idFac);
            System.out.println(factura.getId());
            Object idCliente = factura.get("cliente_id");
            DefaultTableModel tablita = ventaGui.getTablaFacturaDefault();
            tablita.setRowCount(0);
            Cliente cli = Cliente.findById(idCliente);
            System.out.println(cli.getId());
            if (cli != null) {
                String nombre = idCliente + " " + cli.getString("nombre");
                System.out.println(nombre);
                ventaGui.getClienteFactura().setText(nombre);
                LazyList<ArticulosVentas> pr = ArticulosVentas.find("venta_id = ?", idFac);
                Iterator<ArticulosVentas> it = pr.iterator();
                System.out.println("antes del while");
                while (it.hasNext()) {
                    ArticulosVentas prod = it.next();
                    Articulo producto = Articulo.findFirst("id = ?", prod.get("articulo_id"));
                    if (producto != null) {
                        System.out.println("entre");
                        BigDecimal precio;
                        if (factura.getBoolean("pago")) {
                            precio = prod.getBigDecimal("precio_final").setScale(2, RoundingMode.CEILING);
                        } else {
                            precio = producto.getBigDecimal("precio_venta").setScale(2, RoundingMode.CEILING);
                        }
                        BigDecimal cantidad = prod.getBigDecimal("cantidad").setScale(2, RoundingMode.CEILING);
                        Object cols[] = new Object[5];
                        cols[0] = producto.get("id");
                        cols[1] = cantidad;
                        cols[2] = producto.get("codigo");
                        cols[3] = precio;
                        cols[4] = precio.multiply(cantidad).setScale(2);
                        ventaGui.getTablaFacturaDefault().addRow(cols);
                    }
                }
                if (factura.getBoolean("pago")) {
                    ventaGui.getTotalFactura().setText(String.valueOf(factura.getFloat("monto")));
                }
                else
                    actualizarPrecio();
                Base.close();
                System.out.println("sali");
                ventaGui.getRealizarVenta().setEnabled(false);
                ventaGui.getFacturaNueva().setEnabled(false);
                ventaGui.getBusquedaCodigoArticulo().setEnabled(false);
                ventaGui.getBusquedaFram().setEnabled(false);
                ventaGui.getBusquedaNombre().setEnabled(false);
                ventaGui.getClienteFactura().setEnabled(false);
                ventaGui.getCalendarioFactura().setEnabled(false);
                ventaGui.getBorrarArticulosSeleccionados().setEnabled(false);
                ventaGui.getAbona().setEnabled(false);
                ventaGui.setVisible(true);
                ventaGui.toFront();
                System.out.println("termine");
            } else {
                JOptionPane.showMessageDialog(aplicacionGui, "El cliente asociado a esta factura ya no existe", "CLIENTE INEXISTENTE", JOptionPane.ERROR_MESSAGE);
            }

        }
    }
    
    private void actualizarPrecio() {
        BigDecimal importe;
        BigDecimal total = new BigDecimal(0);
        for (int i = 0; i < ventaGui.getTablaFactura().getRowCount(); i++) {
            importe = ((BigDecimal) ventaGui.getTablaFactura().getValueAt(i, 1)).multiply((BigDecimal) ventaGui.getTablaFactura().getValueAt(i, 3)).setScale(2, RoundingMode.CEILING);
            ventaGui.getTablaFactura().setValueAt(importe, i, 4);
        }
        for (int i = 0; i < ventaGui.getTablaFactura().getRowCount(); i++) {
            total = total.add((BigDecimal) ventaGui.getTablaFactura().getValueAt(i, 4)).setScale(2, RoundingMode.CEILING);;
        }
        ventaGui.getTotalFactura().setText(total.toString());
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
        listClientes = busqueda.buscarCliente(nomcli.getText());
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

    public void cargarVentas() {
        abrirBase();
        tablaVentasDefault.setRowCount(0);
        Iterator<Venta> itr = busqueda.filtroVenta(cliente.getString("id"), "0-0-0", "9999-0-0").iterator();
        while (itr.hasNext()) {
            Venta v = itr.next();
            Object row[] = new String[4];
            row[0] = v.getString("id");
            row[1] = v.getDate("fecha").toString();
            if (v.getBoolean("pago")) {
                row[2] = v.get("monto").toString();
                row[3] = "Si";
            } else {
                BigDecimal montox = null;
                BigDecimal cuenta;
                Iterator<ArticulosVentas> itr2 = busqueda.filtroVendidos(v.getString("id")).iterator();
                while (itr2.hasNext()) {
                    ArticulosVentas arvs = itr2.next();
                    Articulo art = Articulo.findById(arvs.getInteger("articulo_id"));
                    cuenta = (art.getBigDecimal("precio_venta")).multiply(arvs.getBigDecimal("cantidad")).setScale(2, RoundingMode.CEILING);
                    if (montox == null) {
                        montox = new BigDecimal(String.valueOf((cuenta).setScale(2, RoundingMode.CEILING)));
                    }
                    montox = new BigDecimal(String.valueOf(montox.add(cuenta).setScale(2, RoundingMode.CEILING)));
                }
                montox.setScale(2, RoundingMode.CEILING);
                row[2] = montox.toString();
                row[3] = "No";
            }
            if (ver.getSelectedIndex() == 0) {
                tablaVentasDefault.addRow(row);
            } else {
                if (ver.getSelectedIndex() == 1) {
                    if (!(v.getBoolean("pago"))) {
                        tablaVentasDefault.addRow(row);
                    }
                } else {
                    if (ver.getSelectedIndex() == 2) {
                        if ((v.getBoolean("pago"))) {
                            tablaVentasDefault.addRow(row);
                        }
                    }
                }
            }
        }

        cerrarBase();
    }
}
