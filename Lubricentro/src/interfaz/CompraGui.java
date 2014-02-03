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
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author jacinto
 */
public class CompraGui extends javax.swing.JInternalFrame {

    
    private DefaultTableModel tablaArticulosDefault;//tabla default de los clientes
    private DefaultTableModel tablaCompraDefault;
    private DefaultTableModel tablaProveedoresDefault;

    /**
     * Creates new form CompraGui
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
        this.compraNueva.addActionListener(lis);
        this.realizarCompra.addActionListener(lis);
        this.borrarArticulosSeleccionados.addActionListener(lis);
        this.abona.addActionListener(lis);
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
        return abona;
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
        totalCompra.setText("");
        proveedorCompra.setText("");

    }
     public void paraVerCompra(boolean si){
         tablaArticulos.setEnabled(!si);
         tablaCompra.setEnabled(!si);
         tablaProveedores.setEnabled(!si);
         busquedaNombreProveedor.setEnabled(!si);
         fram.setEnabled(!si);
         codigo.setEnabled(!si);
         calendarioCompra.setEnabled(!si);
         borrarArticulosSeleccionados.setEnabled(!si);
         abona.setEnabled(!si);
         realizarCompra.setEnabled(!si);
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

    public JTextField getCodigo() {
        return codigo;
    }

    public JTextField getFram() {
        return fram;
    }

 
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        fondoImagen = new javax.swing.JPanel();
        panelTitulo = new org.edisoncor.gui.panel.PanelImage();
        titulo = new javax.swing.JLabel();
        panelClientesAarticulos = new javax.swing.JPanel();
        panelArticulos = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaArticulos = new javax.swing.JTable();
        labelNombre1 = new javax.swing.JLabel();
        codigo = new javax.swing.JTextField();
        labelNombre2 = new javax.swing.JLabel();
        fram = new javax.swing.JTextField();
        panelProveedores = new javax.swing.JPanel();
        busquedaNombreProveedor = new javax.swing.JTextField();
        labelNombre = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaProveedores = new javax.swing.JTable();
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
        abona = new javax.swing.JComboBox();
        panelControlFactura = new javax.swing.JPanel();
        compraNueva = new javax.swing.JButton();
        realizarCompra = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Registrar Compra");
        setPreferredSize(new java.awt.Dimension(841, 439));

        jPanel1.setPreferredSize(new java.awt.Dimension(825, 448));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(822, 448));

        fondoImagen.setPreferredSize(new java.awt.Dimension(820, 400));

        panelTitulo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
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
                "Id", "Codigo", "Marca"
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
        jScrollPane6.setViewportView(tablaArticulos);

        labelNombre1.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        labelNombre1.setText("Codigo");

        codigo.setToolTipText("Filtrar busqueda por codigo");

        labelNombre2.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        labelNombre2.setText("Fram");

        fram.setToolTipText("Filtrar busqueda por equivalencia en fram");

        javax.swing.GroupLayout panelArticulosLayout = new javax.swing.GroupLayout(panelArticulos);
        panelArticulos.setLayout(panelArticulosLayout);
        panelArticulosLayout.setHorizontalGroup(
            panelArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(panelArticulosLayout.createSequentialGroup()
                .addGroup(panelArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelNombre1)
                    .addComponent(labelNombre2))
                .addGap(17, 17, 17)
                .addGroup(panelArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fram)
                    .addComponent(codigo)))
        );
        panelArticulosLayout.setVerticalGroup(
            panelArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelArticulosLayout.createSequentialGroup()
                .addGroup(panelArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNombre1))
                .addGap(10, 10, 10)
                .addGroup(panelArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fram, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNombre2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        panelProveedores.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Proveedores", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Schoolbook L", 3, 18))); // NOI18N

        busquedaNombreProveedor.setToolTipText("Filtrar busqueda por ID");

        labelNombre.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        labelNombre.setText("Nombre");

        tablaProveedores.setAutoCreateRowSorter(true);
        tablaProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod.", "Nombre"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(tablaProveedores);

        javax.swing.GroupLayout panelProveedoresLayout = new javax.swing.GroupLayout(panelProveedores);
        panelProveedores.setLayout(panelProveedoresLayout);
        panelProveedoresLayout.setHorizontalGroup(
            panelProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProveedoresLayout.createSequentialGroup()
                .addComponent(labelNombre)
                .addGap(17, 17, 17)
                .addComponent(busquedaNombreProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE))
            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        panelProveedoresLayout.setVerticalGroup(
            panelProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProveedoresLayout.createSequentialGroup()
                .addGroup(panelProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(busquedaNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelClientesAarticulosLayout = new javax.swing.GroupLayout(panelClientesAarticulos);
        panelClientesAarticulos.setLayout(panelClientesAarticulosLayout);
        panelClientesAarticulosLayout.setHorizontalGroup(
            panelClientesAarticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClientesAarticulosLayout.createSequentialGroup()
                .addGroup(panelClientesAarticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelArticulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelProveedores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        panelClientesAarticulosLayout.setVerticalGroup(
            panelClientesAarticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelClientesAarticulosLayout.createSequentialGroup()
                .addComponent(panelProveedores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(panelArticulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                java.lang.Integer.class, java.math.BigDecimal.class, java.lang.String.class, java.math.BigDecimal.class, java.math.BigDecimal.class
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
        jScrollPane8.setViewportView(tablaCompra);

        labelTotal.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        labelTotal.setText("Total");

        totalCompra.setEditable(false);

        jLabel3.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        jLabel3.setText("Fecha");

        borrarArticulosSeleccionados.setText("Borrar articulos seleccionados");
        borrarArticulosSeleccionados.setToolTipText("Borrar articulos seleccionados en la factura");
        borrarArticulosSeleccionados.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        calendarioCompra.setDateFormatString("yyyy-MM-dd");

        jLabel5.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        jLabel5.setText("Abona");

        abona.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Si", "No" }));

        javax.swing.GroupLayout panelCompraLayout = new javax.swing.GroupLayout(panelCompra);
        panelCompra.setLayout(panelCompraLayout);
        panelCompraLayout.setHorizontalGroup(
            panelCompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(abona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
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
                            .addComponent(abona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        panelControlFactura.setLayout(new java.awt.GridLayout(1, 0));

        compraNueva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/agregar.png"))); // NOI18N
        compraNueva.setToolTipText("Realizar una nueva compra");
        panelControlFactura.add(compraNueva);

        realizarCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/vender.png"))); // NOI18N
        realizarCompra.setToolTipText("Solo registrar la compra");
        panelControlFactura.add(realizarCompra);

        javax.swing.GroupLayout fondoImagenLayout = new javax.swing.GroupLayout(fondoImagen);
        fondoImagen.setLayout(fondoImagenLayout);
        fondoImagenLayout.setHorizontalGroup(
            fondoImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoImagenLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(fondoImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(fondoImagenLayout.createSequentialGroup()
                        .addComponent(panelClientesAarticulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addGroup(fondoImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelCompra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelControlFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 0, 0))
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
                        .addComponent(panelControlFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelClientesAarticulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        jScrollPane1.setViewportView(fondoImagen);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(3, 3, 3))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox abona;
    private javax.swing.JButton borrarArticulosSeleccionados;
    private javax.swing.JTextField busquedaNombreProveedor;
    private com.toedter.calendar.JDateChooser calendarioCompra;
    private javax.swing.JTextField codigo;
    private javax.swing.JButton compraNueva;
    private javax.swing.JPanel fondoImagen;
    private javax.swing.JTextField fram;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel labelCliente;
    private javax.swing.JLabel labelNombre;
    private javax.swing.JLabel labelNombre1;
    private javax.swing.JLabel labelNombre2;
    private javax.swing.JLabel labelTotal;
    private javax.swing.JPanel panelArticulos;
    private javax.swing.JPanel panelClientesAarticulos;
    private javax.swing.JPanel panelCompra;
    private javax.swing.JPanel panelControlFactura;
    private javax.swing.JPanel panelProveedores;
    private org.edisoncor.gui.panel.PanelImage panelTitulo;
    private javax.swing.JTextField proveedorCompra;
    private javax.swing.JButton realizarCompra;
    private javax.swing.JTable tablaArticulos;
    private javax.swing.JTable tablaCompra;
    private javax.swing.JTable tablaProveedores;
    private javax.swing.JLabel titulo;
    private javax.swing.JTextField totalCompra;
    // End of variables declaration//GEN-END:variables
}
