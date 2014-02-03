/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import abm.ABMArticulo;
import abm.ABMCliente;
import abm.ABMCompra;
import abm.ABMProveedor;
import abm.ABMVenta;
import interfaz.ImportarExcelGui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelos.Articulo;
import modelos.ArticulosVentas;
import modelos.Cliente;
import modelos.Compra;
import modelos.Pago;
import modelos.Proveedor;
import modelos.Venta;
import net.sf.jasperreports.engine.util.Pair;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.javalite.activejdbc.Base;

/**
 *
 * @author nico
 */
public class controladorImportarGui implements ActionListener {

    ImportarExcelGui importarGui;
    HSSFWorkbook workbook;
    HSSFSheet sheet;
    ABMProveedor abmProveedor;
    ABMCliente abmCliente;
    Proveedor prov;
    Integer agregados;
    Integer modificados;
    private Cliente cliente;
    private Articulo articulo;
    private ABMArticulo abmArticulo;
    private Boolean importando;
    private Venta venta;
    private Compra compra;
    private ABMVenta abmVenta;
    private ABMCompra abmCompra;

    public controladorImportarGui(ImportarExcelGui importarGui) {
        this.importarGui = importarGui;
        this.importarGui.setActionListener(this);
        abmProveedor = new ABMProveedor();
        abmCliente = new ABMCliente();
        abmArticulo = new ABMArticulo();
        abmVenta = new ABMVenta();
        abmCompra = new ABMCompra();
        prov = new Proveedor();
        cliente = new Cliente();
        articulo = new Articulo();
        importando = false;
        cargarProveedores();
        importarGui.getCategoria().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                habilitarProveedores();
            }
        });
    }

    private void habilitarProveedores() {
        if (!importarGui.getCategoria().getSelectedItem().equals("Artículos")) {
            importarGui.getProveedor().setEnabled(false);
        } else {
            cargarProveedores();
            importarGui.getProveedor().setEnabled(true);
        }
    }

    public void cargarProveedores() {
        importarGui.getProveedor().removeAllItems();
        abrirBase();
        List<Proveedor> proveedores = Proveedor.findAll();
        Iterator<Proveedor> it = proveedores.iterator();
        while (it.hasNext()) {
            prov = it.next();
            importarGui.getProveedor().addItem(prov.get("nombre"));
        }
        cerrarBase();
        importarGui.getProveedor().addItem("");
        importarGui.getProveedor().setSelectedItem("");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == importarGui.getCancelar()) {
            if (importando) {
                int respu = JOptionPane.showConfirmDialog(importarGui, "¿Desea cancelar la importación de datos?", "Cancelar importación", JOptionPane.YES_NO_OPTION);
                if (respu == JOptionPane.YES_OPTION) {
                    importando = false;
                }
            }
            importarGui.setVisible(false);
        }
        if (ae.getSource() == importarGui.getAceptar()) {//Aceptar luego de buscar archivo
            agregados = 0;
            modificados = 0;
            if (importarGui.getCategoria().getSelectedItem().equals("Proveedores")) {//si se selecciono proveedores
                System.out.println("boton aceptar pulsado proveedores");
                if (importarGui.getSelectorArchivos().getSelectedFile() != null) {//corroboro que se haya seleccionado un archivo
                    if (importarGui.getSelectorArchivos().getSelectedFile().getName().contains(".xls")
                            || importarGui.getSelectorArchivos().getSelectedFile().getName().contains(".xlsx")) {//que sea excel
                        try {
                            importarExcelProveedores();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(controladorImportarGui.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(importarGui, "Archivo no encontrado", "Error!", JOptionPane.ERROR_MESSAGE);
                        } catch (IOException ex) {
                            Logger.getLogger(controladorImportarGui.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(importarGui, "Archivo incorrecto", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            if (importarGui.getCategoria().getSelectedItem().equals("Clientes")) {// importar clientes
                System.out.println("boton aceptar pulsado clientes");
                if (importarGui.getSelectorArchivos().getSelectedFile() != null) {
                    if (importarGui.getSelectorArchivos().getSelectedFile().getName().contains(".xls") || importarGui.getSelectorArchivos().getSelectedFile().getName().contains(".xlsx")) {
                        try {
                            importarExcelClientes();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(controladorImportarGui.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(importarGui, "Archivo no encontrado", "Error!", JOptionPane.ERROR_MESSAGE);
                        } catch (IOException ex) {
                            Logger.getLogger(controladorImportarGui.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(importarGui, "Archivo incorrecto", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            if (importarGui.getCategoria().getSelectedItem().equals("Artículos")) {// importar clientes
                System.out.println("boton aceptar pulsado Articulos");
                if (importarGui.getSelectorArchivos().getSelectedFile() != null) {
                    if (importarGui.getSelectorArchivos().getSelectedFile().getName().contains(".xls") || importarGui.getSelectorArchivos().getSelectedFile().getName().contains(".xlsx")) {
                        try {
                            importarExcelArticulos();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(controladorImportarGui.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(importarGui, "Archivo no encontrado", "Error!", JOptionPane.ERROR_MESSAGE);
                        } catch (IOException ex) {
                            Logger.getLogger(controladorImportarGui.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(importarGui, "Archivo incorrecto", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            if (importarGui.getCategoria().getSelectedItem().equals("Compra")) {
                System.out.println("compra seleccionada");
            }
            if (importarGui.getCategoria().getSelectedItem().equals("Venta")) {
                System.out.println("venta seleccionada");
                if (importarGui.getSelectorArchivos().getSelectedFile() != null) {
                    if (importarGui.getSelectorArchivos().getSelectedFile().getName().contains(".xls") || importarGui.getSelectorArchivos().getSelectedFile().getName().contains(".xlsx")) {
                        try {
                            importarVenta();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(controladorImportarGui.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(importarGui, "Archivo no encontrado", "Error!", JOptionPane.ERROR_MESSAGE);
                        } catch (IOException ex) {
                            Logger.getLogger(controladorImportarGui.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(importarGui, "Archivo incorrecto", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            if (importarGui.getCategoria().getSelectedItem().equals("Compra")) {
                System.out.println("compra seleccionada");
                if (importarGui.getSelectorArchivos().getSelectedFile() != null) {
                    if (importarGui.getSelectorArchivos().getSelectedFile().getName().contains(".xls") || importarGui.getSelectorArchivos().getSelectedFile().getName().contains(".xlsx")) {
                        try {
                            importarCompra();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(controladorImportarGui.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(importarGui, "Archivo no encontrado", "Error!", JOptionPane.ERROR_MESSAGE);
                        } catch (IOException ex) {
                            Logger.getLogger(controladorImportarGui.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(importarGui, "Archivo incorrecto", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    private void importarExcelProveedores() throws FileNotFoundException, IOException {
        importarGui.getProgreso().setIndeterminate(true);
        final javax.swing.SwingWorker worker = new javax.swing.SwingWorker() {
            @Override
            protected Void doInBackground() throws Exception {
                if (importarGui.getSelectorArchivos().getSelectedFile().getName().contains("xlsx")) {//Archivos con extensión xlsx
                    importando = true;
                    XSSFWorkbook Libro_trabajo = new XSSFWorkbook(importarGui.getSelectorArchivos().getSelectedFile().getAbsolutePath());
                    XSSFSheet hoja = Libro_trabajo.getSheet("IMPORTARHOJA");
                    if (hoja == null) {
                        JOptionPane.showMessageDialog(null, "No se encontró la hoja IMPORTARHOJA, renombrela e intente nuevamente");
                    } else {
                        Iterator iterarFilas = hoja.rowIterator();
                        XSSFCell celdaNombre;
                        XSSFCell celdaTelefono;
                        String telefonoString;
                        abrirBase();
                        agregados = 0;
                        modificados = 0;
                        iterarFilas.next();
                        while (iterarFilas.hasNext() && importando) {
                            XSSFRow row = (XSSFRow) iterarFilas.next();
                            celdaNombre = (XSSFCell) row.getCell(0);
                            celdaTelefono = (XSSFCell) row.getCell(1);
                            telefonoString = "";
                            if (celdaNombre != null) {
                                if (!celdaNombre.toString().isEmpty()) {
                                    celdaNombre.setCellType(Cell.CELL_TYPE_STRING);
                                    String nombre = celdaNombre.getStringCellValue();
                                    if (celdaTelefono != null) {
                                        celdaTelefono.setCellType(Cell.CELL_TYPE_STRING);
                                        telefonoString = celdaTelefono.toString();
                                    }
                                    prov.set("nombre", nombre, "telefono", telefonoString);
                                    if (abmProveedor.findProveedor(prov)) {
                                        abmProveedor.modificar(prov);
                                        System.out.println("modificando proveedor");
                                    } else {
                                        abmProveedor.alta(prov);
                                        System.out.println("nuevo ");
                                        agregados++;
                                    }
                                }
                            }
                        }
                        cerrarBase();
                    }
                } else if (importarGui.getSelectorArchivos().getSelectedFile().getName().contains("xls")) {
                    importando = true;
                    workbook = new HSSFWorkbook(new FileInputStream(importarGui.getSelectorArchivos().getSelectedFile().getAbsolutePath()));
                    sheet = workbook.getSheet("IMPORTARHOJA");
                    if (sheet == null) {
                        JOptionPane.showMessageDialog(null, "No se encontró la hoja IMPORTARHOJA, renombrela e intente nuevamente");
                    } else {
                        Iterator<Row> iterarFilas = sheet.iterator();
                        Cell celdaNombre;
                        Cell celdaTelefono;
                        String telefonoString;
                        abrirBase();
                        agregados = 0;
                        modificados = 0;
                        iterarFilas.next();
                        while (iterarFilas.hasNext() && importando) {
                            Row row = iterarFilas.next();
                            celdaNombre = row.getCell(0);
                            celdaTelefono = row.getCell(1);
                            telefonoString = "";
                            if (celdaNombre != null) {
                                if (!celdaNombre.toString().isEmpty()) {
                                    celdaNombre.setCellType(Cell.CELL_TYPE_STRING);
                                    String nombre = celdaNombre.getStringCellValue();
                                    if (celdaTelefono != null) {
                                        celdaTelefono.setCellType(Cell.CELL_TYPE_STRING);
                                        telefonoString = celdaTelefono.getStringCellValue();
                                    }
                                    prov.set("nombre", nombre, "telefono", telefonoString);
                                    if (abmProveedor.findProveedor(prov)) {
                                        abmProveedor.modificar(prov);
                                        System.out.println("modifique");

                                    } else {
                                        abmProveedor.alta(prov);
                                        agregados++;
                                    }
                                }
                            }
                        }
                        cerrarBase();
                    }
                }
                return null;
            }

            protected void done() {
                setProgress(100);
                JOptionPane.showMessageDialog(importarGui, "Importación de registros terminada, se han agregado " + agregados + " Proveedores");
                importarGui.getProgreso().setIndeterminate(false);
                importando = false;
            }
        };
        worker.execute();
    }

    ;
                

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

    private void importarExcelClientes() throws FileNotFoundException, IOException {
        importarGui.getProgreso().setIndeterminate(true);
        final javax.swing.SwingWorker worker = new javax.swing.SwingWorker() {
            @Override
            protected Void doInBackground() throws Exception {
                if (importarGui.getSelectorArchivos().getSelectedFile().getName().contains("xlsx")) {
                    importando = true;
                    XSSFWorkbook Libro_trabajo = new XSSFWorkbook(importarGui.getSelectorArchivos().getSelectedFile().getAbsolutePath());
                    XSSFSheet hoja = Libro_trabajo.getSheet("IMPORTARHOJA");
                    if (hoja == null) {
                        JOptionPane.showMessageDialog(null, "No se encontró la hoja IMPORTARHOJA, renombrela e intente nuevamente");
                    } else {
                        Iterator iterarFilas = hoja.rowIterator();
                        XSSFCell celdaNombre;
                        XSSFCell celdaTelefono;
                        XSSFCell celdaCelular;
                        String telefonoString;
                        String telefonoCelularString;
                        abrirBase();
                        agregados = 0;
                        modificados = 0;
                        iterarFilas.next();
                        while (iterarFilas.hasNext() && importando) {
                            XSSFRow row = (XSSFRow) iterarFilas.next();
                            celdaNombre = (XSSFCell) row.getCell(0);
                            celdaTelefono = (XSSFCell) row.getCell(1);
                            celdaCelular = (XSSFCell) row.getCell(2);
                            telefonoString = "";
                            telefonoCelularString = "";
                            if (celdaNombre != null) {
                                if (!celdaNombre.toString().isEmpty()) {
                                    celdaNombre.setCellType(Cell.CELL_TYPE_STRING);
                                    String nombre = celdaNombre.getStringCellValue();
                                    if (celdaTelefono != null) {
                                        celdaTelefono.setCellType(Cell.CELL_TYPE_STRING);
                                        telefonoString = celdaTelefono.toString();
                                    }
                                    if (celdaCelular != null) {
                                        celdaCelular.setCellType(Cell.CELL_TYPE_STRING);
                                        telefonoCelularString = celdaCelular.toString();
                                    }
                                    cliente.set("nombre", nombre, "telefono", telefonoString, "celular", telefonoCelularString);
                                    if (abmCliente.findCliente(cliente)) {
                                        abmCliente.modificar(cliente);
                                        System.out.println("modificando cliente");
                                    } else {
                                        abmCliente.alta(cliente);
                                        System.out.println("nuevo cliente");
                                        agregados++;
                                    }
                                }
                            }
                        }
                        cerrarBase();
                    }
                } else if (importarGui.getSelectorArchivos().getSelectedFile().getName().contains("xls")) {
                    importando = true;
                    workbook = new HSSFWorkbook(new FileInputStream(importarGui.getSelectorArchivos().getSelectedFile().getAbsolutePath()));
                    sheet = workbook.getSheet("IMPORTARHOJA");
                    if (sheet == null) {
                        JOptionPane.showMessageDialog(null, "No se encontró la hoja IMPORTARHOJA, renombrela e intente nuevamente");
                    } else {
                        Iterator<Row> iterarFilas = sheet.iterator();
                        Cell celdaNombre;
                        Cell celdaTelefono;
                        Cell celdaCelular;
                        String telefonoString;
                        String telefonoCelularString;
                        abrirBase();
                        agregados = 0;
                        modificados = 0;
                        iterarFilas.next();
                        while (iterarFilas.hasNext() && importando) {
                            Row row = iterarFilas.next();
                            celdaNombre = row.getCell(0);
                            celdaTelefono = row.getCell(1);
                            celdaCelular = row.getCell(2);
                            telefonoString = "";
                            telefonoCelularString = "";
                            if (celdaNombre != null) {
                                if (!celdaNombre.toString().isEmpty()) {
                                    celdaNombre.setCellType(Cell.CELL_TYPE_STRING);
                                    String nombre = celdaNombre.getStringCellValue();
                                    if (celdaTelefono != null) {
                                        celdaTelefono.setCellType(Cell.CELL_TYPE_STRING);
                                        telefonoString = celdaTelefono.getStringCellValue();
                                    }
                                    if (celdaCelular != null) {
                                        celdaCelular.setCellType(Cell.CELL_TYPE_STRING);
                                        telefonoCelularString = celdaCelular.getStringCellValue();
                                    }
                                    cliente.set("nombre", nombre, "telefono", telefonoString, "celular", telefonoCelularString);
                                    if (abmCliente.findCliente(cliente)) {
                                        abmCliente.modificar(cliente);
                                    } else {
                                        abmCliente.alta(cliente);
                                        agregados++;
                                    }
                                }
                            }
                        }
                        cerrarBase();
                    }
                }
                return null;
            }

            protected void done() {
                setProgress(100);
                JOptionPane.showMessageDialog(importarGui, "Importación de registros terminada, se han agregado " + agregados + " clientes");
                importarGui.getProgreso().setIndeterminate(false);
                importando = false;
            }
        };
        worker.execute();
    }

    ;
    
    
    
    private void importarExcelArticulos() throws FileNotFoundException, IOException {
        importarGui.getProgreso().setIndeterminate(true);
        final javax.swing.SwingWorker worker = new javax.swing.SwingWorker() {
            @Override
            protected Void doInBackground() throws Exception {
                if (importarGui.getSelectorArchivos().getSelectedFile().getName().contains("xlsx")) {
                    importando = true;
                    XSSFWorkbook Libro_trabajo = new XSSFWorkbook(importarGui.getSelectorArchivos().getSelectedFile().getAbsolutePath());
                    XSSFSheet hoja = Libro_trabajo.getSheet("IMPORTARHOJA");
                    if (hoja == null) {
                        JOptionPane.showMessageDialog(null, "No se encontró la hoja IMPORTARHOJA, renombrela e intente nuevamente");
                    } else {
                        Iterator iterarFilas = hoja.rowIterator();
                        XSSFCell celdaCodigo;
                        XSSFCell celdaDescripcion;
                        XSSFCell celdaMarca;
                        XSSFCell celdaPrecioCompra;
                        XSSFCell celdaPrecioVenta;
                        XSSFCell celdaEquivalenciaFram;
                        String codigoString;
                        String descripcionString;
                        String marcaString;
                        String precioString;
                        String precioStringVenta;
                        String equivalenciaString;
                        double precioFloat;
                        BigDecimal precioCompraBig;
                        BigDecimal precioVenta;
                        abrirBase();
                        agregados = 0;
                        modificados = 0;
                        iterarFilas.next();
                        while (iterarFilas.hasNext() && importando) {
                            XSSFRow row = (XSSFRow) iterarFilas.next();
                            celdaCodigo = (XSSFCell) row.getCell(0);
                            celdaDescripcion = (XSSFCell) row.getCell(1);
                            celdaMarca = (XSSFCell) row.getCell(2);
                            celdaPrecioCompra = (XSSFCell) row.getCell(3);
                            celdaPrecioVenta = (XSSFCell) row.getCell(4);
                            celdaEquivalenciaFram = (XSSFCell) row.getCell(5);
                            descripcionString = "";
                            marcaString = "";
                            precioCompraBig = new BigDecimal(0);
                            precioVenta = new BigDecimal(0);
                            equivalenciaString = "";
                            if (celdaCodigo != null) {
                                if (!celdaCodigo.toString().isEmpty()) {
                                    celdaCodigo.setCellType(Cell.CELL_TYPE_STRING);
                                    codigoString = celdaCodigo.getStringCellValue();
                                    if (celdaDescripcion != null) {
                                        celdaDescripcion.setCellType(Cell.CELL_TYPE_STRING);
                                        descripcionString = celdaDescripcion.toString();
                                    }
                                    if (celdaMarca != null) {
                                        celdaMarca.setCellType(Cell.CELL_TYPE_STRING);
                                        marcaString = celdaMarca.toString();
                                    }
                                    if (celdaPrecioCompra != null) {
                                        celdaPrecioCompra.setCellType(Cell.CELL_TYPE_STRING);
                                        precioString = celdaPrecioCompra.toString();
                                        precioFloat = Float.parseFloat(precioString);
                                        precioCompraBig = BigDecimal.valueOf(precioFloat).setScale(2, RoundingMode.CEILING);

                                    }
                                    if (celdaPrecioVenta != null) {
                                        celdaPrecioVenta.setCellType(Cell.CELL_TYPE_STRING);
                                        precioStringVenta = celdaPrecioVenta.toString();
                                        precioFloat = Float.parseFloat(precioStringVenta);
                                        precioVenta = BigDecimal.valueOf(precioFloat).setScale(2, RoundingMode.CEILING);

                                    }
                                    if (celdaEquivalenciaFram != null) {
                                        celdaEquivalenciaFram.setCellType(Cell.CELL_TYPE_STRING);
                                        equivalenciaString = celdaEquivalenciaFram.toString();
                                    }
                                    articulo.set("codigo", codigoString, "descripcion", descripcionString, "marca", marcaString, "precio_compra", precioCompraBig, "precio_venta", precioVenta, "equivalencia_fram", equivalenciaString, "stock", 0, "stock_minimo", 0);
                                    if (abmArticulo.findArticulo(articulo)) {
                                        abmArticulo.modificar(articulo);
                                        if (!importarGui.getProveedor().getSelectedItem().equals("")) {
                                            prov.set("nombre", importarGui.getProveedor().getSelectedItem());
                                            prov = abmProveedor.getProveedor(prov);
                                            prov.add(articulo);
                                        } else {
                                            articulo.set("proveedor_id", null);
                                        }
                                        System.out.println("modificando articulo");
                                    } else {
                                        abmArticulo.alta(articulo);
                                        if (!importarGui.getProveedor().getSelectedItem().equals("")) {
                                            prov.set("nombre", importarGui.getProveedor().getSelectedItem());
                                            prov = abmProveedor.getProveedor(prov);
                                            prov.add(articulo);
                                        }
                                        System.out.println("nuevo articulo");
                                        agregados++;
                                    }
                                }
                            }
                        }
                        cerrarBase();
                    }
                } else if (importarGui.getSelectorArchivos().getSelectedFile().getName().contains("xls")) {
                    importando = true;
                    workbook = new HSSFWorkbook(new FileInputStream(importarGui.getSelectorArchivos().getSelectedFile().getAbsolutePath()));
                    sheet = workbook.getSheet("IMPORTARHOJA");
                    if (sheet == null) {
                        JOptionPane.showMessageDialog(null, "No se encontró la hoja IMPORTARHOJA, renombrela e intente nuevamente");
                    } else {
                        Iterator<Row> iterarFilas = sheet.iterator();
                        Cell celdaCodigo;
                        Cell celdaDescripcion;
                        Cell celdaMarca;
                        Cell celdaPrecioCompra;
                        Cell celdaPrecioVenta;
                        Cell celdaEquivalenciaFram;
                        String codigoString;
                        String descripcionString;
                        String marcaString;
                        String precioString;
                        String equivalenciaString;
                        double precioFloat;
                        BigDecimal precioCompraBig;
                        BigDecimal precioVenta;
                        abrirBase();
                        agregados = 0;
                        modificados = 0;
                        iterarFilas.next();
                        while (iterarFilas.hasNext() && importando) {
                            Row row = iterarFilas.next();
                            celdaCodigo = row.getCell(0);
                            celdaDescripcion = row.getCell(1);
                            celdaMarca = row.getCell(2);
                            celdaPrecioCompra = row.getCell(3);
                            celdaPrecioVenta = row.getCell(4);
                            celdaEquivalenciaFram = row.getCell(5);
                            descripcionString = "";
                            marcaString = "";
                            precioCompraBig = new BigDecimal(0);
                            precioVenta = new BigDecimal(0);
                            equivalenciaString = "";

                            if (celdaCodigo != null) {
                                if (!celdaCodigo.toString().isEmpty()) {
                                    celdaCodigo.setCellType(Cell.CELL_TYPE_STRING);
                                    codigoString = celdaCodigo.getStringCellValue();
                                    if (celdaDescripcion != null) {
                                        celdaDescripcion.setCellType(Cell.CELL_TYPE_STRING);
                                        descripcionString = celdaDescripcion.getStringCellValue();
                                    }
                                    if (celdaMarca != null) {
                                        celdaMarca.setCellType(Cell.CELL_TYPE_STRING);
                                        marcaString = celdaMarca.getStringCellValue();
                                    }
                                    if (celdaPrecioCompra != null) {
                                        celdaPrecioCompra.setCellType(Cell.CELL_TYPE_STRING);
                                        precioString = celdaPrecioCompra.getStringCellValue();
                                        precioFloat = Float.parseFloat(precioString);
                                        precioCompraBig = BigDecimal.valueOf(precioFloat).setScale(2, RoundingMode.CEILING);

                                    }
                                    if (celdaPrecioVenta != null) {
                                        celdaPrecioVenta.setCellType(Cell.CELL_TYPE_STRING);
                                        precioString = celdaPrecioVenta.getStringCellValue();
                                        precioFloat = Float.parseFloat(precioString);
                                        precioVenta = BigDecimal.valueOf(precioFloat).setScale(2, RoundingMode.CEILING);
                                    }
                                    if (celdaEquivalenciaFram != null) {
                                        celdaEquivalenciaFram.setCellType(Cell.CELL_TYPE_STRING);
                                        equivalenciaString = celdaEquivalenciaFram.getStringCellValue();
                                    }
                                    articulo.set("codigo", codigoString, "descripcion", descripcionString, "marca", marcaString, "precio_compra", precioCompraBig, "precio_venta", precioVenta, "equivalencia_fram", equivalenciaString, "stock", 0, "stock_minimo", 0);
                                    if (abmArticulo.findArticulo(articulo)) {
                                        abmArticulo.modificar(articulo);
                                        if (!importarGui.getProveedor().getSelectedItem().equals("")) {
                                            prov.set("nombre", importarGui.getProveedor().getSelectedItem());
                                            prov = abmProveedor.getProveedor(prov);
                                            prov.add(articulo);
                                        } else {
                                            articulo.set("proveedor_id", null);
                                        }
                                        System.out.println("modificando articulo");
                                    } else {
                                        abmArticulo.alta(articulo);
                                        if (!importarGui.getProveedor().getSelectedItem().equals("")) {
                                            prov.set("nombre", importarGui.getProveedor().getSelectedItem());
                                            prov = abmProveedor.getProveedor(prov);
                                            prov.add(articulo);
                                        }
                                        System.out.println("nuevo articulo");
                                        agregados++;
                                    }
                                }
                            }
                        }
                        cerrarBase();
                    }
                }
                return null;
            }

            protected void done() {
                setProgress(100);
                JOptionPane.showMessageDialog(importarGui, "Importación de registros terminada, se han agregado " + agregados + " Articulos");
                importarGui.getProgreso().setIndeterminate(false);
                importando = false;
            }
        };
        worker.execute();
    }

    ;
    
    
    
    
    
    
    
    
    private void importarVenta() throws FileNotFoundException, IOException {
        importarGui.getProgreso().setIndeterminate(true);
        final javax.swing.SwingWorker worker = new javax.swing.SwingWorker() {
            @Override
            protected Void doInBackground() throws Exception {
                if (importarGui.getSelectorArchivos().getSelectedFile().getName().contains("xlsx")) {//Archivos con extensión xlsx
                    importando = true;
                    XSSFWorkbook Libro_trabajo = new XSSFWorkbook(importarGui.getSelectorArchivos().getSelectedFile().getAbsolutePath());
                    XSSFSheet hoja = Libro_trabajo.getSheet("IMPORTARHOJA");
                    if (hoja == null) {
                        JOptionPane.showMessageDialog(null, "No se encontró la hoja IMPORTARHOJA, renombrela e intente nuevamente");
                    } else {
                        Iterator iterarFilas = hoja.rowIterator();
                        XSSFCell celdaCliente;
                        XSSFCell celdaFecha;
                        XSSFCell celdaSiPago;
                        XSSFCell celdaCodigo;
                        XSSFCell celdaCantidad;
                        XSSFCell celdaPrecio;
                        Date fechaDate = Calendar.getInstance().getTime();
                        String siPago;
                        boolean siPagoBool = true;
                        String codigoString;
                        BigDecimal cantidadBigDecimal;
                        double pagoDouble;
                        BigDecimal monto = new BigDecimal(0);
                        BigDecimal pagoBig;
                        pagoBig = new BigDecimal(0);
                        abrirBase();
                        agregados = 0;
                        modificados = 0;
                        System.out.println(1);
                        XSSFRow primerFila = (XSSFRow) iterarFilas.next();
                        celdaCliente = (XSSFCell) primerFila.getCell(1);
                        celdaFecha = (XSSFCell) primerFila.getCell(3);
                        venta = new Venta();
                        articulo = new Articulo();
                        cliente = new Cliente();
                        if (celdaCliente != null) {
                            System.out.println(3);
                            if (!celdaCliente.toString().isEmpty()) {
                                System.out.println(4);
                                celdaCliente.setCellType(Cell.CELL_TYPE_STRING);
                                String nombre = celdaCliente.getStringCellValue();
                                if (celdaFecha != null) {
                                    System.out.println(5);
                                    fechaDate = celdaFecha.getDateCellValue();
                                }
                                XSSFRow segundaFila = (XSSFRow) iterarFilas.next();
                                celdaSiPago = (XSSFCell) segundaFila.getCell(1);
                                if (celdaSiPago != null) {
                                    System.out.println(6);
                                    celdaSiPago.setCellType(Cell.CELL_TYPE_STRING);
                                    siPago = celdaSiPago.getStringCellValue();
                                    siPago = siPago.toLowerCase();
                                    if (siPago.compareTo("si") == 0) {
                                        siPagoBool = true;
                                    } else {
                                        siPagoBool = false;
                                        monto = null;
                                    }
                                }
                                iterarFilas.next();
                                abrirBase();
                                System.out.println(7 + nombre);
                                LinkedList<Pair> parDeProductos = new LinkedList();
                                LinkedList<BigDecimal> preciosFinales = new LinkedList();
                                cliente = Cliente.findFirst("nombre = ?", nombre);
                                System.out.println(8 + nombre + " " + fechaDate);
                                venta.set("cliente_id", cliente.getId());
                                cerrarBase();
                                while (iterarFilas.hasNext() && importando) {
                                    XSSFRow row = (XSSFRow) iterarFilas.next();
                                    celdaCodigo = (XSSFCell) row.getCell(0);
                                    celdaCantidad = (XSSFCell) row.getCell(1);
                                    celdaPrecio = (XSSFCell) row.getCell(2);
                                    if (celdaCodigo != null) {
                                        if (!celdaCodigo.toString().isEmpty()) {
                                            celdaCodigo.setCellType(Cell.CELL_TYPE_STRING);
                                            codigoString = celdaCodigo.toString();
                                            System.out.println("cod " + codigoString);
                                            articulo.set("codigo", codigoString);
                                            abrirBase();
                                            if (abmArticulo.findArticulo(articulo)) {
                                                System.out.println("encontre el articulo");
                                                articulo = abmArticulo.getProducto(articulo);

                                            }
                                            cerrarBase();
                                            cantidadBigDecimal = new BigDecimal(0);
                                            if (celdaCantidad != null) {
                                                celdaCantidad.setCellType(Cell.CELL_TYPE_NUMERIC);
                                                cantidadBigDecimal = BigDecimal.valueOf(celdaCantidad.getNumericCellValue());
                                                System.out.println("cant " + cantidadBigDecimal);
                                            }
                                            if (siPagoBool) {
                                                if (celdaPrecio != null) {
                                                    celdaPrecio.setCellType(Cell.CELL_TYPE_NUMERIC);
                                                    pagoDouble = celdaPrecio.getNumericCellValue();
                                                    System.out.println("sale " + pagoDouble);
                                                    preciosFinales.add(BigDecimal.valueOf(pagoDouble).setScale(2));
                                                    Pair par = new Pair(articulo, cantidadBigDecimal); //creo el par
                                                    parDeProductos.add(par); //meto el par a la lista
                                                    monto = monto.add(cantidadBigDecimal.multiply(BigDecimal.valueOf(pagoDouble).setScale(2)));
                                                }
                                            } else {
                                                Pair par = new Pair(articulo, cantidadBigDecimal); //creo el par
                                                parDeProductos.add(par); //meto el par a la lista
                                            }
                                        }
                                    }
                                }
                                java.sql.Date sqlFecha = new java.sql.Date(fechaDate.getTime());
                                venta.set("fecha", sqlFecha);

                                if (siPagoBool) {
                                    venta.setPreciosFinales(preciosFinales);
                                    Iterator<BigDecimal> it = preciosFinales.iterator();
                                    while (it.hasNext()) {
                                        System.out.println((BigDecimal) it.next());
                                    }
                                    Iterator<Pair> ita = parDeProductos.iterator();
                                    while (ita.hasNext()) {
                                        Pair par = ita.next();
                                        System.out.println(par.second());
                                        Articulo ar = (Articulo) par.first();
                                        System.out.println(ar.getString("codigo"));
                                    }
                                    venta.setProductos(parDeProductos);
                                    venta.set("pago", true);
                                    venta.set("monto", monto);
                                } else {
                                    venta.set("pago", false);
                                    venta.setProductos(parDeProductos);
                                }
                                abrirBase();
                                System.out.println(sqlFecha);

                                System.out.println(abmVenta.alta(venta));
                                cerrarBase();

                            }
                        }
                    }
                } else if (importarGui.getSelectorArchivos().getSelectedFile().getName().contains("xls")) {
                    importando = true;
                    workbook = new HSSFWorkbook(new FileInputStream(importarGui.getSelectorArchivos().getSelectedFile().getAbsolutePath()));
                    sheet = workbook.getSheet("IMPORTARHOJA");
                    if (sheet == null) {
                        JOptionPane.showMessageDialog(null, "No se encontró la hoja IMPORTARHOJA, renombrela e intente nuevamente");
                    } else {
                        Iterator<Row> iterarFilas = sheet.iterator();
                        Cell celdaCliente;
                        Cell celdaFecha;
                        Cell celdaSiPago;
                        Cell celdaCodigo;
                        Cell celdaCantidad;
                        Cell celdaPrecio;
                        Date fechaDate = Calendar.getInstance().getTime();
                        String siPago;
                        boolean siPagoBool = true;
                        String codigoString;
                        BigDecimal cantidadBigDecimal;
                        double pagoDouble;
                        BigDecimal monto = new BigDecimal(0);
                        BigDecimal pagoBig;
                        pagoBig = new BigDecimal(0);
                        abrirBase();
                        agregados = 0;
                        modificados = 0;
                        System.out.println(1);
                        Row primerFila = iterarFilas.next();
                        celdaCliente = primerFila.getCell(1);
                        celdaFecha = primerFila.getCell(3);
                        venta = new Venta();
                        articulo = new Articulo();
                        cliente = new Cliente();
                        if (celdaCliente != null) {
                            System.out.println(3);
                            if (!celdaCliente.toString().isEmpty()) {
                                System.out.println(4);
                                celdaCliente.setCellType(Cell.CELL_TYPE_STRING);
                                String nombre = celdaCliente.getStringCellValue();
                                if (celdaFecha != null) {
                                    System.out.println(5);
                                    fechaDate = celdaFecha.getDateCellValue();
                                }
                                Row segundaFila = iterarFilas.next();
                                celdaSiPago = segundaFila.getCell(1);
                                if (celdaSiPago != null) {
                                    System.out.println(6);
                                    celdaSiPago.setCellType(Cell.CELL_TYPE_STRING);
                                    siPago = celdaSiPago.getStringCellValue();
                                    siPago = siPago.toLowerCase();
                                    if (siPago.compareTo("si") == 0) {
                                        siPagoBool = true;
                                    } else {
                                        siPagoBool = false;
                                        monto = null;
                                    }
                                }
                                iterarFilas.next();
                                abrirBase();
                                System.out.println(7 + nombre);
                                LinkedList<Pair> parDeProductos = new LinkedList();
                                LinkedList<BigDecimal> preciosFinales = new LinkedList();
                                cliente = Cliente.findFirst("nombre = ?", nombre);
                                System.out.println(8 + nombre + " " + fechaDate);
                                venta.set("cliente_id", cliente.getId());
                                cerrarBase();
                                while (iterarFilas.hasNext() && importando) {
                                    Row row = iterarFilas.next();
                                    celdaCodigo = row.getCell(0);
                                    celdaCantidad = row.getCell(1);
                                    celdaPrecio = row.getCell(2);
                                    if (celdaCodigo != null) {
                                        if (!celdaCodigo.toString().isEmpty()) {
                                            celdaCodigo.setCellType(Cell.CELL_TYPE_STRING);
                                            codigoString = celdaCodigo.toString();
                                            System.out.println("cod " + codigoString);
                                            articulo.set("codigo", codigoString);
                                            abrirBase();
                                            if (abmArticulo.findArticulo(articulo)) {
                                                System.out.println("encontre el articulo");
                                                articulo = abmArticulo.getProducto(articulo);

                                            }
                                            cerrarBase();
                                            cantidadBigDecimal = new BigDecimal(0);
                                            if (celdaCantidad != null) {
                                                celdaCantidad.setCellType(Cell.CELL_TYPE_NUMERIC);
                                                cantidadBigDecimal = BigDecimal.valueOf(celdaCantidad.getNumericCellValue());
                                                System.out.println("cant " + cantidadBigDecimal);
                                            }
                                            if (siPagoBool) {
                                                if (celdaPrecio != null) {
                                                    celdaPrecio.setCellType(Cell.CELL_TYPE_NUMERIC);
                                                    pagoDouble = celdaPrecio.getNumericCellValue();
                                                    System.out.println("sale " + pagoDouble);
                                                    preciosFinales.add(BigDecimal.valueOf(pagoDouble).setScale(2));
                                                    Pair par = new Pair(articulo, cantidadBigDecimal); //creo el par
                                                    parDeProductos.add(par); //meto el par a la lista
                                                    monto = monto.add(cantidadBigDecimal.multiply(BigDecimal.valueOf(pagoDouble).setScale(2)));
                                                }
                                            } else {
                                                Pair par = new Pair(articulo, cantidadBigDecimal); //creo el par
                                                parDeProductos.add(par); //meto el par a la lista
                                            }
                                        }
                                    }
                                }
                                java.sql.Date sqlFecha = new java.sql.Date(fechaDate.getTime());
                                venta.set("fecha", sqlFecha);

                                if (siPagoBool) {
                                    venta.setPreciosFinales(preciosFinales);
                                    Iterator<BigDecimal> it = preciosFinales.iterator();
                                    while (it.hasNext()) {
                                        System.out.println((BigDecimal) it.next());
                                    }
                                    Iterator<Pair> ita = parDeProductos.iterator();
                                    while (ita.hasNext()) {
                                        Pair par = ita.next();
                                        System.out.println(par.second());
                                        Articulo ar = (Articulo) par.first();
                                        System.out.println(ar.getString("codigo"));
                                    }
                                    venta.setProductos(parDeProductos);
                                    venta.set("pago", true);
                                    venta.set("monto", monto);
                                } else {
                                    venta.set("pago", false);
                                    venta.setProductos(parDeProductos);
                                }
                                abrirBase();
                                System.out.println(sqlFecha);

                                System.out.println(abmVenta.alta(venta));
                                cerrarBase();
                            }
                        }
                    }
                }

                return null;
            }

            protected void done() {
                setProgress(100);
                JOptionPane.showMessageDialog(importarGui, "Importación de venta finalizada");
                importarGui.getProgreso().setIndeterminate(false);
                importando = false;
            }
        };
        worker.execute();
    }

    ;
    
    
    
    
    private void importarCompra() throws FileNotFoundException, IOException {
        importarGui.getProgreso().setIndeterminate(true);
        final javax.swing.SwingWorker worker = new javax.swing.SwingWorker() {
            @Override
            protected Void doInBackground() throws Exception {
                if (importarGui.getSelectorArchivos().getSelectedFile().getName().contains("xlsx")) {//Archivos con extensión xlsx
                    importando = true;
                    XSSFWorkbook Libro_trabajo = new XSSFWorkbook(importarGui.getSelectorArchivos().getSelectedFile().getAbsolutePath());
                    XSSFSheet hoja = Libro_trabajo.getSheet("IMPORTARHOJA");
                    if (hoja == null) {
                        JOptionPane.showMessageDialog(null, "No se encontró la hoja IMPORTARHOJA, renombrela e intente nuevamente");
                    } else {
                        Iterator iterarFilas = hoja.rowIterator();
                        XSSFCell celdaProveedor;
                        XSSFCell celdaFecha;
                        XSSFCell celdaSiPago;
                        XSSFCell celdaCodigo;
                        XSSFCell celdaCantidad;
                        XSSFCell celdaPrecio;
                        Date fechaDate = Calendar.getInstance().getTime();
                        String siPago;
                        boolean siPagoBool = true;
                        String codigoString;
                        BigDecimal cantidadBigDecimal;
                        double pagoDouble;
                        BigDecimal monto = new BigDecimal(0);
                        BigDecimal pagoBig;
                        pagoBig = new BigDecimal(0);
                        abrirBase();
                        agregados = 0;
                        modificados = 0;
                        System.out.println(1);
                        XSSFRow primerFila = (XSSFRow) iterarFilas.next();
                        celdaProveedor = (XSSFCell) primerFila.getCell(1);
                        celdaFecha = (XSSFCell) primerFila.getCell(3);
                        compra = new Compra();
                        articulo = new Articulo();
                        prov = new Proveedor();
                        if (celdaProveedor != null) {
                            System.out.println(3);
                            if (!celdaProveedor.toString().isEmpty()) {
                                System.out.println(4);
                                celdaProveedor.setCellType(Cell.CELL_TYPE_STRING);
                                String nombre = celdaProveedor.getStringCellValue();
                                if (celdaFecha != null) {
                                    System.out.println(5);
                                    fechaDate = celdaFecha.getDateCellValue();
                                }
                                XSSFRow segundaFila = (XSSFRow) iterarFilas.next();
                                celdaSiPago = (XSSFCell) segundaFila.getCell(1);
                                if (celdaSiPago != null) {
                                    System.out.println(6);
                                    celdaSiPago.setCellType(Cell.CELL_TYPE_STRING);
                                    siPago = celdaSiPago.getStringCellValue();
                                    siPago = siPago.toLowerCase();
                                    if (siPago.compareTo("si") == 0) {
                                        siPagoBool = true;
                                    } else {
                                        siPagoBool = false;
                                    }
                                }
                                iterarFilas.next();
                                abrirBase();
                                System.out.println(7 + nombre);
                                LinkedList<Pair> parDeProductos = new LinkedList();
                                prov = Proveedor.findFirst("nombre = ?", nombre);
                                System.out.println(8 + nombre + " " + fechaDate);
                                compra.set("proveedor_id", prov.getId());
                                cerrarBase();
                                while (iterarFilas.hasNext() && importando) {
                                    XSSFRow row = (XSSFRow) iterarFilas.next();
                                    celdaCodigo = (XSSFCell) row.getCell(0);
                                    celdaCantidad = (XSSFCell) row.getCell(1);
                                    celdaPrecio = (XSSFCell) row.getCell(2);
                                    if (celdaCodigo != null) {
                                        if (!celdaCodigo.toString().isEmpty()) {
                                            celdaCodigo.setCellType(Cell.CELL_TYPE_STRING);
                                            codigoString = celdaCodigo.toString();
                                            System.out.println("cod " + codigoString);
                                            articulo.set("codigo", codigoString);
                                            abrirBase();
                                            if (abmArticulo.findArticulo(articulo)) {
                                                System.out.println("encontre el articulo");
                                                articulo = abmArticulo.getProducto(articulo);

                                            }
                                            cerrarBase();
                                            cantidadBigDecimal = new BigDecimal(0);
                                            if (celdaCantidad != null) {
                                                celdaCantidad.setCellType(Cell.CELL_TYPE_NUMERIC);
                                                cantidadBigDecimal = BigDecimal.valueOf(celdaCantidad.getNumericCellValue());
                                                System.out.println("cant " + cantidadBigDecimal);
                                            }

                                            if (celdaPrecio != null) {
                                                celdaPrecio.setCellType(Cell.CELL_TYPE_NUMERIC);
                                                pagoDouble = celdaPrecio.getNumericCellValue();
                                                articulo.set("precio_compra", BigDecimal.valueOf(pagoDouble));
                                                Pair par = new Pair(articulo, cantidadBigDecimal); //creo el par
                                                parDeProductos.add(par); //meto el par a la lista
                                                System.out.println(cantidadBigDecimal + " " + pagoDouble + "monto " + monto);
                                                monto = monto.add(cantidadBigDecimal.multiply(BigDecimal.valueOf(pagoDouble).setScale(2)));
                                                System.out.println("monto " + monto);
                                            }

                                        }
                                    }
                                }
                                java.sql.Date sqlFecha = new java.sql.Date(fechaDate.getTime());
                                compra.set("fecha", sqlFecha);
                                compra.setProductos(parDeProductos);
                                if (siPagoBool) {
                                    compra.set("pago", true);
                                } else {
                                    compra.set("pago", false);
                                }
                                abrirBase();
                                compra.set("monto", monto);
                                if (abmCompra.alta(compra)) {
                                    System.out.println("se compró");
                                    abrirBase();
                                    if (siPagoBool) {
                                        Pago pago = Pago.create("fecha", sqlFecha, "monto", monto);
                                        pago.saveIt();
                                        prov.add(pago);
                                    } else {
                                        BigDecimal cuentaCorriente = prov.getBigDecimal("cuenta_corriente").subtract(compra.getBigDecimal("monto"));
                                        prov.set("cuenta_corriente", cuentaCorriente);
                                    }

                                }
                                cerrarBase();

                            }
                        }
                    }
                } else if (importarGui.getSelectorArchivos().getSelectedFile().getName().contains("xls")) {
                    importando = true;
                    workbook = new HSSFWorkbook(new FileInputStream(importarGui.getSelectorArchivos().getSelectedFile().getAbsolutePath()));
                    sheet = workbook.getSheet("IMPORTARHOJA");
                    if (sheet == null) {
                        JOptionPane.showMessageDialog(null, "No se encontró la hoja IMPORTARHOJA, renombrela e intente nuevamente");
                    } else {
                        Iterator<Row> iterarFilas = sheet.rowIterator();
                        Cell celdaProveedor;
                        Cell celdaFecha;
                        Cell celdaSiPago;
                        Cell celdaCodigo;
                        Cell celdaCantidad;
                        Cell celdaPrecio;
                        Date fechaDate = Calendar.getInstance().getTime();
                        String siPago;
                        boolean siPagoBool = true;
                        String codigoString;
                        BigDecimal cantidadBigDecimal;
                        double pagoDouble;
                        BigDecimal monto = new BigDecimal(0);
                        abrirBase();
                        agregados = 0;
                        modificados = 0;
                        System.out.println(1);
                        Row primerFila = iterarFilas.next();
                        celdaProveedor = primerFila.getCell(1);
                        celdaFecha = primerFila.getCell(3);
                        compra = new Compra();
                        articulo = new Articulo();
                        prov = new Proveedor();
                        if (celdaProveedor != null) {
                            System.out.println(3);
                            if (!celdaProveedor.toString().isEmpty()) {
                                System.out.println(4);
                                celdaProveedor.setCellType(Cell.CELL_TYPE_STRING);
                                String nombre = celdaProveedor.getStringCellValue();
                                if (celdaFecha != null) {
                                    System.out.println(5);
                                    fechaDate = celdaFecha.getDateCellValue();
                                }
                                Row segundaFila = iterarFilas.next();
                                celdaSiPago = segundaFila.getCell(1);
                                if (celdaSiPago != null) {
                                    System.out.println(6);
                                    celdaSiPago.setCellType(Cell.CELL_TYPE_STRING);
                                    siPago = celdaSiPago.getStringCellValue();
                                    siPago = siPago.toLowerCase();
                                    if (siPago.compareTo("si") == 0) {
                                        siPagoBool = true;
                                    } else {
                                        siPagoBool = false;
                                    }
                                }
                                iterarFilas.next();
                                abrirBase();
                                System.out.println(7 + nombre);
                                LinkedList<Pair> parDeProductos = new LinkedList();
                                prov = Proveedor.findFirst("nombre = ?", nombre);
                                System.out.println(8 + nombre + " " + fechaDate);
                                compra.set("proveedor_id", prov.getId());
                                cerrarBase();
                                while (iterarFilas.hasNext() && importando) {
                                    Row row = iterarFilas.next();
                                    celdaCodigo = row.getCell(0);
                                    celdaCantidad = row.getCell(1);
                                    celdaPrecio = row.getCell(2);
                                    if (celdaCodigo != null) {
                                        if (!celdaCodigo.toString().isEmpty()) {
                                            celdaCodigo.setCellType(Cell.CELL_TYPE_STRING);
                                            codigoString = celdaCodigo.toString();
                                            System.out.println("cod " + codigoString);
                                            articulo.set("codigo", codigoString);
                                            abrirBase();
                                            if (abmArticulo.findArticulo(articulo)) {
                                                System.out.println("encontre el articulo");
                                                articulo = abmArticulo.getProducto(articulo);

                                            }
                                            cerrarBase();
                                            cantidadBigDecimal = new BigDecimal(0);
                                            if (celdaCantidad != null) {
                                                celdaCantidad.setCellType(Cell.CELL_TYPE_NUMERIC);
                                                cantidadBigDecimal = BigDecimal.valueOf(celdaCantidad.getNumericCellValue());
                                                System.out.println("cant " + cantidadBigDecimal);
                                            }

                                            if (celdaPrecio != null) {
                                                celdaPrecio.setCellType(Cell.CELL_TYPE_NUMERIC);
                                                pagoDouble = celdaPrecio.getNumericCellValue();
                                                articulo.set("precio_compra", BigDecimal.valueOf(pagoDouble));
                                                Pair par = new Pair(articulo, cantidadBigDecimal); //creo el par
                                                parDeProductos.add(par); //meto el par a la lista
                                                System.out.println(cantidadBigDecimal + " " + pagoDouble + "monto " + monto);
                                                monto = monto.add(cantidadBigDecimal.multiply(BigDecimal.valueOf(pagoDouble).setScale(2)));
                                                System.out.println("monto " + monto);
                                            }

                                        }
                                    }
                                }
                                java.sql.Date sqlFecha = new java.sql.Date(fechaDate.getTime());
                                compra.set("fecha", sqlFecha);
                                compra.setProductos(parDeProductos);
                                if (siPagoBool) {
                                    compra.set("pago", true);
                                } else {
                                    compra.set("pago", false);
                                }
                                abrirBase();
                                compra.set("monto", monto);
                                if (abmCompra.alta(compra)) {
                                    System.out.println("se compró");
                                    abrirBase();
                                    if (siPagoBool) {
                                        Pago pago = Pago.create("fecha", sqlFecha, "monto", monto);
                                        pago.saveIt();
                                        prov.add(pago);
                                    } else {
                                        BigDecimal cuentaCorriente = prov.getBigDecimal("cuenta_corriente").subtract(compra.getBigDecimal("monto"));
                                        prov.set("cuenta_corriente", cuentaCorriente);
                                    }

                                }
                                cerrarBase();

                            }
                        }
                    }
                }




                return null;
            }

            protected void done() {
                setProgress(100);
                JOptionPane.showMessageDialog(importarGui, "Importación de compra finalizada");
                importarGui.getProgreso().setIndeterminate(false);
                importando = false;
            }
        };
        worker.execute();
    }
;
}
