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
public class ABMArticulo {
 
    
        public Articulo getProducto(Articulo p){
        return Articulo.first("codigo =?", p.get("codigo"));
    }
    public boolean findArticulo(Articulo p){
        return (Articulo.first("codigo = ?",p.get("codigo"))!= null);
    }
    
    
    public boolean alta(Articulo art) {
        //Proveedor pr = Proveedor.first("nombre = ?", art.getNombreProv());
        if (!findArticulo(art)){
                Base.openTransaction();
                Articulo nuevo = Articulo.create("codigo",art.get("codigo"),"descripcion",art.get("descripcion"),"marca",art.get("marca"),"stock",art.get("stock"),"stock_minimo",art.get("stock_minimo"),"precio_compra",art.get("precio_compra"),"precio_venta",art.get("precio_venta"),"equivalencia_fram",art.get("equivalencia_fram"),"proveedor_id",art.get("proveedor_id"));
                nuevo.saveIt();
                //if(pr!=null){
                //  art.add(nuevo);
                //pr.add(nuevo);
                //}
                nuevo.saveIt();
                Base.commitTransaction();
                return true;
        } else {
            System.out.println("Existe articulo");
            return false;
        }
    }
    
    
    public boolean baja(Articulo art) {
        boolean ret=false;
        if (findArticulo(art)){
            Base.openTransaction();
            ret =art.delete();
            Base.commitTransaction();
        }
        return ret;
    }
    
    
    public boolean modificar(Articulo art) {
       boolean ret=false;
       Articulo viejo = Articulo.findFirst("codigo = ?", art.get("codigo"));
       if (viejo!=null){
            Base.openTransaction();
            ret=viejo.set("codigo",art.get("codigo"),"descripcion",art.get("descripcion"),"marca",art.get("marca"),"stock",art.get("stock"),"stock_minimo",art.get("stock_minimo"),"precio_compra",art.get("precio_compra"),"precio_venta",art.get("precio_venta"),"equivalencia_fram",art.get("equivalencia_fram"),"proveedor_id",art.get("proveedor_id")).saveIt();
            Base.commitTransaction();
       }
       return ret;
    }
}
