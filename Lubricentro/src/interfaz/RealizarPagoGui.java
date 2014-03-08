/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import abm.ManejoIp;
import controladores.TratamientoString;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import javax.swing.JOptionPane;
import modelos.Compra;
import modelos.Pago;
import modelos.Proveedor;
import modelos.Venta;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.jdesktop.swingx.renderer.FormatStringValue;

/**
 *
 * @author nico
 */
public class RealizarPagoGui extends javax.swing.JDialog {

    Proveedor prov;
    Float totalFactura = new Float(0);
    Float totalConDescuentoFloat = new Float(0);
    Float porcentaje = new Float(0);
    Compra compra;

    /**
     * Creates new form RealizarPagoGui
     */
    public RealizarPagoGui(java.awt.Frame parent, boolean modal, Proveedor prov) {
        super(parent, modal);
        initComponents();
        setEnabledPanelFac(false);
        totalConDescuento.setText("");
        numeroFac.setText("");
        totalFac.setText("");
        descuento.setText("");
        this.prov = prov;
        abrirBase();
        cuenta.setText(new DecimalFormat("#########.##").format(prov.getFloat("cuenta_corriente")));
        cerrarBase();
        proveedor.setText(prov.getString("nombre"));
        fecha.setDate(Calendar.getInstance().getTime());
        monto.requestFocus();
    }

    public RealizarPagoGui(java.awt.Frame parent, boolean modal, Proveedor prov, Compra compra) {
        super(parent, modal);
        initComponents();
        setEnabledPanelFac(true);
        this.prov = prov;
        abrirBase();
        totalConDescuento.setText("");
        numeroFac.setText("");
        totalFac.setText("");
        descuento.setText("");
        this.compra = compra;
        if (compra != null) {
            totalFac.setText(compra.getString("monto"));
            numeroFac.setText(compra.getString("id"));
            monto.setText(compra.getString("monto"));
        }
        cuenta.setText(new DecimalFormat("#########.##").format(prov.getFloat("cuenta_corriente")));
        cerrarBase();
        proveedor.setText(prov.getString("nombre"));
        fecha.setDate(Calendar.getInstance().getTime());
        descuento.requestFocus();
        descuento.setText("0");

        monto.setEnabled(false);

    }

