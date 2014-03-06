/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import abm.ManejoIp;
import busqueda.Busqueda;
import com.toedter.calendar.JDateChooser;
import interfaz.AplicacionGui;
import interfaz.ClienteGui;
import interfaz.HistorialComprasGui;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelos.Articulo;
import modelos.ArticulosVentas;
import modelos.Cliente;
import modelos.Pago;
import modelos.Venta;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

/**
 *
 * @author jacinto
 */
public class HistorialComprasControlador implements ActionListener {

    private AplicacionGui apgui;
    private HistorialComprasGui historialComprasGui;
    private ClienteGui clienteGui;
    private DefaultTableModel tablaHistorialDef;
    private JTable tablaHistorial;
    private Cliente cliente;
    private BigDecimal ctaCte;
    private Busqueda busqueda;
    private String desde;
    private String hasta;
    private JDateChooser calenDesde;
    private JDateChooser calenHasta;

    public HistorialComprasControlador(AplicacionGui apgui, HistorialComprasGui historialCompras, ClienteGui clienteGui, Cliente cliente, BigDecimal ctaCte) {
        abrirBase();
        this.apgui = apgui;
        this.historialComprasGui = historialCompras;
        this.clienteGui = clienteGui;
        this.cliente = cliente;
        this.ctaCte = ctaCte;
        busqueda = new Busqueda();
        CargarDatosCli();
        tablaHistorialDef = historialCompras.getTablaHistorialDefault();
        desde = "0-0-0";
        hasta = "9999-0-0";
        calenDesde = historialComprasGui.getDesde();
        calenHasta = historialComprasGui.getHasta();
        historialComprasGui.getDesde().getDateEditor().getUiComponent().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                desde = ((JTextField) historialComprasGui.getDesde().getDateEditor().getUiComponent()).getText();
                cargarHistorial();
            }
        });
        historialComprasGui.getHasta().getDateEditor().getUiComponent().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                hasta = ((JTextField) historialComprasGui.getHasta().getDateEditor().getUiComponent()).getText();
                cargarHistorial();
            }
        });
        calenDesde.getJCalendar().addPropertyChangeListener("calendar", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                calenDesdePropertyChange(e);
            }
        });
        calenHasta.getJCalendar().addPropertyChangeListener("calendar", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                calenHastaPropertyChange(e);
            }
        });

        cargarHistorial();
    }
    
     public void calenDesdePropertyChange(PropertyChangeEvent e) {
        final Calendar c = (Calendar) e.getNewValue();
        desde = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DATE);
        cargarHistorial();
    }

    public void calenHastaPropertyChange(PropertyChangeEvent e) {
        final Calendar c = (Calendar) e.getNewValue();
        hasta = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DATE);
        cargarHistorial();
    }

    private void CargarDatosCli() {
        historialComprasGui.setNombre(cliente.getString("nombre"));
        historialComprasGui.setCuenta(ctaCte.toString());
        if (ctaCte.signum() == -1) {
            historialComprasGui.getCuenta().setForeground(Color.red);
            historialComprasGui.setNombre(ctaCte.negate().toString());
        } else {
            historialComprasGui.getCuenta().setForeground(Color.black);
            historialComprasGui.setNombre(ctaCte.toString());
        }
    }

    private void cargarHistorial() {
        tablaHistorialDef.setRowCount(0);
        Iterator<Venta> itr = (busqueda.filtroVenta(cliente.getString("id"), desde, hasta)).iterator();
        while (itr.hasNext()) {
            Venta v = itr.next();
            String row[] = new String[6];
            row[0] = v.getDate("fecha").toString();
            row[1] = "";
            row[2] = "";
            row[3] = "";
            if (v.getBoolean("pago")) {
                row[4] = ("Si");
            } else {
                row[4] = ("No");
            }
            if (v.getBoolean("pago")) {
                Pago p = v.parent(Pago.class);
                if (!(p == null)) {
                    row[5] = p.getDate("fecha").toString();
                } else {
                    row[5] = "";
                }
            } else {
                row[5] = "";
            }
            tablaHistorialDef.addRow(row);
            cargarArtV(v, tablaHistorialDef.getRowCount());
        }
    }

    private void cargarArtV(Venta v, int countRow) {
        tablaHistorialDef.setRowCount(countRow);
        LazyList<ArticulosVentas> pr = ArticulosVentas.find("venta_id = ?", v.getInteger("id"));
        Iterator<ArticulosVentas> it = pr.iterator();
        while (it.hasNext()) {
            ArticulosVentas prod = it.next();
            Articulo producto = Articulo.findFirst("id = ?", prod.get("articulo_id"));
            if (producto != null) {
                BigDecimal cantidad = prod.getBigDecimal("cantidad").setScale(2, RoundingMode.CEILING);
                String row[] = new String[6];
                row[0] = "";
                row[1] = producto.getString("codigo");
                row[2] = producto.getString("descripcion");
                row[3] = cantidad.toString();
                row[4] = "";
                row[5] = "";
                tablaHistorialDef.addRow(row);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == historialComprasGui.getVolver()) {
            clienteGui.setVisible(true);
            clienteGui.toFront();
            historialComprasGui.dispose();
            try {
                this.finalize();
            } catch (Throwable ex) {
                Logger.getLogger(HistorialComprasControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == historialComprasGui.getBuscar()) {
            cargarHistorial();
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
}
