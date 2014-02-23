/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import abm.ManejoIp;
import abm.ManejoUsuario;
import interfaz.AplicacionGui;
import interfaz.LoginGui;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import org.javalite.activejdbc.Base;

/**
 *
 * @author nico
 */
public class ControladorLogin extends Thread {
    
    private String user;
    private char[] pass;
    private ManejoUsuario mu;
    private AplicacionGui app;
    private LoginGui log;
    
    public ControladorLogin(AplicacionGui app) {
        this.app = app;
    }
    
    public void run() {
        mu = new ManejoUsuario();
        abrirBase();
        mu.crearUsuario();
        cerrarBase();
        log = new LoginGui();
        log.setLocationRelativeTo(null);
        log.setVisible(true);
        log.getPass().requestFocus();
        log.getPass().addKeyListener(new KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    log.getPass().setText("");
                    log.getPass().requestFocus();
                }
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    user = log.getUser().getText();
                    pass = log.getPass().getPassword();
                    abrirBase();
                    if (mu.login(user, pass)) {
                        log.dispose();
                        app.setVisible(true);
                        EmailThread emailThread = new EmailThread();
                        emailThread.run();
                    } else {
                        log.getPass().setText("");
                        JOptionPane.showMessageDialog(app, "INTENTE NUEVAMENTE", "¡DATOS INCORRECTOS!", JOptionPane.ERROR_MESSAGE);
                    }
                    cerrarBase();
                    
                }
            }
        });
    }
    
    public LoginGui getLog() {
        return log;
    }
    
    private void abrirBase() {
        if (!Base.hasConnection()) {
            try{             Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://"+ManejoIp.ipServer+"/lubricentro", "tecpro", "tecpro");             }catch(Exception e){                 JOptionPane.showMessageDialog(null, "Ocurrió un error, no se realizó la conexión con el servidor, verifique la conexión \n "+e.getMessage(),null,JOptionPane.ERROR_MESSAGE); }
        }
    }
    
    private void cerrarBase() {
        if (Base.hasConnection()) {
            Base.close();
        }
    }
}
