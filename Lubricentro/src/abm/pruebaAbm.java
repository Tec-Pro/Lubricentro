/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import modelos.Articulo;
import org.javalite.activejdbc.Base;

/**
 *
 * @author nico
 */
public class pruebaAbm {

    /**
     * @param args the command line arguments
     */
        
    public static void main(String[] args) {
                if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/lubricentro", "root", "root");
        }
        ABMArticulo abm= new ABMArticulo();
        Articulo art= new Articulo();
        art.set("codigo","1");
        art.set("descripcion","descripcion1");
        art.set("marca","marca");
        art.set("stock",12);
        art.set("precio_compra",12.12);
        art.set("precio_venta",13.13);
        art.set("equivalencia_fram","equivalancia");

        abm.alta(art);
        art= Articulo.findFirst("codigo = ?", 1);
        System.out.println(abm.baja(art));
        Base.close();
        
    }
}
