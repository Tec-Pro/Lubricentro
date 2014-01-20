/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import abm.ABMArticulo;
import interfaz.AplicacionGui;
import interfaz.ArticuloGui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelos.Articulo;
import modelos.Proveedor;
import net.sf.jasperreports.engine.JRException;
import org.javalite.activejdbc.Base;

/**
 *
 * @author nico
 */
public class ControladorArticulo implements ActionListener, FocusListener {

    private ArticuloGui articuloGui;
    private DefaultTableModel tablaArtDefault;
    private java.util.List<Articulo> listArticulos;
    private java.util.List<Proveedor> listProveedores;
    private JTable tablaArticulos;
    private ABMArticulo abmArticulo;
    private Boolean isNuevo;
    private Boolean editandoInfo;
    private Articulo articulo;
    private ControladorJReport reporteArticulos;

    public ControladorArticulo(ArticuloGui articuloGui) throws JRException, ClassNotFoundException, SQLException {
        isNuevo = true;
        editandoInfo = false;
        articulo = new Articulo();
        this.articuloGui = articuloGui;
        this.articuloGui.setActionListener(this);
        this.articuloGui.setFocusListener(this);
        tablaArtDefault = articuloGui.getTablaArticulosDefault();
        tablaArticulos = articuloGui.getArticulos();
        listArticulos = new LinkedList();
        listProveedores= new LinkedList();
        abmArticulo = new ABMArticulo();
        abrirBase();
        listArticulos = Articulo.findAll();
        cerrarBase();
        actualizarLista();
        reporteArticulos = new ControladorJReport("listadoArticulos.jasper");
        articuloGui.getBusqueda().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busquedaKeyReleased(evt);
            }
        });
        articuloGui.getArticulos().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });

    }

    public void busquedaKeyReleased(java.awt.event.KeyEvent evt) {
        System.out.println("apreté el caracter: " + evt.getKeyChar());
        realizarBusqueda();
    }

    private void realizarBusqueda() {
        abrirBase();
        listArticulos = Articulo.where("codigo like ? or descripcion like ? or marca like ? or equivalencia_fram like ?", "%" + articuloGui.getBusqueda().getText() + "%", "%" + articuloGui.getBusqueda().getText() + "%", "%" + articuloGui.getBusqueda().getText() + "%", "%" + articuloGui.getBusqueda().getText() + "%");
        actualizarLista();
        cerrarBase();

    }

    public void tablaMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            articuloGui.habilitarCampos(false);
            articuloGui.getPrecioVenta().setEnabled(false);
            articuloGui.getBorrar().setEnabled(true);
            articuloGui.getModificar().setEnabled(true);
            articuloGui.getGuardar().setEnabled(false);
            articuloGui.getNuevo().setEnabled(true);
            System.out.println("hice doble click en un articulo");
            articuloGui.limpiarCampos();
            abrirBase();
            articulo = Articulo.findFirst("codigo = ?", tablaArticulos.getValueAt(tablaArticulos.getSelectedRow(), 0));
            Proveedor papacito= articulo.parent(Proveedor.class);
            if(papacito==null)
              articulo.setNombreProv("");  
            else
            articulo.setNombreProv(papacito.getString("nombre"));
            cerrarBase();
            
            articuloGui.CargarCampos(articulo);
            
        }
    }

    private void actualizarLista() {
        abrirBase();
        tablaArtDefault.setRowCount(0);
        Iterator<Articulo> it = listArticulos.iterator();
        while (it.hasNext()) {
            Articulo art = it.next();
            Object row[] = new String[6];
            row[0] = art.getString("codigo");
            row[1] = art.getString("descripcion");
            row[2] = art.getString("marca");
            row[3] = art.getBigDecimal("precio_compra").setScale(2, RoundingMode.CEILING).toString();
            row[4] = art.getBigDecimal("precio_venta").setScale(2, RoundingMode.CEILING).toString();
            row[5] = art.getString("equivalencia_fram");
            tablaArtDefault.addRow(row);
            cerrarBase();
        }
        articuloGui.getCantidadArticulos().setText(String.valueOf(tablaArticulos.getRowCount()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == articuloGui.getNuevo()) {
            System.out.println("Boton nuevo pulsado");
            articuloGui.limpiarCampos();
            articuloGui.habilitarCampos(true);
            isNuevo = true;
            editandoInfo = true;
            articuloGui.getBorrar().setEnabled(false);
            articuloGui.getModificar().setEnabled(false);
            articuloGui.getGuardar().setEnabled(true);
            cargarProveedores();
            articuloGui.getProveedores().setSelectedItem("");
        }
        if (e.getSource() == articuloGui.getGuardar() && editandoInfo && isNuevo) {
            System.out.println("Boton guardar pulsado");
            if (cargarDatosProd(articulo)) {
                abrirBase();
                if (abmArticulo.alta(articulo)) {
                    if(!articulo.getNombreProv().equals("")){
                    Proveedor prov=Proveedor.findFirst("nombre = ?", articulo.getNombreProv());
                    Base.openTransaction();
                    prov.add(articulo);
                     Base.commitTransaction();
                    }
                    articuloGui.habilitarCampos(false);
                    articuloGui.getPrecioVenta().setEnabled(false);
                    articuloGui.limpiarCampos();
                    editandoInfo = false;
                    JOptionPane.showMessageDialog(articuloGui, "¡Artículo guardado exitosamente!");
                    articuloGui.getNuevo().setEnabled(true);
                    articuloGui.getGuardar().setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(articuloGui, "codigo repetido, no se guardó el artículo", "Error!", JOptionPane.ERROR_MESSAGE);
                }
                cerrarBase();
                realizarBusqueda();
            }
        }
        if (e.getSource() == articuloGui.getBorrar()) {

            System.out.println("Boton borrar pulsado");
            articuloGui.habilitarCampos(false);
            articuloGui.getPrecioVenta().setEnabled(false);
            System.out.println(articulo.getString("codigo") != null + "-" + !editandoInfo);
            if (articulo.getString("codigo") != null && !editandoInfo) {
                Integer resp = JOptionPane.showConfirmDialog(articuloGui, "¿Desea borrar el artículo " + articuloGui.getCodigo().getText(), "Confirmar borrado", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) {
                    abrirBase();
                    Boolean seBorro = abmArticulo.baja(articulo);
                    cerrarBase();
                    if (seBorro) {
                        JOptionPane.showMessageDialog(articuloGui, "¡Artículo borrado exitosamente!");
                        articuloGui.limpiarCampos();
                        realizarBusqueda();
                        articuloGui.getBorrar().setEnabled(false);
                        articuloGui.getModificar().setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(articuloGui, "Ocurrió un error, no se borró el artículo", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(articuloGui, "No se seleccionó un artículo");
            }



        }
        if (e.getSource() == articuloGui.getModificar()) {
            System.out.println("Boton modificar pulsado");
            articuloGui.habilitarCampos(true);
            editandoInfo = true;
            isNuevo = false;
            articuloGui.getCodigo().setEnabled(false);
            articuloGui.getBorrar().setEnabled(false);
            articuloGui.getGuardar().setEnabled(true);
            articuloGui.getModificar().setEnabled(false);
            cargarProveedores();
            articuloGui.getProveedores().setSelectedItem(articulo.getNombreProv());
        }

        if (e.getSource() == articuloGui.getGuardar() && editandoInfo && !isNuevo) {
            System.out.println("Boton guardar pulsado");
            if (cargarDatosProd(articulo)) {
                abrirBase();
                if (abmArticulo.modificar(articulo)) {
                    if(!articulo.getNombreProv().equals("")){
                    Proveedor prov=Proveedor.findFirst("nombre = ?", articulo.getNombreProv());
                    Base.openTransaction();
                    prov.add(articulo);
                     Base.commitTransaction();
                    }
                    else{
                        Base.openTransaction();
                        articulo.set("proveedor_id", null);
                        articulo.saveIt();
                        Base.commitTransaction();
                    }
                    articuloGui.habilitarCampos(false);
                    articuloGui.getPrecioVenta().setEnabled(false);
                    articuloGui.limpiarCampos();
                    editandoInfo = false;
                    JOptionPane.showMessageDialog(articuloGui, "¡Artículo modificado exitosamente!");
                    articuloGui.getNuevo().setEnabled(true);
                    articuloGui.getGuardar().setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(articuloGui, "Ocurrió un error,revise los datos", "Error!", JOptionPane.ERROR_MESSAGE);
                }
                cerrarBase();
                realizarBusqueda();
            }
        }
        if (e.getSource() == articuloGui.getExportar()) {
            try {
                reporteArticulos.mostrarReporte();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AplicacionGui.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(AplicacionGui.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JRException ex) {
                Logger.getLogger(AplicacionGui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource() == articuloGui.getPrecioManual()) {
            System.out.println("precioManual");
            if (articuloGui.getPrecioManual().isSelected()) {
                articuloGui.getPrecioVenta().setEnabled(true);
            } else {
                articuloGui.getPrecioVenta().setEnabled(false);
                actualizarPrecioVenta();
            }
        }




    }

    private void actualizarPrecioVenta() {

        try {
            Double precioCompra = Double.valueOf(articuloGui.getPrecioCompra().getText());
            BigDecimal precioVenta = BigDecimal.valueOf(precioCompra * 5.0).setScale(2, RoundingMode.CEILING);
            articuloGui.getPrecioVenta().setText(precioVenta.toString());
        } catch (NumberFormatException | ClassCastException e) {
        }
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

    private boolean cargarDatosProd(Articulo art) {
        boolean ret = true;
        try {
            String codigo = TratamientoString.eliminarTildes(articuloGui.getCodigo().getText());
            art.set("codigo", codigo);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en el codigo", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            String marca = TratamientoString.eliminarTildes(articuloGui.getMarca().getText());
            art.set("marca", marca);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en la marca", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            String desc = TratamientoString.eliminarTildes(articuloGui.getDescripcion().getText());
            art.set("descripcion", desc);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en la descripcion", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            Double precioCompra = Double.valueOf(TratamientoString.eliminarTildes(articuloGui.getPrecioCompra().getText()));
            art.set("precio_compra", BigDecimal.valueOf(precioCompra).setScale(2, RoundingMode.CEILING));
        } catch (NumberFormatException | ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en precio de compra", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            Double precioVenta = Double.valueOf(TratamientoString.eliminarTildes(articuloGui.getPrecioVenta().getText()));
            art.set("precio_venta", BigDecimal.valueOf(precioVenta).setScale(2, RoundingMode.CEILING));
        } catch (NumberFormatException | ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en precio de venta", "Error!", JOptionPane.ERROR_MESSAGE);
        }

        if (Integer.parseInt(articuloGui.getStock().getValue().toString()) < 0) {
            JOptionPane.showMessageDialog(articuloGui, "Stock negativo", "Error!", JOptionPane.ERROR_MESSAGE);
            ret = false;
        } else {
            art.set("stock", articuloGui.getStock().getValue());
        }
        if (Integer.parseInt(articuloGui.getStockMinimo().getValue().toString()) < 0) {
            JOptionPane.showMessageDialog(articuloGui, "Stock minimo negativo", "Error!", JOptionPane.ERROR_MESSAGE);
            ret = false;
        } else {
            art.set("stock_minimo", articuloGui.getStockMinimo().getValue());
        }
        try {
            String equivFram = TratamientoString.eliminarTildes(articuloGui.getEquivFram().getText());
            art.set("equivalencia_fram", equivFram);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en equivalencia FRAM", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        art.setNombreProv(articuloGui.getProveedores().getSelectedItem().toString());
        return ret;
    }

    @Override
    public void focusGained(FocusEvent fe) {
    }

    @Override
    public void focusLost(FocusEvent fe) {
        if (fe.getSource() == articuloGui.getPrecioCompra()) {
            System.out.println("perdi el foco de precio compra");
            if (!articuloGui.getPrecioManual().isSelected()) {
                actualizarPrecioVenta();
            }
        }
    }
    
    private void cargarProveedores(){
        abrirBase();
        articuloGui.getProveedores().removeAllItems();
        listProveedores = Proveedor.findAll();
        Iterator<Proveedor> it = listProveedores.iterator();
        while (it.hasNext()) {
            Proveedor prov = it.next();
            articuloGui.getProveedores().addItem(prov.get("nombre"));
        }
        articuloGui.getProveedores().addItem("");

        
    }
}
