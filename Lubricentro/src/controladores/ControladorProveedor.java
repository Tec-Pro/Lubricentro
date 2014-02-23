/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import abm.ABMCompra;
import abm.ABMProveedor;
import abm.ManejoIp;
import interfaz.AplicacionGui;
import interfaz.ArticuloGui;
import interfaz.CompraGui;
import interfaz.ProveedorGui;
import interfaz.RealizarPagoGui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelos.Articulo;
import modelos.ArticulosCompras;
import modelos.Compra;
import modelos.Pago;
import modelos.Proveedor;
import net.sf.jasperreports.engine.JRException;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

/**
 *
 * @author nico
 */
public class ControladorProveedor implements ActionListener {

    private ProveedorGui proveedorGui;
    private DefaultTableModel tablaArtProvDefault;
    private DefaultTableModel tablaProvDefault;
    private DefaultTableModel tablaPagosDefault;
    private DefaultTableModel tablaComprasDefault;
    private java.util.List<Proveedor> listProveedores;
    private java.util.List<Articulo> listArticulos;
    private java.util.List<Pago> listPagos;
    private java.util.List<Compra> listCompras;
    private JTable tablaProveedor;
    private JTable tablaPagos;
    private JTable tablaCompras;
    private ABMProveedor abmProveedor;
    private Boolean isNuevo;
    private Boolean editandoInfo;
    private Proveedor proveedor;
    private RealizarPagoGui realizarPagoGui;
    private AplicacionGui aplicacionGui;
    private ArticuloGui articuloGui;
    private CompraGui compraGui;
    private JTable tablaArticulos;
    private ControladorJReport reporteProveedor;
    private DecimalFormat formateador = new DecimalFormat("############.##");

