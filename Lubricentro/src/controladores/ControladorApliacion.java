/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import interfaz.AplicacionGui;
import interfaz.ArticuloGui;
import interfaz.ProveedorGui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author nico
 */
public class ControladorApliacion implements ActionListener {
    private AplicacionGui aplicacionGui;
    private ArticuloGui articuloGui;
    private ControladorArticulo controladorArticulo;
    private ControladorProveedor controladorProveedor;
    private ProveedorGui proveedorGui;
    

    public ControladorApliacion() throws JRException, ClassNotFoundException, SQLException {
                JFrame.setDefaultLookAndFeelDecorated(true); //Le agrego un tema lindo al programa
        try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        aplicacionGui= new AplicacionGui();
        aplicacionGui.setActionListener(this);
        aplicacionGui.setExtendedState(JFrame.MAXIMIZED_BOTH);
        articuloGui= new ArticuloGui();
        proveedorGui= new ProveedorGui();
        controladorProveedor= new ControladorProveedor(proveedorGui);
        controladorArticulo= new ControladorArticulo(articuloGui);
        aplicacionGui.getContenedor().add(proveedorGui);
        aplicacionGui.getContenedor().add(articuloGui);
        aplicacionGui.setVisible(true);
        
    }
    
  public static void main(String[] args)  throws InterruptedException, ClassNotFoundException, SQLException, JRException  {
      ControladorApliacion controladorAplicacion= new ControladorApliacion();
      
} 

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource()== aplicacionGui.getArticulos()){
                articuloGui.setVisible(true);
        }
        if(ae.getSource()==aplicacionGui.getProveedores()){
            proveedorGui.setVisible(true);
        }
    }
    
}


