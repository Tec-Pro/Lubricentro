/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import abm.ABMArticulo;
import abm.ABMCliente;
import abm.ABMProveedor;
import interfaz.ImportarExcelGui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelos.Articulo;
import modelos.Cliente;
import modelos.Proveedor;
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

    public controladorImportarGui(ImportarExcelGui importarGui) {
        this.importarGui = importarGui;
        this.importarGui.setActionListener(this);
        abmProveedor = new ABMProveedor();
        abmCliente = new ABMCliente();
        abmArticulo = new ABMArticulo();
        prov = new Proveedor();
        cliente = new Cliente();
        articulo = new Articulo();
        importando = false;
        abrirBase();
        List<Proveedor> proveedores= Proveedor.findAll();
        Iterator<Proveedor> it= proveedores.iterator();
        while(it.hasNext()){
            prov= it.next();
            importarGui.getProveedor().addItem(prov.get("nombre"));
        }
        cerrarBase();
        importarGui.getProveedor().addItem("");
        importarGui.getProveedor().setSelectedItem("");
        importarGui.getCategoria().addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e) {
        habilitarProveedores();
    }
});
    }
    
    private void habilitarProveedores(){
        if (!importarGui.getCategoria().getSelectedItem().equals("Artículos")){
            importarGui.getProveedor().setEnabled(false);
        }
        else{
            importarGui.getProveedor().removeAllItems();
            abrirBase();
        List<Proveedor> proveedores= Proveedor.findAll();
        Iterator<Proveedor> it= proveedores.iterator();
        while(it.hasNext()){
            prov= it.next();
            importarGui.getProveedor().addItem(prov.get("nombre"));
        }
        cerrarBase();
        importarGui.getProveedor().addItem("");
        importarGui.getProveedor().setSelectedItem("");
        importarGui.getProveedor().setEnabled(true);
        }
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
        }
    }

    private void importarExcelProveedores() throws FileNotFoundException, IOException {
        importarGui.getProgreso().setIndeterminate(true);
        final javax.swing.SwingWorker worker = new javax.swing.SwingWorker() {
            @Override
            protected Void doInBackground() throws Exception {
                if (importarGui.getSelectorArchivos().getSelectedFile().getName().contains("xlsx")) {//Archivos con extensión xlsx
                    importando=true;
                    XSSFWorkbook Libro_trabajo = new XSSFWorkbook(importarGui.getSelectorArchivos().getSelectedFile().getAbsolutePath());
                    XSSFSheet hoja = Libro_trabajo.getSheet("IMPORTARHOJA");
                    Iterator iterarFilas = hoja.rowIterator();
                    XSSFCell celdaNombre;
                    XSSFCell celdaTelefono;
                    String telefonoString;
                    abrirBase();
                    agregados = 0;
                    modificados = 0;
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
                } else if (importarGui.getSelectorArchivos().getSelectedFile().getName().contains("xls")) {
                    importando=true;
                    workbook = new HSSFWorkbook(new FileInputStream(importarGui.getSelectorArchivos().getSelectedFile().getAbsolutePath()));
                    sheet = workbook.getSheet("IMPORTARHOJA");
                    Iterator<Row> iterarFilas = sheet.iterator();
                    Cell celdaNombre;
                    Cell celdaTelefono;
                    String telefonoString;
                    abrirBase();
                    agregados = 0;
                    modificados = 0;
                    while (iterarFilas.hasNext()&&importando) {
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
                return null;
            }

            protected void done() {
                setProgress(100);
                JOptionPane.showMessageDialog(importarGui, "Importación de registros terminada, se han agregado " + agregados + " Proveedores");
                importarGui.getProgreso().setIndeterminate(false);
                importando=false;
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
                    importando=true;
                    XSSFWorkbook Libro_trabajo = new XSSFWorkbook(importarGui.getSelectorArchivos().getSelectedFile().getAbsolutePath());
                    XSSFSheet hoja = Libro_trabajo.getSheet("IMPORTARHOJA");
                    Iterator iterarFilas = hoja.rowIterator();
                    XSSFCell celdaNombre;
                    XSSFCell celdaTelefono;
                    XSSFCell celdaCelular;
                    String telefonoString;
                    String telefonoCelularString;
                    abrirBase();
                    agregados = 0;
                    modificados = 0;
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
                } else if (importarGui.getSelectorArchivos().getSelectedFile().getName().contains("xls")) {
                    importando=true;
                    workbook = new HSSFWorkbook(new FileInputStream(importarGui.getSelectorArchivos().getSelectedFile().getAbsolutePath()));
                    sheet = workbook.getSheet("IMPORTARHOJA");
                    Iterator<Row> iterarFilas = sheet.iterator();
                    Cell celdaNombre;
                    Cell celdaTelefono;
                    Cell celdaCelular;
                    String telefonoString;
                    String telefonoCelularString;
                    abrirBase();
                    agregados = 0;
                    modificados = 0;
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
                return null;
            }

            protected void done() {
                setProgress(100);
                JOptionPane.showMessageDialog(importarGui, "Importación de registros terminada, se han agregado " + agregados + " clientes");
                importarGui.getProgreso().setIndeterminate(false);
                importando=false;
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
                    importando=true;
                    XSSFWorkbook Libro_trabajo = new XSSFWorkbook(importarGui.getSelectorArchivos().getSelectedFile().getAbsolutePath());
                    XSSFSheet hoja = Libro_trabajo.getSheet("IMPORTARHOJA");
                    Iterator iterarFilas = hoja.rowIterator();
                    XSSFCell celdaCodigo;
                    XSSFCell celdaDescripcion;
                    XSSFCell celdaMarca;
                    XSSFCell celdaPrecioCompra;
                    XSSFCell celdaEquivalenciaFram;
                    String codigoString;
                    String descripcionString;
                    String marcaString;
                    String precioString;
                    String equivalenciaString;
                    double precioFloat;
                    BigDecimal precioCompraBig;
                    BigDecimal precioVenta;
                    BigDecimal porcentaje;
                    abrirBase();
                    agregados = 0;
                    modificados = 0;
                    while (iterarFilas.hasNext() && importando) {
                        XSSFRow row = (XSSFRow) iterarFilas.next();
                        celdaCodigo = (XSSFCell) row.getCell(0);
                        celdaDescripcion = (XSSFCell) row.getCell(1);
                        celdaMarca = (XSSFCell) row.getCell(2);
                        celdaPrecioCompra = (XSSFCell) row.getCell(3);
                        celdaEquivalenciaFram = (XSSFCell) row.getCell(4);
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
                                    porcentaje = new BigDecimal(5);
                                    precioVenta = precioCompraBig.multiply(porcentaje).setScale(2, RoundingMode.CEILING);

                                }
                                if (celdaEquivalenciaFram != null) {
                                    celdaEquivalenciaFram.setCellType(Cell.CELL_TYPE_STRING);
                                    equivalenciaString = celdaEquivalenciaFram.toString();
                                }
                                articulo.set("codigo", codigoString, "descripcion", descripcionString, "marca", marcaString, "precio_compra", precioCompraBig, "precio_venta", precioVenta, "equivalencia_fram", equivalenciaString, "stock", 0, "stock_minimo", 0);
                                if (abmArticulo.findArticulo(articulo)) {
                                    abmArticulo.modificar(articulo);
                                    if(!importarGui.getProveedor().getSelectedItem().equals("")){
                                        prov.set("nombre", importarGui.getProveedor().getSelectedItem());
                                        prov= abmProveedor.getProveedor(prov);
                                        prov.add(articulo);
                                    }
                                    else{
                                        articulo.set("proveedor_id",null);
                                    }
                                    System.out.println("modificando articulo");
                                } else {
                                    abmArticulo.alta(articulo);
                                    if(!importarGui.getProveedor().getSelectedItem().equals("")){
                                        prov.set("nombre", importarGui.getProveedor().getSelectedItem());
                                        prov= abmProveedor.getProveedor(prov);
                                        prov.add(articulo);
                                    }
                                    System.out.println("nuevo articulo");
                                    agregados++;
                                }
                            }
                        }
                    }
                    cerrarBase();
                } else if (importarGui.getSelectorArchivos().getSelectedFile().getName().contains("xls")) {
                    importando=true;
                    workbook = new HSSFWorkbook(new FileInputStream(importarGui.getSelectorArchivos().getSelectedFile().getAbsolutePath()));
                    sheet = workbook.getSheet("IMPORTARHOJA");
                    Iterator<Row> iterarFilas = sheet.iterator();
                    Cell celdaCodigo;
                    Cell celdaDescripcion;
                    Cell celdaMarca;
                    Cell celdaPrecioCompra;
                    Cell celdaEquivalenciaFram;
                    String codigoString;
                    String descripcionString;
                    String marcaString;
                    String precioString;
                    String equivalenciaString;
                    double precioFloat;
                    BigDecimal precioCompraBig;
                    BigDecimal precioVenta;
                    BigDecimal porcentaje;
                    abrirBase();
                    agregados = 0;
                    modificados = 0;
                    while (iterarFilas.hasNext() && importando) {
                        Row row = iterarFilas.next();
                        celdaCodigo = row.getCell(0);
                        celdaDescripcion = row.getCell(1);
                        celdaMarca = row.getCell(2);
                        celdaPrecioCompra = row.getCell(3);
                        celdaEquivalenciaFram = row.getCell(4);
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
                                    porcentaje = new BigDecimal(5);
                                    precioVenta = precioCompraBig.multiply(porcentaje).setScale(2, RoundingMode.CEILING);

                                }
                                if (celdaEquivalenciaFram != null) {
                                    celdaEquivalenciaFram.setCellType(Cell.CELL_TYPE_STRING);
                                    equivalenciaString = celdaEquivalenciaFram.getStringCellValue();
                                }
                                articulo.set("codigo", codigoString, "descripcion", descripcionString, "marca", marcaString, "precio_compra", precioCompraBig, "precio_venta", precioVenta, "equivalencia_fram", equivalenciaString, "stock", 0, "stock_minimo", 0);
                                if (abmArticulo.findArticulo(articulo)) {
                                    abmArticulo.modificar(articulo);
                                    if(!importarGui.getProveedor().getSelectedItem().equals("")){
                                        prov.set("nombre", importarGui.getProveedor().getSelectedItem());
                                        prov= abmProveedor.getProveedor(prov);
                                        prov.add(articulo);
                                    }
                                    else{
                                        articulo.set("proveedor_id",null);
                                    }
                                    System.out.println("modificando articulo");
                                } else {
                                    abmArticulo.alta(articulo);
                                    if(!importarGui.getProveedor().getSelectedItem().equals("")){
                                        prov.set("nombre", importarGui.getProveedor().getSelectedItem());
                                        prov= abmProveedor.getProveedor(prov);
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
                return null;
            }

            protected void done() {
                setProgress(100);
                JOptionPane.showMessageDialog(importarGui, "Importación de registros terminada, se han agregado " + agregados + "Articulos");
                importarGui.getProgreso().setIndeterminate(false);
                importando=false;
            }
        };
        worker.execute();
    }
;
}
