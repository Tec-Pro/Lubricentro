/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author nico
 */
public class AplicacionGui extends javax.swing.JFrame {

    /**
     * Creates new form AplicacionGui
     */
    public AplicacionGui() {
        initComponents();
    }

    public JDesktopPane getContenedor() {
        return contenedor;
    }

    public JButton getArticulos() {
        return articulos;
    }
    
    public void setActionListener(ActionListener lis){
        this.getArticulos().addActionListener(lis);
        this.proveedores.addActionListener(lis);
        this.importar.addActionListener(lis);
        this.clientes.addActionListener(lis);
        this.cargarBackup.addActionListener(lis);
        this.crearBackup.addActionListener(lis);
        this.cambiosEmail.addActionListener(lis);
        this.modificarUsuario.addActionListener(lis);
        this.cerrarSesion.addActionListener(lis);
        this.enviar.addActionListener(lis);
        this.registrarCompra.addActionListener(lis);
        this.registrarVenta.addActionListener(lis);
    }

    public JMenuItem getCerrarSesion() {
        return cerrarSesion;
    }

    public JMenuItem getEnviar() {
        return enviar;
    }

    
    public JMenuItem getModificarUsuario() {
        return modificarUsuario;
    }

    public JButton getProveedores() {
        return proveedores;
    }

    public JButton getImportar() {
        return importar;
    }

    public JButton getClientes() {
        return clientes;
    }

    public JMenuItem getCargarBackup() {
        return cargarBackup;
    }

    public JMenuItem getCrearBackup() {
        return crearBackup;
    }

    public JMenuItem getCambiosEmail() {
        return cambiosEmail;
    }

    public JButton getRegistrarCompra() {
        return registrarCompra;
    }

    public JButton getRegistrarVenta() {
        return registrarVenta;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contenedor = new javax.swing.JDesktopPane();
        panelBotones = new javax.swing.JPanel();
        articulos = new javax.swing.JButton();
        proveedores = new javax.swing.JButton();
        importar = new javax.swing.JButton();
        clientes = new javax.swing.JButton();
        registrarCompra = new javax.swing.JButton();
        registrarVenta = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        cambiosEmail = new javax.swing.JMenuItem();
        modificarUsuario = new javax.swing.JMenuItem();
        cerrarSesion = new javax.swing.JMenuItem();
        salir = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        crearBackup = new javax.swing.JMenuItem();
        cargarBackup = new javax.swing.JMenuItem();
        enviar = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        tecPro = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Lubricentro");
        setIconImage(new ImageIcon(getClass().getResource("/interfaz/Icons/icono.png")).getImage());

        panelBotones.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        articulos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/productos.png"))); // NOI18N
        articulos.setToolTipText("Gestión de artículos");

        proveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/proveedor.png"))); // NOI18N
        proveedores.setToolTipText("Gestión de proveedores");

        importar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/importar.png"))); // NOI18N
        importar.setToolTipText("Importar datos desde Excel");

        clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/clients.png"))); // NOI18N
        clientes.setToolTipText("Gestión de clientes");

        registrarCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/compras.png"))); // NOI18N
        registrarCompra.setToolTipText("Registrar Compra");

        registrarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/ventas.png"))); // NOI18N
        registrarVenta.setToolTipText("Registrar Venta");

        javax.swing.GroupLayout panelBotonesLayout = new javax.swing.GroupLayout(panelBotones);
        panelBotones.setLayout(panelBotonesLayout);
        panelBotonesLayout.setHorizontalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesLayout.createSequentialGroup()
                .addComponent(clientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
            .addGroup(panelBotonesLayout.createSequentialGroup()
                .addGroup(panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(articulos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(proveedores, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(importar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(registrarCompra, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(registrarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelBotonesLayout.setVerticalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesLayout.createSequentialGroup()
                .addComponent(articulos, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(proveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(importar, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(registrarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(registrarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jMenu1.setText("Archivo");

        cambiosEmail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/mail.png"))); // NOI18N
        cambiosEmail.setText("Cambiar correo");
        jMenu1.add(cambiosEmail);

        modificarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/user.png"))); // NOI18N
        modificarUsuario.setText("Modificar datos del usuario");
        jMenu1.add(modificarUsuario);

        cerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/cerrar.png"))); // NOI18N
        cerrarSesion.setText("Cerrar sesión");
        jMenu1.add(cerrarSesion);

        salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/apagar.png"))); // NOI18N
        salir.setText("Salir");
        salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirActionPerformed(evt);
            }
        });
        jMenu1.add(salir);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Backup");

        crearBackup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/guardarBack.png"))); // NOI18N
        crearBackup.setText("Crear Backup");
        jMenu2.add(crearBackup);

        cargarBackup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/subirBack.png"))); // NOI18N
        cargarBackup.setText("Cargar Backup");
        jMenu2.add(cargarBackup);

        enviar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/enviarEmail.png"))); // NOI18N
        enviar.setText("Enviar ");
        jMenu2.add(enviar);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Acerca de");

        tecPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/acerca.png"))); // NOI18N
        tecPro.setText("Tec-Pro Software");
        tecPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tecProActionPerformed(evt);
            }
        });
        jMenu3.add(tecPro);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(contenedor, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contenedor)
            .addComponent(panelBotones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirActionPerformed
        int ret=JOptionPane.showConfirmDialog(this, "¿Desea salir de la aplicación?","Cerrar aplicación", JOptionPane.YES_NO_OPTION);
        if(ret== JOptionPane.YES_OPTION)
            System.exit(0);
        
    }//GEN-LAST:event_salirActionPerformed

    private void tecProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tecProActionPerformed
        AcercaDe acercaDe= new AcercaDe(this, true);
        acercaDe.setLocationRelativeTo(this);
        acercaDe.setVisible(true);
    }//GEN-LAST:event_tecProActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton articulos;
    private javax.swing.JMenuItem cambiosEmail;
    private javax.swing.JMenuItem cargarBackup;
    private javax.swing.JMenuItem cerrarSesion;
    private javax.swing.JButton clientes;
    private javax.swing.JDesktopPane contenedor;
    private javax.swing.JMenuItem crearBackup;
    private javax.swing.JMenuItem enviar;
    private javax.swing.JButton importar;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem modificarUsuario;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JButton proveedores;
    private javax.swing.JButton registrarCompra;
    private javax.swing.JButton registrarVenta;
    private javax.swing.JMenuItem salir;
    private javax.swing.JMenuItem tecPro;
    // End of variables declaration//GEN-END:variables



}
