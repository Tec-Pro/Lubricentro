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
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelos.Articulo;
import modelos.ArticulosVentas;
import modelos.Cliente;
import modelos.Pago;
import modelos.Venta;
import org.javalite.activejdbc.Base;

/**
 *
 * @author jacinto
 */
public class RealizarPagoVentaControlador implements ActionListener {

    private PagoFacturaGui pagoFacturaGui;
    private Cliente cli;
    private BigDecimal ctaCte;
    private Busqueda busqueda;
    private AplicacionGui apgui;

    public RealizarPagoVentaControlador(PagoFacturaGui pagoFacturaGui, Cliente cli, BigDecimal ctaCte, AplicacionGui apgui) {
        this.apgui = apgui;
        this.pagoFacturaGui = pagoFacturaGui;
        this.pagoFacturaGui.setActionListener(this);
        this.cli = cli;
        this.ctaCte = ctaCte;
        CargarDatosCli();
        busqueda = new Busqueda();
    }

    RealizarPagoVentaControlador() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void CargarDatosCli() {
        pagoFacturaGui.setCliente(cli.getString("nombre"));
        pagoFacturaGui.setCuenta(ctaCte.toString());
        if (ctaCte.signum() == -1) {
            pagoFacturaGui.getCuenta().setForeground(Color.red);
            ctaCte = ctaCte.negate();
            pagoFacturaGui.setCliente(ctaCte.toString());
            ctaCte = ctaCte.negate();
        } else {
            pagoFacturaGui.getCuenta().setForeground(Color.black);
            pagoFacturaGui.setCliente(ctaCte.toString());
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pagoFacturaGui.getAceptar()) {
            if (pagoFacturaGui.getMonto().getText().equals("")) {
                JOptionPane.showMessageDialog(pagoFacturaGui, "Por favor ingrese el monto", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                abrirBase();
                String clienteId = cli.getString("id");
                int idCliente = cli.getInteger("id");
                BigDecimal entrega = new BigDecimal(pagoFacturaGui.getMonto().getText());
                Pago pago = new Pago();
                pago.set("fecha", pagoFacturaGui.getCalendarioText().getText());
                pago.set("monto", entrega);
                pago.set("cliente_id", idCliente);
                pago.saveIt();
                String pagoId = Pago.findFirst("fecha = ? and monto = ? and cliente_id = ?", pagoFacturaGui.getCalendarioText().getText(), entrega, idCliente).getString("id");
                BigDecimal cuentaCliente = new BigDecimal(cli.getString("cuenta"));
                entrega = entrega.add(cuentaCliente);
                Iterator<Venta> itrVenta = cargarDeuda(clienteId).iterator();
                Venta ventaAPagar = null;
                boolean sePuedePagar = true;
                BigDecimal montoVentaAPagar = new BigDecimal(0);
                itrVenta = cargarDeuda(clienteId).iterator();
                BigDecimal aux;
                ABMVenta ambV = new ABMVenta();
                while (sePuedePagar) {
                    sePuedePagar = false;
                    while (itrVenta.hasNext()) {
                        String ventaId = itrVenta.next().getString("id");
                        aux = montoVentaNoAbonada(ventaId);
                        if (entrega.compareTo(aux) >= 0) {
                            sePuedePagar = true;
                            if (montoVentaAPagar.compareTo(aux) <= 0) {
                                ventaAPagar = Venta.findById(ventaId);
                                montoVentaAPagar = new BigDecimal(aux.toString());
                            }
                        }
                    }
                    if (sePuedePagar) {
                        entrega = entrega.subtract(montoVentaAPagar);
                        ambV.pagar(ventaAPagar, montoVentaAPagar);
                        ventaAPagar.set("pago_id", pagoId);
                        ventaAPagar.saveIt();
                        itrVenta = cargarDeuda(clienteId).iterator();
                    }
                }
                cli.set("cuenta", entrega);
                cli.saveIt();
                JOptionPane.showMessageDialog(apgui, "¡Cobro registrado exitosamente!");
                cerrarBase();
            }
        }
        pagoFacturaGui.dispose();
        try {
            this.finalize();
        } catch (Throwable ex) {
            Logger.getLogger(RealizarPagoVentaControlador.class.getName()).log(Level.SEVERE, null, ex);
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

    private void cerrarBase() {
        if (Base.hasConnection()) {
            Base.close();
        }
    }

    public LinkedList<Venta> cargarDeuda(String id) {
        abrirBase();
        Iterator<Venta> itr = busqueda.filtroVenta(id, "0-0-0", "9999-0-0").iterator();
        LinkedList<Venta> retorno = new LinkedList<Venta>();
        while (itr.hasNext()) {
            Venta v = itr.next();
            if (!(v.getBoolean("pago"))) {
                retorno.add(v);
            }
        }
        return retorno;
    }

    public BigDecimal montoVentaNoAbonada(String id) {
        abrirBase();
        BigDecimal montox = null;
        BigDecimal cuenta;
        Iterator<ArticulosVentas> itr2 = busqueda.filtroVendidos(id).iterator();
        while (itr2.hasNext()) {
            ArticulosVentas arvs = itr2.next();
            Articulo art = Articulo.findById(arvs.getInteger("articulo_id"));
            cuenta = (art.getBigDecimal("precio_venta")).multiply(arvs.getBigDecimal("cantidad")).setScale(2, RoundingMode.CEILING);
            if (montox == null) {
                montox = new BigDecimal(String.valueOf((cuenta).setScale(2, RoundingMode.CEILING)));
            } else {
                montox = new BigDecimal(String.valueOf(montox.add(cuenta).setScale(2, RoundingMode.CEILING)));
            }
        }

        return montox.setScale(2, RoundingMode.CEILING);
    }
}
