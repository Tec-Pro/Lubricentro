/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import abm.ManejoUsuario;
import interfaz.AplicacionGui;
import interfaz.LoginGui;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.swing.JOptionPane;
import modelos.Envio;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

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
                        EmailThread emailThread= new EmailThread();
                        emailThread.run();
                    } else {
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
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/lubricentro", "root", "root");
        }
    }

    private void cerrarBase() {
        if (Base.hasConnection()) {
            Base.close();
        }
    }

    
}
