/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import interfaz.AplicacionGui;
import interfaz.ArticuloGui;
import interfaz.CambiarUsuarioGui;
import interfaz.CargarDatosEmail;
import interfaz.ClienteGui;
import interfaz.CompraGui;
import interfaz.EnviarManualGui;
import interfaz.ImportarExcelGui;
import interfaz.ProveedorGui;
import interfaz.VentaGui;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author nico
 */
public class ControladorApliacion implements ActionListener {

    private AplicacionGui aplicacionGui;
    private ArticuloGui articuloGui;
    private ControladorArticulo controladorArticulo;
    private ControladorProveedor controladorProveedor;
    private controladorImportarGui controladorImportarGui;
    private ProveedorGui proveedorGui;
    private ImportarExcelGui importarGui;
    private ClienteGui clienteGui;
    private ControladorCliente controladorCliente;
    private CargarDatosEmail emailGui;
    private ControladorLogin log;
    private File archivoBackup;
    private int selecEnviarBack = 0;
    private Modulo modulo;
    private EnvioEmailControlador envioEmailControlador;
    private ControladorCompra controladorCompra;
    private ControladorVenta controladorVenta;
    private CompraGui compraGui;
    private VentaGui ventaGui;

    public ControladorApliacion() throws JRException, ClassNotFoundException, SQLException {
        JFrame.setDefaultLookAndFeelDecorated(true); //Le agrego un tema lindo al programa
        try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        aplicacionGui = new AplicacionGui();
        log = new ControladorLogin(aplicacionGui);
        log.run();
        aplicacionGui.setCursor(Cursor.WAIT_CURSOR);
        aplicacionGui.setActionListener(this);
        aplicacionGui.setExtendedState(JFrame.MAXIMIZED_BOTH);
        articuloGui = new ArticuloGui();
        proveedorGui = new ProveedorGui();
        clienteGui = new ClienteGui();
        compraGui = new CompraGui();
        ventaGui = new VentaGui();
        controladorCompra = new ControladorCompra(compraGui, aplicacionGui);
        controladorVenta = new ControladorVenta(ventaGui, aplicacionGui);
        controladorProveedor = new ControladorProveedor(proveedorGui, aplicacionGui, articuloGui, compraGui);
        controladorArticulo = new ControladorArticulo(articuloGui);
        controladorCliente = new ControladorCliente(clienteGui, aplicacionGui, ventaGui);
        importarGui = new ImportarExcelGui();
        controladorImportarGui = new controladorImportarGui(importarGui);
        aplicacionGui.getContenedor().add(proveedorGui);
        aplicacionGui.getContenedor().add(articuloGui);
        aplicacionGui.getContenedor().add(importarGui);
        aplicacionGui.getContenedor().add(clienteGui);
        aplicacionGui.getContenedor().add(compraGui);
        aplicacionGui.getContenedor().add(ventaGui);
        aplicacionGui.setCursor(Cursor.DEFAULT_CURSOR);

    }

    public static void main(String[] args) throws InterruptedException, ClassNotFoundException, SQLException, JRException {
        ControladorApliacion controladorAplicacion = new ControladorApliacion();

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == aplicacionGui.getArticulos()) {
            controladorArticulo.cargarTodos();
            articuloGui.setVisible(true);
            articuloGui.toFront();
        }
        if (ae.getSource() == aplicacionGui.getProveedores()) {
            controladorProveedor.cargarTodos();
            proveedorGui.setVisible(true);
            proveedorGui.toFront();
        }
        if (ae.getSource() == aplicacionGui.getImportar()) {
            controladorImportarGui.cargarProveedores();
            importarGui.setVisible(true);
            importarGui.toFront();
        }
        if (ae.getSource() == aplicacionGui.getClientes()) {
            controladorCliente.cargarTodos();
            clienteGui.setVisible(true);
            clienteGui.toFront();
        }
        if (ae.getSource() == aplicacionGui.getCrearBackup()) {
            modulo = new Modulo();
            modulo.conectar();
            modulo.GuardarRutaBackup();
            modulo.CrearBackup();
            String dir = (new File(System.getProperty("user.dir")).getAbsolutePath());
            System.out.println(dir);
        }
        if (ae.getSource() == aplicacionGui.getCargarBackup()) {
            int confirmado = JOptionPane.showConfirmDialog(null, "¿Confirmas la restauración de la Base de Datos?");
            if (JOptionPane.OK_OPTION == confirmado) {
                modulo = new Modulo();
                modulo.conectarMySQL();
                modulo.AbrirRutaBackup();
                try {
                    modulo.RestaurarBackup();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ControladorApliacion.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ControladorApliacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            //final de restaurar backup//
        }
        if (aplicacionGui.getCambiosEmail() == ae.getSource()) {
            emailGui = new CargarDatosEmail(aplicacionGui, true);
            emailGui.setLocationRelativeTo(aplicacionGui);
            emailGui.setVisible(true);
        }

        if (aplicacionGui.getModificarUsuario() == ae.getSource()) {
            CambiarUsuarioGui cambiarUsuarioGui = new CambiarUsuarioGui(aplicacionGui, true);
            cambiarUsuarioGui.setLocationRelativeTo(aplicacionGui);
            cambiarUsuarioGui.setVisible(true);
        }
        if (aplicacionGui.getCerrarSesion() == ae.getSource()) {
            int ret = JOptionPane.showConfirmDialog(articuloGui, "¿Desea cerrar sesión?", null, JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.YES_OPTION) {
                log.getLog().getPass().setText("");
                aplicacionGui.setVisible(false);
                log.getLog().setVisible(true);
            }
        }
        if (aplicacionGui.getEnviar() == ae.getSource()) {

            JFileChooser chooser = new JFileChooser();
            chooser.setApproveButtonText("Enviar");
            chooser.addChoosableFileFilter(new Modulo.SQLFilter());
            chooser.showOpenDialog(null);
            if (chooser.getSelectedFile() != null) {
                archivoBackup = chooser.getSelectedFile();
                selecEnviarBack = 1;
            } else if (chooser.getSelectedFile() == null) {
                selecEnviarBack = 0;
            }
            if (selecEnviarBack == 1) {
                envioEmailControlador = new EnvioEmailControlador();
                EnviarManualGui enviarGui = new EnviarManualGui(aplicacionGui, true, archivoBackup.getAbsolutePath());
                enviarGui.setLocationRelativeTo(aplicacionGui);
                enviarGui.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "No se seleccionó ningun archivo!");
            }

        }
        if (ae.getSource() == aplicacionGui.getRegistrarCompra()) {
            controladorCompra.cargarTodos();
            compraGui.setVisible(true);
            compraGui.toFront();
        }
        if (ae.getSource() == aplicacionGui.getRegistrarVenta()) {
            controladorVenta.cargarTodos();
            ventaGui.setVisible(true);
            ventaGui.toFront();
        }
    }

    private static class SQLFilter extends javax.swing.filechooser.FileFilter {

        final static String sql = "sql";

        public SQLFilter() {
        }

        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            String s = f.getName();
            int i = s.lastIndexOf('.');

            if (i > 0 && i < s.length() - 1) {
                String extension = s.substring(i + 1).toLowerCase();
                if (sql.equals(extension)) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }

        @Override
        public String getDescription() {
            return "Archivos .sql";
        }
    }
}
