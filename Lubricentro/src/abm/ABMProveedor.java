/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import modelos.Pago;
import modelos.Proveedor;
import org.javalite.activejdbc.Base;

/**
 *
 * @author nico
 */
public class ABMProveedor {

    public boolean findProveedor(Proveedor p) {
        return (Proveedor.first("nombre = ?", p.get("nombre")) != null);
    }

    public Proveedor getProveedor(Proveedor c) {
        return Proveedor.first("nombre = ?", c.get("nombre"));
    }

    public boolean alta(Proveedor p) {
        boolean ret = false;
        if (!findProveedor(p)) {
            Base.openTransaction();
            Proveedor nuevo = Proveedor.create("nombre", p.get("nombre"), "telefono", p.get("telefono"));
            ret = nuevo.saveIt();
            Base.commitTransaction();
        }
        return ret;
    }

    public boolean baja(Proveedor p) {
        boolean ret = false;
        Proveedor viejo = getProveedor(p);
        if (viejo != null) {
            Base.openTransaction();
            ret = viejo.delete();
            Base.commitTransaction();
        }
        return ret;
    }

    public boolean modificar(Proveedor p) {
        boolean ret = false;
        Proveedor viejo = Proveedor.findById(p.getId());
        if (viejo != null) {          
            Base.openTransaction();
            ret = viejo.set("nombre", p.get("nombre"), "telefono", p.get("telefono")).saveIt();
            Base.commitTransaction();
        }
        return ret;
    }
    
    public boolean registrarPago(Proveedor prov, Pago pago){
        boolean ret= false;
        Proveedor viejo = Proveedor.findFirst("nombre = ?", prov.get("nombre"));
        if(viejo!=null){
            Base.openTransaction();
            viejo.add(pago);
            Base.commitTransaction();
            ret=true;
        }
        return ret;
    }
}
