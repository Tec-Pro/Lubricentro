/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import interfaz.AplicacionGui;
import interfaz.ArticuloGui;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author nico
 */
public class ControladorApliacion {
    private AplicacionGui aplicacionGui;
    private ArticuloGui articuloGui;
    private ControladorArticulo controladorArticulo;

    public ControladorApliacion() {
                JFrame.setDefaultLookAndFeelDecorated(true); //Le agrego un tema lindo al programa
        try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        aplicacionGui= new AplicacionGui();
        aplicacionGui.setVisible(true);
        articuloGui= new ArticuloGui();
        controladorArticulo= new ControladorArticulo(articuloGui);
        aplicacionGui.getContenedor().add(articuloGui);
        articuloGui.setVisible(true);
        
    }
    
  public static void main(String[] args) {
      ControladorApliacion controladorAplicacion= new ControladorApliacion();
      
} 
    
}


