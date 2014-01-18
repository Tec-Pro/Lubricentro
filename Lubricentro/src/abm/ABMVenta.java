/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import java.util.Iterator;
import java.util.LinkedList;
import modelos.Articulo;
import modelos.ArticulosVentas;
import modelos.ClientesArticulos;
import modelos.Venta;
import net.sf.jasperreports.engine.util.Pair;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

/**
 *
 * @author jacinto
 */
public class ABMVenta {

    int ultimoIdVenta;

    public ABMVenta() {
    }

    //FUNCIONA CORRECTAMENTE
    public boolean alta(Venta v) {
        Base.openTransaction();
        boolean resultOp = true;
        if (v == null) {
            resultOp = false;
        } else {
            Integer idCliente = (Integer) v.get("cliente_id");
            if (v.getBoolean("pago")) { // SI se paga se calcula el monto, si se fia no.
                v.set("monto", calcularMonto(v.getProductos()));//seteo el monto de la venta total en el modelo
                Venta venta = Venta.create("monto", v.get("monto"), "cliente_id", idCliente, "fecha", v.get("fecha"), "pago", v.get("pago"));
                resultOp = resultOp && venta.saveIt();//guardo la venta
                int idVenta = venta.getInteger("id");
                ultimoIdVenta = idVenta;
                resultOp = resultOp && cargarProductosVendidos(idVenta, v.getProductos());//guardo los productos vendidos
                resultOp = resultOp && actualizarAdquisicionCliente(idCliente, v.getProductos());//actualizo la tabla de productos adquiridos por clientes
            } else {
                Venta venta = Venta.create("cliente_id", idCliente, "fecha", v.get("fecha"), "pago", v.get("pago"));
                resultOp = resultOp && venta.saveIt();//guardo la venta
                int idVenta = venta.getInteger("id");
                ultimoIdVenta = idVenta;
                resultOp = resultOp && cargarProductosVendidos(idVenta, v.getProductos());//guardo los productos vendidos
                resultOp = resultOp && actualizarAdquisicionCliente(idCliente, v.getProductos());//actualizo la tabla de productos adquiridos por clientes
            }
        }
        Base.commitTransaction();
        return resultOp;
    }

    //FUNCIONA CORRECTAMENTE
    public boolean bajaConDevolucion(Venta v) {
        Base.openTransaction();
        boolean resultOp = true;
        Integer idVenta = v.getInteger("id");//saco el idVenta
        Venta venta = Venta.findById(idVenta);//la busco en BD y la traigo
        if (venta == null) {
            resultOp = false;
        } else {
            Integer idCliente = (Integer) venta.get("cliente_id");//saco el idcliente de esa venta
            LinkedList<Pair> viejosProductos = buscarProductosVendidos(idVenta); //saco los viejos productos de la venta
            resultOp = resultOp && eliminarAdquisicionCliente(idCliente, viejosProductos);//actualizo los productos adquiridos quitando los viejos productos
            ArticulosVentas.delete("venta_id = ?", idVenta);//elimino todos los productosvendidos
            resultOp = resultOp && venta.delete(); //elimino la venta
        }
        Base.commitTransaction();
        return resultOp;
    }
    
     /*Funcion que calcula el precio actual de los productos que se fiaron y
     * paga la cuenta.
     */
    public boolean pagar(Venta v) {
        if (v == null) {
            return false;
        } else {
            v.set("pago", true);
            v.set("monto", calcularMonto(v.getProductos()));//seteo el monto de la venta total en el modelo
            return v.saveIt();
        }
    }

//FUNCIONA CORRECTAMENTE
    /*Recibe lista de pares <Producto,cantidad> retorna precio total de la venta de todos
     los productos de la lista, multiplicados por su cantidad correspondiente*/
    public Double calcularMonto(LinkedList<Pair> productos) {
        Double acumMonto = 0.0;
        if (productos.isEmpty()) {
            return acumMonto;
        } else {
            Iterator itr = productos.iterator();
            Pair par;
            Articulo prod;
            Integer cant;
            Double precioFinal = 0.0;
            while (itr.hasNext()) {
                par = (Pair) itr.next(); //saco el par de la lista
                prod = (Articulo) par.first(); //saco el producto del par
                cant = (Integer) ((Pair) par.second()).first();//saco la cantidad del par
                precioFinal = (Double) ((Pair) par.second()).second(); //saco el percio al que se vendio
                acumMonto += (precioFinal * cant); //multiplico el precio del producto por la cantidad del mismo
            }
            return acumMonto;
        }
    }

    //FUNCIONA CORRECTAMENTE
    //Carga los productos y cantidades en la tabla productos_vendidos
    private boolean cargarProductosVendidos(int idVenta, LinkedList<Pair> productos) {
        boolean resultOp = true;
        Iterator itr = productos.iterator();
        Articulo prod;
        Pair par;
        Integer cant;
        Double precioFinal = 0.0;
        while (itr.hasNext()) {
            par = (Pair) itr.next(); //saco el par de la lista
            prod = (Articulo) par.first(); //saco el producto del par
            cant = (Integer) ((Pair) par.second()).first();//saco la cantidad del par
            precioFinal = (Double) ((Pair) par.second()).second(); //saco el percio al que se vendio
            ArticulosVentas prodVendido = ArticulosVentas.create("venta_id", idVenta, "producto_id", prod.get("codigo"), "cantidad", cant);
            resultOp = resultOp && prodVendido.saveIt();
        }
        return resultOp;
    }

