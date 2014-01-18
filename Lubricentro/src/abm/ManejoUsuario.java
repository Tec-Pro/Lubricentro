/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import java.util.Arrays;
import modelos.Usuario;
import org.javalite.activejdbc.Base;

/**
 *
 * @author jacinto
 */
public class ManejoUsuario {
    
      public void abrirBase(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/sexshop","root", "root");
    }
    
    public void crearUsuario(){
        abrirBase();
        if (Usuario.findAll().isEmpty()){
            Usuario nuevo = Usuario.createIt();            
        }
        Base.close();
    }
    
    public void modificarNombre(String nombre){
        abrirBase();
        Usuario u = Usuario.findById(1);
        u.set("nombre",nombre);
        Base.close();
    }
    
      public void modificarPass(String pass){
        abrirBase();
        Usuario u = Usuario.findById(1);
        u.set("pass",pass);
        Base.close();
    }
      
    	public boolean login(String user, char[] pass){
            abrirBase();
            Usuario u = Usuario.first("nombre = ?",user);
            if (u != null) {
                char[] correct = u.getString("pass").toCharArray();
                if (user.equals(u.getString("nombre")) && Arrays.equals(pass, correct)) {
                    Base.close();
                    return true;
                }
            }
            Base.close();
            return false;
	}
        
        
    
}
