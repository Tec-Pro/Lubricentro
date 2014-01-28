
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nico
 */
public class GeneradorBackup {
    public void transfer(InputStream input, OutputStream output) throws Exception {
byte[] buf = new byte[1024];
int len;
while ((len = input.read(buf)) > 0) {
output.write(buf, 0, len);
}
input.close();
output.close();
}

public void generarBackup() throws IOException, Exception {
System.err.println("INICIANDO LA GENERACIÓN DE BACKUPS");
String command = "mysqldump --opt --host=localhost --password=root --user=root --databases lubricentro";

java.lang.Process child = Runtime.getRuntime().exec(command);
InputStream input = child.getInputStream();

FileOutputStream output = new FileOutputStream("/home/nico/Escritorio/backUp.sql");
try {
transfer(input, output);
System.err.println("TERMINO DE BACKUPS CONFORME");
} catch (Exception e) {
System.err.println("Se manejo una excepcion: " + e.getMessage());
}
}

public void RestaurarBackup() throws SQLException{
Statement sentencia = null;
Connection coneccionini = null;
conectar();
if (selecRestauraBack==1){
     if (conn!=null){
          try {
               coneccionini = DriverManager.getConnection(urlcero, login, password);
               sentencia = coneccionini.createStatement();
               String comsSQLborra = "DROP DATABASE " + bd;
               sentencia.executeUpdate(comsSQLborra);
               coneccionini = DriverManager.getConnection(urlcero, login, password);
               sentencia = coneccionini.createStatement();
               String comsSQL = "CREATE DATABASE "+bd;
               sentencia.executeUpdate(comsSQL);
               Process child = Runtime.getRuntime().exec("cmd /c mysql --password="+password+" --user="+login+" "+bd+" < " +nombrebackup);
            JOptionPane.showMessageDialog(null,"Backup restaurado exitosamente!");
         } catch (IOException ex) {}
      catch (SQLException ex) {}
}else if (conn==null){ 
try {
   coneccionini = DriverManager.getConnection(urlcero, login, password);
   sentencia = coneccionini.createStatement();
    String comsSQL = "CREATE DATABASE "+bd; 
     sentencia.executeUpdate(comsSQL);
      Process child = Runtime.getRuntime().exec("cmd /c mysql --password="+password+" --user="+login+" "+bd+" < " +nombrebackup);
        JOptionPane.showMessageDialog(null,"Backup restaurado exitosamente!");
     }catch (IOException ex) { }
 catch (SQLException ex) { }
}
 }else if (selecRestauraBack==0){
     JOptionPane.showMessageDialog(null,"No se seleccionó ningun archivo de Backup!");
 } 
}

//CARGAR EL VOID MAIN: Ejem. GeneradorBackup
public static void main(String[] args) {
GeneradorBackup generar = new GeneradorBackup();
try {
generar.generarBackup();
} catch (IOException ex) {
Logger.getLogger(GeneradorBackup.class.getName()).log(Level.SEVERE, null, ex);
System.err.println("Error con IO exception");
ex.printStackTrace();
} catch (Exception ex) {
Logger.getLogger(GeneradorBackup.class.getName()).log(Level.SEVERE, null, ex);
System.err.println("Error con exception simple");
ex.printStackTrace();
}
}
}