    private void setEnabledPanelFac(boolean si) {
        jPanel2.setEnabled(si);
        jLabel10.setEnabled(si);
        jLabel7.setEnabled(si);
        jLabel8.setEnabled(si);
        jLabel9.setEnabled(si);
        descuento.setEnabled(si);
        totalConDescuento.setEnabled(si);
        totalFac.setEnabled(si);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fecha = new org.jdesktop.swingx.JXDatePicker();
        jLabel1 = new javax.swing.JLabel();
        proveedor = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        monto = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        aceptar = new javax.swing.JButton();
        cancelar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        cuenta = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        numeroFac = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        totalFac = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        descuento = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        totalConDescuento = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Realizar pago ");

        fecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fechaActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Century Schoolbook L", 2, 15)); // NOI18N
        jLabel1.setText("Pago realizado a :");

        proveedor.setForeground(new java.awt.Color(255, 0, 0));

        jLabel2.setFont(new java.awt.Font("Century Schoolbook L", 2, 15)); // NOI18N
        jLabel2.setText("El día:");

        monto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                montoActionPerformed(evt);
            }
        });
        monto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                montoFocusLost(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Century Schoolbook L", 2, 15)); // NOI18N
        jLabel4.setText("$");

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        aceptar.setText("ACEPTAR");
        aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarActionPerformed(evt);
            }
        });
        jPanel1.add(aceptar);

        cancelar.setText("CANCELAR");
        cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarActionPerformed(evt);
            }
        });
        jPanel1.add(cancelar);

        jLabel5.setText("Cuenta corriente actual:");

        cuenta.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de la compra"));

        jLabel6.setText("Numero de factura:");

        numeroFac.setText("numfacturita");

        jLabel7.setText("Total de factura:");

        totalFac.setForeground(new java.awt.Color(255, 0, 0));
        totalFac.setText("1928.49");

        jLabel8.setText("Descuento en %:");

        descuento.setForeground(new java.awt.Color(40, 164, 14));
        descuento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                descuentoFocusLost(evt);
            }
        });
        descuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                descuentoKeyPressed(evt);
            }
        });

        jLabel9.setText("Total ");

        totalConDescuento.setForeground(new java.awt.Color(40, 164, 14));
        totalConDescuento.setText("1292.90");

        jLabel10.setText("(Sin descuento)");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(numeroFac, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(totalFac, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator1)
                            .addComponent(descuento)
                            .addComponent(totalConDescuento, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(numeroFac))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(totalFac)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(descuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(totalConDescuento))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Century Schoolbook L", 2, 15)); // NOI18N
        jLabel3.setText("Realizo un pago de");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(proveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(cuenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(12, 12, 12))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(monto, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jLabel4)
                        .addGap(0, 58, Short.MAX_VALUE))))
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cuenta, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(monto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fechaActionPerformed
    }//GEN-LAST:event_fechaActionPerformed

    private void montoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_montoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_montoActionPerformed

    private void cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelarActionPerformed

    private void aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarActionPerformed
        abrirBase();
        //try {
        java.sql.Date sqlFecha = new java.sql.Date(fecha.getDate().getTime());
        Pago pago = Pago.createIt("fecha", sqlFecha, "monto", monto.getText());
        pago.saveIt();
        prov.add(pago);
        String pagoId = pago.getString("id");
        if (compra == null) {
            Float entrega = pago.getFloat("monto");//pago
            Float cuentaCorriente = prov.getFloat("cuenta_corriente");//CC
            //entrega = entrega + cuentaCorriente; //4+(-6)=-2
            LazyList<Compra> compras = Compra.where("pago = ? and proveedor_id = ?", 0, prov.getId()).orderBy("fecha");
            Float deuda=calcularDeuda(compras);
            Float dif= deuda+cuentaCorriente;
            Iterator<Compra> it = compras.iterator();
            boolean sePuedePagar = true;
            Float entreMasDif= entrega+dif;
            Compra compraAPagar;
            while (sePuedePagar && it.hasNext()) {
                sePuedePagar = false;
                compraAPagar = it.next();
                if (entreMasDif>= compraAPagar.getFloat("monto")) {
                    sePuedePagar= true;
                    System.out.println("estoy pagando");
                        Base.openTransaction();
                        compraAPagar.setBoolean("pago", true);
                        compraAPagar.set("fecha_pago", sqlFecha);
                        compraAPagar.set("pago_id", pagoId);
                        compraAPagar.save();
                        entreMasDif= entreMasDif-compraAPagar.getFloat("monto");
                        Base.commitTransaction();
                }
            }
                Base.openTransaction();
                System.out.println(cuentaCorriente+entrega);
                prov.set("cuenta_corriente", cuentaCorriente+entrega);
                prov.saveIt();
                Base.commitTransaction();
            }
        else {

            Base.openTransaction();
            compra.setBoolean("pago", true);
            compra.set("fecha_pago", sqlFecha);
            compra.set("descuento", porcentaje);
            compra.set("pago_id", pagoId);
            compra.save();
            prov.set("cuenta_corriente", prov.getFloat("cuenta_corriente")+pago.getFloat("monto"));
            prov.saveIt();
            Base.commitTransaction();
        }//else{

        // }


        cerrarBase();
        JOptionPane.showMessageDialog(this, "¡Pago registrado correctamente");
        this.dispose();
        //} catch (Exception ex) {
        //    JOptionPane.showMessageDialog(this, "Ocurrió un error","Error",JOptionPane.ERROR_MESSAGE);
        //}
    

    }//GEN-LAST:event_aceptarActionPerformed

    private void montoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_montoFocusLost
    }//GEN-LAST:event_montoFocusLost

    private void descuentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_descuentoFocusLost
        totalFactura = Float.valueOf(totalFac.getText());
        try {
            porcentaje = Float.valueOf(descuento.getText());
            totalConDescuentoFloat = totalFactura - (porcentaje * totalFactura / 100);
            totalConDescuento.setText(new DecimalFormat("#########.##").format(totalConDescuentoFloat));
            monto.setText(new DecimalFormat("#########.##").format(totalConDescuentoFloat));
        } catch (NumberFormatException | ClassCastException e) {
            JOptionPane.showMessageDialog(this, "Error en el porcentaje", "Error!", JOptionPane.ERROR_MESSAGE);
        }    }//GEN-LAST:event_descuentoFocusLost

    private void descuentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_descuentoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            totalFactura = Float.valueOf(totalFac.getText());
            try {
                porcentaje = Float.valueOf(descuento.getText());
                totalConDescuentoFloat = totalFactura - (porcentaje * totalFactura / 100);
                totalConDescuento.setText(new DecimalFormat("#########.##").format(totalConDescuentoFloat));
                monto.setText(new DecimalFormat("#########.##").format(totalConDescuentoFloat));
            } catch (NumberFormatException | ClassCastException e) {
                JOptionPane.showMessageDialog(this, "Error en el porcentaje", "Error!", JOptionPane.ERROR_MESSAGE);
            }
            monto.requestFocus();
        }
    }//GEN-LAST:event_descuentoKeyPressed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aceptar;
    private javax.swing.JButton cancelar;
    private javax.swing.JLabel cuenta;
    private javax.swing.JTextField descuento;
    private org.jdesktop.swingx.JXDatePicker fecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField monto;
    private javax.swing.JLabel numeroFac;
    private javax.swing.JLabel proveedor;
    private javax.swing.JLabel totalConDescuento;
    private javax.swing.JLabel totalFac;
    // End of variables declaration//GEN-END:variables

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
    
    private Float calcularDeuda(LazyList<Compra> compras){
        Iterator<Compra> calcularDeuda= compras.iterator();
            Float deuda=new Float(0);
            while (calcularDeuda.hasNext()){
                deuda= deuda+(calcularDeuda.next().getFloat("monto"));
            }
            return deuda;
    }
}
