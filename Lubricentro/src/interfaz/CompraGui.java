/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author max
 */
public class CompraGui extends javax.swing.JPanel {

    private DefaultTableModel tablaArticulosDefault;//tabla default de los clientes
    private DefaultTableModel tablaCompraDefault;
    private DefaultTableModel tablaProveedoresDefault;

    /**
     * Creates new form AbmClienteGui
     */
    public CompraGui() {
        initComponents();
        tablaArticulosDefault = (DefaultTableModel) tablaArticulos.getModel();//conveirto la tabla
        tablaCompraDefault = (DefaultTableModel) tablaCompra.getModel();
        tablaProveedoresDefault = (DefaultTableModel) tablaProveedores.getModel();
        Calendar miCalendario = Calendar.getInstance();
        java.util.Date eldia = miCalendario.getTime();
        int diaHoy = miCalendario.get(Calendar.DAY_OF_MONTH);
        int mes=miCalendario.get(Calendar.MONTH);
        int anio =miCalendario.get(Calendar.YEAR);
        calendarioCompra.setDate(Date.valueOf(anio+"-"+(mes+1)+"-"+diaHoy));
    }

    /**
     * Seteo el actionListener para los botones articulosALaCompra,
     * proveedorALaCompra, compraNueva, imprimir, realizarCompra,
     * borrarArticulosSeleccionados, modificar
     *
     * @param
     * @return
     * @exception
     */
    public void setActionListener(ActionListener lis) {
        this.articulosALaCompra.addActionListener(lis);
        this.proveedorALaCompra.addActionListener(lis);
        this.compraNueva.addActionListener(lis);
        this.realizarCompra.addActionListener(lis);
        this.borrarArticulosSeleccionados.addActionListener(lis);
        this.modificar.addActionListener(lis);
    }

    /**
     * Retorno la tabla Articulos con tipo TableModelDefault para pdoer realizar
     * inserciones y eliminaciones de filas más facilmente
     *
     * @param
     * @return defaultTableModel
     * @exception
     */
    public DefaultTableModel getTablaArticulosDefault() {
        return tablaArticulosDefault;
    }

    public JComboBox getAbona() {
        return Abona;
    }

    
    
    /**
     * Retorno la tabla compra con tipo TableModelDefault para pdoer realizar
     * inserciones y eliminaciones de filas más facilmente
     *
     * @param
     * @return DefaultTableModel
     * @exception
     */
    public DefaultTableModel getTablaCompraDefault() {
        return tablaCompraDefault;
    }
    
    /**
     * Retorno la tabla proveedores con tipo TableModelDefault para pdoer realizar
     * inserciones y eliminaciones de filas más facilmente
     *
     * @param
     * @return DefaultTableModel
     * @exception
     */
    public DefaultTableModel getTablaProveedoresDefault() {
        return tablaProveedoresDefault;
    }

    /**
     * Retorno boton articulosALaCompra para enviar los articulos seleccionados
     * en la tabla articulos a la tabla compra
     *
     * @param
     * @return JButton
     * @exception
     */
    public JButton getArticulosALaCompra() {
        return articulosALaCompra;
    }

    /**
     * Retorno el campo busqeudaApellido para poder filtrar las busquedas dado
     * el cuil de un proveedor
     *
     * @param
     * @return JTextField
     * @exception
     */
    public JTextField getBusquedaCuil() {
        return busquedaCuil;
    }

    /**
     * Retorno boton borrarArticulosSeleccionados para borrar los articulos
     * seleccionados en la tabla compra
     *
     * @param
     * @return JButton
     * @exception
     */
    public JButton getBorrarArticulosSeleccionados() {
        return borrarArticulosSeleccionados;
    }

    /**
     * Retorno boton modificar para confirmar cambios realizados en una compra
     *
     * @param
     * @return JButton
     * @exception
     */
    public JButton getModificar() {
        return modificar;
    }

    /**
     * Retorno el campo busquedaNombreProveedor para poder filtrar las busquedas
     * dado el nombre de un proveedor
     *
     * @param
     * @return JTextField
     * @exception
     */
    public JTextField getNombreProveedor() {
        return busquedaNombreProveedor;
    }

    /**
     * Retorno boton proveedorALacompra para enviar el proveedor seleccionado al
     * campo proveedor en el panel de la compra
     *
     * @param
     * @return JButton
     * @exception
     */
    public JButton getClienteALaFactura() {
        return proveedorALaCompra;
    }

