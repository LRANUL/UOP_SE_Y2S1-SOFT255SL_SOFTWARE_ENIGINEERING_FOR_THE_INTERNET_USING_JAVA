/**
* SOFT255SL COURSEWORK C1 T1
* Team No:1 
* Team Name: TEAM QUINN 
* Project: Bank Management System.
 */
import DatabaseConnection.DBConnection;
import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
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
public abstract class Charges extends Account{

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    DBConnection db;

    public Charges() {

        db = new DBConnection();

    }

    public void ChargesCustomer(String Acc) {

        String AccNo = Acc;
        if (AccNo.length() > 2) {
            int Type = parseInt(AccNo.substring(0, 2));
            switch (Type) {
                case 25:
                    try {
                        conn = DriverManager.getConnection(db.DatabaseConnectionUrl());
                        String sql = "UPDATE dbo.AccountNormalSavings SET NSAccountBalance = NSAccountBalance - 5 WHERE NSAccountNumber = ?";
                        String sql1 = "INSERT INTO dbo.CustomerTransactionWithdrawal (TransactionAmount,ctTransactionStatusID,tdTransactionDescriptionID,ansNSAccountNumber)"
                                    + "VALUES ('5','TS000001','TD000010'," + AccNo + ")";
                            ps = conn.prepareStatement(sql);
                            ps.setString(1, AccNo);
                            ps.executeUpdate();
                            ps = conn.prepareStatement(sql1);
                            ps.executeUpdate();

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    } finally {
                        JOptionPane.showMessageDialog(null, "Account Charged, Release the Card");
                    }
                    break;
                case 45:
                    try {
                        conn = DriverManager.getConnection(db.DatabaseConnectionUrl());
                        String sql = "UPDATE dbo.AccountBonusSavings SET BSAccountBalance = BSAccountBalance - 5 WHERE BSAccountNumber = ?";
                        String sql1 = "INSERT INTO dbo.CustomerTransactionWithdrawal (TransactionAmount,ctTransactionStatusID,tdTransactionDescriptionID,absBSAccountNumber)"
                                    + "VALUES ('5','TS000001','TD000010'," + AccNo + ")";
                            ps = conn.prepareStatement(sql);
                            ps.setString(1, AccNo);
                            ps.executeUpdate();
                            ps = conn.prepareStatement(sql1);
                            ps.executeUpdate();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    } finally {
                        JOptionPane.showMessageDialog(null, "Account Charged, Release the Card");
                    }
                    break;
                case 75:
                    try {
                        conn = DriverManager.getConnection(db.DatabaseConnectionUrl());
                        String sql = "UPDATE dbo.AccountPremierSavings SET PSAccountBalance = PSAccountBalance - 5 WHERE PSAccountNumber = ?";
                        String sql1 = "INSERT INTO dbo.CustomerTransactionWithdrawal (TransactionAmount,ctTransactionStatusID,tdTransactionDescriptionID,apsPSAccountNumber)"
                                    + "VALUES ('5','TS000001','TD000010'," + AccNo + ")";
                            ps = conn.prepareStatement(sql);
                            ps.setString(1, AccNo);
                            ps.executeUpdate();
                            ps = conn.prepareStatement(sql1);
                            ps.executeUpdate();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    } finally {
                        JOptionPane.showMessageDialog(null, "Account Charged, Release the Card");
                    }
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Account Not Valid");
                    break;
            }
        }

    }

