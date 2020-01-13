/**
* SOFT255SL COURSEWORK C1 T1
* Team No:1 
* Team Name: TEAM QUINN 
* Project: Bank Management System.
 */
import java.awt.Desktop;
import java.io.File;
import DatabaseConnection.DBConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.proteanit.sql.DbUtils;
import javax.swing.JOptionPane;

import LocalTimeAndDate.LocalTimeAndDate;

/**
 * This form allows the bank manager to view generated reports, current transactions
 * Design, Data Management, Logic Implementation done by R.P.L and K.M.A.R.P.C, 
 * Debit Card Charges done by R.P.L
 * @author ranul
 */
public class Manager extends javax.swing.JFrame {
// Creating new object to retrieve current date and time

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    //creating a new object to get database connection class
    DBConnection db;

    // Creating new object to retreive current date and time
    LocalTimeAndDate ltad;

    /**
     * Creates new form Manager
     */
    public Manager() {
        initComponents();
        
        // Creating new object to retrieve current date and time
        ltad = new LocalTimeAndDate();
        
        db = new DBConnection();
        
        // Setting current time
        jlbl_localTime.setText(ltad.retrieveLocalTimeWith12HourClock());

        // Setting current date
        jlbl_localDate.setText(ltad.retrieveLocalDate());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        AccInfo_pnl = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        AccountINFO = new javax.swing.JTable();
        jlbl_localTime = new javax.swing.JLabel();
        jlbl_localDate = new javax.swing.JLabel();
        depositbtn = new javax.swing.JButton();
        withdrawalbtn = new javax.swing.JButton();
        dailyReports = new javax.swing.JButton();
        monthlyReports = new javax.swing.JButton();
        cardrequest = new javax.swing.JButton();
        jFileChooser1 = new javax.swing.JFileChooser();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(18, 63, 72));

