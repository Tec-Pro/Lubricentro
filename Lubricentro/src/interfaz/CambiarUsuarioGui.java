/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import abm.ManejoIp;
import abm.ManejoUsuario;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import javax.swing.JOptionPane;
import modelos.Usuario;
import org.javalite.activejdbc.Base;

/**
 *
 * @author nico
 */
public class CambiarUsuarioGui extends javax.swing.JDialog {

    /**
     * Creates new form CambiarUsuarioGui
     */
    public CambiarUsuarioGui(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        if (!Base.hasConnection()) {
            try{             Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://"+ManejoIp.ipServer+"/lubricentro", "tecpro", "tecpro");             }catch(Exception e){                 JOptionPane.showMessageDialog(null, "Ocurrió un error, no se realizó la conexión con el servidor, verifique la conexión \n "+e.getMessage(),null,JOptionPane.ERROR_MESSAGE); }
        }
        Usuario usuario = Usuario.findById(1);
        this.user.setText(usuario.getString("nombre"));
        if (Base.hasConnection()) {
            Base.close();
        }
        this.pass.requestFocus();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        user = new javax.swing.JTextField();
        pass = new javax.swing.JPasswordField();
        repetirPass = new javax.swing.JPasswordField();
        jPanel1 = new javax.swing.JPanel();
        cancelar = new javax.swing.JButton();
        aceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Usuario:");

        jLabel2.setText("Repita la contraseña:");

        jLabel3.setText("Contraseña:");

        user.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                userKeyPressed(evt);
            }
        });

        pass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passKeyPressed(evt);
            }
        });

        repetirPass.setNextFocusableComponent(aceptar);
        repetirPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                repetirPassKeyPressed(evt);
            }
        });

        jPanel1.setLayout(new java.awt.GridLayout());

        cancelar.setText("Cancelar");
        cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarActionPerformed(evt);
            }
        });
        jPanel1.add(cancelar);

        aceptar.setText("Aceptar");
        jPanel1.add(aceptar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(user)
                    .addComponent(pass)
                    .addComponent(repetirPass, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(repetirPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void userKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_userKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            user.setText("");
            user.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pass.requestFocus();
        }            // TODO add your handling code here:
    }//GEN-LAST:event_userKeyPressed

    private void passKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            pass.setText("");
            pass.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            repetirPass.requestFocus();
        }            // TODO add your handling code here:
    }//GEN-LAST:event_passKeyPressed

    private void repetirPassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_repetirPassKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            repetirPass.setText("");
            repetirPass.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            ManejoUsuario abm = new ManejoUsuario();
            if (Arrays.equals(pass.getPassword(), repetirPass.getPassword())) {
                if (!Base.hasConnection()) {
                    try{             Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://"+ManejoIp.ipServer+"/lubricentro", "tecpro", "tecpro");             }catch(Exception e){                 JOptionPane.showMessageDialog(null, "Ocurrió un error, no se realizó la conexión con el servidor, verifique la conexión \n "+e.getMessage(),null,JOptionPane.ERROR_MESSAGE); }
                    
                }
                boolean res=abm.modificarDatos(user.getText(),String.valueOf(pass.getPassword()));
                if(res){
                    JOptionPane.showMessageDialog(this, "Cambios realizados con éxito", null, JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                }
                else
                    JOptionPane.showMessageDialog(this, "Ocurrió un error, intente nuevamente", null, JOptionPane.ERROR_MESSAGE);
                if (Base.hasConnection()) {
                    Base.close();
                }
            }
            else{
                JOptionPane.showMessageDialog(this, "¡Contraseñas distintas!", null, JOptionPane.ERROR_MESSAGE); 
            }
        }            // TODO add your handling code here:
    }//GEN-LAST:event_repetirPassKeyPressed

    private void cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CambiarUsuarioGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CambiarUsuarioGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CambiarUsuarioGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CambiarUsuarioGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CambiarUsuarioGui dialog = new CambiarUsuarioGui(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aceptar;
    private javax.swing.JButton cancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField pass;
    private javax.swing.JPasswordField repetirPass;
    private javax.swing.JTextField user;
    // End of variables declaration//GEN-END:variables
}