    /**
     * Retorno el campo del proveedor de la compra
     *
     * @param
     * @return JTextField
     * @exception
     */
    public JTextField getProveedorCompra() {
        return proveedorCompra;
    }

    /**
     * Retorno boton compraNueva en donde limpia los campos de la compra para
     * iniciar una nueva compra
     *
     * @param
     * @return JButton
     * @exception
     */
    public JButton getCompraNueva() {
        return compraNueva;
    }

    /**
     * Retorno la tabla proveedores con tipo JTable
     *
     * @param
     * @return JTable
     * @exception
     */
    public JTable getTablaProveedores() {
        return tablaProveedores;
    }

    /**
     * Retorno boton realizar compra para confirmar la compra, se debe 
     * guardar los datos en la base de datos
     *
     * @param
     * @return JButton
     * @exception
     */
    public JButton getRealizarCompra() {
        return realizarCompra;
    }

    /**
     * Retorno tabla articulos con tipo JTable
     *
     * @param
     * @return JTable
     * @exception
     */
    public JTable getTablaArticulos() {
        return tablaArticulos;
    }

    /**
     * Retorno tabla Compra con tipo JTable
     *
     * @param
     * @return JTable
     * @exception
     */
    public JTable getTablaCompra() {
        return tablaCompra;
    }

     public JTextField getCalendarioFacturaText(){
        return ((JTextField)calendarioCompra.getDateEditor().getUiComponent());
    }
     
     public void limpiarVentana(){
        tablaArticulos.clearSelection();
        tablaProveedores.clearSelection();
        tablaCompra.clearSelection();
        while(tablaCompra.getRowCount()>0){
            tablaCompraDefault.removeRow(0);
        }
        Calendar miCalendario = Calendar.getInstance();
        java.util.Date eldia = miCalendario.getTime();
        int diaHoy = miCalendario.get(Calendar.DAY_OF_MONTH);
        int mes=miCalendario.get(Calendar.MONTH);
        int anio =miCalendario.get(Calendar.YEAR);
        calendarioCompra.setDate(Date.valueOf(anio+"-"+(mes+1)+"-"+diaHoy));

    }
    /**
     * Retorno el campo totalCompra contiene el resultado final de
     * la compra
     * @param
     * @return JTextField
     * @exception
     */
    public JTextField getTotalCompra() {
        return totalCompra;
    }

    /**
     * Retorno el calendario que contiene la fecha de la compra
     *
     * @param
     * @return JDateChooser
     * @exception
     */
    public JDateChooser getCalendarioFactura() {
        return calendarioCompra;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        fondoImagen = new javax.swing.JPanel();
        panelTitulo = new org.edisoncor.gui.panel.PanelImage();
        titulo = new javax.swing.JLabel();
        panelClientesAarticulos = new javax.swing.JPanel();
        panelArticulos = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaArticulos = new javax.swing.JTable();
        panelProveedores = new javax.swing.JPanel();
        busquedaNombreProveedor = new javax.swing.JTextField();
        labelNombre = new javax.swing.JLabel();
        busquedaCuil = new javax.swing.JTextField();
        labelCuil = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaProveedores = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        articulosALaCompra = new javax.swing.JButton();
        proveedorALaCompra = new javax.swing.JButton();
        panelCompra = new javax.swing.JPanel();
        labelCliente = new javax.swing.JLabel();
        proveedorCompra = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablaCompra = new javax.swing.JTable();
        labelTotal = new javax.swing.JLabel();
        totalCompra = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        borrarArticulosSeleccionados = new javax.swing.JButton();
        calendarioCompra = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        Abona = new javax.swing.JComboBox();
        panelControlFactura = new javax.swing.JPanel();
        compraNueva = new javax.swing.JButton();
        realizarCompra = new javax.swing.JButton();
        modificar = new javax.swing.JButton();

        jLabel4.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        jLabel4.setText("Abona");

        setPreferredSize(new java.awt.Dimension(825, 448));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(822, 448));

        fondoImagen.setPreferredSize(new java.awt.Dimension(820, 400));

        panelTitulo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/fondoCliente2.jpg"))); // NOI18N
        panelTitulo.setPreferredSize(new java.awt.Dimension(329, 49));

