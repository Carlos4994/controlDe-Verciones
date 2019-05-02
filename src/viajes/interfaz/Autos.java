/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viajes.interfaces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class Autos extends javax.swing.JFrame {

    /**
     * Creates new form Autos
     */
    String id;
    ArrayList listaMarcas = new ArrayList();
    ArrayList listaModelos = new ArrayList();
    DefaultTableModel modelo;

    public Autos() {

        initComponents();
        setLocationRelativeTo(this);
        bloquear();
        bloquearBoton();
        cargarMarca();
        cargarTablaAutos("");

        tbAutos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override //Controlar Sobre carga de Metodos 
            public void valueChanged(ListSelectionEvent e) {

                if (tbAutos.getSelectedRow() != -1) {
                    bloquearBotonesModificar();
                    desbloquear();
                    int fila = tbAutos.getSelectedRow();
                    txtAutPlaca.setText(tbAutos.getValueAt(fila, 0).toString().trim());
                    cbAutMarca.setSelectedItem(tbAutos.getValueAt(fila, 1).toString());
                    cbAutModelo.setSelectedItem(tbAutos.getValueAt(fila, 2).toString());
                    spnAutAnio.setValue(Integer.parseInt(tbAutos.getValueAt(fila, 3).toString()));
                    cbAutColor.setSelectedItem((String) tbAutos.getValueAt(fila, 4));
                    spnAutCapacidad.setValue(Integer.parseInt(tbAutos.getValueAt(fila, 5).toString()));
                    txtAutObservacion.setText(tbAutos.getValueAt(fila, 6).toString().trim());

                }
            }
        });
    }

