/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import interfaz.ConfigurarServerGui;
import java.util.Arrays;
import modelos.Ip;
import org.javalite.activejdbc.Base;

/**
 *
 * @author nico
 */
public class ManejoIp {

    public static String ipServer="";
    public static  boolean servidor;
    
    public void crearIp() {
        if (Ip.findAll().isEmpty()) {
            Base.openTransaction();
                    Base.commitTransaction();
                    new ConfigurarServerGui(null, true).setVisible(true);
        }
    }

    public void modificarIp(String ipNueva) {
        Ip ip = Ip.findById(1);
        Base.openTransaction();
        ip.set("remoto", ipNueva);
        ip.save();
        Base.commitTransaction();
    }

    public void modificarIsServidor(boolean si) {
        Ip ip = Ip.findById(1);
        Base.openTransaction();
        ip.setBoolean("servidor", si);
        ip.save();
        Base.commitTransaction();
    }

    public boolean modificarDatos(String ipNueva, boolean isServer) {
        Ip u = Ip.findById(1);
        if(u!=null){
        Base.openTransaction();
        u.set("remoto", ipNueva, "servidor", isServer);
        boolean ret = u.save();
        Base.commitTransaction();
        return ret;
        }
        else{
          Base.openTransaction();
        u= Ip.create("remoto", ipNueva, "servidor", isServer);
        boolean ret = u.save();
        Base.commitTransaction();
        return ret;  
        }
    }

    public void conseguirDatos(){
        Ip u = Ip.findById(1);
        ipServer= u.getString("remoto");
        servidor= u.getBoolean("servidor");
    }
}
