/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.LinkedList;
import modelos.Articulo;
import modelos.ArticulosCompras;
import modelos.Compra;
import net.sf.jasperreports.engine.util.Pair;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

/**
 *
 * @author jacinto
 */
public class ABMCompra {

    public ABMCompra() {
    }

    public boolean alta(Compra c) {
        Base.openTransaction();
        boolean resultOp = true;
        if (c == null) {
            resultOp = false;
        } else {
            Integer idProveedor = (Integer) c.get("proveedor_id");
            Compra compra = Compra.create("monto", c.get("monto"), "proveedor_id", idProveedor, "fecha", c.get("fecha"), "pago", c.get("pago"));
            resultOp = resultOp && compra.saveIt();//guardo la venta
            int idCompra = compra.getInteger("id");
            resultOp = resultOp && cargarProductosComprass(idCompra, c.getProductos());//guardo los productos vendidos
        }

        Base.commitTransaction();
        return resultOp;
    }

    //FUNCIONA CORRECTAMENTE
    //Carga los productos y cantidades en la tabla productos_comprados
    private boolean cargarProductosComprass(int idCompra, LinkedList<Pair> productos) {
        boolean resultOp = true;
        Iterator itr = productos.iterator();
        Articulo prod;
        Pair par;
        BigDecimal cant;
        while (itr.hasNext()) {
            par = (Pair) itr.next(); //saco el par de la lista
            prod = (Articulo) par.first(); //saco el producto del par
            cant = ((BigDecimal) par.second()).setScale(2, RoundingMode.CEILING);;//saco la cantidad del par
            ArticulosCompras prodComprado = ArticulosCompras.create("compra_id", idCompra, "articulo_id", prod.get("id"), "cantidad", cant, "precio_final", prod.get("precio_compra"));
            resultOp = resultOp && prodComprado.saveIt();

        }
        return resultOp;
    }
//FUNCIONA CORRECTAMENTE
//Actualiza el stock de los productos comprados (incrementandolo)

//    private boolean actualizarStock(LinkedList<Pair> productos) {
//        boolean resultOp = true;
//        Iterator itr = productos.iterator();
//        Articulo prodViejo;
//        Pair par;
//        BigDecimal cant;
//        ABMArticulo abmProd = new ABMArticulo();
//        while (itr.hasNext()) {
//            par = (Pair) itr.next(); //saco el par de la lista
//            prodViejo = (Articulo) par.first(); //saco el producto del par
//            cant = ((BigDecimal) par.second()).setScale(2, RoundingMode.CEILING);;//saco la cantidad del par
//            cant = prodViejo.getIntege("stock") + cant;//asigno a cant el valor nuevo del stock
//            resultOp = resultOp && prodViejo.setInteger("stock", cant).saveIt();//actualizo el stock del producto
//            Proveedor.findById(prodViejo.get("proveedor_id")).add(prodViejo);//creo la relacion
//        }
//        return resultOp;
//    }
    //fUNCIONA CORRECTAMENTE
    /*Elimino una compra y los productos ligados a ella, sin hacer devolucion de stock,
     * ni actualizacion de tablas de productos_comprados
     */
    public boolean baja(Compra c) {
        boolean resultOp = true;
        Base.openTransaction();
        Integer idCompra = c.getInteger("id");//saco el idCompra
        Compra compra = Compra.findById(idCompra);//la busco en BD y la traigo
        if (compra == null) {
            resultOp = false;
        } else {
            ArticulosCompras.delete("compra_id = ?", idCompra);//elimino todos los productoscomprados
            resultOp = resultOp && compra.delete();//elimino la compra y todos los registros que la referencian
        }
        Base.commitTransaction();
        return resultOp;
    }

    //FUNCIONA CORRECTAMENTE
    /*Setear el id de la compra a modificar, la lista de productos nuevos, el idproveedor nuevo 
     * y la fecha nueva, busca la venta vieja y modifica todos sus atributos calculando el nuevo 
     * monto en base a la nueva lista de productos
     */
    public boolean modificar(Compra c) {
        Base.openTransaction();
        boolean resultOp = true;
        if (c == null) {
            resultOp = false;
        } else {
            Integer idCompra = c.getInteger("id");
            Integer idProveedorNuevo = c.getInteger("proveedor_id");
            // c.set("monto", calcularMonto(c.getProductos()));//calculo nuevo monto
            Compra compra = Compra.findById(idCompra);
            compra.set("monto", c.get("monto"));
            compra.set("fecha", c.get("fecha"));
            compra.set("proveedor_id", idProveedorNuevo);
            compra.set("pago", c.get("pago"));
            compra.saveIt();
            resultOp = resultOp && cargarProductosComprass(idCompra, c.getProductos());//guardo los productos nuevos
        }
        Base.commitTransaction();
        return resultOp;
    }

    //FUNCIONA CORRECTAMENTE
    /*Retorna una lista de pares producto-cantidad de una compra(la busca en
     * productos_comprados
     */
    private LinkedList<Pair> buscarProductosComprass(int idCompra) {
        BigDecimal cant;
        ArticulosCompras prodComprado;
        Articulo prod;
        LinkedList<Pair> listaDePares = new LinkedList<Pair>();
        LazyList<ArticulosCompras> productos = ArticulosCompras.find("compra_id = ?", idCompra);
        Iterator itr = productos.iterator();
        while (itr.hasNext()) {
            prodComprado = (ArticulosCompras) itr.next(); //saco el modelo de la lista
            prod = Articulo.findFirst("numero_producto = ?", prodComprado.getInteger("producto_id"));//saco el producto del modelo
            cant = ((BigDecimal) prodComprado.get("cantidad")).setScale(2, RoundingMode.CEILING);;//saco la cantidad del modelo
            Pair par = new Pair(prod, cant); //creo el par producto-cantidad
            listaDePares.add(par);//agrego el par a la lista de pares
        }
        return listaDePares;
    }
    //FUNCIONA CORRECTAMENTE
    /*Recibe lista de pares <Producto,cantidad> retorna precio total de la venta de todos
     los productos de la lista, multiplicados por su cantidad correspondiente*/
    /*private BigDecimal calcularMonto(LinkedList<Pair> productos) {
     BigDecimal acumMonto = new BigDecimal(0);
     if (productos.isEmpty()) {
     return acumMonto;
     } else {
     Iterator itr = productos.iterator();
     Pair par;
     Articulo prod;
     BigDecimal cant;
     while (itr.hasNext()) {
     par = (Pair) itr.next(); //saco el par de la lista
     prod = (Articulo) par.first(); //saco el producto del par
     cant = ((BigDecimal) par.second()).setScale(2, RoundingMode.CEILING);;//saco la cantidad del par
     acumMonto.add(cant.multiply((BigDecimal)prod.get("precio_compra"))); //multiplico el precio del producto por la cantidad del mismo
     }
     acumMonto = acumMonto.setScale(2, RoundingMode.CEILING);
     return acumMonto;
     }
     }*/
}
