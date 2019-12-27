
import DatabaseConnection.DBConnection;
import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class DebitCard extends javax.swing.JFrame{

    String Acc;
    Connection conn = null;
    PreparedStatement ps = null;

    DBConnection db;

    /**
     * Creates new form DebitCard
     */
    public DebitCard() {
        initComponents();
        db = new DBConnection();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public DebitCard(String AccNo) {
        initComponents();
        accfetch.setText(AccNo);

        Acc = accfetch.getText();
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
        image = new javax.swing.JLabel();
        text = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        req = new javax.swing.JButton();
        famreq = new javax.swing.JButton();
        lostreq = new javax.swing.JButton();
        accno = new javax.swing.JTextField();
        accfetch = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 123, 146));

        image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/international-debit-card-388996_1280.jpg"))); // NOI18N

        text.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        text.setForeground(new java.awt.Color(255, 255, 255));
        text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        text.setText("YOUR ONE CARD FOR ALL YOUR NEEDS");

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("REQUEST EXTRA CARD FOR CUSTOMER FAMILY MEMBER - £7");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("REQUEST CARD FOR CUSTOMER - £5");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setText("LOST CARD REQUEST- £10");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Enter Account No");

        req.setText("Request");
        req.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reqActionPerformed(evt);
            }
        });

        famreq.setText("Request");
        famreq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                famreqActionPerformed(evt);
            }
        });

        lostreq.setText("Request");
        lostreq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lostreqActionPerformed(evt);
            }
        });

        accfetch.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        accfetch.setForeground(new java.awt.Color(255, 255, 255));

        jToggleButton1.setBackground(new java.awt.Color(204, 0, 51));
        jToggleButton1.setForeground(new java.awt.Color(255, 255, 255));
        jToggleButton1.setText("X");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(famreq)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(accfetch, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(req))
                    .addComponent(lostreq))
                .addGap(294, 294, 294))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(200, 200, 200)
                        .addComponent(text))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(48, 48, 48)
                                .addComponent(accno, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jToggleButton1)
                    .addComponent(image))
                .addGap(0, 12, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(54, 54, 54)
                    .addComponent(jLabel2)
                    .addContainerGap(574, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToggleButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(image, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(text)
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(req)
                    .addComponent(accfetch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(famreq))
                .addGap(70, 70, 70)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lostreq)
                    .addComponent(accno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(77, 77, 77))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(509, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addGap(236, 236, 236)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void reqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reqActionPerformed
        // Charge fee from customer
        Charges chrg = new Charges(Acc) {
            @Override
            public void debit() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void credit() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        
    }//GEN-LAST:event_reqActionPerformed
      
    private void famreqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_famreqActionPerformed
        // 2nd card Request
        String AccNo = Acc;
        if (AccNo.length() > 2) {
            int Type = parseInt(AccNo.substring(0, 2));
            if (Type == 25) {
                try {
                    conn = DriverManager.getConnection(db.DatabaseConnectionUrl());
                    String sql = "UPDATE dbo.AccountNormalSavings SET NSAccountBalance = NSAccountBalance - 7 WHERE NSAccountNumber = ?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, AccNo);
                    ps.executeUpdate();

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                } finally {
                    JOptionPane.showMessageDialog(null, "Account Charged, Release the Family Card");
                }
            } else if (Type == 45) {
                try {
                    conn = DriverManager.getConnection(db.DatabaseConnectionUrl());
                    String sql = "UPDATE dbo.AccountBonusSavings SET BSAccountBalance = BSAccountBalance - 7 WHERE BSAccountNumber = ?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, AccNo);
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                } finally {
                    JOptionPane.showMessageDialog(null, "Account Charged, Release the Family Card");
                }
            } else if (Type == 75) {
                try {
                    conn = DriverManager.getConnection(db.DatabaseConnectionUrl());
                    String sql = "UPDATE dbo.AccountPremierSavings SET PSAccountBalance = PSAccountBalance - 7 WHERE PSAccountNumber = ?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, AccNo);
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                } finally {
                    JOptionPane.showMessageDialog(null, "Account Charged, Release the Family Card");
                }
            }
    }//GEN-LAST:event_famreqActionPerformed
    }
    private void lostreqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lostreqActionPerformed
        // lost card request
        String AccNo = accno.getText();
        if(AccNo.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "ENTER ACCOUNT NUMBER");
        }
        else if (AccNo.length() > 2) {
            int Type = parseInt(AccNo.substring(0, 2));
            if (Type == 25) {
                try {
                    conn = DriverManager.getConnection(db.DatabaseConnectionUrl());
                    String sql = "UPDATE dbo.AccountNormalSavings SET NSAccountBalance = NSAccountBalance - 10 WHERE NSAccountNumber = ?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, AccNo);
                    ps.executeUpdate();

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                } finally {
                    JOptionPane.showMessageDialog(null, "Account Charged, Release the Replacement Card");
                }
            } else if (Type == 45) {
                try {
                    conn = DriverManager.getConnection(db.DatabaseConnectionUrl());
                    String sql = "UPDATE dbo.AccountBonusSavings SET BSAccountBalance = BSAccountBalance - 10 WHERE BSAccountNumber = ?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, AccNo);
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                } finally {
                    JOptionPane.showMessageDialog(null, "Account Charged, Release the Replacement Card");
                }
            } else if (Type == 75) {
                try {
                    conn = DriverManager.getConnection(db.DatabaseConnectionUrl());
                    String sql = "UPDATE dbo.AccountPremierSavings SET PSAccountBalance = PSAccountBalance - 10 WHERE PSAccountNumber = ?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, AccNo);
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                } finally {
                    JOptionPane.showMessageDialog(null, "Account Charged, Release the Replacement Card");
                }
            }
        }
    }//GEN-LAST:event_lostreqActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // CLOSING
         this.setVisible(false);
    }//GEN-LAST:event_jToggleButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(DebitCard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DebitCard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DebitCard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DebitCard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DebitCard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel accfetch;
    private javax.swing.JTextField accno;
    private javax.swing.JButton famreq;
    private javax.swing.JLabel image;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JButton lostreq;
    private javax.swing.JButton req;
    private javax.swing.JLabel text;
    // End of variables declaration//GEN-END:variables
}
