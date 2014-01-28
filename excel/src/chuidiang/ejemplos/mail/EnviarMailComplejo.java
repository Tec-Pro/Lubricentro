
/*
 * Fichero: EnviarMailComplejo.java
 * Autor: Chuidiang
 * Fecha: 6/04/07 16:38
 */
package chuidiang.ejemplos.mail;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


/**
 * Ejemplo de envio de un correo de texto con una imagen adjunta con javamail
 *
 * @author Chuidiang
 *
  */
public class EnviarMailComplejo
{
    /**
     * @param args se ignoran
     */
    public static void main(String[] args)
    {
        try
        {
          // se obtiene el objeto Session. La configuración es para
          // una cuenta de gmail.
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.live.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", "niqo.orcasitas@hotmail.com");
            props.setProperty("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props, null);
            // session.setDebug(true);

            // Se compone la parte del texto
            BodyPart texto = new MimeBodyPart();
            texto.setText("Texto del mensaje");

            // Se compone el adjunto con la imagen
            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(
                new DataHandler(new FileDataSource("/home/nico/Instaladores/Lubricentro/ba.sql")));
            adjunto.setFileName("futbol.gif");

            // Una MultiParte para agrupar texto e imagen.
            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(texto);
            multiParte.addBodyPart(adjunto);

            // Se compone el correo, dando to, from, subject y el
            // contenido.
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("niqo.orcasitas@hotmail.com"));
            message.addRecipient(
                Message.RecipientType.TO,
                new InternetAddress("niqo.orcasitas@hotmail.com"));
            message.setSubject("Holal");
            message.setContent(multiParte);

            // Se envia el correo.
            Transport t = session.getTransport("smtp");
            t.connect("niqo.orcasitas@hotmail.com", "nico.159357");
            System.out.println(t.isConnected());
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}