    public ControladorProveedor(ProveedorGui proveedorGui, AplicacionGui aplicacionGui, ArticuloGui articuloGui, CompraGui compraGui) throws JRException, ClassNotFoundException, SQLException {
        this.aplicacionGui = aplicacionGui;
        this.articuloGui = articuloGui;
        this.compraGui = compraGui;
        isNuevo = true;
        editandoInfo = false;
        this.proveedorGui = proveedorGui;
        this.proveedorGui.setActionListener(this);
        tablaArtProvDefault = proveedorGui.getArticulosProv();
        tablaProvDefault = proveedorGui.getProveedores();
        tablaPagosDefault = proveedorGui.getPagosDefault();
        tablaComprasDefault = proveedorGui.getComprasDefault();
        tablaProveedor = proveedorGui.getTablaProveedores();
        tablaPagos = proveedorGui.getPagosRealizados();
        tablaArticulos = proveedorGui.getTablaArticulosProv();
        tablaCompras = proveedorGui.getComprasRealizadas();
        listProveedores = new LinkedList();
        listPagos = new LinkedList();
        listCompras = new LinkedList();
        abmProveedor = new ABMProveedor();
        proveedor = new Proveedor();
        reporteProveedor = new ControladorJReport("listadoProveedores.jasper");
        abrirBase();
        listProveedores = Proveedor.findAll();
        cerrarBase();
        actualizarLista();
        proveedorGui.getBusqueda().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busquedaKeyReleased(evt);
            }
        });
        proveedorGui.getTablaProveedores().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        proveedorGui.getPagosRealizados().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaPagosClicked(evt);
            }
        });
        proveedorGui.getTablaArticulosProv().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaArticulosClicked(evt);
            }
        });
        proveedorGui.getComprasRealizadas().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaComprasClicked(evt);
            }
        });

    }

    public void cargarTodos() {
        abrirBase();
        listProveedores = Proveedor.findAll();
        if (!listProveedores.isEmpty() ) {
        realizarBusqueda();
        }
        cerrarBase();

    }

    private void tablaArticulosClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            abrirBase();
            Articulo articulo = Articulo.findFirst("codigo = ?", tablaArticulos.getValueAt(tablaArticulos.getSelectedRow(), 0));
            Proveedor papacito = articulo.parent(Proveedor.class);
            if (papacito == null) {
                articulo.setNombreProv("");
            } else {
                articulo.setNombreProv(papacito.getString("nombre"));
            }
            cerrarBase();

            articuloGui.CargarCampos(articulo);
            articuloGui.setVisible(true);
            articuloGui.toFront();
        }
    }

    private void tablaComprasClicked(java.awt.event.MouseEvent evt) {
        proveedorGui.getBorrarCompra().setEnabled(true);
        if((boolean)tablaCompras.getValueAt(tablaCompras.getSelectedRow(),3)){
            proveedorGui.getPagarFac().setEnabled(false);
        }
        else{
            proveedorGui.getPagarFac().setEnabled(true);
        }
        if (evt.getClickCount() == 2) {
            abrirBase();
            compraGui.limpiarVentana();
            compraGui.paraVerCompra(true);
            Compra compra = Compra.findById(tablaCompras.getValueAt(tablaCompras.getSelectedRow(), 0));
            cerrarBase();
            //PODRÍA HACERSE UNA FUNCION EN COMPRAGUI PARA CARGAR LA COMPRA
            compraGui.getProveedorCompra().setText(proveedor.getString("nombre"));
            abrirBase();
            LazyList<ArticulosCompras> artCom = ArticulosCompras.find("compra_id = ?", compra.getId());
            Iterator<ArticulosCompras> it = artCom.iterator();
            while (it.hasNext()) {
                ArticulosCompras prodCom = it.next();
                Articulo art = Articulo.findById(prodCom.get("articulo_id"));
                if (art != null) {
                    Integer numeroProducto = art.getInteger("id");
                    String codigo = art.getString("codigo");
                    Float precio = prodCom.getFloat("precio_final");
                    Float cantidad = prodCom.getFloat("cantidad");
                    Object cols[] = new Object[5];
                    cols[0] = numeroProducto;
                    cols[1] = cantidad;
                    cols[2] = codigo;
                    cols[3] = formateador.format(precio);
                    cols[4] = formateador.format(precio * cantidad);
                    compraGui.getTablaCompraDefault().addRow(cols);
                }
            }
            compraGui.getTotalCompra().setText(String.valueOf(compra.getFloat("monto")));
            compraGui.getDescuento().setVisible(true);
            compraGui.getLabelTotalConDes().setVisible(true);
            compraGui.getDescuento().setText(new DecimalFormat("#########.##").format(compra.getFloat("monto") - (compra.getFloat("descuento") * compra.getFloat("monto") / 100))+" ("+compra.getString("descuento")+" %)");
            Base.close();
            compraGui.setVisible(true);
            compraGui.toFront();
        }
    }

    private void tablaPagosClicked(java.awt.event.MouseEvent evt) {
        proveedorGui.getBorrarPago().setEnabled(true);

    }

    public void busquedaKeyReleased(java.awt.event.KeyEvent evt) {
        System.out.println("apreté el caracter: " + evt.getKeyChar());
        realizarBusqueda();
    }

    private void realizarBusqueda() {
        abrirBase();
        listProveedores = Proveedor.where("id like ? or nombre like ? or telefono like ? ", "%" + proveedorGui.getBusqueda().getText() + "%", "%" + proveedorGui.getBusqueda().getText() + "%", "%" + proveedorGui.getBusqueda().getText() + "%");
        actualizarLista();
        cerrarBase();

    }

    public void tablaMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            editandoInfo = false;
            proveedorGui.habilitarCampos(false);
            proveedorGui.getBorrar().setEnabled(true);
            proveedorGui.getModificar().setEnabled(true);
            proveedorGui.getGuardar().setEnabled(false);
            proveedorGui.getNuevo().setEnabled(true);
            proveedorGui.getRealizarPago().setEnabled(true);
            proveedorGui.getBorrarPago().setEnabled(false);
            proveedorGui.getBorrarCompra().setEnabled(false);
            proveedorGui.getPagarFac().setEnabled(false);
            System.out.println("hice doble click en un proveedor");
            proveedorGui.limpiarCampos();
            abrirBase();
            proveedor = Proveedor.findFirst("id = ?", tablaProveedor.getValueAt(tablaProveedor.getSelectedRow(), 0));
            proveedorGui.CargarCampos(proveedor);
            listArticulos = proveedor.getAll(Articulo.class);
            tablaArtProvDefault.setRowCount(0);
            Iterator<Articulo> it = listArticulos.iterator();
            cerrarBase();
            while (it.hasNext()) {
                Articulo art = it.next();
                Object row[] = new String[4];
                row[0] = art.getString("codigo");
                row[1] = art.getString("descripcion");
                row[2] = art.getString("marca");
                row[3] = formateador.format("precio_compra");
                tablaArtProvDefault.addRow(row);
            }
            cargarPagos();
            cargarCompras();
        }
    }

    private void actualizarLista() {
        abrirBase();
        tablaProvDefault.setRowCount(0);
        Iterator<Proveedor> it = listProveedores.iterator();
        while (it.hasNext()) {
            Proveedor prov = it.next();
            Object row[] = new String[6];
            row[0] = prov.getString("id");
            row[1] = prov.getString("nombre");
            row[2] = prov.getString("telefono");
            tablaProvDefault.addRow(row);
            cerrarBase();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == proveedorGui.getNuevo()) {
            System.out.println("Boton nuevo pulsado");
            proveedorGui.limpiarCampos();
            proveedorGui.habilitarCampos(true);
            isNuevo = true;
            editandoInfo = true;
            proveedorGui.getBorrar().setEnabled(false);
            proveedorGui.getRealizarPago().setEnabled(false);
            proveedorGui.getModificar().setEnabled(false);
            proveedorGui.getGuardar().setEnabled(true);
            proveedorGui.getCuenta().setText("0.00");
        }
        if (e.getSource() == proveedorGui.getGuardar() && editandoInfo && isNuevo) {
            System.out.println("Boton guardar pulsado");
            if (cargarDatosProv(proveedor)) {
                abrirBase();
                if (abmProveedor.alta(proveedor)) {
                    proveedorGui.habilitarCampos(false);
                    proveedorGui.limpiarCampos();
                    editandoInfo = false;
                    JOptionPane.showMessageDialog(proveedorGui, "¡Proveedor guardado exitosamente!");
                    proveedorGui.getNuevo().setEnabled(true);
                    proveedorGui.getGuardar().setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(proveedorGui, "Ocurrió un error, revise los datos", "Error!", JOptionPane.ERROR_MESSAGE);
                }
                cerrarBase();
                realizarBusqueda();
            }
        }
        if (e.getSource() == proveedorGui.getBorrar()) {

            System.out.println("Boton borrar pulsado");
            proveedorGui.habilitarCampos(false);
            if (proveedor.getId() != null && !editandoInfo) {
                Integer resp = JOptionPane.showConfirmDialog(proveedorGui, "¿Desea borrar el proveedor " + proveedorGui.getNombre().getText(), "Confirmar borrado", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) {
                    abrirBase();
                    Boolean seBorro = abmProveedor.baja(proveedor);
                    cerrarBase();
                    if (seBorro) {
                        JOptionPane.showMessageDialog(proveedorGui, "¡Proveedor borrado exitosamente!");
                        proveedorGui.limpiarCampos();
                        realizarBusqueda();
                        proveedorGui.getBorrar().setEnabled(false);
                        proveedorGui.getModificar().setEnabled(false);
                        proveedorGui.getRealizarPago().setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(proveedorGui, "Ocurrió un error, no se borró el proveedor", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(proveedorGui, "No se seleccionó un proveedor");
            }



        }
        if (e.getSource() == proveedorGui.getModificar()) {
            System.out.println("Boton modificar pulsado");
            proveedorGui.habilitarCampos(true);
            editandoInfo = true;
            isNuevo = false;
            proveedorGui.getBorrar().setEnabled(false);
            proveedorGui.getGuardar().setEnabled(true);
            proveedorGui.getModificar().setEnabled(false);
            proveedorGui.getRealizarPago().setEnabled(false);
        }

        if (e.getSource() == proveedorGui.getGuardar() && editandoInfo && !isNuevo) {
            System.out.println("Boton guardar pulsado");
            if (cargarDatosProv(proveedor)) {
                abrirBase();
                if (abmProveedor.modificar(proveedor)) {
                    proveedorGui.habilitarCampos(false);
                    proveedorGui.limpiarCampos();
                    editandoInfo = false;
                    JOptionPane.showMessageDialog(proveedorGui, "¡Proveedor modificado exitosamente!");
                    proveedorGui.getNuevo().setEnabled(true);
                    proveedorGui.getGuardar().setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(proveedorGui, "Ocurrió un error,revise los datos", "Error!", JOptionPane.ERROR_MESSAGE);
                }
                cerrarBase();
                realizarBusqueda();
            }
        }
        if (e.getSource() == proveedorGui.getRealizarPago()) {
            System.out.println("realizar pago pulsado");
            realizarPagoGui = new RealizarPagoGui(aplicacionGui, true, proveedor);
            realizarPagoGui.setLocationRelativeTo(proveedorGui);
            realizarPagoGui.setVisible(true);
            cargarPagos();
            abrirBase();
            proveedor= abmProveedor.getProveedor(proveedor);
            proveedorGui.CargarCampos(proveedor);
            cerrarBase();
            
        }
        if (e.getSource() == proveedorGui.getBorrarPago()) {
            System.out.println("Borrar pago pulsado");
            Integer resp = JOptionPane.showConfirmDialog(proveedorGui, "¿Desea borrar el pago seleccionado? ", "Confirmar borrado", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                String fecha = tablaPagos.getValueAt(tablaPagos.getSelectedRow(), 0).toString(); //Se le pasa la fecha a la que queremos darle formato
                String dia = fecha.substring(0, 2);
                String mes = fecha.substring(3, 5);
                String anio = fecha.substring(6, 10);
                String fechaSql = anio + mes + dia;
                abrirBase();
                Pago.findFirst("fecha = ? and monto = ? and proveedor_id = ?", fechaSql, tablaPagos.getValueAt(tablaPagos.getSelectedRow(), 1), proveedorGui.getId().getText()).delete();
                cerrarBase();
                cargarPagos();

            }
        }
        if(e.getSource()==proveedorGui.getBorrarCompra()){
                        int row = tablaCompras.getSelectedRow();
            if (row > -1) {
                abrirBase();
               Object id =  tablaCompras.getValueAt(row, 0);
                Compra comp = Compra.findById(id);
                ABMCompra abmC = new ABMCompra();
                if (abmC.baja(comp)) {
                    JOptionPane.showMessageDialog(proveedorGui, "¡Compra eliminada exitosamente!");
                    cargarCompras();
                } else {
                    JOptionPane.showMessageDialog(proveedorGui, "Ocurrió un error, la compra no ha sido eliminada", "Error!", JOptionPane.ERROR_MESSAGE);

                }
                cerrarBase();
            }
        }
        if (e.getSource() == proveedorGui.getExportar()) {
            try {
                reporteProveedor.mostrarReporte();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AplicacionGui.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(AplicacionGui.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JRException ex) {
                Logger.getLogger(AplicacionGui.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (e.getSource() == proveedorGui.getModCuenta()) {
            if (proveedorGui.getModCuenta().isSelected()) {
                proveedorGui.getCuenta().setEnabled(true);
            } else {
                proveedorGui.getCuenta().setEnabled(false);
                proveedorGui.getCuenta().setText(proveedor.getString("cuenta_corriente"));

            }
        }
        if(e.getSource()==proveedorGui.getPagarFac()){
            System.out.println("realizar pago pulsado");
            abrirBase();
            realizarPagoGui = new RealizarPagoGui(aplicacionGui, true, proveedor, (Compra)Compra.findById(tablaCompras.getValueAt(tablaCompras.getSelectedRow(), 0)));
            realizarPagoGui.setLocationRelativeTo(proveedorGui);
            realizarPagoGui.setVisible(true);
            cargarPagos();
            
            proveedor= abmProveedor.getProveedor(proveedor);
            proveedorGui.CargarCampos(proveedor);
            cargarCompras();
            proveedorGui.getPagarFac().setEnabled(false);
            cerrarBase();
        }
    }

    private void abrirBase() {
        if (!Base.hasConnection()) {
            try{             Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://"+ManejoIp.ipServer+"/lubricentro", "tecpro", "tecpro");             }catch(Exception e){                 JOptionPane.showMessageDialog(null, "Ocurrió un error, no se realizó la conexión con el servidor, verifique la conexión \n "+e.getMessage(),null,JOptionPane.ERROR_MESSAGE); }
        }
    }

    private void cerrarBase() {
        if (Base.hasConnection()) {
            Base.close();
        }
    }

    private boolean cargarDatosProv(Proveedor prov) {
        boolean ret = true;
        try {
            String nombre = TratamientoString.eliminarTildes(proveedorGui.getNombre().getText());
            System.out.println(nombre);
            prov.set("nombre", nombre);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(proveedorGui, "Error en el nombre", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            String telefono = TratamientoString.eliminarTildes(proveedorGui.getTelefono().getText());
            prov.set("telefono", telefono);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(proveedorGui, "Error en el telefono", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            String cuenta = TratamientoString.eliminarTildes(proveedorGui.getCuenta().getText());
            Float cuentaFloat = Float.valueOf(cuenta);
            prov.set("cuenta_corriente", formateador.format(cuentaFloat));
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(proveedorGui, "Error en la cuenta ", "Error!", JOptionPane.ERROR_MESSAGE);
        }

        return ret;
    }

    private void cargarPagos() {
        abrirBase();
        listPagos = proveedor.getAll(Pago.class);
        tablaPagosDefault.setRowCount(0);
        Iterator<Pago> it = listPagos.iterator();
        while (it.hasNext()) {
            Pago pago = it.next();
            Object row[] = new String[2];
            Date sqlFecha = pago.getDate("fecha");
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            row[0] = sdf.format(sqlFecha);
            row[1] = formateador.format(pago.getFloat("monto"));
            tablaPagosDefault.addRow(row);
            cerrarBase();
        }
    }

    private void cargarCompras() {
        abrirBase();
        listCompras = proveedor.getAll(Compra.class);
        tablaComprasDefault.setRowCount(0);
        Iterator<Compra> it = listCompras.iterator();
        while (it.hasNext()) {
            Compra compra = it.next();
            Object row[] = new Object[6];
            Date sqlFecha = compra.getDate("fecha");
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            row[0] = compra.getId();
            row[1] = sdf.format(sqlFecha);
            row[2] = formateador.format(compra.getFloat("monto"));
            row[3] = compra.getBoolean("pago");
            row[4]= new DecimalFormat("#########.##").format(compra.getFloat("monto") - (compra.getFloat("descuento") * compra.getFloat("monto") / 100));
            row[5]= compra.get("fecha_pago");
            tablaComprasDefault.addRow(row);
            cerrarBase();
        }
    }
}
