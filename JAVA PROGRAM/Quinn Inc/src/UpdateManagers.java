
import DatabaseConnection.DBConnection;
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
 * @author Hp-Pc
 */
public class UpdateManagers extends javax.swing.JFrame {

    /**
     * Creates new form UpdateManagers
     */
    
    Connection conn=null;
    PreparedStatement ps=null;
    
    DBConnection db;
    
    public UpdateManagers() {
        initComponents();
        
        db=new DBConnection();
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        mUpdate_btn = new javax.swing.JButton();
        Managerid_Lbl = new javax.swing.JLabel();
        User_Lbl = new javax.swing.JLabel();
        Password_Lbl = new javax.swing.JLabel();
        Loginid_Txt = new javax.swing.JTextField();
        Username_Txt = new javax.swing.JTextField();
        Password_Txt = new javax.swing.JTextField();
        close = new javax.swing.JButton();
        Branch_Lbl = new javax.swing.JLabel();
        address_Lbl = new javax.swing.JLabel();
        city_Lbl = new javax.swing.JLabel();
        Email_Lbl = new javax.swing.JLabel();
        slpPosition_Lbl = new javax.swing.JLabel();
        position_Txt = new javax.swing.JTextField();
        branch_Txt = new javax.swing.JTextField();
        address_Txt = new javax.swing.JTextField();
        email_Txt = new javax.swing.JTextField();
        city_Txt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(0, 123, 146));

        jPanel1.setBackground(new java.awt.Color(18, 63, 72));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        mUpdate_btn.setBackground(new java.awt.Color(0, 0, 51));
        mUpdate_btn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mUpdate_btn.setForeground(new java.awt.Color(255, 255, 255));
        mUpdate_btn.setText("Update");
        mUpdate_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mUpdate_btnActionPerformed(evt);
            }
        });

        Managerid_Lbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Managerid_Lbl.setForeground(new java.awt.Color(255, 255, 255));
        Managerid_Lbl.setText("System Login ID");

        User_Lbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        User_Lbl.setForeground(new java.awt.Color(255, 255, 255));
        User_Lbl.setText("User Name");

        Password_Lbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Password_Lbl.setForeground(new java.awt.Color(255, 255, 255));
        Password_Lbl.setText("Password");

        close.setBackground(new java.awt.Color(0, 123, 146));
        close.setForeground(new java.awt.Color(255, 255, 255));
        close.setText("X");
        close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeActionPerformed(evt);
            }
        });

        Branch_Lbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Branch_Lbl.setForeground(new java.awt.Color(240, 240, 240));
        Branch_Lbl.setText("Branch");

        address_Lbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        address_Lbl.setForeground(new java.awt.Color(240, 240, 240));
        address_Lbl.setText("Lane Address");

        city_Lbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        city_Lbl.setForeground(new java.awt.Color(240, 240, 240));
        city_Lbl.setText("City");

        Email_Lbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Email_Lbl.setForeground(new java.awt.Color(240, 240, 240));
        Email_Lbl.setText("Email Address");

        slpPosition_Lbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        slpPosition_Lbl.setForeground(new java.awt.Color(240, 240, 240));
        slpPosition_Lbl.setText("slpPosition ID");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(close))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(Managerid_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(User_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Password_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(Email_Lbl)
                    .addComponent(city_Lbl)
                    .addComponent(address_Lbl)
                    .addComponent(Branch_Lbl)
                    .addComponent(slpPosition_Lbl))
                .addGap(144, 144, 144)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Password_Txt)
                    .addComponent(Username_Txt)
                    .addComponent(email_Txt, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                    .addComponent(address_Txt)
                    .addComponent(branch_Txt)
                    .addComponent(position_Txt)
                    .addComponent(Loginid_Txt)
                    .addComponent(city_Txt))
                .addGap(79, 79, 79))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(226, 226, 226)
                .addComponent(mUpdate_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(close)
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Managerid_Lbl)
                    .addComponent(Loginid_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(User_Lbl)
                    .addComponent(Username_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Password_Lbl)
                    .addComponent(Password_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(position_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(slpPosition_Lbl))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(branch_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Branch_Lbl))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(address_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(address_Lbl))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(city_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(city_Lbl))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(email_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Email_Lbl))
                .addGap(44, 44, 44)
                .addComponent(mUpdate_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void mUpdate_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mUpdate_btnActionPerformed
        
        try {
            conn = DriverManager.getConnection(db.DatabaseConnectionUrl());
            String sql="UPDATE dbo.SystemLogin SET Username =?, Password =? WHERE SystemLoginID =?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,Username_Txt.getText());
            ps.setString(2,Password_Txt.getText());
            ps.setString(3,Loginid_Txt.getText());
            ps.executeUpdate();
            
            String sql2="UPDATE dbo.Manager SET Branch=?, LaneAddress=?, City=?, EmailAddress=? where slSystemLoginID=?";
            ps=conn.prepareStatement(sql2);
            ps.setString(1, branch_Txt.getText());
            ps.setString(2, address_Txt.getText());
            ps.setString(3, city_Txt.getText());
            ps.setString(4, email_Txt.getText());
            ps.setString(5, Loginid_Txt.getText());
            ps.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Update successful");
            
            //closes the window
            this.dispose();
          
            
        } catch (SQLException ex) {
            Logger.getLogger(UpdateManagers.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
            
    }//GEN-LAST:event_mUpdate_btnActionPerformed

    private void closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeActionPerformed
        //Closes the Updatinf Form
        this.setVisible(false);
    }//GEN-LAST:event_closeActionPerformed

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
            java.util.logging.Logger.getLogger(UpdateManagers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateManagers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateManagers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateManagers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UpdateManagers().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Branch_Lbl;
    private javax.swing.JLabel Email_Lbl;
    private javax.swing.JTextField Loginid_Txt;
    private javax.swing.JLabel Managerid_Lbl;
    private javax.swing.JLabel Password_Lbl;
    private javax.swing.JTextField Password_Txt;
    private javax.swing.JLabel User_Lbl;
    private javax.swing.JTextField Username_Txt;
    private javax.swing.JLabel address_Lbl;
    private javax.swing.JTextField address_Txt;
    private javax.swing.JTextField branch_Txt;
    private javax.swing.JLabel city_Lbl;
    private javax.swing.JTextField city_Txt;
    private javax.swing.JButton close;
    private javax.swing.JTextField email_Txt;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton mUpdate_btn;
    private javax.swing.JTextField position_Txt;
    private javax.swing.JLabel slpPosition_Lbl;
    // End of variables declaration//GEN-END:variables
}
