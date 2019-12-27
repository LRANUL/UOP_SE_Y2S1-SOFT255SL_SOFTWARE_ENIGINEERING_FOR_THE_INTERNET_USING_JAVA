
import DatabaseConnection.DBConnection;
import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ranul
 */
public abstract class Charges extends Account {
    String Acc;
    Connection conn = null;
    PreparedStatement ps = null;

    DBConnection db;

 public Charges(String Acc) {
        
        String AccNo = Acc;
        if (AccNo.length() > 2) {
            int Type = parseInt(AccNo.substring(0, 2));
            switch (Type) {
                case 25:
                    try {
                        conn = DriverManager.getConnection(db.DatabaseConnectionUrl());
                        String sql = "UPDATE dbo.AccountNormalSavings SET NSAccountBalance = NSAccountBalance - 5 WHERE NSAccountNumber = ?";
                        ps = conn.prepareStatement(sql);
                        ps.setString(1, AccNo);
                        ps.executeUpdate();
                        
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    } finally {
                        JOptionPane.showMessageDialog(null, "Account Charged, Release the Card");
                    }   break;
                case 45:
                    try {
                        conn = DriverManager.getConnection(db.DatabaseConnectionUrl());
                        String sql = "UPDATE dbo.AccountBonusSavings SET BSAccountBalance = BSAccountBalance - 5 WHERE BSAccountNumber = ?";
                        ps = conn.prepareStatement(sql);
                        ps.setString(1, AccNo);
                        ps.executeUpdate();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    } finally {
                        JOptionPane.showMessageDialog(null, "Account Charged, Release the Card");
                    }   break;
                case 75:
                    try {
                        conn = DriverManager.getConnection(db.DatabaseConnectionUrl());
                        String sql = "UPDATE dbo.AccountPremierSavings SET PSAccountBalance = PSAccountBalance - 5 WHERE PSAccountNumber = ?";
                        ps = conn.prepareStatement(sql);
                        ps.setString(1, AccNo);
                        ps.executeUpdate();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    } finally {
                        JOptionPane.showMessageDialog(null, "Account Charged, Release the Card");
                    }   break;
                default:
                    JOptionPane.showMessageDialog(null, "Account Not Valid");
                    break;
            }
        }

    }
 }