        titulo.setFont(new java.awt.Font("Century Schoolbook L", 3, 24)); // NOI18N
        titulo.setText("COMPRA");
        panelTitulo.add(titulo);

        panelClientesAarticulos.setBorder(null);

        panelArticulos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Artículos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Schoolbook L", 3, 18))); // NOI18N

        tablaArticulos.setAutoCreateRowSorter(true);
        tablaArticulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod.", "Nombre", "Marca"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaArticulos.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jScrollPane6.setViewportView(tablaArticulos);

        javax.swing.GroupLayout panelArticulosLayout = new javax.swing.GroupLayout(panelArticulos);
        panelArticulos.setLayout(panelArticulosLayout);
        panelArticulosLayout.setHorizontalGroup(
            panelArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
        );
        panelArticulosLayout.setVerticalGroup(
            panelArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelArticulosLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
        );

        panelProveedores.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Proveedores", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Schoolbook L", 3, 18))); // NOI18N

        busquedaNombreProveedor.setToolTipText("Filtrar busqueda por ID");

        labelNombre.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        labelNombre.setText("Nombre");

        busquedaCuil.setToolTipText("Filtrar busqueda por apellido");

        labelCuil.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        labelCuil.setText("Cuil");

        tablaProveedores.setAutoCreateRowSorter(true);
        tablaProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod.", "Nombre", "Apellido"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaProveedores.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane7.setViewportView(tablaProveedores);

        javax.swing.GroupLayout panelProveedoresLayout = new javax.swing.GroupLayout(panelProveedores);
        panelProveedores.setLayout(panelProveedoresLayout);
        panelProveedoresLayout.setHorizontalGroup(
            panelProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProveedoresLayout.createSequentialGroup()
                .addGroup(panelProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelCuil)
                    .addComponent(labelNombre))
                .addGap(17, 17, 17)
                .addGroup(panelProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(busquedaCuil, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                    .addComponent(busquedaNombreProveedor)))
            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        panelProveedoresLayout.setVerticalGroup(
            panelProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProveedoresLayout.createSequentialGroup()
                .addGroup(panelProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(busquedaCuil, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelCuil))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(busquedaNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        jPanel1.setBorder(null);
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jPanel3.setBorder(null);
        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        articulosALaCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/enviar.png"))); // NOI18N
        articulosALaCompra.setToolTipText("Cargar articulos a la factura");

        proveedorALaCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/enviar.png"))); // NOI18N
        proveedorALaCompra.setToolTipText("Cargar Proveedor a la compra");

        javax.swing.GroupLayout panelClientesAarticulosLayout = new javax.swing.GroupLayout(panelClientesAarticulos);
        panelClientesAarticulos.setLayout(panelClientesAarticulosLayout);
        panelClientesAarticulosLayout.setHorizontalGroup(
            panelClientesAarticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClientesAarticulosLayout.createSequentialGroup()
                .addGroup(panelClientesAarticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelArticulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelProveedores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelClientesAarticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelClientesAarticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(articulosALaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proveedorALaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panelClientesAarticulosLayout.setVerticalGroup(
            panelClientesAarticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelClientesAarticulosLayout.createSequentialGroup()
                .addGroup(panelClientesAarticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelProveedores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelClientesAarticulosLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(proveedorALaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)))
                .addGroup(panelClientesAarticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelClientesAarticulosLayout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(articulosALaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelClientesAarticulosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelArticulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        panelCompra.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Compra", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Schoolbook L", 3, 18))); // NOI18N

        labelCliente.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        labelCliente.setText("Proveedor");

        proveedorCompra.setEditable(false);

        tablaCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Cantidad", "Articulo", "Precio", "importe"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.math.BigDecimal.class, java.math.BigDecimal.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaCompra.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jScrollPane8.setViewportView(tablaCompra);
        tablaCompra.getColumnModel().getColumn(0).setPreferredWidth(70);
        tablaCompra.getColumnModel().getColumn(0).setMaxWidth(70);
        tablaCompra.getColumnModel().getColumn(1).setPreferredWidth(75);
        tablaCompra.getColumnModel().getColumn(1).setMaxWidth(75);
        tablaCompra.getColumnModel().getColumn(3).setPreferredWidth(75);
        tablaCompra.getColumnModel().getColumn(3).setMaxWidth(75);
        tablaCompra.getColumnModel().getColumn(4).setPreferredWidth(75);
        tablaCompra.getColumnModel().getColumn(4).setMaxWidth(75);

        labelTotal.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        labelTotal.setText("Total");

        totalCompra.setEditable(false);

        jLabel3.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        jLabel3.setText("Fecha");

        borrarArticulosSeleccionados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/borrarSeleccionado.png"))); // NOI18N
        borrarArticulosSeleccionados.setText("Borrar articulos seleccionados");
        borrarArticulosSeleccionados.setToolTipText("Borrar articulos seleccionados en la factura");
        borrarArticulosSeleccionados.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        calendarioCompra.setDateFormatString("yyyy-MM-dd");

        jLabel5.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        jLabel5.setText("Abona");

        Abona.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Si", "No" }));

        javax.swing.GroupLayout panelCompraLayout = new javax.swing.GroupLayout(panelCompra);
        panelCompra.setLayout(panelCompraLayout);
        panelCompraLayout.setHorizontalGroup(
            panelCompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panelCompraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(proveedorCompra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(4, 4, 4)
                .addComponent(calendarioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCompraLayout.createSequentialGroup()
                .addComponent(borrarArticulosSeleccionados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Abona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelTotal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelCompraLayout.setVerticalGroup(
            panelCompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCompraLayout.createSequentialGroup()
                .addGroup(panelCompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelCliente)
                        .addComponent(proveedorCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addComponent(calendarioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(borrarArticulosSeleccionados, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelCompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(totalCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelTotal)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCompraLayout.createSequentialGroup()
                        .addGroup(panelCompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(Abona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        panelControlFactura.setLayout(new java.awt.GridLayout(1, 0));

        compraNueva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/agregar.png"))); // NOI18N
        compraNueva.setToolTipText("Realizar una nueva compra");
        panelControlFactura.add(compraNueva);

        realizarCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/vender.png"))); // NOI18N
        realizarCompra.setToolTipText("Solo registrar la compra");
        panelControlFactura.add(realizarCompra);

        modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/guardar.png"))); // NOI18N
        modificar.setToolTipText("Guardar cambios realizados en la edición de la compra");
        modificar.setEnabled(false);
        panelControlFactura.add(modificar);

        javax.swing.GroupLayout fondoImagenLayout = new javax.swing.GroupLayout(fondoImagen);
        fondoImagen.setLayout(fondoImagenLayout);
        fondoImagenLayout.setHorizontalGroup(
            fondoImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 823, Short.MAX_VALUE)
            .addGroup(fondoImagenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelClientesAarticulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(fondoImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCompra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelControlFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        fondoImagenLayout.setVerticalGroup(
            fondoImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoImagenLayout.createSequentialGroup()
                .addComponent(panelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(fondoImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(fondoImagenLayout.createSequentialGroup()
                        .addComponent(panelCompra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelControlFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelClientesAarticulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jScrollPane1.setViewportView(fondoImagen);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 825, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox Abona;
    private javax.swing.JButton articulosALaCompra;
    private javax.swing.JButton borrarArticulosSeleccionados;
    private javax.swing.JTextField busquedaCuil;
    private javax.swing.JTextField busquedaNombreProveedor;
    private com.toedter.calendar.JDateChooser calendarioCompra;
    private javax.swing.JButton compraNueva;
    private javax.swing.JPanel fondoImagen;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel labelCliente;
    private javax.swing.JLabel labelCuil;
    private javax.swing.JLabel labelNombre;
    private javax.swing.JLabel labelTotal;
    private javax.swing.JButton modificar;
    private javax.swing.JPanel panelArticulos;
    private javax.swing.JPanel panelClientesAarticulos;
    private javax.swing.JPanel panelCompra;
    private javax.swing.JPanel panelControlFactura;
    private javax.swing.JPanel panelProveedores;
    private org.edisoncor.gui.panel.PanelImage panelTitulo;
    private javax.swing.JButton proveedorALaCompra;
    private javax.swing.JTextField proveedorCompra;
    private javax.swing.JButton realizarCompra;
    private javax.swing.JTable tablaArticulos;
    private javax.swing.JTable tablaCompra;
    private javax.swing.JTable tablaProveedores;
    private javax.swing.JLabel titulo;
    private javax.swing.JTextField totalCompra;
    // End of variables declaration//GEN-END:variables
}
