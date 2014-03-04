/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Calendar;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import modelos.Cliente;
import modelos.Venta;

/**
 *
 * @author jacinto
 */
public class PagoFacturaGui extends javax.swing.JInternalFrame {

    
    /**
     * Creates new form PagoFacturaGui
     */
    public PagoFacturaGui() {
        initComponents();
        Calendar miCalendario = Calendar.getInstance();
        java.util.Date eldia = miCalendario.getTime();
        int diaHoy = miCalendario.get(Calendar.DAY_OF_MONTH);
        int mes = miCalendario.get(Calendar.MONTH);
        int anio = miCalendario.get(Calendar.YEAR);
        calendario.setDate(Date.valueOf(anio + "-" + (mes + 1) + "-" + diaHoy));
    }

  
    public JTextField getMonto() {
        return monto;
    }

    public JToggleButton getAceptar() {
        return aceptar;
    }

    public JDateChooser getCalendario() {
        return calendario;
    }

     public JTextField getCalendarioText() {
        return ((JTextField) calendario.getDateEditor().getUiComponent());
    }
    
    public void setActionListener(ActionListener lis) {
        this.monto.addActionListener(lis);
        this.aceptar.addActionListener(lis);
    }

    public JLabel getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente.setText(cliente);
    }

    public JLabel getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta.setText(cuenta);
    }

    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        monto = new javax.swing.JTextField();
        aceptar = new javax.swing.JToggleButton();
        jLabel2 = new javax.swing.JLabel();
        calendario = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        cliente = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cuenta = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

        aceptar.setText("Aceptar");

        jLabel2.setText("Monto");

        calendario.setDateFormatString("yyyy-MM-dd");

        jLabel4.setFont(new java.awt.Font("Century Schoolbook L", 2, 15)); // NOI18N
        jLabel4.setText("Cobro realizado a :");

        cliente.setForeground(new java.awt.Color(255, 0, 0));

        jLabel5.setText("Cuenta corriente actual:");

        cuenta.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Century Schoolbook L", 2, 15)); // NOI18N
        jLabel6.setText("El día:");

        jLabel7.setFont(new java.awt.Font("Century Schoolbook L", 2, 15)); // NOI18N
        jLabel7.setText("$");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(cuenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(12, 12, 12))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(monto, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(jLabel7))
                                    .addComponent(calendario, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel2)
                            .addComponent(aceptar))
                        .addGap(0, 69, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cuenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(calendario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(monto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addComponent(aceptar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton aceptar;
    private com.toedter.calendar.JDateChooser calendario;
    private javax.swing.JLabel cliente;
    private javax.swing.JLabel cuenta;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField monto;
    // End of variables declaration//GEN-END:variables
}
