/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import abm.ABMVenta;
import abm.ManejoIp;
import busqueda.Busqueda;
import interfaz.AplicacionGui;
import interfaz.PagoFacturaGui;
import interfaz.VentaGui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import modelos.Articulo;
import modelos.ArticulosVentas;
import modelos.Cliente;
import modelos.Pago;
import modelos.Venta;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.util.Pair;
import org.javalite.activejdbc.Base;

/**
 *
 * @author jacinto
 */
public class ControladorVenta implements ActionListener, CellEditorListener {

    private static BigDecimal iva = new BigDecimal("1.21").setScale(2, RoundingMode.CEILING);
    private JTextField textan;
    private JTextField textFram;
    private JTextField textcodprod;
    private List prodlista;
    private List clientelista;
    private Busqueda busqueda;
    private ABMVenta abmVenta;
    private VentaGui ventaGui;
    private JTable tablap;
    private JTable tablac;
    private DefaultTableModel tablaClientes;
    private DefaultTableModel tablaProd;
    private JTable tablafac;
    private ControladorJReport reporteFactura;
    private ControladorJReport reporteFacturaSinPagar;
    private AplicacionGui apgui;

    public ControladorVenta(VentaGui ventaGui, AplicacionGui apgui) throws JRException, ClassNotFoundException, SQLException {
        this.apgui = apgui;
        prodlista = new LinkedList<Articulo>();
        clientelista = new LinkedList<Cliente>();
        busqueda = new Busqueda();
        abmVenta = new ABMVenta();
        this.ventaGui = ventaGui;
        this.ventaGui.setActionListener(this);
        tablap = ventaGui.getTablaArticulos();
        tablap.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProdMouseClicked(evt);
            }
        });
        tablac = ventaGui.getTablaClientes();
        tablac.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaClienteMouseClicked(evt);
            }
        });
        textan = ventaGui.getBusquedaNombre();
        textan.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busquedaClienteKeyReleased(evt);
            }
        });
        tablafac = ventaGui.getTablaFactura();
        textFram = ventaGui.getBusquedaFram();
        textFram.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busquedaProductoKeyReleased(evt);
            }
        });

        textcodprod = ventaGui.getBusquedaCodigoArticulo();
        textcodprod.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busquedaProductoKeyReleased(evt);
            }
        });
        tablaClientes = ventaGui.getTablaClientesDefault();
        tablaProd = ventaGui.getTablaArticulosDefault();
        clientelista = busqueda.filtroCliente("", "");
        prodlista = busqueda.filtroProducto("", "");
        actualizarListaCliente();
        actualizarListaProd();
        reporteFactura = new ControladorJReport(("factura.jasper"));
        reporteFacturaSinPagar = new ControladorJReport(("facturaSinPago.jasper"));

    }

    private void busquedaClienteKeyReleased(KeyEvent evt) {
        actualizarListaCliente();
    }

    private void busquedaProductoKeyReleased(KeyEvent evt) {
        actualizarListaProd();
    }

    private void tablaProdMouseClicked(MouseEvent evt) {
        abrirBase();
        BigDecimal porcentaje;
        int[] rows = ventaGui.getTablaArticulos().getSelectedRows();
        if (rows.length > 0) {
            for (int i = 0; i < rows.length; i++) {
                abrirBase();
                if (!existeProdFacc(Integer.valueOf((String) tablap.getValueAt(rows[i], 0)))) {
                    Articulo p = Articulo.findFirst("id = ?", (tablap.getValueAt(rows[i], 0)));
                    Object cols[] = new Object[7];
                    cols[0] = p.get("id");
                    cols[1] = BigDecimal.valueOf(1).setScale(2, RoundingMode.CEILING);
                    cols[2] = p.get("codigo");
                    cols[3] = p.get("descripcion");
                    cols[4] = BigDecimal.valueOf(p.getFloat("precio_venta")).setScale(2, RoundingMode.CEILING);
                    cols[5] = p.getBigDecimal("precio_venta").divide(iva, 2, RoundingMode.CEILING);
                    cols[6] = BigDecimal.valueOf(p.getFloat("precio_venta")).setScale(2, RoundingMode.CEILING);
                    if (Base.hasConnection()) {
                        Base.close();
                    }
                    ventaGui.getTablaFacturaDefault().addRow(cols);
                    setCellEditor();
                    actualizarPrecio();
                } else {
                    System.out.println("que hace guacho");
                }
            }
        }
    }

    private void tablaClienteMouseClicked(MouseEvent evt) {
        int row = ventaGui.getTablaClientes().getSelectedRow();
        if (row > -1) {
            String id = (String) tablac.getValueAt(row, 0);
            String nom = (String) tablac.getValueAt(row, 1);
            ventaGui.getClienteFactura().setText(id + " " + nom);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ventaGui.getBorrarArticulosSeleccionados()) {//boton borrar articulos seleccionados
            int[] rows = ventaGui.getTablaFactura().getSelectedRows();
            if (rows.length > 0) {
                Integer[] idABorrar = new Integer[rows.length];
                for (int i = 0; i < rows.length; i++) {
                    idABorrar[i] = (Integer) tablafac.getValueAt(rows[i], 0);
                }
                int i = 0;
                int cantABorrar = 0;
                while (cantABorrar < rows.length) {
                    while (i < ventaGui.getTablaFactura().getRowCount()) {
                        if ((Integer) ventaGui.getTablaFactura().getValueAt(i, 0) == idABorrar[cantABorrar]) {
                            ventaGui.getTablaFacturaDefault().removeRow(i);
                            cantABorrar++;
                        }
                        i++;
                    }
                    i = 0;
                }
                actualizarPrecio();
            }
        }
        if (e.getSource() == ventaGui.getFacturaNueva()) {
            ventaGui.limpiarVentana();
            ventaGui.paraVerVenta(false);
            ventaGui.getRealizarVenta().setEnabled(true);
        }
        if (e.getSource() == ventaGui.getRealizarVenta()) {//Boton realizar venta
            if (ventaGui.getClienteFactura().getText().equals("") || ventaGui.getCalenFacturaText().getText().equals("") || ventaGui.getTablaFactura().getRowCount() == 0) {
                JOptionPane.showMessageDialog(ventaGui, "Fecha, cliente vacio o no hay productos cargados", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                System.out.println("entre a registrar venta");
                if (ventaGui.getAbonaSi().isSelected()) {
                    abrirBase();
                    Venta v = new Venta();
                    LinkedList<Pair> parDeProductos = new LinkedList();
                    LinkedList<BigDecimal> preciosFinales = new LinkedList();
                    String laFecha = ventaGui.getCalenFacturaText().getText(); //saco la fecha
                    String cliente = ventaGui.getClienteFactura().getText();
                    Integer idCliente = Integer.valueOf(cliente.split(" ")[0]); //saco el id cliente
                    v.set("cliente_id", idCliente);
                    for (int i = 0; i < ventaGui.getTablaFactura().getRowCount(); i++) {
                        abrirBase();
                        Articulo producto = Articulo.findFirst("id = ?", tablafac.getValueAt(i, 0));
                        BigDecimal cantidad = ((BigDecimal) tablafac.getValueAt(i, 1)).setScale(2, RoundingMode.CEILING); //saco la cantidad
                        BigDecimal precioFinal = ((BigDecimal) tablafac.getValueAt(i, 6)).setScale(2, RoundingMode.CEILING);
                        preciosFinales.add(precioFinal);
                        Pair par = new Pair(producto, cantidad); //creo el par
                        parDeProductos.add(par); //meto el par a la lista
                    }
                    v.set("fecha", laFecha);
                    v.setPreciosFinales(preciosFinales);
                    v.setProductos(parDeProductos);
                    v.set("pago", true);
                    BigDecimal bd = new BigDecimal(ventaGui.getTotalFactura().getText());
                    v.set("monto", bd);
                    abrirBase();
                    Base.openTransaction();
                    Pago pago =Pago.createIt("fecha", laFecha,"monto", bd,"cliente_id", idCliente);
                    Base.commitTransaction();
                    Base.close();
                    System.out.println(pago.getId()+" "+ laFecha+" " +bd+" " +idCliente);
                    String pagoId = pago.getString("id");//Pago.findFirst("fecha = ? and monto = ? and cliente_id = ?", laFecha, bd, idCliente).getString("id");
                    System.out.println("el pago id es:  "+pagoId);
                    v.set("pago_id", pagoId);
                    abrirBase();
                    if (abmVenta.alta(v)) {
                        JOptionPane.showMessageDialog(apgui, "Venta realizada con exito.");
                        ventaGui.limpiarVentana();
                        try {
                            {
                                reporteFactura.mostrarFactura(abmVenta.getUltimoIdVenta());
                            }
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(ControladorVenta.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(ControladorVenta.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (JRException ex) {
                            Logger.getLogger(ControladorVenta.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(apgui, "Ocurrió un error inesperado, venta no realizada");
                    }
                    if (Base.hasConnection()) {
                        Base.close();
                    }
                } else {
                    Venta v = new Venta();
                    LinkedList<Pair> parDeProductos = new LinkedList();
                    String laFecha = ventaGui.getCalenFacturaText().getText(); //saco la fecha
                    String cliente = ventaGui.getClienteFactura().getText();
                    Integer idCliente = Integer.valueOf(cliente.split(" ")[0]); //saco el id cliente
                    v.set("cliente_id", idCliente);
                    for (int i = 0; i < ventaGui.getTablaFactura().getRowCount(); i++) {
                        abrirBase();
                        Articulo producto = Articulo.findFirst("id = ?", tablafac.getValueAt(i, 0));
                        BigDecimal cantidad = ((BigDecimal) tablafac.getValueAt(i, 1)).setScale(2, RoundingMode.CEILING); //saco la cantidad
                        Pair par = new Pair(producto, cantidad); //creo el par
                        parDeProductos.add(par); //meto el par a la lista
                    }
                    v.set("fecha", laFecha);
                    v.set("pago", false);
                    v.setProductos(parDeProductos);
                    abrirBase();
                    if (abmVenta.alta(v)) {
                        JOptionPane.showMessageDialog(apgui, "Venta realizada con exito.");
                        ventaGui.limpiarVentana();
                        try {
                            {
                                reporteFacturaSinPagar.mostrarFactura(abmVenta.getUltimoIdVenta());
                            }
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(ControladorVenta.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(ControladorVenta.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        } catch (JRException ex) {
                            Logger.getLogger(ControladorVenta.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(apgui, "Ocurrió un error inesperado, venta no realizada");
                    }
                    if (Base.hasConnection()) {
                        Base.close();
                    }
                }
            }
        }
    }

    public void cargarTodos() {
        actualizarListaCliente();
        actualizarListaProd();
    }

    private void actualizarListaCliente() {
        abrirBase();
        tablaClientes.setRowCount(0);
        clientelista = busqueda.filtroCliente(textan.getText(), "");
        Iterator<Cliente> it = clientelista.iterator();
        while (it.hasNext()) {
            Cliente a = it.next();
            String row[] = new String[2];
            row[0] = a.getId().toString();
            row[1] = a.getString("nombre");
            tablaClientes.addRow(row);
        }
        if (Base.hasConnection()) {
            Base.close();
        }
    }

    private void actualizarListaProd() {
        abrirBase();
        tablaProd.setRowCount(0);
        prodlista = Articulo.where("codigo like ? or descripcion like ?", "%" + textcodprod.getText() + "%", "%" + textcodprod.getText() + "%");
        Iterator<Articulo> it = prodlista.iterator();
        while (it.hasNext()) {
            Articulo a = it.next();
            String rowArray[] = new String[4];
            rowArray[0] = a.getId().toString();
            rowArray[1] = a.getString("codigo");
            rowArray[2] = a.getString("marca");
            rowArray[3] = a.getString("descripcion");
            tablaProd.addRow(rowArray);
        }
        Articulo a = Articulo.findFirst("codigo = ?", textcodprod.getText());
        if (a != null) {
            String fram = a.getString("equivalencia_fram");
            if (!(fram.equals(""))) {
                prodlista = busqueda.filtroProducto2(fram);
                it = prodlista.iterator();
                while (it.hasNext()) {
                    Articulo b = it.next();
                    if (!(b.getInteger("id").equals(a.getInteger("id")))) {
                        String rowArray[] = new String[4];
                        rowArray[0] = b.getId().toString();
                        rowArray[1] = b.getString("codigo");
                        rowArray[2] = b.getString("marca");
                        rowArray[3] = a.getString("descripcion");
                        tablaProd.addRow(rowArray);
                        {
                        }
                    }
                }

            }
        }
        if (Base.hasConnection()) {
            Base.close();
        }
    }

    private void abrirBase() {
        if (!Base.hasConnection()) {
            try {
                Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://" + ManejoIp.ipServer + "/lubricentro", "tecpro", "tecpro");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocurrió un error, no se realizó la conexión con el servidor, verifique la conexión \n " + e.getMessage(), null, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean existeProdFacc(int id) {
        boolean ret = false;
        for (int i = 0; i < tablafac.getRowCount() && !ret; i++) {
            ret = (Integer) tablafac.getValueAt(i, 0) == id;
        }
        return ret;
    }

    public void setCellEditor() {
        for (int i = 0; i < tablafac.getRowCount(); i++) {
            tablafac.getCellEditor(i, 1).addCellEditorListener(this);
            tablafac.getCellEditor(i, 3).addCellEditorListener(this);
        }
    }

    public void actualizarPrecio() {
        BigDecimal importe;
        BigDecimal total = new BigDecimal(0);
        for (int i = 0; i < tablafac.getRowCount(); i++) {
            importe = ((BigDecimal) tablafac.getValueAt(i, 1)).multiply((BigDecimal) ventaGui.getTablaFactura().getValueAt(i, 4)).setScale(2, RoundingMode.CEILING);
            tablafac.setValueAt(importe, i, 6);
        }
        for (int i = 0; i < tablafac.getRowCount(); i++) {
            total = total.add((BigDecimal) tablafac.getValueAt(i, 6)).setScale(2, RoundingMode.CEILING);;
        }
        ventaGui.getTotalFactura().setText(total.toString());
    }

    @Override
    public void editingStopped(ChangeEvent e) {
        actualizarPrecio();
    }

    @Override
    public void editingCanceled(ChangeEvent e) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