        AccInfo_pnl.setBackground(new java.awt.Color(18, 63, 72));
        AccInfo_pnl.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                AccInfo_pnlMouseDragged(evt);
            }
        });
        AccInfo_pnl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                AccInfo_pnlMousePressed(evt);
            }
        });

        AccountINFO.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        AccountINFO.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Transaction No", "Transaction Type", "Teller ID", "Account No", "Account Type", "Amount", "Date and Time"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(AccountINFO);

        jlbl_localTime.setFont(new java.awt.Font("Leelawadee UI", 1, 16)); // NOI18N
        jlbl_localTime.setForeground(new java.awt.Color(255, 255, 255));
        jlbl_localTime.setText("Time");

        jlbl_localDate.setFont(new java.awt.Font("Leelawadee UI", 1, 16)); // NOI18N
        jlbl_localDate.setForeground(new java.awt.Color(255, 255, 255));
        jlbl_localDate.setText("Date");

        depositbtn.setBackground(new java.awt.Color(255, 255, 255));
        depositbtn.setFont(new java.awt.Font("Lucida Sans", 1, 14)); // NOI18N
        depositbtn.setText("Deposits Check");
        depositbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                depositbtnActionPerformed(evt);
            }
        });

        withdrawalbtn.setBackground(new java.awt.Color(255, 255, 255));
        withdrawalbtn.setFont(new java.awt.Font("Lucida Sans", 1, 14)); // NOI18N
        withdrawalbtn.setText("Withdrawal's Check");
        withdrawalbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                withdrawalbtnActionPerformed(evt);
            }
        });

        dailyReports.setBackground(new java.awt.Color(255, 255, 255));
        dailyReports.setFont(new java.awt.Font("Lucida Sans", 1, 14)); // NOI18N
        dailyReports.setText("Daily Reports");
        dailyReports.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dailyReportsActionPerformed(evt);
            }
        });

        monthlyReports.setBackground(new java.awt.Color(255, 255, 255));
        monthlyReports.setFont(new java.awt.Font("Lucida Sans", 1, 14)); // NOI18N
        monthlyReports.setText("Monthly Reports");
        monthlyReports.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthlyReportsActionPerformed(evt);
            }
        });

        cardrequest.setBackground(new java.awt.Color(255, 255, 204));
        cardrequest.setFont(new java.awt.Font("Lucida Sans", 1, 16)); // NOI18N
        cardrequest.setText("Request Debit Card");
        cardrequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardrequestActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AccInfo_pnlLayout = new javax.swing.GroupLayout(AccInfo_pnl);
        AccInfo_pnl.setLayout(AccInfo_pnlLayout);
        AccInfo_pnlLayout.setHorizontalGroup(
            AccInfo_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AccInfo_pnlLayout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(AccInfo_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AccInfo_pnlLayout.createSequentialGroup()
                        .addComponent(depositbtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(withdrawalbtn)
                        .addGap(18, 18, 18)
                        .addComponent(monthlyReports)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dailyReports)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cardrequest)
                        .addGap(43, 43, 43))
                    .addGroup(AccInfo_pnlLayout.createSequentialGroup()
                        .addGroup(AccInfo_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(AccInfo_pnlLayout.createSequentialGroup()
                                .addComponent(jlbl_localTime)
                                .addGap(30, 30, 30)
                                .addComponent(jlbl_localDate))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1235, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(30, Short.MAX_VALUE))))
        );
        AccInfo_pnlLayout.setVerticalGroup(
            AccInfo_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AccInfo_pnlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AccInfo_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbl_localTime, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbl_localDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 109, Short.MAX_VALUE)
                .addGroup(AccInfo_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(depositbtn)
                    .addComponent(withdrawalbtn)
                    .addComponent(dailyReports)
                    .addComponent(monthlyReports)
                    .addComponent(cardrequest))
                .addGap(36, 36, 36)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AccInfo_pnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(AccInfo_pnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jMenu1.setText("File");

        jMenuItem2.setText("Logout");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        //Closing Form and Reopening the Login Screeen
        Access a1 = new Access();
        a1.setVisible(true);
        //closes the login form prevent unnecessary tab creation
        this.setVisible(false);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void monthlyReportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthlyReportsActionPerformed
        File folder = new File("C:\\Users\\ranul\\Documents\\GitHub\\SOFT255SL_SOFTWARE_ENIGINEERING_FOR_THE_INTERNET_USING_JAVA\\JAVA PROGRAM\\Reports\\Monthly_Customer_Transaction_Record_Reports"); // path to the directory to be opened
        Desktop desktop = null;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
        }

        try {
            desktop.open(folder);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Oops GENERATED REPORTS cannot be located, Please contact the bank administration for RECOVERY PROCESS.\n\n THANK YOU.",
                    "E01 - FOLDER MISSING !",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_monthlyReportsActionPerformed

    private void dailyReportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dailyReportsActionPerformed
        File folder = new File("C:\\Users\\ranul\\Documents\\GitHub\\SOFT255SL_SOFTWARE_ENIGINEERING_FOR_THE_INTERNET_USING_JAVA\\JAVA PROGRAM\\Reports\\Daily_Customer_Transaction_Record_Reports"); // path to the directory to be opened
        Desktop desktop = null;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
        }

        try {
            desktop.open(folder);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Oops GENERATED REPORTS cannot be located, Please contact the bank administration for RECOVERY PROCESS.\n\n THANK YOU.",
                    "E01 - FOLDER MISSING !",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_dailyReportsActionPerformed

    private void withdrawalbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_withdrawalbtnActionPerformed
       try {
            conn=DriverManager.getConnection(db.DatabaseConnectionUrl());
            String sql="SELECT * FROM CustomerTransactionWithdrawal";
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            
            AccountINFO.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException ex) {
            Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_withdrawalbtnActionPerformed

    private void depositbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_depositbtnActionPerformed
        try {
            conn = DriverManager.getConnection(db.DatabaseConnectionUrl());
            String sql1 = "Select * from CustomerTransactionDeposit";
            ps = conn.prepareStatement(sql1);
            rs = ps.executeQuery();
            AccountINFO.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (SQLException ex) {
            Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_depositbtnActionPerformed

    private void cardrequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cardrequestActionPerformed
        // Request Debit Card
     
        new DebitCard().setVisible(true);
    }//GEN-LAST:event_cardrequestActionPerformed
 static int xcord,ycord;
    private void AccInfo_pnlMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AccInfo_pnlMousePressed
         xcord = evt.getX();
        ycord = evt.getY();
    }//GEN-LAST:event_AccInfo_pnlMousePressed

    private void AccInfo_pnlMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AccInfo_pnlMouseDragged
       int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xcord, y - ycord);
    }//GEN-LAST:event_AccInfo_pnlMouseDragged

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Manager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Manager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Manager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Manager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Manager().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AccInfo_pnl;
    private javax.swing.JTable AccountINFO;
    private javax.swing.JButton cardrequest;
    private javax.swing.JButton dailyReports;
    private javax.swing.JButton depositbtn;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel jlbl_localDate;
    private javax.swing.JLabel jlbl_localTime;
    private javax.swing.JButton monthlyReports;
    private javax.swing.JButton withdrawalbtn;
    // End of variables declaration//GEN-END:variables
}
