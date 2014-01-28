/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;


import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import modelos.Envio;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

/**
 *
 * @author nico
 */
public class EmailThread extends Thread {
    
    public void run() {
        abrirBase();
        EnvioEmailControlador enviar = new EnvioEmailControlador();
        LazyList<Envio> list= Envio.findAll();
        if (!list.isEmpty()){
        Envio fechaUltEnvio = (Envio) Envio.findAll().get(0);
        Date fechaEnviado = fechaUltEnvio.getDate("fecha");
        Calendar fechaActualMenosMes= Calendar.getInstance();
        fechaActualMenosMes.add(Calendar.MONTH, -1);
        java.sql.Date sqlFecha = new java.sql.Date(fechaActualMenosMes.getTime().getTime());
        System.out.println(fechaEnviado.compareTo(sqlFecha));
        System.out.println(fechaEnviado.compareTo(fechaEnviado));
        System.out.println(fechaEnviado + " " + sqlFecha);
        if(sqlFecha.toString().equals(fechaEnviado.toString())|| !fechaUltEnvio.getBoolean("enviado")){
            System.out.println("booleano"+fechaUltEnvio.getBoolean("enviado"));
            Modulo moduloBackUp= new Modulo();
            moduloBackUp.CrearBackupSilencioso();
          try {
          enviar.enviarMail("", "", true);
         } catch (MessagingException ex) {
         Logger.getLogger(EmailThread.class.getName()).log(Level.SEVERE, null, ex);
         }
        }
        else
        cerrarBase();
        }
        else{
            Modulo moduloBackUp= new Modulo();
            moduloBackUp.CrearBackupSilencioso();
          try {
          enviar.enviarMail("", "", true);
         } catch (MessagingException ex) {
         Logger.getLogger(EmailThread.class.getName()).log(Level.SEVERE, null, ex);
         }
        }
        
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
