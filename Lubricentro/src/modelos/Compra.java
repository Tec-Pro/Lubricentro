/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.LinkedList;
import org.javalite.activejdbc.Model;
import net.sf.jasperreports.engine.util.Pair;

/**
 *
 * @author jacinto
 */
public class Compra extends Model{
    
    public Compra() {
        this.productos = null;
    }
   
    //Lista de pares <Producto,cantidad>
    private LinkedList<Pair> productos;

    public LinkedList<Pair> getProductos() {
        return productos;
    }

    public void setProductos(LinkedList<Pair> productos) {
        this.productos = productos;
    }
}
