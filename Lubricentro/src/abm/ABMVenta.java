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
    /*Elimino una venta y los productos ligados a ella, sin hacer devolucion de stock,
     * ni actualizacion de tablas de adquisicion ni tabla de productos_vendidos
     */
    public boolean baja(Venta v) {
        Base.openTransaction();
        boolean resultOp = true;
        Integer idVenta = v.getInteger("id");//saco el idVenta
        Venta venta = Venta.findById(idVenta);//la busco en BD y la traigo
        
        if (venta == null) {
             resultOp = false;
         } else {
             ArticulosVentas.delete("venta_id = ?", idVenta);//elimino todos los productosvendidos
             resultOp = resultOp && venta.delete();//elimino la venta
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
    public boolean pagar(Venta v, BigDecimal monto) {
        if (v == null) {
            return false;
        } else {
            v.set("pago", true);
            v.set("monto", monto);//seteo el monto de la venta total en el modelo
            return v.saveIt();
        }
    }

//FUNCIONA CORRECTAMENTE
    /*Recibe lista de pares <Producto,cantidad> retorna precio total de la venta de todos
     los productos de la lista, multiplicados por su cantidad correspondiente*/
    public BigDecimal calcularMonto(LinkedList<Pair> productos) {
        BigDecimal acumMonto = new BigDecimal(0);
        if (productos.isEmpty()) {
            return acumMonto;
        } else {
            Iterator itr = productos.iterator();
            Pair par;
            Articulo prod;
             BigDecimal cant;
            BigDecimal precioFinal;
            while (itr.hasNext()) {
                par = (Pair) itr.next(); //saco el par de la lista
                prod = (Articulo) par.first(); //saco el producto del par
                cant = ((BigDecimal) (((Pair) par.second()).first())).setScale(2, RoundingMode.CEILING);//saco la cantidad del par
                precioFinal = ((BigDecimal)prod.get("precio_venta")).setScale(2, RoundingMode.CEILING);
                acumMonto.add(precioFinal.multiply(cant)).setScale(2, RoundingMode.CEILING);; //multiplico el precio del producto por la cantidad del mismo
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
        BigDecimal cant;
        while (itr.hasNext()) {
            par = (Pair) itr.next(); //saco el par de la lista
            prod = (Articulo) par.first(); //saco el producto del par
            cant = ((BigDecimal) par.second()).setScale(2, RoundingMode.CEILING);//saco la cantidad del par
            ArticulosVentas prodVendido = ArticulosVentas.create("venta_id", idVenta, "articulo_id", prod.get("id"), "cantidad", cant);
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
        BigDecimal cant;
        while (itr.hasNext()) {
            par = (Pair) itr.next(); //saco el par de la lista
            prod = (Articulo) par.first(); //saco el producto del par
            cant = ((BigDecimal) par.second()).setScale(2, RoundingMode.CEILING);//saco la cantidad del par
            ClientesArticulos prodAdquirido;
            prodAdquirido = ClientesArticulos.findFirst("cliente_id = ? AND articulo_id = ?", idCliente, prod.get("id"));
            if (prodAdquirido == null) { // sino lo agrego a la tabla
                prodAdquirido = ClientesArticulos.create("cliente_id", idCliente, "articulo_id", prod.get("id"), "cantidad", cant);
                resultOp = resultOp && prodAdquirido.saveIt();
            } else { //si existe modifico la cantidad
                cant = ((prodAdquirido.getBigDecimal("cantidad")).add(cant)).setScale(2, RoundingMode.CEILING);//asigno a cant el valor nuevo de cantidad
                ClientesArticulos.update("cantidad = ?", "cliente_id = ? AND articulo_id = ?", cant, idCliente, prod.get("id"));
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
        BigDecimal cant;
        while (itr.hasNext()) {
            par = (Pair) itr.next(); //saco el par de la lista
            prod = (Articulo) par.first(); //saco el producto del par
            cant = ((BigDecimal) par.second()).setScale(2, RoundingMode.CEILING);//saco la cantidad del par
            ClientesArticulos prodAdquirido;
            prodAdquirido = ClientesArticulos.findFirst("cliente_id = ? AND articulo_id = ?", idCliente, prod.get("id"));
            if (prodAdquirido == null) { //si no existe lo informo
                System.out.println("ERROR - PRODUCTO NO ENCONTRADO EN TABLA DE ADQUISICIONES DE CLIENTE");
            } else {
                if ((prodAdquirido.getBigDecimal("cantidad")).subtract(cant).signum() > 0) {
                    cant = ((prodAdquirido.getBigDecimal("cantidad")).subtract(cant)).setScale(2, RoundingMode.CEILING);//asigno a cant el valor nuevo de cantidad
                    ClientesArticulos.update("cantidad = ?", "cliente_id = ? AND articulo_id = ?", cant, idCliente, prod.get("id"));
                } else {
                    if ((prodAdquirido.getBigDecimal("cantidad")).subtract(cant).signum() == 0) {
                        ClientesArticulos.delete("cliente_id = ? AND articulo_id = ?", idCliente, prod.get("id"));
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
        BigDecimal cant;
        ArticulosVentas prodVendido;
        Articulo prod;
        BigDecimal precioFinal;
        LinkedList<Pair> listaDePares = new LinkedList<Pair>();
        LazyList<ArticulosVentas> productos = ArticulosVentas.find("venta_id = ?", idVenta);
        Iterator itr = productos.iterator();
        while (itr.hasNext()) {
            prodVendido = (ArticulosVentas) itr.next(); //saco el modelo de la lista
            prod = Articulo.findById(prodVendido.get("articulo_id"));//saco el producto del modelo
            cant = prodVendido.getBigDecimal("cantidad").setScale(2, RoundingMode.CEILING);//saco la cantidad del modelo
            precioFinal = (prodVendido.getBigDecimal("precio_final")).setScale(2, RoundingMode.CEILING);
            Pair parInterno = new Pair(cant, precioFinal);
            Pair par = new Pair(prod, parInterno); //creo el par producto-cantidad
            listaDePares.add(par);//agrego el par a la lista de pares
            ArticulosVentas.delete("venta_id = ? AND articulo_id= ?", prodVendido.getInteger("venta_id"), prodVendido.getInteger("articulo_id"));//elimino el modelo de la base de datos
        }
        return listaDePares;
    }
    
      private void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/lubricentro", "root", "root");
        }
    }

    private void cerrarBase() {
        if (Base.hasConnection()) {
            Base.close();
        }
    }

    public int getUltimoIdVenta() {
        return ultimoIdVenta;
    }
    
    
}


