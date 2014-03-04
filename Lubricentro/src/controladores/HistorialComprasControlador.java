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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
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
    private JDateChooser desde;
    private JDateChooser hasta;

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
        desde = historialCompras.getDesde();
        hasta = historialCompras.getHasta();
        cargarHistorial();
    }

    private void CargarDatosCli() {
        historialComprasGui.setNombre(cliente.getString("nombre"));
        historialComprasGui.setCuenta(ctaCte.toString());
        if (ctaCte.signum() == -1) {
            historialComprasGui.getCuenta().setForeground(Color.red);
            ctaCte = ctaCte.negate();
            historialComprasGui.setNombre(ctaCte.toString());
            ctaCte = ctaCte.negate();
        } else {
            historialComprasGui.getCuenta().setForeground(Color.green);
            historialComprasGui.setNombre(ctaCte.toString());
        }
    }

    private void cargarHistorial() {
        tablaHistorialDef.setRowCount(0);
        Iterator<Venta> itr = busqueda.filtroVenta(cliente.getString("id"), desde.getDateFormatString(), hasta.getDateFormatString()).iterator();
        while (itr.hasNext()) {
            Venta v = itr.next();
            String row[] = new String[6];
            row[0] = v.getDate("fecha").toString();
            row[1] = "";
            row[2] = "";
            row[3] = "";
            row[4] = v.getBoolean("pago").toString();
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
