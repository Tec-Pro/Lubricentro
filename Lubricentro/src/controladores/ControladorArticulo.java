/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import abm.ABMArticulo;
import interfaz.ArticuloGui;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelos.Articulo;
import modelos.Cliente;
import org.javalite.activejdbc.Base;

/**
 *
 * @author nico
 */
public class ControladorArticulo implements ActionListener {

    private ArticuloGui articuloGui;
    private DefaultTableModel tablaArtDefault;
    private java.util.List<Articulo> listArticulos;
    private JTable tablaArticulos;
    private ABMArticulo abmArticulo;
    private Boolean isNuevo;
    private Boolean editandoInfo;
    private Articulo articulo;

    public ControladorArticulo(ArticuloGui articuloGui) {
        isNuevo = true;
        editandoInfo = false;
        articulo = new Articulo();
        this.articuloGui = articuloGui;
        this.articuloGui.setActionListener(this);
        tablaArtDefault = articuloGui.getTablaArticulosDefault();
        tablaArticulos = articuloGui.getArticulos();
        listArticulos = new LinkedList();
        abmArticulo = new ABMArticulo();
        abrirBase();
        listArticulos = Articulo.findAll();
        cerrarBase();
        actualizarLista();
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
        listArticulos = Articulo.where("codigo like ? or descripcion like ?", "%" + articuloGui.getBusqueda().getText() + "%", "%" + articuloGui.getBusqueda().getText() + "%");
        actualizarLista();
        cerrarBase();

    }

    public void tablaMouseClicked(java.awt.event.MouseEvent evt) {
        articuloGui.habilitarCampos(false);
        if (evt.getClickCount() == 2) {
            System.out.println("hice doble click en un articulo");
            articuloGui.limpiarCampos();
            abrirBase();
            Articulo art = Articulo.findFirst("codigo = ?", tablaArticulos.getValueAt(tablaArticulos.getSelectedRow(), 0));
            cerrarBase();
            articuloGui.CargarCampos(art);
        }
    }

    private void actualizarLista() {
        abrirBase();
        tablaArtDefault.setRowCount(0);
        Iterator<Articulo> it = listArticulos.iterator();
        while (it.hasNext()) {
            Articulo art = it.next();
            Object row[] = new String[7];
            row[0] = art.getString("codigo");
            row[1] = art.getString("descripcion");
            row[2] = art.getString("marca");
            row[3] = art.getString("stock");
            row[4] = art.getString("precio_compra");
            row[5] = art.getString("precio_venta");
            row[6] = art.getString("equivalencia_fram");
            tablaArtDefault.addRow(row);
            cerrarBase();
        }
    }

    private void agregarFila(Articulo art) {
        Object row[] = new String[7];
        row[0] = art.getString("codigo");
        row[1] = art.getString("descripcion");
        row[2] = art.getString("marca");
        row[3] = art.getString("stock");
        row[4] = art.getString("precio_compra");
        row[5] = art.getString("precio_venta");
        row[6] = art.getString("equivalencia_fram");
        tablaArtDefault.addRow(row);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == articuloGui.getNuevo()) {
            System.out.println("Boton nuevo pulsado");
            articuloGui.limpiarCampos();
            articuloGui.habilitarCampos(true);
            isNuevo = true;
            editandoInfo = true;
        }
        if (e.getSource() == articuloGui.getGuardar()) {
            System.out.println("Boton guardar pulsado");
            if (cargarDatosProd(articulo)) {
                abrirBase();
                if (abmArticulo.alta(articulo)) {
                    articuloGui.habilitarCampos(false);
                    articuloGui.limpiarCampos();
                    editandoInfo = false;
                    JOptionPane.showMessageDialog(articuloGui, "¡Artículo guardado exitosamente!");
                } else {
                    JOptionPane.showMessageDialog(articuloGui, "Ocurrió un error, no se guardó el artículo", "Error!", JOptionPane.ERROR_MESSAGE);
                }
                cerrarBase();
                realizarBusqueda();
            }
        }
        if (e.getSource() == articuloGui.getBorrar()) {
            System.out.println("Boton borrar pulsado");
            articuloGui.habilitarCampos(false);
        }
        if (e.getSource() == articuloGui.getModificar()) {
            System.out.println("Boton modificar pulsado");
            articuloGui.habilitarCampos(true);
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
            String codigo = TratamientoString.eliminarTildes(articuloGui.getCodigo().getText()).toUpperCase();
            art.set("codigo", codigo);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en el codigo", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            String marca = TratamientoString.eliminarTildes(articuloGui.getMarca().getText()).toUpperCase();
            art.set("marca", marca);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en la marca", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            String desc = TratamientoString.eliminarTildes(articuloGui.getDescripcion().getText()).toUpperCase();
            art.set("descripcion", desc);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en la descripcion", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            Double precioCompra = Double.valueOf(TratamientoString.eliminarTildes(articuloGui.getPrecioCompra().getText()).toUpperCase());
            art.set("precio_compra", BigDecimal.valueOf(precioCompra).setScale(2, RoundingMode.CEILING));
        } catch (NumberFormatException | ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en precio de compra", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            Double precioVenta = Double.valueOf(TratamientoString.eliminarTildes(articuloGui.getPrecioVenta().getText()).toUpperCase());
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
        return ret;
    }
}