//    public void modificar() {
//
//        try {
//            conexion cc = new conexion();
//            Connection cn = cc.conectar();
//            Statement comando = cn.createStatement();
//            int cantidad = comando.executeUpdate("update autos set "
//                    + " MOD_CODIGO ='" + cbAutModelo.getSelectedItem().toString().substring(0, 1) + "'" + ", AUT_ANIO ='" + (int) spnAutAnio.getValue() + "', AUT_COLOR ='" + cbAutColor.getSelectedItem().toString() + "', AUT_CAPACIDAD ='" + (int) spnAutCapacidad.getValue() + "' , AUT_OBSERVACION ='" + txtAutObservacion.getText() + "'  where AUT_PLACA = '" + txtAutPlaca.getText() + "'");
//            if (cantidad == 1) {
//                JOptionPane.showMessageDialog(null, " Modifico con Exito");
//                cargarTablaAutos("");
//            } else {
//                JOptionPane.showMessageDialog(null, "No existe Auto de Placa ");
//            }
//            cn.close();
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, " Error -->" + ex);
//        }
//    }
    public void modificarAuto() {
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql = "";
        try {
            sql = "update autos set "
                    + " MOD_CODIGO ='" + cbAutModelo.getSelectedItem().toString().substring(0, 1) + "'" + ", AUT_ANIO ='" + (int) spnAutAnio.getValue() + "', AUT_COLOR ='" + cbAutColor.getSelectedItem().toString() + "', AUT_CAPACIDAD ='" + (int) spnAutCapacidad.getValue() + "' , AUT_OBSERVACION ='" + txtAutObservacion.getText() + "'  where AUT_PLACA = '" + txtAutPlaca.getText() + "'";
            PreparedStatement psd = cn.prepareStatement(sql);
            Integer cantidad = psd.executeUpdate();

            if (cantidad == 1) {
                JOptionPane.showMessageDialog(null, " Modifico con Exito");
                cargarTablaAutos("");
                bloquear();
                bloquearBoton();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "No se ha Modificado");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, " Error -->" + ex);
        }
    }

    public void bloquear() {
        txtAutPlaca.setEnabled(false);
        cbAutColor.setEnabled(false);
        cbAutMarca.setEnabled(false);
        cbAutModelo.setEnabled(false);
        spnAutAnio.setEnabled(false);
        spnAutCapacidad.setEnabled(false);
        txtAutObservacion.setEnabled(false);
    }

    public void bloquearBoton() {
        btnCancelar.setEnabled(true);
        btnEliminar.setEnabled(false);
        btnModificar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnNuevo.setEnabled(true);
    }

    public void bloquearBotonesModificar() {
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnModificar.setEnabled(true);
    }

    public void bloquearBotonNuevo() {
        btnNuevo.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnModificar.setEnabled(false);

    }

    public void desbloquearBoton() {
        btnCancelar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnModificar.setEnabled(true);
        btnGuardar.setEnabled(true);
    }

    public void desbloquear() {
        txtAutPlaca.setEnabled(true);
        cbAutColor.setEnabled(true);
        cbAutMarca.setEnabled(true);
        cbAutModelo.setEnabled(true);
        spnAutAnio.setEnabled(true);
        spnAutCapacidad.setEnabled(true);
        txtAutObservacion.setEnabled(true);
    }

    public void limpiar() {

        txtAutPlaca.setEnabled(false);
        cbAutColor.setEnabled(false);
        cbAutModelo.setEnabled(false);
        spnAutAnio.setEnabled(false);
        spnAutCapacidad.setEnabled(false);
        txtAutObservacion.setEnabled(false);

        txtAutPlaca.setText("");
        cbAutMarca.setSelectedIndex(-1);
        cbAutModelo.setSelectedIndex(-1);
        spnAutAnio.setValue(0);
        cbAutColor.setSelectedIndex(-1);
        spnAutCapacidad.setValue(0);
        txtAutObservacion.setText("");

    }

    public void cargarMarca() {
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql = "";
        sql = "select * from marcas";
        Statement psd;
        try {
            psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);

            while (rs.next()) {
                listaMarcas.add(rs.getString("MAR_CODIGO"));
                id = rs.getString("MAR_CODIGO");
                String nombre = rs.getString("MAR_NOM");
                cbAutMarca.addItem(nombre);

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    public void cargarModelo() {
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql = "";
        sql = "select * from modelos";
        Statement psd;
        try {
            psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                listaModelos.add(rs.getString("MOD_CODIGO"));
                String idMarca = rs.getString("MAR_CODIGO");
                String nombre = rs.getString("MOD_NOMBRE");
                int num = cbAutMarca.getSelectedIndex();
//                if (idMarca.equals(listaMarcas.get(num).toString())) {
//                    cbAutModelo.addItem(nombre);
//                }
                if (num >= 1) {
                    cbAutModelo.addItem(nombre);
                }

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    public void cargarModelo2() {
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql = "";
        Integer item = cbAutMarca.getSelectedIndex() + 1;
        sql = "select * from modelos where MOD_CODIGO = '" + item + "'";
        Statement psd;
        try {
            psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                cbAutModelo.addItem(rs.getString("MOD_CODIGO") + " " + rs.getString("MOD_NOMBRE"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    public void guardarAuto() {
        if (txtAutPlaca.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Placa");
            txtAutPlaca.requestFocus();
        } else if (cbAutMarca.getSelectedItem().equals("")) {
            JOptionPane.showMessageDialog(null, "Escoja Marca");
            cbAutMarca.requestFocus();
        } else if (cbAutModelo.getSelectedItem().equals("")) {
            JOptionPane.showMessageDialog(null, "Escoja Modelo");
            cbAutModelo.requestFocus();
        } else if (Integer.valueOf(spnAutAnio.getValue().toString()) < 1960 || Integer.valueOf(spnAutAnio.getValue().toString()) > 2019) {
            JOptionPane.showMessageDialog(null, "El año debe estar entre 1960 y el año actual");
            spnAutAnio.requestFocus();
        } else if (cbAutColor.getSelectedItem().equals("")) {
            JOptionPane.showMessageDialog(null, "Escoja Color");
            cbAutColor.requestFocus();
        } else if (Integer.valueOf(spnAutCapacidad.getValue().toString()) < 4 || Integer.valueOf(spnAutCapacidad.getValue().toString()) > 9) {
            JOptionPane.showMessageDialog(null, "La Capacidad debe estar entre 4 y 9");
            spnAutCapacidad.requestFocus();
        } else {

            try {
                String AUT_PLACA, MOD_CODIGO, AUT_COLOR, AUT_OBSERVACION;
                Integer AUT_ANIO, AUT_CAPACIDAD;

                conexion cc = new conexion();
                Connection cn = cc.conectar();
                String sql = "";
                sql = "insert into autos(AUT_PLACA,MOD_CODIGO,AUT_ANIO,AUT_COLOR,AUT_CAPACIDAD,AUT_OBSERVACION,AUT_ESTADO) values(?,?,?,?,?,?,?)";

                PreparedStatement psd = cn.prepareStatement(sql);

                psd.setString(1, txtAutPlaca.getText());
                psd.setString(2, cbAutModelo.getSelectedItem().toString().substring(0, 1));
                psd.setInt(3, (int) spnAutAnio.getValue());
                psd.setString(4, cbAutColor.getSelectedItem().toString());
                psd.setInt(5, (int) spnAutCapacidad.getValue());
                if (txtAutObservacion.getText().isEmpty()) {
                    psd.setString(6, "Sin Observaciòn");
                } else {
                    psd.setString(6, txtAutObservacion.getText());
                }
                  psd.setInt(7, 1);
                int n = psd.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Auto Registrado");
                    limpiar();
                    btnNuevo.setEnabled(true);
                    cargarTablaAutos(txtBuscar.getText());
                }

            } catch (SQLException ex) {
                Logger.getLogger(Autos.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

//    public void cargarTablaAutos() {
//        String[] titulos = {"PLACA", "MODELO", "MARCA", "AÑO", "COLOR", "CAPACIDAD", "OBSERVACION"};
//        String[] registros = new String[7];
//        modelo = new DefaultTableModel(null, titulos);
//        conexion cc = new conexion();
//        Connection cn = cc.conectar();
//        String sql = "";
//        sql = "select autos.AUT_PLACA,"
//                + "autos.MOD_CODIGO,"
//                + "autos.AUT_ANIO,"
//                + "autos.AUT_COLOR,"
//                + "autos.AUT_CAPACIDAD,"
//                + "autos.AUT_OBSERVACION,"
//                + "modelos.MOD_NOMBRE,"
//                + "marcas.MAR_NOM"
//                + " from autos, modelos, marcas"
//                + " where autos.MOD_CODIGO = modelos.MOD_CODIGO and modelos.MAR_CODIGO = marcas.MAR_CODIGO";
//        try {
//            Statement psd = cn.createStatement();
//            ResultSet rs = psd.executeQuery(sql);
//            while (rs.next()) {
//                registros[0] = rs.getString("AUT_PLACA");
//                registros[1] = rs.getString("MAR_NOM");
//                registros[2] = rs.getString("MOD_NOMBRE");
//                registros[3] = rs.getString("AUT_ANIO");
//                registros[4] = rs.getString("AUT_COLOR");
//                registros[5] = rs.getString("AUT_CAPACIDAD");
//                registros[6] = rs.getString("AUT_OBSERVACION");
//
//                modelo.addRow(registros);
//            }
//            tbAutos.setModel(modelo);
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, ex);
//        }
//
//    }
    public void cargarTablaAutos(String placa) {
        String[] titulos = {"PLACA", "MODELO", "MARCA", "AÑO", "COLOR", "CAPACIDAD", "OBSERVACION"};
        String[] registros = new String[7];
        modelo = new DefaultTableModel(null, titulos);
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql = "";
        sql = "select autos.AUT_PLACA,"
                + "autos.MOD_CODIGO,"
                + "autos.AUT_ANIO,"
                + "autos.AUT_COLOR,"
                + "autos.AUT_CAPACIDAD,"
                + "autos.AUT_OBSERVACION,"
                + "modelos.MOD_NOMBRE,"
                + "marcas.MAR_NOM"
                + " from autos, modelos, marcas"
                + " where autos.MOD_CODIGO = modelos.MOD_CODIGO and modelos.MAR_CODIGO = marcas.MAR_CODIGO "
                + "and autos.AUT_PLACA like '%" + placa + "'"
                +" and autos.AUT_ESTADO =1";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                registros[0] = rs.getString("AUT_PLACA");
                registros[1] = rs.getString("MAR_NOM");
                registros[2] = rs.getString("MOD_NOMBRE");
                registros[3] = rs.getString("AUT_ANIO");
                registros[4] = rs.getString("AUT_COLOR");
                registros[5] = rs.getString("AUT_CAPACIDAD");
                registros[6] = rs.getString("AUT_OBSERVACION");

                modelo.addRow(registros);
            }
            tbAutos.setModel(modelo);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

//    public void eliminarAutos(String placa) {
//
//        try {
//            conexion cc = new conexion();
//            Connection cn = cc.conectar();
//            Statement comando = cn.createStatement();
//            int cantidad = comando.executeUpdate("delete from autos where AUT_PLACA = '" + placa + "'");
//            if (cantidad == 1) {
//                JOptionPane.showMessageDialog(null, "Eliminado Exitosamente");
//            } else {
//                JOptionPane.showMessageDialog(null, "No existe Auto con la Placa " + placa);
//            }
//            cn.close();
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "error " + ex);
//        }
//
//    }
    public void eliminarAutos() {
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql = "";
        sql = "update autos set AUT_ESTADO = 0 where AUT_PLACA = '" + txtAutPlaca.getText() + "'";
        
        
        if(JOptionPane.showConfirmDialog(new JInternalFrame(), 
                "Esta seguro que desea Eliminar ?",
                "Borrar Registro",
                JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
        try {

            PreparedStatement psd = cn.prepareStatement(sql);
            int cantidad = psd.executeUpdate();
            if (cantidad == 1) {
                JOptionPane.showMessageDialog(null, "Eliminado Exitosamente");
                  cargarTablaAutos("");
                bloquear();
                bloquearBoton();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "No se ha Eliminado ");
            }
            cn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField2 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtAutPlaca = new javax.swing.JTextField();
        cbAutModelo = new javax.swing.JComboBox<>();
        spnAutAnio = new javax.swing.JSpinner();
        cbAutColor = new javax.swing.JComboBox<>();
        spnAutCapacidad = new javax.swing.JSpinner();
        txtAutObservacion = new javax.swing.JTextField();
        cbAutMarca = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbAutos = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();

        jTextField2.setText("jTextField2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setText("Placa");

        jLabel3.setText("Modelo");

        jLabel4.setText("Año");

        jLabel5.setText("Color");

        jLabel6.setText("Capacidad");

        jLabel7.setText("Observaciòn");

        txtAutPlaca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAutPlacaActionPerformed(evt);
            }
        });
        txtAutPlaca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAutPlacaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAutPlacaKeyTyped(evt);
            }
        });

        cbAutModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAutModeloActionPerformed(evt);
            }
        });

        cbAutColor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Rojo", "Azul", "Beige", "Negro", "Plateado" }));
        cbAutColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAutColorActionPerformed(evt);
            }
        });

        txtAutObservacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAutObservacionKeyTyped(evt);
            }
        });

        cbAutMarca.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbAutMarcaItemStateChanged(evt);
            }
        });
        cbAutMarca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbAutMarcaMouseClicked(evt);
            }
        });
        cbAutMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAutMarcaActionPerformed(evt);
            }
        });

        jLabel2.setText("Marca");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel2))
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtAutObservacion, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                    .addComponent(txtAutPlaca)
                    .addComponent(cbAutModelo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(spnAutAnio)
                    .addComponent(cbAutColor, 0, 158, Short.MAX_VALUE)
                    .addComponent(spnAutCapacidad)
                    .addComponent(cbAutMarca, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(179, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtAutPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbAutMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbAutModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(spnAutAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbAutColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(spnAutCapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txtAutObservacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevo)
                .addGap(18, 18, 18)
                .addComponent(btnGuardar)
                .addGap(18, 18, 18)
                .addComponent(btnModificar)
                .addGap(18, 18, 18)
                .addComponent(btnEliminar)
                .addGap(18, 18, 18)
                .addComponent(btnCancelar)
                .addGap(18, 18, 18)
                .addComponent(btnSalir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tbAutos.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tbAutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbAutos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbAutosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbAutos);

        jLabel8.setText("Buscar Por Placa");

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtAutPlacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAutPlacaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAutPlacaActionPerformed

    private void cbAutColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAutColorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbAutColorActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        modificarAuto();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        desbloquear();
        desbloquearBoton();
        bloquearBotonNuevo();

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtAutPlacaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAutPlacaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtAutPlaca.getText().length() >= 7) {
            evt.consume();
        }
    }//GEN-LAST:event_txtAutPlacaKeyTyped

    private void txtAutObservacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAutObservacionKeyTyped

    }//GEN-LAST:event_txtAutObservacionKeyTyped

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardarAuto();

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void cbAutMarcaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbAutMarcaItemStateChanged
        cargarModelo();
    }//GEN-LAST:event_cbAutMarcaItemStateChanged

    private void cbAutMarcaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbAutMarcaMouseClicked

    }//GEN-LAST:event_cbAutMarcaMouseClicked

    private void cbAutMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAutMarcaActionPerformed
        cbAutModelo.removeAllItems();
        //cargarModelo();
        cargarModelo2();
    }//GEN-LAST:event_cbAutMarcaActionPerformed

    private void cbAutModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAutModeloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbAutModeloActionPerformed

    private void txtAutPlacaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAutPlacaKeyReleased

    }//GEN-LAST:event_txtAutPlacaKeyReleased

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
//        int i = tbAutos.getSelectedRow();
//        String placa = (String) tbAutos.getValueAt(i, 0);
        eliminarAutos();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tbAutosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbAutosMouseClicked
        btnEliminar.setEnabled(true);
    }//GEN-LAST:event_tbAutosMouseClicked

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        cargarTablaAutos(txtBuscar.getText());
    }//GEN-LAST:event_txtBuscarKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Autos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Autos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Autos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Autos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Autos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> cbAutColor;
    private javax.swing.JComboBox<String> cbAutMarca;
    private javax.swing.JComboBox<String> cbAutModelo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JSpinner spnAutAnio;
    private javax.swing.JSpinner spnAutCapacidad;
    private javax.swing.JTable tbAutos;
    private javax.swing.JTextField txtAutObservacion;
    private javax.swing.JTextField txtAutPlaca;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
