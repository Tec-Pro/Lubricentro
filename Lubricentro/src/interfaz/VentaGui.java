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
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author nico
 */
public class VentaGui extends javax.swing.JPanel {

    private DefaultTableModel tablaArticulosDefault;//tabla default de los clientes
    private DefaultTableModel tablaFacturaDefault;
    private DefaultTableModel tablaClientesDefault;

    /**
     * Creates new form AbmClienteGui
     */
    public VentaGui() {
        initComponents();
        tablaArticulosDefault = (DefaultTableModel) tablaArticulos.getModel();//conveirto la tabla
        tablaFacturaDefault = (DefaultTableModel) tablaFactura.getModel();
        tablaClientesDefault = (DefaultTableModel) tablaClientes.getModel();
        Calendar miCalendario = Calendar.getInstance();
        java.util.Date eldia = miCalendario.getTime();
        int diaHoy = miCalendario.get(Calendar.DAY_OF_MONTH);
        int mes=miCalendario.get(Calendar.MONTH);
        int anio =miCalendario.get(Calendar.YEAR);
        calendarioFactura.setDate(Date.valueOf(anio+"-"+(mes+1)+"-"+diaHoy));
       
    }


    /**
     * Seteo el actionListener para los botones articulosALafactura,
     * clienteALafactura, facturaNueva, imprimir realizarVenta,
     * borrarArticulosSeleccionados, modificar
     *
     * @param
     * @return
     * @exception
     */
    public void setActionListener(ActionListener lis) {
        this.articulosALaFactura.addActionListener(lis);
        this.clienteALaFactura.addActionListener(lis);
        this.facturaNueva.addActionListener(lis);
        this.realizarVenta.addActionListener(lis);
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

    /**
     * Retorno la tabla Clientes con tipo TableModelDefault para pdoer realizar
     * inserciones y eliminaciones de filas más facilmente
     *
     * @param
     * @return DefaultTableModel
     * @exception
     */
    public DefaultTableModel getTablaclientesDefault() {
        return tablaArticulosDefault;
    }

    /**
     * Retorno la tabla factura con tipo TableModelDefault para pdoer realizar
     * inserciones y eliminaciones de filas más facilmente
     *
     * @param
     * @return DefaultTableModel
     * @exception
     */
    public DefaultTableModel getTablaFacturaDefault() {
        return tablaFacturaDefault;
    }

    /**
     * Retorno boton articulosALaFactura para enviar los articulos seleccionados
     * en la tabla articulos a la tabla factura
     *
     * @param
     * @return JButton
     * @exception
     */
    public JButton getArticulosALaFactura() {
        return articulosALaFactura;
    }

    /**
     * Retorno el campo busqeudaApellido para poder filtrar las busquedas dado
     * el apellido de un cliente
     *
     * @param
     * @return JTextField
     * @exception
     */
    public JTextField getBusquedaApellido() {
        return busquedaApellido;
    }

    /**
     * Retorno el campo busquedaCodigoArticulo para poder filtrar las busquedas
     * dado el codigo de un articulo
     *
     * @param
     * @return JTextField
     * @exception
     */
    public JTextField getBusquedaCodigoArticulo() {
        return busquedaCodigoArticulo;
    }

    /**
     * Retorno la tabla clientescon tipo TableModelDefault para pdoer realizar
     * inserciones y eliminaciones de filas más facilmente
     *
     * @param
     * @return DefaultTableModel
     * @exception
     */
    public DefaultTableModel getTablaClientesDefault() {
        return tablaClientesDefault;
    }

    /**
     * Retorno boton borrarArticulosSeleccionados para borrar los articulos
     * seleccionados en la tabla factura
     *
     * @param
     * @return JButton
     * @exception
     */
    public JButton getBorrarArticulosSeleccionados() {
        return borrarArticulosSeleccionados;
    }

    
    
    /**
     * Retorno boton modificar para confirmar cambios realizados en una factura
     *
     * @param
     * @return JButton
     * @exception
     */
    public JButton getModificar() {
        return modificar;
    }

    /**
     * Retorno el campo busquedaCodigoCliente para poder filtrar las busquedas
     * dado el id de un cliente
     *
     * @param
     * @return JTextField
     * @exception
     */
    public JTextField getBusquedaCodigoCliente() {
        return busquedaCodigoCliente;
    }

    /**
     * Retorno el campo busquedaMarca para poder filtrar las busquedas dado la
     * marca de un producto
     *
     * @param
     * @return JTextField
     * @exception
     */
    public JTextField getBusquedaMarca() {
        return busquedaMarca;
    }

    /**
     * Retorno el campo busquedaCodigoNombre para poder filtrar las busquedas
     * dado el nombre de un producto
     *
     * @param
     * @return JTextField
     * @exception
     */
    public JTextField getBusquedaNombre() {
        return busquedaNombre;
    }

    /**
     * Retorno boton clienteALafacutra para enviar el cliente seleccionado al
     * campo cliente en el panel de la factura
     *
     * @param
     * @return JButton
     * @exception
     */
    public JButton getClienteALaFactura() {
        return clienteALaFactura;
    }

    /**
     * Retorno el campo del cliente de la factura
     *
     * @param
     * @return JTextField
     * @exception
     */
    public JTextField getClienteFactura() {
        return clienteFactura;
    }

    /**
     * Retorno boton facturaNueva en donde limpia los campos de la factura para
     * iniciar una nueva venta
     *
     * @param
     * @return JButton
     * @exception
     */
    public JButton getFacturaNueva() {
        return facturaNueva;
    }

    /**
     * Retorno la tabla clientes con tipo JTable
     *
     * @param
     * @return JTable
     * @exception
     */
    public JTable getTablaClientes() {
        return tablaClientes;
    }

    /**
     * Retorno boton realizar venta para confirmar la venta, se debe guardar los
     * datos en la base de datos
     *
     * @param
     * @return JButton
     * @exception
     */
    public JButton getRealizarVenta() {
        return realizarVenta;
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
     * Retorno tabla factura con tipo JTable
     *
     * @param
     * @return JTable
     * @exception
     */
    public JTable getTablaFactura() {
        return tablaFactura;
    }

    /**
     * Retorno el campo totalFactura que contiene el resultado final del a
     * factura
     *
     * @param
     * @return JTextField
     * @exception
     */
    public JTextField getTotalFactura() {
        return totalFactura;
    }

    /**
     * Retorno el calendario que contiene la fecha de la factura
     *
     * @param
     * @return JDateChooser
     * @exception
     */
    public JDateChooser getCalendarioFactura() {
        return calendarioFactura;
    }
    
    public void limpiarVentana(){
        tablaArticulos.clearSelection();
        tablaClientes.clearSelection();
        tablaFactura.clearSelection();
        while(tablaFactura.getRowCount()>0){
            tablaFacturaDefault.removeRow(0);
        }
        Calendar miCalendario = Calendar.getInstance();
        java.util.Date eldia = miCalendario.getTime();
        int diaHoy = miCalendario.get(Calendar.DAY_OF_MONTH);
        int mes=miCalendario.get(Calendar.MONTH);
        int anio =miCalendario.get(Calendar.YEAR);
        calendarioFactura.setDate(Date.valueOf(anio+"-"+(mes+1)+"-"+diaHoy));
        clienteFactura.setText("");

    }


    
       public JTextField getCalenFacturaText(){
        return ((JTextField)calendarioFactura.getDateEditor().getUiComponent());
    }

    public JComboBox getAbona() {
        return abona;
    }

       
       
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        fondoImagen = new javax.swing.JPanel();
        panelTitulo = new org.edisoncor.gui.panel.PanelImage();
        titulo = new javax.swing.JLabel();
        panelClientesAarticulos = new javax.swing.JPanel();
        panelArticulos = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaArticulos = new javax.swing.JTable();
        labelBusquedaCodigo = new javax.swing.JLabel();
        labelBusquedaNombre = new javax.swing.JLabel();
        labelBusquedaMarca = new javax.swing.JLabel();
        busquedaCodigoArticulo = new javax.swing.JTextField();
        busquedaNombre = new javax.swing.JTextField();
        busquedaMarca = new javax.swing.JTextField();
        panelClientes = new javax.swing.JPanel();
        busquedaCodigoCliente = new javax.swing.JTextField();
        labelCodigo = new javax.swing.JLabel();
        busquedaApellido = new javax.swing.JTextField();
        labelApellido = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        clienteALaFactura = new javax.swing.JButton();
        articulosALaFactura = new javax.swing.JButton();
        panelFactura = new javax.swing.JPanel();
        labelCliente = new javax.swing.JLabel();
        clienteFactura = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablaFactura = new javax.swing.JTable();
        labelTotal = new javax.swing.JLabel();
        totalFactura = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        borrarArticulosSeleccionados = new javax.swing.JButton();
        calendarioFactura = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        abona = new javax.swing.JComboBox();
        panelControlFactura = new javax.swing.JPanel();
        facturaNueva = new javax.swing.JButton();
        realizarVenta = new javax.swing.JButton();
        modificar = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(825, 448));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(822, 448));