    public void ChargesFamily(String Acc) {

        String AccNo = Acc;

        System.out.println(AccNo);
        if (AccNo.length() > 2) {
            int Type = parseInt(AccNo.substring(0, 2));
            switch (Type) {
                case 25:
                    try {
                        conn = DriverManager.getConnection(db.DatabaseConnectionUrl());
                        String sql = "UPDATE dbo.AccountNormalSavings SET NSAccountBalance = NSAccountBalance - 7 WHERE NSAccountNumber = ?";
                        String sql1 = "INSERT INTO dbo.CustomerTransactionWithdrawal (TransactionAmount,ctTransactionStatusID,tdTransactionDescriptionID,ansNSAccountNumber)"
                                    + "VALUES ('7','TS000001','TD000010'," + AccNo + ")";
                            ps = conn.prepareStatement(sql);
                            ps.setString(1, AccNo);
                            ps.executeUpdate();
                            ps = conn.prepareStatement(sql1);
                            ps.executeUpdate();

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    } finally {
                        JOptionPane.showMessageDialog(null, "Account Charged, Release the Family Card");
                    }
                    break;
                case 45:
                    try {
                        conn = DriverManager.getConnection(db.DatabaseConnectionUrl());
                        String sql = "UPDATE dbo.AccountBonusSavings SET BSAccountBalance = BSAccountBalance - 7 WHERE BSAccountNumber = ?";
                        String sql1 = "INSERT INTO dbo.CustomerTransactionWithdrawal (TransactionAmount,ctTransactionStatusID,tdTransactionDescriptionID,absBSAccountNumber)"
                                    + "VALUES ('7','TS000001','TD000010'," + AccNo + ")";
                            ps = conn.prepareStatement(sql);
                            ps.setString(1, AccNo);
                            ps.executeUpdate();
                            ps = conn.prepareStatement(sql1);
                            ps.executeUpdate();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    } finally {
                        JOptionPane.showMessageDialog(null, "Account Charged, Release the Family Card");
                    }
                    break;
                case 75:
                    try {
                        conn = DriverManager.getConnection(db.DatabaseConnectionUrl());
                        String sql = "UPDATE dbo.AccountPremierSavings SET PSAccountBalance = PSAccountBalance - 7 WHERE PSAccountNumber = ?";
                        String sql1 = "INSERT INTO dbo.CustomerTransactionWithdrawal (TransactionAmount,ctTransactionStatusID,tdTransactionDescriptionID,apsPSAccountNumber)"
                                    + "VALUES ('7','TS000001','TD000010'," + AccNo + ")";
                            ps = conn.prepareStatement(sql);
                            ps.setString(1, AccNo);
                            ps.executeUpdate();
                            ps = conn.prepareStatement(sql1);
                            ps.executeUpdate();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    } finally {
                        JOptionPane.showMessageDialog(null, "Account Charged, Release the Family Card");
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void LostCard(String Acc) {
        String AccNo = Acc;

        if (AccNo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "ENTER ACCOUNT NUMBER");
        } else {
            if (AccNo.length() > 2) {
                int Type = parseInt(AccNo.substring(0, 2));
                switch (Type) {
                    case 25:
                        try {
                            conn = DriverManager.getConnection(db.DatabaseConnectionUrl());
                            String sql = "UPDATE dbo.AccountNormalSavings SET NSAccountBalance = NSAccountBalance - 10 WHERE NSAccountNumber = ?";
                            String sql1 = "INSERT INTO dbo.CustomerTransactionWithdrawal (TransactionAmount,ctTransactionStatusID,tdTransactionDescriptionID,ansNSAccountNumber)"
                                    + "VALUES ('10','TS000001','TD000010'," + AccNo + ")";
                            ps = conn.prepareStatement(sql);
                            ps.setString(1, AccNo);
                            ps.executeUpdate();
                            ps = conn.prepareStatement(sql1);
                            ps.executeUpdate();

                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex);
                        } finally {
                            JOptionPane.showMessageDialog(null, "Account Charged, Release the Replacement Card");
                        }
                        break;
                    case 45:
                        try {
                            conn = DriverManager.getConnection(db.DatabaseConnectionUrl());
                            String sql = "UPDATE dbo.AccountBonusSavings SET BSAccountBalance = BSAccountBalance - 10 WHERE BSAccountNumber = ?";
                           String sql1 = "INSERT INTO dbo.CustomerTransactionWithdrawal (TransactionAmount,ctTransactionStatusID,tdTransactionDescriptionID,absBSAccountNumber)"
                                    + "VALUES ('10','TS000001','TD000010'," + AccNo + ")";
                            ps = conn.prepareStatement(sql);
                            ps.setString(1, AccNo);
                            ps.executeUpdate();
                            ps = conn.prepareStatement(sql1);
                            ps.executeUpdate();
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex);
                        } finally {
                            JOptionPane.showMessageDialog(null, "Account Charged, Release the Replacement Card");
                        }
                        break;
                    case 75:
                        try {
                            conn = DriverManager.getConnection(db.DatabaseConnectionUrl());
                            String sql = "UPDATE dbo.AccountPremierSavings SET PSAccountBalance = PSAccountBalance - 10 WHERE PSAccountNumber = ?";
                            String sql1 = "INSERT INTO dbo.CustomerTransactionWithdrawal (TransactionAmount,ctTransactionStatusID,tdTransactionDescriptionID,apsPSAccountNumber)"
                                    + "VALUES ('10','TS000001','TD000010'," + AccNo + ")";
                            ps = conn.prepareStatement(sql);
                            ps.setString(1, AccNo);
                            ps.executeUpdate();
                            ps = conn.prepareStatement(sql1);
                            ps.executeUpdate();
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex);
                        } finally {
                            JOptionPane.showMessageDialog(null, "Account Charged, Release the Replacement Card");
                        }
                        break;
                    default:
                        break;
                }
            }

        }
    }

    private void initComponents() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
