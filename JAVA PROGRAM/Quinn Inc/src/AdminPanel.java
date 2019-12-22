
import DatabaseConnection.DBConnection;
import LocalTimeAndDate.LocalTimeAndDate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ranul
 */
public class AdminPanel extends javax.swing.JFrame {

    // Creating new object to retreive current date and time
    LocalTimeAndDate ltad;
    //
    Connection conn = null;
    PreparedStatement ps=null;
    ResultSet rs=null;
    
    //creating a new object to get database connection class
    DBConnection db;
    
  
    
    /**
     * Creates new form AdminPanel
     */
    public AdminPanel() {
        initComponents();

        db=new DBConnection();
        
        // Creating new object to retrieve current date and time
        ltad = new LocalTimeAndDate();
        
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        admin_pnl = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        panel_Lbl = new javax.swing.JLabel();
        jlbl_localDate = new javax.swing.JLabel();
        jlbl_localTime = new javax.swing.JLabel();
        showTellers_Btn = new javax.swing.JButton();
        removeTellers_Btn = new javax.swing.JButton();
        updateCred1_Btn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TellerINFO = new javax.swing.JTable();
        transaction_Btn = new javax.swing.JButton();
        showManagers_Btn = new javax.swing.JButton();
        removeManagers_Btn = new javax.swing.JButton();
        updateCred2_Btn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        ManagersINFO = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        currentTNX_table = new javax.swing.JTable();
        insertManager_Btn = new javax.swing.JButton();
        insertTeller_Btn = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        logout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        admin_pnl.setBackground(new java.awt.Color(246, 246, 246));
        admin_pnl.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel2.setBackground(new java.awt.Color(0, 0, 51));

        panel_Lbl.setFont(new java.awt.Font("Constantia", 0, 36)); // NOI18N
        panel_Lbl.setForeground(new java.awt.Color(255, 255, 255));
        panel_Lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panel_Lbl.setText("ADMIN PANEL - QUINN INC BANK");

        jlbl_localDate.setFont(new java.awt.Font("Leelawadee UI", 1, 16)); // NOI18N
        jlbl_localDate.setForeground(new java.awt.Color(255, 255, 255));
        jlbl_localDate.setText("Date");

        jlbl_localTime.setFont(new java.awt.Font("Leelawadee UI", 1, 16)); // NOI18N
        jlbl_localTime.setForeground(new java.awt.Color(255, 255, 255));
        jlbl_localTime.setText("Time");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addComponent(panel_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 1675, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlbl_localTime)
                .addGap(36, 36, 36)
                .addComponent(jlbl_localDate)
                .addGap(25, 25, 25))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbl_localDate, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbl_localTime, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panel_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        showTellers_Btn.setBackground(new java.awt.Color(51, 51, 51));
        showTellers_Btn.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        showTellers_Btn.setForeground(new java.awt.Color(255, 255, 255));
        showTellers_Btn.setText("Show All Tellers");
        showTellers_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showTellers_BtnActionPerformed(evt);
            }
        });

        removeTellers_Btn.setBackground(new java.awt.Color(51, 51, 51));
        removeTellers_Btn.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        removeTellers_Btn.setForeground(new java.awt.Color(255, 255, 255));
        removeTellers_Btn.setText("Remove Teller");
        removeTellers_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeTellers_BtnActionPerformed(evt);
            }
        });

        updateCred1_Btn.setBackground(new java.awt.Color(51, 51, 51));
        updateCred1_Btn.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        updateCred1_Btn.setForeground(new java.awt.Color(255, 255, 255));
        updateCred1_Btn.setText("Update Credentials");
        updateCred1_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateCred1_BtnActionPerformed(evt);
            }
        });

        TellerINFO.setBorder(new javax.swing.border.MatteBorder(null));
        TellerINFO.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        TellerINFO.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Teller ID", "Branch", "First Name", "Last Name", "Lane Address", "City", "E-Mail", "Login Id"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TellerINFO);

        transaction_Btn.setBackground(new java.awt.Color(51, 51, 51));
        transaction_Btn.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        transaction_Btn.setForeground(new java.awt.Color(255, 255, 255));
        transaction_Btn.setText("Customer transactions Withdrawal");
        transaction_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transaction_BtnActionPerformed(evt);
            }
        });

        showManagers_Btn.setBackground(new java.awt.Color(51, 51, 51));
        showManagers_Btn.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        showManagers_Btn.setForeground(new java.awt.Color(255, 255, 255));
        showManagers_Btn.setText("Show All Managers");
        showManagers_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showManagers_BtnActionPerformed(evt);
            }
        });

        removeManagers_Btn.setBackground(new java.awt.Color(51, 51, 51));
        removeManagers_Btn.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        removeManagers_Btn.setForeground(new java.awt.Color(255, 255, 255));
        removeManagers_Btn.setText("Remove Manager");
        removeManagers_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeManagers_BtnActionPerformed(evt);
            }
        });

        updateCred2_Btn.setBackground(new java.awt.Color(51, 51, 51));
        updateCred2_Btn.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        updateCred2_Btn.setForeground(new java.awt.Color(255, 255, 255));
        updateCred2_Btn.setText("Update Credentials");
        updateCred2_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateCred2_BtnActionPerformed(evt);
            }
        });

        ManagersINFO.setBorder(new javax.swing.border.MatteBorder(null));
        ManagersINFO.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ManagersINFO.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Manager ID", "Branch", "First Name", "Last Name", "Lane Address", "City", "E-Mail Address", "Login Id"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(ManagersINFO);

        currentTNX_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Transaction Number", "Transaction Amount", "Transaction date and time", "NSAccount No", "BSAccount Number", "TellerID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(currentTNX_table);

        insertManager_Btn.setBackground(new java.awt.Color(51, 51, 51));
        insertManager_Btn.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        insertManager_Btn.setForeground(new java.awt.Color(255, 255, 255));
        insertManager_Btn.setText("Insert New Managers");
        insertManager_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertManager_BtnActionPerformed(evt);
            }
        });

        insertTeller_Btn.setBackground(new java.awt.Color(51, 51, 51));
        insertTeller_Btn.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        insertTeller_Btn.setForeground(new java.awt.Color(255, 255, 255));
        insertTeller_Btn.setText("Insert New Tellers");
        insertTeller_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertTeller_BtnActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(51, 51, 51));
        jButton1.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Customer transaction Deposit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout admin_pnlLayout = new javax.swing.GroupLayout(admin_pnl);
        admin_pnl.setLayout(admin_pnlLayout);
        admin_pnlLayout.setHorizontalGroup(
            admin_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(admin_pnlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(admin_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(admin_pnlLayout.createSequentialGroup()
                        .addComponent(transaction_Btn)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, admin_pnlLayout.createSequentialGroup()
                        .addGroup(admin_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(admin_pnlLayout.createSequentialGroup()
                                .addComponent(showTellers_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(63, 63, 63)
                                .addComponent(removeTellers_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(72, 72, 72)
                                .addComponent(updateCred1_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(insertTeller_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(admin_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(admin_pnlLayout.createSequentialGroup()
                                .addComponent(showManagers_Btn)
                                .addGap(80, 80, 80)
                                .addComponent(removeManagers_Btn)
                                .addGap(68, 68, 68)
                                .addComponent(updateCred2_Btn)
                                .addGap(55, 55, 55)
                                .addComponent(insertManager_Btn))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 949, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(22, 22, 22))
        );
        admin_pnlLayout.setVerticalGroup(
            admin_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(admin_pnlLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(admin_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, admin_pnlLayout.createSequentialGroup()
                        .addGroup(admin_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(showTellers_Btn)
                            .addComponent(removeTellers_Btn))
                        .addGap(32, 32, 32)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, admin_pnlLayout.createSequentialGroup()
                        .addGroup(admin_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(removeManagers_Btn)
                            .addComponent(updateCred2_Btn)
                            .addComponent(insertManager_Btn)
                            .addComponent(insertTeller_Btn)
                            .addComponent(updateCred1_Btn)
                            .addComponent(showManagers_Btn))
                        .addGap(32, 32, 32)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(admin_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(transaction_Btn)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jMenu1.setText("Menu");
        jMenu1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        logout.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        logout.setText("Logout");
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });
        jMenu1.add(logout);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(admin_pnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(admin_pnl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        // TODO add your handling code here:
        //Closing Form and Reopening the Login Screeen
        Access a1 = new Access();
        a1.setVisible(true);
        //closes the login form prevent unnecessary tab creation
        this.setVisible(false);
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        // TODO add your handling code here:Access a1 = new Access();
        //Closing Form and Reopening the Login Screeen
        Access a1 = new Access();
        a1.setVisible(true);
        //closes the login form prevent unnecessary tab creation
        this.setVisible(false);
    }//GEN-LAST:event_logoutActionPerformed

    private void showTellers_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showTellers_BtnActionPerformed
        
        try {
            //connection to the databbase
            conn=DriverManager.getConnection(db.DatabaseConnectionUrl());
            //The select statement
            String sql="SELECT TellerID,Branch,FirstName,LastName,LaneAddress, City, EmailAddress,slSystemLoginID FROM dbo.Teller";
            ps=conn.prepareStatement(sql);
            //executes the query
            rs=ps.executeQuery();
            
            //display the retrived data in the table
            TellerINFO.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException ex) {
            Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }//GEN-LAST:event_showTellers_BtnActionPerformed

    private void removeTellers_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeTellers_BtnActionPerformed
        
          try {
              
              // The database connection
            conn=DriverManager.getConnection(db.DatabaseConnectionUrl());
            //get and store selected row
            int row=TellerINFO.getSelectedRow();
            // get the data from row 
            String value=TellerINFO.getModel().getValueAt(row, 0).toString();
            //sql select statement
            String sql="Delete from dbo.Teller where TellerID=" + value;
            ps=conn.prepareStatement(sql);
            //executes the update
            ps.executeUpdate();
            //Display the message 
            JOptionPane.showMessageDialog(null,"Delete Successfull");
            
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }//GEN-LAST:event_removeTellers_BtnActionPerformed

    private void showManagers_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showManagers_BtnActionPerformed
        
         try {
            conn=DriverManager.getConnection(db.DatabaseConnectionUrl());
            String sql="SELECT ManagerId,Branch,FirstName,LastName,LaneAddress, City, EmailAddress,slSystemLoginID FROM dbo.Manager";
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            
            ManagersINFO.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException ex) {
            Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
          
    }//GEN-LAST:event_showManagers_BtnActionPerformed

    private void transaction_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transaction_BtnActionPerformed
       
        try {
            conn=DriverManager.getConnection(db.DatabaseConnectionUrl());
            String sql="SELECT * FROM CustomerTransactionWithdrawal";
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            
            currentTNX_table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException ex) {
            Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_transaction_BtnActionPerformed

    private void updateCred2_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateCred2_BtnActionPerformed
       UpdateManagers up=new UpdateManagers();
       up.setVisible(true);
       
    }//GEN-LAST:event_updateCred2_BtnActionPerformed

    private void updateCred1_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateCred1_BtnActionPerformed
        
        UpdateTellers up= new UpdateTellers();
        up.setVisible(true);
        
        
    }//GEN-LAST:event_updateCred1_BtnActionPerformed

    private void insertManager_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertManager_BtnActionPerformed
        CreateManager cm= new CreateManager();
        cm.setVisible(true);
        
    }//GEN-LAST:event_insertManager_BtnActionPerformed

    private void insertTeller_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertTeller_BtnActionPerformed
        CreateTellers ct = new CreateTellers();
        ct.setVisible(true);
    }//GEN-LAST:event_insertTeller_BtnActionPerformed

    private void removeManagers_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeManagers_BtnActionPerformed
        
        try {
              // The database connection
            conn=DriverManager.getConnection(db.DatabaseConnectionUrl());
            //get and store selected row
            int row=ManagersINFO.getSelectedRow();
            // get the data from row 
            String value=ManagersINFO.getModel().getValueAt(row,0).toString();
            //sql select statement
            String sql="DELETE FROM dbo.Manager  WHERE ManagerId="+value;
            ps=conn.prepareStatement(sql);
            //executes the update
            ps.executeUpdate();
            //Display the message 
            JOptionPane.showMessageDialog(null,"Delete Successful ");
            
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_removeManagers_BtnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            conn=DriverManager.getConnection(db.DatabaseConnectionUrl());
            String sql1="Select * from CustomerTransactionDeposit";
            ps=conn.prepareStatement(sql1);
            rs=ps.executeQuery();
            currentTNX_table.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(AdminPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminPanel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable ManagersINFO;
    private javax.swing.JTable TellerINFO;
    private javax.swing.JPanel admin_pnl;
    private javax.swing.JTable currentTNX_table;
    private javax.swing.JButton insertManager_Btn;
    private javax.swing.JButton insertTeller_Btn;
    private javax.swing.JButton jButton1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel jlbl_localDate;
    private javax.swing.JLabel jlbl_localTime;
    private javax.swing.JMenuItem logout;
    private javax.swing.JLabel panel_Lbl;
    private javax.swing.JButton removeManagers_Btn;
    private javax.swing.JButton removeTellers_Btn;
    private javax.swing.JButton showManagers_Btn;
    private javax.swing.JButton showTellers_Btn;
    private javax.swing.JButton transaction_Btn;
    private javax.swing.JButton updateCred1_Btn;
    private javax.swing.JButton updateCred2_Btn;
    // End of variables declaration//GEN-END:variables
}
