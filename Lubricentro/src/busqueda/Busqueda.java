/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package busqueda;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import modelos.Articulo;
import modelos.ArticulosCompras;
import modelos.ArticulosVentas;
import modelos.Cliente;
import modelos.ClientesArticulos;
import modelos.Compra;
import modelos.Proveedor;
import modelos.Venta;

/**
 *
 * @author jacinto
 */
public class Busqueda {
    
    /*
     * No hace falta distinguir entre mayúsculas y minúsculas.
     */

    
    /**
     *
     * @param id
     * @return Cliente asociado a esa id.
     */
    public Cliente buscarCliente(Object id){
        Cliente result = Cliente.findById(id);
        return result;
    }   
    
    public List<Cliente> buscarCliente(String nombre){
        List<Cliente> result;
        result = Cliente.where("nombre like ?","%"+nombre+"%");
        return result;
    }   
    
    /**
     * @param nombre, @param apellido e @param id del cliente.
     * Filtra los que tienen nombre a los pasados y
     * @return lista filtrada de clientes.
     */
     public List<Cliente> filtroCliente(String nombre,String codigo){
        List<Cliente> result;
        result = Cliente.where("nombre like ? and id like ?","%"+nombre+"%", codigo+"%");
        return result;
    }
    
     /**
     * @param idcliente, @param fecha desde (Pasada como string), @param fecha hasta (Pasada como String).
     * Filtra las ventas de los clientes que empiezan con idcliente entre las fechas pasadas.
     * @return lista filtrada de ventas.
     */
    public List<Venta> filtroVenta(String idcliente, String desde, String hasta){
        List<Venta> result;
        result = Venta.where("cliente_id like ? and fecha between ? and ?", idcliente+"%",desde, hasta);
        return result;
    }
    
    /**
     * @param idproveedor, @param fecha desde (Pasada como string), @param fecha hasta (Pasada como String).
     * Filtra las compras a los proveedores que empiezan con idproveedores entre las fechas pasadas.
     * @return lista filtrada de compras.
     */
    
   public List<Compra> filtroCompra(String idproveedor, String desde, String hasta){
        List<Compra> result;
        result = Compra.where("proveedor_id like ? and fecha between ? and ?", idproveedor+"%",desde,hasta);
        return result;
    }
   
   /**
     * @param cuil, @param nombre, @param apellido.
     * Filtra los proveedores que tienen lo pasado en nombre.
     * @return lista filtrada de proveedores.
     */
    public List<Proveedor> filtroProveedor(String nombre, String codigo) {
        List<Proveedor> result;
        result = Proveedor.where("nombre like ? and id like ?","%"+nombre+"%","%"+codigo+"%");
        return result;
    }
    
     /**
     * @param idcompra, @param idproducto.
     * Filtra los productos comprados en idcompra o las compras que contienen idproducto o ambas.
     * @returns lista filtrada de productos comprados.
     */
    public List<ArticulosCompras> filtroComprados(String idcompra, String idproducto){
        List<ArticulosCompras> result;
        result = ArticulosCompras.where("compra_id like ? and producto_id like ?", idcompra+"%",idproducto+"%");
        return result;
    }
     /**
     * @param idventa, @param idproducto.
     * Filtra los productos vendidos en idventa o las ventas que contienen idproducto o ambas.
     * @returns lista filtrada de productos vendidos.
     */
    public List<ArticulosVentas> filtroVendidos(String idventa, String idproducto){
        List<ArticulosVentas> result;
        result = ArticulosVentas.where("venta_id like ? and producto_id like ?", idventa+"%",idproducto+"%");
        return result;
    }
    /**
     * @param idcliente, @param idproducto.
     * Filtra los productos adquiridos por idcliente o los clientes que adquirieron idproducto.
     * @returns lista filtrada de productos adquiridos.
     */
     public List<ClientesArticulos> filtroAdquiridos(String idcliente, String idproducto){
        List<ClientesArticulos> result;
        result = ClientesArticulos.where("cliente_id like ? and producto_id like ?", idcliente+"%",idproducto+"%");
        return result;
    }

     /**
     * @param idcliente.
     * Devuelve todos los productos asociados a un cliente.
     * @returns lista filtrada de productos.
     */
     public List<Articulo> productosAdquiridos(String idcliente){
        List<ClientesArticulos> adquiridos;
        List<Articulo> result = new LinkedList<Articulo>();
        adquiridos = ClientesArticulos.where("cliente_id like ?", "%"+idcliente+"%");
        Iterator<ClientesArticulos> it = adquiridos.iterator();
        while(it.hasNext()){
            ClientesArticulos a = it.next();
            Articulo p = Articulo.first("codigo = ?", a.get("producto_id"));
            result.add(p);
        }
        return result;
    }
     
    public List<Articulo> productosVendidos(String idventa){
        List<ArticulosVentas> vendidos;
        List<Articulo> result = new LinkedList<Articulo>();
        vendidos = ArticulosVentas.where("venta_id like ?", "%"+idventa+"%");
        Iterator<ArticulosVentas> it = vendidos.iterator();
        while(it.hasNext()){
            ArticulosVentas pv = it.next();
            Articulo p = Articulo.first("codigo = ?", pv.get("producto_id"));
            result.add(p);
        }
        return result;
    }
     
    public List<Articulo> provee(String cuil){
        Proveedor p = Proveedor.findFirst("nombre= ?", cuil);
        return Articulo.where("proveedor_id = ?",p.getId() );
    }
     /**
     * @param 
     * Devuelve todos los proveedores de la base de datos.
     * @returns lista de todos los proveedores.
     */
    public List<Proveedor> proveedores(){
        return Proveedor.findAll();
    }
    
    /**
     * @param código_producto, @param nombre_producto, @param marca.
     * Filtra aquellos que empiecen con el código pasado y que contengan el equivalente en fram y marca.
     * @return lista filtrada de productos
     */
    public List<Articulo> filtroProducto(String codigo, String fram, String marca){
        List<Articulo> result;
        result = Articulo.where("codigo like ? and equivalencia_fram like ? and marca like ?", codigo+"%","%"+fram+"%","%"+marca+"%");
        Iterator<Articulo> itr = result.iterator();
        List result2;
        while (itr.hasNext()){
            Articulo art = itr.next();
            result2 = Articulo.where("equivalencia_fram like ?",art.get("equivalencia_fram"));
            result.addAll(result2);
        }
        return result;
    }
    
}