    //FUNCIONA CORRECTAMENTE
    /*Agrego los productos adquiridos por el cliente a la tabla adquirio,
     * retorna un booleano que es true si las adquisiciones se actualizaron
     * con exito
     */
    private boolean actualizarAdquisicionCliente(int idCliente, LinkedList<Pair> productos) {
        boolean resultOp = true;
        Iterator itr = productos.iterator();
        Articulo prod;
        Pair par;
        Integer cant;
        while (itr.hasNext()) {
            par = (Pair) itr.next(); //saco el par de la lista
            prod = (Articulo) par.first(); //saco el producto del par
            cant = (Integer) ((Pair) par.second()).first();//saco la cantidad del par
            ClientesArticulos prodAdquirido;
            prodAdquirido = ClientesArticulos.findFirst("cliente_id = ? AND producto_id = ?", idCliente, prod.get("codigo"));
            if (prodAdquirido == null) { // sino lo agrego a la tabla
                prodAdquirido = ClientesArticulos.create("cliente_id", idCliente, "producto_id", prod.get("codigo"), "cantidad", cant);
                resultOp = resultOp && prodAdquirido.saveIt();
            } else { //si existe modifico la cantidad
                cant = prodAdquirido.getInteger("cantidad") + cant;//asigno a cant el valor nuevo de cantidad
                ClientesArticulos.update("cantidad = ?", "cliente_id = ? AND producto_id = ?", cant, idCliente, prod.get("codigo"));
            }
        }
        return resultOp;
    }

   
    //FUNCIONA CORRECTAMENTE
    /*Busca los productos adquiridos por el cliente y actualiza su cantidad tras la eliminacion
     * o modificacion de una venta, si la cantidad del producto adquirido es 0 lo borra de la tabla
     * sino decrementa en la cantidad que este fue adquirido
     */
    public boolean eliminarAdquisicionCliente(int idCliente, LinkedList<Pair> productos) {
        boolean resultOp = true;
        Iterator itr = productos.iterator();
        Articulo prod;
        Pair par;
        Integer cant;
        while (itr.hasNext()) {
            par = (Pair) itr.next(); //saco el par de la lista
            prod = (Articulo) par.first(); //saco el producto del par
            cant = (Integer) ((Pair) par.second()).first();//saco la cantidad del par
            ClientesArticulos prodAdquirido;
            prodAdquirido = ClientesArticulos.findFirst("cliente_id = ? AND producto_id = ?", idCliente, prod.get("codigo"));
            if (prodAdquirido == null) { //si no existe lo informo
                System.out.println("ERROR - PRODUCTO NO ENCONTRADO EN TABLA DE ADQUISICIONES DE CLIENTE");
            } else {
                if (prodAdquirido.getInteger("cantidad") - cant > 0) {
                    cant = prodAdquirido.getInteger("cantidad") - cant;//asigno a cant el valor nuevo de cantidad
                    ClientesArticulos.update("cantidad = ?", "cliente_id = ? AND producto_id = ?", cant, idCliente, prod.get("codigo"));
                } else {
                    if (prodAdquirido.getInteger("cantidad") - cant == 0) {
                        ClientesArticulos.delete("cliente_id = ? AND producto_id = ?", idCliente, prod.get("codigo"));
                    } else {
                        resultOp = false;
                        System.out.println("ERROR LA CANTIDAD DE PRODUCTOS ADQUIRIDOS ES MENOR A LA VENDIDA");
                    }
                }
            }
        }
        return resultOp;
    }

    //FUNCIONA CORRECTAMENTE
    /*Retorna una lista de pares producto-cantidad de una compra(la busca en
     * productos_comprados y a su vez
     * elimina estos productos de la base de la misma tabla
     */
    private LinkedList<Pair> buscarProductosVendidos(int idVenta) {
        Integer cant;
        ArticulosVentas prodVendido;
        Articulo prod;
        Double precioFinal;
        LinkedList<Pair> listaDePares = new LinkedList<Pair>();
        LazyList<ArticulosVentas> productos = ArticulosVentas.find("venta_id = ?", idVenta);
        Iterator itr = productos.iterator();
        while (itr.hasNext()) {
            prodVendido = (ArticulosVentas) itr.next(); //saco el modelo de la lista
            prod = Articulo.findFirst("numero_producto = ?", prodVendido.getInteger("producto_id"));//saco el producto del modelo
            cant = prodVendido.getInteger("cantidad");//saco la cantidad del modelo
            precioFinal = prodVendido.getDouble("precio_final");
            Pair parInterno = new Pair(cant, precioFinal);
            Pair par = new Pair(prod, parInterno); //creo el par producto-cantidad
            listaDePares.add(par);//agrego el par a la lista de pares
            ArticulosVentas.delete("venta_id = ? AND producto_id = ?", prodVendido.getInteger("venta_id"), prodVendido.getInteger("producto_id"));//elimino el modelo de la base de datos
        }
        return listaDePares;
    }
    
    
    
}