        fondoImagen.setPreferredSize(new java.awt.Dimension(820, 400));

        panelTitulo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/fondoCliente2.jpg"))); // NOI18N
        panelTitulo.setPreferredSize(new java.awt.Dimension(329, 49));

        titulo.setFont(new java.awt.Font("Century Schoolbook L", 3, 24)); // NOI18N
        titulo.setText("VENTA");
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

        labelBusquedaCodigo.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        labelBusquedaCodigo.setText("Código");

        labelBusquedaNombre.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        labelBusquedaNombre.setText("Nombre");

        labelBusquedaMarca.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        labelBusquedaMarca.setText("Marca");

        busquedaCodigoArticulo.setToolTipText("Filtrar por código");

        busquedaNombre.setToolTipText("Filtrar por nombre");

        busquedaMarca.setToolTipText("Filtrar por marca");

        javax.swing.GroupLayout panelArticulosLayout = new javax.swing.GroupLayout(panelArticulos);
        panelArticulos.setLayout(panelArticulosLayout);
        panelArticulosLayout.setHorizontalGroup(
            panelArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelArticulosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelBusquedaNombre)
                    .addComponent(labelBusquedaMarca)
                    .addComponent(labelBusquedaCodigo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(busquedaCodigoArticulo, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                    .addComponent(busquedaMarca)
                    .addComponent(busquedaNombre)))
            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        panelArticulosLayout.setVerticalGroup(
            panelArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelArticulosLayout.createSequentialGroup()
                .addGroup(panelArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelBusquedaCodigo)
                    .addComponent(busquedaCodigoArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelBusquedaNombre)
                    .addComponent(busquedaNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelBusquedaMarca)
                    .addComponent(busquedaMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        panelClientes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Clientes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Schoolbook L", 3, 18))); // NOI18N

        busquedaCodigoCliente.setToolTipText("Filtrar busqueda por ID");

        labelCodigo.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        labelCodigo.setText("Código");

        busquedaApellido.setToolTipText("Filtrar busqueda por apellido");

        labelApellido.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        labelApellido.setText("Apellido");

        tablaClientes.setAutoCreateRowSorter(true);
        tablaClientes.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaClientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane7.setViewportView(tablaClientes);

        javax.swing.GroupLayout panelClientesLayout = new javax.swing.GroupLayout(panelClientes);
        panelClientes.setLayout(panelClientesLayout);
        panelClientesLayout.setHorizontalGroup(
            panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClientesLayout.createSequentialGroup()
                .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelApellido)
                    .addComponent(labelCodigo))
                .addGap(17, 17, 17)
                .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(busquedaApellido, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                    .addComponent(busquedaCodigoCliente)))
            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        panelClientesLayout.setVerticalGroup(
            panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClientesLayout.createSequentialGroup()
                .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(busquedaApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelApellido))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(busquedaCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelCodigo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        jPanel1.setBorder(null);
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jPanel3.setBorder(null);
        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        clienteALaFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/enviar.png"))); // NOI18N
        clienteALaFactura.setToolTipText("Cargar cliente a la factura");

        articulosALaFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/enviar.png"))); // NOI18N
        articulosALaFactura.setToolTipText("Cargar articulos a la factura");

        javax.swing.GroupLayout panelClientesAarticulosLayout = new javax.swing.GroupLayout(panelClientesAarticulos);
        panelClientesAarticulos.setLayout(panelClientesAarticulosLayout);
        panelClientesAarticulosLayout.setHorizontalGroup(
            panelClientesAarticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClientesAarticulosLayout.createSequentialGroup()
                .addGroup(panelClientesAarticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelArticulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelClientesAarticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelClientesAarticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(clienteALaFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(articulosALaFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panelClientesAarticulosLayout.setVerticalGroup(
            panelClientesAarticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelClientesAarticulosLayout.createSequentialGroup()
                .addGroup(panelClientesAarticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelClientesAarticulosLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(clienteALaFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(panelClientesAarticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelClientesAarticulosLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(articulosALaFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelClientesAarticulosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelArticulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        panelFactura.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Factura", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Schoolbook L", 3, 18))); // NOI18N

        labelCliente.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        labelCliente.setText("Cliente");

        clienteFactura.setEditable(false);

        tablaFactura.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaFactura.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jScrollPane8.setViewportView(tablaFactura);
        tablaFactura.getColumnModel().getColumn(0).setPreferredWidth(70);
        tablaFactura.getColumnModel().getColumn(0).setMaxWidth(70);
        tablaFactura.getColumnModel().getColumn(1).setPreferredWidth(75);
        tablaFactura.getColumnModel().getColumn(1).setMaxWidth(75);
        tablaFactura.getColumnModel().getColumn(3).setPreferredWidth(75);
        tablaFactura.getColumnModel().getColumn(3).setMaxWidth(75);
        tablaFactura.getColumnModel().getColumn(4).setPreferredWidth(75);
        tablaFactura.getColumnModel().getColumn(4).setMaxWidth(75);

        labelTotal.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        labelTotal.setText("Total");

        totalFactura.setEditable(false);

        jLabel3.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        jLabel3.setText("Fecha");

        borrarArticulosSeleccionados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/borrarSeleccionado.png"))); // NOI18N
        borrarArticulosSeleccionados.setText("Borrar articulos seleccionados");
        borrarArticulosSeleccionados.setToolTipText("Borrar articulos seleccionados en la factura");
        borrarArticulosSeleccionados.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        calendarioFactura.setDateFormatString("yyyy-MM-dd");

        jLabel4.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        jLabel4.setText("Abona");

        abona.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Si", "No" }));

        javax.swing.GroupLayout panelFacturaLayout = new javax.swing.GroupLayout(panelFactura);
        panelFactura.setLayout(panelFacturaLayout);
        panelFacturaLayout.setHorizontalGroup(
            panelFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panelFacturaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clienteFactura)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(4, 4, 4)
                .addComponent(calendarioFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFacturaLayout.createSequentialGroup()
                .addComponent(borrarArticulosSeleccionados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(abona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(labelTotal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelFacturaLayout.setVerticalGroup(
            panelFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFacturaLayout.createSequentialGroup()
                .addGroup(panelFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelCliente)
                        .addComponent(clienteFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addComponent(calendarioFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(borrarArticulosSeleccionados, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(totalFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelTotal)
                            .addComponent(abona, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFacturaLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addContainerGap())))
        );

        panelControlFactura.setLayout(new java.awt.GridLayout(1, 0));

        facturaNueva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/agregar.png"))); // NOI18N
        facturaNueva.setToolTipText("Realizar una nueva venta");
        panelControlFactura.add(facturaNueva);

        realizarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/vender.png"))); // NOI18N
        realizarVenta.setToolTipText("Solo registrar la venta");
        panelControlFactura.add(realizarVenta);

        modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/guardar.png"))); // NOI18N
        modificar.setToolTipText("Guardar cambios realizados en la edición de la factura");
        modificar.setEnabled(false);
        panelControlFactura.add(modificar);

        javax.swing.GroupLayout fondoImagenLayout = new javax.swing.GroupLayout(fondoImagen);
        fondoImagen.setLayout(fondoImagenLayout);
        fondoImagenLayout.setHorizontalGroup(
            fondoImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(fondoImagenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelClientesAarticulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(fondoImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                        .addComponent(panelFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
    private javax.swing.JComboBox abona;
    private javax.swing.JButton articulosALaFactura;
    private javax.swing.JButton borrarArticulosSeleccionados;
    private javax.swing.JTextField busquedaApellido;
    private javax.swing.JTextField busquedaCodigoArticulo;
    private javax.swing.JTextField busquedaCodigoCliente;
    private javax.swing.JTextField busquedaMarca;
    private javax.swing.JTextField busquedaNombre;
    private com.toedter.calendar.JDateChooser calendarioFactura;
    private javax.swing.JButton clienteALaFactura;
    private javax.swing.JTextField clienteFactura;
    private javax.swing.JButton facturaNueva;
    private javax.swing.JPanel fondoImagen;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel labelApellido;
    private javax.swing.JLabel labelBusquedaCodigo;
    private javax.swing.JLabel labelBusquedaMarca;
    private javax.swing.JLabel labelBusquedaNombre;
    private javax.swing.JLabel labelCliente;
    private javax.swing.JLabel labelCodigo;
    private javax.swing.JLabel labelTotal;
    private javax.swing.JButton modificar;
    private javax.swing.JPanel panelArticulos;
    private javax.swing.JPanel panelClientes;
    private javax.swing.JPanel panelClientesAarticulos;
    private javax.swing.JPanel panelControlFactura;
    private javax.swing.JPanel panelFactura;
    private org.edisoncor.gui.panel.PanelImage panelTitulo;
    private javax.swing.JButton realizarVenta;
    private javax.swing.JTable tablaArticulos;
    private javax.swing.JTable tablaClientes;
    private javax.swing.JTable tablaFactura;
    private javax.swing.JLabel titulo;
    private javax.swing.JTextField totalFactura;
    // End of variables declaration//GEN-END:variables
}
