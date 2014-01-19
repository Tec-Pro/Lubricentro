/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import abm.ABMArticulo;
import abm.ABMProveedor;
import interfaz.ArticuloGui;
import interfaz.ProveedorGui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelos.Articulo;
import modelos.Proveedor;
import org.javalite.activejdbc.Base;

/**
 *
 * @author nico
 */
public class ControladorProveedor implements ActionListener {

    private ProveedorGui proveedorGui;
    private DefaultTableModel tablaArtProvDefault;
    private DefaultTableModel tablaProvDefault;
    private java.util.List<Proveedor> listProveedores;
    private java.util.List<Articulo> listArticulos;
    private JTable tablaProveedor;
    private ABMProveedor abmProveedor;
    private Boolean isNuevo;
    private Boolean editandoInfo;
    private Proveedor proveedor;

    public ControladorProveedor(ProveedorGui proveedorGui){
        isNuevo = true;
        editandoInfo = false;
        this.proveedorGui = proveedorGui;
        this.proveedorGui.setActionListener(this);
        tablaArtProvDefault = proveedorGui.getArticulosProv();
        tablaProvDefault = proveedorGui.getProveedores();
        tablaProveedor = proveedorGui.getTablaProveedores();
        listProveedores = new LinkedList();
        abmProveedor = new ABMProveedor();
        proveedor = new Proveedor();
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
            proveedorGui.habilitarCampos(false);
            proveedorGui.getBorrar().setEnabled(true);
            proveedorGui.getModificar().setEnabled(true);
            proveedorGui.getGuardar().setEnabled(false);
            proveedorGui.getNuevo().setEnabled(true);
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
                Object row[] = new String[6];
                row[0] = art.getString("codigo");
                row[1] = art.getString("descripcion");
                row[2] = art.getString("marca");
                row[3] = art.getBigDecimal("precio_compra").setScale(2, RoundingMode.CEILING).toString();
                row[4] = art.getBigDecimal("precio_venta").setScale(2, RoundingMode.CEILING).toString();
                row[5] = art.getString("equivalencia_fram");
                tablaArtProvDefault.addRow(row);

            }
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
            proveedorGui.getModificar().setEnabled(false);
            proveedorGui.getGuardar().setEnabled(true);
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
            if (proveedor.getString("id") != null && !editandoInfo) {
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
            JOptionPane.showMessageDialog(proveedorGui, "Error en el nombre", "Error!", JOptionPane.ERROR_MESSAGE);
        }

        return ret;
    }
}
