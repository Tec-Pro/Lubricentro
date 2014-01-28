 
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class Mensaje {
        static String usuarioEmisorMensaje = "niqo.orcasitas@hotmail.com";
        //password real de la cuenta usuarioEmisorMensaje
        static String passwordEmisorMensaje = "nico.159357";
        //Dirección del servidor para este protocolo (SMTP)
        static String smtpHost = "smtp.live.com";
        //Puerto que se usará en el servidor.
        static String smtpPuerto = "465";
        //Indicamos que vamos a auntenticarnos en el servidor
        static String smtpAuth = "true";
        static Properties props = new Properties();
   
    public static void enviarMensaje(){
        //Asiganamos algunas propiedades
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPuerto);
        props.put("mail.smtp.auth", smtpAuth);
       
        //Se obtiene una sesión con las propiedades anteriormente que hemos
        //guardado en -props-
        Session sesion = Session.getDefaultInstance(props, null);
       
        try {
            //Empezamos a crear el e-mail
            Message mensaje = new MimeMessage(sesion);
            //Rellenamos los campos necesarios de un e-mail
            //El asunto
            mensaje.setSubject("Mensajes desde Java con una cuenta de Hotmail");
            // Emisor del mensaje
            mensaje.setFrom(new InternetAddress(usuarioEmisorMensaje));
           
            //En este caso tenemos uno o varios receptores
            Address [] receptores = new Address []{
                new InternetAddress ("niqo.orcasitas@hotmail.com"),
                    //vemos que nuestros contactos pueden ser de distintos servicios
                //Hotmail, Gmail, etc
            };
            //Agregamos la lista de los receptores.
            mensaje.addRecipients(Message.RecipientType.TO, receptores);
            //Aquí va el contenido del mensaje
            mensaje.setText("Cuerpo de nuestro e-mail");
            //Ahora vamos a enviar el mensaje
            Transport t = sesion.getTransport("smtp");
            //Pero antes tenemos que auntenticarnos con una cuenta real de
            //Hotmail
            t.connect(usuarioEmisorMensaje, passwordEmisorMensaje);
            t.sendMessage(mensaje, mensaje.getRecipients(Message.RecipientType.TO));
        }catch(MessagingException e) {
            System.err.println(e.getMessage());
        }
    }
    
        public static void main(String[] args) {
        Mensaje.enviarMensaje();
        System.out.println("Mensaje Enviado!!!");
    }
}
