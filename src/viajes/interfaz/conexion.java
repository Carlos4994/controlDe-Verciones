/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viajes.interfaces;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class conexion {

    Connection conect = null;

    public Connection conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conect = DriverManager.getConnection("jdbc:mysql://localhost/viajes", "root", "");
           // JOptionPane.showMessageDialog(null, "Se Conecto ");
            System.out.println("Se Conecto");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Sin conexiòn intentalo màs tarde, ponte en contacto con el administrador :v");
        }
        return conect;
    }
}
